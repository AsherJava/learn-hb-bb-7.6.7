/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.workflow2.todo.entity;

import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.workflow2.todo.extend.env.WorkFlowNode;
import java.util.List;

public interface TodoTabInfo {
    public List<WorkFlowNode> getFlowNodes();

    public WorkFlowType getFlowObjectType();

    public String getEntityCaliber();

    public String getPeriod();

    public String getWorkflowType();

    public boolean isSubmitExplain();

    public boolean isBackDescriptionNeedWrite();

    public boolean isReportDimensionEnable();

    public boolean isMultiEntityWithReportDimensionEnable();
}

