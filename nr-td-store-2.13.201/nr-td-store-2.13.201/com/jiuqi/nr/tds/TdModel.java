/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.tds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.tds.TdColumn;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TdModel {
    private String name;
    private List<TdColumn> columns = new ArrayList<TdColumn>();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TdColumn> getColumns() {
        return this.columns;
    }

    public void setColumns(List<TdColumn> columns) {
        this.columns = columns;
    }
}

