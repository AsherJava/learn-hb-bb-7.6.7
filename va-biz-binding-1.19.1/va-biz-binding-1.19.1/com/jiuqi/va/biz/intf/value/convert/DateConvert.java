/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value.convert;

import com.jiuqi.va.biz.intf.value.Converter;
import com.jiuqi.va.biz.utils.Utils;
import java.util.Date;

public final class DateConvert {
    public static final Converter toString2 = new Converter(){

        @Override
        public Object cast(Object data) {
            return Utils.formatDateTime((Date)data);
        }

        @Override
        public Class<?> getTargetType() {
            return String.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Date.class;
        }
    };
    public static final Converter fromString = new Converter(){

        @Override
        public Object cast(Object data) {
            return Utils.parseDateTime((String)data);
        }

        @Override
        public Class<?> getTargetType() {
            return Date.class;
        }

        @Override
        public Class<?> getSourceType() {
            return String.class;
        }
    };
    public static final Converter toLong = new Converter(){

        @Override
        public Object cast(Object data) {
            return ((Date)data).getTime();
        }

        @Override
        public Class<?> getTargetType() {
            return Long.TYPE;
        }

        @Override
        public Class<?> getSourceType() {
            return Date.class;
        }
    };
    public static final Converter fromLong = new Converter(){

        @Override
        public Object cast(Object data) {
            return new Date((Long)data);
        }

        @Override
        public Class<?> getTargetType() {
            return Date.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Long.TYPE;
        }
    };
    public static final Converter fromInteger = new Converter(){

        @Override
        public Object cast(Object data) {
            return new Date(((Integer)data).intValue());
        }

        @Override
        public Class<?> getTargetType() {
            return Date.class;
        }

        @Override
        public Class<?> getSourceType() {
            return Integer.TYPE;
        }
    };
}

