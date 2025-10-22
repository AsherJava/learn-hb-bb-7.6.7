/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

public class FormFoldingSpecialRegion {
    private Integer startIdx;
    private Integer endIdx;

    public FormFoldingSpecialRegion() {
    }

    public FormFoldingSpecialRegion(Integer startIdx, Integer endIdx) {
        this.startIdx = startIdx;
        this.endIdx = endIdx;
    }

    public Integer getStartIdx() {
        return this.startIdx;
    }

    public void setStartIdx(Integer startIdx) {
        this.startIdx = startIdx;
    }

    public Integer getEndIdx() {
        return this.endIdx;
    }

    public void setEndIdx(Integer endIdx) {
        this.endIdx = endIdx;
    }

    public String toString() {
        return this.startIdx + "," + this.endIdx;
    }
}

