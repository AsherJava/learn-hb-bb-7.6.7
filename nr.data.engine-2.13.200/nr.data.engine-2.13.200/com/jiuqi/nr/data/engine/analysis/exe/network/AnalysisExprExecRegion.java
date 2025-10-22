/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.BoolValue
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DebugLogType
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.ExecuteException
 *  com.jiuqi.np.dataengine.executors.CalcExecutor
 *  com.jiuqi.np.dataengine.executors.CheckExecutor
 *  com.jiuqi.np.dataengine.executors.DNAFmlDataCommittor
 *  com.jiuqi.np.dataengine.executors.EvalExecutor
 *  com.jiuqi.np.dataengine.executors.ExecutorBase
 *  com.jiuqi.np.dataengine.executors.ExprExecCenter
 *  com.jiuqi.np.dataengine.executors.StatExecutor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.engine.analysis.exe.network;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.BoolValue;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.executors.CalcExecutor;
import com.jiuqi.np.dataengine.executors.CheckExecutor;
import com.jiuqi.np.dataengine.executors.DNAFmlDataCommittor;
import com.jiuqi.np.dataengine.executors.EvalExecutor;
import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.executors.ExprExecCenter;
import com.jiuqi.np.dataengine.executors.StatExecutor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;
import com.jiuqi.nr.data.engine.analysis.exe.ParsedFloatRegionConfig;
import com.jiuqi.nr.data.engine.analysis.exe.query.AnalysisMemoryDataSetReader;
import com.jiuqi.nr.data.engine.analysis.exe.query.AnalysisQueryRegion;
import com.jiuqi.nr.data.engine.analysis.exe.query.AnalysisQuerySqlBuilder;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisExprExecRegion
extends ExprExecCenter {
    private static final Logger logger = LoggerFactory.getLogger(AnalysisExprExecRegion.class);
    public final AnalysisQueryRegion queryRegion;
    public Set<QueryField> regionReads;
    public Set<QueryField> regionWrites;
    private String regionCode;
    private boolean useStatResult;
    private final QueryParam queryParam;
    private boolean isMain;

    public AnalysisExprExecRegion(QueryContext context, DimensionSet masterDimensions, DimensionSet dimensions, QueryParam queryParam) {
        super(context);
        AnalysisQuerySqlBuilder sqlBuilder = new AnalysisQuerySqlBuilder();
        if (!DataEngineConsts.DATA_ENGINE_DEBUG) {
            sqlBuilder.setSqlSoftParse(true);
        }
        this.queryRegion = new AnalysisQueryRegion(dimensions, sqlBuilder);
        this.regionCode = OrderGenerator.newOrder();
        this.queryParam = queryParam;
        this.queryRegion.getSqlBuilder().setQueryParam(queryParam);
        this.queryRegion.getSqlBuilder().setMasterDimensions(masterDimensions);
    }

    protected boolean doInitialization(Object initInfo) throws ExecuteException {
        try {
            this.queryRegion.doInit((AnalysisContext)this.context);
        }
        catch (ParseException e) {
            throw new ExecuteException((Throwable)e);
        }
        return super.doInitialization(initInfo);
    }

    protected boolean doExecution(Object taskInfo) throws Exception {
        AnalysisContext aContext = (AnalysisContext)this.context;
        aContext.getConditionJuder().setEvalExecutor(this.findEvalExecutor());
        boolean result = super.doExecution(taskInfo);
        AbstractMonitor monitor = (AbstractMonitor)this.context.getMonitor();
        monitor.step();
        return result;
    }

    protected boolean executeChildren(Object subTask, BoolValue somethingDone) throws Exception {
        IExpression rowCondition;
        AnalysisContext qContext = (AnalysisContext)this.context;
        ParsedFloatRegionConfig floatConfig = qContext.getFloatConfig();
        if (floatConfig != null && (rowCondition = floatConfig.getRowCondition()) != null && !rowCondition.judge((IContext)qContext)) {
            return true;
        }
        return super.executeChildren(subTask, somethingDone);
    }

    protected Object disassembleTask(Object taskInfo) {
        AnalysisContext qContext = (AnalysisContext)this.context;
        AnalysisMemoryDataSetReader dataReader = null;
        try {
            dataReader = (AnalysisMemoryDataSetReader)qContext.getDataReader();
            if (dataReader == null) {
                dataReader = this.loadDataReader(qContext);
            }
            if (dataReader != null) {
                if (qContext.isFloat()) {
                    qContext.commitRow();
                }
                if (dataReader.next()) {
                    DimensionValueSet rowKey = dataReader.getCurrentRowKey();
                    DimensionValueSet currentMarsterKeys = qContext.getCurrentMasterKey();
                    for (int i = 0; i < rowKey.size(); ++i) {
                        String dimName = rowKey.getName(i);
                        if (!currentMarsterKeys.hasValue(dimName)) continue;
                        currentMarsterKeys.setValue(dimName, rowKey.getValue(i));
                    }
                    qContext.setRowKey(rowKey);
                    return taskInfo;
                }
            }
        }
        catch (Exception e) {
            StringBuilder msg = new StringBuilder(e.getMessage() + "\n\u5f71\u54cd\u7684\u516c\u5f0f:\n");
            for (ExecutorBase executor : this.executors) {
                if (!(executor instanceof CalcExecutor)) continue;
                CalcExecutor calcExecutor = (CalcExecutor)executor;
                for (int i = 0; i < calcExecutor.size(); ++i) {
                    IExpression expression = calcExecutor.get(i);
                    if (!(expression instanceof CalcExpression)) continue;
                    CalcExpression calcExpression = (CalcExpression)expression;
                    msg.append(calcExpression.getSource()).append("\n");
                }
            }
            ExecuteException ee = new ExecuteException(msg.toString(), (Throwable)e);
            qContext.getMonitor().exception((Exception)ee);
        }
        if (!qContext.isFloat()) {
            try {
                qContext.commitRow();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (dataReader != null) {
            dataReader.reset();
        }
        qContext.setDataReader(null);
        return null;
    }

    protected AnalysisMemoryDataSetReader loadDataReader(AnalysisContext qContext) throws Exception {
        AnalysisMemoryDataSetReader dataReader;
        if (DataEngineConsts.DATA_ENGINE_DEBUG) {
            qContext.getMonitor().debug(this.queryRegion.toString(), DataEngineConsts.DebugLogType.REGION);
        }
        LinkedHashSet<DimensionValueSet> statMasterKeySet = new LinkedHashSet<DimensionValueSet>();
        boolean needExpand = this.isMain();
        if (needExpand) {
            DimensionValueSet masterKeys = qContext.getMasterKeys();
            DimensionValueSet expandKeys = new DimensionValueSet();
            if (this.queryRegion.isEmpty()) {
                expandKeys.assign(masterKeys);
            } else {
                for (int i = 0; i < masterKeys.size(); ++i) {
                    if (!this.queryRegion.getLoopDimensions().contains(masterKeys.getName(i))) continue;
                    expandKeys.setValue(masterKeys.getName(i), masterKeys.getValue(i));
                }
            }
            QueryTable primaryTable = this.queryRegion.getSqlBuilder().getPrimaryTable();
            if (primaryTable != null) {
                TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(primaryTable.getTableName());
                ExpressionUtils.expandDims((DimensionValueSet)expandKeys, statMasterKeySet, (TableModelRunInfo)tableInfo);
            } else {
                ExpressionUtils.expandDims((DimensionValueSet)expandKeys, statMasterKeySet);
            }
        }
        if ((dataReader = this.queryRegion.runQuery(qContext, statMasterKeySet)).size() > 0) {
            this.queryRegion.loadLeftJoinDatas(qContext, needExpand);
        }
        if (this.isMain) {
            dataReader.sort(qContext);
        }
        AbstractMonitor monitor = (AbstractMonitor)qContext.getMonitor();
        monitor.addRecordCount(dataReader.size());
        monitor.addFieldCount(dataReader.getColumnCount());
        return dataReader;
    }

    public void combine(AnalysisExprExecRegion another) {
        int count = another.executors.size();
        for (int i = count - 1; i >= 0; --i) {
            ExecutorBase executor = (ExecutorBase)another.executors.get(i);
            if (executor instanceof CalcExecutor) {
                this.getCalcExecutor().combine((CalcExecutor)executor);
                continue;
            }
            if (executor instanceof CheckExecutor) {
                this.getCheckExecutor().combine((CheckExecutor)executor);
                continue;
            }
            if (executor instanceof EvalExecutor) {
                this.getEvalExecutor().combine((EvalExecutor)executor);
                continue;
            }
            if (executor instanceof StatExecutor) {
                this.getStatExecutor().combine((StatExecutor)executor);
                continue;
            }
            if (executor instanceof DNAFmlDataCommittor) continue;
            another.remove(executor);
            this.add(executor);
            executor.replacePrecursor((ExecutorBase)another.findCalcExecutor(), (ExecutorBase)this.getCalcExecutor());
            executor.replacePrecursor((ExecutorBase)another.findCheckExecutor(), (ExecutorBase)this.getCheckExecutor());
            executor.replacePrecursor((ExecutorBase)another.findEvalExecutor(), (ExecutorBase)this.getEvalExecutor());
            executor.replacePrecursor((ExecutorBase)another.findStatExecutor(), (ExecutorBase)this.getStatExecutor());
        }
        if (another.regionReads != null) {
            if (this.regionReads == null) {
                this.regionReads = new HashSet<QueryField>();
            }
            this.regionReads.addAll(another.regionReads);
        }
        if (another.regionWrites != null) {
            if (this.regionWrites == null) {
                this.regionWrites = new HashSet<QueryField>();
            }
            this.regionWrites.addAll(another.regionWrites);
        }
        this.queryRegion.combine(another.queryRegion);
        this.setUseStatResult(this.getUseStatResult() || another.getUseStatResult());
    }

    public final String getRegionCode() {
        return this.regionCode;
    }

    public final void setRegionCode(String value) {
        this.regionCode = value;
    }

    public final boolean getUseStatResult() {
        return this.useStatResult;
    }

    public final void setUseStatResult(boolean value) {
        this.useStatResult = value;
    }

    public DimensionSet getRegionDimensions() {
        return this.queryRegion.getDimensions();
    }

    public boolean isMain() {
        return this.isMain;
    }

    public void setMain(boolean isMain) {
        this.isMain = isMain;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.regionCode == null ? 0 : this.regionCode.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (((Object)((Object)this)).getClass() != obj.getClass()) {
            return false;
        }
        AnalysisExprExecRegion other = (AnalysisExprExecRegion)((Object)obj);
        if (this.regionCode == null) {
            return other.regionCode == null;
        }
        return this.regionCode.equals(other.regionCode);
    }
}

