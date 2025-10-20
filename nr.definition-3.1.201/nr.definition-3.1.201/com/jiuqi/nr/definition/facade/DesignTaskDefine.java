/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.facade.PeriodSetting;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import java.util.Date;
import java.util.List;

public interface DesignTaskDefine
extends TaskDefine {
    public void setUpdateTime(Date var1);

    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    @Override
    public String getDescription();

    public void setDescription(String var1);

    public void setMasterEntitiesKey(String var1);

    @Deprecated
    public void setMasterEntityCount(int var1);

    public void setMeasureUnit(String var1);

    @Deprecated
    public void setPeriodType(PeriodType var1);

    public void setFromPeriod(String var1);

    public void setToPeriod(String var1);

    public void setTaskPeriodOffset(int var1);

    public void setTaskCode(String var1);

    public void setTaskFilePrefix(String var1);

    @Deprecated
    public void setGroupName(String var1);

    @Deprecated
    public void setBigDataChanged(boolean var1);

    @Deprecated
    public void setTaskExtension(String var1);

    public void setTaskGatherType(TaskGatherType var1);

    @Override
    public FormulaSyntaxStyle getFormulaSyntaxStyle();

    public void setFormulaSyntaxStyle(FormulaSyntaxStyle var1);

    @Deprecated
    public void setCommitEntitiesKey(String var1);

    @Deprecated
    public void setDueDateOffset(int var1);

    @Deprecated
    public void setPeriodBeginOffset(int var1);

    @Deprecated
    public void setPeriodEndOffset(int var1);

    @Deprecated
    public void setPeriodSetting(List<PeriodSetting> var1);

    public void setFlowsSetting(DesignTaskFlowsDefine var1);

    public void setEntityViewsInEFDC(String var1);

    public void setTaskType(TaskType var1);

    public void setCreateUserName(String var1);

    public void setCreateTime(String var1);

    public void setEfdcSwitch(boolean var1);

    public void setFillInAutomaticallyDue(FillInAutomaticallyDue var1);

    public void setDataScheme(String var1);

    public void setDw(String var1);

    public void setDateTime(String var1);

    public void setDims(String var1);

    public void setFilterExpression(String var1);

    public void setFillingDateType(FillDateType var1);

    public void setFillingDateDays(int var1);

    public void setFilterTemplate(String var1);
}

