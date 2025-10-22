/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio;

import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellLinkColumn;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;

public interface IRegionTableModel {
    public DataTable getDataTable();

    public TableModelDefine getTableModelDefine();

    public List<ITableBizKeyColumn> getDimensionColumns();

    public List<ITableBizKeyColumn> getBizKeyColumns();

    public List<ITableBizKeyColumn> getBuildColumns();

    public List<ITableCellLinkColumn> getCellLinkColumns();

    public ITableBizKeyColumn findDimensionColumn(ColumnModelDefine var1);

    public ITableBizKeyColumn findBizKeyColumn(ColumnModelDefine var1);
}

