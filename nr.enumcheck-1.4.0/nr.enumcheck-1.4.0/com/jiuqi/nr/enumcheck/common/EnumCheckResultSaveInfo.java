/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

import java.io.Serializable;

public class EnumCheckResultSaveInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean saveStatus;
    private int errorUnitCount;
    private int errorEnumCount;
    private int errorFormCount;

    public boolean isSaveStatus() {
        return this.saveStatus;
    }

    public void setSaveStatus(boolean saveStatus) {
        this.saveStatus = saveStatus;
    }

    public int getErrorUnitCount() {
        return this.errorUnitCount;
    }

    public void setErrorUnitCount(int errorUnitCount) {
        this.errorUnitCount = errorUnitCount;
    }

    public int getErrorEnumCount() {
        return this.errorEnumCount;
    }

    public void setErrorEnumCount(int errorEnumCount) {
        this.errorEnumCount = errorEnumCount;
    }

    public int getErrorFormCount() {
        return this.errorFormCount;
    }

    public void setErrorFormCount(int errorFormCount) {
        this.errorFormCount = errorFormCount;
    }
}

