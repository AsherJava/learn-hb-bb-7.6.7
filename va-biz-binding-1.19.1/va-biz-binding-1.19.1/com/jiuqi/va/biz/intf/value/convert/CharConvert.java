/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value.convert;

import com.jiuqi.va.biz.intf.value.Converter;

public class CharConvert {
    public static final Converter toBoolean = new Converter(){

        @Override
        public Object cast(Object data) {
            return ((Character)data).charValue() == 'T';
        }

        @Override
        public Class<?> getTargetType() {
            return Boolean.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return Character.TYPE;
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
            return Character.TYPE;
        }
    };
}

