/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeName
 */
package com.jiuqi.va.query.template.plugin;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.jiuqi.va.query.template.plugin.QueryFormulaImpl;
import com.jiuqi.va.query.template.plugin.QueryPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
@JsonTypeName(value="formula")
public class FormulaPlugin
implements QueryPlugin {
    private List<QueryFormulaImpl> formulas = new ArrayList<QueryFormulaImpl>();
    private Map<String, Object> formulaValues = new HashMap<String, Object>();

    @Override
    public String getName() {
        return "formula";
    }

    @Override
    public String getTitle() {
        return "\u516c\u5f0f";
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public int getSortNum() {
        return 8;
    }

    public List<QueryFormulaImpl> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(List<QueryFormulaImpl> formulas) {
        this.formulas = formulas;
    }

    public QueryFormulaImpl getFormula(String id) {
        return this.formulas.stream().filter(f -> f.getId().equals(id)).findFirst().orElse(null);
    }

    public Map<String, Object> getFormulaValues() {
        return this.formulaValues;
    }

    public void setFormulaValues(Map<String, Object> formulaValues) {
        this.formulaValues = formulaValues;
    }
}

