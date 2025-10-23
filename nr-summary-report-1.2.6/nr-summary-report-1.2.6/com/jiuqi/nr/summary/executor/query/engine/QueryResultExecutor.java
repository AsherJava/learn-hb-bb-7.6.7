/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 */
package com.jiuqi.nr.summary.executor.query.engine;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSModel;
import com.jiuqi.nr.summary.executor.query.engine.DataBuffer;
import com.jiuqi.nr.summary.executor.query.engine.EngineQueryParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryResultExecutor {
    private final SummaryDSModel summaryDSModel;

    public QueryResultExecutor(SummaryDSModel summaryDSModel) {
        this.summaryDSModel = summaryDSModel;
    }

    public MemoryDataSet<ColumnInfo> process(IReadonlyTable dbTableValue, EngineQueryParam param) throws Exception {
        Metadata<ColumnInfo> metadata = param.getMetadata();
        Map<String, Integer> columnMap = param.getColumnMap();
        MemoryDataSet<ColumnInfo> dataSet = new MemoryDataSet<ColumnInfo>(null, metadata);
        this.buildAllRow(dataSet, dbTableValue, param);
        dataSet = this.resetCols(dataSet, param);
        Object dataTime = dbTableValue.getMasterKeys().getValue("DATATIME");
        this.processRefData(dataSet, param.getDsModel(), dataTime);
        return dataSet;
    }

    protected void buildAllRow(MemoryDataSet<ColumnInfo> dataSet, IReadonlyTable table, EngineQueryParam param) {
        int totalCount = table.getTotalCount();
        this.buildAllRow(dataSet, table, param, -1, -1);
    }

    protected void buildAllRow(MemoryDataSet<ColumnInfo> dataSet, IReadonlyTable table, EngineQueryParam param, int begin, int end) {
        int tableCount;
        if (begin < 0) {
            begin = 0;
        }
        if ((tableCount = table.getCount()) <= 0) {
            return;
        }
        if (end < 0 || end > tableCount) {
            end = tableCount;
        }
        if (end - begin <= 0) {
            return;
        }
        for (int i = begin; i < end; ++i) {
            IDataRow row = table.getItem(i);
            this.processRow(dataSet, row, param, i);
        }
    }

    protected void processRow(MemoryDataSet<ColumnInfo> dataSet, IDataRow dataRow, EngineQueryParam param, int rowNumber) {
        Map<String, Integer> columnMap = param.getColumnMap();
        Metadata<ColumnInfo> metadata = param.getMetadata();
        DataRow row = dataSet.add();
        for (int col = 0; col < metadata.size(); ++col) {
            Column columnInfo = metadata.getColumn(col);
            int fieldIndex = columnMap.getOrDefault(columnInfo.getName(), -1);
            if (fieldIndex < 0) continue;
            AbstractData dataValue = dataRow.getValue(fieldIndex);
            if (dataValue.isNull) {
                row.setNull(col);
                continue;
            }
            row.setValue(col, dataValue.getAsObject());
        }
    }

    public MemoryDataSet<ColumnInfo> resetCols(MemoryDataSet<ColumnInfo> dataSet, EngineQueryParam queryParam) {
        SummaryDSModel dsModel = queryParam.getDsModel();
        Map<String, Integer> columnMap = queryParam.getColumnMap();
        ArrayList<Column> newColumns = new ArrayList<Column>();
        for (Column column : dataSet.getMetadata().getColumns()) {
            newColumns.add(column.clone());
        }
        List commonFields = dsModel.getCommonFields();
        commonFields.forEach(dsField -> {
            String name = dsField.getName();
            if (!columnMap.containsKey(name)) {
                ColumnInfo colInfo = new ColumnInfo();
                colInfo.setFieldType(1);
                Column col = new Column(name, dsField.getValType(), dsField.getSourceType(), (Object)colInfo);
                newColumns.add(col);
            }
        });
        if (newColumns.size() > dataSet.getMetadata().size()) {
            Metadata newMetadata = new Metadata();
            for (Column column : newColumns) {
                newMetadata.addColumn(column);
            }
            int[] colIndexes = new int[dataSet.getMetadata().size()];
            for (int i = 0; i < colIndexes.length; ++i) {
                colIndexes[i] = newMetadata.indexOf(dataSet.getMetadata().getColumn(i).getName());
            }
            MemoryDataSet newDataSet = new MemoryDataSet(ColumnInfo.class, newMetadata);
            newDataSet.setSize(dataSet.size());
            for (int i = 0; i < dataSet.size(); ++i) {
                newDataSet.add();
                Object[] newData = newDataSet.getBuffer(i);
                Object[] data = dataSet.getBuffer(i);
                for (int j = 0; j < colIndexes.length; ++j) {
                    newData[colIndexes[j]] = data[j];
                }
            }
            dataSet = newDataSet;
        }
        return dataSet;
    }

    public void processRefData(MemoryDataSet<ColumnInfo> data, SummaryDSModel dsModel, Object dataTime) throws Exception {
        if (data == null || data.size() == 0) {
            return;
        }
        ArrayList<IProcessor> processors = new ArrayList<IProcessor>();
        this.generateChildDimProcessor(data, processors, dsModel, dataTime);
        if (processors.size() > 0) {
            for (int i = 0; i < data.size(); ++i) {
                DataRow row = data.get(i);
                for (IProcessor processor : processors) {
                    processor.process(row);
                }
            }
        }
    }

    private void generateChildDimProcessor(MemoryDataSet<ColumnInfo> data, List<IProcessor> processors, SummaryDSModel dsModel, Object dataTime) throws Exception {
        HashMap<String, List> map = new HashMap<String, List>();
        List commonFields = dsModel.getCommonFields();
        commonFields.forEach(dsField -> {
            String keyField = dsField.getKeyField();
            String name = dsField.getName();
            if (!name.equals(keyField)) {
                ArrayList<String> strings = (ArrayList<String>)map.get(keyField);
                if (strings == null) {
                    strings = new ArrayList<String>();
                    map.put(keyField, strings);
                }
                strings.add(name);
            }
        });
        map.forEach((key, value) -> {
            DSField field = dsModel.findField((String)key);
            String[] nameSplit = field.getMessageAlias().split("\\.");
            String dimName = nameSplit[0];
            String[] fieldNames = new String[value.size()];
            int[] fieldIndices = new int[value.size()];
            for (int i = 0; i < value.size(); ++i) {
                String subFieldName = (String)value.get(i);
                DSField field1 = dsModel.findField(subFieldName);
                String[] split = field1.getMessageAlias().split("\\.");
                fieldNames[i] = split[1];
                fieldIndices[i] = data.getMetadata().indexOf(subFieldName);
            }
            try {
                processors.add(new ChildDimProcessor(dimName, data.getMetadata().indexOf(key), fieldIndices, fieldNames, dataTime));
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static interface IProcessor {
        public void process(DataRow var1) throws Exception;
    }

    private class ChildDimProcessor
    implements IProcessor {
        private int keyIndex;
        private int[] fieldIndices;
        private DataBuffer dataBuffer;

        public ChildDimProcessor(String dimName, int keyIndex, int[] fieldIndices, String[] fieldNames, Object dataTime) throws Exception {
            this.keyIndex = keyIndex;
            this.fieldIndices = fieldIndices;
            this.dataBuffer = new DataBuffer(dimName, fieldNames, dataTime);
        }

        @Override
        public void process(DataRow row) throws Exception {
            String key = row.getString(this.keyIndex);
            String[] values = this.dataBuffer.getValues(key);
            if (values != null) {
                for (int i = 0; i < this.fieldIndices.length; ++i) {
                    row.setString(this.fieldIndices[i], values[i]);
                }
            }
        }
    }
}

