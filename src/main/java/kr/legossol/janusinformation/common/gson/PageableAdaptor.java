package kr.legossol.janusinformation.common.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class PageableAdaptor implements JsonSerializer<Object>, JsonDeserializer<Object> {
    private static final String FIELD_TOTAL = "total";
    private static final String FIELD_CONTENTS = "content";
    private static final String FIELD_PAGEABLE = "pageable";

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if(typeOfT instanceof ParameterizedType) {
            ParameterizedType typeClass = (ParameterizedType) typeOfT;
            Type rawType = typeClass.getRawType();
            if(Page.class.equals(rawType)) {
                JsonObject pageJsonObj = json.getAsJsonObject();

                long total = pageJsonObj.get(FIELD_TOTAL).getAsLong();
                JsonElement contentJson = pageJsonObj.get(FIELD_CONTENTS);
                List<Object> contents = Collections.emptyList();
                if (!contentJson.isJsonNull() && contentJson.isJsonArray()) {
                    contents = context.deserialize(contentJson,
                            new DynamicGenericType(List.class, typeClass.getActualTypeArguments()));
                }
                Pageable pageable = context.deserialize(pageJsonObj.get(FIELD_PAGEABLE), PageRequest.class);
                if (pageable == null) {
                    pageable = PageRequest.of(0, 0);
                }
                return new PageImpl<>(contents, pageable, total);
            }
        } else if(Pageable.class.equals(typeOfT)) {
            return context.deserialize(json, PageRequest.class);
        }
        throw new JsonParseException("Un-support pageable type to deserialize. type = " + typeOfT.getTypeName());

        }
    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }
    @Getter
    @RequiredArgsConstructor
    private static class DynamicGenericType implements ParameterizedType {
        private final Type rawType;
        private final Type[] actualTypeArguments;
        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
