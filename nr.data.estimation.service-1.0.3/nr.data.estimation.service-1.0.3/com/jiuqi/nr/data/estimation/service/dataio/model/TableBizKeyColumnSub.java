/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.nr.data.estimation.service.dataio.model.TableBizKeyColumn;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class TableBizKeyColumnSub
extends TableBizKeyColumn {
    private ColumnModelDefine oriColumnModel;

    public TableBizKeyColumnSub(ColumnModelDefine subColumnModel) {
        super(subColumnModel);
    }

    @Override
    public String getColumnName() {
        return this.oriColumnModel.getCode();
    }

    public ColumnModelDefine getOriColumnModel() {
        return this.oriColumnModel;
    }

    public void setOriColumnModel(ColumnModelDefine oriColumnModel) {
        this.oriColumnModel = oriColumnModel;
    }
}

