/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 */
package com.jiuqi.nr.definition.formulatracking;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;

public class CustomVariable
extends Variable {
    private static final long serialVersionUID = 1L;

    public CustomVariable(String varName, int dataType) {
        super(varName, dataType);
    }

    public Object getVarValue(IContext context) throws Exception {
        QueryContext qContext = (QueryContext)context;
        VariableManager variableManager = qContext.getExeContext().getEnv().getVariableManager();
        Variable variable = variableManager.find(this.getVarName());
        return variable.getVarValue((IContext)qContext);
    }
}

