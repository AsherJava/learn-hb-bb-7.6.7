/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value.convert;

import com.jiuqi.va.biz.intf.value.Converter;

public class ObjectConvert {
    public static final Converter toString2 = new Converter(){

        @Override
        public Object cast(Object data) {
            return (String)data;
        }

        @Override
        public Class<?> getTargetType() {
            return String.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Object.class;
        }
    };
    public static final Converter fromString = new Converter(){

        @Override
        public Object cast(Object data) {
            return data;
        }

        @Override
        public Class<?> getTargetType() {
            return Object.class;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
}

