/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.paramlanguage.bean;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.PeriodSetting;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.util.StringUtils;
import java.util.Date;
import java.util.List;

public class I18nRunTimeFormSchemeDefine
implements FormSchemeDefine {
    private final FormSchemeDefine formSchemeDefine;
    private String title;

    public I18nRunTimeFormSchemeDefine(FormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
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
        return this.formSchemeDefine.getPeriodType();
    }

    @Override
    public String getFromPeriod() {
        return this.formSchemeDefine.getFromPeriod();
    }

    @Override
    public String getToPeriod() {
        return this.formSchemeDefine.getToPeriod();
    }

    @Override
    public String getDescription() {
        return this.formSchemeDefine.getDescription();
    }

    @Override
    public String getMasterEntitiesKey() {
        return this.formSchemeDefine.getMasterEntitiesKey();
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
        return this.formSchemeDefine.getFlowsSetting();
    }

    @Override
    public int getPeriodOffset() {
        return this.formSchemeDefine.getPeriodOffset();
    }

    @Override
    @Deprecated
    public int getDueDateOffset() {
        return this.formSchemeDefine.getDueDateOffset();
    }

    @Override
    public List<PeriodSetting> getPeriodSetting() {
        return this.formSchemeDefine.getPeriodSetting();
    }

    @Override
    public String getMeasureUnit() {
        return this.formSchemeDefine.getMeasureUnit();
    }

    @Override
    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        return this.formSchemeDefine.getFillInAutomaticallyDue();
    }

    @Override
    public String getDw() {
        return this.formSchemeDefine.getDw();
    }

    @Override
    public String getDateTime() {
        return this.formSchemeDefine.getDateTime();
    }

    @Override
    public String getDims() {
        return this.formSchemeDefine.getDims();
    }

    @Override
    public String getFilterExpression() {
        return this.formSchemeDefine.getFilterExpression();
    }

    public String getKey() {
        return this.formSchemeDefine.getKey();
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

    public String getTitle() {
        return StringUtils.isEmpty((String)this.title) ? this.formSchemeDefine.getTitle() : this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

