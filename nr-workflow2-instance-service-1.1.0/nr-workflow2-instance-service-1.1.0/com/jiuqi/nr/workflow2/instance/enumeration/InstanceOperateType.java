/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.instance.enumeration;

public enum InstanceOperateType {
    START("\u542f\u52a8"),
    CLEAR("\u6e05\u9664"),
    STOP("\u505c\u7528");

    public final String title;

    private InstanceOperateType(String title) {
        this.title = title;
    }
}

