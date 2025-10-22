/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchFormulaExecuteInfo
implements Serializable {
    private static final long serialVersionUID = 8187332719800505555L;
    private String taskKey;
    private String formSchemeKey;
    private String formulaSchemeKey;
    private List<String> forms;
    private Map<String, List<String>> formulaMaps;
    private String asyncTaskKey;
    private Map<String, Object> variableMap = new HashMap<String, Object>();

    public String getTaskKey() {
        return this.taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
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

    public List<String> getForms() {
        return this.forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    public Map<String, List<String>> getFormulaMaps() {
        return this.formulaMaps;
    }

    public void setFormulaMaps(Map<String, List<String>> formulaMaps) {
        this.formulaMaps = formulaMaps;
    }

    public String getAsyncTaskKey() {
        return this.asyncTaskKey;
    }

    public void setAsyncTaskKey(String asyncTaskKey) {
        this.asyncTaskKey = asyncTaskKey;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }
}

