/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.sql.loader.TableLoaderException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskLog
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.engine.exception.DataGatherExecption
 *  com.jiuqi.nr.data.engine.gather.GatherCondition
 *  com.jiuqi.nr.data.engine.gather.GatherDirection
 *  com.jiuqi.nr.data.engine.gather.GatherEntityFilterProvider
 *  com.jiuqi.nr.data.engine.gather.GatherEntityMap
 *  com.jiuqi.nr.data.engine.gather.GatherTableDefine
 *  com.jiuqi.nr.data.engine.gather.IDataGather
 *  com.jiuqi.nr.data.engine.gather.IDataGatherProvider
 *  com.jiuqi.nr.data.engine.gather.NotGatherEntityValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.gather.service.impl;

import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskLog;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.engine.exception.DataGatherExecption;
import com.jiuqi.nr.data.engine.gather.GatherCondition;
import com.jiuqi.nr.data.engine.gather.GatherDirection;
import com.jiuqi.nr.data.engine.gather.GatherEntityFilterProvider;
import com.jiuqi.nr.data.engine.gather.GatherEntityMap;
import com.jiuqi.nr.data.engine.gather.GatherTableDefine;
import com.jiuqi.nr.data.engine.gather.IDataGather;
import com.jiuqi.nr.data.engine.gather.IDataGatherProvider;
import com.jiuqi.nr.data.engine.gather.NotGatherEntityValue;
import com.jiuqi.nr.data.gather.bean.SelectDataGatherParam;
import com.jiuqi.nr.data.gather.bean.event.GatherCompleteEvent;
import com.jiuqi.nr.data.gather.bean.event.GatherCompleteSource;
import com.jiuqi.nr.data.gather.exception.DataGatherException;
import com.jiuqi.nr.data.gather.listener.DataGatherHandler;
import com.jiuqi.nr.data.gather.listener.DataGatherParallelMonitor;
import com.jiuqi.nr.data.gather.listener.DataGatherUnitEntityFilterProvider;
import com.jiuqi.nr.data.gather.service.IDataSelectGatherService;
import com.jiuqi.nr.data.gather.service.impl.ErrorItemListGatherService;
import com.jiuqi.nr.data.gather.util.GatherTableUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

