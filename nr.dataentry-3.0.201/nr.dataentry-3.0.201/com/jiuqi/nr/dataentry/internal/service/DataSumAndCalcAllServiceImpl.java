/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo;
import com.jiuqi.nr.dataentry.paramInfo.DataSumAndCalcAllInfo;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataentry.service.IBatchDataSumService;
import com.jiuqi.nr.dataentry.service.IDataSumAndCalcAllService;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSumAndCalcAllServiceImpl
implements IDataSumAndCalcAllService {
    private static final Logger logger = LoggerFactory.getLogger(DataSumAndCalcAllServiceImpl.class);
    @Autowired
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private IBatchDataSumService batchDataSumService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Resource
    private IJtableEntityService jtableEntityService;
    @Resource
    private IJtableParamService jtableParamService;
    @Resource
    private IFormulaRunTimeController formulaRunTimeController;

    @Override
    public void dataSumAndCalcAllForm(DataSumAndCalcAllInfo dataSumAndCalcAllInfo, AsyncTaskMonitor asyncTaskMonitor) {
        BatchDataSumInfo batchDataSumInfo = dataSumAndCalcAllInfo.getBatchDataSumInfo();
        BatchCalculateInfo batchCalculateInfo = dataSumAndCalcAllInfo.getBatchCalculateInfo();
        String formulaSchemeName = dataSumAndCalcAllInfo.getFormulaSchemeName();
        String formulaSchemeKey = batchCalculateInfo.getFormulaSchemeKey();
        if (formulaSchemeName != "" && formulaSchemeName != null) {
            formulaSchemeName = formulaSchemeName.trim();
            List formulaSchemes = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(batchCalculateInfo.getFormSchemeKey());
            for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemes) {
                if (!formulaSchemeDefine.getTitle().equals(formulaSchemeName)) continue;
                formulaSchemeKey = formulaSchemeDefine.getKey();
                break;
            }
            batchCalculateInfo.setFormulaSchemeKey(formulaSchemeKey);
        }
        JtableContext context = batchDataSumInfo.getContext();
        JtableContext jtableContext = new JtableContext(context);
        List entityList = this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
        EntityViewData unitEntity = null;
        for (EntityViewData entity : entityList) {
            if (!entity.isMasterEntity()) continue;
            unitEntity = entity;
        }
        DimensionValue dimensionValue = (DimensionValue)jtableContext.getDimensionSet().get(unitEntity.getDimensionName());
        EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
        entityQueryInfo.setEntityViewKey(unitEntity.getKey());
        entityQueryInfo.setParentKey(dimensionValue.getValue());
        entityQueryInfo.setAllChildren(true);
        entityQueryInfo.setContext(jtableContext);
        EntityReturnInfo entityData = this.jtableEntityService.queryEntityData(entityQueryInfo);
        List allEntityKey = DimensionValueSetUtil.getAllEntityKey((EntityReturnInfo)entityData, (boolean)true);
        batchDataSumInfo.setSourceKeys(allEntityKey);
        this.batchDataSumService.batchDataSumForm(batchDataSumInfo, asyncTaskMonitor, 1.0f);
        TaskState dataSumState = this.asyncTaskManager.queryTaskState(asyncTaskMonitor.getTaskId());
        if (dataSumState.equals((Object)TaskState.ERROR)) {
            String dataSumResult = this.asyncTaskManager.queryResult(asyncTaskMonitor.getTaskId());
            asyncTaskMonitor.error(dataSumResult, null, "");
            return;
        }
        this.batchCalculateService.batchCalculateForm(batchCalculateInfo, asyncTaskMonitor);
        TaskState calcAllState = this.asyncTaskManager.queryTaskState(asyncTaskMonitor.getTaskId());
        if (calcAllState.equals((Object)TaskState.ERROR)) {
            String calcAllResult = this.asyncTaskManager.queryResult(asyncTaskMonitor.getTaskId());
            asyncTaskMonitor.error(calcAllResult, null, "");
            return;
        }
        String summaryCalculate = "summary_calculate_success_info";
        if (!asyncTaskMonitor.isFinish()) {
            asyncTaskMonitor.finish(summaryCalculate, (Object)"");
        }
    }
}

