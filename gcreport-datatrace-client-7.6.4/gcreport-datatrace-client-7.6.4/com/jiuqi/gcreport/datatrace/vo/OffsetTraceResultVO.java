/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.datatrace.vo;

import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceResultVO;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

public class OffsetTraceResultVO {
    private String id;
    private String mRecid;
    private String taskId;
    private String schemeId;
    private String defaultPeriod;
    private String systemId;
    private Integer acctYear;
    private Integer acctPeriod;
    private String unitCode;
    private String unitName;
    @NotNull
    private String oppUnitCode;
    private String oppUnitName;
    private String subjectCode;
    private String subjectName;
    private String memo;
    private String debitStr;
    private String creditStr;
    @NotNull
    private String offSetCurr;
    private String offSetCurrName;
    private Double diffd;
    private Double diffc;
    private String diff;
    private String inputUnitId;
    private String inputUnitName;
    private String unitVersion;
    private String orgType;
    private int elmMode;
    private String elmModeTitle;
    private String ruleId;
    private String ruleTitle;
    private String ruleParentId;
    private int offSetSrcType;
    private String offSetSrcTypeName;
    private String fetchFormula;
    private String fetchFormulaTitle;
    private List<OffsetAmtTraceResultVO> OffsetAmtTraces;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmRecid() {
        return this.mRecid;
    }

    public void setmRecid(String mRecid) {
        this.mRecid = mRecid;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getOppUnitCode() {
        return this.oppUnitCode;
    }

    public void setOppUnitCode(String oppUnitCode) {
        this.oppUnitCode = oppUnitCode;
    }

    public String getOppUnitName() {
        return this.oppUnitName;
    }

    public void setOppUnitName(String oppUnitName) {
        this.oppUnitName = oppUnitName;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDebitStr() {
        return this.debitStr;
    }

    public void setDebitStr(String debitStr) {
        this.debitStr = debitStr;
    }

    public String getCreditStr() {
        return this.creditStr;
    }

    public void setCreditStr(String creditStr) {
        this.creditStr = creditStr;
    }

    public String getOffSetCurr() {
        return this.offSetCurr;
    }

    public void setOffSetCurr(String offSetCurr) {
        this.offSetCurr = offSetCurr;
    }

    public String getOffSetCurrName() {
        return this.offSetCurrName;
    }

    public void setOffSetCurrName(String offSetCurrName) {
        this.offSetCurrName = offSetCurrName;
    }

    public Double getDiffd() {
        return this.diffd;
    }

    public void setDiffd(Double diffd) {
        this.diffd = diffd;
    }

    public Double getDiffc() {
        return this.diffc;
    }

    public void setDiffc(Double diffc) {
        this.diffc = diffc;
    }

    public String getDiff() {
        return this.diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getInputUnitId() {
        return this.inputUnitId;
    }

    public void setInputUnitId(String inputUnitId) {
        this.inputUnitId = inputUnitId;
    }

    public String getInputUnitName() {
        return this.inputUnitName;
    }

    public void setInputUnitName(String inputUnitName) {
        this.inputUnitName = inputUnitName;
    }

    public String getUnitVersion() {
        return this.unitVersion;
    }

    public void setUnitVersion(String unitVersion) {
        this.unitVersion = unitVersion;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public int getElmMode() {
        return this.elmMode;
    }

    public void setElmMode(int elmMode) {
        this.elmMode = elmMode;
    }

    public String getElmModeTitle() {
        return this.elmModeTitle;
    }

    public void setElmModeTitle(String elmModeTitle) {
        this.elmModeTitle = elmModeTitle;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleTitle() {
        return this.ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getRuleParentId() {
        return this.ruleParentId;
    }

    public void setRuleParentId(String ruleParentId) {
        this.ruleParentId = ruleParentId;
    }

    public int getOffSetSrcType() {
        return this.offSetSrcType;
    }

    public void setOffSetSrcType(int offSetSrcType) {
        this.offSetSrcType = offSetSrcType;
    }

    public String getOffSetSrcTypeName() {
        return this.offSetSrcTypeName;
    }

    public void setOffSetSrcTypeName(String offSetSrcTypeName) {
        this.offSetSrcTypeName = offSetSrcTypeName;
    }

    public String getFetchFormula() {
        return this.fetchFormula;
    }

    public void setFetchFormula(String fetchFormula) {
        this.fetchFormula = fetchFormula;
    }

    public String getFetchFormulaTitle() {
        return this.fetchFormulaTitle;
    }

    public void setFetchFormulaTitle(String fetchFormulaTitle) {
        this.fetchFormulaTitle = fetchFormulaTitle;
    }

    public List<OffsetAmtTraceResultVO> getOffsetAmtTraces() {
        if (null == this.OffsetAmtTraces) {
            return new ArrayList<OffsetAmtTraceResultVO>();
        }
        return this.OffsetAmtTraces;
    }

    public void setOffsetAmtTraces(List<OffsetAmtTraceResultVO> offsetAmtTraces) {
        this.OffsetAmtTraces = offsetAmtTraces;
    }
}

