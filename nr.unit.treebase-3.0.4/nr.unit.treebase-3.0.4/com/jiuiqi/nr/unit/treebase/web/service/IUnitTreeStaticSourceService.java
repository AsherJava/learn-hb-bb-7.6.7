/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebase.web.service;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.web.response.WorkFlowStatusConfig;
import java.util.List;

public interface IUnitTreeStaticSourceService {
    public List<WorkFlowStatusConfig> createWorkflowStatusSource(IUnitTreeContext var1);
}

