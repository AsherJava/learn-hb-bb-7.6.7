/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModelSub;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.data.estimation.service.dataio.model.RegionTableModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;

public class RegionTableModelSub
extends RegionTableModel
implements IRegionTableModelSub {
    private final TableModelDefine oriTableModelDefine;
    private List<ITableBizKeyColumn> otherKeyColumns;

    public RegionTableModelSub(TableModelDefine oriTableModelDefine, TableModelDefine subTableModelDefine) {
        super(subTableModelDefine);
        this.oriTableModelDefine = oriTableModelDefine;
    }

    @Override
    public List<ITableBizKeyColumn> getOtherKeyColumns() {
        return this.otherKeyColumns;
    }

    public TableModelDefine getOriTableModelDefine() {
        return this.oriTableModelDefine;
    }

    public void setOtherKeyColumns(List<ITableBizKeyColumn> otherKeyColumns) {
        this.otherKeyColumns = otherKeyColumns;
    }

    @Override
    protected ITableBizKeyColumn getDimensionColumn(ColumnModelDefine rowKeyColumn) {
        ITableBizKeyColumn dimensionColumn = super.getDimensionColumn(rowKeyColumn);
        if (dimensionColumn == null) {
            dimensionColumn = this.getOtherKeyColumns().stream().filter(otherKeyColumn -> otherKeyColumn.getColumnModel().getID().equals(rowKeyColumn.getID())).findFirst().orElse(null);
        }
        return dimensionColumn;
    }
}

