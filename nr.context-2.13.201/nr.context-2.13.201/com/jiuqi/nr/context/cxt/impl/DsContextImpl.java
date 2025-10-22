/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 */
package com.jiuqi.nr.context.cxt.impl;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.Extension;
import com.jiuqi.nr.context.cxt.impl.ExtensionImpl;
import java.util.List;

public class DsContextImpl
implements DsContext {
    private static final long serialVersionUID = 4642367062528158638L;
    private String entityId;
    private String filterExpression;
    private Extension extension;
    private List<Variable> variables;
    private String taskKey;

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }

    @Override
    public String getContextEntityId() {
        return this.entityId;
    }

    @Override
    public String getContextFilterExpression() {
        return this.filterExpression;
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    @Override
    public List<Variable> getVariables() {
        return this.variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    @Override
    public Extension getExtension() {
        if (this.extension == null) {
            this.extension = new ExtensionImpl();
        }
        return this.extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }
}

