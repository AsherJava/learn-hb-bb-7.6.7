/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.rest.param;

import com.jiuqi.nr.zbquery.model.ZBQueryModel;

public class AdaptFormulaParam {
    private ZBQueryModel zbQueryModel;
    private String formulaStr;

    public ZBQueryModel getZbQueryModel() {
        return this.zbQueryModel;
    }

    public void setZbQueryModel(ZBQueryModel zbQueryModel) {
        this.zbQueryModel = zbQueryModel;
    }

    public String getFormulaStr() {
        return this.formulaStr;
    }

    public void setFormulaStr(String formulaStr) {
        this.formulaStr = formulaStr;
    }
}

