/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.dto;

import com.jiuqi.nr.formula.dto.FormulaPathNode;
import java.util.List;

public class FormulaSearchItem {
    private String code;
    private String formKey;
    private String key;
    private String title;
    private List<FormulaPathNode> formulaPathNodes;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

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

    public List<FormulaPathNode> getGroupPaths() {
        return this.formulaPathNodes;
    }

    public void setGroupPaths(List<FormulaPathNode> formulaPathNodes) {
        this.formulaPathNodes = formulaPathNodes;
    }
}

