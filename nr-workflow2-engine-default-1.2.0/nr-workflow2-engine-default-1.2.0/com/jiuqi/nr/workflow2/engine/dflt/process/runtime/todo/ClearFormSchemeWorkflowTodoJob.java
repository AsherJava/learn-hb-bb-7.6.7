/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.defaultlog.Logger
 *  com.jiuqi.bi.monitor.IProgressMonitor
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoBeanUtils;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoJob;

public class ClearFormSchemeWorkflowTodoJob {
    public static TodoJob createJob(String formSchemeKey) {
        return TodoJob.createTodoJob(TodoJob.OperationType.CLEAR_FORMSCHEME_WORKFLOW, formSchemeKey);
    }

    public void execute(JobContext context) throws JobExecutionException {
        String formSchemeKey = ClearFormSchemeWorkflowTodoJob.getParam(context);
        Logger logger = context.getDefaultLogger();
        logger.info("\u63a5\u6536\u53c2\u6570\u5b8c\u6210\uff0c\u64cd\u4f5c\u7c7b\u578b\uff1a" + TodoJob.OperationType.CLEAR_FORMSCHEME_WORKFLOW.name() + "\uff0c\u62a5\u8868\u65b9\u6848\uff1a" + formSchemeKey);
        String taskName = "nr_wf2_todo";
        IProgressMonitor monitor = context.getMonitor();
        monitor.startTask("nr_wf2_todo", 1);
        TodoBeanUtils.getTodoManipulationService().deleteTodoMessageByFormSchemeKey(formSchemeKey);
        monitor.stepIn();
        monitor.finishTask("nr_wf2_todo");
    }

    private static String getParam(JobContext context) {
        return context.getParameterValue("OPT_INFO");
    }
}

