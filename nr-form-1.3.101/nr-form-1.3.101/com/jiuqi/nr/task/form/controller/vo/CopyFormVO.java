/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.controller.vo;

public class CopyFormVO {
    private String sourceKey;
    private String newTitle;
    private String newCode;
    private String groupKey;
    private boolean copyStyleOnly;

    public String getSourceKey() {
        return this.sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public String getNewTitle() {
        return this.newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewCode() {
        return this.newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public boolean isCopyStyleOnly() {
        return this.copyStyleOnly;
    }

    public void setCopyStyleOnly(boolean copyStyleOnly) {
        this.copyStyleOnly = copyStyleOnly;
    }
}

