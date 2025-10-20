/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.unionrule.vo.BaseRuleVO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 */
package com.jiuqi.gcreport.onekeymerge.dto;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.unionrule.vo.BaseRuleVO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import java.util.List;

public class AutoMergeDTO {
    private String periodType;
    private String taskId;
    private String mergeMode;
    private List<String> mergeTasks;
    private String periodStr;
    private List<String> ruleIds;
    private List<GcOrgCacheVO> mergeOrgProp;
    private BaseDataDTO currency;
    private String orgType;
    private List<BaseRuleVO> ruleBaseData;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getMergeMode() {
        return this.mergeMode;
    }

    public void setMergeMode(String mergeMode) {
        this.mergeMode = mergeMode;
    }

    public List<String> getMergeTasks() {
        return this.mergeTasks;
    }

    public void setMergeTasks(List<String> mergeTasks) {
        this.mergeTasks = mergeTasks;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public List<String> getRuleIds() {
        return this.ruleIds;
    }

    public void setRuleIds(List<String> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public List<GcOrgCacheVO> getMergeOrgProp() {
        return this.mergeOrgProp;
    }

    public void setMergeOrgProp(List<GcOrgCacheVO> mergeOrgProp) {
        this.mergeOrgProp = mergeOrgProp;
    }

    public BaseDataDTO getCurrency() {
        return this.currency;
    }

    public void setCurrency(BaseDataDTO currency) {
        this.currency = currency;
    }

    public String toString() {
        return "AutoMergeDTO{periodType='" + this.periodType + '\'' + ", taskId='" + this.taskId + '\'' + ", mergeMode='" + this.mergeMode + '\'' + ", configNodes=" + this.mergeTasks + ", periodStr='" + this.periodStr + '\'' + ", ruleBaseData=" + this.ruleIds + ", mergeOrgProp=" + this.mergeOrgProp + ", currency='" + this.currency + '\'' + ", orgType='" + this.orgType + '\'' + '}';
    }

    public List<BaseRuleVO> getRuleBaseData() {
        return this.ruleBaseData;
    }

    public void setRuleBaseData(List<BaseRuleVO> ruleBaseData) {
        this.ruleBaseData = ruleBaseData;
    }
}

