/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action;

public interface ActionReturnObject {
    public String getType();

    default public boolean isSuccess() {
        return true;
    }
}

