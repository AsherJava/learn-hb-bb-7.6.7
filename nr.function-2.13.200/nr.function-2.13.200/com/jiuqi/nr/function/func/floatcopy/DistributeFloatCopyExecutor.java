/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.nr.data.engine.util.EntityQueryHelper
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.function.func.floatcopy;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.nr.data.engine.util.EntityQueryHelper;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.function.func.floatcopy.DistributeFloatCopyParaParser;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyExecutor;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyQueryInfo;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyRowData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DistributeFloatCopyExecutor
extends FloatCopyExecutor {
    private String subUnitColExp;

    public DistributeFloatCopyExecutor(DistributeFloatCopyParaParser parser, IReportFunction function) {
        super(parser, function);
        this.subUnitColExp = parser.getSubUnitColExp();
    }

    @Override
    public boolean execute(QueryContext qContext) throws Exception {
        DimensionValueSet currValueSet = qContext.getCurrentMasterKey();
        ExecutorContext exeContext = qContext.getExeContext();
        List<DimensionValueSet> vsList = this.queryCondition.getDimValueSetList(exeContext, currValueSet);
        List<FloatCopyRowData> srcRowDatas = this.getSrcRowDatas(qContext, vsList);
        HashMap<String, ArrayList<FloatCopyRowData>> srcRowDatasByUnit = new HashMap<String, ArrayList<FloatCopyRowData>>();
        for (FloatCopyRowData rowData : srcRowDatas) {
            String unitId = rowData.getValue(rowData.size() - 1).getAsString();
            ArrayList<FloatCopyRowData> rows = (ArrayList<FloatCopyRowData>)srcRowDatasByUnit.get(unitId);
            if (rows == null) {
                rows = new ArrayList<FloatCopyRowData>();
                srcRowDatasByUnit.put(unitId, rows);
            }
            rows.add(rowData);
        }
        String unitDim = exeContext.getEnv().getUnitDimesion(exeContext);
        EntityQueryHelper entityQueryHelper = (EntityQueryHelper)SpringBeanProvider.getBean(EntityQueryHelper.class);
        IEntityTable entityTable = entityQueryHelper.queryEntityTreeByDimensionName(qContext, unitDim, null, qContext.getPeriodWrapper().toString());
        for (String unitId : srcRowDatasByUnit.keySet()) {
            if (unitId == null || entityTable.findByEntityKey(unitId) == null) continue;
            List rows = (List)srcRowDatasByUnit.get(unitId);
            DimensionValueSet destDimValueSet = new DimensionValueSet(currValueSet);
            destDimValueSet.setValue(unitDim, (Object)unitId);
            IDataQuery destRequest = this.createDataQuery(qContext, this.copyCondition.getRow(), destDimValueSet, null);
            IDataTable destResult = destRequest.executeQuery(exeContext);
            this.clearDest(destResult, this.copyCondition.isClearBeforeCopy());
            List<FloatCopyRowData> destRowDatas = this.queryRowDatas(this.copyCondition.getRow(), destResult);
            this.copy(rows, destRowDatas, destResult, this.copyCondition.getRow().getQueryColumns());
        }
        return true;
    }

    @Override
    protected List<FloatCopyRowData> getSrcRowDatas(QueryContext qContext, List<DimensionValueSet> vsList) throws Exception {
        ExecutorContext destExeContext;
        ArrayList<FloatCopyRowData> rowDatas = new ArrayList<FloatCopyRowData>();
        ExecutorContext srcExeContext = destExeContext = qContext.getExeContext();
        for (DimensionValueSet dv : vsList) {
            IDataQuery request = this.createDataQuery(qContext, this.queryCondition.getRow(), dv, this.queryCondition.getFilter());
            request.addExpressionColumn(this.subUnitColExp);
            IDataTable result = request.executeQuery(srcExeContext);
            rowDatas.addAll(this.queryRowDatas(this.queryCondition.getRow(), result));
        }
        return rowDatas;
    }

    @Override
    protected List<FloatCopyRowData> queryRowDatas(FloatCopyQueryInfo row, IDataTable result) throws DataTypeException {
        ArrayList<FloatCopyRowData> rowList = new ArrayList<FloatCopyRowData>();
        int fieldCount = result.getFieldsInfo().getFieldCount();
        for (int i = 0; i < result.getCount(); ++i) {
            IDataRow dataRow = result.getItem(i);
            FloatCopyRowData rowData = new FloatCopyRowData(row.getQueryColumns().size() + 1);
            rowData.setRowKey(dataRow.getRowKeys());
            StringBuffer keyDataStr = new StringBuffer();
            List<Integer> keyColumns = row.getKeyColumns();
            for (int col : keyColumns) {
                String value = dataRow.getAsString(col);
                if (value == null) continue;
                keyDataStr.append(value).append("#");
            }
            if (keyDataStr.length() == 0) continue;
            rowData.setKeyColumnValue(keyDataStr.toString());
            for (int j = 0; j < row.getQueryColumns().size(); ++j) {
                rowData.setValue(j, dataRow.getValue(j));
            }
            if (fieldCount == row.getQueryColumns().size() + 2) {
                rowData.setValue(row.getQueryColumns().size(), dataRow.getValue(row.getQueryColumns().size() + 1));
            }
            rowList.add(rowData);
        }
        return rowList;
    }
}

