/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfoUpper;
import com.jiuqi.nr.data.estimation.service.dataio.ITableBizKeyColumn;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellLinkColumn;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.ArrayList;
import java.util.List;

public class DataRegionInfoUpper
implements IDataRegionInfoUpper {
    private final DataRegionDefine dataRegion;
    private List<ITableBizKeyColumn> dimensionColumns = new ArrayList<ITableBizKeyColumn>();
    private List<ITableBizKeyColumn> bizKeyColumns = new ArrayList<ITableBizKeyColumn>();
    private List<ITableBizKeyColumn> buildColumns = new ArrayList<ITableBizKeyColumn>();
    private List<ITableCellLinkColumn> cellLinksColumns = new ArrayList<ITableCellLinkColumn>();

    public DataRegionInfoUpper(DataRegionDefine dataRegion) {
        this.dataRegion = dataRegion;
    }

    @Override
    public DataRegionDefine getDataRegion() {
        return this.dataRegion;
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
    public List<ITableCellLinkColumn> getCellLinksColumns() {
        return this.cellLinksColumns;
    }

    public void setCellLinksColumns(List<ITableCellLinkColumn> cellLinksColumns) {
        this.cellLinksColumns = cellLinksColumns;
    }
}

