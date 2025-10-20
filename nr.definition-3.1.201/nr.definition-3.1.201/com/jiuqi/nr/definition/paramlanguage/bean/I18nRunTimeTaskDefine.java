/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.paramlanguage.bean;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.facade.PeriodSetting;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.util.StringUtils;
import java.util.Date;
import java.util.List;

public class I18nRunTimeTaskDefine
implements TaskDefine {
    private final TaskDefine taskDefine;
    private String title;

    public I18nRunTimeTaskDefine(TaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    @Override
    public String getDescription() {
        return this.taskDefine.getDescription();
    }

    @Override
    public String getMasterEntitiesKey() {
        return this.taskDefine.getMasterEntitiesKey();
    }

    @Override
    @Deprecated
    public int getMasterEntityCount() {
        return this.taskDefine.getMasterEntityCount();
    }

    @Override
    public String getMeasureUnit() {
        return this.taskDefine.getMeasureUnit();
    }

    @Override
    public PeriodType getPeriodType() {
        return this.taskDefine.getPeriodType();
    }

    @Override
    public String getFromPeriod() {
        return this.taskDefine.getFromPeriod();
    }

    @Override
    public String getToPeriod() {
        return this.taskDefine.getToPeriod();
    }

    @Override
    public int getTaskPeriodOffset() {
        return this.taskDefine.getTaskPeriodOffset();
    }

    @Override
    public String getTaskCode() {
        return this.taskDefine.getTaskCode();
    }

    @Override
    public String getTaskFilePrefix() {
        return this.taskDefine.getTaskFilePrefix();
    }

    @Override
    public String getGroupName() {
        return this.taskDefine.getGroupName();
    }

    @Override
    public boolean getBigDataChanged() {
        return this.taskDefine.getBigDataChanged();
    }

    @Override
    public String getTaskExtension() {
        return this.taskDefine.getTaskExtension();
    }

    @Override
    public TaskGatherType getTaskGatherType() {
        return this.taskDefine.getTaskGatherType();
    }

    @Override
    public FormulaSyntaxStyle getFormulaSyntaxStyle() {
        return this.taskDefine.getFormulaSyntaxStyle();
    }

    @Override
    public String getCommitEntitiesKey() {
        return this.taskDefine.getCommitEntitiesKey();
    }

    @Override
    @Deprecated
    public int getDueDateOffset() {
        return this.taskDefine.getDueDateOffset();
    }

    @Override
    public int getPeriodBeginOffset() {
        return this.taskDefine.getPeriodBeginOffset();
    }

    @Override
    public int getPeriodEndOffset() {
        return this.taskDefine.getPeriodEndOffset();
    }

    @Override
    public List<PeriodSetting> getPeriodSetting() {
        return this.taskDefine.getPeriodSetting();
    }

    @Override
    public TaskFlowsDefine getFlowsSetting() {
        return this.taskDefine.getFlowsSetting();
    }

    @Override
    public String getEntityViewsInEFDC() {
        return this.taskDefine.getEntityViewsInEFDC();
    }

    @Override
    public TaskType getTaskType() {
        return this.taskDefine.getTaskType();
    }

    @Override
    public String getCreateUserName() {
        return this.taskDefine.getCreateUserName();
    }

    @Override
    public String getCreateTime() {
        return this.taskDefine.getCreateTime();
    }

    @Override
    public boolean getEfdcSwitch() {
        return this.taskDefine.getEfdcSwitch();
    }

    @Override
    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        return this.taskDefine.getFillInAutomaticallyDue();
    }

    @Override
    public String getDataScheme() {
        return this.taskDefine.getDataScheme();
    }

    @Override
    public String getDw() {
        return this.taskDefine.getDw();
    }

    @Override
    public String getDateTime() {
        return this.taskDefine.getDateTime();
    }

    @Override
    public String getDims() {
        return this.taskDefine.getDims();
    }

    @Override
    public String getFilterExpression() {
        return this.taskDefine.getFilterExpression();
    }

    public String getKey() {
        return this.taskDefine.getKey();
    }

    public String getOrder() {
        return this.taskDefine.getOrder();
    }

    public String getVersion() {
        return this.taskDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.taskDefine.getOwnerLevelAndId();
    }

    public Date getUpdateTime() {
        return this.taskDefine.getUpdateTime();
    }

    public String getTitle() {
        return StringUtils.isEmpty((String)this.title) ? this.taskDefine.getTitle() : this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public FillDateType getFillingDateType() {
        return this.taskDefine.getFillingDateType();
    }

    @Override
    public int getFillingDateDays() {
        return this.taskDefine.getFillingDateDays();
    }

    @Override
    public String getFilterTemplate() {
        return this.taskDefine.getFilterTemplate();
    }
}

