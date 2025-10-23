/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.PeriodMatchingType
 *  com.jiuqi.nr.definition.common.TaskLinkExpressionType
 *  com.jiuqi.nr.definition.common.TaskLinkMatchingType
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 */
package com.jiuqi.nr.zbselector.bean.impl;

import com.jiuqi.nr.definition.common.PeriodMatchingType;
import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import java.util.Date;

public class TaskLinkImpl {
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
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
    private PeriodMatchingType configuration;
    private int isHidden;
    private String specified;
    private String theoffset;
    private String beginTime;
    private String endTime;
    private TaskLinkMatchingType matchingType = TaskLinkMatchingType.MATCHING_TYPE_PRIMARYKEY;
    private TaskLinkExpressionType expressionType = TaskLinkExpressionType.EQUALS;
    private String showTitle;
    private String id;

    public TaskLinkImpl() {
    }

    public TaskLinkImpl(TaskLinkDefine taskLinkDefine, String relatedTaskKey) {
        this.setKey(taskLinkDefine.getKey());
        this.setTitle(taskLinkDefine.getTitle());
        this.setOrder(taskLinkDefine.getOrder());
        this.setVersion(taskLinkDefine.getVersion());
        this.setOwnerLevelAndId(taskLinkDefine.getOwnerLevelAndId());
        this.setUpdateTime(taskLinkDefine.getUpdateTime());
        this.setDescription(taskLinkDefine.getDescription());
        this.setLinkAlias(taskLinkDefine.getLinkAlias());
        this.setCurrentFormSchemeKey(taskLinkDefine.getCurrentFormSchemeKey());
        this.setRelatedTaskKey(taskLinkDefine.getRelatedTaskKey());
        this.setRelatedFormSchemeKey(taskLinkDefine.getRelatedFormSchemeKey());
        this.setRelatedTaskCode(taskLinkDefine.getRelatedTaskCode());
        this.setPeriodOffset(taskLinkDefine.getPeriodOffset());
        this.setCurrentFormula(taskLinkDefine.getCurrentFormula());
        this.setRelatedFormula(taskLinkDefine.getRelatedFormula());
        this.setMatching(taskLinkDefine.getMatching());
        this.setConfiguration(taskLinkDefine.getConfiguration());
        this.setIsHidden(taskLinkDefine.getIsHidden());
        this.setSpecified(taskLinkDefine.getSpecified());
        this.setTheoffset(taskLinkDefine.getTheoffset());
        this.setBeginTime(taskLinkDefine.getBeginTime());
        this.setEndTime(taskLinkDefine.getEndTime());
        this.setMatchingType(taskLinkDefine.getMatchingType());
        this.setExpressionType(taskLinkDefine.getExpressionType());
        this.setRelatedTaskKey(relatedTaskKey);
        this.setShowTitle(taskLinkDefine.getDescription() + "@" + taskLinkDefine.getLinkAlias());
        this.setId(relatedTaskKey + "@" + taskLinkDefine.getRelatedFormSchemeKey() + "@" + taskLinkDefine.getLinkAlias());
    }

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

    public String getCurrentFormula() {
        return this.currentTaskFormula;
    }

    public void setCurrentFormula(String currentTaskFormula) {
        this.currentTaskFormula = currentTaskFormula;
    }

    public String getRelatedFormula() {
        return this.relatedTaskFormula;
    }

    public void setRelatedFormula(String relatedTaskFormula) {
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

    public String getShowTitle() {
        return this.showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

