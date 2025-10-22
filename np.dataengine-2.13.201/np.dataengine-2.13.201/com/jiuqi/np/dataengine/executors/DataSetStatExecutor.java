/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryRegion;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.EvalExecutor;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.ExprExecContext;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DataSetStatExecutor {
    private ArrayList<StatItem> statItems = new ArrayList();
    private EvalExecutor evalExecutor = null;
    private List<QueryField> dataSetQueryFields = new ArrayList<QueryField>();
    private DataSet<FieldDefine> dataSet;
    private IDataAccessProvider dataAccessProvider;
    private boolean needQueryData;

    public DataSetStatExecutor(DataSet<FieldDefine> dataSet, IDataAccessProvider dataAccessProvider, boolean needQueryData) {
        this.dataSet = dataSet;
        this.dataAccessProvider = dataAccessProvider;
        this.needQueryData = needQueryData;
    }

    public int size() {
        return this.statItems.size();
    }

    public StatItem get(int index) {
        return this.statItems.get(index);
    }

    public int add(StatItem statItem) {
        this.statItems.add(statItem);
        return this.statItems.size() - 1;
    }

    public void remove(StatItem statItem) {
        this.statItems.remove(statItem);
    }

    public void combine(DataSetStatExecutor another) {
        this.statItems.addAll(another.statItems);
    }

    public void resetStatItems() {
        int count = this.statItems.size();
        for (int i = 0; i < count; ++i) {
            StatItem statItem = this.statItems.get(i);
            statItem.reset();
        }
    }

    public EvalExecutor getEvalExecutor(ExecutorContext executorContext, DimensionValueSet masterKeys, QueryParam queryParam) throws ParseException {
        if (this.evalExecutor == null) {
            QueryContext qContext = new QueryContext(executorContext, queryParam, null);
            qContext.setMasterKeys(masterKeys);
            this.evalExecutor = new EvalExecutor(new ExprExecContext(qContext));
        }
        return this.evalExecutor;
    }

    public List<QueryField> getDataSetQueryFields() {
        return this.dataSetQueryFields;
    }

    public boolean doStatistic() throws Exception {
        QueryContext qContext = this.evalExecutor.context;
        HashMap<QueryField, Integer> fieldIndexMap = new HashMap<QueryField, Integer>();
        if (this.needQueryData) {
            this.dataSet = this.doQuery(qContext);
        }
        if (fieldIndexMap.isEmpty()) {
            for (QueryField queryField : this.dataSetQueryFields) {
                int index = this.dataSet.getMetadata().indexOf(queryField.getFieldName());
                fieldIndexMap.put(queryField, index);
            }
        }
        for (DataRow row : this.dataSet) {
            for (Map.Entry entry : fieldIndexMap.entrySet()) {
                QueryField queryField = (QueryField)entry.getKey();
                int index = (Integer)entry.getValue();
                Object resultValue = row.getValue(index);
                if (!this.needQueryData) {
                    resultValue = DataEngineConsts.formatData(queryField, resultValue);
                }
                qContext.writeData(queryField, resultValue);
            }
            this.evalExecutor.doEvaluate();
            int count = this.statItems.size();
            for (int j = 0; j < count; ++j) {
                StatItem statItem = this.statItems.get(j);
                statItem.runStatistic();
            }
        }
        return true;
    }

    private MemoryDataSet<FieldDefine> doQuery(QueryContext qContext) throws ParseException, ExpressionException, ExecuteException, Exception, DataSetException {
        Metadata metaData = new Metadata();
        MemoryDataSet dataSet = new MemoryDataSet(metaData);
        TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.dataSetQueryFields.get(0).getTableName());
        QuerySqlBuilder sqlBuilder = new QuerySqlBuilder();
        QueryRegion queryRegion = new QueryRegion(tableInfo.getDimensions(), sqlBuilder);
        for (QueryField queryField : this.dataSetQueryFields) {
            metaData.addColumn(new Column(queryField.getFieldName(), queryField.getDataType()));
            queryRegion.addQueryField(queryField);
        }
        sqlBuilder.setQueryRegion(queryRegion);
        sqlBuilder.setForUpdateOnly(false);
        sqlBuilder.setQueryParam(qContext.getQueryParam());
        queryRegion.doInit(qContext);
        queryRegion.runQuery(qContext, null);
        IQueryFieldDataReader dataReader = qContext.getDataReader();
        ArrayList<Integer> queryFieldIndexes = new ArrayList<Integer>();
        for (QueryField queryField : this.dataSetQueryFields) {
            queryFieldIndexes.add(dataReader.findIndex(queryField));
        }
        while (dataReader.next()) {
            DataRow row = dataSet.add();
            for (int i = 0; i < metaData.getColumnCount(); ++i) {
                row.setValue(i, dataReader.readData(i));
            }
        }
        return dataSet;
    }
}

