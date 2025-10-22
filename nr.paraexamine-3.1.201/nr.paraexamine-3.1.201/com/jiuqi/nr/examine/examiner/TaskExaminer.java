/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.examine.examiner;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.examiner.AbstractExaminer;
import com.jiuqi.nr.examine.examiner.IErrorWriter;
import com.jiuqi.nr.examine.task.ExamineTask;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskExaminer
extends AbstractExaminer {
    private List<DesignTaskDefine> designTaskDefines = new ArrayList<DesignTaskDefine>();
    private boolean hasRange = false;

    public TaskExaminer(ExamineTask task) {
        super(task);
    }

    @Override
    protected void checkRefuse() {
        this.checkRuntimeRefuse();
    }

    @Override
    protected void checkQuote() {
        this.designTaskDefines.forEach(taskDefine -> this.checkFlowEntity((DesignTaskDefine)taskDefine));
    }

    private void checkFlowEntity(final DesignTaskDefine taskDefine) {
        String masterEntitiesKes = taskDefine.getDw().concat(";");
        if (StringUtils.isNotEmpty((String)taskDefine.getDims())) {
            masterEntitiesKes = masterEntitiesKes.concat(taskDefine.getDims()).concat(";");
        }
        masterEntitiesKes = masterEntitiesKes.concat(taskDefine.getDateTime());
        this.checkFlowEntity(taskDefine.getKey(), masterEntitiesKes, new IErrorWriter(){

            @Override
            public void quoteError() {
                TaskExaminer.this.writeQuotError(taskDefine.getKey(), taskDefine.getTitle(), ErrorType.TASK_QUOTE_FLOWVIEW_NONE);
            }

            @Override
            public void error() {
                TaskExaminer.this.writeQuotError(taskDefine.getKey(), taskDefine.getTitle(), ErrorType.TASK_QUOTE_FLOWVIEW_ERROR);
            }
        });
    }

    @Override
    protected void checkError() {
        for (DesignTaskDefine taskDefine : this.designTaskDefines) {
            this.checkPeriodEntity(taskDefine);
        }
    }

    private void checkPeriodEntity(DesignTaskDefine taskDefine) {
        IPeriodEntityAdapter periodEntityAdapter;
        if (StringUtils.isEmpty((String)taskDefine.getDateTime())) {
            this.writeError(taskDefine.getKey(), taskDefine.getTitle(), ErrorType.TASK_ERROR_PERIOD_LOST);
        }
        if (!(periodEntityAdapter = this.task.getEnv().getPeriodEntityAdapter()).isPeriodEntity(taskDefine.getDateTime())) {
            this.writeError(taskDefine.getKey(), taskDefine.getTitle(), ErrorType.TASK_ERROR_PERIOD_LOST);
        }
    }

    private void checkRuntimeRefuse() {
        if (this.hasRange) {
            return;
        }
        IRunTimeViewController nrRuntimeController = this.task.getEnv().getNrRuntimeController();
        List runTimeTaskList = nrRuntimeController.getAllTaskDefines();
        Set keySet = this.designTaskDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        for (TaskDefine taskDefine : runTimeTaskList) {
            if (!keySet.add(taskDefine.getKey())) continue;
            this.writeRefuse(taskDefine.getKey(), taskDefine.getTitle(), ErrorType.TASK_REFUSE_RUNTIME);
        }
    }

    @Override
    protected ParaType getParaType() {
        return ParaType.TASK;
    }

    @Override
    protected void init() {
        String paraKey = this.task.getKey();
        if (StringUtils.isEmpty((String)paraKey)) {
            this.designTaskDefines = this.task.getEnv().getNrDesignController().getAllTaskDefines();
        } else {
            DesignTaskDefine taskDefine = this.task.getEnv().getNrDesignController().queryTaskDefine(paraKey);
            this.designTaskDefines = new ArrayList<DesignTaskDefine>();
            if (taskDefine != null) {
                this.designTaskDefines.add(taskDefine);
            }
            this.hasRange = true;
        }
    }
}

