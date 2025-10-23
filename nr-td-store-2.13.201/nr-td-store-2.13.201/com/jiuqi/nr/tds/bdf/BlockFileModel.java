/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.tds.bdf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.tds.TdColumn;
import com.jiuqi.nr.tds.TdModel;
import com.jiuqi.nr.tds.bdf.BlockFileColumn;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BlockFileModel {
    private String name;
    private List<BlockFileColumn> columns = new ArrayList<BlockFileColumn>();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BlockFileColumn> getColumns() {
        return this.columns;
    }

    public void setColumns(List<BlockFileColumn> columns) {
        this.columns = columns;
    }

    public BlockFileModel() {
    }

    public BlockFileModel(TdModel model) {
        this.name = model.getName();
        for (TdColumn column : model.getColumns()) {
            this.columns.add(new BlockFileColumn(column));
        }
    }
}

