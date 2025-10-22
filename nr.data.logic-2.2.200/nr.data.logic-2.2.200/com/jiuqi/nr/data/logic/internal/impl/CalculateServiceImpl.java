/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.logic.internal.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.logic.api.ICalculateService;
import com.jiuqi.nr.data.logic.api.param.CalEvent;
import com.jiuqi.nr.data.logic.api.param.CalExeResult;
import com.jiuqi.nr.data.logic.api.param.CalPar;
import com.jiuqi.nr.data.logic.api.param.CalState;
import com.jiuqi.nr.data.logic.api.param.FmlExecInfo;
import com.jiuqi.nr.data.logic.common.CommonUtils;
import com.jiuqi.nr.data.logic.internal.impl.CalEngineMonitor;
import com.jiuqi.nr.data.logic.internal.impl.DataLogicServiceFactory;
import com.jiuqi.nr.data.logic.internal.impl.DefCalMonitor;
import com.jiuqi.nr.data.logic.internal.impl.EmptyFmlMonitor;
import com.jiuqi.nr.data.logic.internal.impl.FmlExecInfoProvider;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.spi.ICalOptionProvider;
import com.jiuqi.nr.data.logic.spi.ICalculateMonitor;
import com.jiuqi.nr.data.logic.spi.IFmlExecInfoProvider;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class CalculateServiceImpl
implements ICalculateService {
    private static final Logger logger = LoggerFactory.getLogger(CalculateServiceImpl.class);
    private final DataLogicServiceFactory serviceFactory;
    private final IProviderStore providerStore;
    private final ICalOptionProvider optionProvider;
    private final IFmlExecInfoProvider fmlExecInfoProvider;

    public CalculateServiceImpl(DataLogicServiceFactory serviceFactory, IProviderStore providerStore, ICalOptionProvider optionProvider, IFmlExecInfoProvider fmlExecInfoProvider) {
        this.serviceFactory = serviceFactory;
        this.providerStore = providerStore;
        this.optionProvider = optionProvider;
        this.fmlExecInfoProvider = fmlExecInfoProvider;
    }

    @Override
    public CalExeResult calculate(CalPar calPar) {
        DefCalMonitor defCalMonitor = new DefCalMonitor(EmptyFmlMonitor.getInstance(), this.serviceFactory.getCalculateListeners());
        return this.execute(calPar, defCalMonitor);
    }

    @Override
    public CalExeResult calculate(CalPar calPar, ICalculateMonitor monitor) {
        return this.execute(calPar, monitor);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private CalExeResult execute(CalPar calPar, ICalculateMonitor monitor) {
        monitor.progressAndMessage(0.07, "calculate_start");
        if (this.fmlExecInfoProvider instanceof FmlExecInfoProvider) {
            FmlExecInfoProvider provider = (FmlExecInfoProvider)this.fmlExecInfoProvider;
            provider.setProviderStore(this.providerStore);
        }
        this.validate(this.fmlExecInfoProvider);
        CalEvent calEvent = new CalEvent(calPar.getFormSchemeKey(), this.fmlExecInfoProvider);
        monitor.executeBefore(calEvent);
        List<FmlExecInfo> fmlExecInfos = this.fmlExecInfoProvider.getFmlExecInfo();
        DimensionCombination dim = (DimensionCombination)this.fmlExecInfoProvider.getDimensionCollection().getDimensionCombinations().get(0);
        String formulaSchemeInfo = this.serviceFactory.getLogHelper().getFormulaSchemeInfo(Collections.singletonList(this.fmlExecInfoProvider.getFormulaSchemeKey()));
        FormSchemeDefine formScheme = this.serviceFactory.getRunTimeViewController().getFormScheme(calPar.getFormSchemeKey());
        LogDimensionCollection logDimension = this.serviceFactory.getLogHelper().getLogDimension(formScheme, dim.toDimensionValueSet());
        if (CollectionUtils.isEmpty(fmlExecInfos)) {
            this.serviceFactory.getLogHelper().calInfo(formScheme.getTaskKey(), logDimension, "\u65e0\u8fd0\u7b97\u516c\u5f0f", "\u65e0\u8fd0\u7b97\u516c\u5f0f\u2014\u2014" + formulaSchemeInfo);
            return new CalExeResult(CalState.NO_FML);
        }
        FmlExecInfo fmlExecInfo = fmlExecInfos.get(0);
        List<IParsedExpression> parsedExpressions = fmlExecInfo.getParsedExpressions();
        List<IParsedExpression> filteredFml = this.serviceFactory.getFormulaParseUtil().customFilterFml(parsedExpressions, calPar.getFormSchemeKey(), this.fmlExecInfoProvider.getFormulaSchemeKey());
        if (CollectionUtils.isEmpty(filteredFml)) {
            this.serviceFactory.getLogHelper().calInfo(formScheme.getTaskKey(), logDimension, "\u65e0\u8fd0\u7b97\u516c\u5f0f", "\u65e0\u8fd0\u7b97\u516c\u5f0f\u2014\u2014" + formulaSchemeInfo);
            return new CalExeResult(CalState.NO_FML);
        }
        int calCount = this.optionProvider.getCalculateCount(calPar.getFormSchemeKey());
        double formulaStartProgress = 0.3;
        double formulaEndProgress = 1.0;
        DimensionValueSet exeDim = fmlExecInfo.getDimensionValueSet();
        try {
            ExecutorContext executorContext = this.fmlExecInfoProvider.getFmlExecutorContext(this.serviceFactory.getFormulaParseUtil().getExecutorContext(calPar.getFormSchemeKey(), exeDim));
            LogDimensionCollection logDimension1 = this.serviceFactory.getLogHelper().getLogDimension(formScheme, exeDim);
            String formulaInfo = this.serviceFactory.getLogHelper().getFormulaInfo(filteredFml.stream().filter(CommonUtils.distinctByKey(o -> o.getSource().getId())).map(o -> o.getSource().getId()).collect(Collectors.toList()));
            for (int i = 0; i < calCount; ++i) {
                if (monitor.isCancel()) {
                    monitor.canceled(null, null);
                    this.serviceFactory.getLogHelper().calInfo(formScheme.getTaskKey(), logDimension1, "\u8fd0\u7b97\u6267\u884c\u53d6\u6d88", "\u8c03\u7528\u5f15\u64ce\u6267\u884c\u8fd0\u7b97\u516c\u5f0f\u88ab\u53d6\u6d88\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formulaInfo);
                    CalExeResult calExeResult = new CalExeResult(CalState.CANCELED);
                    return calExeResult;
                }
                FormulaCallBack callBack = this.serviceFactory.getFormulaParseUtil().getFmlCallBack(filteredFml, this.fmlExecInfoProvider.fmlJIT());
                IFormulaRunner runner = this.serviceFactory.getDataAccessProvider().newFormulaRunner(callBack);
                DataStatusPresetInfo info = this.initDataStatusPresetInfo(formScheme.getKey(), this.fmlExecInfoProvider.getFormulaSchemeKey(), exeDim, callBack.getParsedExpressions());
                double runStartProgress = formulaStartProgress + (double)i / (double)calCount * (formulaEndProgress - formulaStartProgress);
                double runEndProgress = formulaStartProgress + (double)(i + 1) / (double)calCount * (formulaEndProgress - formulaStartProgress);
                CalEngineMonitor calEngineMonitor = new CalEngineMonitor(monitor, info);
                calEngineMonitor.setStartProgress(runStartProgress, runEndProgress);
                monitor.progressAndMessage(runStartProgress, "calculateing");
                try {
                    runner.prepareCalc(executorContext, exeDim, (IMonitor)calEngineMonitor);
                    runner.run((IMonitor)calEngineMonitor);
                    this.serviceFactory.getLogHelper().calInfo(formScheme.getTaskKey(), logDimension1, "\u8fd0\u7b97\u6267\u884c\u6210\u529f", "\u8c03\u7528\u5f15\u64ce\u6267\u884c\u8fd0\u7b97\u516c\u5f0f\u6210\u529f\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formulaInfo);
                    continue;
                }
                catch (Throwable throwable) {
                    this.serviceFactory.getLogHelper().calError(formScheme.getTaskKey(), logDimension1, "\u8fd0\u7b97\u6267\u884c\u5931\u8d25", "\u8c03\u7528\u5f15\u64ce\u6267\u884c\u8fd0\u7b97\u516c\u5f0f\u51fa\u9519\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formulaInfo);
                    logger.error("\u8fd0\u7b97\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + throwable.getMessage(), throwable);
                    monitor.error("calculate_execute_exception", throwable);
                    CalExeResult calExeResult = new CalExeResult(CalState.ERROR);
                    monitor.executeAfter(calEvent);
                    return calExeResult;
                }
            }
            monitor.finish("calculate_success_info", null);
            CalExeResult calExeResult = new CalExeResult(CalState.SUCCESS);
            return calExeResult;
        }
        catch (Exception e) {
            logger.error("\u8fd0\u7b97\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            monitor.error("calculate_execute_exception", e);
            CalExeResult calExeResult = new CalExeResult(CalState.ERROR);
            return calExeResult;
        }
        finally {
            monitor.executeAfter(calEvent);
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

    private DataStatusPresetInfo initDataStatusPresetInfo(String formSchemeKey, String formulaSchemeKey, DimensionValueSet dimensionValueSet, Collection<IParsedExpression> expressions) {
        if (this.serviceFactory.getDataStatusPreInitService() != null && DimensionUtil.dimSingle(dimensionValueSet)) {
            try {
                DimensionCombination combination = new DimensionCombinationBuilder(dimensionValueSet).getCombination();
                Set formulaKeys = expressions.stream().map(o -> o.getSource().getId()).collect(Collectors.toSet());
                return this.serviceFactory.getDataStatusPreInitService().initInfo(combination, formSchemeKey, formulaSchemeKey, formulaKeys);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }
}

