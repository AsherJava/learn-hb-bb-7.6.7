/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value.convert;

import com.jiuqi.va.biz.intf.value.Converter;
import java.io.UnsupportedEncodingException;

public final class StringConvert {
    public static final Converter toBoolean = new Converter(){

        @Override
        public Object cast(Object data) {
            return Boolean.parseBoolean((String)data);
        }

        @Override
        public Class<?> getTargetType() {
            return Boolean.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
    public static final Converter toByte = new Converter(){

        @Override
        public Object cast(Object data) {
            return Byte.parseByte((String)data);
        }

        @Override
        public Class<?> getTargetType() {
            return Byte.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
    public static final Converter toShort = new Converter(){

        @Override
        public Object cast(Object data) {
            return Short.parseShort((String)data);
        }

        @Override
        public Class<?> getTargetType() {
            return Short.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
    public static final Converter toInt = new Converter(){

        @Override
        public Object cast(Object data) {
            return Integer.parseInt((String)data);
        }

        @Override
        public Class<?> getTargetType() {
            return Integer.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
    public static final Converter toLong = new Converter(){

        @Override
        public Object cast(Object data) {
            return Long.parseLong((String)data);
        }

        @Override
        public Class<?> getTargetType() {
            return Long.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
    public static final Converter toFloat = new Converter(){

        @Override
        public Object cast(Object data) {
            return Float.valueOf(Float.parseFloat((String)data));
        }

        @Override
        public Class<?> getTargetType() {
            return Float.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
    public static final Converter toDouble = new Converter(){

        @Override
        public Object cast(Object data) {
            return Double.parseDouble((String)data);
        }

        @Override
        public Class<?> getTargetType() {
            return Double.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
    public static final Converter toChar = new Converter(){

        @Override
        public Object cast(Object data) {
            return Character.valueOf(((String)data).charAt(0));
        }

        @Override
        public Class<?> getTargetType() {
            return Character.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
    public static final Converter toByteArray = new Converter(){

        @Override
        public Object cast(Object data) {
            try {
                return ((String)data).getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Class<?> getTargetType() {
            return byte[].class;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
}

