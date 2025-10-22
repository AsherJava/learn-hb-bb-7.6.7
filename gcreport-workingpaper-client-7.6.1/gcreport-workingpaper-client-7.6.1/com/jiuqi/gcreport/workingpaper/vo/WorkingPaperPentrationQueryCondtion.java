/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.vo;

import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import java.util.List;

public class WorkingPaperPentrationQueryCondtion
extends WorkingPaperQueryCondition {
    private String pentraColumnName;
    private String workPaperVoJson;
    private List<String> unitIdList;
    private List<String> oppUnitIdList;
    private String currPentrationKMDM;
    private String currShowTypeValue;
    private String currPentrationRuleID;
    private String currPentrationMerid;
    private Integer offsetSrcType;
    private String mrecid;
    private String fetchSetGroupId;
    private String primarySettingId;

    public List<String> getUnitIdList() {
        return this.unitIdList;
    }

    public void setUnitIdList(List<String> unitIdList) {
        this.unitIdList = unitIdList;
    }

    public List<String> getOppUnitIdList() {
        return this.oppUnitIdList;
    }

    public void setOppUnitIdList(List<String> oppUnitIdList) {
        this.oppUnitIdList = oppUnitIdList;
    }

    public String getCurrPentrationKMDM() {
        return this.currPentrationKMDM;
    }

    public void setCurrPentrationKMDM(String currPentrationKMDM) {
        this.currPentrationKMDM = currPentrationKMDM;
    }

    public String getCurrShowTypeValue() {
        return this.currShowTypeValue;
    }

    public void setCurrShowTypeValue(String currShowTypeValue) {
        this.currShowTypeValue = currShowTypeValue;
    }

    public String getCurrPentrationRuleID() {
        return this.currPentrationRuleID;
    }

    public void setCurrPentrationRuleID(String currPentrationRuleID) {
        this.currPentrationRuleID = currPentrationRuleID;
    }

    public String getCurrPentrationMerid() {
        return this.currPentrationMerid;
    }

    public void setCurrPentrationMerid(String currPentrationMerid) {
        this.currPentrationMerid = currPentrationMerid;
    }

    public Integer getOffsetSrcType() {
        return this.offsetSrcType;
    }

    public void setOffsetSrcType(Integer offsetSrcType) {
        this.offsetSrcType = offsetSrcType;
    }

    public String getMrecid() {
        return this.mrecid;
    }

    public void setMrecid(String mrecid) {
        this.mrecid = mrecid;
    }

    public String getFetchSetGroupId() {
        return this.fetchSetGroupId;
    }

    public void setFetchSetGroupId(String fetchSetGroupId) {
        this.fetchSetGroupId = fetchSetGroupId;
    }

    public String getPrimarySettingId() {
        return this.primarySettingId;
    }

    public void setPrimarySettingId(String primarySettingId) {
        this.primarySettingId = primarySettingId;
    }

    public String getPentraColumnName() {
        return this.pentraColumnName;
    }

    public void setPentraColumnName(String pentraColumnName) {
        this.pentraColumnName = pentraColumnName;
    }

    public String getWorkPaperVoJson() {
        return this.workPaperVoJson;
    }

    public void setWorkPaperVoJson(String workPaperVoJson) {
        this.workPaperVoJson = workPaperVoJson;
    }
}

