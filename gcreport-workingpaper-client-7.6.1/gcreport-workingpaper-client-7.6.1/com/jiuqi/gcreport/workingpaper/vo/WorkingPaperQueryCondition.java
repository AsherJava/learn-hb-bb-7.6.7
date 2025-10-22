/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 */
package com.jiuqi.gcreport.workingpaper.vo;

import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WorkingPaperQueryCondition {
    private String taskID;
    private String taskName;
    private String schemeID;
    private String schemeName;
    private String org_type;
    private String orgid;
    private String orgName;
    private String currency;
    private String currencyCode;
    private String selectAdjustCode;
    private String workType;
    private String workTypeName;
    private String isAll;
    private int pageNum;
    private int pageSize;
    private Boolean isFilterZero;
    private String showType;
    private List<String> orgchild;
    private Integer acctPeriod;
    private Integer acctYear;
    private List<Integer> elmModes;
    private Map<String, Object> filterCondition;
    private String unitId;
    private String oppUnitId;
    private List<String> unitIds;
    private List<String> oppUnitIds;
    private String subject;
    private String cacheRecordKey;
    private String[] htmlTableData;
    private String periodStr;
    private String periodTypeChar;
    private Integer periodType;
    private List<String> baseSubjectCodes;
    private List<UnionRuleVO> baseRuleProp;
    private Boolean showManualOffset;
    private List<String> otherShowColumnKeys;
    private List<String> otherShowColumnTitles;
    private boolean isPentrate = false;
    private Boolean sumSubjectCodeByLevel;
    private String primaryTableType;
    private Boolean arbitrarilyMerge;
    private List<String> orgIds;
    private String orgBatchId;
    private Integer orgComSupLength;
    private boolean curPrimarySubjectOffsetEntry;
    private List<String> exportCols;
    private String queryType;
    private String queryWayId;
    private Map<String, String> assistName2Labels;
    private List<String> mergeColumnSelectKeys;

    public List<String> getMergeColumnSelectKeys() {
        return this.mergeColumnSelectKeys;
    }

    public void setMergeColumnSelectKeys(List<String> mergeColumnSelectKeys) {
        this.mergeColumnSelectKeys = mergeColumnSelectKeys;
    }

    public Map<String, String> getAssistName2Labels() {
        if (this.assistName2Labels == null) {
            return new LinkedHashMap<String, String>();
        }
        return this.assistName2Labels;
    }

    public void setAssistName2Labels(Map<String, String> assistName2Labels) {
        this.assistName2Labels = assistName2Labels;
    }

    public Integer getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public String getPeriodTypeChar() {
        return this.periodTypeChar;
    }

    public void setPeriodTypeChar(String periodTypeChar) {
        this.periodTypeChar = periodTypeChar;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String[] getHtmlTableData() {
        return this.htmlTableData;
    }

    public void setHtmlTableData(String[] htmlTableData) {
        this.htmlTableData = htmlTableData;
    }

    public String getCacheRecordKey() {
        return this.cacheRecordKey;
    }

    public void setCacheRecordKey(String cacheRecordKey) {
        this.cacheRecordKey = cacheRecordKey;
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

    public List<Integer> getElmModes() {
        return this.elmModes;
    }

    public void setElmModes(List<Integer> elmModes) {
        this.elmModes = elmModes;
    }

    public Map<String, Object> getFilterCondition() {
        if (this.filterCondition == null) {
            this.filterCondition = new HashMap<String, Object>();
        }
        return this.filterCondition;
    }

    public void setFilterCondition(Map<String, Object> filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getOrgchild() {
        return this.orgchild;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public void setOrgchild(List<String> orgchild) {
        this.orgchild = orgchild;
    }

    public String getOrg_type() {
        return this.org_type;
    }

    public void setOrg_type(String org_type) {
        this.org_type = org_type;
    }

    public String getSchemeID() {
        return this.schemeID;
    }

    public void setSchemeID(String schemeID) {
        this.schemeID = schemeID;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getTaskID() {
        return this.taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        if (null == this.currency) {
            this.currency = currencyCode;
        }
    }

    public String getWorkType() {
        return this.workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getShowType() {
        return this.showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getWorkTypeName() {
        return this.workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public String getIsAll() {
        return this.isAll;
    }

    public void setIsAll(String isAll) {
        this.isAll = isAll;
    }

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

    public Boolean getIsFilterZero() {
        return this.isFilterZero == false;
    }

    public void setIsFilterZero(Boolean isFilterZero) {
        this.isFilterZero = isFilterZero;
    }

    public List<String> getBaseSubjectCodes() {
        if (this.baseSubjectCodes == null) {
            return Collections.emptyList();
        }
        return this.baseSubjectCodes;
    }

    public void setBaseSubjectCodes(List<String> baseSubjectCodes) {
        this.baseSubjectCodes = baseSubjectCodes;
    }

    public Boolean getFilterZero() {
        return this.isFilterZero;
    }

    public void setFilterZero(Boolean filterZero) {
        this.isFilterZero = filterZero;
    }

    public List<UnionRuleVO> getBaseRuleProp() {
        return this.baseRuleProp;
    }

    public void setBaseRuleProp(List<UnionRuleVO> baseRuleProp) {
        this.baseRuleProp = baseRuleProp;
    }

    public Boolean getShowManualOffset() {
        return this.showManualOffset;
    }

    public void setShowManualOffset(Boolean showManualOffset) {
        this.showManualOffset = showManualOffset;
    }

    public List<String> getOtherShowColumnKeys() {
        if (this.otherShowColumnKeys == null) {
            return new ArrayList<String>();
        }
        return this.otherShowColumnKeys;
    }

    public void setOtherShowColumnKeys(List<String> otherShowColumnKeys) {
        this.otherShowColumnKeys = otherShowColumnKeys;
    }

    public boolean isPentrate() {
        return this.isPentrate;
    }

    public void setPentrate(boolean pentrate) {
        this.isPentrate = pentrate;
    }

    public Boolean getSumSubjectCodeByLevel() {
        if (null == this.sumSubjectCodeByLevel) {
            return Boolean.TRUE;
        }
        return this.sumSubjectCodeByLevel;
    }

    public void setSumSubjectCodeByLevel(Boolean sumSubjectCodeByLevel) {
        this.sumSubjectCodeByLevel = sumSubjectCodeByLevel;
    }

    public String getPrimaryTableType() {
        return this.primaryTableType;
    }

    public void setPrimaryTableType(String primaryTableType) {
        this.primaryTableType = primaryTableType;
    }

    public Boolean getArbitrarilyMerge() {
        return this.arbitrarilyMerge;
    }

    public void setArbitrarilyMerge(Boolean arbitrarilyMerge) {
        this.arbitrarilyMerge = arbitrarilyMerge;
    }

    public List<String> getOrgIds() {
        return this.orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    public String getOrgBatchId() {
        return this.orgBatchId;
    }

    public void setOrgBatchId(String orgBatchId) {
        this.orgBatchId = orgBatchId;
    }

    public Integer getOrgComSupLength() {
        return this.orgComSupLength;
    }

    public void setOrgComSupLength(Integer orgComSupLength) {
        this.orgComSupLength = orgComSupLength;
    }

    public boolean isCurPrimarySubjectOffsetEntry() {
        if (Objects.isNull(this.curPrimarySubjectOffsetEntry)) {
            this.curPrimarySubjectOffsetEntry = false;
        }
        return this.curPrimarySubjectOffsetEntry;
    }

    public void setCurPrimarySubjectOffsetEntry(boolean curPrimarySubjectOffsetEntry) {
        this.curPrimarySubjectOffsetEntry = curPrimarySubjectOffsetEntry;
    }

    public List<String> getExportCols() {
        return this.exportCols;
    }

    public void setExportCols(List<String> exportCols) {
        this.exportCols = exportCols;
    }

    public List<String> getOtherShowColumnTitles() {
        return this.otherShowColumnTitles;
    }

    public void setOtherShowColumnTitles(List<String> otherShowColumnTitles) {
        this.otherShowColumnTitles = otherShowColumnTitles;
    }

    public String getQueryType() {
        return this.queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getQueryWayId() {
        return this.queryWayId;
    }

    public void setQueryWayId(String queryWayId) {
        this.queryWayId = queryWayId;
    }

    public List<String> getUnitIds() {
        return this.unitIds;
    }

    public void setUnitIds(List<String> unitIds) {
        this.unitIds = unitIds;
    }

    public List<String> getOppUnitIds() {
        return this.oppUnitIds;
    }

    public void setOppUnitIds(List<String> oppUnitIds) {
        this.oppUnitIds = oppUnitIds;
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
}

