/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value.convert;

import com.jiuqi.va.biz.intf.value.Converter;
import com.jiuqi.va.biz.intf.value.EnumValue;

public class EnumConvert {
    public static final Converter toString2 = new Converter(){

        @Override
        public Object cast(Object data) {
            return "" + (data instanceof EnumValue ? ((EnumValue)data).getValue() : ((Enum)data).ordinal());
        }

        @Override
        public Class<?> getTargetType() {
            return String.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Enum.class;
        }
    };
    public static final Converter fromString = new Converter(){

        @Override
        public Object cast(Object data) {
            return Integer.parseInt(data.toString());
        }

        @Override
        public Class<?> getTargetType() {
            return Enum.class;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
    public static final Converter toInt = new Converter(){

        @Override
        public Object cast(Object data) {
            return data instanceof EnumValue ? ((EnumValue)data).getValue() : ((Enum)data).ordinal();
        }

        @Override
        public Class<?> getTargetType() {
            return Integer.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return Enum.class;
        }
    };
    public static final Converter fromInt = new Converter(){

        @Override
        public Object cast(Object data) {
            return Integer.parseInt(data.toString());
        }

        @Override
        public Class<?> getTargetType() {
            return Enum.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Integer.TYPE;
        }
    };
}

