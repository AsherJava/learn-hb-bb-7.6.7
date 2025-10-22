/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.parallel.BatchParallelExeTask
 *  com.jiuqi.nr.parallel.IParallelTaskExecuter
 */
package com.jiuqi.nr.data.logic.internal.parallel;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.logic.facade.extend.ICalFmlProvider;
import com.jiuqi.nr.data.logic.facade.extend.param.FmlExecInfo;
import com.jiuqi.nr.data.logic.facade.extend.param.GetCalFmlEnv;
import com.jiuqi.nr.data.logic.facade.param.input.CalculateParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.internal.helper.Helper;
import com.jiuqi.nr.data.logic.internal.obj.ParallelFormulaExecuteInfo;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.data.logic.internal.util.SystemOptionUtil;
import com.jiuqi.nr.data.logic.monitor.CalBatchSerialMonitor;
import com.jiuqi.nr.data.logic.monitor.ParallelMonitor;
import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.IParallelTaskExecuter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component(value="parallelCalculateTaskExecuterNew")
public class ParallelCalculateTaskExecuter
implements IParallelTaskExecuter {
    private static final Logger logger = LoggerFactory.getLogger(ParallelCalculateTaskExecuter.class);
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private SystemOptionUtil systemOptionUtil;
    @Autowired
    private FormulaParseUtil formulaParseUtil;
    @Autowired
    private ParamUtil paramUtil;
    @Autowired
    private Helper helper;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doExecute(BatchParallelExeTask task, IMonitor monitor) {
        logger.debug("task {}-{} run calc", (Object)task.getParentTaskID(), (Object)task.getKey());
        try {
            ParallelFormulaExecuteInfo taskInfo = (ParallelFormulaExecuteInfo)task.getTaskInfo();
            ParallelFormulaExecuteInfo parallelFormulaExecuteInfo = (ParallelFormulaExecuteInfo)task.getTaskInfo();
            String formSchemeKey = parallelFormulaExecuteInfo.getFormSchemeKey();
            String formulaSchemeKey = parallelFormulaExecuteInfo.getFormulaSchemeKey();
            Map<String, List<String>> formulaMaps = parallelFormulaExecuteInfo.getFormulaMaps();
            List<String> forms = parallelFormulaExecuteInfo.getForms();
            int batchSplitCount = this.systemOptionUtil.getBatchSplitCount();
            Map dimensionValues = task.getDimensionValues();
            List<DimensionValueSet> dimensionValueSets = DimensionUtil.getDimensionValueSetList(dimensionValues, batchSplitCount);
            DimensionValueSet dimExeC = DimensionUtil.getDimensionValueSet(dimensionValues);
            CalculateParam calculateParam = new CalculateParam();
            calculateParam.setFormulaSchemeKey(formulaSchemeKey);
            ArrayList<FmlExecInfo> fmlExecInfos = new ArrayList<FmlExecInfo>();
            List<IParsedExpression> formulaList = this.formulaParseUtil.listExpression(calculateParam, DataEngineConsts.FormulaType.CALCULATE, forms, formulaMaps);
            for (DimensionValueSet dimensionValueSet : dimensionValueSets) {
                GetCalFmlEnv getFmlEnv = this.getGetFmlEnv(taskInfo, dimensionValueSet, formulaList);
                ICalFmlProvider fmlProvider = this.paramUtil.getCalFmlProvider(getFmlEnv);
                List<FmlExecInfo> fml = fmlProvider.getFml(getFmlEnv);
                if (CollectionUtils.isEmpty(fml)) continue;
                fmlExecInfos.addAll(fml);
            }
            for (FmlExecInfo fmlExecInfo : fmlExecInfos) {
                List<IParsedExpression> parsedExpressions = fmlExecInfo.getParsedExpressions();
                DimensionValueSet dimensionValueSet = fmlExecInfo.getDimensionValueSet();
                FormulaCallBack callBack = this.formulaParseUtil.getFmlCallBack(parsedExpressions, parallelFormulaExecuteInfo.isFmlJIT());
                ExecutorContext executorContext = this.formulaParseUtil.getExecutorContext(calculateParam, dimExeC);
                IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callBack);
                double serialWeight = 1.0 / (double)fmlExecInfos.size();
                CalBatchSerialMonitor serialMonitor = new CalBatchSerialMonitor(formSchemeKey, formulaSchemeKey, dimensionValueSet, formulaList);
                serialMonitor.setProgressWeight(serialWeight);
                AbstractMonitor abstractMonitor = (AbstractMonitor)monitor;
                serialMonitor.setStartProgress(abstractMonitor.getCurrentProgress());
                serialMonitor.setMainMonitor(abstractMonitor);
                if (monitor instanceof ParallelMonitor) {
                    serialMonitor.setFmlEngineExtMonitor(((ParallelMonitor)monitor).getFmlEngineExtMonitor());
                }
                runner.prepareCalc(executorContext, null, (IMonitor)serialMonitor);
                runner.setMasterKeyValues(dimensionValueSet);
                runner.run((IMonitor)serialMonitor);
                if (!monitor.isCancel()) continue;
                break;
            }
        }
        catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            if (throwable instanceof Exception) {
                monitor.exception((Exception)throwable);
            } else {
                monitor.exception((Exception)new RuntimeException(throwable));
            }
        }
        finally {
            task.setFinished(true);
        }
    }

    private GetCalFmlEnv getGetFmlEnv(ParallelFormulaExecuteInfo taskInfo, DimensionValueSet dimensionValueSet, List<IParsedExpression> parsedExpressions) {
        return new GetCalFmlEnv().setFormSchemeKey(taskInfo.getFormSchemeKey()).setFormulaSchemeKey(taskInfo.getFormulaSchemeKey()).setFormulaMaps(taskInfo.getFormulaMaps()).setAccessForms(taskInfo.getForms()).setWholeBetween(taskInfo.isContainBetween()).setDimensionValueSet(dimensionValueSet).setParsedExpressions(parsedExpressions).setMode(Mode.getByKey(taskInfo.getMode())).setProviderStore(this.helper.getProviderStore(taskInfo.getAccessIgnoreItems()));
    }
}

