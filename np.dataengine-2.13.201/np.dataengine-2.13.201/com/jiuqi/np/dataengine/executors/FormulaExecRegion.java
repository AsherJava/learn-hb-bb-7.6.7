/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.math.Round
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.BoolValue
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.math.Round;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.BoolValue;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.collector.FmlExecuteCollector;
import com.jiuqi.np.dataengine.collector.FocusInfoCollector;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataRegion;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryRegion;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.CalcExecutor;
import com.jiuqi.np.dataengine.executors.CheckExecutor;
import com.jiuqi.np.dataengine.executors.DNAFmlDataCommittor;
import com.jiuqi.np.dataengine.executors.EvalExecutor;
import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.executors.ExprExecRegion;
import com.jiuqi.np.dataengine.executors.StatExecutor;
import com.jiuqi.np.dataengine.fml.account.AccountQueryRegion;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.log.LogRow;
import com.jiuqi.np.dataengine.log.LogType;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.query.MemorySteamLoader;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import com.jiuqi.np.dataengine.query.UpdateDatas;
import com.jiuqi.np.dataengine.reader.MemoryDataSetReader;
import java.sql.SQLException;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormulaExecRegion
extends ExprExecRegion
implements DNAFmlDataCommittor.FmlDataCommittor {
    private static final Logger logger = LoggerFactory.getLogger(FormulaExecRegion.class);
    public boolean forbidRegionDivide;
    private final QueryParam queryParam;
    protected UpdateDatas updateDatas;

    public FormulaExecRegion(QueryContext context, DimensionSet masterDimensions, DataRegion scope, QueryParam queryParam) {
        super(context, scope);
        this.queryParam = queryParam;
        QuerySqlBuilder sqlBuilder = this.queryRegion.getSqlBuilder();
        if (sqlBuilder != null) {
            sqlBuilder.setQueryParam(queryParam);
            sqlBuilder.setMasterDimensions(masterDimensions);
        }
    }

    @Override
    public QueryRegion createQueryRegion(QueryContext context, DataRegion scope) {
        QueryRegion queryRegion = null;
        if (scope.isAccount()) {
            queryRegion = new AccountQueryRegion(scope.getDimensions());
        } else {
            QuerySqlBuilder sqlBuilder = new QuerySqlBuilder();
            if (!context.isDebug() && !context.outFMLPlan()) {
                sqlBuilder.setSqlSoftParse(true);
            }
            sqlBuilder.setIgnoreDefaultOrderBy(true);
            queryRegion = new QueryRegion(scope.getDimensions(), sqlBuilder);
        }
        queryRegion.setType(1);
        return queryRegion;
    }

    @Override
    protected boolean doInitialization(Object initInfo) throws ExecuteException {
        try {
            this.updateDatas = new UpdateDatas();
            this.context.setUpdateDatas(this.updateDatas);
            this.queryRegion.doInit(this.context);
        }
        catch (ParseException e) {
            throw new ExecuteException(e);
        }
        return super.doInitialization(initInfo);
    }

    @Override
    public void print() {
        this.printExecRegion();
    }

    @Override
    protected boolean doExecution(Object taskInfo) throws Exception {
        this.printExecRegion();
        boolean result = true;
        try {
            AbstractMonitor monitor = (AbstractMonitor)this.context.getMonitor();
            if (monitor.isCancel()) {
                monitor.step();
                return result;
            }
            FmlExecuteCollector collector = monitor.getCollector();
            this.queryRegion.setStatQuery(this.hasStatExecutor());
            MemoryDataSetReader dataReader = null;
            if (this.queryRegion.isFloatStreamCalc()) {
                long start = System.currentTimeMillis();
                this.queryRegion.createDataReader(this.context);
                dataReader = (MemoryDataSetReader)this.context.getDataReader();
                try (MemorySteamLoader loader = this.queryRegion.queryMemorySteamLoader(this.context, null);){
                    long exeCost = System.currentTimeMillis() - start;
                    dataReader.setStreamLoader(loader);
                    this.updateDatas.reset();
                    this.context.setUpdateDatas(this.updateDatas);
                    this.cloneFloatExpressions();
                    result = super.doExecution(taskInfo);
                    long totalCost = System.currentTimeMillis() - start;
                    if (collector != null) {
                        QuerySqlBuilder sqlBuilder = this.queryRegion.getSqlBuilder();
                        LogRow logRow = new LogRow(LogType.STREAM, exeCost, totalCost, dataReader.getColumnCount(), dataReader.getCurrentIndex() + 1, this.context.getPhysicalTableName(sqlBuilder.getPrimaryTable()), sqlBuilder.getSql(), this.context.getDateFormat());
                        collector.addSqlLogRow(logRow);
                    }
                }
                monitor.addRecordCount(dataReader.getCurrentIndex() + 1);
                monitor.addFieldCount(dataReader.getColumnCount());
                this.printRegionResult(dataReader, start);
            } else {
                dataReader = this.createDataReaderAndQuery(this.context);
                this.updateDatas.reset();
                this.context.setUpdateDatas(this.updateDatas);
                this.cloneFloatExpressions();
                result = super.doExecution(taskInfo);
            }
            this.commitData(this.context);
            if (dataReader != null) {
                dataReader.reset();
            }
            this.context.setDataReader(null);
            monitor.step();
        }
        catch (Exception e) {
            this.onException(this.context, e);
        }
        return result;
    }

    @Override
    protected boolean executeChildren(Object subTask, BoolValue somethingDone) throws Exception {
        boolean result = super.executeChildren(subTask, somethingDone);
        if (this.queryRegion.isFloatStreamCalc() && this.updateDatas.getUpdateRecordSize() >= DataEngineConsts.MAX_DATA_SIZE) {
            this.commitData(this.context);
            this.updateDatas.reset();
            this.context.setUpdateDatas(this.updateDatas);
        }
        return result;
    }

    @Override
    protected Object disassembleTask(Object taskInfo) {
        QueryContext qContext = this.context;
        if (this.context.getMonitor().isCancel()) {
            return null;
        }
        MemoryDataSetReader dataReader = null;
        try {
            if (qContext.getDataReader() instanceof MemoryDataSetReader) {
                dataReader = (MemoryDataSetReader)qContext.getDataReader();
            }
            if (dataReader == null) {
                dataReader = this.createDataReaderAndQuery(qContext);
            }
            if (dataReader != null) {
                if (this.queryRegion.isNeedMemoryFilter()) {
                    while (dataReader.next()) {
                        if (!this.queryRegion.judge(qContext)) continue;
                        DimensionValueSet rowKey = dataReader.getCurrentRowKey();
                        qContext.setRowKey(rowKey);
                        return taskInfo;
                    }
                } else if (dataReader.next()) {
                    DimensionValueSet rowKey = dataReader.getCurrentRowKey();
                    qContext.setRowKey(rowKey);
                    return taskInfo;
                }
            }
        }
        catch (Exception e) {
            this.onException(qContext, e);
        }
        return null;
    }

    private void onException(QueryContext qContext, Exception e) {
        StringBuilder msg = new StringBuilder(e.getMessage() + "\n\u5f71\u54cd\u7684\u516c\u5f0f:\n");
        HashSet<String> formulaSet = new HashSet<String>();
        for (ExecutorBase executor : this.executors) {
            String formulaId;
            IExpression expression;
            int i;
            if (executor instanceof CalcExecutor) {
                CalcExecutor calcExecutor = (CalcExecutor)executor;
                for (i = 0; i < calcExecutor.size(); ++i) {
                    CalcExpression calcExpression;
                    expression = calcExecutor.get(i);
                    if (!(expression instanceof CalcExpression) || formulaSet.contains(formulaId = (calcExpression = (CalcExpression)expression).getSource().getId())) continue;
                    formulaSet.add(formulaId);
                    msg.append(calcExpression.getSource()).append("\n");
                }
                continue;
            }
            if (!(executor instanceof CheckExecutor)) continue;
            CheckExecutor checkExecutor = (CheckExecutor)executor;
            for (i = 0; i < checkExecutor.size(); ++i) {
                CheckExpression checkExpression;
                expression = checkExecutor.get(i);
                if (!(expression instanceof CheckExpression) || formulaSet.contains(formulaId = (checkExpression = (CheckExpression)expression).getSource().getId())) continue;
                formulaSet.add(formulaId);
                msg.append(checkExpression.getSource()).append("\n");
            }
        }
        ExecuteException ee = new ExecuteException(msg.toString(), e);
        qContext.getMonitor().exception(ee);
    }

    private void cloneFloatExpressions() {
        if (this.queryRegion.isFloat() && !this.isFuncCalc()) {
            EvalExecutor evalExecutor;
            CheckExecutor checkExecutor;
            CalcExecutor calcExecutor = this.findCalcExecutor();
            if (calcExecutor != null) {
                calcExecutor.cloneExpressions(this.context);
            }
            if ((checkExecutor = this.findCheckExecutor()) != null) {
                checkExecutor.cloneExpressions(this.context);
            }
            if ((evalExecutor = this.findEvalExecutor()) != null) {
                evalExecutor.cloneExpressions(this.context);
            }
        }
    }

    private MemoryDataSetReader createDataReaderAndQuery(QueryContext qContext) throws Exception, ParseException {
        long start = System.currentTimeMillis();
        MemoryDataSetReader dataReader = this.queryRegion.runQuery(qContext, null);
        if ((this.queryRegion.isEmpty() || this.getUseStatResult() || !this.queryRegion.isFloat()) && !this.context.isZbCalcMode()) {
            QueryTable primaryTable;
            int i;
            HashSet<DimensionValueSet> statMasterKeySet = new HashSet<DimensionValueSet>();
            DimensionValueSet masterKeys = qContext.getMasterKeys();
            DimensionValueSet expandKeys = new DimensionValueSet();
            if (this.queryRegion.isEmpty()) {
                for (i = 0; i < masterKeys.size(); ++i) {
                    expandKeys.setValue(masterKeys.getName(i), masterKeys.getValue(i));
                }
            } else {
                for (i = 0; i < masterKeys.size(); ++i) {
                    if (!this.queryRegion.getLoopDimensions().contains(masterKeys.getName(i))) continue;
                    expandKeys.setValue(masterKeys.getName(i), masterKeys.getValue(i));
                }
            }
            if ((primaryTable = this.queryRegion.getSqlBuilder().getPrimaryTable()) != null) {
                TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(primaryTable.getTableName());
                ExpressionUtils.expandDims(expandKeys, statMasterKeySet, tableInfo);
            } else {
                ExpressionUtils.expandDims(expandKeys, statMasterKeySet);
            }
            dataReader.expandByDims(statMasterKeySet);
        }
        if (dataReader.size() > 0) {
            this.queryRegion.loadLeftJoinDatas(qContext, !this.queryRegion.isFloat());
        }
        AbstractMonitor monitor = (AbstractMonitor)qContext.getMonitor();
        monitor.addRecordCount(dataReader.size());
        monitor.addFieldCount(dataReader.getColumnCount());
        this.printRegionResult(dataReader, start);
        return dataReader;
    }

    @Override
    public void commitData(QueryContext qContext) throws SQLException, ExpressionException, ParseException {
        UpdateDatas updateDatas = qContext.getUpdateDatas();
        try {
            FmlExecuteCollector fmlExecuteCollector = qContext.getFmlExecuteCollector();
            if (fmlExecuteCollector != null && !fmlExecuteCollector.getConfig().isCanCommit()) {
                qContext.getMonitor().message("\u6267\u884c\u4fe1\u606f\u6536\u96c6\u8df3\u8fc7\u6570\u636e\u63d0\u4ea4", this);
                return;
            }
            this.printUpdataDatas(updateDatas);
            updateDatas.commitData(qContext, qContext.getMonitor(), this.queryParam);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        qContext.setUpdateDatas(null);
    }

    private void printUpdataDatas(UpdateDatas updateDatas) {
    }

    private void printRegionResult(MemoryDataSetReader dataReader, long start) {
        if (this.context.outFMLPlan()) {
            try {
                StringBuilder msg = new StringBuilder();
                msg.append("\u6267\u884c\u57df").append(this.getRegionCode()).append("\u6570\u636e\u52a0\u8f7d\u5b8c\u6210,");
                msg.append("\u67e5\u8be2\u8017\u65f6\uff1a" + Round.callFunction((Number)Float.valueOf((float)(System.currentTimeMillis() - start) / 1000.0f), (int)2) + "s").append("\n");
                dataReader.print(msg);
                this.context.getMonitor().message(msg.toString(), this);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    protected void printExecRegion() {
        block11: {
            if (this.context.outFMLPlan()) {
                try {
                    EvalExecutor evalExecutor;
                    StatExecutor statExecutor;
                    CheckExecutor checkExecutor;
                    FmlExecuteCollector collector = this.context.getFmlExecuteCollector();
                    StringBuilder msg = new StringBuilder();
                    msg.append("\u6267\u884c\u57df").append(this.getRegionCode()).append("\n");
                    msg.append("\u6267\u884c\u57df\u57fa\u672c\u4fe1\u606f\uff1a\n");
                    this.queryRegion.printQueryRegion(this.context, msg);
                    msg.append("region.useStatResult=").append(this.getUseStatResult()).append("\n");
                    CalcExecutor calcExecutor = this.findCalcExecutor();
                    if (calcExecutor != null) {
                        FocusInfoCollector focusInfoCollector;
                        msg.append("\u6267\u884c\u57df\u8fd0\u7b97\u516c\u5f0f\uff1a\n");
                        calcExecutor.print(msg);
                        if (collector != null && (focusInfoCollector = collector.getFocusInfoCollector()) != null) {
                            for (int i = 0; i < calcExecutor.size(); ++i) {
                                focusInfoCollector.collectExpression((CalcExpression)calcExecutor.get(i));
                            }
                        }
                    }
                    if ((checkExecutor = this.findCheckExecutor()) != null) {
                        FocusInfoCollector focusInfoCollector;
                        msg.append("\u6267\u884c\u57df\u5ba1\u6838\u516c\u5f0f\uff1a\n");
                        checkExecutor.print(msg);
                        if (collector != null && (focusInfoCollector = collector.getFocusInfoCollector()) != null) {
                            for (int i = 0; i < checkExecutor.size(); ++i) {
                                focusInfoCollector.collectExpression((CheckExpression)checkExecutor.get(i));
                            }
                        }
                    }
                    if ((statExecutor = this.findStatExecutor()) != null) {
                        msg.append("\u6267\u884c\u57df\u6d6e\u52a8\u7edf\u8ba1\u5355\u5143\uff1a\n");
                        statExecutor.print(msg);
                    }
                    if ((evalExecutor = this.findEvalExecutor()) != null) {
                        msg.append("\u6267\u884c\u57df\u53d6\u6570\u516c\u5f0f\uff1a\n");
                        evalExecutor.print(msg);
                    }
                    this.context.getMonitor().message(msg.toString(), this);
                }
                catch (Exception e) {
                    if (!this.context.isDebug()) break block11;
                    this.context.getMonitor().exception(e);
                }
            }
        }
    }
}

