/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.logic.common.CommonUtils;
import com.jiuqi.nr.data.logic.common.ExceptionEnum;
import com.jiuqi.nr.data.logic.facade.extend.ICalFmlProvider;
import com.jiuqi.nr.data.logic.facade.extend.IFmlExecInfoProvider;
import com.jiuqi.nr.data.logic.facade.extend.param.FmlExecInfo;
import com.jiuqi.nr.data.logic.facade.extend.param.GetCalFmlEnv;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CalPar;
import com.jiuqi.nr.data.logic.facade.param.input.CalculateParam;
import com.jiuqi.nr.data.logic.facade.service.ICalculateService;
import com.jiuqi.nr.data.logic.internal.async.BatchCalculateAsyncTaskExecutor;
import com.jiuqi.nr.data.logic.internal.helper.CalculateHelper;
import com.jiuqi.nr.data.logic.internal.helper.Helper;
import com.jiuqi.nr.data.logic.internal.log.LogHelper;
import com.jiuqi.nr.data.logic.internal.provider.FmlExecInfoProviderFactory;
import com.jiuqi.nr.data.logic.internal.service.ICalculateExecuteService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.data.logic.monitor.CalculateMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class CalculateServiceImpl
implements ICalculateService {
    private static final String MSG_NO_CAL_FORMULA = "\u65e0\u8fd0\u7b97\u516c\u5f0f";
    private static final String MSG_NO_CAL_FORMULA_PREFIX = "\u65e0\u8fd0\u7b97\u516c\u5f0f\u2014\u2014";
    private static final Logger logger = LoggerFactory.getLogger(CalculateServiceImpl.class);
    @Autowired
    private FormulaParseUtil formulaParseUtil;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private ICalculateExecuteService calculateExecuteService;
    @Autowired
    private CalculateHelper calculateHelper;
    @Autowired
    private ParamUtil paramUtil;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private LogHelper logHelper;
    @Autowired
    private FmlExecInfoProviderFactory fmlExecInfoProviderFactory;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private Helper helper;

    @Override
    public String calculate(CalculateParam calculateParam) {
        DimensionValueSet mergeDimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(calculateParam.getDimensionCollection());
        if (mergeDimensionValueSet == null) {
            logger.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
            return ExceptionEnum.DIM_EXPAND_EXC.getDesc();
        }
        Map<String, List<String>> formulas = this.formulaParseUtil.getFormulas(calculateParam.getMode(), calculateParam.getRangeKeys(), calculateParam.getFormulaSchemeKey(), DataEngineConsts.FormulaType.CALCULATE);
        ArrayList<String> formKeys = new ArrayList<String>(formulas.keySet());
        boolean containBetween = formKeys.remove("00000000-0000-0000-0000-000000000000");
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKey(calculateParam.getFormulaSchemeKey());
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(calculateParam.getDimensionCollection());
        accessFormParam.setTaskKey(formScheme.getTaskKey());
        accessFormParam.setIgnoreAccessItems(calculateParam.getIgnoreItems());
        accessFormParam.setFormSchemeKey(formScheme.getKey());
        accessFormParam.setFormKeys(formKeys);
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_SYSTEM_WRITE);
        DimensionAccessFormInfo dimensionAccessFormInfo = this.dataAccessServiceProvider.getDataAccessFormService().getBatchAccessForms(accessFormParam);
        List accessForms = dimensionAccessFormInfo.getAccessForms();
        LogDimensionCollection logDimension = this.logHelper.getLogDimension(formScheme, mergeDimensionValueSet);
        String formInfo = this.logHelper.getFormInfo(formKeys);
        String formulaSchemeInfo = this.logHelper.getFormulaSchemeInfo(Collections.singletonList(calculateParam.getFormulaSchemeKey()));
        if (CollectionUtils.isEmpty(accessForms)) {
            this.logHelper.calInfo(formScheme.getTaskKey(), logDimension, "\u65e0\u8fd0\u7b97\u6743\u9650", "\u5bf9\u5355\u4f4d\u3001\u62a5\u8868\u65e0\u8fd0\u7b97\u6743\u9650\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formInfo);
            return "\u65e0\u8fd0\u7b97\u6743\u9650";
        }
        List accessFormKeys = ((DimensionAccessFormInfo.AccessFormInfo)accessForms.get(0)).getFormKeys();
        if (containBetween) {
            accessFormKeys.add("00000000-0000-0000-0000-000000000000");
        }
        List<IParsedExpression> expressions = this.formulaParseUtil.listExpression(calculateParam, DataEngineConsts.FormulaType.CALCULATE, accessFormKeys);
        DimensionValueSet dimensionValueSet = DimensionUtil.getDimensionValueSet(((DimensionAccessFormInfo.AccessFormInfo)accessForms.get(0)).getDimensions());
        GetCalFmlEnv getCalFmlEnv = new GetCalFmlEnv().setFormSchemeKey(formScheme.getKey()).setFormulaSchemeKey(calculateParam.getFormulaSchemeKey()).setFormulaMaps(formulas).setAccessForms(accessFormKeys).setWholeBetween(containBetween).setDimensionValueSet(dimensionValueSet).setParsedExpressions(expressions).setMode(calculateParam.getMode()).setProviderStore(this.helper.getProviderStore(calculateParam.getIgnoreItems()));
        ICalFmlProvider calFmlProvider = this.paramUtil.getCalFmlProvider(getCalFmlEnv);
        List<FmlExecInfo> fmlExecInfos = calFmlProvider.getFml(getCalFmlEnv);
        if (CollectionUtils.isEmpty(fmlExecInfos) || CollectionUtils.isEmpty(fmlExecInfos.get(0).getParsedExpressions())) {
            this.logHelper.calInfo(formScheme.getTaskKey(), logDimension, MSG_NO_CAL_FORMULA, MSG_NO_CAL_FORMULA_PREFIX + formulaSchemeInfo + "\u2014\u2014" + formInfo);
            return "\u65e0\u516c\u5f0f";
        }
        List<IParsedExpression> exeFml = fmlExecInfos.get(0).getParsedExpressions();
        DimensionValueSet exeDim = fmlExecInfos.get(0).getDimensionValueSet();
        ExecutorContext executorContext = this.formulaParseUtil.getExecutorContext(calculateParam, exeDim);
        FormulaCallBack callBack = this.formulaParseUtil.getFmlCallBack(exeFml, calculateParam.isFmlJIT());
        IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callBack);
        CalculateMonitor monitor = new CalculateMonitor(formScheme.getKey(), calculateParam.getFormulaSchemeKey(), exeDim, exeFml);
        LogDimensionCollection logDimension1 = this.logHelper.getLogDimension(formScheme, exeDim);
        String formInfo1 = this.logHelper.getFormInfo(accessFormKeys);
        try {
            runner.prepareCalc(executorContext, exeDim, (IMonitor)monitor);
            runner.run((IMonitor)monitor);
            this.logHelper.calInfo(formScheme.getTaskKey(), logDimension1, "\u8fd0\u7b97\u6267\u884c\u6210\u529f", "\u8c03\u7528\u5f15\u64ce\u6267\u884c\u8fd0\u7b97\u516c\u5f0f\u6210\u529f\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formInfo1);
        }
        catch (Throwable throwable) {
            this.logHelper.calError(formScheme.getTaskKey(), logDimension1, "\u8fd0\u7b97\u6267\u884c\u5931\u8d25", "\u8c03\u7528\u5f15\u64ce\u6267\u884c\u8fd0\u7b97\u516c\u5f0f\u51fa\u9519\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formInfo1);
            logger.error("\u8fd0\u7b97\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + throwable.getMessage(), throwable);
        }
        this.calculateHelper.publishCalInfo(calculateParam.getDimensionCollection(), formScheme, formKeys);
        return "success";
    }

    @Override
    public void batchCalculate(CalculateParam calculateParam, IFmlMonitor fmlMonitor) {
        this.calculateExecuteService.execute(calculateParam, fmlMonitor);
    }

    @Override
    public String batchCalculateAsync(CalculateParam calculateParam) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = this.paramUtil.getNpRealTimeTaskInfo(calculateParam, (AbstractRealTimeJob)new BatchCalculateAsyncTaskExecutor());
        return this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNC_TASK_BATCH_CALCULATE");
    }

    @Override
    public void calculate(CalPar calPar) {
        IFmlExecInfoProvider provider = this.fmlExecInfoProviderFactory.getProvider(calPar.getBaseFmlFactoryParam());
        this.validate(provider);
        List<FmlExecInfo> fmlExecInfos = provider.getFmlExecInfo();
        DimensionCombination dim = (DimensionCombination)provider.getDimensionCollection().getDimensionCombinations().get(0);
        String formulaSchemeInfo = this.logHelper.getFormulaSchemeInfo(Collections.singletonList(provider.getFormulaSchemeKey()));
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(calPar.getFormSchemeKey());
        LogDimensionCollection logDimension = this.logHelper.getLogDimension(formScheme, dim.toDimensionValueSet());
        if (CollectionUtils.isEmpty(fmlExecInfos)) {
            this.logHelper.calInfo(formScheme.getTaskKey(), logDimension, MSG_NO_CAL_FORMULA, MSG_NO_CAL_FORMULA_PREFIX + formulaSchemeInfo);
            return;
        }
        FmlExecInfo fmlExecInfo = fmlExecInfos.get(0);
        List<IParsedExpression> parsedExpressions = fmlExecInfo.getParsedExpressions();
        List<IParsedExpression> filteredFml = this.formulaParseUtil.customFilterFml(parsedExpressions, calPar.getFormSchemeKey(), provider.getFormulaSchemeKey());
        if (CollectionUtils.isEmpty(filteredFml)) {
            this.logHelper.calInfo(formScheme.getTaskKey(), logDimension, MSG_NO_CAL_FORMULA, MSG_NO_CAL_FORMULA_PREFIX + formulaSchemeInfo);
        } else {
            DimensionValueSet exeDim = fmlExecInfo.getDimensionValueSet();
            ExecutorContext executorContext = provider.getFmlExecutorContext(this.formulaParseUtil.getExecutorContext(calPar.getFormSchemeKey(), exeDim));
            FormulaCallBack callBack = this.formulaParseUtil.getFmlCallBack(filteredFml, provider.fmlJIT());
            IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callBack);
            CalculateMonitor monitor = new CalculateMonitor(formScheme.getKey(), provider.getFormulaSchemeKey(), exeDim, filteredFml);
            LogDimensionCollection logDimension1 = this.logHelper.getLogDimension(formScheme, exeDim);
            String formulaInfo = this.logHelper.getFormulaInfo(filteredFml.stream().filter(CommonUtils.distinctByKey(o -> o.getSource().getId())).map(o -> o.getSource().getId()).collect(Collectors.toList()));
            try {
                runner.prepareCalc(executorContext, exeDim, (IMonitor)monitor);
                runner.run((IMonitor)monitor);
                this.logHelper.calInfo(formScheme.getTaskKey(), logDimension1, "\u8fd0\u7b97\u6267\u884c\u6210\u529f", "\u8c03\u7528\u5f15\u64ce\u6267\u884c\u8fd0\u7b97\u516c\u5f0f\u6210\u529f\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formulaInfo);
            }
            catch (Throwable throwable) {
                this.logHelper.calError(formScheme.getTaskKey(), logDimension1, "\u8fd0\u7b97\u6267\u884c\u5931\u8d25", "\u8c03\u7528\u5f15\u64ce\u6267\u884c\u8fd0\u7b97\u516c\u5f0f\u51fa\u9519\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formulaInfo);
                logger.error("\u8fd0\u7b97\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + throwable.getMessage(), throwable);
            }
        }
    }

    private void validate(IFmlExecInfoProvider provider) {
        DimensionCollection dimensionCollection = provider.getDimensionCollection();
        String formulaSchemeKey = provider.getFormulaSchemeKey();
        List<FmlExecInfo> fmlExecInfo = provider.getFmlExecInfo();
        if (dimensionCollection == null || !StringUtils.hasText(formulaSchemeKey)) {
            throw new IllegalArgumentException("dimensionCollection or formulaSchemeKey is null");
        }
        if (dimensionCollection.getDimensionCombinations().size() > 1) {
            throw new UnsupportedOperationException("\u8be5\u63a5\u53e3\u6682\u4e0d\u652f\u6301\u591a\u7ef4\u5ea6");
        }
        if (fmlExecInfo != null && fmlExecInfo.size() > 1) {
            throw new UnsupportedOperationException("\u8be5\u63a5\u53e3\u6682\u4e0d\u652f\u6301fmlExecInfo.size()>1");
        }
    }
}

