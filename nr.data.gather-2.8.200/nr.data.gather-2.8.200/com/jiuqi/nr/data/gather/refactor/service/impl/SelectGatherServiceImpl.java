/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.sql.loader.TableLoaderException
 *  com.jiuqi.bi.util.StringUtils
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
 *  com.jiuqi.nr.common.log.UnitReportLog
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.engine.exception.DataGatherExecption
 *  com.jiuqi.nr.data.engine.gather.GatherCondition
 *  com.jiuqi.nr.data.engine.gather.GatherDirection
 *  com.jiuqi.nr.data.engine.gather.GatherEntityFilterProvider
 *  com.jiuqi.nr.data.engine.gather.GatherEntityMap
 *  com.jiuqi.nr.data.engine.gather.GatherTableDefine
 *  com.jiuqi.nr.data.engine.gather.IDataGather
 *  com.jiuqi.nr.data.engine.gather.IDataGatherProvider
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.access.MergeDataPermission
 *  com.jiuqi.nr.dataservice.core.access.UnitPermission
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.gather.refactor.service.impl;

import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.util.StringUtils;
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
import com.jiuqi.nr.common.log.UnitReportLog;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.engine.exception.DataGatherExecption;
import com.jiuqi.nr.data.engine.gather.GatherCondition;
import com.jiuqi.nr.data.engine.gather.GatherDirection;
import com.jiuqi.nr.data.engine.gather.GatherEntityFilterProvider;
import com.jiuqi.nr.data.engine.gather.GatherEntityMap;
import com.jiuqi.nr.data.engine.gather.GatherTableDefine;
import com.jiuqi.nr.data.engine.gather.IDataGather;
import com.jiuqi.nr.data.engine.gather.IDataGatherProvider;
import com.jiuqi.nr.data.gather.bean.SelectDataGatherParam;
import com.jiuqi.nr.data.gather.exception.DataGatherException;
import com.jiuqi.nr.data.gather.listener.DataGatherHandler;
import com.jiuqi.nr.data.gather.listener.DataGatherParallelMonitor;
import com.jiuqi.nr.data.gather.listener.DataGatherUnitEntityFilterProvider;
import com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor;
import com.jiuqi.nr.data.gather.refactor.monitor.MonitorEventParam;
import com.jiuqi.nr.data.gather.refactor.monitor.impl.DefaultGatherServiceMonitor;
import com.jiuqi.nr.data.gather.refactor.provider.NodeGatherOptionProvider;
import com.jiuqi.nr.data.gather.refactor.service.SelectGatherService;
import com.jiuqi.nr.data.gather.service.impl.ErrorItemListGatherService;
import com.jiuqi.nr.data.gather.util.GatherTableUtil;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.access.MergeDataPermission;
import com.jiuqi.nr.dataservice.core.access.UnitPermission;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SelectGatherServiceImpl
implements SelectGatherService {
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
    private ErrorItemListGatherService errorItemListGatherService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private IProviderStore providerStore;
    @Autowired
    private NodeGatherOptionProvider optionProvider;

    @Override
    public void selectNodeGather(SelectDataGatherParam selectDataGatherParam) {
        DefaultGatherServiceMonitor monitor = new DefaultGatherServiceMonitor(null, this.dataGatherHandlerList, this.applicationContext);
        this.selectNodeGather(selectDataGatherParam, monitor);
    }

    @Override
    public void selectNodeGather(SelectDataGatherParam selectDataGatherParam, IGatherServiceMonitor monitor) {
        FormSchemeDefine formScheme;
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("\u6c47\u603b\u670d\u52a1", OperLevel.USER_OPER);
        AsyncTaskLog asyncTaskLog = new AsyncTaskLog(Boolean.valueOf(false));
        monitor.progressAndMessage(0.01, "summary_start");
        try {
            formScheme = this.runtimeView.getFormScheme(selectDataGatherParam.getFormSchemeKey());
        }
        catch (Exception e) {
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
        List dimensionCombines = dimensionCollection.getDimensionCombinations();
        if (CollectionUtils.isEmpty(dimensionCombines)) {
            logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u51fa\u73b0\u9519\u8bef", "\u4e1a\u52a1\u9519\u8bef\uff1a\u53ef\u6c47\u603b\u7684\u5355\u4f4d\u4e0d\u5b58\u5728\uff01");
            monitor.error("no_summary_unit", null);
            logger.debug("\u6c92\u6709\u53ef\u6c47\u603b\u7684\u5355\u4f4d\uff01");
            return;
        }
        if (StringUtils.isEmpty((String)targetKey)) {
            asyncTaskLog.setMessage("\u76ee\u6807\u6c47\u603b\u8282\u70b9\u4e3a\u7a7a\u3002");
            String objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
            logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u83b7\u53d6\u76ee\u6807\u8282\u70b9", "\u4e1a\u52a1\u9519\u8bef\uff1a\u76ee\u6807\u6c47\u603b\u8282\u70b9\u4e3a\u7a7a");
            monitor.error("target_summary_node_null", null, objectToJson);
            return;
        }
        if (targetKey.contains(";")) {
            asyncTaskLog.setMessage("\u76ee\u6807\u6c47\u603b\u8282\u70b9\u4e3a\u591a\u4e2a\uff0c\u76ee\u524d\u53ea\u652f\u6301\u4e00\u4e2a\u9876\u7ea7\u8282\u70b9\u3002");
            String objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
            logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u83b7\u53d6\u76ee\u6807\u8282\u70b9", "\u4e1a\u52a1\u9519\u8bef\uff1a\u76ee\u6807\u6c47\u603b\u8282\u70b9\u4e3a\u591a\u4e2a\uff0c\u76ee\u524d\u53ea\u652f\u6301\u4e00\u4e2a\u9876\u7ea7\u8282\u70b9\u3002");
            monitor.error("target_summary_node_more", null, objectToJson);
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
        UnitReportLog unitReportLog = this.dataServiceLoggerFactory.getUnitReportLog();
        dataGather.setUnitReportLog(unitReportLog);
        List<Object> formKeys = new ArrayList();
        if (StringUtils.isNotEmpty((String)selectDataGatherParam.getFormKeys())) {
            formKeys = Stream.of(selectDataGatherParam.getFormKeys().split(";")).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(formKeys)) {
            formKeys = this.runtimeView.queryAllFormKeysByFormScheme(formScheme.getKey());
        }
        monitor.progressAndMessage(0.05, "summary_auth_fliter");
        EvaluatorParam evaluatorParam = new EvaluatorParam();
        evaluatorParam.setTaskId(taskDefine.getKey());
        evaluatorParam.setFormSchemeId(formScheme.getKey());
        DataPermissionEvaluator evaluator = this.providerStore.getDataPermissionEvaluatorFactory().createEvaluator(evaluatorParam, dimensionCollection, formKeys);
        MergeDataPermission dataPermission = evaluator.mergeAccess(dimensionCollection, formKeys, AuthType.SYS_WRITEABLE);
        List accessResources = dataPermission.getAccessResources();
        if (CollectionUtils.isEmpty(accessResources)) {
            asyncTaskLog.setMessage("\u76ee\u6807\u5355\u4f4d\u5bf9\u76ee\u6807\u62a5\u8868\u65e0\u6743\u9650\uff0c\u8bf7\u68c0\u67e5\uff01");
            String objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
            logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u5355\u4f4d\u6743\u9650\u5224\u65ad", "\u4e1a\u52a1\u9519\u8bef\uff1a\u76ee\u6807\u5355\u4f4d\u5bf9\u76ee\u6807\u62a5\u8868\u65e0\u6743\u9650\uff0c\u8bf7\u68c0\u67e5\uff01");
            monitor.error("no_summary_access", null, objectToJson);
            return;
        }
        EntityViewDefine entityViewDefine = entityId.equals(formScheme.getDw()) ? this.runtimeView.getViewByFormSchemeKey(formScheme.getKey()) : this.entityViewRunTimeController.buildEntityView(entityId, dsContext.getContextFilterExpression());
        GatherEntityMap gatherEntityMap = null;
        GatherCondition condition = new GatherCondition();
        condition.setSumAfterParentUpload(Boolean.valueOf(this.optionProvider.isIgnoreWorkFlow()));
        condition.setFormSchemeKey(selectDataGatherParam.getFormSchemeKey());
        dataGather.setGatherCondition(condition);
        MonitorEventParam monitorEventParam = new MonitorEventParam(targetKey, selectDataGatherParam);
        monitorEventParam.setGatherEntityMap(gatherEntityMap);
        monitor.executeBefore(monitorEventParam);
        monitor.progressAndMessage(0.1, "summary_ing");
        HashSet<String> gatherFormKey = new HashSet<String>();
        for (int formInfoIndex = 0; formInfoIndex < accessResources.size(); ++formInfoIndex) {
            if (monitor.isCancel()) {
                return;
            }
            UnitPermission unitPermission = (UnitPermission)accessResources.get(formInfoIndex);
            Map dimensionValue = unitPermission.getMasterKey();
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionValue);
            List accessFormKeys = unitPermission.getResourceIds();
            gatherFormKey.addAll(accessFormKeys);
            double formStartProgress = (double)formInfoIndex / (double)accessResources.size() * 0.9 + 0.1;
            double formEndProgress = (double)(formInfoIndex + 1) / (double)accessResources.size() * 0.9 + 0.1;
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
                logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u83b7\u53d6\u53ef\u6c47\u603b\u62a5\u8868", "\u4e1a\u52a1\u9519\u8bef\uff1a\u672a\u627e\u5230\u53ef\u6c47\u603b\u62a5\u8868\uff0c\u8bf7\u68c0\u67e5\u6c47\u603b\u914d\u7f6e\uff0c\u6765\u6e90\u5355\u4f4d\u4e3a" + sourceKeys);
                monitor.error("summary_warn_info", null, objectToJson);
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
            if (StringUtils.isNotEmpty((String)periodCode)) {
                condition.setPeriodCode(periodCode);
            }
            condition.setRecursive(false);
            condition.setGatherDirection(GatherDirection.GATHER_TO_GROUP);
            condition.setGatherTables(gatherTableDefineList);
            dataGather.setGatherCondition(condition);
            DataGatherParallelMonitor parallelMonitor = new DataGatherParallelMonitor(monitor);
            parallelMonitor.setStartProgress(formStartProgress, formEndProgress);
            dataGather.setMonitor((IMonitor)parallelMonitor);
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
            catch (SQLException e) {
                logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u5931\u8d25", "\u9009\u62e9\u6c47\u603b\u5931\u8d25-SQLException" + e.getMessage() + "\uff0c\u6765\u6e90\u5355\u4f4d\u4e3a" + sourceKeys);
                monitor.error("summary_fail_info", e);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
                return;
            }
            catch (Exception e) {
                if (e instanceof TableLoaderException && e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                    String logMsg = "\u6709\u591a\u4e2a\u4eba\u5728\u540c\u65f6\u6c47\u603b\u540c\u4e00\u5355\u4f4d\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5\uff01";
                    logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u5931\u8d25", logMsg);
                    monitor.error(logMsg, e);
                } else if (e instanceof DataGatherExecption) {
                    logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u5931\u8d25", "DataGatherExecption\uff0c\u6765\u6e90\u5355\u4f4d\u4e3a" + sourceKeys);
                    monitor.error(e.getMessage(), e);
                } else {
                    logHelper.error(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u5931\u8d25", "\u9009\u62e9\u6c47\u603b\u5931\u8d25" + e.getMessage() + "\u6765\u6e90\u5355\u4f4d\u4e3a" + sourceKeys);
                    monitor.error("summary_fail_info", e);
                }
                return;
            }
            if (!parallelMonitor.isCancel()) continue;
            logHelper.info(selectDataGatherParam.getTaskKey(), logDimensionCollection, unitReportLog, "\u9009\u62e9\u6c47\u603b\u53d6\u6d88\u5b8c\u6210", "\u9009\u62e9\u6c47\u603b\u53d6\u6d88\u5b8c\u6210");
            return;
        }
        monitorEventParam.setFormKeys(gatherFormKey);
        monitor.executeAfter(monitorEventParam);
        asyncTaskLog.setSuccess(Boolean.valueOf(true));
        String objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
        if (StringUtils.isEmpty((String)asyncTaskLog.getMessage())) {
            monitor.finish("summary_success_info", objectToJson);
        } else {
            monitor.error("summary_fail_info", null, objectToJson);
        }
        logHelper.info(selectDataGatherParam.getTaskKey(), logDimensionCollection, "\u9009\u62e9\u6c47\u603b\u670d\u52a1\u5c42\u7ed3\u675f", "\u9009\u62e9\u6c47\u603b\u5b8c\u6210,\u6765\u6e90\u5355\u4f4d\uff1a" + sourceKeys);
    }

    public void setRuntimeView(IRunTimeViewController runtimeView) {
        this.runtimeView = runtimeView;
    }

    public void setDataDefinitionRuntimeController(IDataDefinitionRuntimeController dataDefinitionRuntimeController) {
        this.dataDefinitionRuntimeController = dataDefinitionRuntimeController;
    }

    public void setEntityViewRunTimeController(IEntityViewRunTimeController entityViewRunTimeController) {
        this.entityViewRunTimeController = entityViewRunTimeController;
    }

    public void setDataGatherHandlerList(List<DataGatherHandler> dataGatherHandlerList) {
        this.dataGatherHandlerList = dataGatherHandlerList;
    }

    public void setDataGatherProvider(IDataGatherProvider dataGatherProvider) {
        this.dataGatherProvider = dataGatherProvider;
    }

    public void setEntityMetaService(IEntityMetaService entityMetaService) {
        this.entityMetaService = entityMetaService;
    }

    public void setPeriodEngineService(PeriodEngineService periodEngineService) {
        this.periodEngineService = periodEngineService;
    }

    public void setDataGatherUnitEntityFilterProvider(DataGatherUnitEntityFilterProvider dataGatherUnitEntityFilterProvider) {
        this.dataGatherUnitEntityFilterProvider = dataGatherUnitEntityFilterProvider;
    }

    public void setErrorItemListGatherService(ErrorItemListGatherService errorItemListGatherService) {
        this.errorItemListGatherService = errorItemListGatherService;
    }

    public void setDataServiceLoggerFactory(DataServiceLoggerFactory dataServiceLoggerFactory) {
        this.dataServiceLoggerFactory = dataServiceLoggerFactory;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public void setOptionProvider(NodeGatherOptionProvider optionProvider) {
        this.optionProvider = optionProvider;
    }
}

