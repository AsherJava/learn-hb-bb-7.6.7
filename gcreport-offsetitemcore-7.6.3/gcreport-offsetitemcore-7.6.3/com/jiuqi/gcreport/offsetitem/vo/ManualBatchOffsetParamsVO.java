/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.List;

public class ManualBatchOffsetParamsVO
extends QueryParamsVO {
    private Double toleranceRange;
    private String offsetType;
    private Boolean unilateralOffsetFlag;
    private Boolean canBothDc;
    private List<GcOrgCacheVO> unitOrgList;
    private List<GcOrgCacheVO> oppUnitOrgList;
    private String diffSubjectCode;
    private boolean chooseFilter;
    private List<String> recordIds;

    public List<String> getRecordIds() {
        return this.recordIds;
    }

    public void setRecordIds(List<String> recordIds) {
        this.recordIds = recordIds;
    }

    public Double getToleranceRange() {
        return this.toleranceRange;
    }

    public void setToleranceRange(Double toleranceRange) {
        this.toleranceRange = toleranceRange;
    }

    public String getOffsetType() {
        return this.offsetType;
    }

    public void setOffsetType(String offsetType) {
        this.offsetType = offsetType;
    }

    public Boolean getUnilateralOffsetFlag() {
        return this.unilateralOffsetFlag;
    }

    public void setUnilateralOffsetFlag(Boolean unilateralOffsetFlag) {
        this.unilateralOffsetFlag = unilateralOffsetFlag;
    }

    public Boolean getCanBothDc() {
        return this.canBothDc;
    }

    public void setCanBothDc(Boolean canBothDc) {
        this.canBothDc = canBothDc;
    }

    public List<GcOrgCacheVO> getUnitOrgList() {
        return this.unitOrgList;
    }

    public void setUnitOrgList(List<GcOrgCacheVO> unitOrgList) {
        this.unitOrgList = unitOrgList;
    }

    public List<GcOrgCacheVO> getOppUnitOrgList() {
        return this.oppUnitOrgList;
    }

    public void setOppUnitOrgList(List<GcOrgCacheVO> oppUnitOrgList) {
        this.oppUnitOrgList = oppUnitOrgList;
    }

    public String getDiffSubjectCode() {
        return this.diffSubjectCode;
    }

    public void setDiffSubjectCode(String diffSubjectCode) {
        this.diffSubjectCode = diffSubjectCode;
    }

    public boolean isChooseFilter() {
        return this.chooseFilter;
    }

    public void setChooseFilter(boolean chooseFilter) {
        this.chooseFilter = chooseFilter;
    }
}

