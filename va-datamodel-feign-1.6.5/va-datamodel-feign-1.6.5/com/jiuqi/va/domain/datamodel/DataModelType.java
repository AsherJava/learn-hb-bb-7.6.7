/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.va.mapper.jdialect.DialectException
 */
package com.jiuqi.va.domain.datamodel;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.va.mapper.jdialect.DialectException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.util.StringUtils;

public class DataModelType {
    public static final int UUID = 1;
    public static final int NVARCHAR = 2;
    public static final int INTEGER = 3;
    public static final int NUMERIC = 4;
    public static final int DATE = 5;
    public static final int TIMESTAMP = 6;
    public static final int CLOB = 7;
    public static final int BOOLEAN = 8;
    public static final String UUID_STR = "UUID\u578b";
    public static final String NVARCHAR_STR = "\u5b57\u7b26\u578b";
    public static final String INTEGER_STR = "\u6574\u6570\u578b";
    public static final String NUMERIC_STR = "\u6570\u503c\u578b";
    public static final String DATE_STR = "\u65e5\u671f\u578b";
    public static final String TIMESTAMP_STR = "\u65f6\u95f4\u6233\u578b";
    public static final String CLOB_STR = "\u6587\u672c\u578b";
    public static final String BOOLEAN_STR = "\u5e03\u5c14\u578b";

    public static ColumnType modelTypeToColumnType(int dataModelType) {
        switch (dataModelType) {
            case 1: {
                return ColumnType.UUID;
            }
            case 2: {
                return ColumnType.NVARCHAR;
            }
            case 4: {
                return ColumnType.NUMERIC;
            }
            case 3: {
                return ColumnType.INTEGER;
            }
            case 5: {
                return ColumnType.DATE;
            }
            case 6: {
                return ColumnType.TIMESTAMP;
            }
            case 7: {
                return ColumnType.CLOB;
            }
            case 8: {
                return ColumnType.INTEGER;
            }
        }
        throw new DialectException("Not supported Types value:" + dataModelType);
    }

    public static int columnTypeToModelType(ColumnType columnType) {
        switch (columnType) {
            case UUID: {
                return 1;
            }
            case NVARCHAR: {
                return 2;
            }
            case NUMERIC: {
                return 4;
            }
            case INTEGER: {
                return 3;
            }
            case DATE: {
                return 5;
            }
            case TIMESTAMP: {
                return 6;
            }
            case CLOB: {
                return 7;
            }
        }
        throw new DialectException("Not supported Types value:" + (Object)((Object)columnType));
    }

    @Deprecated
    public static enum SubBizType {
        BILLMaster(1),
        BILLSUB(2),
        BILLSLAVE(4),
        MULTIPLESUB(9);

        private Integer index;

        private SubBizType(Integer index) {
            this.index = index;
        }

        public Integer getIndex() {
            return this.index;
        }
    }

    public static class BizTypeDeserializer
    extends JsonDeserializer<BizType> {
        public BizType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return BizType.valueOf(p.getValueAsString());
        }
    }

    public static class BizTypeSerializer
    extends JsonSerializer<BizType> {
        public void serialize(BizType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value == null) {
                gen.writeNull();
            } else {
                gen.writeString(value.toString());
            }
        }
    }

    @JsonSerialize(using=BizTypeSerializer.class)
    @JsonDeserialize(using=BizTypeDeserializer.class)
    public static class BizType {
        private static ConcurrentHashMap<String, BizType> cache = new ConcurrentHashMap();
        public static final BizType BASEDATA = BizType.valueOf("BASEDATA");
        public static final BizType BILL = BizType.valueOf("BILL");
        public static final BizType OTHER = BizType.valueOf("OTHER");
        private String name;

        private BizType(String name) {
            this.name = name;
        }

        public String name() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }

        public static BizType valueOf(String name) {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            if (cache.containsKey(name)) {
                return cache.get(name);
            }
            if (name.matches("^[A-Za-z0-9]{1,50}$")) {
                BizType biz2 = new BizType(name);
                cache.put(name, biz2);
                return biz2;
            }
            throw new RuntimeException("\u975e\u6cd5\u7684BizType\u5b57\u7b26");
        }

        public static BizType[] values() {
            return cache.values().toArray(new BizType[cache.size()]);
        }
    }

    public static enum ColumnAttr {
        SYSTEM,
        FIXED,
        EXTEND;

    }

    public static enum ColumnType {
        UUID,
        NVARCHAR,
        INTEGER,
        NUMERIC,
        DATE,
        TIMESTAMP,
        CLOB;

    }
}

