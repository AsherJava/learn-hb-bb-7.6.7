/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  org.jsoup.nodes.Element
 */
package com.jiuqi.nr.var.formula.vo;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;

public class ReportFormulaGroup {
    List<Element> elements = new ArrayList<Element>();
    List<String> formulas = new ArrayList<String>();
    String formSchemeKey;
    String dataSchemeKey;
    private FormulaDim formulaDim;

    public ReportFormulaGroup(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public ReportFormulaGroup() {
    }

    public FormulaDim getFormulaDim() {
        return this.formulaDim;
    }

    public void setFormulaDim(FormulaDim formulaDim) {
        this.formulaDim = formulaDim;
    }

    public List<Element> getElements() {
        return this.elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public List<String> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(List<String> formulas) {
        this.formulas = formulas;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public static class FormulaDim {
        private List<String> entityIds;
        private DimensionValueSet dimensionValueSet;

        public List<String> getEntityIds() {
            return this.entityIds;
        }

        public void setEntityIds(List<String> entityIds) {
            this.entityIds = entityIds;
        }

        public DimensionValueSet getDimensionValueSet() {
            return this.dimensionValueSet;
        }

        public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
            this.dimensionValueSet = dimensionValueSet;
        }
    }
}

