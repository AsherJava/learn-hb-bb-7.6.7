/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.facade.PeriodSetting;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskDefineImpl;
import com.jiuqi.nr.definition.service.ITaskFlowExtendService;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class TaskDTO
implements TaskDefine {
    private final TaskDefine define;
    private Function<String, TaskFlowsDefine> oldFlowsGetter;
    private final ITaskFlowExtendService service;

    public TaskDTO(TaskDefine define, Function<String, TaskFlowsDefine> oldFlowsGetter, ITaskFlowExtendService service) {
        this.define = define;
        this.oldFlowsGetter = oldFlowsGetter;
        this.service = service;
    }

    @Override
    public String getDescription() {
        return this.define.getDescription();
    }

    @Override
    public String getMasterEntitiesKey() {
        return this.define.getMasterEntitiesKey();
    }

    @Override
    public int getMasterEntityCount() {
        return this.define.getMasterEntityCount();
    }

    @Override
    public String getMeasureUnit() {
        return this.define.getMeasureUnit();
    }

    @Override
    public PeriodType getPeriodType() {
        return this.define.getPeriodType();
    }

    @Override
    public String getFromPeriod() {
        return this.define.getFromPeriod();
    }

    @Override
    public String getToPeriod() {
        return this.define.getToPeriod();
    }

    @Override
    public int getTaskPeriodOffset() {
        return this.define.getTaskPeriodOffset();
    }

    @Override
    public String getTaskCode() {
        return this.define.getTaskCode();
    }

    @Override
    public String getTaskFilePrefix() {
        return this.define.getTaskFilePrefix();
    }

    @Override
    public String getGroupName() {
        return this.define.getGroupName();
    }

    @Override
    public boolean getBigDataChanged() {
        return this.define.getBigDataChanged();
    }

    @Override
    public String getTaskExtension() {
        return this.define.getTaskExtension();
    }

    @Override
    public TaskGatherType getTaskGatherType() {
        return this.define.getTaskGatherType();
    }

    @Override
    public FormulaSyntaxStyle getFormulaSyntaxStyle() {
        return this.define.getFormulaSyntaxStyle();
    }

    @Override
    public String getCommitEntitiesKey() {
        return this.define.getCommitEntitiesKey();
    }

    @Override
    public int getDueDateOffset() {
        return this.define.getDueDateOffset();
    }

    @Override
    public int getPeriodBeginOffset() {
        return this.define.getPeriodBeginOffset();
    }

    @Override
    public int getPeriodEndOffset() {
        return this.define.getPeriodEndOffset();
    }

    @Override
    public List<PeriodSetting> getPeriodSetting() {
        return this.define.getPeriodSetting();
    }

    @Override
    public TaskFlowsDefine getFlowsSetting() {
        if (null == this.define.getFlowsSetting()) {
            TaskFlowsDefine flowsDefine = this.oldFlowsGetter.apply(this.define.getKey());
            ((RunTimeTaskDefineImpl)this.define).setFlowsSetting(flowsDefine);
        }
        if (null != this.service) {
            return this.service.getFlowsSetting(this.define.getKey(), this.define.getFlowsSetting());
        }
        return this.define.getFlowsSetting();
    }

    @Override
    public String getEntityViewsInEFDC() {
        return this.define.getEntityViewsInEFDC();
    }

    @Override
    public TaskType getTaskType() {
        return this.define.getTaskType();
    }

    @Override
    public String getCreateUserName() {
        return this.define.getCreateUserName();
    }

    @Override
    public String getCreateTime() {
        return this.define.getCreateTime();
    }

    @Override
    public boolean getEfdcSwitch() {
        return this.define.getEfdcSwitch();
    }

    @Override
    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        if (null != this.service) {
            return this.service.getFillInAutomaticallyDue(this.define.getKey(), this.define.getFillInAutomaticallyDue());
        }
        return this.define.getFillInAutomaticallyDue();
    }

    @Override
    public String getDataScheme() {
        return this.define.getDataScheme();
    }

    @Override
    public String getDw() {
        return this.define.getDw();
    }

    @Override
    public String getDateTime() {
        return this.define.getDateTime();
    }

    @Override
    public String getDims() {
        return this.define.getDims();
    }

    @Override
    public String getFilterExpression() {
        return this.define.getFilterExpression();
    }

    @Override
    public FillDateType getFillingDateType() {
        if (null != this.service) {
            return this.service.getFillingDateType(this.define.getKey(), this.define.getFillingDateType());
        }
        return this.define.getFillingDateType();
    }

    @Override
    public int getFillingDateDays() {
        if (null != this.service) {
            return this.service.getFillingDateDays(this.define.getKey(), this.define.getFillingDateDays());
        }
        return this.define.getFillingDateDays();
    }

    @Override
    public String getFilterTemplate() {
        return this.define.getFilterTemplate();
    }

    public Date getUpdateTime() {
        return this.define.getUpdateTime();
    }

    public String getKey() {
        return this.define.getKey();
    }

    public String getTitle() {
        return this.define.getTitle();
    }

    public String getOrder() {
        return this.define.getOrder();
    }

    public String getVersion() {
        return this.define.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.define.getOwnerLevelAndId();
    }
}

