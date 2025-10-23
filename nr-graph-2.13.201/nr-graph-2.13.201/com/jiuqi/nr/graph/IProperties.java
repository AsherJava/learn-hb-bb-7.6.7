/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph;

import java.util.Set;

public interface IProperties {
    public Set<String> getPropertyKeys();

    public Object getProperty(String var1);

    public <T> T getProperty(String var1, Class<T> var2);
}

