/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 */
package com.jiuqi.nr.entity.engine.filter;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.executors.QueryContext;
import com.jiuqi.nr.entity.engine.filter.IEntityDataFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityDataFilter
implements IEntityDataFilter {
    private static final Logger log = LoggerFactory.getLogger(EntityDataFilter.class);
    private String expression;
    private DimensionValueSet masterKey;
    private String dimensionName;
    private ExecutorContext context;
    private QueryContext queryContext;
    private String entityId;

    public EntityDataFilter(String expression, String entityId) {
        this.expression = expression;
        this.entityId = entityId;
    }

    public void setMasterKey(DimensionValueSet masterKey, String dimensionName) {
        this.masterKey = masterKey;
        this.dimensionName = dimensionName;
    }

    public void buildFilterEnv(ExecutorContext context, QueryContext queryContext) {
        this.context = context;
        this.queryContext = queryContext;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String getDataFilter() {
        return "NR_EXPRESSION_FILTER";
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    @Override
    public DimensionValueSet getMasterKey() {
        return this.masterKey;
    }

    @Override
    public String getDimensionName() {
        return this.dimensionName;
    }

    @Override
    public ExecutorContext getContext() {
        return this.context;
    }

    @Override
    public QueryContext getQueryContext() {
        return this.queryContext;
    }

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public Object getCache(String name) {
        if (this.context == null) {
            return null;
        }
        VariableManager variableManager = this.context.getVariableManager();
        Variable variable = variableManager.find(name);
        if (variable == null) {
            return null;
        }
        Object varValue = null;
        try {
            varValue = variable.getVarValue((IContext)this.context);
        }
        catch (Exception e) {
            log.warn(String.format("\u8bfb\u53d6\u7f13\u5b58[%s]\u5931\u8d25", name), e);
        }
        return varValue;
    }

    @Override
    public void putCache(String name, Object value) {
        if (this.context == null) {
            return;
        }
        VariableManager variableManager = this.context.getVariableManager();
        Variable findVariable = variableManager.find(name);
        if (findVariable != null) {
            findVariable.setVarValue(value);
        } else {
            Variable variable = new Variable(name, name, 0, value);
            variableManager.add(variable);
        }
    }

    public String toString() {
        return "EntityDataFilter{expression='" + this.expression + '\'' + ", masterKey=" + this.masterKey + ", dimensionName='" + this.dimensionName + '\'' + ", context=" + (Object)((Object)this.context) + ", queryContext=" + this.queryContext + ", entityId='" + this.entityId + '\'' + '}';
    }
}

