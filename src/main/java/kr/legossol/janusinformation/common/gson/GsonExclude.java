package kr.legossol.janusinformation.common.gson;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GsonExclude {
    /**
     * Object -> Json 제외 여부 <br/>
     * {@link When}
     * @return true / false
     */
    When[] when() default {When.SERIALIZE, When.DESERIALIZE};

    enum When {
        /**
         * Object -> JSON String
         */
        SERIALIZE,
        /**
         * JSON String -> Object
         */
        DESERIALIZE
        ;
    }
}
