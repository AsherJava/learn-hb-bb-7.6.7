/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.scheme.vo;

import com.jiuqi.gcreport.financialcheckapi.scheme.vo.CheckAttributeNumVO;
import java.util.List;

public class FinancialCheckSchemeNumVO {
    private Integer totalNum;
    private Integer bothNum;
    private Integer singleNum;
    private Integer offsetVchrNum;
    private List<CheckAttributeNumVO> checkAttributeNumVOS;

    public Integer getTotalNum() {
        return this.totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getBothNum() {
        return this.bothNum;
    }

    public void setBothNum(Integer bothNum) {
        this.bothNum = bothNum;
    }

    public Integer getSingleNum() {
        return this.singleNum;
    }

    public void setSingleNum(Integer singleNum) {
        this.singleNum = singleNum;
    }

    public List<CheckAttributeNumVO> getCheckAttributeNumVOS() {
        return this.checkAttributeNumVOS;
    }

    public void setCheckAttributeNumVOS(List<CheckAttributeNumVO> checkAttributeNumVOS) {
        this.checkAttributeNumVOS = checkAttributeNumVOS;
    }

    public Integer getOffsetVchrNum() {
        return this.offsetVchrNum;
    }

    public void setOffsetVchrNum(Integer offsetVchrNum) {
        this.offsetVchrNum = offsetVchrNum;
    }
}

