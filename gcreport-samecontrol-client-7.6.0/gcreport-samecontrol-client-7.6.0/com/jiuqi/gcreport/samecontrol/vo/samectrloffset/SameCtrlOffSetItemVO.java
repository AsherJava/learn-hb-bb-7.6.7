/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.samecontrol.vo.samectrloffset;

import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;

public class SameCtrlOffSetItemVO {
    private String id;
    private String taskId;
    private String schemeId;
    private String defaultPeriod;
    private String ruleId;
    private String ruleTitle;
    private String ruleParentId;
    private Integer elmMode;
    private String elmModeTitle;
    private String mRecid;
    private String orgType;
    private String inputUnitCode;
    private String inputUnitName;
    private String inputUnitParents;
    private String sameParentCode;
    private String changedParentCode;
    private String unitCode;
    private String unitTitle;
    private GcOrgCacheVO unitVo;
    private String oppUnitCode;
    private String oppUnitTitle;
    private GcOrgCacheVO oppUnitVo;
    private String subjectCode;
    private String subjectTitle;
    private ConsolidatedSubjectEO subjectVo;
    private Double offSetDebit;
    private String offsetDebitStr;
    private Double offSetCredit;
    private String offsetCreditStr;
    private Double diff;
    private String diffStr;
    private String offSetCurr;
    private Integer orient;
    private String srcOffsetGroupId;
    private Integer unitChangeYear;
    private String sameCtrlChgId;
    private String memo;
    private String srcId;
    private String effectType;
    private String sameCtrlSrcType;
    private String sameCtrlSrcTypeTitle;
    private String sourceMethodTtile;
    private int periodType;
    private String showTabCode;
    private int groupIndex;
    private int sortOrder;
    private int rowspan;
    private int index;
    private String adjust;

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }

    public String getSameCtrlSrcTypeTitle() {
        return this.sameCtrlSrcTypeTitle;
    }

    public void setSameCtrlSrcTypeTitle(String sameCtrlSrcTypeTitle) {
        this.sameCtrlSrcTypeTitle = sameCtrlSrcTypeTitle;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getElmMode() {
        return this.elmMode;
    }

    public void setElmMode(Integer elmMode) {
        this.elmMode = elmMode;
    }

    public String getElmModeTitle() {
        return this.elmModeTitle;
    }

    public void setElmModeTitle(String elmModeTitle) {
        this.elmModeTitle = elmModeTitle;
    }

    public String getmRecid() {
        return this.mRecid;
    }

    public void setmRecid(String mRecid) {
        this.mRecid = mRecid;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getInputUnitCode() {
        return this.inputUnitCode;
    }

    public void setInputUnitCode(String inputUnitCode) {
        this.inputUnitCode = inputUnitCode;
    }

    public String getInputUnitName() {
        return this.inputUnitName;
    }

    public void setInputUnitName(String inputUnitName) {
        this.inputUnitName = inputUnitName;
    }

    public String getSameParentCode() {
        return this.sameParentCode;
    }

    public void setSameParentCode(String sameParentCode) {
        this.sameParentCode = sameParentCode;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getOppUnitCode() {
        return this.oppUnitCode;
    }

    public void setOppUnitCode(String oppUnitCode) {
        this.oppUnitCode = oppUnitCode;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Double getOffSetDebit() {
        return this.offSetDebit;
    }

    public void setOffSetDebit(Double offSetDebit) {
        this.offSetDebit = offSetDebit;
    }

    public Double getOffSetCredit() {
        return this.offSetCredit;
    }

    public void setOffSetCredit(Double offSetCredit) {
        this.offSetCredit = offSetCredit;
    }

    public String getOffSetCurr() {
        return this.offSetCurr;
    }

    public void setOffSetCurr(String offSetCurr) {
        this.offSetCurr = offSetCurr;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public String getSrcOffsetGroupId() {
        return this.srcOffsetGroupId;
    }

    public void setSrcOffsetGroupId(String srcOffsetGroupId) {
        this.srcOffsetGroupId = srcOffsetGroupId;
    }

    public Integer getUnitChangeYear() {
        return this.unitChangeYear;
    }

    public void setUnitChangeYear(Integer unitChangeYear) {
        this.unitChangeYear = unitChangeYear;
    }

    public String getSameCtrlChgId() {
        return this.sameCtrlChgId;
    }

    public void setSameCtrlChgId(String sameCtrlChgId) {
        this.sameCtrlChgId = sameCtrlChgId;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getEffectType() {
        return this.effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public String getSameCtrlSrcType() {
        return this.sameCtrlSrcType;
    }

    public void setSameCtrlSrcType(String sameCtrlSrcType) {
        this.sameCtrlSrcType = sameCtrlSrcType;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public GcOrgCacheVO getUnitVo() {
        return this.unitVo;
    }

    public void setUnitVo(GcOrgCacheVO unitVo) {
        this.unitVo = unitVo;
    }

    public String getOppUnitTitle() {
        return this.oppUnitTitle;
    }

    public void setOppUnitTitle(String oppUnitTitle) {
        this.oppUnitTitle = oppUnitTitle;
    }

    public GcOrgCacheVO getOppUnitVo() {
        return this.oppUnitVo;
    }

    public void setOppUnitVo(GcOrgCacheVO oppUnitVo) {
        this.oppUnitVo = oppUnitVo;
    }

    public int getGroupIndex() {
        return this.groupIndex;
    }

    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    public int getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public String getShowTabCode() {
        return this.showTabCode;
    }

    public void setShowTabCode(String showTabCode) {
        this.showTabCode = showTabCode;
    }

    public String getRuleTitle() {
        return this.ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getInputUnitParents() {
        return this.inputUnitParents;
    }

    public void setInputUnitParents(String inputUnitParents) {
        this.inputUnitParents = inputUnitParents;
    }

    public String getSubjectTitle() {
        return this.subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public ConsolidatedSubjectEO getSubjectVo() {
        return this.subjectVo;
    }

    public void setSubjectVo(ConsolidatedSubjectEO subjectVo) {
        this.subjectVo = subjectVo;
    }

    public Double getDiff() {
        return this.diff;
    }

    public void setDiff(Double diff) {
        this.diff = diff;
    }

    public String getSourceMethodTtile() {
        return this.sourceMethodTtile;
    }

    public void setSourceMethodTtile(String sourceMethodTtile) {
        this.sourceMethodTtile = sourceMethodTtile;
    }

    public int getRowspan() {
        return this.rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getOffsetDebitStr() {
        return this.offsetDebitStr;
    }

    public void setOffsetDebitStr(String offsetDebitStr) {
        this.offsetDebitStr = offsetDebitStr;
    }

    public String getOffsetCreditStr() {
        return this.offsetCreditStr;
    }

    public void setOffsetCreditStr(String offsetCreditStr) {
        this.offsetCreditStr = offsetCreditStr;
    }

    public String getDiffStr() {
        return this.diffStr;
    }

    public void setDiffStr(String diffStr) {
        this.diffStr = diffStr;
    }

    public String getRuleParentId() {
        return this.ruleParentId;
    }

    public void setRuleParentId(String ruleParentId) {
        this.ruleParentId = ruleParentId;
    }

    public String getChangedParentCode() {
        return this.changedParentCode;
    }

    public void setChangedParentCode(String changedParentCode) {
        this.changedParentCode = changedParentCode;
    }
}

