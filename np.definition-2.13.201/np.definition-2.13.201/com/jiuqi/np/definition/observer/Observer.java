/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.observer;

public interface Observer {
    public boolean isAsyn();

    public void excute(Object[] var1) throws Exception;

    default public String getName() {
        return this.getClass().getName();
    }
}

