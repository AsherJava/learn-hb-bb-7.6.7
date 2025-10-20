/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.vo.DebugZbInfoVO
 *  com.jiuqi.nr.common.asynctask.entity.AysncTaskArgsInfo
 */
package com.jiuqi.gcreport.onekeymerge.dto;

import com.jiuqi.gcreport.calculate.vo.DebugZbInfoVO;
import com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum;
import com.jiuqi.nr.common.asynctask.entity.AysncTaskArgsInfo;
import java.util.List;

public class FinishCalcAsyncTaskDTO
extends AysncTaskArgsInfo {
    private String taskId;
    private String schemeId;
    private String currency;
    private Integer acctYear;
    private Integer acctPeriod;
    private Integer periodType;
    private String periodStr;
    private String orgType;
    private String adjTypeId;
    private String orgId;
    private List<String> defines;
    private String taskLogId;
    private MergeTypeEnum mergeType;
    private List<String> ruleIds;
    private List<String> taskCodes;
    private String ipAddr;
    private DebugZbInfoVO debugZb;
    private String userName;
    private String loginToken;
    private String selectAdjustCode;
    private boolean rewriteDiff;
    private boolean dataSum;

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

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public Integer getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getAdjTypeId() {
        return this.adjTypeId;
    }

    public void setAdjTypeId(String adjTypeId) {
        this.adjTypeId = adjTypeId;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<String> getDefines() {
        return this.defines;
    }

    public void setDefines(List<String> defines) {
        this.defines = defines;
    }

    public String getTaskLogId() {
        return this.taskLogId;
    }

    public void setTaskLogId(String taskLogId) {
        this.taskLogId = taskLogId;
    }

    public MergeTypeEnum getMergeType() {
        return this.mergeType;
    }

    public void setMergeType(MergeTypeEnum mergeType) {
        this.mergeType = mergeType;
    }

    public List<String> getRuleIds() {
        return this.ruleIds;
    }

    public void setRuleIds(List<String> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public List<String> getTaskCodes() {
        return this.taskCodes;
    }

    public void setTaskCodes(List<String> taskCodes) {
        this.taskCodes = taskCodes;
    }

    public String getIpAddr() {
        return this.ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public DebugZbInfoVO getDebugZb() {
        return this.debugZb;
    }

    public void setDebugZb(DebugZbInfoVO debugZb) {
        this.debugZb = debugZb;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginToken() {
        return this.loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public boolean isRewriteDiff() {
        return this.rewriteDiff;
    }

    public void setRewriteDiff(boolean rewriteDiff) {
        this.rewriteDiff = rewriteDiff;
    }

    public boolean isDataSum() {
        return this.dataSum;
    }

    public void setDataSum(boolean dataSum) {
        this.dataSum = dataSum;
    }
}

