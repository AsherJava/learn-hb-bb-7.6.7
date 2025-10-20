/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.shiro.config;

public interface MyShiroAfterFilter {
    public boolean execute();

    default public void executeForFailure() {
    }

    default public int getOrderNum() {
        return 99;
    }
}

