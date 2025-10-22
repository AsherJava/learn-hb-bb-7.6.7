/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.nr.data.estimation.service.dataio.IRegionTableModel;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellLinkColumn;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionTableModel
implements IRegionTableModel {
    private DataTable dataTable;
    private final TableModelDefine tableModelDefine;
    private List<ITableBizKeyColumn> dimensionColumns = new ArrayList<ITableBizKeyColumn>();
    private List<ITableBizKeyColumn> bizKeyColumns = new ArrayList<ITableBizKeyColumn>();
    private List<ITableBizKeyColumn> buildColumns = new ArrayList<ITableBizKeyColumn>();
    private List<ITableCellLinkColumn> cellLinkColumns = new ArrayList<ITableCellLinkColumn>();
    private final Map<String, ITableBizKeyColumn> key2DimColumnMap = new HashMap<String, ITableBizKeyColumn>();
    private final Map<String, ITableBizKeyColumn> key2BizColumnMap = new HashMap<String, ITableBizKeyColumn>();

    public RegionTableModel(TableModelDefine tableModelDefine) {
        this.tableModelDefine = tableModelDefine;
    }

    @Override
    public DataTable getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    @Override
    public TableModelDefine getTableModelDefine() {
        return this.tableModelDefine;
    }

    @Override
    public List<ITableBizKeyColumn> getDimensionColumns() {
        return this.dimensionColumns;
    }

    public void setDimensionColumns(List<ITableBizKeyColumn> dimensionColumns) {
        this.dimensionColumns = dimensionColumns;
    }

    @Override
    public List<ITableBizKeyColumn> getBizKeyColumns() {
        return this.bizKeyColumns;
    }

    public void setBizKeyColumns(List<ITableBizKeyColumn> bizKeyColumns) {
        this.bizKeyColumns = bizKeyColumns;
    }

    @Override
    public List<ITableBizKeyColumn> getBuildColumns() {
        return this.buildColumns;
    }

    public void setBuildColumns(List<ITableBizKeyColumn> buildColumns) {
        this.buildColumns = buildColumns;
    }

    @Override
    public List<ITableCellLinkColumn> getCellLinkColumns() {
        return this.cellLinkColumns;
    }

    @Override
    public ITableBizKeyColumn findDimensionColumn(ColumnModelDefine rowKeyColumn) {
        if (!this.key2DimColumnMap.containsKey(rowKeyColumn.getID())) {
            this.key2DimColumnMap.put(rowKeyColumn.getID(), this.getDimensionColumn(rowKeyColumn));
        }
        return this.key2DimColumnMap.get(rowKeyColumn.getID());
    }

    @Override
    public ITableBizKeyColumn findBizKeyColumn(ColumnModelDefine rowKeyColumn) {
        if (!this.key2BizColumnMap.containsKey(rowKeyColumn.getID())) {
            this.key2BizColumnMap.put(rowKeyColumn.getID(), this.getBizKeyColumn(rowKeyColumn));
        }
        return this.key2BizColumnMap.get(rowKeyColumn.getID());
    }

    public void setCellLinkColumns(List<ITableCellLinkColumn> cellLinkColumns) {
        this.cellLinkColumns = cellLinkColumns;
    }

    protected ITableBizKeyColumn getDimensionColumn(ColumnModelDefine rowKeyColumn) {
        return this.getDimensionColumns().stream().filter(column -> column.getColumnModel().getID().equals(rowKeyColumn.getID())).findFirst().orElse(null);
    }

    protected ITableBizKeyColumn getBizKeyColumn(ColumnModelDefine rowKeyColumn) {
        ITableBizKeyColumn bizKeyColumn = this.getBizKeyColumns().stream().filter(column -> column.getColumnModel().getID().equals(rowKeyColumn.getID())).findFirst().orElse(null);
        if (bizKeyColumn == null) {
            bizKeyColumn = this.getBuildColumns().stream().filter(column -> column.getColumnModel().getID().equals(rowKeyColumn.getID())).findFirst().orElse(null);
        }
        return bizKeyColumn;
    }
}

