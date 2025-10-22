/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormulaUnitGroup
 */
package com.jiuqi.nr.data.logic.facade.extend.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.definition.facade.FormulaUnitGroup;
import java.util.List;
import java.util.Map;

public class GetCheckFmlEnv {
    private String formulaSchemeKey;
    private String formSchemeKey;
    private DimensionValueSet dimensionValueSet;
    private Mode mode;
    private Map<String, List<String>> formulaMaps;
    private List<String> accessForms;
    private boolean wholeBetween;
    private List<Integer> formulaCheckTypes;
    private List<FormulaUnitGroup> formulaUnitGroups;

    public List<String> getAccessForms() {
        return this.accessForms;
    }

    public GetCheckFmlEnv setAccessForms(List<String> accessForms) {
        this.accessForms = accessForms;
        return this;
    }

    public boolean isWholeBetween() {
        return this.wholeBetween;
    }

    public GetCheckFmlEnv setWholeBetween(boolean wholeBetween) {
        this.wholeBetween = wholeBetween;
        return this;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public GetCheckFmlEnv setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
        return this;
    }

    public Map<String, List<String>> getFormulaMaps() {
        return this.formulaMaps;
    }

    public GetCheckFmlEnv setFormulaMaps(Map<String, List<String>> formulaMaps) {
        this.formulaMaps = formulaMaps;
        return this;
    }

    public List<Integer> getFormulaCheckTypes() {
        return this.formulaCheckTypes;
    }

    public GetCheckFmlEnv setFormulaCheckTypes(List<Integer> formulaCheckTypes) {
        this.formulaCheckTypes = formulaCheckTypes;
        return this;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public GetCheckFmlEnv setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
        return this;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public GetCheckFmlEnv setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
        return this;
    }

    public List<FormulaUnitGroup> getFormulaUnitGroups() {
        return this.formulaUnitGroups;
    }

    public GetCheckFmlEnv setFormulaUnitGroups(List<FormulaUnitGroup> formulaUnitGroups) {
        this.formulaUnitGroups = formulaUnitGroups;
        return this;
    }

    public Mode getMode() {
        return this.mode;
    }

    public GetCheckFmlEnv setMode(Mode mode) {
        this.mode = mode;
        return this;
    }
}

