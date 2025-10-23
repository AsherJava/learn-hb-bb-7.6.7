/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.workflow2.todo.extend;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.workflow2.todo.entityimpl.UploadState;
import com.jiuqi.nr.workflow2.todo.entityimpl.WorkflowButton;
import com.jiuqi.nr.workflow2.todo.extend.WorkFlowStateTableModel;
import com.jiuqi.nr.workflow2.todo.extend.env.WorkFlowNode;
import java.util.List;

public interface TodoExtendInterface {
    public List<WorkFlowNode> getAllWorkflowNode(String var1, String var2);

    public WorkFlowType getFlowObjectType(String var1, String var2);

    public WorkFlowType getFlowObjectType(String var1);

    public List<WorkflowButton> getWorkFlowButtons(String var1, String var2, String var3);

    public List<UploadState> getUploadStates(String var1, String var2, String var3);

    public WorkFlowStateTableModel getStateTableModel(String var1, String var2);

    public WorkFlowStateTableModel getStateTableModel(String var1);

    public boolean isReportDimensionEnable(String var1);

    public boolean isCorporate(TaskDefine var1, DataDimension var2);

    public boolean isMultiEntityCaliberWithReportDimensionEnable(String var1);
}

