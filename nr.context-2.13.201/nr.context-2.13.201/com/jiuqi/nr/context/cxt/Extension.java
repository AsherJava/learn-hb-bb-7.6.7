/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.context.cxt;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public interface Extension
extends Serializable {
    public void put(String var1, Serializable var2);

    public Map<String, Object> get();

    public Object get(String var1);

    public Set<String> getKeys();
}

