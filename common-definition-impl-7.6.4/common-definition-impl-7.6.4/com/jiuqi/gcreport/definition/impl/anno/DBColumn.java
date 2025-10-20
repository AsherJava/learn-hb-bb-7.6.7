/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.FIELD})
public @interface DBColumn {
    public String title() default "";

    public String refTabField() default "";

    public String description() default "\u6b64\u8bb0\u5f55\u662f\u901a\u8fc7\u5b9e\u4f53\u81ea\u52a8\u8f6c\u5316\u751f\u6210\uff0c\u7981\u6b62\u4fee\u6539\uff01\uff01";

    public String nameInDB() default "";

    public boolean isRequired() default false;

    public int pkOrdinal() default -1;

    public boolean isRecid() default false;

    public boolean isRecver() default false;

    public int fieldValueType() default 0;

    public DBType dbType();

    public int length() default 36;

    public int precision() default 19;

    public int scale() default 4;

    public String defaultValue() default "";

    public int order() default 999;

    public static enum DBType {
        Int(3, 4, 5),
        Long(8, -5, 10),
        Double(1, 8, 3),
        Boolean(4, -7, 1),
        Numeric(8, 3, 10),
        Varchar(2, 12, 6),
        NVarchar(2, -9, 6),
        Text(16, 2005, 12),
        NText(16, 2011, 12),
        Date(5, 91, 2),
        DateTime(6, 93, 2),
        Blob(18, 2004, 9),
        UUID(7, 1, 33);

        private static Map<Integer, DBType> type2DBTypeCache;
        private static Map<Integer, DBType> dbType2DBTypeCache;
        private static Map<Integer, DBType> biType2DBTypeCache;
        private int type;
        private int dbType;
        private int biType;

        private static Map<Integer, DBType> cachedType2Value() {
            if (type2DBTypeCache.isEmpty()) {
                for (DBType dbType : DBType.values()) {
                    type2DBTypeCache.put(dbType.type, dbType);
                }
            }
            return type2DBTypeCache;
        }

        private static Map<Integer, DBType> cachedDbType2Value() {
            if (dbType2DBTypeCache.isEmpty()) {
                for (DBType dbType : DBType.values()) {
                    dbType2DBTypeCache.put(dbType.dbType, dbType);
                }
            }
            return dbType2DBTypeCache;
        }

        private static Map<Integer, DBType> cachedBiType2Value() {
            if (biType2DBTypeCache.isEmpty()) {
                for (DBType dbType : DBType.values()) {
                    biType2DBTypeCache.put(dbType.biType, dbType);
                }
            }
            return biType2DBTypeCache;
        }

        private DBType(int type, int dbType, int biType) {
            this.type = type;
            this.dbType = dbType;
            this.biType = biType;
        }

        public int getType() {
            return this.type;
        }

        public int getDbType() {
            return this.dbType;
        }

        public int getBiType() {
            return this.biType;
        }

        public static DBType forType(int type) {
            DBType dbType = DBType.cachedType2Value().get(type);
            return null == dbType ? Varchar : dbType;
        }

        public static DBType forDbType(int type) {
            DBType dbType = DBType.cachedDbType2Value().get(type);
            return null == dbType ? Varchar : dbType;
        }

        public static DBType forBIType(int type) {
            DBType dbType = DBType.cachedBiType2Value().get(type);
            return null == dbType ? Varchar : dbType;
        }

        static {
            type2DBTypeCache = new HashMap<Integer, DBType>();
            dbType2DBTypeCache = new HashMap<Integer, DBType>();
            biType2DBTypeCache = new HashMap<Integer, DBType>();
        }
    }
}

