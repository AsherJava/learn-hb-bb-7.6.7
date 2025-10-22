/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryRegion;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.DataSetStatExecutor;
import com.jiuqi.np.dataengine.executors.EvalExecutor;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.StatisticInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataSetExprEvaluator
implements IDataSetExprEvaluator {
    private DataSet<FieldDefine> dataSet;
    private IDataAccessProvider dataAccessProvider;
    private IExpression exp;
    private QueryContext qContext;
    private List<QueryField> dataSetQueryFields = new ArrayList<QueryField>();
    private QueryParam queryParam;
    private boolean hasFieldValueProcessor;

    public DataSetExprEvaluator(DataSet<FieldDefine> dataSet) {
        this.dataSet = dataSet;
    }

    public DataSetExprEvaluator(DataSet<FieldDefine> dataSet, IDataAccessProvider dataAccessProvider) {
        this.dataSet = dataSet;
        this.dataAccessProvider = dataAccessProvider;
    }

    @Override
    public AbstractData evaluate(DataRow dataRow) throws Exception {
        for (QueryField queryField : this.dataSetQueryFields) {
            int index = this.dataSet.getMetadata().indexOf(queryField.getFieldCode());
            Object resultValue = dataRow.getValue(index);
            if (!this.hasFieldValueProcessor) {
                resultValue = DataEngineConsts.formatData(queryField, resultValue);
            }
            this.qContext.writeData(queryField, resultValue);
        }
        return AbstractData.valueOf(this.exp.evaluate((IContext)this.qContext), this.exp.getType((IContext)this.qContext));
    }

    @Override
    public boolean judge(DataRow dataRow) throws Exception {
        for (QueryField queryField : this.dataSetQueryFields) {
            int index = this.dataSet.getMetadata().indexOf(queryField.getFieldCode());
            Object resultValue = DataEngineConsts.formatData(queryField, dataRow.getValue(index));
            this.qContext.writeData(queryField, resultValue);
        }
        return this.exp.judge((IContext)this.qContext);
    }

    @Override
    public void prepare(ExecutorContext executorContext, DimensionValueSet dimensionValueSet, String formula) throws Exception {
        this.qContext = new QueryContext(executorContext, this.queryParam, new AbstractMonitor());
        this.qContext.setMasterKeys(dimensionValueSet);
        this.dataSetQueryFields.clear();
        IFmlExecEnvironment env = executorContext.getEnv();
        this.hasFieldValueProcessor = env != null && env.getFieldValueProcessor() != null;
        ReportFormulaParser parser = this.qContext.getExeContext().getCache().getFormulaParser(this.qContext.getExeContext());
        this.exp = parser.parseEval(formula, (IContext)this.qContext);
        ArrayList<DynamicDataNode> dataNodes = new ArrayList<DynamicDataNode>();
        for (IASTNode child : this.exp) {
            if (!(child instanceof DynamicDataNode)) continue;
            dataNodes.add((DynamicDataNode)child);
        }
        QueryFields queryFields = null;
        ArrayList<DataSetStatExecutor> statExecutors = new ArrayList<DataSetStatExecutor>();
        for (DynamicDataNode dataNode : dataNodes) {
            QueryField queryField = dataNode.getQueryField();
            if (dataNode.getStatisticInfo() != null) {
                DataSetStatExecutor statExecutor = new DataSetStatExecutor(this.dataSet, this.dataAccessProvider, this.hasFieldValueProcessor);
                statExecutors.add(statExecutor);
                StatisticInfo statisticInfo = dataNode.getStatisticInfo();
                StatItem statItem = this.qContext.getStatItemCollection().creatStatItem(this.qContext, statisticInfo);
                statExecutor.getDataSetQueryFields().add(queryField);
                statExecutor.add(statItem);
                EvalExecutor evalExecutor = statExecutor.getEvalExecutor(executorContext, dimensionValueSet, this.queryParam);
                int valueIndex = evalExecutor.add((IASTNode)new DynamicDataNode(dataNode.getQueryField()));
                statItem.setValue(evalExecutor, valueIndex);
                if (statisticInfo.condNode == null) continue;
                int condIndex = evalExecutor.add(statisticInfo.condNode);
                statItem.setCondition(evalExecutor, condIndex);
                for (IASTNode child : statisticInfo.condNode) {
                    if (!(child instanceof DynamicDataNode)) continue;
                    DynamicDataNode condDataNode = (DynamicDataNode)child;
                    statExecutor.getDataSetQueryFields().add(condDataNode.getQueryField());
                }
                continue;
            }
            if (!this.dataSet.getMetadata().contains(queryField.getFieldCode())) {
                if (queryFields == null) {
                    queryFields = new QueryFields();
                }
                queryFields.add(queryField);
                continue;
            }
            this.dataSetQueryFields.add(queryField);
        }
        for (DataSetStatExecutor statExecutor : statExecutors) {
            statExecutor.doStatistic();
        }
        if (queryFields != null) {
            QuerySqlBuilder sqlBuilder = new QuerySqlBuilder();
            QueryRegion queryRegion = new QueryRegion(dimensionValueSet.getDimensionSet(), sqlBuilder);
            queryRegion.setType(1);
            queryRegion.addQueryFields(queryFields);
            sqlBuilder.setQueryRegion(queryRegion);
            boolean useDNASql = this.qContext.getExeContext().isUseDnaSql();
            sqlBuilder.setUseDNASql(useDNASql);
            sqlBuilder.setForUpdateOnly(false);
            sqlBuilder.setQueryParam(this.queryParam);
            queryRegion.doInit(this.qContext);
            queryRegion.runQuery(this.qContext, null);
            this.qContext.getDataReader().next();
        }
    }

    public void setQueryParam(QueryParam queryParam) {
        this.queryParam = queryParam;
    }

    @Override
    public void close() throws IOException {
        this.queryParam.closeConnection();
    }
}

