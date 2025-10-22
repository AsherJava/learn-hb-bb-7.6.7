/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.datacheck.fmlcheck.vo;

import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;

public class FmlSchemeVO {
    private String key;
    private String title;

    public FmlSchemeVO(FormulaSchemeDefine formulaSchemeDefine) {
        this.key = formulaSchemeDefine.getKey();
        this.title = formulaSchemeDefine.getTitle();
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
}

