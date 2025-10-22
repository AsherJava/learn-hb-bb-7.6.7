/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.gcreport.formulaschemeconfig.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class FormulaSchemeColumn {
    private String label;
    private String property;
    private Integer minWidth;

    public FormulaSchemeColumn() {
    }

    public FormulaSchemeColumn(String label, String property, Integer minWidth) {
        this.label = label;
        this.property = property;
        this.minWidth = minWidth;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Integer getMinWidth() {
        return this.minWidth;
    }

    public void setMinWidth(Integer minWidth) {
        this.minWidth = minWidth;
    }
}

