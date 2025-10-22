/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.nodecheck;

import java.math.BigDecimal;
import java.util.List;

public class NodeCheckInfo {
    private String selectMode;
    private boolean recursive;
    private BigDecimal errorRange;
    private List<String> formKeys;

    public boolean isRecursive() {
        return this.recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public BigDecimal getErrorRange() {
        return this.errorRange;
    }

    public void setErrorRange(BigDecimal errorRange) {
        this.errorRange = errorRange;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public String getSelectMode() {
        return this.selectMode;
    }

    public void setSelectMode(String selectMode) {
        this.selectMode = selectMode;
    }
}

