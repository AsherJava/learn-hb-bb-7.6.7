/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.datatrace.vo;

import com.jiuqi.common.base.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GcDataTraceCondi {
    private String gcDataTraceType;
    private Integer pageSize;
    private Integer pageNum;
    private Integer acctPeriod;
    private Integer acctYear;
    private String selectAdjustCode;
    private String periodStr;
    private String taskId;
    private String schemeId;
    private String srcId;
    private String inputUnitId;
    private String unitId;
    private String oppUnitId;
    private String orgType;
    private String currency;
    private String defineCode;
    private String billCode;
    private String billTitle;
    private List<String> ruleIds;
    private List<String> otherShowColumnNames;
    private Map<String, Object> extendParams;

    public Map<String, Object> getExtendParams() {
        return this.extendParams == null ? new HashMap() : this.extendParams;
    }

    public void addExtendParams(String key, Object value) {
        if (this.extendParams == null) {
            this.extendParams = new HashMap<String, Object>();
        }
        this.extendParams.put(key, value);
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getGcDataTraceType() {
        return this.gcDataTraceType;
    }

    public void setGcDataTraceType(String gcDataTraceType) {
        this.gcDataTraceType = gcDataTraceType;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public List<String> getRuleIds() {
        return this.ruleIds;
    }

    public void setRuleIds(List<String> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getInputUnitId() {
        return this.inputUnitId;
    }

    public void setInputUnitId(String inputUnitId) {
        this.inputUnitId = inputUnitId;
    }

    public String getDefineCode() {
        return this.defineCode;
    }

    public void setDefineCode(String defineCode) {
        this.defineCode = defineCode;
    }

    public String getCurrency() {
        if (StringUtils.isEmpty((String)this.currency)) {
            this.currency = "CNY";
        }
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.oppUnitId = oppUnitId;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getBillTitle() {
        return this.billTitle;
    }

    public void setBillTitle(String billTitle) {
        this.billTitle = billTitle;
    }

    public List<String> getOtherShowColumnNames() {
        return this.otherShowColumnNames;
    }

    public void setOtherShowColumnNames(List<String> otherShowColumnNames) {
        this.otherShowColumnNames = otherShowColumnNames;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }
}

