/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value.convert;

import com.jiuqi.va.biz.intf.value.Converter;

public class LongConvert {
    public static final Converter toBoolean = new Converter(){

        @Override
        public Object cast(Object data) {
            return ((Number)data).longValue() == 1L;
        }

        @Override
        public Class<?> getTargetType() {
            return Boolean.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return Long.TYPE;
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
            return Long.TYPE;
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
            return Long.TYPE;
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
            return Long.TYPE;
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
            return Long.TYPE;
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
            return Long.TYPE;
        }
    };
}

