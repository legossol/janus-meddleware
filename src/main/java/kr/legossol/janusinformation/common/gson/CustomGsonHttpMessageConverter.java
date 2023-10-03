package kr.legossol.janusinformation.common.gson;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
public final class CustomGsonHttpMessageConverter extends GsonHttpMessageConverter {

    CustomGsonHttpMessageConverter(Gson gson) {
        super(gson);
    }

    @Override
    protected Object readInternal(Type resolvedType, Reader reader) {

        JsonElement element = getGson().fromJson(reader, JsonElement.class);
        if (element instanceof JsonArray) {
            // JsonArray 처리
            Type type = new TypeToken<List<Map<String, Object>>>() {
            }.getType();
            List<Map<String, Object>> dataList = getGson().fromJson(element, type);
            for (Map<String, Object> data : dataList) {
                removeEmptyValue(data);
            }
            return getGson().fromJson(getGson().toJson(dataList), resolvedType);
        } else {
            // JsonObject 처리
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> data = getGson().fromJson(element, type);
            removeEmptyValue(data);
            return getGson().fromJson(getGson().toJson(data), resolvedType);
        }
    }
    private void removeEmptyValue(Map<String, Object> data) {
        data.entrySet().removeIf(
                entry -> entry.getValue() == null || (entry.getValue().getClass().equals(ArrayList.class)
                        && ((ArrayList<?>) entry.getValue()).isEmpty()));

    }
}
