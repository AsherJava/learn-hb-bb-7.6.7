/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.access.MergeDataPermission
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.access.UnitPermission
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.parallel.BatchParallelExeTask
 *  com.jiuqi.nr.parallel.IParallelRunner
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.logic.api.param.CheckExeResult;
import com.jiuqi.nr.data.logic.common.ExceptionEnum;
import com.jiuqi.nr.data.logic.facade.extend.param.CheckEvent;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.base.BaseEnv;
import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.service.ICheckResultService;
import com.jiuqi.nr.data.logic.internal.helper.CheckDataCollector;
import com.jiuqi.nr.data.logic.internal.helper.CheckHelper;
import com.jiuqi.nr.data.logic.internal.log.LogHelper;
import com.jiuqi.nr.data.logic.internal.obj.CKRRec;
import com.jiuqi.nr.data.logic.internal.obj.CheckData;
import com.jiuqi.nr.data.logic.internal.obj.CheckExeParam;
import com.jiuqi.nr.data.logic.internal.obj.FmlDimForm;
import com.jiuqi.nr.data.logic.internal.obj.ParallelFormulaExecuteInfo;
import com.jiuqi.nr.data.logic.internal.service.ICKRRecService;
import com.jiuqi.nr.data.logic.internal.service.ICheckExecuteService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.data.logic.internal.util.SplitCheckTableHelper;
import com.jiuqi.nr.data.logic.internal.util.SystemOptionUtil;
import com.jiuqi.nr.data.logic.monitor.ParallelMonitor;
import com.jiuqi.nr.data.logic.spi.ICheckMonitor;
import com.jiuqi.nr.data.logic.spi.ICheckOptionProvider;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.access.MergeDataPermission;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.access.UnitPermission;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.IParallelRunner;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class CheckExecuteServiceImpl
implements ICheckExecuteService {
    private static final Logger logger = LoggerFactory.getLogger(CheckExecuteServiceImpl.class);
    @Autowired
    private IParallelRunner parallelRunner;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private ParamUtil paramUtil;
    @Autowired
    private CheckHelper checkHelper;
    @Autowired
    private FormulaParseUtil formulaParseUtil;
    @Autowired
    private ICheckResultService checkResultService;
    @Autowired
    private LogHelper logHelper;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Value(value="${jiuqi.nr.data.logic.auto-check-bj:false}")
    private boolean autoCheckBJ;
    @Autowired
    private ICKRRecService ckrRecService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private SplitCheckTableHelper splitCheckTableHelper;
    @Autowired
    private SystemOptionUtil systemOptionUtil;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Deprecated
    public String execute(CheckParam checkParam, IFmlMonitor fmlMonitor, ActionEnum action, String executeId) {
        String actionId;
        long actionTime = System.currentTimeMillis();
        boolean monitorEnable = fmlMonitor != null;
        String customActionId = checkParam.getActionId();
        String string = StringUtils.isNotEmpty((String)customActionId) ? customActionId : (actionId = monitorEnable ? fmlMonitor.getTaskId() : UUID.randomUUID().toString());
        if (StringUtils.isEmpty((String)actionId)) {
            actionId = UUID.randomUUID().toString();
        }
        if (monitorEnable) {
            fmlMonitor.progressAndMessage(0.07, "get_param");
        }
        String formulaSchemeKey = checkParam.getFormulaSchemeKey();
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKey(formulaSchemeKey);
        String formSchemeKey = formScheme.getKey();
        DimensionCollection dimensionCollection = checkParam.getDimensionCollection();
        CheckEvent checkEvent = this.prepCheckEvent(formScheme, action, actionId, checkParam);
        try {
            List<String> formKeys;
            this.checkHelper.beforeCheck(checkEvent);
            this.checkHelper.cleanCache(action, dimensionCollection, formulaSchemeKey);
            LogDimensionCollection logDimension = this.logHelper.getLogDimension(formScheme, dimensionCollection);
            if (logDimension == null) {
                logger.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
                if (monitorEnable) {
                    fmlMonitor.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc(), null);
                }
                String string2 = actionId;
                return string2;
            }
            boolean record = this.checkHelper.recordOpen();
            IEntityDefine dwEntity = this.entityMetaService.queryEntity(formScheme.getDw());
            assert (dwEntity != null) : "\u4e3b\u7ef4\u5ea6\u672a\u627e\u5230";
            Mode mode = checkParam.getMode();
            List<String> rangeKeys = checkParam.getRangeKeys();
            Map<String, List<String>> formulas = this.formulaParseUtil.getFormulas(mode, rangeKeys, formulaSchemeKey, DataEngineConsts.FormulaType.CHECK);
            if (Mode.FORMULA == mode) {
                List<FormulaDefine> formulaDefines = this.formulaParseUtil.getFormulaDefines(formulaSchemeKey, rangeKeys);
                formKeys = formulaDefines.stream().map(FormulaDefine::getFormKey).distinct().collect(Collectors.toList());
            } else {
                formKeys = new ArrayList<String>(rangeKeys);
            }
            boolean containBetweenForm = formKeys.isEmpty() || formKeys.contains("00000000-0000-0000-0000-000000000000") || formKeys.contains(null);
            formKeys.remove("00000000-0000-0000-0000-000000000000");
            formKeys.remove(null);
            String formulaSchemeInfo = this.logHelper.getFormulaSchemeInfo(Collections.singletonList(formulaSchemeKey));
            List<DimensionAccessFormInfo.AccessFormInfo> accessForms = this.paramUtil.getAccessFormInfos(formKeys, checkParam, formScheme, AccessLevel.FormAccessLevel.FORM_READ);
            double formulaStartProgress = 0.3;
            double formulaEndProgress = 1.0;
            CheckData data = new CheckData(executeId);
            CheckDataCollector.getInstance().put(executeId, data);
            if (CollectionUtils.isEmpty(accessForms)) {
                String formInfo = this.logHelper.getFormInfo(formKeys);
                this.logHelper.checkInfo(formScheme.getTaskKey(), logDimension, action.getTitle() + "\u65e0\u5ba1\u6838\u6743\u9650", "\u5bf9\u5355\u4f4d\u3001\u62a5\u8868\u65e0\u5ba1\u6838\u6743\u9650\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formInfo);
            } else {
                List<FmlDimForm> fmlDimForms = this.paramUtil.getFmlDimForms(formScheme, accessForms, DataEngineConsts.FormulaType.CHECK);
                if (containBetweenForm) {
                    this.paramUtil.addBJFormAccess(fmlDimForms, formScheme.getKey(), dwEntity.getDimensionName());
                }
                for (int i = 0; i < fmlDimForms.size(); ++i) {
                    FmlDimForm fmlDimForm = fmlDimForms.get(i);
                    DimensionCollection fmlDimCollection = fmlDimForm.getDimensionCollection();
                    if (!containBetweenForm && this.autoCheckBJ) {
                        this.checkHelper.cleanCKR(action, formSchemeKey, actionId, Collections.singletonList("00000000-0000-0000-0000-000000000000"), this.dimensionCollectionUtil.getDimensionValues(fmlDimCollection));
                    }
                    ArrayList<String> execForms = new ArrayList<String>(fmlDimForm.getFormKeys());
                    double formStartProgress = formulaStartProgress + (double)i * 1.0 / (double)fmlDimForms.size() * (formulaEndProgress - formulaStartProgress);
                    double formEndProgress = formulaStartProgress + (double)(i + 1) * 1.0 / (double)fmlDimForms.size() * (formulaEndProgress - formulaStartProgress);
                    if (monitorEnable && fmlMonitor.isCancel()) {
                        String string3 = actionId;
                        return string3;
                    }
                    ParallelMonitor parallelMonitor = new ParallelMonitor(fmlMonitor);
                    parallelMonitor.setRunType(DataEngineConsts.DataEngineRunType.BATCH_CHECK);
                    parallelMonitor.setStartProgress(formStartProgress, formEndProgress);
                    BatchParallelExeTask batchTask = new BatchParallelExeTask();
                    batchTask.setKey(Guid.newGuid());
                    batchTask.setDimensionValues(this.dimensionCollectionUtil.getDimensionValues(fmlDimCollection));
                    batchTask.setMainDimName(dwEntity.getDimensionName());
                    ParallelFormulaExecuteInfo parallelFormulaExecuteInfo = new ParallelFormulaExecuteInfo();
                    parallelFormulaExecuteInfo.setFormSchemeKey(formSchemeKey);
                    parallelFormulaExecuteInfo.setFormulaSchemeKey(formulaSchemeKey);
                    parallelFormulaExecuteInfo.setForms(execForms);
                    parallelFormulaExecuteInfo.setFormulaMaps(formulas);
                    parallelFormulaExecuteInfo.setFormulaCheckTypes(checkParam.getFormulaCheckType());
                    parallelFormulaExecuteInfo.setContainBetween(containBetweenForm);
                    parallelFormulaExecuteInfo.setMode(mode.getKey());
                    parallelFormulaExecuteInfo.setActionId(actionId);
                    parallelFormulaExecuteInfo.setExecuteId(executeId);
                    parallelFormulaExecuteInfo.setCheckMax(checkParam.getCheckMax());
                    parallelFormulaExecuteInfo.setActionName(action.getName());
                    parallelFormulaExecuteInfo.setCheckRecord(record);
                    parallelFormulaExecuteInfo.setActionTime(actionTime);
                    parallelFormulaExecuteInfo.setFmlJIT(checkParam.isFmlJIT());
                    parallelFormulaExecuteInfo.setCheckDes(checkParam.isCheckDes());
                    parallelFormulaExecuteInfo.setAllowableError(this.systemOptionUtil.getAllowableError(formScheme.getTaskKey()));
                    batchTask.setType("parallel.check.new");
                    batchTask.setTaskInfo((Serializable)parallelFormulaExecuteInfo);
                    String taskMsg = "\u5ba1\u6838\u6267\u884c\u5206\u53d1\u4efb\u52a1\u6620\u5c04\u5173\u7cfb\uff1a" + actionId + ":" + (monitorEnable ? fmlMonitor.getTaskId() : "null") + ":" + batchTask.getKey();
                    logger.debug(taskMsg);
                    try {
                        this.parallelRunner.run(batchTask, (IMonitor)parallelMonitor);
                        if (!parallelMonitor.isCancel()) continue;
                        if (monitorEnable) {
                            fmlMonitor.canceled(null, null);
                        }
                        String string4 = actionId;
                        return string4;
                    }
                    catch (Exception e) {
                        logger.error("\u5ba1\u6838\u5e76\u53d1\u6267\u884c\u5f02\u5e38:" + e.getMessage(), e);
                        if (!monitorEnable) continue;
                        fmlMonitor.error("check_execute_exception", e);
                    }
                }
            }
            if (monitorEnable) {
                if (ActionEnum.BATCH_CHECK == action) {
                    CheckResultQueryParam checkQuery = new CheckResultQueryParam();
                    checkQuery.setBatchId(actionId);
                    checkQuery.setDimensionCollection(checkParam.getDimensionCollection());
                    checkQuery.setFormulaSchemeKeys(Collections.singletonList(checkParam.getFormulaSchemeKey()));
                    boolean existError = this.checkResultService.existError(checkQuery);
                    if (existError) {
                        fmlMonitor.error("check_warn_info", null, null);
                    } else {
                        fmlMonitor.finish("check_success_info", null);
                    }
                } else {
                    fmlMonitor.finish("check_success_info", null);
                }
            }
            String string5 = actionId;
            return string5;
        }
        finally {
            this.record(action, actionTime, actionId, formSchemeKey);
            this.checkHelper.afterCheck(checkEvent);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CheckExeResult execute(CheckParam checkParam, CheckExeParam checkExeParam) {
        String actionId;
        ActionEnum action = checkExeParam.getAction();
        String executeId = checkExeParam.getExecuteId();
        IProviderStore providerStore = checkExeParam.getProviderStore();
        ICheckOptionProvider optionProvider = checkExeParam.getOptionProvider();
        ICheckMonitor monitor = checkExeParam.getMonitor();
        long actionTime = System.currentTimeMillis();
        String customActionId = checkParam.getActionId();
        String string = actionId = StringUtils.isNotEmpty((String)customActionId) ? customActionId : monitor.getTaskId();
        if (StringUtils.isEmpty((String)actionId)) {
            actionId = UUID.randomUUID().toString();
        }
        monitor.progressAndMessage(0.07, "get_param");
        String formulaSchemeKey = checkParam.getFormulaSchemeKey();
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKey(formulaSchemeKey);
        String formSchemeKey = formScheme.getKey();
        DimensionCollection dimensionCollection = checkParam.getDimensionCollection();
        CheckEvent checkEvent = this.prepCheckEvent(formScheme, action, actionId, checkParam);
        try {
            Object fmlDimForms;
            List<String> formKeys;
            monitor.executeBefore(checkEvent);
            this.checkHelper.cleanCache(action, dimensionCollection, formulaSchemeKey);
            LogDimensionCollection logDimension = this.logHelper.getLogDimension(formScheme, dimensionCollection);
            if (logDimension == null) {
                logger.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
                monitor.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc(), null);
                CheckExeResult checkExeResult = new CheckExeResult(actionId);
                return checkExeResult;
            }
            boolean record = this.checkHelper.recordOpen();
            IEntityDefine dwEntity = this.entityMetaService.queryEntity(formScheme.getDw());
            assert (dwEntity != null) : "\u4e3b\u7ef4\u5ea6\u672a\u627e\u5230";
            Mode mode = checkParam.getMode();
            List<String> rangeKeys = checkParam.getRangeKeys();
            Map<String, List<String>> formulas = this.formulaParseUtil.getFormulas(mode, rangeKeys, formulaSchemeKey, DataEngineConsts.FormulaType.CHECK);
            if (Mode.FORMULA == mode) {
                List<FormulaDefine> formulaDefines = this.formulaParseUtil.getFormulaDefines(formulaSchemeKey, rangeKeys);
                formKeys = formulaDefines.stream().map(FormulaDefine::getFormKey).distinct().collect(Collectors.toList());
            } else {
                formKeys = new ArrayList<String>(rangeKeys);
            }
            boolean selectBJ = formKeys.contains("00000000-0000-0000-0000-000000000000") || formKeys.contains(null);
            boolean addBJ = CollectionUtils.isEmpty(rangeKeys);
            boolean containBetweenForm = addBJ || selectBJ;
            formKeys.remove("00000000-0000-0000-0000-000000000000");
            formKeys.remove(null);
            String formulaSchemeInfo = this.logHelper.getFormulaSchemeInfo(Collections.singletonList(formulaSchemeKey));
            if (monitor.isCancel()) {
                monitor.canceled(null, null);
                this.logHelper.checkInfo(formScheme.getTaskKey(), logDimension, action.getTitle() + "\u6267\u884c\u53d6\u6d88", "\u5ba1\u6838\u6267\u884c\u5224\u65ad\u6743\u9650\u524d\u88ab\u53d6\u6d88\u2014\u2014" + formulaSchemeInfo);
                CheckExeResult checkExeResult = new CheckExeResult(actionId);
                return checkExeResult;
            }
            List<UnitPermission> accessForms = this.getAccessFormInfos(formKeys, checkParam, formScheme, providerStore, dwEntity);
            double formulaStartProgress = 0.3;
            double formulaEndProgress = 1.0;
            CheckData data = new CheckData(executeId);
            CheckDataCollector.getInstance().put(executeId, data);
            if (CollectionUtils.isEmpty(accessForms) && !selectBJ) {
                String formInfo = this.logHelper.getFormInfo(formKeys);
                this.logHelper.checkInfo(formScheme.getTaskKey(), logDimension, action.getTitle() + "\u65e0\u5ba1\u6838\u6743\u9650", "\u5bf9\u5355\u4f4d\u3001\u62a5\u8868\u65e0\u5ba1\u6838\u6743\u9650\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formInfo);
            } else {
                if (!CollectionUtils.isEmpty(accessForms)) {
                    fmlDimForms = this.paramUtil.getFmlDimForms2(formScheme, accessForms, DataEngineConsts.FormulaType.CHECK);
                    if (addBJ) {
                        this.paramUtil.addBJFormAccess((List<FmlDimForm>)fmlDimForms, formScheme.getKey(), dwEntity.getDimensionName());
                    }
                } else {
                    fmlDimForms = new ArrayList();
                }
                if (selectBJ) {
                    this.paramUtil.addBJAllPermission(formScheme, dimensionCollection, dwEntity.getDimensionName(), (List<FmlDimForm>)fmlDimForms);
                }
                for (int i = 0; i < fmlDimForms.size(); ++i) {
                    FmlDimForm fmlDimForm = (FmlDimForm)fmlDimForms.get(i);
                    DimensionCollection fmlDimCollection = fmlDimForm.getDimensionCollection();
                    if (!containBetweenForm && this.autoCheckBJ) {
                        this.checkHelper.cleanCKR(action, formSchemeKey, actionId, Collections.singletonList("00000000-0000-0000-0000-000000000000"), this.dimensionCollectionUtil.getDimensionValues(fmlDimCollection));
                    }
                    ArrayList<String> execForms = new ArrayList<String>(fmlDimForm.getFormKeys());
                    double formStartProgress = formulaStartProgress + (double)i * 1.0 / (double)fmlDimForms.size() * (formulaEndProgress - formulaStartProgress);
                    double formEndProgress = formulaStartProgress + (double)(i + 1) * 1.0 / (double)fmlDimForms.size() * (formulaEndProgress - formulaStartProgress);
                    if (monitor.isCancel()) {
                        monitor.canceled(null, null);
                        this.logHelper.checkInfo(formScheme.getTaskKey(), logDimension, action.getTitle() + "\u6267\u884c\u53d6\u6d88", "\u5ba1\u6838\u6267\u884c\u88ab\u53d6\u6d88\u2014\u2014" + formulaSchemeInfo);
                        CheckExeResult checkExeResult = new CheckExeResult(actionId);
                        return checkExeResult;
                    }
                    ParallelMonitor parallelMonitor = new ParallelMonitor(monitor);
                    parallelMonitor.setRunType(DataEngineConsts.DataEngineRunType.BATCH_CHECK);
                    parallelMonitor.setStartProgress(formStartProgress, formEndProgress);
                    BatchParallelExeTask batchTask = new BatchParallelExeTask();
                    batchTask.setKey(Guid.newGuid());
                    batchTask.setDimensionValues(this.dimensionCollectionUtil.getDimensionValues(fmlDimCollection));
                    batchTask.setMainDimName(dwEntity.getDimensionName());
                    ParallelFormulaExecuteInfo parallelFormulaExecuteInfo = new ParallelFormulaExecuteInfo();
                    parallelFormulaExecuteInfo.setFormSchemeKey(formSchemeKey);
                    parallelFormulaExecuteInfo.setFormulaSchemeKey(formulaSchemeKey);
                    parallelFormulaExecuteInfo.setForms(execForms);
                    parallelFormulaExecuteInfo.setFormulaMaps(formulas);
                    parallelFormulaExecuteInfo.setFormulaCheckTypes(checkParam.getFormulaCheckType());
                    parallelFormulaExecuteInfo.setContainBetween(containBetweenForm);
                    parallelFormulaExecuteInfo.setMode(mode.getKey());
                    parallelFormulaExecuteInfo.setActionId(actionId);
                    parallelFormulaExecuteInfo.setExecuteId(executeId);
                    parallelFormulaExecuteInfo.setActionName(action.getName());
                    parallelFormulaExecuteInfo.setCheckRecord(record);
                    parallelFormulaExecuteInfo.setActionTime(actionTime);
                    parallelFormulaExecuteInfo.setFmlJIT(checkParam.isFmlJIT());
                    parallelFormulaExecuteInfo.setCheckDes(checkParam.isCheckDes());
                    parallelFormulaExecuteInfo.setAllowableError(this.systemOptionUtil.getAllowableError(formScheme.getTaskKey()));
                    parallelFormulaExecuteInfo.setBatchSplitCount(optionProvider.getBatchSplitCount());
                    parallelFormulaExecuteInfo.setCheckMax(optionProvider.getCheckMax());
                    batchTask.setType("parallel.check.new");
                    batchTask.setTaskInfo((Serializable)parallelFormulaExecuteInfo);
                    String taskMsg = "\u5ba1\u6838\u6267\u884c\u5206\u53d1\u4efb\u52a1\u6620\u5c04\u5173\u7cfb\uff1a" + actionId + ":" + monitor.getTaskId() + ":" + batchTask.getKey();
                    logger.debug(taskMsg);
                    try {
                        this.parallelRunner.run(batchTask, (IMonitor)parallelMonitor);
                        continue;
                    }
                    catch (Exception e) {
                        logger.error("\u5ba1\u6838\u5e76\u53d1\u6267\u884c\u5f02\u5e38:" + e.getMessage(), e);
                        monitor.error("check_execute_exception", e);
                    }
                }
            }
            if (monitor.isCancel()) {
                monitor.canceled(null, null);
                this.logHelper.checkInfo(formScheme.getTaskKey(), logDimension, action.getTitle() + "\u6267\u884c\u53d6\u6d88", "\u5ba1\u6838\u6267\u884c\u4e3b\u4efb\u52a1\u53d6\u6d88\u5b8c\u6210\u2014\u2014" + formulaSchemeInfo);
                fmlDimForms = new CheckExeResult(actionId);
                return fmlDimForms;
            }
            if (ActionEnum.BATCH_CHECK == action) {
                CheckResultQueryParam checkQuery = new CheckResultQueryParam();
                checkQuery.setBatchId(actionId);
                checkQuery.setDimensionCollection(checkParam.getDimensionCollection());
                checkQuery.setFormulaSchemeKeys(Collections.singletonList(checkParam.getFormulaSchemeKey()));
                boolean existError = this.checkResultService.existError(checkQuery);
                if (existError) {
                    monitor.error("check_warn_info", null, null);
                } else {
                    monitor.finish("check_success_info", null);
                }
            } else {
                monitor.finish("check_success_info", null);
            }
            CheckExeResult checkExeResult = new CheckExeResult(actionId);
            return checkExeResult;
        }
        finally {
            this.record(action, actionTime, actionId, formSchemeKey);
            monitor.executeAfter(checkEvent);
        }
    }

    private CheckEvent prepCheckEvent(FormSchemeDefine formScheme, ActionEnum action, String actionId, CheckParam checkParam) {
        CheckEvent checkEvent = new CheckEvent();
        checkEvent.setFormSchemeKey(formScheme.getKey());
        checkEvent.setActionType(action);
        checkEvent.setActionId(actionId);
        checkEvent.setCheckParam(checkParam);
        if (ActionEnum.ALL_CHECK == action) {
            checkEvent.setCheckResultTableName(this.splitCheckTableHelper.getSplitAllCKRTableName(formScheme));
        } else if (ActionEnum.BATCH_CHECK == action) {
            checkEvent.setCheckResultTableName(this.splitCheckTableHelper.getSplitCKRTableName(formScheme));
        }
        return checkEvent;
    }

    private void record(ActionEnum action, long actionTime, String actionId, String formSchemeKey) {
        if (action == ActionEnum.BATCH_CHECK) {
            long finishTime = System.currentTimeMillis();
            CKRRec ckrRec = new CKRRec();
            ckrRec.setKey(UUID.randomUUID().toString());
            ckrRec.setBatchId(actionId);
            ckrRec.setStartTime(actionTime);
            ckrRec.setFinishTime(finishTime);
            ckrRec.setFormSchemeKey(formSchemeKey);
            ckrRec.setUserID(NpContextHolder.getContext().getUserId());
            try {
                this.ckrRecService.insert(ckrRec);
            }
            catch (Exception e) {
                logger.error(ckrRec + "-" + e.getMessage(), e);
            }
        }
    }

    private List<UnitPermission> getAccessFormInfos(List<String> formKeys, BaseEnv baseEnv, FormSchemeDefine formSchemeDefine, IProviderStore providerStore, IEntityDefine dwEntity) {
        if (CollectionUtils.isEmpty(formKeys) && Mode.FORMULA == baseEnv.getMode()) {
            return new ArrayList<UnitPermission>();
        }
        EvaluatorParam evaluatorParam = new EvaluatorParam(formSchemeDefine.getTaskKey(), formSchemeDefine.getKey(), ResouceType.FORM.getCode());
        if (CollectionUtils.isEmpty(formKeys)) {
            formKeys = this.runTimeViewController.queryAllFormKeysByFormScheme(formSchemeDefine.getKey());
        }
        DataPermissionEvaluator evaluator = providerStore.getDataPermissionEvaluatorFactory().createEvaluator(evaluatorParam, baseEnv.getDimensionCollection(), (Collection)formKeys);
        MergeDataPermission dataPermission = evaluator.mergeAccess(baseEnv.getDimensionCollection(), (Collection)formKeys, AuthType.READABLE);
        return dataPermission.getAccessResources();
    }
}

