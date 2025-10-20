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
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskLinkEntityMappingRule;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormSchemeDefineImpl;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@DBAnno.DBTable(dbTable="NR_PARAM_TASKLINK")
@DBAnno.DBLink(linkWith=RunTimeFormSchemeDefineImpl.class, linkField="key", field="currentFormSchemeKey")
public class RunTimeTaskLinkDefineImpl
implements TaskLinkDefine {
    public static final String TABLE_NAME = "NR_PARAM_TASKLINK";
    public static final String FIELD_NAME_KEY = "TR_KEY";
    public static final String FIELD_NAME_FORMSCHEME_KEY = "TR_CURRENT_FORM_SCHEME_KEY";
    private static final long serialVersionUID = 1762979408474597740L;
    @DBAnno.DBField(dbField="TR_KEY", isPk=true)
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
    @DBAnno.DBField(dbField="TR_CURRENT_FORM_SCHEME_KEY")
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
    private PeriodMatchingType configuration;
    @DBAnno.DBField(dbField="tr_ishidden")
    private int isHidden;
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

    public String getTitle() {
        return this.title;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public String getDescription() {
        return this.description;
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
    public String getRelatedTaskCode() {
        return this.relatedTaskCode;
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
    public String getMatching() {
        return this.matching;
    }

    @Override
    public PeriodMatchingType getConfiguration() {
        return this.configuration;
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
    public TaskLinkMatchingType getMatchingType() {
        return this.matchingType;
    }

    @Override
    public TaskLinkExpressionType getExpressionType() {
        return this.expressionType;
    }

    public void setOrgMappingRule(List<TaskLinkOrgMappingRule> orgMappingRule) {
        this.orgMappingRule = orgMappingRule;
    }

    @Override
    public List<TaskLinkOrgMappingRule> getOrgMappingRules() {
        return this.orgMappingRule;
    }

    @Override
    public String getRelatedDims() {
        return this.relatedDims;
    }

    @Override
    public Map<String, String> getDimMapping() {
        HashMap<String, String> dimMap = new HashMap<String, String>();
        if (!StringUtils.hasText(this.relatedDims)) {
            return dimMap;
        }
        String[] dimRuleObjs = this.relatedDims.split("/");
        for (int i = 0; i < dimRuleObjs.length; ++i) {
            String dimRuleObj = dimRuleObjs[i];
            String[] dimAndDimData = dimRuleObj.split(";");
            dimMap.put(dimAndDimData[0], dimAndDimData[1]);
        }
        return dimMap;
    }

    public void setRelatedDims(String relatedDims) {
        this.relatedDims = relatedDims;
    }

    @Override
    public TaskLinkEntityMappingRule getRelatedEntity(String currentEntity) {
        if (!StringUtils.hasText(currentEntity) || CollectionUtils.isEmpty(this.orgMappingRule)) {
            return null;
        }
        for (TaskLinkOrgMappingRule orgMapping : this.orgMappingRule) {
            if (!currentEntity.equals(orgMapping.getTargetEntity())) continue;
            return new TaskLinkEntityMappingRule(orgMapping, this.relatedDims);
        }
        return null;
    }
}

