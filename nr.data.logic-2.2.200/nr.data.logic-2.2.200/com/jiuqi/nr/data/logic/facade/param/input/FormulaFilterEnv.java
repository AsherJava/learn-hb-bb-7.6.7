/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.logic.exeception.LogicMappingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class FormulaFilterEnv {
    private String formSchemeKey;
    private String formulaSchemeKey;
    private String formKey;
    @Deprecated
    private Map<String, Object> variableMap;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    @Deprecated
    public Map<String, Object> getVariableMap() {
        if (this.variableMap == null) {
            this.variableMap = new HashMap<String, Object>();
            DsContext dsContext = DsContextHolder.getDsContext();
            List variables = dsContext.getVariables();
            if (!CollectionUtils.isEmpty(variables)) {
                for (Variable variable : variables) {
                    try {
                        this.variableMap.put(variable.getVarName(), variable.getVarValue(null));
                    }
                    catch (Exception e) {
                        throw new LogicMappingException(e.getMessage());
                    }
                }
            }
        }
        return this.variableMap;
    }

    @Deprecated
    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }
}

