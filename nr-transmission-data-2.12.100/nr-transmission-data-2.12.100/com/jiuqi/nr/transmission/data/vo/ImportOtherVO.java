/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.fielddatacrud.ImpMode
 */
package com.jiuqi.nr.transmission.data.vo;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import java.util.List;

public class ImportOtherVO {
    private String executeKey;
    private String mappingSchemeKey;
    private String taskKey;
    private String formSchemeKey;
    private List<Variable> variables;
    private ImpMode mode;

    public String getExecuteKey() {
        return this.executeKey;
    }

    public void setExecuteKey(String executeKey) {
        this.executeKey = executeKey;
    }

    public String getMappingSchemeKey() {
        return this.mappingSchemeKey;
    }

    public void setMappingSchemeKey(String mappingSchemeKey) {
        this.mappingSchemeKey = mappingSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<Variable> getVariables() {
        return this.variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public ImpMode getMode() {
        return this.mode;
    }

    public void setMode(ImpMode mode) {
        this.mode = mode;
    }
}

