/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 */
package com.jiuqi.nr.expression.filter.parse;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import java.util.AbstractMap;

public class FilterExecuteContext<T extends AbstractMap<String, Object>>
extends QueryContext
implements IContext {
    private T param;
    private T data;
    private ExecutorContext executorContext;

    public FilterExecuteContext(T param, T data, ExecutorContext context) throws ParseException {
        super(context, null, null);
        this.param = param;
        this.data = data;
        this.executorContext = context;
        DimensionValueSet varDimensionValueSet = context.getVarDimensionValueSet();
        if (varDimensionValueSet != null) {
            super.setMasterKeys(varDimensionValueSet);
        } else {
            super.setMasterKeys(new DimensionValueSet());
        }
        this.setCache();
    }

    public FilterExecuteContext(T param, T data) throws ParseException {
        super(new ExecutorContext(null), null, null);
        this.param = param;
        this.data = data;
        super.setMasterKeys(new DimensionValueSet());
        this.setCache();
    }

    public FilterExecuteContext(ExecutorContext exeContext) throws ParseException {
        super(exeContext, null, null);
        this.executorContext = exeContext;
    }

    public T getParam() {
        return this.param;
    }

    public T getData() {
        return this.data;
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    public void refreshData(T param, T data) {
        this.param = param;
        this.data = data;
        this.setCache();
    }

    private void setCache() {
        String filterData = "FILTER-DATA";
        String filterParam = "FILTER-PARAM";
        VariableManager variableManager = super.getExeContext().getVariableManager();
        Variable dataVariable = variableManager.find(filterData);
        if (dataVariable != null) {
            dataVariable.setVarValue(this.data);
        } else {
            variableManager.add(new Variable(filterData, "\u8fc7\u6ee4\u6570\u636e", 0, this.data));
        }
        Variable paramVariable = variableManager.find(filterParam);
        if (paramVariable != null) {
            paramVariable.setVarValue(this.param);
        } else {
            variableManager.add(new Variable(filterParam, "\u8fc7\u6ee4\u53c2\u6570", 0, this.param));
        }
    }
}

