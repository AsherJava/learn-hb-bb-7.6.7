/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.ClearFormSchemeWorkflowTodoJob;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.ClearTaskWorkflowTodoJob;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.RemoveInstanceTodoJob;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.SendTodoJob;
import org.apache.commons.collections4.map.HashedMap;

@RealTimeJob(group="NR_WF_TODO_SENDER", groupTitle="\u62a5\u8868\u4e0a\u62a5\u6d41\u7a0b\u5f85\u529e\u4e8b\u9879\u53d1\u9001\u4efb\u52a1")
public class TodoJob
extends AbstractRealTimeJob {
    private static final long serialVersionUID = -7755168804327974955L;
    private static final String TITLE = "\u4e0a\u62a5\u6d41\u7a0b\u5f85\u529e\u4e8b\u9879\u53d1\u9001\u4efb\u52a1";
    private static final String P_OPT_TYPE = "OPT_TYPE";
    public static final String P_OPT_INFO = "OPT_INFO";
    public static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    static TodoJob createTodoJob(OperationType optType, Object param) {
        TodoJob job = new TodoJob();
        HashedMap<String, String> params = job.getParams();
        if (params == null) {
            params = new HashedMap<String, String>();
            job.setParams(params);
        }
        params.put(P_OPT_TYPE, optType.name());
        if (param instanceof String) {
            params.put(P_OPT_INFO, (String)param);
        } else {
            String paramJson;
            try {
                paramJson = OBJECTMAPPER.writeValueAsString(param);
            }
            catch (JsonProcessingException e) {
                throw new ProcessRuntimeException("jiuqi.nr.default", "\u5f85\u529e\u4e8b\u9879\u5bf9\u8c61\u5e8f\u5217\u5316\u9519\u8bef\u3002");
            }
            params.put(P_OPT_INFO, paramJson);
        }
        return job;
    }

    public TodoJob() {
        this.setTitle(TITLE);
        NpContext ctx = NpContextHolder.getContext();
        if (ctx != null) {
            this.setUserGuid(ctx.getUserId());
            this.setUserName(ctx.getUserName());
        }
    }

    private static OperationType getOptType(JobContext jobContext) {
        return OperationType.valueOf(jobContext.getParameterValue(P_OPT_TYPE));
    }

    public void execute(JobContext context) throws JobExecutionException {
        OperationType operationType = TodoJob.getOptType(context);
        switch (operationType) {
            case START_INSTANCE: {
                new SendTodoJob.StartInstanceSendTodoJob(context).execute();
                break;
            }
            case COMPLETE_TASK: {
                new SendTodoJob.CompleteTaskSendTodoJob(context).execute();
                break;
            }
            case RETRIVE_TASK: {
                new SendTodoJob.RetriveTaskSendTodoJob(context).execute();
                break;
            }
            case REFREASH_INSTANCE: {
                new SendTodoJob.RefreshInstanceSendTodoJob(context).execute();
                break;
            }
            case REMOVE_INSTANCE: {
                new RemoveInstanceTodoJob().execute(context);
                break;
            }
            case CLEAR_TASK_WORKFLOW: {
                new ClearTaskWorkflowTodoJob().execute(context);
                break;
            }
            case CLEAR_FORMSCHEME_WORKFLOW: {
                new ClearFormSchemeWorkflowTodoJob().execute(context);
                break;
            }
        }
    }

    public static enum OperationType {
        START_INSTANCE,
        REMOVE_INSTANCE,
        REFREASH_INSTANCE,
        COMPLETE_TASK,
        RETRIVE_TASK,
        CLEAR_TASK_WORKFLOW,
        CLEAR_FORMSCHEME_WORKFLOW;

    }
}

