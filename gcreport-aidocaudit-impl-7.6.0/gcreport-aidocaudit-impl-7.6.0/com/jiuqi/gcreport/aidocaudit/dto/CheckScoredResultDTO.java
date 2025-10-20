/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import java.util.List;

public class CheckScoredResultDTO {
    private Boolean hasScored;
    private Integer orgTotal;
    private Integer scoredOrgNum;
    private Integer noScoreOrgNum;
    private List<String> noScoreOrgIds;
    private List<String> scoredOrgIds;

    public CheckScoredResultDTO() {
    }

    public CheckScoredResultDTO(Boolean hasScored, Integer orgTotal, Integer scoredOrgNum, Integer noScoreOrgNum, List<String> noScoreOrgIds, List<String> scoredOrgIds) {
        this.hasScored = hasScored;
        this.orgTotal = orgTotal;
        this.scoredOrgNum = scoredOrgNum;
        this.noScoreOrgNum = noScoreOrgNum;
        this.noScoreOrgIds = noScoreOrgIds;
        this.scoredOrgIds = scoredOrgIds;
    }

    public Boolean getHasScored() {
        return this.hasScored;
    }

    public void setHasScored(Boolean hasScored) {
        this.hasScored = hasScored;
    }

    public Integer getOrgTotal() {
        return this.orgTotal;
    }

    public void setOrgTotal(Integer orgTotal) {
        this.orgTotal = orgTotal;
    }

    public Integer getScoredOrgNum() {
        return this.scoredOrgNum;
    }

    public void setScoredOrgNum(Integer scoredOrgNum) {
        this.scoredOrgNum = scoredOrgNum;
    }

    public List<String> getNoScoreOrgIds() {
        return this.noScoreOrgIds;
    }

    public void setNoScoreOrgIds(List<String> noScoreOrgIds) {
        this.noScoreOrgIds = noScoreOrgIds;
    }

    public List<String> getScoredOrgIds() {
        return this.scoredOrgIds;
    }

    public void setScoredOrgIds(List<String> scoredOrgIds) {
        this.scoredOrgIds = scoredOrgIds;
    }

    public Integer getNoScoreOrgNum() {
        return this.noScoreOrgNum;
    }

    public void setNoScoreOrgNum(Integer noScoreOrgNum) {
        this.noScoreOrgNum = noScoreOrgNum;
    }
}

