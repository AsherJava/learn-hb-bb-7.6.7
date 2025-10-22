/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.resultBean;

import java.util.List;
import java.util.Map;

public class QuoteForms {
    private String targetFormSchemeKey;
    private String sourceFormSchemeKey;
    private String targetFormGroupKey;
    private List<String> formulaSchemekeys;
    private List<String> financialSchemekeys;
    private List<String> printSchemekeys;
    private boolean createNewFormulaScheme;
    private boolean createNewFinancialScheme;
    private boolean createNewPrintScheme;
    private boolean quoteFormGroup;
    private Map<String, List<String>> forms;

    public String getTargetFormSchemeKey() {
        return this.targetFormSchemeKey;
    }

    public void setTargetFormSchemeKey(String targetFormSchemeKey) {
        this.targetFormSchemeKey = targetFormSchemeKey;
    }

    public String getSourceFormSchemeKey() {
        return this.sourceFormSchemeKey;
    }

    public void setSourceFormSchemeKey(String sourceFormSchemeKey) {
        this.sourceFormSchemeKey = sourceFormSchemeKey;
    }

    public String getTargetFormGroupKey() {
        return this.targetFormGroupKey;
    }

    public void setTargetFormGroupKey(String targetFormGroupKey) {
        this.targetFormGroupKey = targetFormGroupKey;
    }

    public List<String> getFormulaSchemekeys() {
        return this.formulaSchemekeys;
    }

    public void setFormulaSchemekeys(List<String> formulaSchemekeys) {
        this.formulaSchemekeys = formulaSchemekeys;
    }

    public List<String> getFinancialSchemekeys() {
        return this.financialSchemekeys;
    }

    public void setFinancialSchemekeys(List<String> financialSchemekeys) {
        this.financialSchemekeys = financialSchemekeys;
    }

    public List<String> getPrintSchemekeys() {
        return this.printSchemekeys;
    }

    public void setPrintSchemekeys(List<String> printSchemekeys) {
        this.printSchemekeys = printSchemekeys;
    }

    public boolean isCreateNewFormulaScheme() {
        return this.createNewFormulaScheme;
    }

    public void setCreateNewFormulaScheme(boolean createNewFormulaScheme) {
        this.createNewFormulaScheme = createNewFormulaScheme;
    }

    public boolean isCreateNewFinancialScheme() {
        return this.createNewFinancialScheme;
    }

    public void setCreateNewFinancialScheme(boolean createNewFinancialScheme) {
        this.createNewFinancialScheme = createNewFinancialScheme;
    }

    public boolean isCreateNewPrintScheme() {
        return this.createNewPrintScheme;
    }

    public void setCreateNewPrintScheme(boolean createNewPrintScheme) {
        this.createNewPrintScheme = createNewPrintScheme;
    }

    public boolean isQuoteFormGroup() {
        return this.quoteFormGroup;
    }

    public void setQuoteFormGroup(boolean quoteFormGroup) {
        this.quoteFormGroup = quoteFormGroup;
    }

    public Map<String, List<String>> getForms() {
        return this.forms;
    }

    public void setForms(Map<String, List<String>> forms) {
        this.forms = forms;
    }
}

