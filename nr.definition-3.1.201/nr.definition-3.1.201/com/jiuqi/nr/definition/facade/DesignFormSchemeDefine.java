/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.PeriodSetting;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.log.ComparePropertyAble;
import java.util.Date;
import java.util.List;

public interface DesignFormSchemeDefine
extends FormSchemeDefine,
ComparePropertyAble,
Cloneable {
    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setUpdateTime(Date var1);

    public void setTaskKey(String var1);

    public void setFormSchemeCode(String var1);

    public void setPeriodType(PeriodType var1);

    @Deprecated
    public void setFromPeriod(String var1);

    @Deprecated
    public void setToPeriod(String var1);

    public void setDescription(String var1);

    @Deprecated
    public void setMasterEntitiesKey(String var1);

    public void setTaskPrefix(String var1);

    public void setFilePrefix(String var1);

    public void setFlowsSetting(DesignTaskFlowsDefine var1);

    public void setPeriodOffset(int var1);

    @Deprecated
    public void setDueDateOffset(int var1);

    public void setPeriodSetting(List<PeriodSetting> var1);

    public void setMeasureUnit(String var1);

    public void setFillInAutomaticallyDue(FillInAutomaticallyDue var1);

    public void setDw(String var1);

    public void setDateTime(String var1);

    public void setDims(String var1);

    public void setFilterExpression(String var1);
}

