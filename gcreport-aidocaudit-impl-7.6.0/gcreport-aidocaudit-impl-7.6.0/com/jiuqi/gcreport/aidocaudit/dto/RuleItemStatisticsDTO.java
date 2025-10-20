/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import java.math.BigDecimal;

public class RuleItemStatisticsDTO {
    private Integer matchUnitNum;
    private Integer suspectMatchUnitNum;
    private Integer unMatchUnitNum;
    private BigDecimal passRate;

    public RuleItemStatisticsDTO(Integer matchUnitNum, Integer suspectMatchUnitNum, Integer unMatchUnitNum, BigDecimal passRate) {
        this.matchUnitNum = matchUnitNum;
        this.suspectMatchUnitNum = suspectMatchUnitNum;
        this.unMatchUnitNum = unMatchUnitNum;
        this.passRate = passRate;
    }

    public Integer getMatchUnitNum() {
        return this.matchUnitNum;
    }

    public void setMatchUnitNum(Integer matchUnitNum) {
        this.matchUnitNum = matchUnitNum;
    }

    public Integer getSuspectMatchUnitNum() {
        return this.suspectMatchUnitNum;
    }

    public void setSuspectMatchUnitNum(Integer suspectMatchUnitNum) {
        this.suspectMatchUnitNum = suspectMatchUnitNum;
    }

    public Integer getUnMatchUnitNum() {
        return this.unMatchUnitNum;
    }

    public void setUnMatchUnitNum(Integer unMatchUnitNum) {
        this.unMatchUnitNum = unMatchUnitNum;
    }

    public BigDecimal getPassRate() {
        return this.passRate;
    }

    public void setPassRate(BigDecimal passRate) {
        this.passRate = passRate;
    }
}

