/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.gcreport.nr.impl.function;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import java.util.Map;

public class GcBaseExecutorContext
extends ExecutorContext {
    private static IDataDefinitionRuntimeController dataDefinitionController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
    private String taskId;
    private String orgId;
    private Map<String, Object> extendFields;

    public GcBaseExecutorContext() {
        super(dataDefinitionController);
    }

    public Map<String, Object> getExtendFields() {
        return this.extendFields;
    }

    public void setExtendFields(Map<String, Object> extendFields) {
        this.extendFields = extendFields;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}

