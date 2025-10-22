/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.logic.internal.impl;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.logic.api.ICheckService;
import com.jiuqi.nr.data.logic.api.param.CheckExeResult;
import com.jiuqi.nr.data.logic.common.ExceptionEnum;
import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.internal.entity.FmlCheckResultEntity;
import com.jiuqi.nr.data.logic.internal.helper.CKDValCollectorCache;
import com.jiuqi.nr.data.logic.internal.helper.CheckDataCollector;
import com.jiuqi.nr.data.logic.internal.impl.DataLogicServiceFactory;
import com.jiuqi.nr.data.logic.internal.impl.DefCheckMonitor;
import com.jiuqi.nr.data.logic.internal.impl.EmptyFmlMonitor;
import com.jiuqi.nr.data.logic.internal.obj.CKRPackageInfo;
import com.jiuqi.nr.data.logic.internal.obj.CheckData;
import com.jiuqi.nr.data.logic.internal.obj.CheckExeParam;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.IDimDataLoader;
import com.jiuqi.nr.data.logic.spi.ICheckMonitor;
import com.jiuqi.nr.data.logic.spi.ICheckOptionProvider;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckServiceImpl
implements ICheckService {
    private static final Logger logger = LoggerFactory.getLogger(CheckServiceImpl.class);
    private final DataLogicServiceFactory serviceFactory;
    private final IProviderStore providerStore;
    private final ICheckOptionProvider optionProvider;

    public CheckServiceImpl(DataLogicServiceFactory serviceFactory, IProviderStore providerStore, ICheckOptionProvider optionProvider) {
        this.serviceFactory = serviceFactory;
        this.providerStore = providerStore;
        this.optionProvider = optionProvider;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CheckResult check(CheckParam checkParam) {
        DimensionValueSet dimensionValueSet = this.serviceFactory.getDimensionCollectionUtil().getMergeDimensionValueSet(checkParam.getDimensionCollection());
        if (dimensionValueSet == null) {
            logger.error(ExceptionEnum.DIM_EXPAND_EXC.getDesc());
            return null;
        }
        String executeId = UUID.randomUUID().toString();
        DefCheckMonitor defCheckMonitor = new DefCheckMonitor(EmptyFmlMonitor.getInstance(), this.serviceFactory.getFmlCheckListeners());
        try {
            CheckExeParam checkExeParam = new CheckExeParam(this.providerStore, this.optionProvider, defCheckMonitor, ActionEnum.CHECK, executeId);
            this.serviceFactory.getCheckExecuteService().execute(checkParam, checkExeParam);
            String formulaSchemeKey = checkParam.getFormulaSchemeKey();
            FormSchemeDefine formScheme = this.serviceFactory.getParamUtil().getFormSchemeByFormulaSchemeKey(formulaSchemeKey);
            CheckData checkData = CheckDataCollector.getInstance().get(executeId);
            List<FmlCheckResultEntity> checkResultEntities = new ArrayList<FmlCheckResultEntity>();
            if (null != checkData) {
                checkResultEntities = new ArrayList<FmlCheckResultEntity>(checkData.getCheckResultEntities());
            }
            checkResultEntities = CheckResultUtil.orderCheckResult(checkResultEntities);
            QueryContext queryContext = null;
            ExecutorContext executorContext = this.serviceFactory.getFormulaParseUtil().getExecutorContext(checkParam, dimensionValueSet);
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
            checkDesQueryParam.setRecordId(checkResultEntities.stream().map(FmlCheckResultEntity::getRecId).collect(Collectors.toList()));
            checkDesQueryParam.setDimensionCollection(checkParam.getDimensionCollection());
            List<CheckDesObj> checkDesObjs = this.serviceFactory.getCheckErrorDescriptionService().queryFormulaCheckDes(checkDesQueryParam);
            List<EntityData> dwDimEntities = this.serviceFactory.getEntityUtil().getDwDimEntities(formScheme);
            Map<String, IDimDataLoader> entityLoaderMap = this.serviceFactory.getEntityUtil().getDimDataLoaders(formScheme, dimensionValueSet, dwDimEntities);
            HashMap<String, String> colDimNameMap = new HashMap<String, String>();
            colDimNameMap.put("MDCODE", this.serviceFactory.getEntityUtil().getEntity(formScheme.getDw()).getDimensionName());
            colDimNameMap.put("PERIOD", this.serviceFactory.getEntityUtil().getPeriodEntity(formScheme.getDateTime()).getDimensionName());
            CKDValCollectorCache ckdValCollectorCache = new CKDValCollectorCache(this.serviceFactory.getCheckDesValidatorProviders());
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
    public CheckExeResult allCheck(CheckParam checkParam) {
        String executeId = UUID.randomUUID().toString();
        try {
            DefCheckMonitor defCheckMonitor = new DefCheckMonitor(EmptyFmlMonitor.getInstance(), this.serviceFactory.getFmlCheckListeners());
            CheckExeParam checkExeParam = new CheckExeParam(this.providerStore, this.optionProvider, defCheckMonitor, ActionEnum.ALL_CHECK, executeId);
            CheckExeResult checkExeResult = this.serviceFactory.getCheckExecuteService().execute(checkParam, checkExeParam);
            return checkExeResult;
        }
        finally {
            CheckDataCollector.getInstance().remove(executeId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CheckExeResult allCheck(CheckParam checkParam, ICheckMonitor checkMonitor) {
        String executeId = UUID.randomUUID().toString();
        try {
            CheckExeParam checkExeParam = new CheckExeParam(this.providerStore, this.optionProvider, checkMonitor, ActionEnum.ALL_CHECK, executeId);
            CheckExeResult checkExeResult = this.serviceFactory.getCheckExecuteService().execute(checkParam, checkExeParam);
            return checkExeResult;
        }
        finally {
            CheckDataCollector.getInstance().remove(executeId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CheckExeResult batchCheck(CheckParam checkParam) {
        String executeId = UUID.randomUUID().toString();
        try {
            DefCheckMonitor defCheckMonitor = new DefCheckMonitor(EmptyFmlMonitor.getInstance(), this.serviceFactory.getFmlCheckListeners());
            CheckExeParam checkExeParam = new CheckExeParam(this.providerStore, this.optionProvider, defCheckMonitor, ActionEnum.BATCH_CHECK, executeId);
            CheckExeResult checkExeResult = this.serviceFactory.getCheckExecuteService().execute(checkParam, checkExeParam);
            return checkExeResult;
        }
        finally {
            CheckDataCollector.getInstance().remove(executeId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CheckExeResult batchCheck(CheckParam checkParam, ICheckMonitor checkMonitor) {
        String executeId = UUID.randomUUID().toString();
        try {
            CheckExeParam checkExeParam = new CheckExeParam(this.providerStore, this.optionProvider, checkMonitor, ActionEnum.BATCH_CHECK, executeId);
            CheckExeResult checkExeResult = this.serviceFactory.getCheckExecuteService().execute(checkParam, checkExeParam);
            return checkExeResult;
        }
        finally {
            CheckDataCollector.getInstance().remove(executeId);
        }
    }
}

