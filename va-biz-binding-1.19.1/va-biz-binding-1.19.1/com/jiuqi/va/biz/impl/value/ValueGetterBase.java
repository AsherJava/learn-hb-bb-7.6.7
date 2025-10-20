/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.value;

import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.ValueGetter;
import java.lang.reflect.Type;

public abstract class ValueGetterBase
implements ValueGetter {
    @Override
    public boolean isNull(String name) {
        return this.getValue(name) == null;
    }

    @Override
    public boolean getBoolean(String name) {
        return Convert.cast(this.getValue(name), Boolean.TYPE);
    }

    @Override
    public byte getByte(String name) {
        return Convert.cast(this.getValue(name), Byte.TYPE);
    }

    @Override
    public short getShort(String name) {
        return Convert.cast(this.getValue(name), Short.TYPE);
    }

    @Override
    public int getInt(String name) {
        return Convert.cast(this.getValue(name), Integer.TYPE);
    }

    @Override
    public long getLong(String name) {
        return Convert.cast(this.getValue(name), Long.TYPE);
    }

    @Override
    public float getFloat(String name) {
        return Convert.cast(this.getValue(name), Float.TYPE).floatValue();
    }

    @Override
    public double getDouble(String name) {
        return Convert.cast(this.getValue(name), Double.TYPE);
    }

    @Override
    public char getChar(String name) {
        return Convert.cast(this.getValue(name), Character.TYPE).charValue();
    }

    @Override
    public String getString(String name) {
        return Convert.cast(this.getValue(name), String.class);
    }

    @Override
    public <T> T getValue(String name, Class<T> valueClass) {
        return Convert.cast(this.getValue(name), valueClass);
    }

    @Override
    public Object getValue(String name, Type type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValueGetter getObject(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getArray(Class<?> componentClass, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValueGetter[] getArray(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T convert(Class<T> valueClass) {
        throw new UnsupportedOperationException();
    }
}

