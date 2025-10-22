/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.vo;

import com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO;

public class GcCalcAndFinishResultVO
extends GcBaseTaskStateVO {
    private String orgName;
    private String prcessResult;
    private String calcPrcessResult;
    private Long useTime;
    private Integer calcUseTime;

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPrcessResult() {
        return this.prcessResult;
    }

    public void setPrcessResult(String prcessResult) {
        this.prcessResult = prcessResult;
    }

    public String getCalcPrcessResult() {
        return this.calcPrcessResult;
    }

    public void setCalcPrcessResult(String calcPrcessResult) {
        this.calcPrcessResult = calcPrcessResult;
    }

    public Long getUseTime() {
        return this.useTime;
    }

    public void setUseTime(Long useTime) {
        this.useTime = useTime;
    }

    public Integer getCalcUseTime() {
        return this.calcUseTime;
    }

    public void setCalcUseTime(Integer calcUseTime) {
        this.calcUseTime = calcUseTime;
    }
}

