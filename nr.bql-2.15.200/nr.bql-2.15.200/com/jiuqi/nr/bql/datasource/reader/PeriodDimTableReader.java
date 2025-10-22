/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.datasource.reader.DataField
 *  com.jiuqi.bi.adhoc.datasource.reader.DataPage
 *  com.jiuqi.bi.adhoc.datasource.reader.DataTable
 *  com.jiuqi.bi.adhoc.datasource.reader.IReadContext
 *  com.jiuqi.bi.adhoc.engine.AdHocEngineException
 *  com.jiuqi.bi.adhoc.engine.IDataListener
 *  com.jiuqi.bi.adhoc.model.FieldInfo
 *  com.jiuqi.bi.adhoc.model.TableInfo
 *  com.jiuqi.bi.adhoc.model.TimeGranularity
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.IDataRowFilter
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.bql.datasource.reader;

import com.jiuqi.bi.adhoc.datasource.reader.DataField;
import com.jiuqi.bi.adhoc.datasource.reader.DataPage;
import com.jiuqi.bi.adhoc.datasource.reader.DataTable;
import com.jiuqi.bi.adhoc.datasource.reader.IReadContext;
import com.jiuqi.bi.adhoc.engine.AdHocEngineException;
import com.jiuqi.bi.adhoc.engine.IDataListener;
import com.jiuqi.bi.adhoc.model.FieldInfo;
import com.jiuqi.bi.adhoc.model.TableInfo;
import com.jiuqi.bi.adhoc.model.TimeGranularity;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.IDataRowFilter;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.bql.datasource.MemoryDataRowFilter;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PeriodDimTableReader {
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;

    public long queryPeriodDimTable(IReadContext context, DataTable dataTable, IDataListener listener, DataPage dataPage) throws DataSetException, AdHocEngineException, ParseException {
        String periodEntityId = dataTable.getTable().getTable().getGuid();
        List<IPeriodRow> periodRows = this.getPeriodRows(periodEntityId);
        List fields = dataTable.getFields();
        HashMap<String, String> fieldNameMap = new HashMap<String, String>();
        Metadata<ColumnInfo> resultMetadata = this.createResultMetaData(fields, fieldNameMap);
        Metadata allFieldsMetadata = new Metadata();
        MemoryDataRowFilter filter = this.parsePeriodFilter(context, dataTable, (Metadata<ColumnInfo>)allFieldsMetadata);
        int rowCount = 0;
        MemoryDataSet result = new MemoryDataSet(null, resultMetadata);
        if (filter.isEmpty()) {
            this.loadPeroidDatas(periodRows, fields.stream().map(DataField::getField).collect(Collectors.toList()), (MemoryDataSet<ColumnInfo>)result);
        } else {
            DataSet<ColumnInfo> allFieldsesult = this.getFilteredResult(periodRows, (Metadata<ColumnInfo>)allFieldsMetadata, filter, dataTable.getTable().getTable().getFields());
            ArrayList<Integer> colIndexes = new ArrayList<Integer>();
            for (int i = 0; i < resultMetadata.getColumnCount(); ++i) {
                int index = allFieldsMetadata.indexOf((String)fieldNameMap.get(resultMetadata.getColumn(i).getName()));
                colIndexes.add(index);
            }
            for (DataRow row : allFieldsesult) {
                DataRow resultRow = result.add();
                for (int i = 0; i < colIndexes.size(); ++i) {
                    resultRow.setValue(i, row.getValue(((Integer)colIndexes.get(i)).intValue()));
                }
                resultRow.commit();
            }
        }
        listener.start(result.getMetadata());
        for (DataRow row : result) {
            listener.process(row);
        }
        rowCount = result.size();
        listener.finish();
        return rowCount;
    }

    private DataSet<ColumnInfo> getFilteredResult(List<IPeriodRow> periodRows, Metadata<ColumnInfo> allFieldsMetadata, MemoryDataRowFilter filter, List<FieldInfo> fieldInfos) throws DataSetException {
        MemoryDataSet dataSet = new MemoryDataSet(null, allFieldsMetadata);
        this.loadPeroidDatas(periodRows, fieldInfos, (MemoryDataSet<ColumnInfo>)dataSet);
        DataSet result = dataSet.filter((IDataRowFilter)filter);
        return result;
    }

    private MemoryDataRowFilter parsePeriodFilter(IReadContext context, DataTable dataTable, Metadata<ColumnInfo> allFieldsMetadata) throws ParseException {
        ExecutorContext exeContext = new ExecutorContext(this.dataDefinitionController);
        QueryContext qContext = new QueryContext(context, exeContext, dataTable.getTable().getTableName(), null);
        qContext.setMetadata(allFieldsMetadata);
        qContext.getTableInfoMap().put(dataTable.getTable().getTableName(), dataTable.getTable().getTable());
        MemoryDataRowFilter filter = new MemoryDataRowFilter(qContext);
        for (FieldInfo fieldInfo : dataTable.getTable().getTable().getFields()) {
            Column column = new Column(fieldInfo.getName(), fieldInfo.getDataType());
            allFieldsMetadata.addColumn(column);
        }
        for (DataField field : dataTable.getFields()) {
            this.parsePeriodFieldFilter(qContext, filter, field);
        }
        if (StringUtils.isNotEmpty((String)dataTable.getFilter())) {
            qContext.getLogger().debug("dataTableFilter (" + dataTable.getFilter() + ") append to rowFilter");
            filter.appendFilter(dataTable.getFilter());
        }
        return filter;
    }

    private MemoryDataRowFilter parsePeriodFilter(IReadContext context, String filter, Metadata<ColumnInfo> allFieldsMetadata, TableInfo periodTableInfo) throws ParseException {
        ExecutorContext exeContext = new ExecutorContext(this.dataDefinitionController);
        QueryContext qContext = new QueryContext(context, exeContext, periodTableInfo.getName(), null);
        qContext.setMetadata(allFieldsMetadata);
        qContext.getTableInfoMap().put(periodTableInfo.getName(), periodTableInfo);
        MemoryDataRowFilter rowFilter = new MemoryDataRowFilter(qContext);
        rowFilter.appendFilter(filter);
        for (FieldInfo fieldInfo : periodTableInfo.getFields()) {
            Column column = new Column(fieldInfo.getName(), fieldInfo.getDataType());
            allFieldsMetadata.addColumn(column);
        }
        return rowFilter;
    }

    public void periodFilterToMasterKeys(QueryContext qContext, DimensionValueSet masterKeys, DataTable dataTable) throws ParseException, DataSetException {
        Metadata allFieldsMetadata = new Metadata();
        MemoryDataRowFilter filter = this.parsePeriodFilter(qContext.getReadContext(), dataTable, (Metadata<ColumnInfo>)allFieldsMetadata);
        if (!filter.isEmpty()) {
            qContext.getLogger().debug("periodFilter :" + filter.toString());
            String periodEntityId = dataTable.getTable().getTable().getGuid();
            List<IPeriodRow> periodRows = this.getPeriodRows(periodEntityId);
            DataSet<ColumnInfo> result = this.getFilteredResult(periodRows, (Metadata<ColumnInfo>)allFieldsMetadata, filter, dataTable.getTable().getTable().getFields());
            int periodCodeIndex = allFieldsMetadata.indexOf(PeriodTableColumn.CODE.getCode());
            ArrayList<String> filteredKeys = new ArrayList<String>();
            for (DataRow row : result) {
                filteredKeys.add(row.getString(periodCodeIndex));
            }
            masterKeys.setValue("DATATIME", filteredKeys);
        }
    }

    public void periodFilterToMasterKeys(QueryContext qContext, DimensionValueSet masterKeys, String filter, TableInfo periodTableInfo) throws ParseException, DataSetException {
        Metadata allFieldsMetadata = new Metadata();
        MemoryDataRowFilter rowFilter = this.parsePeriodFilter(qContext.getReadContext(), filter, (Metadata<ColumnInfo>)allFieldsMetadata, periodTableInfo);
        if (!filter.isEmpty()) {
            String periodEntityId = periodTableInfo.getGuid();
            List<IPeriodRow> periodRows = this.getPeriodRows(periodEntityId);
            DataSet<ColumnInfo> result = this.getFilteredResult(periodRows, (Metadata<ColumnInfo>)allFieldsMetadata, rowFilter, periodTableInfo.getFields());
            int periodCodeIndex = allFieldsMetadata.indexOf(PeriodTableColumn.CODE.getCode());
            ArrayList<String> filteredKeys = new ArrayList<String>();
            for (DataRow row : result) {
                filteredKeys.add(row.getString(periodCodeIndex));
            }
            masterKeys.setValue("DATATIME", filteredKeys);
        }
    }

    public IPeriodEntityAdapter getPeriodEntityAdapter() {
        return this.periodEngineService.getPeriodAdapter();
    }

    private Metadata<ColumnInfo> createResultMetaData(List<DataField> fields, Map<String, String> fieldNameMap) {
        Metadata metadata = new Metadata();
        for (DataField field : fields) {
            FieldInfo fieldInfo = field.getField();
            Column column = new Column(field.getReturnName(), fieldInfo.getDataType());
            if (!field.isVisible()) continue;
            metadata.addColumn(column);
            fieldNameMap.put(field.getReturnName(), field.getField().getName());
        }
        return metadata;
    }

    private void loadPeroidDatas(List<IPeriodRow> periodRows, List<FieldInfo> fieldInfos, MemoryDataSet<ColumnInfo> dataSet) throws DataSetException {
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
        for (IPeriodRow period : periodRows) {
            DataRow row = dataSet.add();
            for (int i = 0; i < fieldInfos.size(); ++i) {
                FieldInfo fieldInfo = fieldInfos.get(i);
                if (fieldInfo.isTimeKey()) {
                    row.setValue(i, (Object)period.getTimeKey());
                    continue;
                }
                if (TimeGranularity.YEAR == fieldInfo.getTimegranularity()) {
                    row.setValue(i, (Object)period.getYear());
                    continue;
                }
                if (TimeGranularity.QUARTER == fieldInfo.getTimegranularity()) {
                    row.setValue(i, (Object)period.getQuarter());
                    continue;
                }
                if (TimeGranularity.MONTH == fieldInfo.getTimegranularity()) {
                    row.setValue(i, (Object)period.getMonth());
                    continue;
                }
                if (TimeGranularity.DAY == fieldInfo.getTimegranularity()) {
                    row.setValue(i, (Object)period.getDay());
                    continue;
                }
                if ("SYS_DAYS".equals(fieldInfo.getName())) {
                    row.setValue(i, (Object)period.getDays());
                    continue;
                }
                if ("SYS_LASTDAY".equals(fieldInfo.getName())) {
                    row.setValue(i, (Object)dFormat.format(period.getEndDate()));
                    continue;
                }
                if (!PeriodTableColumn.CODE.getCode().equals(fieldInfo.getName())) continue;
                row.setValue(i, (Object)period.getCode());
            }
            row.commit();
        }
    }

    private void parsePeriodFieldFilter(QueryContext qContext, MemoryDataRowFilter filter, DataField field) throws ParseException {
        List values = field.getValues();
        if (values != null && values.size() > 0) {
            filter.addValuesFilter(qContext.getMetadata().indexOf(field.getField().getName()), values);
        } else if (StringUtils.isNotEmpty((String)field.getFilter())) {
            filter.appendFilter(field.getFilter());
        }
    }

    public List<IPeriodRow> getPeriodRows(String periodEntityId) {
        IPeriodProvider periodProvider = this.getPeriodEntityAdapter().getPeriodProvider(periodEntityId);
        List periodRows = periodProvider.getPeriodItems();
        return periodRows;
    }

    public String getCurrentPeriod(String periodEntityId) {
        IPeriodProvider periodProvider = this.getPeriodEntityAdapter().getPeriodProvider(periodEntityId);
        IPeriodRow periodRow = periodProvider.getCurPeriod();
        return periodRow.getCode();
    }
}

