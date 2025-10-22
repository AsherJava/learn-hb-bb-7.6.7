/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.text.StringHelper
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.StringSerializer;
import com.jiuqi.np.period.text.StringHelper;

public final class IntegerValueFormat
implements StringSerializer {
    private static final int MAXINTLENGTH = 10;

    @Override
    public boolean serialize(StringBuilder dest, Object value) {
        if (value instanceof Integer) {
            dest.append((Integer)value);
            return true;
        }
        return false;
    }

    @Override
    public Object deserialize(String value) {
        if (value.length() <= 10 && StringHelper.isNumberString((String)value)) {
            return Integer.valueOf(value);
        }
        return null;
    }
}

