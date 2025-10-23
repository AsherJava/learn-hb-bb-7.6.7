/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

public class EntityQueryParam {
    private String entityId;
    private String entityKeyData;
    private int level;
    private boolean all;
    private boolean cb;
    private String rowFilter;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityKeyData() {
        return this.entityKeyData;
    }

    public void setEntityKeyData(String entityKeyData) {
        this.entityKeyData = entityKeyData;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isAll() {
        return this.all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public boolean isCb() {
        return this.cb;
    }

    public void setCb(boolean cb) {
        this.cb = cb;
    }

    public String getRowFilter() {
        return this.rowFilter;
    }

    public void setRowFilter(String rowFilter) {
        this.rowFilter = rowFilter;
    }
}

