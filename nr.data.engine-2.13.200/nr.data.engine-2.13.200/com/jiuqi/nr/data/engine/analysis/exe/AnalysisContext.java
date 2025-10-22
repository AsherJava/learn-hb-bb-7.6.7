/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.executors.StatUnit
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.reader.QueryFieldInfo
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.engine.analysis.exe;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisConditionJudger;
import com.jiuqi.nr.data.engine.analysis.exe.DataRowComparator;
import com.jiuqi.nr.data.engine.analysis.exe.ParsedFloatRegionConfig;
import com.jiuqi.nr.data.engine.analysis.exe.ParsedGroupingConfig;
import com.jiuqi.nr.data.engine.analysis.exe.ParsedOrderField;
import com.jiuqi.nr.data.engine.analysis.exe.query.AnalysisMemoryDataSetReader;
import com.jiuqi.nr.data.engine.analysis.exe.query.AnalysisRowData;
import com.jiuqi.nr.data.engine.analysis.exe.query.grouping.AnalysisGroupingRow;
import com.jiuqi.nr.data.engine.analysis.exe.query.grouping.ConstGroupingColumn;
import com.jiuqi.nr.data.engine.analysis.exe.query.grouping.ExpressionGroupingColumn;
import com.jiuqi.nr.data.engine.analysis.exe.query.grouping.IAnalysisGroupingColumn;
import com.jiuqi.nr.data.engine.analysis.exe.query.grouping.StatGroupingColumn;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnalysisContext
extends QueryContext {
    private static final Logger logger = LogFactory.getLogger(AnalysisContext.class);
    private DimensionValueSet destMasterKeys;
    private final Set<QueryTable> destTables = new HashSet<QueryTable>();
    private ParsedFloatRegionConfig floatConfig;
    private IDataUpdator updator;
    private Map<QueryField, QueryFieldInfo> fieldInfoSeach;
    private MemoryDataSet<QueryFieldInfo> updateDataSet;
    private DataRow unCommitRow;
    private double inputOrder = -1.0;
    private Map<String, AnalysisGroupingRow> groupingRows;
    private Map<Integer, List<IExpression>> groupingDestFieldIndexes;
    private ReportFormulaParser formulaParser;
    private final Map<String, Integer> keyOrderIndexes = new HashMap<String, Integer>();
    private String batchKey;
    private AnalysisConditionJudger conditionJuder;
    private final Map<Long, StatUnit> statUnitMap = new HashMap<Long, StatUnit>();

    public AnalysisContext(ExecutorContext exeContext, QueryParam queryParam, IMonitor monitor) throws ParseException {
        super(exeContext, queryParam, monitor);
        this.setBatch(true);
    }

    public void setMasterKeys(DimensionValueSet masterKeys) {
        super.setMasterKeys(masterKeys);
        if (masterKeys != null) {
            for (int i = 0; i < masterKeys.size(); ++i) {
                if (!(masterKeys.getValue(i) instanceof List)) continue;
                String dimName = masterKeys.getName(i);
                List values = (List)masterKeys.getValue(i);
                this.batchKey = dimName;
                for (int n = 0; n < values.size(); ++n) {
                    Object value = values.get(n);
                    this.keyOrderIndexes.put(value.toString(), n);
                }
            }
        }
    }

    private Map<QueryField, QueryFieldInfo> getFieldInfoSeach() {
        if (this.fieldInfoSeach == null) {
            this.fieldInfoSeach = new HashMap<QueryField, QueryFieldInfo>();
        }
        return this.fieldInfoSeach;
    }

    public int findIndex(QueryField queryField) {
        QueryFieldInfo info = this.getFieldInfoSeach().get(queryField);
        if (info == null) {
            return -1;
        }
        return info.index;
    }

    public QueryFieldInfo findQueryFieldInfo(QueryField queryField) {
        return this.getFieldInfoSeach().get(queryField);
    }

    public void setValue(int index, Object value) {
        if (this.unCommitRow == null) {
            if (this.updateDataSet.size() == 0) {
                Metadata metadata = this.updateDataSet.getMetadata();
                metadata.addColumn(new Column("Grouping_flag", 5, null));
                metadata.addColumn(new Column("detail_order", 5, null));
            }
            this.unCommitRow = this.updateDataSet.add();
        }
        if (value == null) {
            this.unCommitRow.setNull(index);
        } else {
            this.unCommitRow.setValue(index, value);
        }
    }

    public void commitRow() throws Exception {
        if (this.unCommitRow != null) {
            Metadata metadata = this.updateDataSet.getMetadata();
            AnalysisMemoryDataSetReader dataReader = (AnalysisMemoryDataSetReader)this.getDataReader();
            AnalysisRowData rowDatas = dataReader.getRowDatas();
            if (rowDatas != null) {
                this.trySumByGroups((Metadata<QueryFieldInfo>)metadata, rowDatas);
                this.unCommitRow.setValue(metadata.getColumnCount() - 2, (Object)rowDatas.getGroupFieldValueStr());
                this.unCommitRow.setValue(metadata.getColumnCount() - 1, (Object)this.updateDataSet.size());
                this.unCommitRow.commit();
                this.unCommitRow = null;
            }
        }
    }

    private void trySumByGroups(Metadata<QueryFieldInfo> metadata, AnalysisRowData rowDatas) {
        ParsedGroupingConfig groupingConfig = this.getGroupingConfig();
        if (groupingConfig != null && !groupingConfig.getGroupingQueryFields().isEmpty()) {
            try {
                String groupFieldValueStr = rowDatas.getGroupFieldValueStr();
                List<Object> groupFieldValues = rowDatas.getGroupFieldValues();
                if (StringUtils.isNotEmpty((String)groupFieldValueStr)) {
                    int codeLength = groupFieldValueStr.length();
                    for (int level = groupingConfig.getLevelLength().length - 1; level >= 0; --level) {
                        int levelLength = groupingConfig.getLevelLength()[level];
                        String groupKey = this.getNewKeyValue(groupFieldValueStr, codeLength, levelLength);
                        AnalysisGroupingRow groupingRow = this.getGroupingRows().get(groupKey);
                        if (groupingRow == null) {
                            groupingRow = this.createGroupingRow(metadata, level, groupKey);
                            this.getGroupingRows().put(groupKey, groupingRow);
                            groupingRow.setGroupFieldValues(groupFieldValues);
                            groupingRow.setGroupFieldValueStr(groupFieldValueStr);
                        }
                        for (int i = 0; i < metadata.getColumnCount(); ++i) {
                            groupingRow.writeValue(this, i, this.unCommitRow.getValue(i));
                        }
                    }
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
    }

    private AnalysisGroupingRow createGroupingRow(Metadata<QueryFieldInfo> metadata, int level, String groupFieldValueStr) {
        IAnalysisGroupingColumn[] columns = new IAnalysisGroupingColumn[metadata.getColumnCount()];
        for (int index : this.groupingDestFieldIndexes.keySet()) {
            IExpression destExp = this.groupingDestFieldIndexes.get(index).get(level);
            columns[index] = new ExpressionGroupingColumn(destExp);
        }
        columns[columns.length - 2] = new ConstGroupingColumn(groupFieldValueStr);
        columns[columns.length - 1] = new ConstGroupingColumn(level - 100);
        for (int i = 0; i < columns.length; ++i) {
            if (columns[i] != null) continue;
            columns[i] = new StatGroupingColumn(metadata.getColumn(i).getDataType());
        }
        AnalysisGroupingRow groupingRow = new AnalysisGroupingRow(columns);
        return groupingRow;
    }

    public QueryFieldInfo putIndex(DataModelDefinitionsCache cache, QueryField queryField) {
        Map<QueryField, QueryFieldInfo> fieldInfoMap;
        QueryFieldInfo queryFieldInfo;
        if (this.updateDataSet == null) {
            Metadata metadata = new Metadata(this.updateDataSet);
            this.updateDataSet = new MemoryDataSet(QueryFieldInfo.class, metadata);
        }
        if ((queryFieldInfo = (fieldInfoMap = this.getFieldInfoSeach()).get(queryField)) == null) {
            ColumnModelDefine field = cache.findField(queryField.getUID());
            queryFieldInfo = new QueryFieldInfo(queryField, field, fieldInfoMap.size());
            this.updateDataSet.getMetadata().addColumn(new Column(queryFieldInfo.queryField.getFieldName(), queryFieldInfo.queryField.getDataType(), (Object)queryFieldInfo));
            fieldInfoMap.put(queryField, queryFieldInfo);
        }
        return queryFieldInfo;
    }

    private IDataUpdator getUpdator() throws Exception {
        if (this.updator == null) {
            Metadata metadata = this.updateDataSet.getMetadata();
            IDataUpdator updator = this.createDestDataUpdater((Metadata<QueryFieldInfo>)metadata);
            this.addReusltsToUpdator((Metadata<QueryFieldInfo>)metadata, updator);
            return updator;
        }
        return this.updator;
    }

    private void addReusltsToUpdator(Metadata<QueryFieldInfo> metadata, IDataUpdator updator) throws DataTypeException {
        int fieldCount = updator.getFieldsInfo().getFieldCount();
        for (DataRow row : this.updateDataSet) {
            IDataRow insertedRow = updator.addInsertedRow();
            for (int i = 0; i < metadata.getColumnCount() - 2; ++i) {
                insertedRow.setValue(i, row.getValue(i));
            }
            if (!(this.inputOrder > 0.0)) continue;
            insertedRow.setValue(fieldCount - 1, (Object)this.inputOrder);
            this.inputOrder += 1.0;
        }
    }

    private IDataUpdator createDestDataUpdater(Metadata<QueryFieldInfo> metadata) throws Exception {
        IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        IDataQuery request = accessProvider.newDataQuery();
        TableModelRunInfo tableInfo = null;
        for (int index = 0; index < metadata.getColumnCount() - 2; ++index) {
            QueryFieldInfo queryFieldInfo = (QueryFieldInfo)metadata.getColumn(index).getInfo();
            if (queryFieldInfo == null) continue;
            ColumnModelDefine fieldDefine = queryFieldInfo.fieldDefine;
            FieldDefine field = this.getExeContext().getCache().getDataModelDefinitionsCache().getFieldDefine(fieldDefine);
            request.addColumn(field);
            if (tableInfo != null) continue;
            TableModelDefine tableModel = this.getExeContext().getCache().getDataModelDefinitionsCache().getTableModel(fieldDefine);
            tableInfo = this.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(tableModel.getName());
        }
        if (tableInfo != null && tableInfo.getOrderField() != null) {
            ColumnModelDefine orderField = tableInfo.getOrderField();
            FieldDefine field = this.getExeContext().getCache().getDataModelDefinitionsCache().getFieldDefine(orderField);
            request.addColumn(field);
            this.inputOrder = 1.0;
        }
        request.setQueryParam(this.getQueryParam());
        request.setMasterKeys(this.destMasterKeys);
        request.setDefaultGroupName(this.getDefaultGroupName());
        for (String dimension : this.getTempAssistantTables().keySet()) {
            TempAssistantTable tempTable = (TempAssistantTable)this.getTempAssistantTables().get(dimension);
            request.setTempAssistantTable(dimension, tempTable);
        }
        IDataUpdator updator = request.openForUpdate(this.getExeContext(), true);
        return updator;
    }

    public void commitUpdator() throws Exception {
        if (this.updateDataSet == null) {
            return;
        }
        if (this.groupingRows != null) {
            if (this.getGroupingConfig().isDiscardDetail()) {
                this.updateDataSet.clear();
            }
            this.addGroupingRows();
        }
        if (this.floatConfig != null) {
            this.doSort();
            this.topN();
        }
        this.getUpdator().commitChanges();
    }

    private void topN() {
        if (this.floatConfig.getTopN() > 0) {
            for (int i = this.updateDataSet.size() - 1; i >= this.floatConfig.getTopN(); --i) {
                this.updateDataSet.remove(i);
            }
        }
    }

    private void doSort() {
        DataRowComparator comparator = new DataRowComparator((Metadata<QueryFieldInfo>)this.updateDataSet.getMetadata());
        if (this.floatConfig.getOrderFields().size() > 0) {
            for (int index = 0; index < this.floatConfig.getOrderFields().size(); ++index) {
                ParsedOrderField parsedOrderField = (ParsedOrderField)this.floatConfig.getOrderFields().get(index);
                int columnIndex = this.updateDataSet.getMetadata().indexOf(parsedOrderField.getField().getFieldName());
                if (columnIndex < 0) continue;
                comparator.addOrderColumn(columnIndex, parsedOrderField.isDesc());
            }
        } else if (this.groupingDestFieldIndexes != null) {
            comparator.addOrderColumn(this.updateDataSet.getMetadata().size() - 2, false);
            comparator.addOrderColumn(this.updateDataSet.getMetadata().size() - 1, false);
        }
        if (comparator.orderSize() > 0) {
            this.updateDataSet.sort((Comparator)comparator);
        }
    }

    private void addGroupingRows() throws DataSetException {
        for (AnalysisGroupingRow groupingRow : this.groupingRows.values()) {
            int i;
            int count = this.updateDataSet.getMetadata().getColumnCount();
            DataRow dataRow = this.updateDataSet.add();
            List<Object> groupFieldValues = groupingRow.getGroupFieldValues();
            for (i = 0; i < groupFieldValues.size(); ++i) {
                QueryField queryField = this.floatConfig.getParsedGroupingConfig().getGroupingQueryFields().get(i);
                Object value = groupFieldValues.get(i);
                this.writeData(queryField, value);
            }
            for (i = 0; i < count; ++i) {
                dataRow.setValue(i, groupingRow.readValue(this, i));
            }
            dataRow.commit();
        }
    }

    private String getNewKeyValue(String groupFieldValue, int codeLength, int levelLength) {
        String newValue = groupFieldValue;
        if (levelLength < codeLength) {
            newValue = groupFieldValue.substring(0, levelLength);
        }
        return newValue;
    }

    public void addDestTable(QueryTable table) {
        this.destTables.add(table);
    }

    public boolean isDest(QueryTable table) {
        return this.destTables.contains(table);
    }

    public ParsedFloatRegionConfig getFloatConfig() {
        return this.floatConfig;
    }

    public void setFloatConfig(ParsedFloatRegionConfig floatConfig) {
        this.floatConfig = floatConfig;
    }

    public void setRowKey(DimensionValueSet rowKey) {
        super.setRowKey(rowKey);
    }

    public boolean isFloat() {
        return this.floatConfig != null;
    }

    public DimensionValueSet getDestMasterKeys() {
        return this.destMasterKeys;
    }

    public void setDestMasterKeys(DimensionValueSet destMasterKeys) {
        this.destMasterKeys = destMasterKeys;
    }

    private Map<String, AnalysisGroupingRow> getGroupingRows() {
        ParsedGroupingConfig groupingConfig;
        if (this.groupingRows == null && (groupingConfig = this.getGroupingConfig()) != null && groupingConfig.getGroupingQueryFields().size() > 0) {
            this.groupingRows = new HashMap<String, AnalysisGroupingRow>();
        }
        return this.groupingRows;
    }

    public void buildDestFieldIndexes() throws ParseException {
        ParsedGroupingConfig groupingConfig;
        if (this.groupingDestFieldIndexes == null && (groupingConfig = this.getGroupingConfig()) != null) {
            this.groupingDestFieldIndexes = new HashMap<Integer, List<IExpression>>();
            Map<QueryField, List<IExpression>> levelExpressions = groupingConfig.getLevelExpressions();
            for (QueryField queryField : levelExpressions.keySet()) {
                int index = this.findIndex(queryField);
                if (index < 0) {
                    QueryFieldInfo info = this.putIndex(this.getExeContext().getCache().getDataModelDefinitionsCache(), queryField);
                    index = info.index;
                }
                this.groupingDestFieldIndexes.put(index, levelExpressions.get(queryField));
            }
        }
    }

    public ParsedGroupingConfig getGroupingConfig() {
        if (this.floatConfig != null) {
            return this.floatConfig.getParsedGroupingConfig();
        }
        return null;
    }

    public ReportFormulaParser getFormulaParser() throws ParseException {
        if (this.formulaParser != null) {
            return this.formulaParser;
        }
        return super.getFormulaParser();
    }

    public void setFormulaParser(ReportFormulaParser formulaParser) {
        this.formulaParser = formulaParser;
    }

    public String getBatchKey() {
        return this.batchKey;
    }

    public Map<String, Integer> getKeyOrderIndexes() {
        return this.keyOrderIndexes;
    }

    public AnalysisConditionJudger getConditionJuder() {
        return this.conditionJuder;
    }

    public void setConditionJuder(AnalysisConditionJudger conditionJuder) {
        this.conditionJuder = conditionJuder;
    }

    public Map<Long, StatUnit> getStatUnitMap() {
        return this.statUnitMap;
    }
}

