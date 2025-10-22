/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.common;

public class AddTagParam {
    private String batchId;
    private String period;
    private boolean diffTable;
    private boolean zeroTable;
    private String taskKey;
    private String entityId;
    private String title;
    private String category;
    private String description;

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public boolean isDiffTable() {
        return this.diffTable;
    }

    public void setDiffTable(boolean diffTable) {
        this.diffTable = diffTable;
    }

    public boolean isZeroTable() {
        return this.zeroTable;
    }

    public void setZeroTable(boolean zeroTable) {
        this.zeroTable = zeroTable;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

