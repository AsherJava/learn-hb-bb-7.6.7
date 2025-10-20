/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

public abstract class ParamSerializer<T> {
    public abstract T unSerializeParam(String var1, Class<T> var2);

    public abstract String serializeParam(T var1);
}

