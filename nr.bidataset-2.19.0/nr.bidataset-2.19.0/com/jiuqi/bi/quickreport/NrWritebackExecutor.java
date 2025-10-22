/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.quickreport.writeback.IWritebackContext
 *  com.jiuqi.bi.quickreport.writeback.IWritebackExecutor
 *  com.jiuqi.bi.quickreport.writeback.WritebackException
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.ExecuteException
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.bi.quickreport;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.quickreport.NrWritebackParam;
import com.jiuqi.bi.quickreport.NrWrtiebackContext;
import com.jiuqi.bi.quickreport.NrWrtiebackDataRegion;
import com.jiuqi.bi.quickreport.NrWrtiebackFieldInfo;
import com.jiuqi.bi.quickreport.writeback.IWritebackContext;
import com.jiuqi.bi.quickreport.writeback.IWritebackExecutor;
import com.jiuqi.bi.quickreport.writeback.WritebackException;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.datascheme.api.DataTable;
import java.util.Map;
import java.util.UUID;

public class NrWritebackExecutor
implements IWritebackExecutor {
    private NrWritebackParam param;
    private String tableCode;

    public NrWritebackExecutor(NrWritebackParam param, String tableCode) {
        this.param = param;
        this.tableCode = tableCode;
    }

    public void write(IWritebackContext context, MemoryDataSet<?> dataSet) throws WritebackException {
        try {
            DataTable table = this.param.dataSchemeService.getDataTableByCode(this.tableCode);
            if (table == null) {
                table = this.param.dataSchemeService.getDataTable(this.tableCode);
            }
            NrWrtiebackContext wrtiebackTableInfo = new NrWrtiebackContext(context, dataSet, table);
            wrtiebackTableInfo.doInit(this.param);
            for (Map.Entry<DimensionValueSet, NrWrtiebackDataRegion> entry : wrtiebackTableInfo.getRegions().entrySet()) {
                this.writeRegion(dataSet, wrtiebackTableInfo, entry);
            }
        }
        catch (Exception e) {
            throw new WritebackException(e.getMessage(), (Throwable)e);
        }
    }

    private void writeRegion(MemoryDataSet<?> dataSet, NrWrtiebackContext wrtiebackTableInfo, Map.Entry<DimensionValueSet, NrWrtiebackDataRegion> entry) throws Exception, WritebackException, IncorrectQueryException, ParseException, ExpressionException, ExecuteException {
        DimensionValueSet masterKeys = entry.getKey();
        NrWrtiebackDataRegion region = entry.getValue();
        IDataQuery query = this.param.dataAccessProvider.newDataQuery();
        query.setMasterKeys(masterKeys);
        for (NrWrtiebackFieldInfo fieldInfo : wrtiebackTableInfo.getDataFieldInfos()) {
            int fieldIndex = query.addColumn(fieldInfo.getFieldDefine());
            fieldInfo.setFieldIndex(fieldIndex);
        }
        if (wrtiebackTableInfo.isFloat()) {
            IDataUpdator updator = query.openForUpdate(wrtiebackTableInfo.getExecutorContext(), true);
            updator.needCheckDuplicateKeys(true);
            int rowIndex = 1;
            for (int srcRowIndex : region.getResultRowIndexes()) {
                DataRow row = dataSet.get(srcRowIndex);
                DimensionValueSet rowKeys = this.createRowKey(wrtiebackTableInfo, srcRowIndex, row);
                IDataRow dataRow = updator.addInsertedRow(rowKeys);
                this.writeRow(wrtiebackTableInfo, rowIndex, row, dataRow);
                ++rowIndex;
            }
            updator.commitChanges();
        } else {
            IDataTable dataTable = query.executeQuery(wrtiebackTableInfo.getExecutorContext());
            for (int srcRowIndex : region.getResultRowIndexes()) {
                DataRow row;
                DimensionValueSet rowKeys = this.createRowKey(wrtiebackTableInfo, srcRowIndex, row = dataSet.get(srcRowIndex));
                IDataRow dataRow = dataTable.findRow(rowKeys);
                if (dataRow == null) {
                    dataRow = dataTable.appendRow(rowKeys);
                }
                this.writeRow(wrtiebackTableInfo, 1, row, dataRow);
            }
            dataTable.commitChanges(true);
        }
    }

    private void writeRow(NrWrtiebackContext wrtiebackTableInfo, int rowIndex, DataRow row, IDataRow dataRow) {
        for (NrWrtiebackFieldInfo fieldInfo : wrtiebackTableInfo.getDataFieldInfos()) {
            Object value = null;
            int columnIndex = fieldInfo.getColumnIndex();
            if (columnIndex >= 0) {
                value = row.getValue(columnIndex);
            } else if (fieldInfo.isFloatOrder()) {
                value = rowIndex * 1000;
            }
            dataRow.setValue(fieldInfo.getFieldIndex(), value);
        }
    }

    private DimensionValueSet createRowKey(NrWrtiebackContext wrtiebackTableInfo, int srcRowIndex, DataRow row) throws WritebackException {
        DimensionValueSet rowKeys = new DimensionValueSet();
        for (NrWrtiebackFieldInfo keyFieldInfo : wrtiebackTableInfo.getRowKeyFieldInfos()) {
            Object keyValue = null;
            int columnIndex = keyFieldInfo.getColumnIndex();
            if (columnIndex >= 0) {
                keyValue = row.getValue(columnIndex);
            }
            if (keyFieldInfo.isDataTime()) {
                keyValue = TimeDimUtils.timeKeyToPeriod((String)((String)keyValue), (PeriodType)wrtiebackTableInfo.getPeriodType());
            } else if (keyFieldInfo.isBizkeyOrder()) {
                keyValue = UUID.randomUUID().toString();
            }
            if (keyValue == null) {
                throw new WritebackException("\u7b2c" + srcRowIndex + "\u884c" + (columnIndex + 1) + "\u5217\uff0c\u4e3b\u952e\u5b57\u6bb5\u503c\u4e3a\u7a7a");
            }
            rowKeys.setValue(keyFieldInfo.getDimensionName(), keyValue);
        }
        return rowKeys;
    }
}

