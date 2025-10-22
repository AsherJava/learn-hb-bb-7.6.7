/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.offsetitem.dto;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryParamsDTO
extends GcTaskBaseArguments {
    private String systemId;
    private List<String> unitIdList;
    private List<String> oppUnitIdList;
    private boolean fixedUnitQueryPosition;
    private boolean whenOneUnitIsAllChild;
    private List<String> rules;
    private List<String> subjectCodes;
    private List<String> gcBusinessTypeCodes;
    private String gcBusinessTypeQueryRule;
    private boolean isOnlyQueryNullGcBusinessTypeCode = false;
    private boolean isOnlyQueryNullElmMode = false;
    private boolean isOnlyQueryNullRule = false;
    private Collection<Integer> offSetSrcTypes;
    private List<Integer> forbidOffSetSrcTypes;
    private boolean isDelete;
    private List<Integer> elmModes;
    private int pageNum;
    private int pageSize;
    private List<String> otherShowColumns;
    private List<String> otherShowColumnTitles;
    private List<String> notOffsetOtherColumns;
    private List<String> notOffsetOtherTitles;
    private List<String> sumTabOtherTitles;
    private List<String> sumTabOtherColumns;
    private boolean queryAllColumns;
    private Map<String, Object> filterCondition;
    private Map<String, Object> sumTabPenetrateCondition;
    private Boolean existGcBusinessType;
    private Map<String, Object> sumSubFilterCondition;
    private Map<String, Object> offsetFilterCondition;
    private Map<String, Object> notOffsetFilterCondition;
    private Map<String, Object> notOffsetParentFilterCondition;
    private List<String> mrecids;
    private List<String> srcOffsetGroupIds;
    private String tabName;
    private boolean isCarryOver;
    private boolean filterDisableItem;
    private boolean isSameCtrl;
    private String sameCtrlPeriodStr;
    private Boolean arbitrarilyMerge;
    private List<String> orgIds;
    private String orgBatchId;
    private Integer orgComSupLength;
    private Boolean enableTempTableFilterUnitOrOppUnit = false;
    private List<String> tempGroupIdList = new ArrayList<String>();
    private List<String> effectTypes;
    private List<GcOrgCacheVO> orgList;

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Map<String, Object> getSumSubFilterCondition() {
        return this.sumSubFilterCondition;
    }

    public void setSumSubFilterCondition(Map<String, Object> sumSubFilterCondition) {
        this.sumSubFilterCondition = sumSubFilterCondition;
    }

    public Integer getAcctPeriod() {
        Integer acctPeriod = super.getAcctPeriod();
        if (acctPeriod == null) {
            return -1;
        }
        return acctPeriod;
    }

    public String getCurrencyUpperCase() {
        String currency = super.getCurrency();
        if (StringUtils.isEmpty((String)currency)) {
            currency = "CNY";
        }
        return currency.toUpperCase();
    }

    public Boolean getExistGcBusinessType() {
        return this.existGcBusinessType;
    }

    public void setExistGcBusinessType(Boolean existGcBusinessType) {
        this.existGcBusinessType = existGcBusinessType;
    }

    public Map<String, Object> getSumTabPenetrateCondition() {
        return this.sumTabPenetrateCondition;
    }

    public void setSumTabPenetrateCondition(Map<String, Object> sumTabPenetrateCondition) {
        this.sumTabPenetrateCondition = sumTabPenetrateCondition;
    }

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

    public List<String> getRules() {
        return this.rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    public List<Integer> getElmModes() {
        return this.elmModes;
    }

    public void setElmModes(List<Integer> elmModes) {
        this.elmModes = elmModes;
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

    public List<String> getOtherShowColumns() {
        if (null == this.otherShowColumns) {
            this.otherShowColumns = new ArrayList<String>();
        }
        return this.otherShowColumns;
    }

    public void setOtherShowColumns(List<String> otherShowColumns) {
        this.otherShowColumns = otherShowColumns;
    }

    public List<String> getOtherShowColumnTitles() {
        return this.otherShowColumnTitles;
    }

    public List<String> getNotOffsetOtherColumns() {
        return this.notOffsetOtherColumns;
    }

    public void setNotOffsetOtherColumns(List<String> notOffsetOtherColumns) {
        this.notOffsetOtherColumns = notOffsetOtherColumns;
    }

    public List<String> getNotOffsetOtherTitles() {
        return this.notOffsetOtherTitles;
    }

    public void setNotOffsetOtherTitles(List<String> notOffsetOtherTitles) {
        this.notOffsetOtherTitles = notOffsetOtherTitles;
    }

    public List<String> getSumTabOtherTitles() {
        return this.sumTabOtherTitles;
    }

    public void setSumTabOtherTitles(List<String> sumTabOtherTitles) {
        this.sumTabOtherTitles = sumTabOtherTitles;
    }

    public List<String> getSumTabOtherColumns() {
        return this.sumTabOtherColumns;
    }

    public void setSumTabOtherColumns(List<String> sumTabOtherColumns) {
        this.sumTabOtherColumns = sumTabOtherColumns;
    }

    public void setOtherShowColumnTitles(List<String> otherShowColumnTitles) {
        this.otherShowColumnTitles = otherShowColumnTitles;
    }

    public Map<String, Object> getFilterCondition() {
        if (null == this.filterCondition) {
            this.filterCondition = new HashMap<String, Object>();
        }
        return this.filterCondition;
    }

    public void setFilterCondition(Map<String, Object> filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getOrgType() {
        String orgType = super.getOrgType();
        if (StringUtils.isEmpty((String)orgType)) {
            return "MD_ORG_CORPORATE";
        }
        return orgType;
    }

    public Collection<Integer> getOffSetSrcTypes() {
        return this.offSetSrcTypes;
    }

    public void setOffSetSrcTypes(Collection<Integer> offSetSrcTypes) {
        this.offSetSrcTypes = offSetSrcTypes;
    }

    public List<String> getSubjectCodes() {
        return this.subjectCodes;
    }

    public void setSubjectCodes(List<String> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }

    public List<Integer> getForbidOffSetSrcTypes() {
        return this.forbidOffSetSrcTypes;
    }

    public void setForbidOffSetSrcTypes(List<Integer> forbidOffSetSrcTypes) {
        this.forbidOffSetSrcTypes = forbidOffSetSrcTypes;
    }

    public boolean isQueryAllColumns() {
        return this.queryAllColumns;
    }

    public void setQueryAllColumns(boolean queryAllColumns) {
        this.queryAllColumns = queryAllColumns;
    }

    public boolean isDelete() {
        return this.isDelete;
    }

    public void setDelete(boolean delete) {
        this.isDelete = delete;
    }

    public Map<String, Object> getOffsetFilterCondition() {
        return this.offsetFilterCondition;
    }

    public void setOffsetFilterCondition(Map<String, Object> offsetFilterCondition) {
        this.offsetFilterCondition = offsetFilterCondition;
    }

    public Map<String, Object> getNotOffsetFilterCondition() {
        return this.notOffsetFilterCondition;
    }

    public void setNotOffsetFilterCondition(Map<String, Object> notOffsetFilterCondition) {
        this.notOffsetFilterCondition = notOffsetFilterCondition;
    }

    public Map<String, Object> getNotOffsetParentFilterCondition() {
        return this.notOffsetParentFilterCondition;
    }

    public void setNotOffsetParentFilterCondition(Map<String, Object> notOffsetParentFilterCondition) {
        this.notOffsetParentFilterCondition = notOffsetParentFilterCondition;
    }

    public List<String> getMrecids() {
        return this.mrecids;
    }

    public void setMrecids(List<String> mrecids) {
        this.mrecids = mrecids;
    }

    public List<String> getSrcOffsetGroupIds() {
        return this.srcOffsetGroupIds;
    }

    public void setSrcOffsetGroupIds(List<String> srcOffsetGroupIds) {
        this.srcOffsetGroupIds = srcOffsetGroupIds;
    }

    public boolean isFixedUnitQueryPosition() {
        return this.fixedUnitQueryPosition;
    }

    public void setFixedUnitQueryPosition(boolean fixedUnitQueryPosition) {
        this.fixedUnitQueryPosition = fixedUnitQueryPosition;
    }

    public boolean isWhenOneUnitIsAllChild() {
        return this.whenOneUnitIsAllChild;
    }

    public void setWhenOneUnitIsAllChild(boolean whenOneUnitIsAllChild) {
        this.whenOneUnitIsAllChild = whenOneUnitIsAllChild;
    }

    public String getTabName() {
        return this.tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public boolean isCarryOver() {
        return this.isCarryOver;
    }

    public void setCarryOver(boolean carryOver) {
        this.isCarryOver = carryOver;
    }

    public boolean isFilterDisableItem() {
        return this.filterDisableItem;
    }

    public void setFilterDisableItem(boolean filterDisableItem) {
        this.filterDisableItem = filterDisableItem;
    }

    public boolean isSameCtrl() {
        return this.isSameCtrl;
    }

    public void setSameCtrl(boolean sameCtrl) {
        this.isSameCtrl = sameCtrl;
    }

    public String getSameCtrlPeriodStr() {
        return this.sameCtrlPeriodStr;
    }

    public void setSameCtrlPeriodStr(String sameCtrlPeriodStr) {
        this.sameCtrlPeriodStr = sameCtrlPeriodStr;
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

    public List<String> getGcBusinessTypeCodes() {
        return this.gcBusinessTypeCodes;
    }

    public void setGcBusinessTypeCodes(List<String> gcBusinessTypeCodes) {
        this.gcBusinessTypeCodes = gcBusinessTypeCodes;
    }

    public boolean isOnlyQueryNullGcBusinessTypeCode() {
        return this.isOnlyQueryNullGcBusinessTypeCode;
    }

    public void setOnlyQueryNullGcBusinessTypeCode(boolean isOnlyQueryNullGcBusinessTypeCode) {
        this.isOnlyQueryNullGcBusinessTypeCode = isOnlyQueryNullGcBusinessTypeCode;
    }

    public boolean isOnlyQueryNullElmMode() {
        return this.isOnlyQueryNullElmMode;
    }

    public void setOnlyQueryNullElmMode(boolean isOnlyQueryNullElmMode) {
        this.isOnlyQueryNullElmMode = isOnlyQueryNullElmMode;
    }

    public boolean isOnlyQueryNullRule() {
        return this.isOnlyQueryNullRule;
    }

    public void setOnlyQueryNullRule(boolean isOnlyQueryNullRule) {
        this.isOnlyQueryNullRule = isOnlyQueryNullRule;
    }

    public String getGcBusinessTypeQueryRule() {
        return this.gcBusinessTypeQueryRule;
    }

    public void setGcBusinessTypeQueryRule(String gcBusinessTypeQueryRule) {
        this.gcBusinessTypeQueryRule = gcBusinessTypeQueryRule;
    }

    public List<String> getTempGroupIdList() {
        return this.tempGroupIdList;
    }

    public void setTempGroupIdList(List<String> tempGroupIdList) {
        this.tempGroupIdList = tempGroupIdList;
    }

    public Boolean getEnableTempTableFilterUnitOrOppUnit() {
        return this.enableTempTableFilterUnitOrOppUnit;
    }

    public void setEnableTempTableFilterUnitOrOppUnit(Boolean enableTempTableFilterUnitOrOppUnit) {
        this.enableTempTableFilterUnitOrOppUnit = enableTempTableFilterUnitOrOppUnit;
    }

    public List<String> getEffectTypes() {
        return this.effectTypes;
    }

    public void setEffectTypes(List<String> effectTypes) {
        this.effectTypes = effectTypes;
    }

    public List<GcOrgCacheVO> getOrgList() {
        return this.orgList;
    }

    public void setOrgList(List<GcOrgCacheVO> orgList) {
        this.orgList = orgList;
    }
}

