/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.log;

import com.jiuqi.nr.definition.log.CompareFieldType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ComparePropertyAnno {

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.METHOD})
    public static @interface CompareMethod {
        public String title();

        public CompareFieldType type() default CompareFieldType.OBJECT;

        public int order() default 0x7FFFFFFF;

        public boolean isDescription() default false;
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD})
    public static @interface CompareField {
        public String title();

        public CompareFieldType type() default CompareFieldType.OBJECT;

        public int order() default 0x7FFFFFFF;

        public boolean isDescription() default false;
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface CompareType {
    }
}

