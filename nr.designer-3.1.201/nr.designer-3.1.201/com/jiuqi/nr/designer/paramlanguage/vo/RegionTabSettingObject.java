/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.paramlanguage.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegionTabSettingObject {
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="DisplayCondition")
    private String displayCondition;
    @JsonProperty(value="FilterCondition")
    private String filterCondition;
    @JsonProperty(value="BindingExpression")
    private String bindingExpression;
    @JsonProperty(value="Order")
    private String order;
    @JsonProperty(value="RowNum")
    private int rowNum;
    @JsonProperty(value="ID")
    private String ID;

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayCondition() {
        return this.displayCondition;
    }

    public void setDisplayCondition(String displayCondition) {
        this.displayCondition = displayCondition;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getBindingExpression() {
        return this.bindingExpression;
    }

    public void setBindingExpression(String bindingExpression) {
        this.bindingExpression = bindingExpression;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }
}

