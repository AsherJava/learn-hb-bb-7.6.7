/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.definition.facade;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.facade.PeriodSetting;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import java.util.List;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface TaskDefine
extends IMetaItem {
    public String getDescription();

    public String getMasterEntitiesKey();

    @Deprecated
    public int getMasterEntityCount();

    public String getMeasureUnit();

    public PeriodType getPeriodType();

    public String getFromPeriod();

    public String getToPeriod();

    public int getTaskPeriodOffset();

    public String getTaskCode();

    public String getTaskFilePrefix();

    @Deprecated
    public String getGroupName();

    @Deprecated
    public boolean getBigDataChanged();

    @Deprecated
    public String getTaskExtension();

    public TaskGatherType getTaskGatherType();

    public FormulaSyntaxStyle getFormulaSyntaxStyle();

    @Deprecated
    public String getCommitEntitiesKey();

    @Deprecated
    public int getDueDateOffset();

    @Deprecated
    public int getPeriodBeginOffset();

    @Deprecated
    public int getPeriodEndOffset();

    @Deprecated
    public List<PeriodSetting> getPeriodSetting();

    public TaskFlowsDefine getFlowsSetting();

    public String getEntityViewsInEFDC();

    public TaskType getTaskType();

    public String getCreateUserName();

    public String getCreateTime();

    public boolean getEfdcSwitch();

    public FillInAutomaticallyDue getFillInAutomaticallyDue();

    public String getDataScheme();

    public String getDw();

    public String getDateTime();

    public String getDims();

    public String getFilterExpression();

    public FillDateType getFillingDateType();

    public int getFillingDateDays();

    public String getFilterTemplate();
}

