/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempResource
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.summary.executor.sum;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.summary.executor.sum.SumBeanSet;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryParam;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryRegion;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryDataSet;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryDimensionInfo;
import com.jiuqi.nr.summary.executor.sum.parse.SummaryFmlExecEnvironment;
import com.jiuqi.nr.summary.executor.sum.parse.func.InCalibre;
import com.jiuqi.nr.summary.executor.sum.parse.func.InCollection;
import java.util.List;
import org.slf4j.Logger;

public class SumContext
extends QueryContext
implements IContext,
AutoCloseable {
    private SumBeanSet beanSet;
    private RuntimeSummaryParam param;
    private ReportFormulaParser formulaParser;
    private RuntimeSummaryRegion currentRegion;
    private DimensionValueSet targetDimValues;
    private SummaryDataSet summaryDataSet;
    private ExecutorContext calcExecutorContext;
    private Logger logger;

    public SumContext(ExecutorContext exeContext, SumBeanSet beanSet, RuntimeSummaryParam param, IMonitor monitor, Logger logger) throws ParseException {
        super(exeContext, beanSet.getQueryParam(), monitor);
        this.beanSet = beanSet;
        this.param = param;
        this.logger = logger;
        this.tempResource = new TempResource();
    }

    public SumContext(SumContext other, RuntimeSummaryRegion currentRegion, SummaryDimensionInfo dimensionInfo) throws Exception {
        super(other.exeContext, other.queryParam, other.getMonitor());
        this.beanSet = other.beanSet;
        this.param = other.param;
        this.formulaParser = other.formulaParser;
        this.calcExecutorContext = other.calcExecutorContext;
        this.logger = other.getLogger();
        this.currentRegion = currentRegion;
        this.masterKeys = dimensionInfo.getSourceDimValues();
        this.targetDimValues = dimensionInfo.getTargetDimValues();
        this.summaryDataSet = currentRegion.prepareSummaryDataSet(this);
        this.tempResource = new TempResource();
    }

    public void runStatistic() throws SyntaxException {
        this.summaryDataSet.statistic(this);
    }

    public ReportFormulaParser getFormulaParser() {
        if (this.formulaParser == null) {
            try {
                this.formulaParser = this.exeContext.getCache().getFormulaParser(true);
            }
            catch (ParseException e) {
                this.getMonitor().exception((Exception)((Object)e));
            }
            FunctionProvider sumFuncProvider = new FunctionProvider();
            sumFuncProvider.add((IFunction)new InCalibre());
            sumFuncProvider.add((IFunction)new InCollection());
            this.formulaParser.registerFunctionProvider((IFunctionProvider)sumFuncProvider);
        }
        return this.formulaParser;
    }

    public TableModelRunInfo getTableModelRunInfoByDataTable(String tableName) throws ParseException {
        DataModelDefinitionsCache dataModelDefinitionsCache = this.exeContext.getCache().getDataModelDefinitionsCache();
        TableModelRunInfo tableRunInfo = null;
        DataTable schemeDataTable = this.beanSet.dataSchemeService.getDataTableByCode(tableName);
        List deployInfos = this.beanSet.dataSchemeService.getDeployInfoByDataTableKey(schemeDataTable.getKey());
        if (deployInfos.size() > 0) {
            tableRunInfo = dataModelDefinitionsCache.getTableInfoByCode(((DataFieldDeployInfo)deployInfos.get(0)).getTableName());
        }
        return tableRunInfo;
    }

    public SumBeanSet getBeanSet() {
        return this.beanSet;
    }

    public RuntimeSummaryParam getParam() {
        return this.param;
    }

    public RuntimeSummaryRegion getCurrentRegion() {
        return this.currentRegion;
    }

    public DimensionValueSet getTargetDimValues() {
        return this.targetDimValues;
    }

    public void setSummaryDataSet(SummaryDataSet summaryDataSet) {
        this.summaryDataSet = summaryDataSet;
    }

    public SummaryDataSet getSummaryDataSet() {
        return this.summaryDataSet;
    }

    public ExecutorContext getCalcExecutorContext() {
        if (this.calcExecutorContext == null) {
            this.calcExecutorContext = new ExecutorContext(this.beanSet.dataDefinitionController);
            SummaryFmlExecEnvironment env = new SummaryFmlExecEnvironment(this.param, this.beanSet);
            this.calcExecutorContext.setEnv((IFmlExecEnvironment)env);
        }
        return this.calcExecutorContext;
    }

    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public void close() throws Exception {
        if (this.tempResource != null) {
            this.tempResource.close();
            this.tempResource = null;
        }
        if (this.queryParam != null) {
            this.queryParam.closeConnection();
        }
    }
}

