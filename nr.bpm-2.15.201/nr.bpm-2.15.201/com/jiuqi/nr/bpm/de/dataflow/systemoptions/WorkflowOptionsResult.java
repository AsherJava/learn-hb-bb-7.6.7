/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.bpm.de.dataflow.systemoptions;

import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;

public class WorkflowOptionsResult {
    private static final String WORKFLOWID = "nr-flow-id";
    private static final String WORKFLOW_END_NODE = "flow-end-node";
    private static final String WORKFLOW_ASYNC_OPT = "flow-async-opt";
    private static final String WORKFLOW_ASYNC_OPT_NUM = "flow-async-opt-num";
    private static final String WORKFLOW_ASYNC_TODO = "flow-async-todo";
    private static final String WORKFLOW_ASYNC_TODO_NUM = "flow-async-todo_num";
    private static final String WORKFLOW_FORCE_NODE = "workflow-force-node";

    public static boolean ignoreCheckEndEvent() {
        INvwaSystemOptionService nvwaSystemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
        String systemStr = nvwaSystemOptionService.get(WORKFLOWID, WORKFLOW_END_NODE);
        return !"0".equals(systemStr);
    }

    public static int asyncNum() {
        INvwaSystemOptionService nvwaSystemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
        String systemStr = nvwaSystemOptionService.get(WORKFLOWID, WORKFLOW_ASYNC_OPT_NUM);
        return Integer.parseInt(systemStr);
    }

    public static int asyncTodoNum() {
        INvwaSystemOptionService nvwaSystemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
        String systemStr = nvwaSystemOptionService.get(WORKFLOWID, WORKFLOW_ASYNC_TODO_NUM);
        return Integer.parseInt(systemStr);
    }

    public static boolean ignoreCurrentUnit() {
        INvwaSystemOptionService nvwaSystemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
        String systemStr = nvwaSystemOptionService.get(WORKFLOWID, WORKFLOW_FORCE_NODE);
        return !"0".equals(systemStr);
    }
}

