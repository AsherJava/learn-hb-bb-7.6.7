/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.logic.common.ExceptionEnum;
import com.jiuqi.nr.data.logic.facade.extend.ICheckDesValidatorProvider;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.facade.service.ICheckService;
import com.jiuqi.nr.data.logic.internal.async.AllCheckAsyncTaskExecutor;
import com.jiuqi.nr.data.logic.internal.async.BatchCheckAsyncTaskExecutor;
import com.jiuqi.nr.data.logic.internal.entity.FmlCheckResultEntity;
import com.jiuqi.nr.data.logic.internal.helper.CKDValCollectorCache;
import com.jiuqi.nr.data.logic.internal.helper.CheckDataCollector;
import com.jiuqi.nr.data.logic.internal.obj.CKRPackageInfo;
import com.jiuqi.nr.data.logic.internal.obj.CheckData;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.service.ICheckExecuteService;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.IDimDataLoader;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Deprecated
public class CheckServiceImpl
implements ICheckService {
    private static final Logger logger = LoggerFactory.getLogger(CheckServiceImpl.class);
    @Autowired
    private FormulaParseUtil formulaParseUtil;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private ICheckExecuteService checkExecuteService;
    @Autowired
    private ParamUtil paramUtil;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private ICheckErrorDescriptionService checkErrorDescriptionService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private List<ICheckDesValidatorProvider> checkDesValidatorProviders;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CheckResult check(CheckParam checkParam) {
        DimensionValueSet dimensionValueSet = this.dimensionCollectionUtil.getMergeDimensionValueSet(checkParam.getDimensionCollection());
        if (dimensionValueSet == null) {
            logger.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
            return null;
        }
        String executeId = UUID.randomUUID().toString();
        try {
            this.checkExecuteService.execute(checkParam, null, ActionEnum.CHECK, executeId);
            String formulaSchemeKey = checkParam.getFormulaSchemeKey();
            FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKey(formulaSchemeKey);
            CheckData checkData = CheckDataCollector.getInstance().get(executeId);
            List<FmlCheckResultEntity> checkResultEntities = new ArrayList<FmlCheckResultEntity>();
            if (null != checkData) {
                checkResultEntities = new ArrayList<FmlCheckResultEntity>(checkData.getCheckResultEntities());
            }
            checkResultEntities = CheckResultUtil.orderCheckResult(checkResultEntities);
            QueryContext queryContext = null;
            ExecutorContext executorContext = this.formulaParseUtil.getExecutorContext(checkParam, dimensionValueSet);
            try {
                queryContext = new QueryContext(executorContext, null);
            }
            catch (ParseException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            FormulaShowInfo formulaShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ);
            CheckDesQueryParam checkDesQueryParam = new CheckDesQueryParam();
            ArrayList<String> formulaSchemeKeys = new ArrayList<String>();
            formulaSchemeKeys.add(formulaSchemeKey);
            checkDesQueryParam.setFormulaSchemeKey(formulaSchemeKeys);
            checkDesQueryParam.setFormKey(checkParam.getRangeKeys());
            checkDesQueryParam.setDimensionCollection(checkParam.getDimensionCollection());
            List<CheckDesObj> checkDesObjs = this.checkErrorDescriptionService.queryFormulaCheckDes(checkDesQueryParam);
            List<EntityData> dwDimEntities = this.entityUtil.getDwDimEntities(formScheme);
            Map<String, IDimDataLoader> entityLoaderMap = this.entityUtil.getDimDataLoaders(formScheme, dimensionValueSet, dwDimEntities);
            HashMap<String, String> colDimNameMap = new HashMap<String, String>();
            colDimNameMap.put("MDCODE", this.entityUtil.getEntity(formScheme.getDw()).getDimensionName());
            colDimNameMap.put("PERIOD", this.entityUtil.getPeriodEntity(formScheme.getDateTime()).getDimensionName());
            CKDValCollectorCache ckdValCollectorCache = new CKDValCollectorCache(this.checkDesValidatorProviders);
            CKRPackageInfo ckrPackageInfo = new CKRPackageInfo(queryContext, formulaShowInfo, entityLoaderMap, colDimNameMap, Collections.emptyMap(), null, formScheme.getKey(), ckdValCollectorCache);
            CheckResult checkResult = CheckResultUtil.packageCheckResult(checkResultEntities, checkDesObjs, ckrPackageInfo);
            return checkResult;
        }
        finally {
            CheckDataCollector.getInstance().remove(executeId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String allCheck(CheckParam checkParam, IFmlMonitor fmlMonitor) {
        String executeId = UUID.randomUUID().toString();
        try {
            String string = this.checkExecuteService.execute(checkParam, fmlMonitor, ActionEnum.ALL_CHECK, executeId);
            return string;
        }
        finally {
            CheckDataCollector.getInstance().remove(executeId);
        }
    }

    @Override
    public String allCheckAsync(CheckParam checkParam) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = this.paramUtil.getNpRealTimeTaskInfo(checkParam, (AbstractRealTimeJob)new AllCheckAsyncTaskExecutor());
        return this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNC_TASK_ALL_CHECK");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String batchCheck(CheckParam checkParam, IFmlMonitor fmlMonitor) {
        String executeId = UUID.randomUUID().toString();
        try {
            String string = this.checkExecuteService.execute(checkParam, fmlMonitor, ActionEnum.BATCH_CHECK, executeId);
            return string;
        }
        finally {
            CheckDataCollector.getInstance().remove(executeId);
        }
    }

    @Override
    public String batchCheckAsync(CheckParam checkParam) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = this.paramUtil.getNpRealTimeTaskInfo(checkParam, (AbstractRealTimeJob)new BatchCheckAsyncTaskExecutor());
        return this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNC_TASK_BATCH_CHECK");
    }
}

