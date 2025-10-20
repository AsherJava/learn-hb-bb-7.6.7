/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value;

import java.lang.reflect.Type;

public interface ValueGetter {
    public boolean isNull(String var1);

    public boolean getBoolean(String var1);

    public byte getByte(String var1);

    public short getShort(String var1);

    public int getInt(String var1);

    public long getLong(String var1);

    public float getFloat(String var1);

    public double getDouble(String var1);

    public char getChar(String var1);

    public String getString(String var1);

    public <T> T getValue(String var1, Class<T> var2);

    public Object getValue(String var1, Type var2);

    public Object getValue(String var1);

    public ValueGetter getObject(String var1);

    public Object getArray(Class<?> var1, String var2);

    public ValueGetter[] getArray(String var1);

    public <T> T convert(Class<T> var1);
}

