/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.StringSerializer;

public final class StringValueFormat
implements StringSerializer {
    private static final char QUOTATION = '\"';
    private static final int MINLENGTH = 2;

    @Override
    public boolean serialize(StringBuilder dest, Object value) {
        if (value instanceof String) {
            dest.append((String)value);
            return true;
        }
        return false;
    }

    @Override
    public Object deserialize(String value) {
        if (value.length() > 2 && value.charAt(0) == '\"' && value.charAt(value.length() - 1) == '\"') {
            return value.substring(1, value.length() - 1);
        }
        return null;
    }
}

