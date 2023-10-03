package kr.legossol.janusinformation.common.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GsonExclusionStrategy  implements ExclusionStrategy {
    private final GsonExclude.When when;
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        GsonExclude annotation = f.getAnnotation(GsonExclude.class);
        if(annotation != null) {
            return Arrays.asList(annotation.when()).contains(when);
        }
        return false;
    }
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

}
