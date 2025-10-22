/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.parallel.BatchParallelExeTask
 *  com.jiuqi.nr.parallel.IParallelRunner
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.bi.util.Guid;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.logic.common.ExceptionEnum;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CalculateParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.internal.helper.CalculateHelper;
import com.jiuqi.nr.data.logic.internal.log.LogHelper;
import com.jiuqi.nr.data.logic.internal.obj.FmlDimForm;
import com.jiuqi.nr.data.logic.internal.obj.ParallelFormulaExecuteInfo;
import com.jiuqi.nr.data.logic.internal.service.ICalculateExecuteService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.data.logic.internal.util.SystemOptionUtil;
import com.jiuqi.nr.data.logic.monitor.ParallelMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.IParallelRunner;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class CalculateExecuteServiceImpl
implements ICalculateExecuteService {
    private static final Logger logger = LoggerFactory.getLogger(CalculateExecuteServiceImpl.class);
    @Autowired
    private ParamUtil paramUtil;
    @Autowired
    private SystemOptionUtil systemOptionUtil;
    @Autowired
    private IParallelRunner parallelRunner;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private FormulaParseUtil formulaParseUtil;
    @Autowired
    private CalculateHelper calculateHelper;
    @Autowired
    private LogHelper logHelper;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;

    @Override
    public void execute(CalculateParam calculateParam, IFmlMonitor fmlMonitor) {
        boolean monitorEnable;
        boolean bl = monitorEnable = fmlMonitor != null;
        if (monitorEnable) {
            fmlMonitor.progressAndMessage(0.07, "calculate_start");
        }
        String formulaSchemeKey = calculateParam.getFormulaSchemeKey();
        DimensionCollection dimensionCollection = calculateParam.getDimensionCollection();
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKey(formulaSchemeKey);
        LogDimensionCollection logDimension = this.logHelper.getLogDimension(formScheme, dimensionCollection);
        if (logDimension == null) {
            logger.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
            if (monitorEnable) {
                fmlMonitor.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc(), null);
            }
            return;
        }
        Mode mode = calculateParam.getMode();
        List<String> rangeKeys = calculateParam.getRangeKeys();
        Map<String, List<String>> formulas = this.formulaParseUtil.getFormulas(mode, rangeKeys, formulaSchemeKey, DataEngineConsts.FormulaType.CALCULATE);
        ArrayList<String> formKeys = new ArrayList<String>(formulas.keySet());
        boolean selectBJ = false;
        boolean addBJ = false;
        if (CollectionUtils.isEmpty(rangeKeys)) {
            addBJ = true;
        } else {
            selectBJ = formKeys.contains("00000000-0000-0000-0000-000000000000") || formKeys.contains(null);
        }
        boolean containBetweenForm = addBJ || selectBJ;
        String formSchemeKey = formScheme.getKey();
        IEntityDefine dwEntity = this.entityMetaService.queryEntity(formScheme.getDw());
        if (monitorEnable) {
            fmlMonitor.progressAndMessage(0.1, "calculate_auth_fliter");
        }
        formKeys.remove("00000000-0000-0000-0000-000000000000");
        formKeys.remove(null);
        String formulaSchemeInfo = this.logHelper.getFormulaSchemeInfo(Collections.singletonList(formulaSchemeKey));
        if (monitorEnable && fmlMonitor.isCancel()) {
            fmlMonitor.canceled(null, null);
            this.logHelper.calInfo(formScheme.getTaskKey(), logDimension, "\u8fd0\u7b97\u6267\u884c\u53d6\u6d88", "\u8fd0\u7b97\u6267\u884c\u5224\u65ad\u6743\u9650\u524d\u88ab\u53d6\u6d88\u2014\u2014" + formulaSchemeInfo);
            return;
        }
        List<DimensionAccessFormInfo.AccessFormInfo> accessForms = this.paramUtil.getAccessFormInfos(formKeys, calculateParam, formScheme, AccessLevel.FormAccessLevel.FORM_DATA_SYSTEM_WRITE);
        int calCount = this.systemOptionUtil.getCalculateCount(formulaSchemeKey);
        double formulaStartProgress = 0.3;
        double formulaEndProgress = 1.0;
        HashSet<String> calFormSet = new HashSet<String>();
        if (CollectionUtils.isEmpty(accessForms) && !selectBJ) {
            String formInfo = this.logHelper.getFormInfo(formKeys);
            this.logHelper.calInfo(formScheme.getTaskKey(), logDimension, "\u65e0\u8fd0\u7b97\u6743\u9650", "\u5bf9\u5355\u4f4d\u3001\u62a5\u8868\u65e0\u8fd0\u7b97\u6743\u9650\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formInfo);
        } else {
            List<Object> fmlDimForms;
            if (!CollectionUtils.isEmpty(accessForms)) {
                fmlDimForms = this.paramUtil.getFmlDimForms(formScheme, accessForms, DataEngineConsts.FormulaType.CALCULATE);
                if (addBJ) {
                    this.paramUtil.addBJFormAccess(fmlDimForms, formScheme.getKey(), dwEntity.getDimensionName());
                }
            } else {
                fmlDimForms = new ArrayList();
            }
            if (selectBJ) {
                this.paramUtil.addBJAllPermission(formScheme, dimensionCollection, dwEntity.getDimensionName(), fmlDimForms);
            }
            for (int i = 0; i < fmlDimForms.size(); ++i) {
                double formStartProgress = formulaStartProgress + (double)i / (double)fmlDimForms.size() * (formulaEndProgress - formulaStartProgress);
                double formEndProgress = formulaStartProgress + (double)(i + 1) / (double)fmlDimForms.size() * (formulaEndProgress - formulaStartProgress);
                FmlDimForm fmlDimForm = (FmlDimForm)fmlDimForms.get(i);
                DimensionCollection fmlDimCollection = fmlDimForm.getDimensionCollection();
                LogDimensionCollection logDimension1 = this.logHelper.getLogDimension(formScheme, fmlDimCollection);
                if (logDimension1 == null) {
                    logger.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
                    if (!monitorEnable) continue;
                    fmlMonitor.progressAndMessage(formEndProgress, "calculateing");
                    continue;
                }
                ArrayList<String> execForms = new ArrayList<String>(fmlDimForm.getFormKeys());
                calFormSet.addAll(execForms);
                for (int j = 0; j < calCount; ++j) {
                    String formOrFmlInfo;
                    if (monitorEnable && fmlMonitor.isCancel()) {
                        return;
                    }
                    ParallelMonitor parallelMonitor = new ParallelMonitor(fmlMonitor);
                    parallelMonitor.setRunType(DataEngineConsts.DataEngineRunType.BATCH_CALCULATE);
                    double runStartProgress = formStartProgress + (double)j / (double)calCount * (formEndProgress - formStartProgress);
                    double runEndProgress = formStartProgress + (double)(j + 1) / (double)calCount * (formEndProgress - formStartProgress);
                    parallelMonitor.setStartProgress(runStartProgress, runEndProgress);
                    BatchParallelExeTask batchTask = new BatchParallelExeTask();
                    batchTask.setKey(Guid.newGuid());
                    batchTask.setDimensionValues(this.dimensionCollectionUtil.getDimensionValues(fmlDimCollection));
                    if (null != dwEntity) {
                        batchTask.setMainDimName(dwEntity.getDimensionName());
                    }
                    ParallelFormulaExecuteInfo parallelFormulaExecuteInfo = new ParallelFormulaExecuteInfo();
                    parallelFormulaExecuteInfo.setFormSchemeKey(formSchemeKey);
                    parallelFormulaExecuteInfo.setFormulaSchemeKey(formulaSchemeKey);
                    parallelFormulaExecuteInfo.setForms(execForms);
                    parallelFormulaExecuteInfo.setFormulaMaps(formulas);
                    parallelFormulaExecuteInfo.setContainBetween(containBetweenForm);
                    parallelFormulaExecuteInfo.setMode(mode.getKey());
                    parallelFormulaExecuteInfo.setFmlJIT(calculateParam.isFmlJIT());
                    parallelFormulaExecuteInfo.setAccessIgnoreItems(calculateParam.getIgnoreItems());
                    batchTask.setType("parallel.calc.new");
                    batchTask.setTaskInfo((Serializable)parallelFormulaExecuteInfo);
                    if (monitorEnable) {
                        fmlMonitor.progressAndMessage(runStartProgress, "calculateing");
                    }
                    if (calculateParam.getMode() == Mode.FORM) {
                        formOrFmlInfo = this.logHelper.getFormInfo(execForms);
                    } else {
                        ArrayList<String> formulaKeys = new ArrayList<String>();
                        for (String execForm : execForms) {
                            List<String> strings = formulas.get(execForm);
                            if (strings == null) continue;
                            formulaKeys.addAll(strings);
                        }
                        formOrFmlInfo = this.logHelper.getFormulaInfo(formulaKeys);
                    }
                    String taskMsg = "\u8fd0\u7b97\u6267\u884c\u5206\u53d1\u4efb\u52a1\u6620\u5c04\u5173\u7cfb\uff1a" + (monitorEnable ? fmlMonitor.getTaskId() : "null") + ":" + batchTask.getKey();
                    logger.debug(taskMsg);
                    try {
                        if (parallelMonitor.isCancel()) {
                            if (monitorEnable) {
                                fmlMonitor.canceled(null, null);
                            }
                            this.logHelper.calInfo(formScheme.getTaskKey(), logDimension1, "\u8fd0\u7b97\u6267\u884c\u53d6\u6d88", "\u8c03\u7528\u5f15\u64ce\u6267\u884c\u8fd0\u7b97\u516c\u5f0f\u88ab\u53d6\u6d88\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formOrFmlInfo);
                            return;
                        }
                        this.parallelRunner.run(batchTask, (IMonitor)parallelMonitor);
                        this.logHelper.calInfo(formScheme.getTaskKey(), logDimension1, "\u8fd0\u7b97\u6267\u884c\u6210\u529f", "\u8c03\u7528\u5f15\u64ce\u6267\u884c\u8fd0\u7b97\u516c\u5f0f\u6210\u529f\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formOrFmlInfo);
                        continue;
                    }
                    catch (Exception e) {
                        logger.error("\u8fd0\u7b97\u5e76\u53d1\u6267\u884c\u5f02\u5e38:" + e.getMessage(), e);
                        this.logHelper.calError(formScheme.getTaskKey(), logDimension1, "\u8fd0\u7b97\u6267\u884c\u5931\u8d25", "\u8c03\u7528\u5f15\u64ce\u6267\u884c\u8fd0\u7b97\u516c\u5f0f\u51fa\u9519\u2014\u2014" + formulaSchemeInfo + "\u2014\u2014" + formOrFmlInfo);
                        if (!monitorEnable) continue;
                        fmlMonitor.error("calculate_execute_exception", e);
                    }
                }
            }
        }
        if (monitorEnable && fmlMonitor.isCancel()) {
            fmlMonitor.canceled(null, null);
            this.logHelper.calInfo(formScheme.getTaskKey(), logDimension, "\u8fd0\u7b97\u6267\u884c\u53d6\u6d88", "\u8fd0\u7b97\u6267\u884c\u88ab\u53d6\u6d88\u2014\u2014" + formulaSchemeInfo);
            return;
        }
        this.calculateHelper.publishCalInfo(dimensionCollection, formScheme, new ArrayList<String>(calFormSet));
        if (monitorEnable) {
            fmlMonitor.finish("calculate_success_info", null);
        }
    }
}

