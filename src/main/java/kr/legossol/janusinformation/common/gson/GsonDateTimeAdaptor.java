package kr.legossol.janusinformation.common.gson;

import com.google.gson.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
public class GsonDateTimeAdaptor implements JsonSerializer<Object>, JsonDeserializer<Object> {
    private final String serializeFormat;
    private final List<String> deserializeFormats;

    GsonDateTimeAdaptor(@NotNull @Valid Config config) {
        this.serializeFormat = config.getSerialize();

        List<String> tmpFormats = config.getDeserialize();
        if (CollectionUtils.isEmpty(tmpFormats)) {
            tmpFormats = Collections.singletonList(this.serializeFormat);
        }
        this.deserializeFormats = tmpFormats;
    }

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Class<?> typeClass = (Class<?>) typeOfT;
        List<String> formats = this.deserializeFormats;
        String dateTimeValue = json.getAsString();
        if (LocalDateTime.class.equals(typeClass)) {
            return parse(formats, dateTimeValue, (format, value) -> LocalDateTime.from(DateTimeFormatter.ofPattern(format).parse(value)));
        } else if(ZonedDateTime.class.equals(typeClass)) {
            return parse(formats, dateTimeValue, (format, value) -> {
                TemporalAccessor parseTemporalAccessor = DateTimeFormatter.ofPattern(format).parse(value);
                try {
                    return ZonedDateTime.from(parseTemporalAccessor);
                } catch (Exception e) {
                    return ZonedDateTime.of(LocalDateTime.from(parseTemporalAccessor), ZoneId.systemDefault());
                }
            });
        }else if(LocalDate.class.equals(typeClass)) {
            LocalDateTime localDateTime = (LocalDateTime) parse(formats, dateTimeValue, (format, value) -> LocalDateTime.from(DateTimeFormatter.ofPattern(format).parse(value)));
            return localDateTime.toLocalDate();
        }else if(LocalTime.class.equals(typeClass)) {
            LocalDateTime localDateTime = (LocalDateTime) parse(formats, dateTimeValue, (format, value) -> LocalDateTime.from(DateTimeFormatter.ofPattern(format).parse(value)));
            return localDateTime.toLocalTime();
        }else if (Date.class.isAssignableFrom(typeClass)) {
            Date date = (Date) parse(formats, dateTimeValue, (format, value) -> new SimpleDateFormat(format).parse(value));
            if(Date.class.equals(typeClass)) {
                return date;
            } else if(Timestamp.class.equals(typeClass)) {
                return new Timestamp(date.getTime());
            } else if(java.sql.Date.class.equals(typeClass)) {
                return new java.sql.Date(date.getTime());
            } else if(Time.class.equals(typeClass)) {
                return new Time(date.getTime());
            }throw new JsonParseException("Not support deserialize date/time class. class = " + typeOfT.getTypeName());
        }
        throw new JsonParseException("Not support deserialize date/time class. class = " + typeOfT.getTypeName());
    }

    private Object parse(List<String> formats, String value, DateTimeParseFunction parseFunction) {
        for(String format : formats) {
            try {
                return parseFunction.parse(format, value);
            } catch (Exception ignore) {}
        }
        throw new JsonParseException("Cannot parse date/time format. Undefined. value = " + value);
    }

    interface DateTimeParseFunction {
        Object parse(String format, String value) throws Exception;
    }

    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        Class<?> typeClass = (Class<?>) typeOfSrc;
        String responseFormat = this.serializeFormat;

        // LocalTime일 때는 별도의 포맷 필요
        if (typeOfSrc.equals(LocalTime.class)) {
            responseFormat = "HH:mm:ss";
        }

        if(Date.class.isAssignableFrom(typeClass)) {
            return new JsonPrimitive(new SimpleDateFormat(responseFormat).format(src));
        } else if(TemporalAccessor.class.isAssignableFrom(typeClass)) {
            return new JsonPrimitive(DateTimeFormatter.ofPattern(responseFormat).format((TemporalAccessor) src));
        }
        throw new JsonParseException("Not support serialize date/time class. class = " + typeOfSrc.getTypeName());
    }
    @Getter(AccessLevel.PACKAGE)
    @NoArgsConstructor
    @Validated
    @ToString
    public static class Config{
        @NotNull
        @Setter(AccessLevel.PACKAGE)
        private String serialize;
        private List<String> deserialize;

        void setDeserialize(List<String> deserialize){this.deserialize = deserialize;}
        void setDeserialize(String deserialize){
            this.deserialize = Collections.singletonList(deserialize);
        }

    }
}