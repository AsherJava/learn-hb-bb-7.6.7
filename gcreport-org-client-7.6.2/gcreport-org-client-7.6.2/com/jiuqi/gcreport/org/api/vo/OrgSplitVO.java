/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.vo;

import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;

public class OrgSplitVO {
    private OrgToJsonVO splitedUnit;
    private OrgToJsonVO splitUnit;
    private boolean splitDiffFlag;
    private Double scale;

    public OrgToJsonVO getSplitedUnit() {
        return this.splitedUnit;
    }

    public void setSplitedUnit(OrgToJsonVO splitedUnit) {
        this.splitedUnit = splitedUnit;
    }

    public OrgToJsonVO getSplitUnit() {
        return this.splitUnit;
    }

    public void setSplitUnit(OrgToJsonVO splitUnit) {
        this.splitUnit = splitUnit;
    }

    public boolean getSplitDiffFlag() {
        return this.splitDiffFlag;
    }

    public void setSplitDiffFlag(boolean splitDiffFlag) {
        this.splitDiffFlag = splitDiffFlag;
    }

    public Double getScale() {
        return this.scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    public OrgSplitVO() {
    }

    public OrgSplitVO(OrgToJsonVO splitedUnit, OrgToJsonVO splitUnit, Double scale, boolean splitDiffFlag) {
        this.splitedUnit = splitedUnit;
        this.splitUnit = splitUnit;
        this.scale = scale;
        this.splitDiffFlag = splitDiffFlag;
    }

    public String toString() {
        return "OrgSplitVO{splitedUnit=" + this.splitedUnit + ", splitUnit=" + this.splitUnit + ", scale=" + this.scale + '}';
    }
}

