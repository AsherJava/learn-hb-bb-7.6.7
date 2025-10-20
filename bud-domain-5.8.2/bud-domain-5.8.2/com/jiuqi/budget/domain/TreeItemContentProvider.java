/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

import java.util.Map;

public interface TreeItemContentProvider<T> {
    public String getId(T var1);

    public String getText(T var1);

    public Map<String, Object> getAttributes(T var1);
}

