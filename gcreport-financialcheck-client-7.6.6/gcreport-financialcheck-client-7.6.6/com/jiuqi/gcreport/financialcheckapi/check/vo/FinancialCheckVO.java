/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.check.vo;

import com.jiuqi.gcreport.financialcheckapi.check.vo.CheckResult;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO;
import java.util.Collections;
import java.util.List;

public class FinancialCheckVO {
    private List<GcRelatedItemVO> localUncheckedData = Collections.emptyList();
    private Integer localUncheckedTotal = 0;
    private List<GcRelatedItemVO> oppUncheckedData = Collections.emptyList();
    private Integer oppUncheckedTotal = 0;
    private List<GcRelatedItemVO> uncheckedDiffData = Collections.emptyList();
    private Integer uncheckedDiffTotal = 0;
    private List<GcRelatedItemVO> checkedData = Collections.emptyList();
    private Integer checkedTotal = 0;
    private List<GcRelatedItemVO> checkData = Collections.emptyList();
    private Integer checkTotal = 0;
    private List<GcRelatedItemVO> offsetedData = Collections.emptyList();
    private Integer offsetedTotal = 0;
    private CheckResult checkResult = new CheckResult();
    private String canCheckResult;

    public List<GcRelatedItemVO> getLocalUncheckedData() {
        return this.localUncheckedData;
    }

    public void setLocalUncheckedData(List<GcRelatedItemVO> localUncheckedData) {
        this.localUncheckedData = localUncheckedData;
    }

    public List<GcRelatedItemVO> getOppUncheckedData() {
        return this.oppUncheckedData;
    }

    public void setOppUncheckedData(List<GcRelatedItemVO> oppUncheckedData) {
        this.oppUncheckedData = oppUncheckedData;
    }

    public List<GcRelatedItemVO> getUncheckedDiffData() {
        return this.uncheckedDiffData;
    }

    public void setUncheckedDiffData(List<GcRelatedItemVO> uncheckedDiffData) {
        this.uncheckedDiffData = uncheckedDiffData;
    }

    public List<GcRelatedItemVO> getCheckedData() {
        return this.checkedData;
    }

    public void setCheckedData(List<GcRelatedItemVO> checkedData) {
        this.checkedData = checkedData;
    }

    public List<GcRelatedItemVO> getOffsetedData() {
        return this.offsetedData;
    }

    public void setOffsetedData(List<GcRelatedItemVO> offsetedData) {
        this.offsetedData = offsetedData;
    }

    public Integer getOffsetedTotal() {
        return this.offsetedTotal;
    }

    public void setOffsetedTotal(Integer offsetedTotal) {
        this.offsetedTotal = offsetedTotal;
    }

    public CheckResult getCheckResult() {
        return this.checkResult;
    }

    public void setCheckResult(CheckResult checkResult) {
        this.checkResult = checkResult;
    }

    public String getCanCheckResult() {
        return this.canCheckResult;
    }

    public void setCanCheckResult(String canCheckResult) {
        this.canCheckResult = canCheckResult;
    }

    public Integer getLocalUncheckedTotal() {
        return this.localUncheckedTotal;
    }

    public void setLocalUncheckedTotal(Integer localUncheckedTotal) {
        this.localUncheckedTotal = localUncheckedTotal;
    }

    public Integer getOppUncheckedTotal() {
        return this.oppUncheckedTotal;
    }

    public void setOppUncheckedTotal(Integer oppUncheckedTotal) {
        this.oppUncheckedTotal = oppUncheckedTotal;
    }

    public Integer getUncheckedDiffTotal() {
        return this.uncheckedDiffTotal;
    }

    public void setUncheckedDiffTotal(Integer uncheckedDiffTotal) {
        this.uncheckedDiffTotal = uncheckedDiffTotal;
    }

    public Integer getCheckedTotal() {
        return this.checkedTotal;
    }

    public void setCheckedTotal(Integer checkedTotal) {
        this.checkedTotal = checkedTotal;
    }

    public List<GcRelatedItemVO> getCheckData() {
        return this.checkData;
    }

    public void setCheckData(List<GcRelatedItemVO> checkData) {
        this.checkData = checkData;
    }

    public Integer getCheckTotal() {
        return this.checkTotal;
    }

    public void setCheckTotal(Integer checkTotal) {
        this.checkTotal = checkTotal;
    }

    public String toString() {
        return "FinancialCheckVO{localUncheckedData=" + this.localUncheckedData + ", localUncheckedTotal=" + this.localUncheckedTotal + ", oppUncheckedData=" + this.oppUncheckedData + ", oppUncheckedTotal=" + this.oppUncheckedTotal + ", uncheckedDiffData=" + this.uncheckedDiffData + ", uncheckedDiffTotal=" + this.uncheckedDiffTotal + ", checkedData=" + this.checkedData + ", checkedTotal=" + this.checkedTotal + ", checkData=" + this.checkData + ", checkTotal=" + this.checkTotal + ", offsetedData=" + this.offsetedData + ", offsetedTotal=" + this.offsetedTotal + ", checkResult=" + this.checkResult + ", canCheckResult='" + this.canCheckResult + '\'' + '}';
    }
}

