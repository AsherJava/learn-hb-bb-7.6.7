/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.PeriodSetting;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller.DesignTimeViewController;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.log.CompareFieldType;
import com.jiuqi.nr.definition.log.ComparePropertyAble;
import com.jiuqi.nr.definition.log.ComparePropertyAnno;
import java.util.Date;
import java.util.List;

@ComparePropertyAnno.CompareType
public class DesignFormSchemeDefineGetterImpl
implements FormSchemeDefine,
ComparePropertyAble {
    private static final long serialVersionUID = 1L;
    private DesignFormSchemeDefine designFormSchemeDefine;
    private DesignTaskDefine designTaskDefine;

    public DesignFormSchemeDefineGetterImpl(DesignFormSchemeDefine designFormSchemeDefine) {
        if (designFormSchemeDefine != null) {
            this.designFormSchemeDefine = designFormSchemeDefine;
            IDesignTimeViewController designTimeViewController = BeanUtil.getBean(DesignTimeViewController.class);
            this.designTaskDefine = designTimeViewController.queryTaskDefine(designFormSchemeDefine.getTaskKey());
        }
    }

    public DesignFormSchemeDefineGetterImpl(DesignFormSchemeDefine designFormSchemeDefine, DesignTaskDefine taskDefine) {
        if (designFormSchemeDefine != null) {
            this.designFormSchemeDefine = designFormSchemeDefine;
            this.designTaskDefine = taskDefine;
        }
    }

    @Override
    public String getDw() {
        return this.designTaskDefine.getDw();
    }

    @Override
    public String getDateTime() {
        return this.designTaskDefine.getDateTime();
    }

    @Override
    public String getDims() {
        return this.designTaskDefine.getDims();
    }

    @Override
    @ComparePropertyAnno.CompareMethod(title="\u4efb\u52a1", type=CompareFieldType.TASK, order=1, isDescription=true)
    public String getTaskKey() {
        return this.designFormSchemeDefine.getTaskKey();
    }

    @Override
    @ComparePropertyAnno.CompareMethod(title="\u65b9\u6848\u6807\u8bc6", order=3, isDescription=true)
    public String getFormSchemeCode() {
        return this.designFormSchemeDefine.getFormSchemeCode();
    }

    @Override
    @ComparePropertyAnno.CompareMethod(title="\u5468\u671f\u7c7b\u578b")
    public PeriodType getPeriodType() {
        return this.designTaskDefine.getPeriodType();
    }

    @Override
    @ComparePropertyAnno.CompareMethod(title="\u65b9\u6848\u8d77\u59cb\u65f6\u95f4")
    public String getFromPeriod() {
        return this.designTaskDefine.getFromPeriod();
    }

    @Override
    @ComparePropertyAnno.CompareMethod(title="\u65b9\u6848\u7ec8\u6b62\u65f6\u95f4")
    public String getToPeriod() {
        return this.designTaskDefine.getToPeriod();
    }

    @Override
    public String getDescription() {
        return this.designFormSchemeDefine.getDescription();
    }

    @Override
    @ComparePropertyAnno.CompareMethod(title="\u65b9\u6848\u4e3b\u4f53", type=CompareFieldType.VIEW)
    public String getMasterEntitiesKey() {
        return this.designTaskDefine.getMasterEntitiesKey();
    }

    @Override
    public String getTaskPrefix() {
        return this.designFormSchemeDefine.getTaskPrefix();
    }

    @Override
    public String getFilePrefix() {
        return this.designFormSchemeDefine.getFilePrefix();
    }

    @Override
    public TaskFlowsDefine getFlowsSetting() {
        TaskFlowsDefine taskTaskFlowsDefine = null;
        if (this.designTaskDefine != null) {
            taskTaskFlowsDefine = this.designTaskDefine.getFlowsSetting();
            return taskTaskFlowsDefine;
        }
        return taskTaskFlowsDefine;
    }

    @Override
    public int getPeriodOffset() {
        return this.designTaskDefine.getTaskPeriodOffset();
    }

    @Override
    public int getDueDateOffset() {
        return this.designTaskDefine.getDueDateOffset();
    }

    @Override
    public List<PeriodSetting> getPeriodSetting() {
        return this.designFormSchemeDefine.getPeriodSetting();
    }

    @Override
    public String getMeasureUnit() {
        return this.designTaskDefine.getMeasureUnit();
    }

    @Override
    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        return this.designTaskDefine.getFillInAutomaticallyDue();
    }

    public String getKey() {
        return this.designFormSchemeDefine.getKey();
    }

    @ComparePropertyAnno.CompareMethod(title="\u65b9\u6848\u6807\u9898", order=2, isDescription=true)
    public String getTitle() {
        return this.designFormSchemeDefine.getTitle();
    }

    public String getOrder() {
        return this.designFormSchemeDefine.getOrder();
    }

    public String getVersion() {
        return this.designFormSchemeDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.designFormSchemeDefine.getOwnerLevelAndId();
    }

    public Date getUpdateTime() {
        return this.designFormSchemeDefine.getUpdateTime();
    }

    @Override
    public String getFilterExpression() {
        return this.designTaskDefine.getFilterExpression();
    }
}

