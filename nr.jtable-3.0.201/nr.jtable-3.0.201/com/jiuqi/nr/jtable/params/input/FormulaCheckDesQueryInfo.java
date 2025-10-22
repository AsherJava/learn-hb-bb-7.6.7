/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.HashMap;
import java.util.Map;

public class FormulaCheckDesQueryInfo {
    private boolean ifFromCopy;
    private String formSchemeKey;
    private String formulaSchemeKey;
    private String formKey;
    private String formulaKey;
    private int globRow;
    private int globCol;
    private String taskKey;
    private Map<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
    private String desKey;

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

    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
    }

    public int getGlobRow() {
        return this.globRow;
    }

    public void setGlobRow(int globRow) {
        this.globRow = globRow;
    }

    public int getGlobCol() {
        return this.globCol;
    }

    public void setGlobCol(int globCol) {
        this.globCol = globCol;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getDesKey() {
        return this.desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey;
    }

    public boolean isIfFromCopy() {
        return this.ifFromCopy;
    }

    public void setIfFromCopy(boolean ifFromCopy) {
        this.ifFromCopy = ifFromCopy;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }
}

