/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.runner.vo;

import com.jiuqi.gcreport.inputdata.runner.vo.InputDataRemindRuleSetVO;
import java.util.List;

public class InputDataMessageRemindConfigVO {
    private String taskId;
    private String periodTypeSet;
    private String periodStr;
    private String selectAdjustCode;
    private String unitRange;
    private String changeDataGather;
    private String remindType;
    private String mailTitle;
    private String mailContent;
    private List<String> userAndRoleData;
    private List<InputDataRemindRuleSetVO> ruleSetData;
    private String userId;
    private String orgType;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPeriodTypeSet() {
        return this.periodTypeSet;
    }

    public void setPeriodTypeSet(String periodTypeSet) {
        this.periodTypeSet = periodTypeSet;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public String getUnitRange() {
        return this.unitRange;
    }

    public void setUnitRange(String unitRange) {
        this.unitRange = unitRange;
    }

    public String getChangeDataGather() {
        return this.changeDataGather;
    }

    public void setChangeDataGather(String changeDataGather) {
        this.changeDataGather = changeDataGather;
    }

    public String getRemindType() {
        return this.remindType;
    }

    public void setRemindType(String remindType) {
        this.remindType = remindType;
    }

    public String getMailTitle() {
        return this.mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getMailContent() {
        return this.mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public List<String> getUserAndRoleData() {
        return this.userAndRoleData;
    }

    public void setUserAndRoleData(List<String> userAndRoleData) {
        this.userAndRoleData = userAndRoleData;
    }

    public List<InputDataRemindRuleSetVO> getRuleSetData() {
        return this.ruleSetData;
    }

    public void setRuleSetData(List<InputDataRemindRuleSetVO> ruleSetData) {
        this.ruleSetData = ruleSetData;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}

