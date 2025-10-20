/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.designer.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class CopyFormType {
    private boolean copyFormStyle;
    private boolean copyField;
    private boolean copyPrintTelement;
    private boolean copyFormula;
    private boolean financialFormula;

    public boolean isCopyFormStyle() {
        return this.copyFormStyle;
    }

    public void setCopyFormStyle(boolean copyFormStyle) {
        this.copyFormStyle = copyFormStyle;
    }

    public boolean isCopyField() {
        return this.copyField;
    }

    public void setCopyField(boolean copyField) {
        this.copyField = copyField;
    }

    public boolean isCopyPrintTelement() {
        return this.copyPrintTelement;
    }

    public void setCopyPrintTelement(boolean copyPrintTelement) {
        this.copyPrintTelement = copyPrintTelement;
    }

    public boolean isCopyFormula() {
        return this.copyFormula;
    }

    public void setCopyFormula(boolean copyFormula) {
        this.copyFormula = copyFormula;
    }

    public boolean isFinancialFormula() {
        return this.financialFormula;
    }

    public void setFinancialFormula(boolean financialFormula) {
        this.financialFormula = financialFormula;
    }
}

