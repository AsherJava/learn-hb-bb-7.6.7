/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.jdialect.model.ColumnModel
 */
package com.jiuqi.va.paramsync.domain;

import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.jdialect.model.ColumnModel;
import com.jiuqi.va.paramsync.domain.VaParamTableColumnModel;
import java.util.ArrayList;
import java.util.List;

public class VaParamTableModel {
    private String tableName;
    private List<VaParamTableColumnModel> columns;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<VaParamTableColumnModel> getColumns() {
        return this.columns;
    }

    public void setColumns(List<VaParamTableColumnModel> columns) {
        this.columns = columns;
    }

    public void addVPTableColumnModel(VaParamTableColumnModel column) {
        if (this.columns == null) {
            this.columns = new ArrayList<VaParamTableColumnModel>();
        }
        this.columns.add(column);
    }

    public void addJTableColumnModel(ColumnModel column) {
        if (this.columns == null) {
            this.columns = new ArrayList<VaParamTableColumnModel>();
        }
        this.columns.add(VaParamTableColumnModel.newCopy(column));
    }

    public static VaParamTableModel newCopy(JTableModel jTableModel) {
        VaParamTableModel model = new VaParamTableModel();
        model.setTableName(jTableModel.getTableName());
        for (ColumnModel column : jTableModel.getColumns()) {
            model.addJTableColumnModel(column);
        }
        return model;
    }
}

