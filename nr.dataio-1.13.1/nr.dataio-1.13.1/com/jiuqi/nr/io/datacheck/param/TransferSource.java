/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.io.datacheck.param;

import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;

public class TransferSource {
    private FieldDefine dataField;
    private Object value;
    private IDataRow dataRow;
    private TableContext tableContext;
    private RegionData regionData;
    private String dataSchemeKey;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public RegionData getRegionData() {
        return this.regionData;
    }

    public void setRegionData(RegionData regionData) {
        this.regionData = regionData;
    }

    public TableContext getTableContext() {
        return this.tableContext;
    }

    public void setTableContext(TableContext tableContext) {
        this.tableContext = tableContext;
    }

    public FieldDefine getDataField() {
        return this.dataField;
    }

    public void setDataField(FieldDefine dataField) {
        this.dataField = dataField;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public IDataRow getDataRow() {
        return this.dataRow;
    }

    public void setDataRow(IDataRow dataRow) {
        this.dataRow = dataRow;
    }
}