public class SelectGatherServiceImpl
implements IDataSelectGatherService {
    private static final Logger logger = LoggerFactory.getLogger(SelectGatherServiceImpl.class);
    @Resource
    IRunTimeViewController runtimeView;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    IDataGatherProvider dataGatherProvider;
    @Autowired(required=false)
    private List<DataGatherHandler> dataGatherHandlerList;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired(required=false)
    private DataGatherUnitEntityFilterProvider dataGatherUnitEntityFilterProvider;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private ErrorItemListGatherService errorItemListGatherService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void selectNodeGather(SelectDataGatherParam selectDataGatherParam) {
        this.asyncDataSelectGather(selectDataGatherParam, null);
    }

    @Override
    public void asyncDataSelectGather(SelectDataGatherParam selectDataGatherParam, AsyncTaskMonitor asyncTaskMonitor) {
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("\u6c47\u603b\u670d\u52a1", OperLevel.USER_OPER);
        AsyncTaskLog asyncTaskLog = new AsyncTaskLog(Boolean.valueOf(false));
        if (asyncTaskMonitor != null) {
            asyncTaskMonitor.progressAndMessage(0.01, "summary_start");
        }
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(selectDataGatherParam.getFormSchemeKey());
        }
        catch (Exception e2) {
            throw new DataGatherException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{selectDataGatherParam.getFormSchemeKey()});
        }
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(selectDataGatherParam.getTaskKey());
        String dataSchemeKey = taskDefine.getDataScheme();
        String entityId = formScheme.getDw();
        DsContext dsContext = DsContextHolder.getDsContext();
        if (dsContext != null && StringUtils.isNotEmpty((String)dsContext.getContextEntityId())) {
            entityId = dsContext.getContextEntityId();
        }
        String entityDimension = this.entityMetaService.getDimensionName(entityId);
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimension = periodAdapter.getPeriodDimensionName();
        List<String> sourceKeys = selectDataGatherParam.getSourceKeys();
        DimensionCollection dimensionCollection = selectDataGatherParam.getDimensionCollection();
        DimensionValueSet dimensionSet = dimensionCollection.combineWithoutVarDim();
        String targetKey = String.valueOf(dimensionSet.getValue(entityDimension));
        String periodCode = String.valueOf(dimensionSet.getValue(periodDimension));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(entityId, new String[]{targetKey});
        logDimensionCollection.setPeriod(formScheme.getDateTime(), periodCode);
        logHelper.info(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u670d\u52a1\u5c42\u5f00\u59cb", "\u9009\u62e9\u6c47\u603b\u670d\u52a1\u5c42\u5f00\u59cb,\u6765\u6e90\u5355\u4f4d\uff1a" + sourceKeys.toString());
        List dimensionCombines = dimensionCollection.getDimensionCombinations();
        if (CollectionUtils.isEmpty(dimensionCombines)) {
            logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u51fa\u73b0\u9519\u8bef", "\u4e1a\u52a1\u9519\u8bef\uff1a\u53ef\u6c47\u603b\u7684\u5355\u4f4d\u4e0d\u5b58\u5728\uff01");
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.error("no_summary_unit", null);
            }
            logger.debug("\u6c92\u6709\u53ef\u6c47\u603b\u7684\u5355\u4f4d\uff01");
            return;
        }
        if (StringUtils.isEmpty((String)targetKey)) {
            asyncTaskLog.setMessage("\u76ee\u6807\u6c47\u603b\u8282\u70b9\u4e3a\u7a7a\u3002");
            String objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
            logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u83b7\u53d6\u76ee\u6807\u8282\u70b9", "\u4e1a\u52a1\u9519\u8bef\uff1a\u76ee\u6807\u6c47\u603b\u8282\u70b9\u4e3a\u7a7a");
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.error("target_summary_node_null", null, objectToJson);
            }
            return;
        }
        if (targetKey.contains(";")) {
            asyncTaskLog.setMessage("\u76ee\u6807\u6c47\u603b\u8282\u70b9\u4e3a\u591a\u4e2a\uff0c\u76ee\u524d\u53ea\u652f\u6301\u4e00\u4e2a\u9876\u7ea7\u8282\u70b9\u3002");
            String objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
            logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u83b7\u53d6\u76ee\u6807\u8282\u70b9", "\u4e1a\u52a1\u9519\u8bef\uff1a\u76ee\u6807\u6c47\u603b\u8282\u70b9\u4e3a\u591a\u4e2a\uff0c\u76ee\u524d\u53ea\u652f\u6301\u4e00\u4e2a\u9876\u7ea7\u8282\u70b9\u3002");
            if (asyncTaskMonitor != null) {
                asyncTaskMonitor.error("target_summary_node_more", null, objectToJson);
            }
            return;
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, selectDataGatherParam.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(selectDataGatherParam.getFormSchemeKey());
        IDataGather dataGather = this.dataGatherProvider.newDataGather(queryEnvironment);
        this.dataGatherUnitEntityFilterProvider.setGatherParam(selectDataGatherParam);
        this.dataGatherUnitEntityFilterProvider.setMainDimensionName(entityDimension);
        dataGather.setEntityFilterProvider((GatherEntityFilterProvider)this.dataGatherUnitEntityFilterProvider);
        if (asyncTaskMonitor != null) {
            asyncTaskMonitor.progressAndMessage(0.05, "summary_auth_fliter");
        }
        List accessFormInfos = null;
        IDataAccessFormService dataAccessFormService = this.dataAccessServiceProvider.getDataAccessFormService();
        List<Object> formKeys = new ArrayList();
        if (StringUtils.isNotEmpty((String)selectDataGatherParam.getFormKeys())) {
            formKeys = Stream.of(selectDataGatherParam.getFormKeys().split(";")).collect(Collectors.toList());
        }
        Set<String> ignoreItems = selectDataGatherParam.getIgnoreAccessItems();
        boolean ignoreWorkFlow = ignoreItems.contains("upload");
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(dimensionCollection);
        accessFormParam.setTaskKey(selectDataGatherParam.getTaskKey());
        accessFormParam.setIgnoreAccessItems(ignoreItems);
        accessFormParam.setFormSchemeKey(selectDataGatherParam.getFormSchemeKey());
        accessFormParam.setFormKeys(formKeys);
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_SYSTEM_WRITE);
        DimensionAccessFormInfo dimensionAccessInfos = dataAccessFormService.getBatchAccessForms(accessFormParam);
        accessFormInfos = dimensionAccessInfos.getAccessForms();
        EntityViewDefine entityViewDefine = entityId.equals(formScheme.getDw()) ? this.runtimeView.getViewByFormSchemeKey(formScheme.getKey()) : this.entityViewRunTimeController.buildEntityView(entityId, dsContext.getContextFilterExpression());
        GatherEntityMap gatherEntityMap = null;
        GatherCondition condition = new GatherCondition();
        condition.setSumAfterParentUpload(Boolean.valueOf(ignoreWorkFlow));
        condition.setFormSchemeKey(selectDataGatherParam.getFormSchemeKey());
        dataGather.setGatherCondition(condition);
        if (!CollectionUtils.isEmpty(this.dataGatherHandlerList)) {
            GatherEntityMap finalGatherEntityMap = gatherEntityMap;
            this.dataGatherHandlerList.stream().forEach(handler -> handler.executeBefore(targetKey, selectDataGatherParam, finalGatherEntityMap));
        }
        HashMap noAccessEntityForms = new HashMap();
        List dimensionSplit = dimensionCombines.stream().map(e -> e.toDimensionValueSet()).collect(Collectors.toList());
        if (asyncTaskMonitor != null) {
            asyncTaskMonitor.progressAndMessage(0.1, "summary_ing");
        }
        HashSet gatherformKey = new HashSet();
        for (int formInfoIndex = 0; formInfoIndex < accessFormInfos.size(); ++formInfoIndex) {
            if (asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
                return;
            }
            DimensionAccessFormInfo.AccessFormInfo dimensionValueFormInfo = (DimensionAccessFormInfo.AccessFormInfo)accessFormInfos.get(formInfoIndex);
            Map dimensionValue = dimensionValueFormInfo.getDimensions();
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionValue);
            List accessFormKeys = dimensionValueFormInfo.getFormKeys();
            gatherformKey.addAll(accessFormKeys);
            double formStartProgress = (double)formInfoIndex / (double)accessFormInfos.size() * 0.9 + 0.1;
            double formEndProgress = (double)(formInfoIndex + 1) / (double)accessFormInfos.size() * 0.9 + 0.1;
            ArrayList gatherTableDefineList = new ArrayList();
            HashMap tableCache = new HashMap();
            HashSet warnTables = new HashSet();
            accessFormKeys.forEach(formKey -> gatherTableDefineList.addAll(GatherTableUtil.getGatherTables(formKey, null, tableCache, warnTables)));
            if (!warnTables.isEmpty()) {
                for (String warnTable : warnTables) {
                    logger.warn("\u5b58\u50a8\u8868\u3010".concat(warnTable).concat("\u3011\u8fc7\u6ee4\u6761\u4ef6\u5b58\u5728\u4ea4\u53c9\u573a\u666f\uff0c\u8bf7\u68c0\u67e5\uff01"));
                }
            }
            if (gatherTableDefineList.isEmpty() || StringUtils.isEmpty((String)targetKey)) {
                asyncTaskLog.setMessage("\u672a\u627e\u5230\u53ef\u6c47\u603b\u62a5\u8868\uff0c\u8bf7\u68c0\u67e5\u6c47\u603b\u914d\u7f6e\uff01");
                String objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
                logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u83b7\u53d6\u53ef\u6c47\u603b\u62a5\u8868", "\u4e1a\u52a1\u9519\u8bef\uff1a\u672a\u627e\u5230\u53ef\u6c47\u603b\u62a5\u8868\uff0c\u8bf7\u68c0\u67e5\u6c47\u603b\u914d\u7f6e\uff0c\u6765\u6e90\u5355\u4f4d\u4e3a" + sourceKeys.toString());
                if (asyncTaskMonitor != null) {
                    asyncTaskMonitor.error("summary_warn_info", null, objectToJson);
                }
                return;
            }
            dimensionValueSet.clearValue(entityDimension);
            dimensionValueSet.clearValue(periodDimension);
            condition.setFormSchemeKey(selectDataGatherParam.getFormSchemeKey());
            condition.setSourceDimensions(dimensionValueSet);
            condition.setTargetDimension(dimensionValueSet);
            condition.setUnitView(entityViewDefine);
            condition.setTaskKey(selectDataGatherParam.getTaskKey());
            condition.setDataSchemeKey(dataSchemeKey);
            if (!noAccessEntityForms.isEmpty()) {
                NotGatherEntityValue notGatherEntityValue = new NotGatherEntityValue();
                notGatherEntityValue.setEntityForms(noAccessEntityForms);
                condition.setNotGatherEntityValue(notGatherEntityValue);
            }
            if (StringUtils.isNotEmpty((String)periodCode)) {
                condition.setPeriodCode(periodCode);
            }
            condition.setRecursive(false);
            condition.setGatherDirection(GatherDirection.GATHER_TO_GROUP);
            condition.setGatherTables(gatherTableDefineList);
            dataGather.setGatherCondition(condition);
            DataGatherParallelMonitor monitor = new DataGatherParallelMonitor();
            if (asyncTaskMonitor != null) {
                monitor = new DataGatherParallelMonitor(asyncTaskMonitor);
            }
            monitor.setStartProgress(formStartProgress, formEndProgress);
            dataGather.setMonitor((IMonitor)monitor);
            logHelper.info(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u8c03\u7528\u5f15\u64ce\u5c42", "\u5f15\u64ce\u8c03\u7528\u5f00\u59cb");
            try {
                dataGather.executeSelectGather((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, targetKey, sourceKeys);
                Map bizKeyOrderMappings = condition.getBizKeyOrderMappings();
                if (!CollectionUtils.isEmpty(bizKeyOrderMappings)) {
                    DimensionValueSet dimensionValueSet1 = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionValue);
                    dimensionValueSet1.clearValue(entityDimension);
                    List<String> collect = gatherTableDefineList.stream().filter(a -> !a.isFixed() && a.getTableDefine().getSyncError() != false).map(GatherTableDefine::getFormId).distinct().collect(Collectors.toList());
                    this.errorItemListGatherService.doErrorItemListGather(formScheme.getKey(), collect, dimensionValueSet1, bizKeyOrderMappings, condition.getGatherEntityValue(), condition.getGatherSingleDims());
                }
            }
            catch (SQLException e3) {
                logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u5931\u8d25", "\u9009\u62e9\u6c47\u603b\u5931\u8d25-SQLException" + e3.getMessage() + "\uff0c\u6765\u6e90\u5355\u4f4d\u4e3a" + sourceKeys.toString());
                if (asyncTaskMonitor != null) {
                    asyncTaskMonitor.error("summary_fail_info", (Throwable)e3);
                }
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e3.getMessage(), e3);
                return;
            }
            catch (Exception e4) {
                if (e4 instanceof TableLoaderException && e4.getCause() instanceof SQLIntegrityConstraintViolationException) {
                    String logMsg = "\u6709\u591a\u4e2a\u4eba\u5728\u540c\u65f6\u6c47\u603b\u540c\u4e00\u5355\u4f4d\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5\uff01";
                    logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u5931\u8d25", logMsg);
                    if (asyncTaskMonitor != null) {
                        asyncTaskMonitor.error(logMsg, (Throwable)e4);
                    }
                } else if (e4 instanceof DataGatherExecption) {
                    logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u5931\u8d25", "DataGatherExecption\uff0c\u6765\u6e90\u5355\u4f4d\u4e3a" + sourceKeys.toString());
                    if (asyncTaskMonitor != null) {
                        asyncTaskMonitor.error(e4.getMessage(), (Throwable)e4);
                    }
                } else {
                    logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u5931\u8d25", "\u9009\u62e9\u6c47\u603b\u5931\u8d25" + e4.getMessage() + "\u6765\u6e90\u5355\u4f4d\u4e3a" + sourceKeys.toString());
                    if (asyncTaskMonitor != null) {
                        asyncTaskMonitor.error("summary_fail_info", (Throwable)e4);
                    }
                }
                return;
            }
            logHelper.info(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u8c03\u7528\u5f15\u64ce\u5c42", "\u5f15\u64ce\u8c03\u7528\u5b8c\u6210");
        }
        if (!CollectionUtils.isEmpty(this.dataGatherHandlerList)) {
            GatherEntityMap finalGatherEntityMap = gatherEntityMap;
            this.dataGatherHandlerList.stream().forEach(handler -> handler.executeAfter(targetKey, selectDataGatherParam, finalGatherEntityMap));
        }
        GatherCompleteSource source = new GatherCompleteSource(formScheme.getKey(), new ArrayList<String>(gatherformKey), dimensionCollection, sourceKeys, false);
        this.applicationContext.publishEvent(new GatherCompleteEvent(source));
        if (asyncTaskMonitor != null) {
            asyncTaskLog.setSuccess(Boolean.valueOf(true));
            String objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
            if (StringUtils.isEmpty((String)asyncTaskLog.getMessage())) {
                asyncTaskMonitor.finish("summary_success_info", (Object)objectToJson);
            } else {
                asyncTaskMonitor.error("summary_fail_info", null, objectToJson);
            }
        }
        logHelper.info(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u670d\u52a1\u5c42\u7ed3\u675f", "\u9009\u62e9\u6c47\u603b\u5b8c\u6210,\u6765\u6e90\u5355\u4f4d\uff1a" + sourceKeys.toString());
    }
}

