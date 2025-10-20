/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value.convert;

import com.jiuqi.va.biz.intf.value.Converter;
import com.jiuqi.va.biz.utils.Utils;

public class DoubleConvert {
    public static final Converter toBoolean = new Converter(){

        @Override
        public Object cast(Object data) {
            return ((Number)data).doubleValue() == 1.0;
        }

        @Override
        public Class<?> getTargetType() {
            return Boolean.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return Double.TYPE;
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
            return Double.TYPE;
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
            return Double.TYPE;
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
            return Double.TYPE;
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
            return Double.TYPE;
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
            return Double.TYPE;
        }
    };
    public static final Converter toString2 = new Converter(){

        @Override
        public Object cast(Object data) {
            return Utils.toString((Double)data);
        }

        @Override
        public Class<?> getTargetType() {
            return String.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Double.TYPE;
        }
    };
}

