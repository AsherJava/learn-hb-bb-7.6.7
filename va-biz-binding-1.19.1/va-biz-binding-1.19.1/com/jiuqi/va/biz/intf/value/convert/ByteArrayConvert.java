/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value.convert;

import com.jiuqi.va.biz.intf.value.Converter;
import java.io.UnsupportedEncodingException;

public class ByteArrayConvert {
    public static final Converter toString2 = new Converter(){

        @Override
        public Object cast(Object data) {
            try {
                return new String((byte[])data, "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Class<?> getTargetType() {
            return String.class;
        }

        @Override
        public Class<?> getSourceType() {
            return byte[].class;
        }
    };
}

