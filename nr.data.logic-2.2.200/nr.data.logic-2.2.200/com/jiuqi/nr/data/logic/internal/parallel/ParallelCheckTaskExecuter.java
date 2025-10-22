/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaUnitGroup
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.parallel.BatchParallelExeTask
 *  com.jiuqi.nr.parallel.BatchTaskExecuteFactoryMgr
 *  com.jiuqi.nr.parallel.IParallelTaskExecuter
 *  org.jetbrains.annotations.Nullable
 */
package com.jiuqi.nr.data.logic.internal.parallel;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.api.param.CheckExeContext;
import com.jiuqi.nr.data.logic.facade.extend.IAllCheckCacheCtrl;
import com.jiuqi.nr.data.logic.facade.extend.ICheckErrorFilter;
import com.jiuqi.nr.data.logic.facade.extend.ICheckErrorFilterFactory;
import com.jiuqi.nr.data.logic.facade.extend.ICheckFmlProvider;
import com.jiuqi.nr.data.logic.facade.extend.SpecificDimBuilder;
import com.jiuqi.nr.data.logic.facade.extend.param.ErrFilterParam;
import com.jiuqi.nr.data.logic.facade.extend.param.FmlExecInfo;
import com.jiuqi.nr.data.logic.facade.extend.param.GetCheckFmlEnv;
import com.jiuqi.nr.data.logic.facade.listener.ICheckRecordListener;
import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.output.CheckRecordData;
import com.jiuqi.nr.data.logic.internal.helper.CheckHelper;
import com.jiuqi.nr.data.logic.internal.impl.cksr.obj.CheckRecordFormInfo;
import com.jiuqi.nr.data.logic.internal.log.LogHelper;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.obj.ParallelFormulaExecuteInfo;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.data.logic.internal.util.SystemOptionUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.FixedDimBuilder;
import com.jiuqi.nr.data.logic.monitor.CheckBatchSerialMonitor;
import com.jiuqi.nr.data.logic.monitor.CheckMonitor;
import com.jiuqi.nr.data.logic.monitor.ParallelMonitor;
import com.jiuqi.nr.data.logic.spi.ICheckErrHandler;
import com.jiuqi.nr.data.logic.spi.ICheckErrHandlerProvider;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaUnitGroup;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.BatchTaskExecuteFactoryMgr;
import com.jiuqi.nr.parallel.IParallelTaskExecuter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component(value="parallelCheckTaskExecuterNew")
public class ParallelCheckTaskExecuter
implements IParallelTaskExecuter {
    private static final Logger logger = LoggerFactory.getLogger(ParallelCheckTaskExecuter.class);
    @Autowired
    private SystemOptionUtil systemOptionUtil;
    @Autowired
    private FormulaParseUtil formulaParseUtil;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private ParamUtil paramUtil;
    @Autowired(required=false)
    private IAllCheckCacheCtrl allCheckCacheCtrl;
    @Autowired
    private CheckHelper checkHelper;
    @Autowired
    private List<ICheckFmlProvider> fmlProviders;
    @Autowired
    private LogHelper logHelper;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired(required=false)
    private List<ICheckErrHandlerProvider> checkErrHandlerProviders;
    @Autowired(required=false)
    private ICheckErrorFilterFactory checkErrorFilterFactory;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doExecute(BatchParallelExeTask task, IMonitor monitor) {
        ParallelFormulaExecuteInfo taskInfo = (ParallelFormulaExecuteInfo)task.getTaskInfo();
        String taskMsg = "\u5ba1\u6838\u6267\u884c\u4efb\u52a1\u4fe1\u606f\uff1a" + taskInfo.getActionName() + "\u2014\u2014" + taskInfo.getActionId() + ":" + task.getParentTaskID() + ":" + task.getKey();
        logger.debug(taskMsg);
        String formulaSchemeKey = taskInfo.getFormulaSchemeKey();
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKey(formulaSchemeKey);
        String formulaSchemeInfo = this.logHelper.getFormulaSchemeInfo(Collections.singletonList(formulaSchemeKey));
        LogDimensionCollection logDimension = null;
        String formOrFmlInfo = null;
        ActionEnum action = ActionEnum.getByName(taskInfo.getActionName());
        assert (action != null) : "\u5ba1\u6838\u64cd\u4f5c\u7c7b\u578b\u5f02\u5e38";
        try {
            boolean record = taskInfo.isCheckRecord();
            Map dimensionValues = task.getDimensionValues();
            String mainDimName = this.entityMetaService.getDimensionName(formScheme.getDw());
            DimensionValueSet dimExeC = DimensionUtil.getDimensionValueSet(dimensionValues);
            ICheckErrorFilter checkErrorFilter = this.getCheckErrorFilter(formScheme, dimExeC, taskInfo);
            int batchSplitCount = this.getBatchSplitCount(taskInfo);
            List<DimensionValueSet> dimensionValueSetList = DimensionUtil.getDimensionValueSetList(dimensionValues, batchSplitCount);
            CheckParam checkParam = new CheckParam();
            checkParam.setFormulaSchemeKey(formulaSchemeKey);
            Mode mode = Mode.getByKey(taskInfo.getMode());
            ArrayList<CheckRecordData> checkRecordDataList = new ArrayList<CheckRecordData>();
            ArrayList<FmlExecInfo> fmlExecInfos = new ArrayList<FmlExecInfo>();
            Iterator<DimensionValueSet> iterator = dimensionValueSetList.iterator();
            while (iterator.hasNext()) {
                Object fml;
                DimensionValueSet dimensionValueSet;
                Iterator<ICheckErrHandler> value = (dimensionValueSet = iterator.next()).getValue(mainDimName);
                List<String> units = value instanceof String ? Collections.singletonList((String)((Object)value)) : (List<String>)((Object)value);
                List<FormulaUnitGroup> formulaUnitGroups = this.formulaParseUtil.getFormulaUnitGroups(units, formulaSchemeKey);
                GetCheckFmlEnv getFmlEnv = ParallelCheckTaskExecuter.getGetFmlEnv(taskInfo, dimensionValueSet, formulaUnitGroups);
                ICheckFmlProvider fmlProvider = this.getFmlProvider(getFmlEnv);
                if (fmlProvider == null || CollectionUtils.isEmpty(fml = fmlProvider.getFml(getFmlEnv))) continue;
                fmlExecInfos.addAll((Collection<FmlExecInfo>)fml);
            }
            IDatabase database = this.paramUtil.getDataBase();
            List<ICheckErrHandler> checkErrHandlers = this.getCheckErrHandler(action, taskInfo);
            if (!CollectionUtils.isEmpty(checkErrHandlers)) {
                for (ICheckErrHandler checkErrHandler : checkErrHandlers) {
                    checkErrHandler.start();
                }
            }
            ArrayList<String> cacheKeys = new ArrayList<String>();
            for (FmlExecInfo fmlExecInfo : fmlExecInfos) {
                List<IParsedExpression> execFml = this.formulaParseUtil.customFilterFml(fmlExecInfo.getParsedExpressions(), formScheme.getKey(), formulaSchemeKey);
                logDimension = this.logHelper.getLogDimension(formScheme, fmlExecInfo.getDimensionValueSet());
                if (mode == Mode.FORM) {
                    formOrFmlInfo = this.logHelper.getFormInfo(taskInfo.getForms());
                } else {
                    LinkedHashSet<String> formulaCodes = new LinkedHashSet<String>();
                    for (IParsedExpression parsedExpression : execFml) {
                        formulaCodes.add(parsedExpression.getSource().getCode());
                    }
                    formOrFmlInfo = this.logHelper.getFormulaInfoByCode(formulaCodes);
                }
                FormulaCallBack callBack = this.formulaParseUtil.getFmlCallBack(execFml, taskInfo.isFmlJIT());
                CheckMonitor checkMonitor = (CheckMonitor)BatchTaskExecuteFactoryMgr.getInstance().findFactory("parallel.check.new").getMonitor(null);
                this.setMonitorEnv(taskInfo, formScheme, fmlExecInfo.getDimensionValueSet(), checkMonitor, callBack, database);
                checkMonitor.setCheckErrHandlers(checkErrHandlers);
                checkMonitor.setCheckErrorFilter(checkErrorFilter);
                cacheKeys.add(checkMonitor.getCacheKey());
                CheckBatchSerialMonitor serialMonitor = new CheckBatchSerialMonitor();
                double serialWeight = 1.0 / (double)fmlExecInfos.size();
                serialMonitor.setProgressWeight(serialWeight);
                AbstractMonitor abstractMonitor = (AbstractMonitor)monitor;
                serialMonitor.setStartProgress(abstractMonitor.getCurrentProgress());
                serialMonitor.setMainMonitor(abstractMonitor);
                serialMonitor.setErrorMonitor(checkMonitor);
                if (monitor instanceof ParallelMonitor) {
                    serialMonitor.setFmlEngineExtMonitor(((ParallelMonitor)monitor).getFmlEngineExtMonitor());
                }
                IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callBack);
                ExecutorContext executorContext = this.formulaParseUtil.getExecutorContext(checkParam, dimExeC);
                checkMonitor.setExecutorContext(executorContext);
                runner.prepareCheck(executorContext, null, (IMonitor)serialMonitor);
                runner.setMasterKeyValues(fmlExecInfo.getDimensionValueSet());
                runner.run((IMonitor)serialMonitor);
                if (monitor.isCancel()) {
                    this.logHelper.checkInfo(formScheme.getTaskKey(), logDimension, action.getTitle() + "\u6267\u884c\u53d6\u6d88", "\u5b50\u4efb\u52a1\u8c03\u7528\u5f15\u64ce\u6267\u884c\u5ba1\u6838\u516c\u5f0f\u88ab\u53d6\u6d88\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formOrFmlInfo);
                    break;
                }
                this.logHelper.checkInfo(formScheme.getTaskKey(), logDimension, action.getTitle() + "\u6267\u884c\u6210\u529f", "\u8c03\u7528\u5f15\u64ce\u6267\u884c\u5ba1\u6838\u516c\u5f0f\u6210\u529f\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formOrFmlInfo);
                if (record) {
                    checkRecordDataList.addAll(checkMonitor.getCheckRecordData().values());
                }
                if (!taskInfo.isCheckDes()) continue;
                this.checkDes(formulaSchemeKey, formScheme, fmlExecInfo, execFml);
            }
            if (monitor.isCancel()) {
                this.cancel(taskInfo, formScheme, action, checkErrHandlers, cacheKeys);
            } else {
                this.finish(taskInfo, checkRecordDataList, checkErrHandlers);
            }
        }
        catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            if (throwable instanceof Exception) {
                monitor.exception((Exception)throwable);
            } else {
                monitor.exception((Exception)new RuntimeException(throwable));
            }
            this.logHelper.checkError(formScheme.getTaskKey(), logDimension, action.getTitle() + "\u6267\u884c\u5931\u8d25", "\u8c03\u7528\u5f15\u64ce\u6267\u884c\u5ba1\u6838\u516c\u5f0f\u51fa\u9519\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formOrFmlInfo);
        }
        finally {
            task.setFinished(true);
        }
    }

    private void cancel(ParallelFormulaExecuteInfo taskInfo, FormSchemeDefine formScheme, ActionEnum action, List<ICheckErrHandler> checkErrHandlers, List<String> cacheKeys) {
        this.checkHelper.delCheckResult(action, formScheme.getKey(), taskInfo.getActionId(), cacheKeys, taskInfo.getExecuteId());
        if (!CollectionUtils.isEmpty(checkErrHandlers)) {
            for (ICheckErrHandler checkErrHandler : checkErrHandlers) {
                checkErrHandler.canceled();
            }
        }
    }

    private void finish(ParallelFormulaExecuteInfo taskInfo, List<CheckRecordData> checkRecordDataList, List<ICheckErrHandler> checkErrHandlers) {
        List<ICheckRecordListener> enableRecordListeners = this.checkHelper.getEnableRecordListeners();
        if (!CollectionUtils.isEmpty(checkRecordDataList) && !CollectionUtils.isEmpty(enableRecordListeners)) {
            this.checkHelper.checkRecord(enableRecordListeners, checkRecordDataList, taskInfo);
        }
        if (!CollectionUtils.isEmpty(checkErrHandlers)) {
            for (ICheckErrHandler checkErrHandler : checkErrHandlers) {
                checkErrHandler.finish();
            }
        }
    }

    @Nullable
    private ICheckErrorFilter getCheckErrorFilter(FormSchemeDefine formScheme, DimensionValueSet dimExeC, ParallelFormulaExecuteInfo taskInfo) {
        ICheckErrorFilter checkErrorFilter = null;
        if (this.checkErrorFilterFactory != null) {
            ErrFilterParam errFilterParam = new ErrFilterParam();
            errFilterParam.setTaskKey(formScheme.getTaskKey());
            errFilterParam.setFormSchemeKey(formScheme.getKey());
            errFilterParam.setDimensionValueSet(dimExeC);
            errFilterParam.setFormKeys(taskInfo.getForms());
            checkErrorFilter = this.checkErrorFilterFactory.getCheckErrorFilter(errFilterParam);
        }
        return checkErrorFilter;
    }

    private List<ICheckErrHandler> getCheckErrHandler(ActionEnum action, ParallelFormulaExecuteInfo taskInfo) {
        if (this.checkErrHandlerProviders != null) {
            ArrayList<ICheckErrHandler> checkErrHandlers = new ArrayList<ICheckErrHandler>();
            CheckExeContext checkExeContext = new CheckExeContext();
            checkExeContext.setActionType(action);
            checkExeContext.setBatchId(taskInfo.getActionId());
            checkExeContext.setFormSchemeKey(taskInfo.getFormSchemeKey());
            checkExeContext.setFormulaSchemeKey(taskInfo.getFormulaSchemeKey());
            for (ICheckErrHandlerProvider checkErrHandlerProvider : this.checkErrHandlerProviders) {
                checkErrHandlers.add(checkErrHandlerProvider.getCheckErrHandler(checkExeContext));
            }
            return checkErrHandlers;
        }
        return Collections.emptyList();
    }

    private int getBatchSplitCount(ParallelFormulaExecuteInfo taskInfo) {
        return taskInfo.getBatchSplitCount() > 0 ? taskInfo.getBatchSplitCount() : this.systemOptionUtil.getBatchSplitCount();
    }

    private void checkDes(String formulaSchemeKey, FormSchemeDefine formScheme, FmlExecInfo fmlExecInfo, List<IParsedExpression> execFml) {
        CheckDesParam checkDesParam = new CheckDesParam();
        checkDesParam.setFormSchemeKey(formScheme.getKey());
        checkDesParam.setFormulaSchemeKey(Collections.singletonList(formulaSchemeKey));
        DimensionValueSet dimensionValueSet = fmlExecInfo.getDimensionValueSet();
        FixedDimBuilder fixedDimBuilder = new FixedDimBuilder(this.entityUtil.getContextMainDimId(formScheme.getDw()), dimensionValueSet);
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, formScheme.getKey(), (SpecificDimBuilder)fixedDimBuilder);
        checkDesParam.setDimensionCollection(dimensionCollection);
        checkDesParam.setFormulaKey(execFml.stream().map(IParsedExpression::getKey).collect(Collectors.toList()));
        this.checkHelper.checkDes(checkDesParam);
    }

    private static GetCheckFmlEnv getGetFmlEnv(ParallelFormulaExecuteInfo taskInfo, DimensionValueSet dimensionValueSet, List<FormulaUnitGroup> formulaUnitGroups) {
        return new GetCheckFmlEnv().setFormSchemeKey(taskInfo.getFormSchemeKey()).setFormulaSchemeKey(taskInfo.getFormulaSchemeKey()).setFormulaMaps(taskInfo.getFormulaMaps()).setAccessForms(taskInfo.getForms()).setFormulaCheckTypes(taskInfo.getFormulaCheckTypes()).setWholeBetween(taskInfo.isContainBetween()).setDimensionValueSet(dimensionValueSet).setFormulaUnitGroups(formulaUnitGroups).setMode(Mode.getByKey(taskInfo.getMode()));
    }

    private ICheckFmlProvider getFmlProvider(GetCheckFmlEnv env) {
        ICheckFmlProvider defaultFmlProvider = null;
        for (ICheckFmlProvider fmlProvider : this.fmlProviders) {
            if (fmlProvider.isUse(env)) {
                return fmlProvider;
            }
            if (defaultFmlProvider != null || !"DefaultCheckFmlProvider".equals(fmlProvider.getType())) continue;
            defaultFmlProvider = fmlProvider;
        }
        return defaultFmlProvider;
    }

    private void setMonitorEnv(ParallelFormulaExecuteInfo taskInfo, FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, CheckMonitor checkMonitor, FormulaCallBack callBack, IDatabase database) {
        ActionEnum action = ActionEnum.getByName(taskInfo.getActionName());
        checkMonitor.setAction(action);
        checkMonitor.setRecord(taskInfo.isCheckRecord());
        checkMonitor.setDatabase(database);
        if (taskInfo.isCheckRecord()) {
            ParallelCheckTaskExecuter.initCheckRecord(dimensionValueSet, checkMonitor, callBack);
        }
        List<String> dimEntityNames = this.entityUtil.getDimEntities(formScheme).stream().map(EntityData::getDimensionName).collect(Collectors.toList());
        checkMonitor.setFormSchemeKey(formScheme.getKey());
        checkMonitor.setFormulaSchemeKey(taskInfo.getFormulaSchemeKey());
        EntityData dwEntity = this.entityUtil.getEntity(this.entityUtil.getContextMainDimId(formScheme.getDw()));
        checkMonitor.setDwDimensionName(dwEntity.getDimensionName());
        EntityData periodEntity = this.entityUtil.getPeriodEntity(formScheme.getDateTime());
        checkMonitor.setPeriodDimensionName(periodEntity.getDimensionName());
        checkMonitor.setDimDimensionName(dimEntityNames);
        Map<String, DimensionValue> dimensionSet = DimensionUtil.getDimensionSet(dimensionValueSet);
        Map<String, DimensionValue> onlyDimCartesian = this.entityUtil.getOnlyDimCartesian(dimensionSet, formScheme);
        List<DimensionValueSet> onlyDim = DimensionUtil.getDimensionValueSetList(onlyDimCartesian);
        checkMonitor.setOnlyDimCartesian(onlyDim);
        checkMonitor.setCheckDim(dimensionValueSet);
        checkMonitor.setActionID(taskInfo.getActionId());
        checkMonitor.setExecuteId(taskInfo.getExecuteId());
        checkMonitor.setCheckMax(taskInfo.getCheckMax());
        checkMonitor.setAllCheckCacheCtrl(this.allCheckCacheCtrl);
        checkMonitor.setCacheKey(CheckResultUtil.buildAllCheckCacheKey(taskInfo.getFormulaSchemeKey(), new DimensionValueSet(dimensionValueSet)));
        if (ActionEnum.BATCH_CHECK == action) {
            checkMonitor.setDwEntityDataLoader(this.entityUtil.getEntityDataLoader(dwEntity.getKey(), periodEntity.getKey(), dimensionValueSet, null));
        }
        checkMonitor.setAllowableError(taskInfo.getAllowableError());
    }

    private static void initCheckRecord(DimensionValueSet dimensionValueSet, CheckMonitor checkMonitor, FormulaCallBack callBack) {
        HashMap<String, CheckRecordFormInfo> exeFormInfoMap = new HashMap<String, CheckRecordFormInfo>();
        if (!CollectionUtils.isEmpty(callBack.getParsedExpressions())) {
            for (IParsedExpression parsedExpression : callBack.getParsedExpressions()) {
                String formKey = parsedExpression.getFormKey();
                Integer checkType = parsedExpression.getSource().getChecktype();
                ParallelCheckTaskExecuter.collectCheckRecordFormInfo(exeFormInfoMap, formKey, checkType);
            }
        } else if (!CollectionUtils.isEmpty(callBack.getFormulas())) {
            for (Formula formula : callBack.getFormulas()) {
                String formKey = formula.getFormKey();
                Integer checkType = formula.getChecktype();
                ParallelCheckTaskExecuter.collectCheckRecordFormInfo(exeFormInfoMap, formKey, checkType);
            }
        }
        checkMonitor.setCheckRecordData(CheckRecordData.initCheckRecordData(dimensionValueSet, new ArrayList<CheckRecordFormInfo>(exeFormInfoMap.values())));
    }

    private static void collectCheckRecordFormInfo(Map<String, CheckRecordFormInfo> exeFormInfoMap, String formKey, Integer checkType) {
        if (!StringUtils.hasText(formKey)) {
            formKey = "00000000-0000-0000-0000-000000000000";
        }
        if (exeFormInfoMap.containsKey(formKey)) {
            exeFormInfoMap.get(formKey).getExeFmlAllCheckTypes().add(checkType);
        } else {
            HashSet<Integer> checkTypes = new HashSet<Integer>(3);
            checkTypes.add(checkType);
            exeFormInfoMap.put(formKey, new CheckRecordFormInfo(formKey, checkTypes));
        }
    }
}

