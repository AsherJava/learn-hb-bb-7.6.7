/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 */
package com.jiuqi.np.dataengine.var;

import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;

public class ContextVariableManagerProvider {
    private IReportDynamicNodeProvider priorityContextVariableManager;
    private IReportDynamicNodeProvider normalContextVariableManager;

    public IReportDynamicNodeProvider getPriorityContextVariableManager() {
        return this.priorityContextVariableManager;
    }

    public void setPriorityContextVariableManager(IReportDynamicNodeProvider priorityContextVariableManager) {
        this.priorityContextVariableManager = priorityContextVariableManager;
    }

    public IReportDynamicNodeProvider getNormalContextVariableManager() {
        return this.normalContextVariableManager;
    }

    public void setNormalContextVariableManager(IReportDynamicNodeProvider normalContextVariableManager) {
        this.normalContextVariableManager = normalContextVariableManager;
    }
}

