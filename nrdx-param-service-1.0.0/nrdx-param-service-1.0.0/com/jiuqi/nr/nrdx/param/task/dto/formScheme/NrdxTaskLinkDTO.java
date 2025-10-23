/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.jiuqi.nr.definition.common.PeriodMatchingType
 *  com.jiuqi.nr.definition.common.TaskLinkExpressionType
 *  com.jiuqi.nr.definition.common.TaskLinkMatchingType
 *  com.jiuqi.nr.definition.facade.DesignTaskLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule
 */
package com.jiuqi.nr.nrdx.param.task.dto.formScheme;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.definition.common.PeriodMatchingType;
import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import java.util.Date;
import java.util.List;

public class NrdxTaskLinkDTO {
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String description;
    private String linkAlias;
    private String currentFormSchemeKey;
    private String relatedTaskKey;
    private String relatedFormSchemeKey;
    private String relatedTaskCode;
    private String periodOffset;
    private String currentTaskFormula;
    private String relatedTaskFormula;
    private String matching;
    private PeriodMatchingType configuration = PeriodMatchingType.PERIOD_TYPE_ALL;
    private int isHidden;
    private String specified;
    private String theoffset;
    private String beginTime;
    private String endTime;
    private TaskLinkMatchingType matchingType = TaskLinkMatchingType.MATCHING_TYPE_PRIMARYKEY;
    private TaskLinkExpressionType expressionType;
    private String relatedDims;
    private List<TaskLinkOrgMappingRule> orgMappingRule;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkAlias() {
        return this.linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    public String getCurrentFormSchemeKey() {
        return this.currentFormSchemeKey;
    }

    public void setCurrentFormSchemeKey(String currentFormSchemeKey) {
        this.currentFormSchemeKey = currentFormSchemeKey;
    }

    public String getRelatedTaskKey() {
        return this.relatedTaskKey;
    }

    public void setRelatedTaskKey(String relatedTaskKey) {
        this.relatedTaskKey = relatedTaskKey;
    }

    public String getRelatedFormSchemeKey() {
        return this.relatedFormSchemeKey;
    }

    public void setRelatedFormSchemeKey(String relatedFormSchemeKey) {
        this.relatedFormSchemeKey = relatedFormSchemeKey;
    }

    public String getRelatedTaskCode() {
        return this.relatedTaskCode;
    }

    public void setRelatedTaskCode(String relatedTaskCode) {
        this.relatedTaskCode = relatedTaskCode;
    }

    public String getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(String periodOffset) {
        this.periodOffset = periodOffset;
    }

    public String getCurrentTaskFormula() {
        return this.currentTaskFormula;
    }

    public void setCurrentTaskFormula(String currentTaskFormula) {
        this.currentTaskFormula = currentTaskFormula;
    }

    public String getRelatedTaskFormula() {
        return this.relatedTaskFormula;
    }

    public void setRelatedTaskFormula(String relatedTaskFormula) {
        this.relatedTaskFormula = relatedTaskFormula;
    }

    public String getMatching() {
        return this.matching;
    }

    public void setMatching(String matching) {
        this.matching = matching;
    }

    public PeriodMatchingType getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(PeriodMatchingType configuration) {
        this.configuration = configuration;
    }

    public int getIsHidden() {
        return this.isHidden;
    }

    public void setIsHidden(int isHidden) {
        this.isHidden = isHidden;
    }

    public String getSpecified() {
        return this.specified;
    }

    public void setSpecified(String specified) {
        this.specified = specified;
    }

    public String getTheoffset() {
        return this.theoffset;
    }

    public void setTheoffset(String theoffset) {
        this.theoffset = theoffset;
    }

    public String getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public TaskLinkMatchingType getMatchingType() {
        return this.matchingType;
    }

    public void setMatchingType(TaskLinkMatchingType matchingType) {
        this.matchingType = matchingType;
    }

    public TaskLinkExpressionType getExpressionType() {
        return this.expressionType;
    }

    public void setExpressionType(TaskLinkExpressionType expressionType) {
        this.expressionType = expressionType;
    }

    public String getRelatedDims() {
        return this.relatedDims;
    }

    public void setRelatedDims(String relatedDims) {
        this.relatedDims = relatedDims;
    }

    public List<TaskLinkOrgMappingRule> getOrgMappingRule() {
        return this.orgMappingRule;
    }

    public void setOrgMappingRule(List<TaskLinkOrgMappingRule> orgMappingRule) {
        this.orgMappingRule = orgMappingRule;
    }

    public static NrdxTaskLinkDTO valueOf(TaskLinkDefine designTaskLinkDefine) {
        if (designTaskLinkDefine == null) {
            return null;
        }
        NrdxTaskLinkDTO nrdxTaskLinkDTO = new NrdxTaskLinkDTO();
        nrdxTaskLinkDTO.setBeginTime(designTaskLinkDefine.getBeginTime());
        nrdxTaskLinkDTO.setConfiguration(designTaskLinkDefine.getConfiguration());
        nrdxTaskLinkDTO.setCurrentFormSchemeKey(designTaskLinkDefine.getCurrentFormSchemeKey());
        nrdxTaskLinkDTO.setDescription(designTaskLinkDefine.getDescription());
        nrdxTaskLinkDTO.setEndTime(designTaskLinkDefine.getEndTime());
        nrdxTaskLinkDTO.setIsHidden(designTaskLinkDefine.getIsHidden());
        nrdxTaskLinkDTO.setKey(designTaskLinkDefine.getKey());
        nrdxTaskLinkDTO.setLinkAlias(designTaskLinkDefine.getLinkAlias());
        nrdxTaskLinkDTO.setMatching(designTaskLinkDefine.getMatching());
        nrdxTaskLinkDTO.setMatchingType(designTaskLinkDefine.getMatchingType());
        nrdxTaskLinkDTO.setOrder(designTaskLinkDefine.getOrder());
        nrdxTaskLinkDTO.setOwnerLevelAndId(designTaskLinkDefine.getOwnerLevelAndId());
        nrdxTaskLinkDTO.setPeriodOffset(designTaskLinkDefine.getPeriodOffset());
        nrdxTaskLinkDTO.setCurrentTaskFormula(designTaskLinkDefine.getCurrentFormula());
        nrdxTaskLinkDTO.setRelatedTaskFormula(designTaskLinkDefine.getRelatedFormula());
        nrdxTaskLinkDTO.setRelatedFormSchemeKey(designTaskLinkDefine.getRelatedFormSchemeKey());
        nrdxTaskLinkDTO.setRelatedTaskCode(designTaskLinkDefine.getRelatedTaskCode());
        nrdxTaskLinkDTO.setRelatedTaskKey(designTaskLinkDefine.getRelatedTaskKey());
        nrdxTaskLinkDTO.setSpecified(designTaskLinkDefine.getSpecified());
        nrdxTaskLinkDTO.setTheoffset(designTaskLinkDefine.getTheoffset());
        nrdxTaskLinkDTO.setTitle(designTaskLinkDefine.getTitle());
        nrdxTaskLinkDTO.setUpdateTime(designTaskLinkDefine.getUpdateTime());
        nrdxTaskLinkDTO.setVersion(designTaskLinkDefine.getVersion());
        nrdxTaskLinkDTO.setExpressionType(designTaskLinkDefine.getExpressionType());
        nrdxTaskLinkDTO.setRelatedDims(designTaskLinkDefine.getRelatedDims());
        nrdxTaskLinkDTO.setOrgMappingRule(designTaskLinkDefine.getOrgMappingRules());
        return nrdxTaskLinkDTO;
    }

    public void value2Define(DesignTaskLinkDefine linkDefine) {
        linkDefine.setBeginTime(this.getBeginTime());
        linkDefine.setConfiguration(this.getConfiguration());
        linkDefine.setCurrentFormSchemeKey(this.getCurrentFormSchemeKey());
        linkDefine.setDescription(this.getDescription());
        linkDefine.setEndTime(this.getEndTime());
        linkDefine.setIsHidden(this.getIsHidden());
        linkDefine.setKey(this.getKey());
        linkDefine.setLinkAlias(this.getLinkAlias());
        linkDefine.setMatching(this.getMatching());
        linkDefine.setMatchingType(this.getMatchingType());
        linkDefine.setOrder(this.getOrder());
        linkDefine.setOwnerLevelAndId(this.getOwnerLevelAndId());
        linkDefine.setPeriodOffset(this.getPeriodOffset());
        linkDefine.setCurrentTaskFormula(this.getCurrentTaskFormula());
        linkDefine.setRelatedTaskFormula(this.getRelatedTaskFormula());
        linkDefine.setRelatedFormSchemeKey(this.getRelatedFormSchemeKey());
        linkDefine.setRelatedTaskCode(this.getRelatedTaskCode());
        linkDefine.setRelatedTaskKey(this.getRelatedTaskKey());
        linkDefine.setSpecified(this.getSpecified());
        linkDefine.setTheoffset(this.getTheoffset());
        linkDefine.setTitle(this.getTitle());
        linkDefine.setUpdateTime(this.getUpdateTime());
        linkDefine.setVersion(this.getVersion());
        linkDefine.setExpressionType(this.getExpressionType());
        linkDefine.setDimMappingRule(this.getRelatedDims());
        linkDefine.setOrgMappingRule(this.getOrgMappingRule());
    }
}

