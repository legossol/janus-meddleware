package kr.legossol.janusinformation.common.gson;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.gson.GsonProperties;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Configuration
@Slf4j
public class GsonConfig {
    public static final String CUSTOM_HTTP_MESSAGE_CONVERTERS_BEAN_NAME = "customHttpMessageConverters";

    @Bean
    public Gson gson(GsonProperties gsonProperties, GsonConfigProperties config) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .serializeNulls()
                .setLenient()
                .addSerializationExclusionStrategy(new GsonExclusionStrategy(GsonExclude.When.SERIALIZE))
                .addDeserializationExclusionStrategy(new GsonExclusionStrategy(GsonExclude.When.DESERIALIZE))
                .registerTypeAdapter(Page.class, new PageableAdaptor())
                .registerTypeAdapter(Pageable.class, new PageableAdaptor())
                .registerTypeAdapter(Double.class, new DoubleJsonSerializer())
                .registerTypeAdapterFactory(OptionalTypeAdapter.FACTORY);


        if (StringUtils.hasText(gsonProperties.getDateFormat())) {
            gsonBuilder.setDateFormat(gsonProperties.getDateFormat());
        }

        if (config.getDateTimeFormat() != null
                && StringUtils.hasText(config.getDateTimeFormat().getSerialize())) {
            gsonBuilder
                    .registerTypeAdapter(LocalDateTime.class, new GsonDateTimeAdaptor(config.getDateTimeFormat()))
                    .registerTypeAdapter(ZonedDateTime.class, new GsonDateTimeAdaptor(config.getDateTimeFormat()))
                    .registerTypeAdapter(LocalDate.class, new GsonDateTimeAdaptor(config.getDateTimeFormat()))
                    .registerTypeAdapter(LocalTime.class, new GsonDateTimeAdaptor(config.getDateTimeFormat()))
                    .registerTypeAdapter(Date.class, new GsonDateTimeAdaptor(config.getDateTimeFormat()))
                    .registerTypeAdapter(java.sql.Date.class, new GsonDateTimeAdaptor(config.getDateTimeFormat()))
                    .registerTypeAdapter(Timestamp.class, new GsonDateTimeAdaptor(config.getDateTimeFormat()))
                    .registerTypeAdapter(Time.class, new GsonDateTimeAdaptor(config.getDateTimeFormat()))
            ;
        }
        return gsonBuilder.create();
    }

    @Bean(CUSTOM_HTTP_MESSAGE_CONVERTERS_BEAN_NAME)
    public HttpMessageConverters customConverter(Gson gson) {
        Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        CustomGsonHttpMessageConverter gsonHttpMessageConverter = new CustomGsonHttpMessageConverter(gson);
        messageConverters.add(gsonHttpMessageConverter);
        return new HttpMessageConverters(true, messageConverters);
    }
    private static final class DoubleJsonSerializer implements JsonSerializer<Double> {

        @Override
        public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == src.longValue()) {
                return new JsonPrimitive(src.longValue());
            }
            return new JsonPrimitive(src);
        }
    }
}

