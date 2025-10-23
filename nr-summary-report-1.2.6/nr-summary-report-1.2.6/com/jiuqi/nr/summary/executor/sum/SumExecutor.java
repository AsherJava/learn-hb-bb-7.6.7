/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.summary.executor.sum;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.summary.executor.sum.SumBeanSet;
import com.jiuqi.nr.summary.executor.sum.SumContext;
import com.jiuqi.nr.summary.executor.sum.SumParam;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryParam;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryReport;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryDimensionInfo;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryMonitor;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SumExecutor {
    private static final Logger log = LoggerFactory.getLogger(SumExecutor.class);
    @Autowired
    private SumBeanSet beanSet;

    public void executeSum(SumParam param, boolean afterCalculate, AsyncTaskMonitor monitor) throws Exception {
        try (SumContext sumContext = this.prepare(param, afterCalculate, monitor);){
            if (sumContext.getMonitor().isCancel()) {
                return;
            }
            this.run(sumContext);
            sumContext.getMonitor().finish();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private SumContext prepare(SumParam param, boolean afterCalculate, AsyncTaskMonitor monitor) throws Exception {
        String fromSchemeKey = this.getFormSchemeKey(param);
        ExecutorContext executorContext = this.createExecutorContext(fromSchemeKey);
        RuntimeSummaryParam sumParam = new RuntimeSummaryParam(param);
        SummaryMonitor summaryMonitor = new SummaryMonitor(monitor);
        SumContext sumContext = new SumContext(executorContext, this.beanSet, sumParam, (IMonitor)summaryMonitor, log);
        sumParam.doInit(sumContext, afterCalculate, fromSchemeKey);
        summaryMonitor.prepare();
        return sumContext;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void run(SumContext sumContext) throws Exception {
        RuntimeSummaryParam param = sumContext.getParam();
        SummaryMonitor monitor = (SummaryMonitor)sumContext.getMonitor();
        QueryParam queryParam = sumContext.getQueryParam();
        Map<DimensionValueSet, DimensionValueSet> summaryDims = param.expandSummaryDims(sumContext);
        double step = 0.8 / (double)(summaryDims.size() * param.getReports().size());
        monitor.setStep(step);
        for (Map.Entry<DimensionValueSet, DimensionValueSet> entry : summaryDims.entrySet()) {
            if (sumContext.getMonitor().isCancel()) break;
            SummaryDimensionInfo dimensionInfo = new SummaryDimensionInfo(entry.getKey(), entry.getValue());
            try {
                this.initMdTempTable(param, queryParam, dimensionInfo);
                for (RuntimeSummaryReport report : param.getReports()) {
                    if (sumContext.getMonitor().isCancel()) break;
                    report.doExecute(sumContext, dimensionInfo);
                    monitor.step();
                }
                this.calc(sumContext, dimensionInfo.getTargetDimValues());
            }
            finally {
                if (dimensionInfo.getMdTempAssistantTable() == null) continue;
                dimensionInfo.getMdTempAssistantTable().dropTempTable(queryParam.getConnection());
            }
        }
    }

    private void calc(SumContext sumContext, DimensionValueSet targetDimValues) throws ParseException {
        if (sumContext.getMonitor().isCancel()) {
            return;
        }
        FormulaCallBack callBack = sumContext.getParam().getCalcFormulaCallBack();
        if (callBack == null) {
            return;
        }
        this.calcByCallBack(sumContext, targetDimValues, callBack);
        if (sumContext.getParam().isSumBySolution()) {
            FormulaCallBack spanCallBack = sumContext.getParam().getSpanFormulaCallBack(sumContext);
            if (spanCallBack == null) {
                return;
            }
            this.calcByCallBack(sumContext, targetDimValues, spanCallBack);
        }
    }

    private void calcByCallBack(SumContext sumContext, DimensionValueSet targetDimValues, FormulaCallBack callBack) {
        AbstractMonitor calcMonitor = new AbstractMonitor();
        IFormulaRunner formulaRunner = sumContext.getBeanSet().dataAccessProvider.newFormulaRunner(callBack);
        try {
            formulaRunner.prepareCalc(sumContext.getCalcExecutorContext(), targetDimValues, (IMonitor)calcMonitor);
            formulaRunner.run((IMonitor)calcMonitor);
        }
        catch (Exception e) {
            calcMonitor.exception(e);
        }
    }

    private void initMdTempTable(RuntimeSummaryParam param, QueryParam queryParam, SummaryDimensionInfo dimensionInfo) throws Exception, SQLException {
        String mainDimName = param.getSourceMdEntity().getDimensionName();
        List sourceMDKeys = (List)dimensionInfo.getSourceDimValues().getValue(mainDimName);
        if (sourceMDKeys.size() > DataEngineUtil.getMaxInSize((IDatabase)queryParam.getDatabase())) {
            TempAssistantTable tempAssistantTable = new TempAssistantTable(sourceMDKeys, 6);
            tempAssistantTable.createTempTable(queryParam.getConnection());
            tempAssistantTable.insertIntoTempTable(queryParam.getConnection());
            dimensionInfo.setMdTempAssistantTable(tempAssistantTable);
        }
    }

    private String getFormSchemeKey(SumParam param) throws Exception {
        String fromSchemeKey = null;
        String reportTaskKey = param.getSummarySolutionModel().getMainTask();
        SchemePeriodLinkDefine schemePeriodLink = this.beanSet.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(param.getCurrPeriod().toString(), reportTaskKey);
        if (schemePeriodLink != null) {
            fromSchemeKey = schemePeriodLink.getSchemeKey();
        } else {
            List formSchemeDefines = this.beanSet.runTimeViewController.queryFormSchemeByTask(reportTaskKey);
            if (formSchemeDefines.size() > 0) {
                fromSchemeKey = ((FormSchemeDefine)formSchemeDefines.get(0)).getKey();
            }
        }
        return fromSchemeKey;
    }

    private ExecutorContext createExecutorContext(String fromSchemeKey) {
        ExecutorContext executorContext = new ExecutorContext(this.beanSet.dataDefinitionController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.beanSet.runTimeViewController, this.beanSet.dataDefinitionController, this.beanSet.entityViewRunTimeController, fromSchemeKey);
        executorContext.setEnv((IFmlExecEnvironment)env);
        return executorContext;
    }
}

