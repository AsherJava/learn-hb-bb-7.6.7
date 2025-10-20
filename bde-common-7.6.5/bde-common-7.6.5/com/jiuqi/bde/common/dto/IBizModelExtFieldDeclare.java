/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto;

public interface IBizModelExtFieldDeclare<T> {
    public String getCode();

    public String getName();

    public int getShowOrder();

    public int getOrder();

    public String generateId(String var1);

    public T parseToObject(String var1);
}

