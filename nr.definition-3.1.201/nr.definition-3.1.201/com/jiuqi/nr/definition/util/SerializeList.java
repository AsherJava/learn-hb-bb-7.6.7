/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.util;

import java.util.List;

public interface SerializeList<T> {
    public byte[] serialize(List<T> var1);

    public List<T> deserialize(byte[] var1, Class<T> var2);
}

