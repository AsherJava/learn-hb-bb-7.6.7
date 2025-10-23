/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IParameter
 */
package com.jiuqi.nr.dafafill.web.vo;

import com.jiuqi.bi.syntax.function.IParameter;

public class FormulaParameterVO {
    private String name;
    private int dataType;
    private String title;
    private boolean isOmitable;

    public FormulaParameterVO(IParameter parameter) {
        this.name = parameter.name();
        this.title = parameter.title();
        this.dataType = parameter.dataType();
        this.isOmitable = parameter.isOmitable();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isOmitable() {
        return this.isOmitable;
    }

    public void setOmitable(boolean omitable) {
        this.isOmitable = omitable;
    }
}

