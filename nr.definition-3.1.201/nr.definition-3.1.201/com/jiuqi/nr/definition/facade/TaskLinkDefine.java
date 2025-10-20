/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.common.PeriodMatchingType;
import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.facade.TaskLinkEntityMappingRule;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import java.util.List;
import java.util.Map;

public interface TaskLinkDefine
extends IMetaItem {
    public String getLinkAlias();

    public String getCurrentFormSchemeKey();

    public String getRelatedTaskKey();

    public String getRelatedFormSchemeKey();

    public String getRelatedTaskCode();

    public TaskLinkMatchingType getMatchingType();

    public String getPeriodOffset();

    public String getCurrentFormula();

    public String getRelatedFormula();

    public String getDescription();

    public String getMatching();

    public PeriodMatchingType getConfiguration();

    public int getIsHidden();

    public String getSpecified();

    public String getTheoffset();

    public String getBeginTime();

    public String getEndTime();

    public TaskLinkExpressionType getExpressionType();

    public List<TaskLinkOrgMappingRule> getOrgMappingRules();

    public String getRelatedDims();

    public Map<String, String> getDimMapping();

    public TaskLinkEntityMappingRule getRelatedEntity(String var1);
}

