/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.archive.common;

public enum ArchiveTaskStatusEnum {
    TASK_START(0),
    TASK_END(1);

    private int status;

    private ArchiveTaskStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }
}

