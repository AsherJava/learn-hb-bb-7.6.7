/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.singlequeryimport.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DbSelectIndex {
    @JsonProperty(value="row")
    private int row;
    @JsonProperty(value="column")
    private int column;
    @JsonProperty(value="title")
    private String title;
    @JsonProperty(value="data")
    private String data;
    @JsonProperty(value="type")
    private String type;

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return this.column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

