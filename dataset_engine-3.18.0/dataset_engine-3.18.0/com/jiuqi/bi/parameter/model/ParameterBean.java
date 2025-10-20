/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.model;

import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;

public class ParameterBean {
    private String name;
    private String title;
    private DataSourceModel dataSourceModel;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DataSourceModel getDataSourceModel() {
        return this.dataSourceModel;
    }

    public void setDataSourceModel(DataSourceModel dataSourceModel) {
        this.dataSourceModel = dataSourceModel;
    }
}

