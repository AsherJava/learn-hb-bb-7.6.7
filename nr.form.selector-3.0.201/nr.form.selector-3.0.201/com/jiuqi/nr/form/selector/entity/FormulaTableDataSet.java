/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.selector.entity;

import com.jiuqi.nr.form.selector.entity.FormulaData;
import java.util.List;
import java.util.Map;

public class FormulaTableDataSet {
    private List<FormulaData> pageData;
    private Map<String, List<String>> formFormulas;

    public List<FormulaData> getPageData() {
        return this.pageData;
    }

    public void setPageData(List<FormulaData> pageData) {
        this.pageData = pageData;
    }

    public Map<String, List<String>> getFormFormulas() {
        return this.formFormulas;
    }

    public void setFormFormulas(Map<String, List<String>> formFormulas) {
        this.formFormulas = formFormulas;
    }
}

