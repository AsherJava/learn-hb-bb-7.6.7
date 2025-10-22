/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.datawarning.serviceimpl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.datawarning.dao.IWarningRow;
import com.jiuqi.nr.datawarning.service.IDataWarningTable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataWarningTable
implements IDataWarningTable {
    int rowCount;
    List<IWarningRow> allRows = new ArrayList<IWarningRow>();

    protected void appendRow(IWarningRow row) {
        this.allRows.add(row);
    }

    protected void updateRow(int index, IWarningRow row) {
    }

    protected void appendRows(List<IWarningRow> rows) {
    }

    protected void deleteRow(int index) {
    }

    @Override
    public List<IWarningRow> getAllRows() {
        return null;
    }

    @Override
    public IWarningRow getRow(int index) {
        return null;
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public List<IWarningRow> getRowsByFieldCode(String fieldCode, DimensionValueSet rowKey) {
        List<IWarningRow> result = this.allRows.stream().filter(row -> fieldCode.equals(row.getFieldCode()) && rowKey.equals((Object)row.getRowDimensionValueSet())).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<IWarningRow> getRows(String fieldKey) {
        return null;
    }
}

