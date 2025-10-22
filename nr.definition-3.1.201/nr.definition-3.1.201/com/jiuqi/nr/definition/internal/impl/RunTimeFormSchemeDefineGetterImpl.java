/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.PeriodSetting;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.util.NrDefinitionBeanHelper;
import java.util.Date;
import java.util.List;

public class RunTimeFormSchemeDefineGetterImpl
implements FormSchemeDefine {
    private static final long serialVersionUID = 1L;
    private FormSchemeDefine formSchemeDefine;

    public RunTimeFormSchemeDefineGetterImpl(FormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
    }

    @Override
    public String getDw() {
        return this.getTaskDefine().getDw();
    }

    @Override
    public String getDateTime() {
        return this.getTaskDefine().getDateTime();
    }

    @Override
    public String getDims() {
        return this.getTaskDefine().getDims();
    }

    @Override
    public String getFilterExpression() {
        return this.getTaskDefine().getFilterExpression();
    }

    @Override
    public String getTaskKey() {
        return this.formSchemeDefine.getTaskKey();
    }

    @Override
    public String getFormSchemeCode() {
        return this.formSchemeDefine.getFormSchemeCode();
    }

    @Override
    public PeriodType getPeriodType() {
        return this.getTaskDefine().getPeriodType();
    }

    @Override
    public String getFromPeriod() {
        return this.getTaskDefine().getFromPeriod();
    }

    @Override
    public String getToPeriod() {
        return this.getTaskDefine().getToPeriod();
    }

    @Override
    public String getDescription() {
        return this.formSchemeDefine.getDescription();
    }

    @Override
    public String getMasterEntitiesKey() {
        return this.getTaskDefine().getMasterEntitiesKey();
    }

    @Override
    public String getTaskPrefix() {
        return this.formSchemeDefine.getTaskPrefix();
    }

    @Override
    public String getFilePrefix() {
        return this.formSchemeDefine.getFilePrefix();
    }

    @Override
    public TaskFlowsDefine getFlowsSetting() {
        return this.getTaskDefine().getFlowsSetting();
    }

    @Override
    public int getPeriodOffset() {
        return this.getTaskDefine().getTaskPeriodOffset();
    }

    @Override
    public int getDueDateOffset() {
        return this.getTaskDefine().getDueDateOffset();
    }

    @Override
    public List<PeriodSetting> getPeriodSetting() {
        return this.formSchemeDefine.getPeriodSetting();
    }

    @Override
    public String getMeasureUnit() {
        return this.getTaskDefine().getMeasureUnit();
    }

    @Override
    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        return this.getTaskDefine().getFillInAutomaticallyDue();
    }

    public String getKey() {
        return this.formSchemeDefine.getKey();
    }

    public String getTitle() {
        return this.formSchemeDefine.getTitle();
    }

    public String getOrder() {
        return this.formSchemeDefine.getOrder();
    }

    public String getVersion() {
        return this.formSchemeDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.formSchemeDefine.getOwnerLevelAndId();
    }

    public Date getUpdateTime() {
        return this.formSchemeDefine.getUpdateTime();
    }

    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public void setFormSchemeDefine(FormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
    }

    private TaskDefine getTaskDefine() {
        return NrDefinitionBeanHelper.getRunTimeViewController().getTask(this.formSchemeDefine.getTaskKey());
    }
}

