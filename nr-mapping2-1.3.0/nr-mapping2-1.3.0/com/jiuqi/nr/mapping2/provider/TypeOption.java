/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.provider;

public class TypeOption {
    private boolean modifyTask;
    private boolean modifyFormScheme;
    private boolean modifyOrgEntity;
    private boolean copyTask;
    private boolean copyFormScheme;
    private boolean copyOrgEntity;

    public TypeOption() {
    }

    public TypeOption(boolean modifyTask, boolean modifyFormScheme, boolean modifyOrgEntity, boolean copyTask, boolean copyFormScheme, boolean copyOrgEntity) {
        this.modifyTask = modifyTask;
        this.modifyFormScheme = modifyFormScheme;
        this.modifyOrgEntity = modifyOrgEntity;
        this.copyTask = copyTask;
        this.copyFormScheme = copyFormScheme;
        this.copyOrgEntity = copyOrgEntity;
    }

    public boolean isModifyTask() {
        return this.modifyTask;
    }

    public void setModifyTask(boolean modifyTask) {
        this.modifyTask = modifyTask;
    }

    public boolean isModifyFormScheme() {
        return this.modifyFormScheme;
    }

    public void setModifyFormScheme(boolean modifyFormScheme) {
        this.modifyFormScheme = modifyFormScheme;
    }

    public boolean isModifyOrgEntity() {
        return this.modifyOrgEntity;
    }

    public void setModifyOrgEntity(boolean modifyOrgEntity) {
        this.modifyOrgEntity = modifyOrgEntity;
    }

    public boolean isCopyTask() {
        return this.copyTask;
    }

    public void setCopyTask(boolean copyTask) {
        this.copyTask = copyTask;
    }

    public boolean isCopyFormScheme() {
        return this.copyFormScheme;
    }

    public void setCopyFormScheme(boolean copyFormScheme) {
        this.copyFormScheme = copyFormScheme;
    }

    public boolean isCopyOrgEntity() {
        return this.copyOrgEntity;
    }

    public void setCopyOrgEntity(boolean copyOrgEntity) {
        this.copyOrgEntity = copyOrgEntity;
    }
}

