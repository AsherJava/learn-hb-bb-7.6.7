/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.Min
 */
package com.jiuqi.gcreport.journalsingle.condition;

import javax.validation.constraints.Min;

public class JournalPostRuleCondition {
    public String taskId;
    public String schemeId;
    public String formId;
    public String ruleId;
    public String zbId;
    public String periodStr;
    @Min(value=1L, message="\u9875\u7801\u53c2\u6570\u4e0d\u6b63\u786e")
    private @Min(value=1L, message="\u9875\u7801\u53c2\u6570\u4e0d\u6b63\u786e") int pageNum = 1;
    @Min(value=1L, message="\u6bcf\u9875\u6570\u4e0d\u80fd\u5c0f\u4e8e1")
    private @Min(value=1L, message="\u6bcf\u9875\u6570\u4e0d\u80fd\u5c0f\u4e8e1") int pageSize = 20;
    private String keyWord;

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyWord() {
        return this.keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
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

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getZbId() {
        return this.zbId;
    }

    public void setZbId(String zbId) {
        this.zbId = zbId;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }
}

