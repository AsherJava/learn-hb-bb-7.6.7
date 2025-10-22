/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.survey.model.Choice
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.survey.model.Choice;
import java.io.Serializable;
import java.util.List;

public class SurveyAllEnumVO
implements Serializable {
    private String tableCode;
    private String zbCode;
    private List<Choice> choiceFormulas;
    private String filterFormula;

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getZbCode() {
        return this.zbCode;
    }

    public void setZbCode(String zbCode) {
        this.zbCode = zbCode;
    }

    public List<Choice> getChoiceFormulas() {
        return this.choiceFormulas;
    }

    public void setChoiceFormulas(List<Choice> choiceFormulas) {
        this.choiceFormulas = choiceFormulas;
    }

    public String getFilterFormula() {
        return this.filterFormula;
    }

    public void setFilterFormula(String filterFormula) {
        this.filterFormula = filterFormula;
    }
}

