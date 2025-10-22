/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio;

import com.jiuqi.nr.data.estimation.service.dataio.ITableColumn;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public interface ITableBizKeyColumn
extends ITableColumn {
    public DataField getDataField();

    public ColumnModelDefine getColumnModel();
}

