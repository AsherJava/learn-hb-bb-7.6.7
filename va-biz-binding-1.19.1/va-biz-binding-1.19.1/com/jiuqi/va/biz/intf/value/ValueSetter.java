/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value;

import java.lang.reflect.Type;

public interface ValueSetter {
    public void setNull(String var1);

    public void setBoolean(String var1, boolean var2);

    public void setByte(String var1, byte var2);

    public void setShort(String var1, short var2);

    public void setInt(String var1, int var2);

    public void setLong(String var1, long var2);

    public void setFloat(String var1, float var2);

    public void setDouble(String var1, double var2);

    public void setChar(String var1, char var2);

    public void setValue(String var1, Object var2);

    public void setValue(String var1, Object var2, Type var3);

    public ValueSetter setObject(String var1);

    public void setArray(String var1, Object var2);

    public ValueSetter[] setArray(String var1, int var2);
}

