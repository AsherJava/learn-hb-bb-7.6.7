/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 */
package com.jiuqi.nr.zbquery.rest.vo;

import com.jiuqi.np.dataengine.var.Variable;

public class AdaptFormulaVariableVO {
    private String code;
    private String title;
    private int type = 0;
    private Object initValue;

    public AdaptFormulaVariableVO() {
    }

    public AdaptFormulaVariableVO(Variable variable) {
        this.code = variable.getVarName();
        this.title = variable.getVarTitle();
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getInitValue() {
        return this.initValue;
    }

    public void setInitValue(Object initValue) {
        this.initValue = initValue;
    }
}

