/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value.convert;

import com.jiuqi.va.biz.intf.value.Converter;
import java.math.BigDecimal;

public class BigDecimalConvert {
    public static final Converter toBoolean = new Converter(){

        @Override
        public Object cast(Object data) {
            return ((Number)data).intValue() == 1;
        }

        @Override
        public Class<?> getTargetType() {
            return Boolean.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return BigDecimal.class;
        }
    };
    public static final Converter fromBoolean = new Converter(){

        @Override
        public Object cast(Object data) {
            return BigDecimal.valueOf((Boolean)data != false ? 1L : 0L);
        }

        @Override
        public Class<?> getTargetType() {
            return BigDecimal.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Boolean.TYPE;
        }
    };
    public static final Converter toByte = new Converter(){

        @Override
        public Object cast(Object data) {
            return ((Number)data).byteValue();
        }

        @Override
        public Class<?> getTargetType() {
            return Byte.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return BigDecimal.class;
        }
    };
    public static final Converter fromByte = new Converter(){

        @Override
        public Object cast(Object data) {
            return BigDecimal.valueOf(((Byte)data).byteValue());
        }

        @Override
        public Class<?> getTargetType() {
            return BigDecimal.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Byte.TYPE;
        }
    };
    public static final Converter toShort = new Converter(){

        @Override
        public Object cast(Object data) {
            return ((Number)data).shortValue();
        }

        @Override
        public Class<?> getTargetType() {
            return Short.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return BigDecimal.class;
        }
    };
    public static final Converter fromShort = new Converter(){

        @Override
        public Object cast(Object data) {
            return BigDecimal.valueOf(((Short)data).shortValue());
        }

        @Override
        public Class<?> getTargetType() {
            return BigDecimal.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Short.TYPE;
        }
    };
    public static final Converter toInt = new Converter(){

        @Override
        public Object cast(Object data) {
            return ((Number)data).intValue();
        }

        @Override
        public Class<?> getTargetType() {
            return Integer.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return BigDecimal.class;
        }
    };
    public static final Converter fromInt = new Converter(){

        @Override
        public Object cast(Object data) {
            return BigDecimal.valueOf(((Integer)data).intValue());
        }

        @Override
        public Class<?> getTargetType() {
            return BigDecimal.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Integer.TYPE;
        }
    };
    public static final Converter toLong = new Converter(){

        @Override
        public Object cast(Object data) {
            return ((Number)data).longValue();
        }

        @Override
        public Class<?> getTargetType() {
            return Long.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return BigDecimal.class;
        }
    };
    public static final Converter fromLong = new Converter(){

        @Override
        public Object cast(Object data) {
            return BigDecimal.valueOf((Long)data);
        }

        @Override
        public Class<?> getTargetType() {
            return BigDecimal.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Long.TYPE;
        }
    };
    public static final Converter toFloat = new Converter(){

        @Override
        public Object cast(Object data) {
            return Float.valueOf(((Number)data).floatValue());
        }

        @Override
        public Class<?> getTargetType() {
            return Float.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return BigDecimal.class;
        }
    };
    public static final Converter fromFloat = new Converter(){

        @Override
        public Object cast(Object data) {
            return BigDecimal.valueOf(((Float)data).floatValue());
        }

        @Override
        public Class<?> getTargetType() {
            return BigDecimal.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Float.TYPE;
        }
    };
    public static final Converter toDouble = new Converter(){

        @Override
        public Object cast(Object data) {
            return ((Number)data).doubleValue();
        }

        @Override
        public Class<?> getTargetType() {
            return Double.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return BigDecimal.class;
        }
    };
    public static final Converter fromDouble = new Converter(){

        @Override
        public Object cast(Object data) {
            return BigDecimal.valueOf((Double)data);
        }

        @Override
        public Class<?> getTargetType() {
            return BigDecimal.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Double.TYPE;
        }
    };
    public static final Converter toString2 = new Converter(){

        @Override
        public Object cast(Object data) {
            return data.toString();
        }

        @Override
        public Class<?> getTargetType() {
            return String.class;
        }

        @Override
        public Class<?> getSourceType() {
            return BigDecimal.class;
        }
    };
    public static final Converter fromString = new Converter(){

        @Override
        public Object cast(Object data) {
            return new BigDecimal((String)data);
        }

        @Override
        public Class<?> getTargetType() {
            return BigDecimal.class;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
}

