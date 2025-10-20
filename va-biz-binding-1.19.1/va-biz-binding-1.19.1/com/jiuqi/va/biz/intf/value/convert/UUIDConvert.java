/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value.convert;

import com.jiuqi.va.biz.intf.value.Converter;
import com.jiuqi.va.biz.utils.Utils;
import java.util.UUID;

public class UUIDConvert {
    public static final Converter toString2 = new Converter(){

        @Override
        public Object cast(Object data) {
            return ((UUID)data).toString();
        }

        @Override
        public Class<?> getTargetType() {
            return String.class;
        }

        @Override
        public Class<?> getSourceType() {
            return UUID.class;
        }
    };
    public static final Converter fromString = new Converter(){

        @Override
        public Object cast(Object data) {
            return UUID.fromString((String)data);
        }

        @Override
        public Class<?> getTargetType() {
            return UUID.class;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
    public static final Converter toByteArray = new Converter(){

        @Override
        public Object cast(Object data) {
            return Utils.toBytes((UUID)data);
        }

        @Override
        public Class<?> getTargetType() {
            return byte[].class;
        }

        @Override
        public Class<?> getSourceType() {
            return UUID.class;
        }
    };
    public static final Converter fromByteArray = new Converter(){

        @Override
        public Object cast(Object data) {
            return Utils.toUUID((byte[])data);
        }

        @Override
        public Class<?> getTargetType() {
            return UUID.class;
        }

        @Override
        public Class<?> getSourceType() {
            return byte[].class;
        }
    };
}

