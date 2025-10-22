/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.var;

import com.jiuqi.np.dataengine.var.Variable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class VariableManagerBase {
    private ConcurrentHashMap<String, Variable> variables = new ConcurrentHashMap(16);

    public final void add(Variable variable) {
        String varName = variable.getVarName();
        if (!this.variables.containsKey(varName = varName.toUpperCase())) {
            this.variables.put(varName, variable);
        }
    }

    public final Variable remove(String varName) {
        varName = varName.toUpperCase();
        return this.variables.remove(varName);
    }

    public final Variable find(String varName) {
        varName = varName.toUpperCase();
        return this.variables.get(varName);
    }

    protected final Variable findOrCreate(String varName) {
        Variable variable = this.variables.get(varName = varName.toUpperCase());
        if (variable == null) {
            variable = this.checkAutoVariable(varName);
        }
        return variable;
    }

    public List<Variable> getAllVars() {
        return new ArrayList<Variable>(this.variables.values());
    }

    protected final Variable checkAutoVariable(String varName) {
        if (varName == null) {
            return null;
        }
        Variable variable = null;
        return variable;
    }
}

