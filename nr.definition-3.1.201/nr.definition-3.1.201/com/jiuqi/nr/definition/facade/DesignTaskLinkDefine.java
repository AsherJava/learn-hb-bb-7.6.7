/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.common.PeriodMatchingType;
import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import java.util.Date;
import java.util.List;

public interface DesignTaskLinkDefine
extends TaskLinkDefine {
    public void setUpdateTime(Date var1);

    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setDescription(String var1);

    public void setLinkAlias(String var1);

    public void setCurrentFormSchemeKey(String var1);

    public void setRelatedTaskKey(String var1);

    public void setRelatedFormSchemeKey(String var1);

    public void setRelatedTaskCode(String var1);

    public void setPeriodOffset(String var1);

    public void setCurrentTaskFormula(String var1);

    public void setRelatedTaskFormula(String var1);

    public void setMatching(String var1);

    public void setConfiguration(PeriodMatchingType var1);

    public void setIsHidden(int var1);

    public void setSpecified(String var1);

    public void setTheoffset(String var1);

    public void setBeginTime(String var1);

    public void setEndTime(String var1);

    public void setMatchingType(TaskLinkMatchingType var1);

    public void setExpressionType(TaskLinkExpressionType var1);

    public void setDimMappingRule(String var1);

    public void setOrgMappingRule(List<TaskLinkOrgMappingRule> var1);
}

