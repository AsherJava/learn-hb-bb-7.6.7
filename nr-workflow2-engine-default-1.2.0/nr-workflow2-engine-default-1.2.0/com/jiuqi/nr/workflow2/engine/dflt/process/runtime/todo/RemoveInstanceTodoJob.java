/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.defaultlog.Logger
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 *  com.jiuqi.nr.workflow2.todo.event.TodoDeleteEvent
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoBeanUtils;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoJob;
import com.jiuqi.nr.workflow2.todo.event.TodoDeleteEvent;
import java.util.List;
import org.springframework.context.ApplicationEvent;

public class RemoveInstanceTodoJob {
    public static TodoJob createJob(List<String> instanceIds) {
        return TodoJob.createTodoJob(TodoJob.OperationType.REMOVE_INSTANCE, instanceIds);
    }

    public void execute(JobContext context) throws JobExecutionException {
        List<String> todoRemoveParam = RemoveInstanceTodoJob.getParam(context);
        Logger logger = context.getDefaultLogger();
        logger.info("\u63a5\u6536\u53c2\u6570\u5b8c\u6210\uff0c\u64cd\u4f5c\u7c7b\u578b\uff1a" + TodoJob.OperationType.REMOVE_INSTANCE.name() + "\uff0c\u6d41\u7a0b\u5b9e\u4f8b\u6570\uff1a" + todoRemoveParam.size());
        if (todoRemoveParam.isEmpty()) {
            return;
        }
        IProgressMonitor monitor = context.getMonitor();
        String taskName = "nr_wf2_todo";
        monitor.startTask("nr_wf2_todo", new int[]{50, 50});
        if (!todoRemoveParam.isEmpty()) {
            logger.info("\u5f00\u59cb\u5220\u9664\u5f85\u529e");
            TodoBeanUtils.getTodoManipulationService().batchClearTodo(todoRemoveParam);
            monitor.stepIn();
            logger.info("\u89e6\u53d1\u5f85\u529e\u5220\u9664\u540e\u4e8b\u4ef6");
            TodoDeleteEvent event = new TodoDeleteEvent(todoRemoveParam, todoRemoveParam);
            TodoBeanUtils.getApplicationEventPublisher().publishEvent((ApplicationEvent)event);
            monitor.stepIn();
        }
        monitor.finishTask("nr_wf2_todo");
    }

    private static List<String> getParam(JobContext context) {
        String paramJson = context.getParameterValue("OPT_INFO");
        try {
            return (List)TodoJob.OBJECTMAPPER.readValue(paramJson, (TypeReference)new TypeReference<List<String>>(){});
        }
        catch (Exception e) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u5f85\u529e\u4e8b\u9879\u5bf9\u8c61\u53cd\u5e8f\u5217\u5316\u9519\u8bef\u3002");
        }
    }
}

