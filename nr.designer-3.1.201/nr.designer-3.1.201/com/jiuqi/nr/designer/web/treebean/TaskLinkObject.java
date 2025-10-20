/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.treebean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.designer.web.rest.vo.TaskLinkDimMappingVO;
import com.jiuqi.nr.designer.web.rest.vo.TaskLinkOrgMappingVO;
import java.util.List;

public class TaskLinkObject {
    @JsonProperty(value="key")
    private String key;
    @JsonProperty(value="title")
    private String title = "";
    @JsonProperty(value="currentFormSchemeKey")
    private String currentFormSchemeKey;
    @JsonProperty(value="relatedTaskKey")
    private String relatedTaskKey;
    @JsonProperty(value="relatedFormSchemeKey")
    private String relatedFormSchemeKey;
    @JsonProperty(value="currentTaskFormula")
    private String currentTaskFormula;
    @JsonProperty(value="relatedTaskFormula")
    private String relatedTaskFormula;
    @JsonProperty(value="periodOffset")
    private String periodOffset;
    @JsonProperty(value="description")
    private String description;
    @JsonProperty(value="linkAlias")
    private String linkAlias;
    @JsonProperty(value="matching")
    private String matching;
    @JsonProperty(value="configuration")
    private Integer configuration;
    @JsonProperty(value="isHidden")
    private Integer isHidden;
    @JsonProperty(value="specified")
    private String specified;
    @JsonProperty(value="theoffset")
    private String theoffset;
    @JsonProperty(value="begintime")
    private String beginTime;
    @JsonProperty(value="multiplePeriod")
    private boolean multiplePeriod;
    @JsonProperty(value="matchingType")
    private Integer matchingType;
    @JsonProperty(value="order")
    private String order;
    @JsonProperty(value="expression")
    private int expressionType;
    @JsonProperty(value="endtime")
    private String endTime;
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="SameServeCode")
    private boolean sameServeCode;
    @JsonProperty(value="IsNew")
    private boolean IsNew;
    @JsonProperty(value="IsDirty")
    private boolean IsDirty;
    @JsonProperty(value="OrgMapping")
    private List<TaskLinkOrgMappingVO> orgMappingRules;
    @JsonProperty(value="DimMapping")
    private List<TaskLinkDimMappingVO> dimMappingVO;
    @JsonProperty(value="IsDeleted")
    private boolean IsDeleted;

    public List<TaskLinkDimMappingVO> getDimMappingVO() {
        return this.dimMappingVO;
    }

    public void setDimMappingVO(List<TaskLinkDimMappingVO> dimMappingVO) {
        this.dimMappingVO = dimMappingVO;
    }

    public void setOrgMappingRules(List<TaskLinkOrgMappingVO> orgMappingRules) {
        this.orgMappingRules = orgMappingRules;
    }

    public List<TaskLinkOrgMappingVO> getOrgMappingRules() {
        return this.orgMappingRules;
    }

    public boolean getMultiplePeriod() {
        return this.multiplePeriod;
    }

    public void setMultiplePeriod(boolean multiplePeriod) {
        this.multiplePeriod = multiplePeriod;
    }

    public Integer getMatchingType() {
        return this.matchingType;
    }

    public void setMatchingType(Integer matchingType) {
        this.matchingType = matchingType;
    }

    @JsonIgnore
    public boolean isIsNew() {
        return this.IsNew;
    }

    @JsonIgnore
    public void setIsNew(boolean isNew) {
        this.IsNew = isNew;
    }

    @JsonIgnore
    public boolean isIsDirty() {
        return this.IsDirty;
    }

    @JsonIgnore
    public void setIsDirty(boolean isDirty) {
        this.IsDirty = isDirty;
    }

    @JsonIgnore
    public boolean isIsDeleted() {
        return this.IsDeleted;
    }

    @JsonIgnore
    public void setIsDeleted(boolean isDeleted) {
        this.IsDeleted = isDeleted;
    }

    @JsonIgnore
    public String getTitle() {
        return this.title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public String getCurrentFormSchemeKey() {
        return this.currentFormSchemeKey;
    }

    @JsonIgnore
    public void setCurrentFormSchemeKey(String currentFormSchemeKey) {
        this.currentFormSchemeKey = currentFormSchemeKey;
    }

    @JsonIgnore
    public String getRelatedTaskKey() {
        return this.relatedTaskKey;
    }

    @JsonIgnore
    public void setRelatedTaskKey(String relatedTaskKey) {
        this.relatedTaskKey = relatedTaskKey;
    }

    @JsonIgnore
    public String getRelatedFormSchemeKey() {
        return this.relatedFormSchemeKey;
    }

    @JsonIgnore
    public void setRelatedFormSchemeKey(String relatedFormSchemeKey) {
        this.relatedFormSchemeKey = relatedFormSchemeKey;
    }

    @JsonIgnore
    public String getCurrentTaskFormula() {
        return this.currentTaskFormula;
    }

    @JsonIgnore
    public void setCurrentTaskFormula(String currentTaskFormula) {
        this.currentTaskFormula = currentTaskFormula;
    }

    @JsonIgnore
    public String getRelatedTaskFormula() {
        return this.relatedTaskFormula;
    }

    @JsonIgnore
    public void setRelatedTaskFormula(String relatedTaskFormula) {
        this.relatedTaskFormula = relatedTaskFormula;
    }

    @JsonIgnore
    public String getDescription() {
        return this.description;
    }

    @JsonIgnore
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public String getLinkAlias() {
        return this.linkAlias;
    }

    @JsonIgnore
    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    @JsonIgnore
    public String getKey() {
        return this.key;
    }

    @JsonIgnore
    public void setKey(String key) {
        this.key = key;
    }

    @JsonIgnore
    public String getPeriodOffset() {
        return this.periodOffset;
    }

    @JsonIgnore
    public void setPeriodOffset(String periodOffset) {
        this.periodOffset = periodOffset;
    }

    @JsonIgnore
    public String getMatching() {
        return this.matching;
    }

    @JsonIgnore
    public void setMatching(String matching) {
        this.matching = matching;
    }

    @JsonIgnore
    public Integer getConfiguration() {
        return this.configuration;
    }

    @JsonIgnore
    public void setConfiguration(Integer configuration) {
        this.configuration = configuration;
    }

    @JsonIgnore
    public Integer getIsHidden() {
        return this.isHidden;
    }

    @JsonIgnore
    public void setIsHidden(Integer isHidden) {
        this.isHidden = isHidden;
    }

    @JsonIgnore
    public String getSpecified() {
        return this.specified;
    }

    @JsonIgnore
    public void setSpecified(String specified) {
        this.specified = specified;
    }

    @JsonIgnore
    public String getTheoffset() {
        return this.theoffset;
    }

    @JsonIgnore
    public void setTheoffset(String theoffset) {
        this.theoffset = theoffset;
    }

    @JsonIgnore
    public String getBeginTime() {
        return this.beginTime;
    }

    @JsonIgnore
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    @JsonIgnore
    public String getEndTime() {
        return this.endTime;
    }

    @JsonIgnore
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @JsonIgnore
    public String getOrder() {
        return this.order;
    }

    @JsonIgnore
    public void setOrder(String order) {
        this.order = order;
    }

    @JsonIgnore
    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    @JsonIgnore
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @JsonIgnore
    public boolean getSameServeCode() {
        return this.sameServeCode;
    }

    @JsonIgnore
    public void setSameServeCode(boolean sameServeCode) {
        this.sameServeCode = sameServeCode;
    }

    public TaskLinkObject(String currentFormSchemeKey) {
        this.currentFormSchemeKey = currentFormSchemeKey;
        this.relatedTaskKey = null;
        this.relatedFormSchemeKey = null;
        this.currentTaskFormula = null;
        this.relatedTaskFormula = null;
        this.description = null;
        this.linkAlias = null;
        this.key = null;
        this.periodOffset = null;
        this.ownerLevelAndId = null;
    }

    public TaskLinkObject() {
        this.currentFormSchemeKey = null;
        this.relatedTaskKey = null;
        this.relatedFormSchemeKey = null;
        this.currentTaskFormula = null;
        this.relatedTaskFormula = null;
        this.description = null;
        this.linkAlias = null;
        this.key = null;
        this.periodOffset = null;
        this.ownerLevelAndId = null;
    }

    @JsonIgnore
    public void setExpressionType(int expressionType) {
        this.expressionType = expressionType;
    }

    @JsonIgnore
    public int getExpressionType() {
        return this.expressionType;
    }
}

