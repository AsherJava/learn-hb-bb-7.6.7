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
import com.jiuqi.nr.definition.facade.PeriodSetting;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import java.util.List;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface FormSchemeDefine
extends IMetaItem {
    public String getTaskKey();

    public String getFormSchemeCode();

    public PeriodType getPeriodType();

    public String getFromPeriod();

    public String getToPeriod();

    public String getDescription();

    public String getMasterEntitiesKey();

    public String getTaskPrefix();

    public String getFilePrefix();

    public TaskFlowsDefine getFlowsSetting();

    public int getPeriodOffset();

    @Deprecated
    public int getDueDateOffset();

    public List<PeriodSetting> getPeriodSetting();

    public String getMeasureUnit();

    public FillInAutomaticallyDue getFillInAutomaticallyDue();

    public String getDw();

    public String getDateTime();

    public String getDims();

    public String getFilterExpression();
}

