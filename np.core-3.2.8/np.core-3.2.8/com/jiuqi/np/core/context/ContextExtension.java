/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.context;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Consumer;

public interface ContextExtension
extends Serializable {
    public void put(String var1, Serializable var2);

    public void remove(String var1);

    public Object get(String var1);

    public void apply(Consumer<Map.Entry<String, Object>> var1);
}

