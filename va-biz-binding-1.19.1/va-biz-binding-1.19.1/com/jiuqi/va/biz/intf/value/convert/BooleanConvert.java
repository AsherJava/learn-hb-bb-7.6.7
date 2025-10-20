/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value.convert;

import com.jiuqi.va.biz.intf.value.Converter;

public class BooleanConvert {
    public static final Converter toByte = new Converter(){

        @Override
        public Object cast(Object data) {
            return (byte)((Boolean)data != false ? 1 : 0);
        }

        @Override
        public Class<?> getTargetType() {
            return Byte.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return Boolean.TYPE;
        }
    };
    public static final Converter toShort = new Converter(){

        @Override
        public Object cast(Object data) {
            return (short)((Boolean)data != false ? 1 : 0);
        }

        @Override
        public Class<?> getTargetType() {
            return Short.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return Boolean.TYPE;
        }
    };
    public static final Converter toInt = new Converter(){

        @Override
        public Object cast(Object data) {
            return (Boolean)data != false ? 1 : 0;
        }

        @Override
        public Class<?> getTargetType() {
            return Integer.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return Boolean.TYPE;
        }
    };
    public static final Converter toLong = new Converter(){

        @Override
        public Object cast(Object data) {
            return (long)((Boolean)data != false ? 1 : 0);
        }

        @Override
        public Class<?> getTargetType() {
            return Long.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return Boolean.TYPE;
        }
    };
    public static final Converter toFloat = new Converter(){

        @Override
        public Object cast(Object data) {
            return Float.valueOf((Boolean)data != false ? 1 : 0);
        }

        @Override
        public Class<?> getTargetType() {
            return Float.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return Boolean.TYPE;
        }
    };
    public static final Converter toDouble = new Converter(){

        @Override
        public Object cast(Object data) {
            return (double)((Boolean)data != false ? 1 : 0);
        }

        @Override
        public Class<?> getTargetType() {
            return Double.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return Boolean.TYPE;
        }
    };
    public static final Converter toChar = new Converter(){

        @Override
        public Object cast(Object data) {
            return Character.valueOf((Boolean)data != false ? (char)'F' : 'T');
        }

        @Override
        public Class<?> getTargetType() {
            return Character.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return Boolean.TYPE;
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
            return Boolean.TYPE;
        }
    };
}

