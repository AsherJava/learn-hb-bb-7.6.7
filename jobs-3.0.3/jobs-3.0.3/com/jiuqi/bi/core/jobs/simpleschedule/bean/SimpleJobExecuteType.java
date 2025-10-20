/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.simpleschedule.bean;

public enum SimpleJobExecuteType {
    API("API"),
    ANNOTATION("ANNOTATION");

    private String name;

    private SimpleJobExecuteType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

