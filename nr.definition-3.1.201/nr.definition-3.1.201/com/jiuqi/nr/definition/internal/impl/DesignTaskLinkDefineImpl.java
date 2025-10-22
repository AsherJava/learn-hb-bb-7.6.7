/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.common.PeriodMatchingType;
import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskLinkEntityMappingRule;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@DBAnno.DBTable(dbTable="NR_PARAM_TASKLINK_DES")
@DBAnno.DBLink(linkWith=DesignTaskGroupLink.class, linkField="taskKey", field="key")
public class DesignTaskLinkDefineImpl
implements DesignTaskLinkDefine {
    private static final long serialVersionUID = -5998780147623094394L;
    @DBAnno.DBField(dbField="tr_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="tr_title")
    private String title;
    @DBAnno.DBField(dbField="tr_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="tr_version")
    private String version;
    @DBAnno.DBField(dbField="tr_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="tr_updatetime", tranWith="transTimeStamp", autoDate=true, dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;
    @DBAnno.DBField(dbField="tr_desc")
    private String description;
    @DBAnno.DBField(dbField="tr_link_alias")
    private String linkAlias;
    @DBAnno.DBField(dbField="tr_current_form_scheme_key")
    private String currentFormSchemeKey;
    @DBAnno.DBField(dbField="tr_related_task_key")
    private String relatedTaskKey;
    @DBAnno.DBField(dbField="tr_related_form_scheme_key")
    private String relatedFormSchemeKey;
    @DBAnno.DBField(dbField="tr_related_task_code")
    private String relatedTaskCode;
    @DBAnno.DBField(dbField="tr_period_offset")
    private String periodOffset;
    @DBAnno.DBField(dbField="tr_current_formula")
    private String currentTaskFormula;
    @DBAnno.DBField(dbField="tr_related_formula")
    private String relatedTaskFormula;
    @DBAnno.DBField(dbField="tr_related_dims")
    private String relatedDims;
    @DBAnno.DBField(dbField="tr_matching")
    private String matching;
    @DBAnno.DBField(dbField="tr_configuration", tranWith="transPeriodMatchingType", dbType=Integer.class, appType=PeriodMatchingType.class)
    private PeriodMatchingType configuration = PeriodMatchingType.PERIOD_TYPE_ALL;
    @DBAnno.DBField(dbField="tr_ishidden")
    private int isHidden = 1;
    @DBAnno.DBField(dbField="tr_specified")
    private String specified;
    @DBAnno.DBField(dbField="tr_theoffset")
    private String theoffset;
    @DBAnno.DBField(dbField="tr_begin_time")
    private String beginTime;
    @DBAnno.DBField(dbField="tr_end_time")
    private String endTime;
    @DBAnno.DBField(dbField="tr_matchingtype", tranWith="transTaskLinkMatchingType", dbType=Integer.class, appType=TaskLinkMatchingType.class)
    private TaskLinkMatchingType matchingType = TaskLinkMatchingType.MATCHING_TYPE_PRIMARYKEY;
    @DBAnno.DBField(dbField="tr_expressiontype", tranWith="transTaskLinkExpressionType", dbType=Integer.class, appType=TaskLinkExpressionType.class)
    private TaskLinkExpressionType expressionType = TaskLinkExpressionType.EQUALS;
    private List<TaskLinkOrgMappingRule> orgMappingRule;

    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getRelatedTaskKey() {
        return this.relatedTaskKey;
    }

    @Override
    public String getPeriodOffset() {
        return this.periodOffset;
    }

    @Override
    public void setRelatedTaskKey(String relatedTaskKey) {
        this.relatedTaskKey = relatedTaskKey;
    }

    @Override
    public void setPeriodOffset(String periodOffset) {
        this.periodOffset = periodOffset;
    }

    @Override
    public void setCurrentTaskFormula(String currentTaskFormula) {
        this.currentTaskFormula = currentTaskFormula;
    }

    @Override
    public void setRelatedTaskFormula(String relatedTaskFormula) {
        this.relatedTaskFormula = relatedTaskFormula;
    }

    @Override
    public String getRelatedTaskCode() {
        return this.relatedTaskCode;
    }

    @Override
    public void setRelatedTaskCode(String relatedTaskCode) {
        this.relatedTaskCode = relatedTaskCode;
    }

    @Override
    public String getLinkAlias() {
        return this.linkAlias;
    }

    @Override
    public String getCurrentFormSchemeKey() {
        return this.currentFormSchemeKey;
    }

    @Override
    public String getRelatedFormSchemeKey() {
        return this.relatedFormSchemeKey;
    }

    @Override
    public String getCurrentFormula() {
        return this.currentTaskFormula;
    }

    @Override
    public String getRelatedFormula() {
        return this.relatedTaskFormula;
    }

    @Override
    public void setLinkAlias(String number) {
        this.linkAlias = number;
    }

    @Override
    public void setCurrentFormSchemeKey(String currentFormSchemeKey) {
        this.currentFormSchemeKey = currentFormSchemeKey;
    }

    @Override
    public void setRelatedFormSchemeKey(String relatedFormSchemeKey) {
        this.relatedFormSchemeKey = relatedFormSchemeKey;
    }

    @Override
    public String getMatching() {
        return this.matching;
    }

    @Override
    public PeriodMatchingType getConfiguration() {
        return this.configuration;
    }

    @Override
    public void setMatching(String matching) {
        this.matching = matching;
    }

    @Override
    public void setConfiguration(PeriodMatchingType configuration) {
        this.configuration = configuration;
    }

    @Override
    public void setIsHidden(int isHidden) {
        this.isHidden = isHidden;
    }

    @Override
    public int getIsHidden() {
        return this.isHidden;
    }

    @Override
    public String getSpecified() {
        return this.specified;
    }

    @Override
    public String getTheoffset() {
        return this.theoffset;
    }

    @Override
    public String getBeginTime() {
        return this.beginTime;
    }

    @Override
    public String getEndTime() {
        return this.endTime;
    }

    @Override
    public void setSpecified(String specified) {
        this.specified = specified;
    }

    @Override
    public void setTheoffset(String theoffset) {
        this.theoffset = this.specified;
    }

    @Override
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    @Override
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public TaskLinkMatchingType getMatchingType() {
        return this.matchingType;
    }

    @Override
    public void setMatchingType(TaskLinkMatchingType matchingType) {
        this.matchingType = matchingType;
    }

    @Override
    public void setExpressionType(TaskLinkExpressionType expressionType) {
        this.expressionType = expressionType;
    }

    @Override
    public void setDimMappingRule(String dimMappingRule) {
        this.relatedDims = dimMappingRule;
    }

    @Override
    public String getRelatedDims() {
        return this.relatedDims;
    }

    @Override
    public Map<String, String> getDimMapping() {
        return null;
    }

    @Override
    public void setOrgMappingRule(List<TaskLinkOrgMappingRule> orgMappingRule) {
        this.orgMappingRule = orgMappingRule;
    }

    @Override
    public TaskLinkExpressionType getExpressionType() {
        return this.expressionType;
    }

    @Override
    public List<TaskLinkOrgMappingRule> getOrgMappingRules() {
        return this.orgMappingRule;
    }

    @Override
    public TaskLinkEntityMappingRule getRelatedEntity(String currentEntity) {
        return null;
    }
}

