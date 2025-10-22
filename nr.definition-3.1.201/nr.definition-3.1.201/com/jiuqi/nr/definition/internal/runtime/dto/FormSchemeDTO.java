/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import java.util.ArrayList;
import java.util.List;

public class FormSchemeDTO {
    private final FormSchemeDefine formSchemeDefine;
    private final List<FormulaVariDefine> formulaVariables;
    private final List<IDimensionFilter> dimensionFilters;

    public FormSchemeDTO(FormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
        this.formulaVariables = new ArrayList<FormulaVariDefine>();
        this.dimensionFilters = new ArrayList<IDimensionFilter>();
    }

    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public List<FormulaVariDefine> getFormulaVariables() {
        return this.formulaVariables;
    }

    public List<IDimensionFilter> getDimensionFilters() {
        return this.dimensionFilters;
    }
}

