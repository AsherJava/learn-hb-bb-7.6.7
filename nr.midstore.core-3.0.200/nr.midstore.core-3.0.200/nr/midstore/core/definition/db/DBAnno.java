/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class DBAnno {

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface DBLinks {
        public DBLink[] value();
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    @Repeatable(value=DBLinks.class)
    public static @interface DBLink {
        public Class<?> linkWith();

        public String linkField();

        public String field();
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD})
    public static @interface DBField {
        public String dbField();

        public String get() default "";

        public String set() default "";

        public String tranWith() default "";

        public Class<?> appType() default Object.class;

        public Class<?> dbType() default Object.class;

        public boolean isPk() default false;

        public boolean isOrder() default false;

        public boolean notUpdate() default false;

        public boolean autoDate() default false;

        public boolean upper() default false;
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface DBTable {
        public String dbTable() default "";
    }
}

