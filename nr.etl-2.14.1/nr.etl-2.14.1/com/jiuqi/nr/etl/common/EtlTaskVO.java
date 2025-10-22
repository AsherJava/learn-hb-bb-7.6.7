/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.etl.common;

public class EtlTaskVO {
    private String etlTaskId;
    private String etlTaskTitle;

    public EtlTaskVO() {
    }

    public EtlTaskVO(String etlTaskId, String etlTaskTitle) {
        this.etlTaskId = etlTaskId;
        this.etlTaskTitle = etlTaskTitle;
    }

    public String getEtlTaskId() {
        return this.etlTaskId;
    }

    public void setEtlTaskId(String etlTaskId) {
        this.etlTaskId = etlTaskId;
    }

    public String getEtlTaskTitle() {
        return this.etlTaskTitle;
    }

    public void setEtlTaskTitle(String etlTaskTitle) {
        this.etlTaskTitle = etlTaskTitle;
    }
}

