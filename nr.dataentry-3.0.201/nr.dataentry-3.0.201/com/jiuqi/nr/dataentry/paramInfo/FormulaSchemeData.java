/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import java.util.Map;

public class FormulaSchemeData {
    private String key;
    private String title;
    private String type;
    private boolean defaultScheme;
    private String displayMode;
    private boolean show;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private int formulaSchemeMenuApply;
    private Map<String, String> efdcPayment;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDefaultScheme() {
        return this.defaultScheme;
    }

    public void setDefaultScheme(boolean defaultScheme) {
        this.defaultScheme = defaultScheme;
    }

    public boolean isShow() {
        return this.show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getFormulaSchemeMenuApply() {
        return this.formulaSchemeMenuApply;
    }

    public void setFormulaSchemeMenuApply(int formulaSchemeMenuApply) {
        this.formulaSchemeMenuApply = formulaSchemeMenuApply;
    }

    public void init(FormulaSchemeDefine formulaSchemeDefine) {
        this.key = formulaSchemeDefine.getKey();
        this.title = formulaSchemeDefine.getTitle();
        this.formulaSchemeMenuApply = formulaSchemeDefine.getFormulaSchemeMenuApply();
        this.type = formulaSchemeDefine.getFormulaSchemeType().name();
        this.defaultScheme = formulaSchemeDefine.isDefault();
        if (formulaSchemeDefine.getDisplayMode() != null) {
            this.displayMode = formulaSchemeDefine.getDisplayMode().name();
        }
        this.show = formulaSchemeDefine.isShow();
    }

    public String getDisplayMode() {
        return this.displayMode;
    }

    public void setDisplayMode(String displayMode) {
        this.displayMode = displayMode;
    }

    public Map<String, String> getEfdcPayment() {
        return this.efdcPayment;
    }

    public void setEfdcPayment(Map<String, String> efdcPayment) {
        this.efdcPayment = efdcPayment;
    }
}

