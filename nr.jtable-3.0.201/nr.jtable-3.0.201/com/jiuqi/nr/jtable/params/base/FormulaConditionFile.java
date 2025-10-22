/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FormulaConditionFile
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> calDatalinks;
    private HashMap<String, Boolean> result;
    private HashMap<String, List<String>> floatError;
    private String calCellColor;
    private String FormulaConditionJS;
    private HashMap<String, FormulaConditionObject> TargetByDataLink = new HashMap();
    private Set<String> otherFormDataLink = new LinkedHashSet<String>();
    private Set<String> otherDataLink;
    private List<FormulaCondition> periodCells = new ArrayList<FormulaCondition>();
    private List<String> noColorFormula;
    private List<String> specialFloatAssignDataLinks;
    private List<String> haveConditionCalLinks;

    public List<String> getHaveConditionCalLinks() {
        return this.haveConditionCalLinks;
    }

    public void setHaveConditionCalLinks(List<String> haveConditionCalLinks) {
        this.haveConditionCalLinks = haveConditionCalLinks;
    }

    public List<String> getSpecialFloatAssignDataLinks() {
        return this.specialFloatAssignDataLinks;
    }

    public void setSpecialFloatAssignDataLinks(List<String> specialFloatAssignDataLinks) {
        this.specialFloatAssignDataLinks = specialFloatAssignDataLinks;
    }

    public List<String> getNoColorFormula() {
        return this.noColorFormula;
    }

    public void setNoColorFormula(List<String> noColorFormula) {
        this.noColorFormula = noColorFormula;
    }

    public HashMap<String, List<String>> getFloatError() {
        return this.floatError;
    }

    public void setFloatError(HashMap<String, List<String>> floatError) {
        this.floatError = floatError;
    }

    public List<String> getCalDatalinks() {
        return this.calDatalinks;
    }

    public void setCalDatalinks(List<String> calDatalinks) {
        this.calDatalinks = calDatalinks;
    }

    public HashMap<String, Boolean> getResult() {
        return this.result;
    }

    public void setResult(HashMap<String, Boolean> result) {
        this.result = result;
    }

    public List<FormulaCondition> getPeriodCells() {
        return this.periodCells;
    }

    public Set<String> getOtherDataLink() {
        return this.otherDataLink;
    }

    public String getFormulaConditionJS() {
        return this.FormulaConditionJS;
    }

    public String getCalCellColor() {
        return this.calCellColor;
    }

    public void setCalCellColor(String calCellColor) {
        this.calCellColor = calCellColor;
    }

    public void addOtherDataLink(String datalink) {
        this.otherDataLink.add(datalink);
    }

    public void AddPeriodCell(FormulaCondition formulaCondition) {
        this.periodCells.add(formulaCondition);
    }

    public Set<String> getOtherFormDataLink() {
        return this.otherFormDataLink;
    }

    public void AddOtherFormDataLink(String datalink) {
        this.otherFormDataLink.add(datalink);
    }

    public void setFormulaConditionJS(String formulaConditionJS) {
        this.FormulaConditionJS = formulaConditionJS;
    }

    public HashMap<String, FormulaConditionObject> getTargetByDtaLink() {
        return this.TargetByDataLink;
    }

    public void AddTargetByDataLink(String datalink, String dataLinkCode, String filedCode, FormulaCondition formulaCondition) {
        if (this.TargetByDataLink.containsKey(datalink)) {
            this.TargetByDataLink.get(datalink).getFormulaConditions().add(formulaCondition);
        } else {
            FormulaConditionObject formulaConditionObject = new FormulaConditionObject();
            formulaConditionObject.setFieldCode(filedCode);
            formulaConditionObject.setDataLinkCode(dataLinkCode);
            this.TargetByDataLink.put(datalink, formulaConditionObject);
            this.TargetByDataLink.get(datalink).getFormulaConditions().add(formulaCondition);
        }
    }

    public FormulaConditionFile() {
        this.otherDataLink = new LinkedHashSet<String>();
        this.calDatalinks = new ArrayList<String>();
        this.noColorFormula = new ArrayList<String>();
        this.floatError = new HashMap();
        this.result = new HashMap();
    }

    public static class FormulaCondition
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private String condition;
        private String formula;
        private String calTarget;

        public String getCondition() {
            return this.condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getFormula() {
            return this.formula;
        }

        public void setFormula(String formula) {
            this.formula = formula;
        }

        public String getCalTarget() {
            return this.calTarget;
        }

        public void setCalTarget(String calTarget) {
            this.calTarget = calTarget;
        }
    }

    public class FormulaConditionObject
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private String fieldCode;
        private String dataLinkCode;
        private List<FormulaCondition> formulaConditions = new ArrayList<FormulaCondition>();

        public String getFieldCode() {
            return this.fieldCode;
        }

        public void setFieldCode(String fieldCode) {
            this.fieldCode = fieldCode;
        }

        public String getDataLinkCode() {
            return this.dataLinkCode;
        }

        public void setDataLinkCode(String dataLinkCode) {
            this.dataLinkCode = dataLinkCode;
        }

        public List<FormulaCondition> getFormulaConditions() {
            return this.formulaConditions;
        }

        public void setFormulaConditions(List<FormulaCondition> formulaConditions) {
            this.formulaConditions = formulaConditions;
        }
    }
}

