/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
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
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.engine.execption.DataGatherExecption
 *  com.jiuqi.nr.data.engine.gather.FloatTableGatherSetting
 *  com.jiuqi.nr.data.engine.gather.GatherCondition
 *  com.jiuqi.nr.data.engine.gather.GatherDirection
 *  com.jiuqi.nr.data.engine.gather.GatherEntityFilterProvider
 *  com.jiuqi.nr.data.engine.gather.GatherEntityMap
 *  com.jiuqi.nr.data.engine.gather.GatherTableDefine
 *  com.jiuqi.nr.data.engine.gather.GatherTempTableHandler
 *  com.jiuqi.nr.data.engine.gather.IDataGather
 *  com.jiuqi.nr.data.engine.gather.IDataGatherProvider
 *  com.jiuqi.nr.data.engine.gather.NotGatherEntityValue
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.access.MergeDataPermission
 *  com.jiuqi.nr.dataservice.core.access.UnitPermission
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.formtype.common.EntityUnitNatureGetter
 *  com.jiuqi.nr.formtype.common.UnitNature
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.gather.refactor.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
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
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.engine.execption.DataGatherExecption;
import com.jiuqi.nr.data.engine.gather.FloatTableGatherSetting;
import com.jiuqi.nr.data.engine.gather.GatherCondition;
import com.jiuqi.nr.data.engine.gather.GatherDirection;
import com.jiuqi.nr.data.engine.gather.GatherEntityFilterProvider;
import com.jiuqi.nr.data.engine.gather.GatherEntityMap;
import com.jiuqi.nr.data.engine.gather.GatherTableDefine;
import com.jiuqi.nr.data.engine.gather.GatherTempTableHandler;
import com.jiuqi.nr.data.engine.gather.IDataGather;
import com.jiuqi.nr.data.engine.gather.IDataGatherProvider;
import com.jiuqi.nr.data.engine.gather.NotGatherEntityValue;
import com.jiuqi.nr.data.gather.bean.NodeGatherParam;
import com.jiuqi.nr.data.gather.exception.DataGatherException;
import com.jiuqi.nr.data.gather.listener.DataGatherHandler;
import com.jiuqi.nr.data.gather.listener.DataGatherParallelMonitor;
import com.jiuqi.nr.data.gather.listener.DataGatherUnitEntityFilterProvider;
import com.jiuqi.nr.data.gather.listener.FloatTableGatherHandler;
import com.jiuqi.nr.data.gather.listener.FloatTableGatherProvider;
import com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor;
import com.jiuqi.nr.data.gather.refactor.monitor.MonitorEventParam;
import com.jiuqi.nr.data.gather.refactor.monitor.impl.DefaultGatherServiceMonitor;
import com.jiuqi.nr.data.gather.refactor.provider.NodeGatherOptionProvider;
import com.jiuqi.nr.data.gather.refactor.service.NodeGatherService;
import com.jiuqi.nr.data.gather.service.impl.ErrorItemListGatherService;
import com.jiuqi.nr.data.gather.util.GatherTableUtil;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.access.MergeDataPermission;
import com.jiuqi.nr.dataservice.core.access.UnitPermission;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.formtype.common.EntityUnitNatureGetter;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class NodeGatherServiceImpl
implements NodeGatherService {
    private static final Logger logger = LoggerFactory.getLogger(NodeGatherServiceImpl.class);
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
    private GatherTempTableHandler gatherTempTableHandler;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired(required=false)
    private DataGatherUnitEntityFilterProvider dataGatherUnitEntityFilterProvider;
    @Autowired
    private ErrorItemListGatherService errorItemListGatherService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private FloatTableGatherHandler floatTableGatherHandler;
    @Autowired
    private IProviderStore providerStore;
    @Autowired
    private NodeGatherOptionProvider optionProvider;

    @Override
    public void nodeGather(NodeGatherParam nodeGatherParam) {
        DefaultGatherServiceMonitor monitor = new DefaultGatherServiceMonitor(null, this.dataGatherHandlerList, this.applicationContext);
        this.nodeGather(nodeGatherParam, monitor);
    }

    @Override
    public void nodeGather(NodeGatherParam nodeGatherParam, IGatherServiceMonitor monitor) {
        String objectToJson;
        String isRecursive;
        FormSchemeDefine formScheme;
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("\u6c47\u603b\u670d\u52a1", OperLevel.USER_OPER);
        AsyncTaskLog asyncTaskLog = new AsyncTaskLog(Boolean.valueOf(false));
        monitor.progressAndMessage(0.01, "summary_start");
        try {
            formScheme = this.runtimeView.getFormScheme(nodeGatherParam.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new DataGatherException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{nodeGatherParam.getFormSchemeKey()});
        }
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(nodeGatherParam.getTaskKey());
        String dataSchemeKey = taskDefine.getDataScheme();
        String entityId = formScheme.getDw();
        DsContext dsContext = DsContextHolder.getDsContext();
        if (dsContext != null && StringUtils.isNotEmpty((String)dsContext.getContextEntityId())) {
            entityId = dsContext.getContextEntityId();
        }
        String entityDimension = this.entityMetaService.getDimensionName(entityId);
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimension = periodAdapter.getPeriodDimensionName();
        DimensionCollection dimensionCollection = nodeGatherParam.getDimensionCollection();
        List dimensionCombines = dimensionCollection.getDimensionCombinations();
        if (CollectionUtils.isEmpty(dimensionCombines)) {
            monitor.error("no_summary_unit", null);
            logger.debug("\u6c92\u6709\u53ef\u6c47\u603b\u7684\u5355\u4f4d\uff01");
            return;
        }
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        for (DimensionCombination dimensionCombine : dimensionCombines) {
            dimensionValueSets.add(dimensionCombine.toDimensionValueSet());
        }
        DimensionValueSet dimensionSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensionValueSets);
        String targetKey = String.valueOf(dimensionSet.getValue(entityDimension));
        String periodCode = String.valueOf(dimensionSet.getValue(periodDimension));
        DimensionValueSet accessDimensionValueSet = new DimensionValueSet(dimensionSet);
        Map singleSelectDimCols = this.gatherTempTableHandler.getSingleSelectDimCols(dataSchemeKey, nodeGatherParam.getTaskKey());
        for (String singleDimName : singleSelectDimCols.keySet()) {
            accessDimensionValueSet.clearValue(singleDimName);
        }
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(entityId, new String[]{targetKey});
        logDimensionCollection.setPeriod(formScheme.getDateTime(), periodCode);
        boolean recursive = nodeGatherParam.isRecursive();
        String string = isRecursive = recursive ? "-\u5c42\u5c42\u6c47\u603b" : "";
        if (StringUtils.isEmpty((String)targetKey)) {
            asyncTaskLog.setMessage("\u76ee\u6807\u6c47\u603b\u8282\u70b9\u4e3a\u7a7a\u3002");
            String objectToJson2 = JsonUtil.objectToJson((Object)asyncTaskLog);
            logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u83b7\u53d6\u76ee\u6807\u8282\u70b9", "\u4e1a\u52a1\u9519\u8bef\uff1a\u76ee\u6807\u8282\u70b9\u4e3a\u7a7a");
            monitor.error("target_summary_node_null", null, objectToJson2);
            return;
        }
        if (targetKey.contains(";")) {
            asyncTaskLog.setMessage("\u76ee\u6807\u6c47\u603b\u8282\u70b9\u4e3a\u591a\u4e2a\uff0c\u76ee\u524d\u53ea\u652f\u6301\u4e00\u4e2a\u9876\u7ea7\u8282\u70b9\u3002");
            String objectToJson3 = JsonUtil.objectToJson((Object)asyncTaskLog);
            logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u83b7\u53d6\u76ee\u6807\u8282\u70b9", "\u4e1a\u52a1\u9519\u8bef\uff1a\u76ee\u6807\u6c47\u603b\u8282\u70b9\u4e3a\u591a\u4e2a\uff0c\u76ee\u524d\u53ea\u652f\u6301\u4e00\u4e2a\u9876\u7ea7\u8282\u70b9\u3002");
            monitor.error("target_summary_node_more", null, objectToJson3);
            return;
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, nodeGatherParam.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        IDataGather dataGather = this.getDataGather(nodeGatherParam, entityDimension);
        UnitReportLog unitReportLog = this.dataServiceLoggerFactory.getUnitReportLog();
        dataGather.setUnitReportLog(unitReportLog);
        monitor.progressAndMessage(0.05, "summary_auth_fliter");
        List<String> formKeys = this.getFormKeys(nodeGatherParam);
        EvaluatorParam evaluatorParam = new EvaluatorParam();
        evaluatorParam.setTaskId(taskDefine.getKey());
        evaluatorParam.setFormSchemeId(nodeGatherParam.getFormSchemeKey());
        DataPermissionEvaluator evaluator = this.providerStore.getDataPermissionEvaluatorFactory().createEvaluator(evaluatorParam, dimensionCollection, formKeys);
        MergeDataPermission dataPermission = evaluator.mergeAccess(dimensionCollection, formKeys, AuthType.SYS_WRITEABLE);
        List accessResources = dataPermission.getAccessResources();
        EntityViewDefine entityViewDefine = this.getEntityViewDefine(entityId, formScheme, dsContext == null ? null : dsContext.getContextFilterExpression());
        GatherEntityMap gatherEntityMap = null;
        GatherCondition condition = new GatherCondition();
        condition.setSumAfterParentUpload(Boolean.valueOf(this.optionProvider.isIgnoreWorkFlow()));
        condition.setFormSchemeKey(nodeGatherParam.getFormSchemeKey());
        condition.setBZHZBGather(nodeGatherParam.isBZHZBGather());
        dataGather.setGatherCondition(condition);
        if (nodeGatherParam.isDifference()) {
            try {
                IEntityTable entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, entityViewDefine, periodCode, nodeGatherParam.getFormSchemeKey());
                IEntityRow entityRow = entityTable.findByEntityKey(targetKey);
                if (entityRow == null) {
                    asyncTaskLog.setMessage("\u6c47\u603b\u76ee\u6807\u5355\u4f4d\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u6c47\u603b\u3002");
                    logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u83b7\u53d6\u76ee\u6807\u5355\u4f4d", "\u4e1a\u52a1\u9519\u8bef\uff1a\u6c47\u603b\u76ee\u6807\u5355\u4f4d\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u6c47\u603b\u3002");
                    monitor.error("no_summary_unit", null);
                    return;
                }
                IFormTypeApplyService formTypeApplyService = (IFormTypeApplyService)BeanUtil.getBean(IFormTypeApplyService.class);
                EntityUnitNatureGetter entityFormGather = formTypeApplyService.getEntityFormTypeGetter(entityId);
                if (this.isMinus(entityFormGather, entityRow)) {
                    targetKey = entityRow.getParentEntityKey();
                }
            }
            catch (Exception e) {
                logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u5dee\u989d\u6c47\u603b\u5224\u65ad\u76ee\u6807\u5355\u4f4d\u7c7b\u578b\u5931\u8d25", "\u4e1a\u52a1\u9519\u8bef\uff1a\u5dee\u989d\u6c47\u603b\u5224\u65ad\u76ee\u6807\u5355\u4f4d\u7c7b\u578b\u5931\u8d25\uff01");
                monitor.error("summary_fail_info", e);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
        }
        if (recursive || nodeGatherParam.isDifference()) {
            try {
                gatherEntityMap = dataGather.getGatherEntityMap(targetKey, nodeGatherParam.isDifference(), recursive, (com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, nodeGatherParam.getFormSchemeKey(), entityViewDefine, periodCode);
            }
            catch (Exception e) {
                logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u6784\u5efa\u6c47\u603b\u5355\u4f4d\u53ca\u4e0b\u7ea7\u5355\u4f4dMap", "\u4e1a\u52a1\u9519\u8bef\uff1a\u6784\u9020\u6c47\u603b\u5355\u4f4d\u548c\u4e0b\u7ea7\u5355\u4f4dMap\u5931\u8d25\uff01");
                monitor.error("summary_fail_info", e);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
        }
        MonitorEventParam monitorEventParam = new MonitorEventParam(targetKey, nodeGatherParam);
        monitorEventParam.setGatherEntityMap(gatherEntityMap);
        monitor.executeBefore(monitorEventParam);
        HashMap<String, Set> noAccessEntityForms = new HashMap<String, Set>();
        if (recursive) {
            if (gatherEntityMap == null || gatherEntityMap.getGatherEntitys() == null || gatherEntityMap.getGatherEntitys().isEmpty()) {
                asyncTaskLog.setMessage("\u6ca1\u6709\u53c2\u4e0e\u6c47\u603b\u7684\u5355\u4f4d\u3002");
                objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
                logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u83b7\u53d6\u6c47\u603b\u5355\u4f4d", "\u4e1a\u52a1\u9519\u8bef\uff1a\u6ca1\u6709\u53c2\u4e0e\u6c47\u603b\u7684\u5355\u4f4d\u3002");
                monitor.error("no_summary_unit", null, objectToJson);
                return;
            }
            HashSet<String> ignoreItems = new HashSet<String>();
            String sysDataSumUploaded = this.iNvwaSystemOptionService.get("nr-data-entry-group", "DATASUM_AFTER_UPLOAD");
            if (null != sysDataSumUploaded && Integer.parseInt(sysDataSumUploaded) == 1) {
                ignoreItems.add("upload");
            }
            List gatherEntities = gatherEntityMap.getGatherEntitys();
            String recursiveUnit = gatherEntities.stream().map(String::valueOf).collect(Collectors.joining(";"));
            accessDimensionValueSet.setValue(entityDimension, (Object)recursiveUnit);
            DimensionCollection newDimension = DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)accessDimensionValueSet, (String)formScheme.getKey());
            MergeDataPermission mergeDataPermission = evaluator.mergeAccess(newDimension, formKeys, AuthType.WRITEABLE, ignoreItems);
            List unAccessResources = mergeDataPermission.getUnAccessResources();
            for (UnitPermission noAccessInfo : unAccessResources) {
                Map masterKey = noAccessInfo.getMasterKey();
                List resourceIds = noAccessInfo.getResourceIds();
                String unitKeys = ((DimensionValue)masterKey.get(entityDimension)).getValue();
                ArrayList<String> unitKeyList = new ArrayList<String>(Arrays.asList(unitKeys.split(";")));
                for (String unitKey : unitKeyList) {
                    noAccessEntityForms.computeIfAbsent(unitKey, k -> new HashSet()).addAll(resourceIds);
                }
            }
        } else if (nodeGatherParam.isDifference()) {
            if (gatherEntityMap == null || gatherEntityMap.getGatherEntitys() == null || gatherEntityMap.getGatherEntitys().isEmpty()) {
                asyncTaskLog.setMessage("\u672a\u627e\u5230\u5dee\u989d\u6c47\u603b\u8282\u70b9\u3002");
                objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
                logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u83b7\u53d6\u5dee\u989d\u8282\u70b9", "\u4e1a\u52a1\u9519\u8bef\uff1a\u672a\u627e\u5230\u5dee\u989d\u6c47\u603b\u8282\u70b9\u3002");
                monitor.error("nofind_balance_summary_node", null, objectToJson);
                return;
            }
            List gatherEntities = gatherEntityMap.getGatherEntitys();
            if (gatherEntities.size() != 1) {
                asyncTaskLog.setMessage("\u627e\u5230\u591a\u4e2a\u5dee\u989d\u5355\u4f4d\u3002");
                String objectToJson4 = JsonUtil.objectToJson((Object)asyncTaskLog);
                logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u83b7\u53d6\u5dee\u989d\u8282\u70b9", "\u4e1a\u52a1\u9519\u8bef\uff1a\u627e\u5230\u591a\u4e2a\u5dee\u989d\u5355\u4f4d\u3002");
                monitor.error("more_balance_summary_node", null, objectToJson4);
                return;
            }
            String minusUnit = (String)gatherEntities.get(0);
            accessDimensionValueSet.setValue(entityDimension, (Object)minusUnit);
            DimensionCollection newDimension = DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)accessDimensionValueSet, (String)formScheme.getKey());
            MergeDataPermission diffDataPermission = evaluator.mergeAccess(newDimension, formKeys, AuthType.WRITEABLE);
            accessResources = diffDataPermission.getAccessResources();
        }
        monitor.progressAndMessage(0.1, "summary_ing");
        if (accessResources.isEmpty()) {
            asyncTaskLog.setMessage("\u76ee\u6807\u5355\u4f4d\u5bf9\u76ee\u6807\u62a5\u8868\u65e0\u6743\u9650\uff0c\u8bf7\u68c0\u67e5\uff01");
            objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
            logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u5355\u4f4d\u6743\u9650\u5224\u65ad", "\u4e1a\u52a1\u9519\u8bef\uff1a\u76ee\u6807\u5355\u4f4d\u5bf9\u76ee\u6807\u62a5\u8868\u65e0\u6743\u9650\uff0c\u8bf7\u68c0\u67e5\uff01");
            monitor.error("no_summary_access", null, objectToJson);
            return;
        }
        HashMap<String, FloatTableGatherSetting> floatTableGatherSettingMap = new HashMap();
        List<Object> floatTableGatherParam = new ArrayList();
        FloatTableGatherProvider floatTableGatherProvider = this.floatTableGatherHandler.getFloatTableGatherProvider();
        if (!CollectionUtils.isEmpty(floatTableGatherProvider.getFloatTableGatherParam())) {
            floatTableGatherParam = floatTableGatherProvider.getFloatTableGatherParam();
            floatTableGatherSettingMap = floatTableGatherParam.stream().collect(Collectors.toMap(FloatTableGatherSetting::getRegionKey, a -> a));
        }
        HashSet<String> gatherFormKey = new HashSet<String>();
        for (int formInfoIndex = 0; formInfoIndex < accessResources.size(); ++formInfoIndex) {
            if (monitor.isCancel()) {
                return;
            }
            UnitPermission unitPermission = (UnitPermission)accessResources.get(formInfoIndex);
            Map dimensionValue = unitPermission.getMasterKey();
            List accessFormKeys = unitPermission.getResourceIds();
            gatherFormKey.addAll(accessFormKeys);
            double formStartProgress = (double)formInfoIndex / (double)accessResources.size() * 0.9 + 0.1;
            double formEndProgress = (double)(formInfoIndex + 1) / (double)accessResources.size() * 0.9 + 0.1;
            ArrayList<GatherTableDefine> gatherTableDefineList = new ArrayList<GatherTableDefine>();
            HashSet<String> warnTables = new HashSet<String>();
            boolean isSingleFloatTableGahter = false;
            if (!CollectionUtils.isEmpty(floatTableGatherParam)) {
                isSingleFloatTableGahter = ((FloatTableGatherSetting)floatTableGatherParam.get(0)).isSingleFloatTableGather();
            }
            boolean finalIsSingleFloatTableGather = isSingleFloatTableGahter;
            accessFormKeys.forEach(formKey -> gatherTableDefineList.addAll(GatherTableUtil.getGatherTables(formKey, null, new HashMap<String, Set<String>>(), warnTables, finalIsSingleFloatTableGather)));
            NodeGatherServiceImpl.printWarnTable(warnTables);
            if (gatherTableDefineList.isEmpty() || StringUtils.isEmpty((String)targetKey)) {
                asyncTaskLog.setMessage("\u672a\u627e\u5230\u53ef\u6c47\u603b\u62a5\u8868\uff0c\u8bf7\u68c0\u67e5\u6c47\u603b\u914d\u7f6e\uff01");
                String objectToJson5 = JsonUtil.objectToJson((Object)asyncTaskLog);
                logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u83b7\u53d6\u53ef\u6c47\u603b\u62a5\u8868", "\u4e1a\u52a1\u9519\u8bef\uff1a\u672a\u627e\u5230\u53ef\u6c47\u603b\u62a5\u8868\uff0c\u8bf7\u68c0\u67e5\u6c47\u603b\u914d\u7f6e\uff01");
                monitor.error("summary_warn_info", null, objectToJson5);
                return;
            }
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionValue);
            NodeGatherServiceImpl.clearDwAndPeriod(dimensionValueSet, entityDimension, periodDimension);
            this.setGatherCondition(condition, nodeGatherParam, dimensionValueSet, dimensionValueSet, entityViewDefine, dataSchemeKey, periodCode, recursive, nodeGatherParam.isDifference(), gatherTableDefineList);
            if (!noAccessEntityForms.isEmpty()) {
                NotGatherEntityValue notGatherEntityValue = new NotGatherEntityValue();
                notGatherEntityValue.setEntityForms(noAccessEntityForms);
                condition.setNotGatherEntityValue(notGatherEntityValue);
            }
            if (!CollectionUtils.isEmpty(floatTableGatherProvider.getFloatTableGatherParam())) {
                List<GatherTableDefine> gatherFloatTableDefineList = this.setFloatTableGatherParam(gatherTableDefineList, floatTableGatherSettingMap);
                condition.setGatherTables(gatherFloatTableDefineList);
            }
            dataGather.setGatherCondition(condition);
            DataGatherParallelMonitor parallelMonitor = new DataGatherParallelMonitor(monitor);
            parallelMonitor.setStartProgress(formStartProgress, formEndProgress);
            dataGather.setMonitor((IMonitor)parallelMonitor);
            try {
                dataGather.executeNodeGather((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, targetKey);
                Map bizKeyOrderMappings = condition.getBizKeyOrderMappings();
                if (!CollectionUtils.isEmpty(bizKeyOrderMappings)) {
                    DimensionValueSet dimensionValueSet1 = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionValue);
                    dimensionValueSet1.clearValue(entityDimension);
                    List<String> collect = gatherTableDefineList.stream().filter(a -> !a.isFixed() && a.getTableDefine().getSyncError() != false).map(GatherTableDefine::getFormId).distinct().collect(Collectors.toList());
                    this.errorItemListGatherService.doErrorItemListGather(formScheme.getKey(), collect, dimensionValueSet1, bizKeyOrderMappings, condition.getGatherEntityValue(), condition.getGatherSingleDims());
                }
            }
            catch (SQLException e) {
                logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u5931\u8d25", "\u6c47\u603b\u5931\u8d25-SQLException-" + e.getMessage());
                monitor.error("summary_fail_info", e);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
                return;
            }
            catch (DataGatherExecption e) {
                logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u5931\u8d25", "\u6c47\u603b\u5931\u8d25-" + e.getMessage());
                monitor.error(e.getMessage(), e);
                return;
            }
            catch (Exception e) {
                logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u5931\u8d25", "\u6c47\u603b\u5931\u8d25-" + e.getMessage());
                monitor.error("summary_fail_info", e);
                return;
            }
            if (!parallelMonitor.isCancel()) continue;
            logHelper.info(nodeGatherParam.getTaskKey(), logDimensionCollection, unitReportLog, "\u8282\u70b9\u6c47\u603b\u53d6\u6d88\u5b8c\u6210", "\u8282\u70b9\u6c47\u603b\u53d6\u6d88\u5b8c\u6210");
            return;
        }
        monitorEventParam.setFormKeys(gatherFormKey);
        monitor.executeAfter(monitorEventParam);
        asyncTaskLog.setSuccess(Boolean.valueOf(true));
        String objectToJson6 = JsonUtil.objectToJson((Object)asyncTaskLog);
        if (StringUtils.isEmpty((String)asyncTaskLog.getMessage())) {
            monitor.finish("summary_success_info", objectToJson6);
        } else {
            monitor.error("summary_fail_info", null, objectToJson6);
        }
        logHelper.info(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u670d\u52a1\u5c42\u5b8c\u6210" + isRecursive, "\u8282\u70b9\u6c47\u603b" + isRecursive + "\u7ed3\u675f");
    }

    private EntityViewDefine getEntityViewDefine(String entityId, FormSchemeDefine formScheme, String contextFilterExpression) {
        EntityViewDefine entityViewDefine = entityId.equals(formScheme.getDw()) ? this.runtimeView.getViewByFormSchemeKey(formScheme.getKey()) : this.entityViewRunTimeController.buildEntityView(entityId, contextFilterExpression);
        return entityViewDefine;
    }

    @Override
    public void nodeGatherByDim(NodeGatherParam nodeGatherParam) {
        DefaultGatherServiceMonitor monitor = new DefaultGatherServiceMonitor(null, this.dataGatherHandlerList, this.applicationContext);
        this.nodeGatherByDim(nodeGatherParam, monitor);
    }

    @Override
    public void nodeGatherByDim(NodeGatherParam nodeGatherParam, IGatherServiceMonitor monitor) {
        FormSchemeDefine formScheme;
        if (StringUtils.isEmpty((String)nodeGatherParam.getGatherDimName())) {
            monitor.error("no_gather_dim", null);
            logger.debug("\u6c92\u6709\u76ee\u6807\u6c47\u603b\u7ef4\u5ea6\uff01");
            return;
        }
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("\u6c47\u603b\u670d\u52a1", OperLevel.USER_OPER);
        AsyncTaskLog asyncTaskLog = new AsyncTaskLog(Boolean.valueOf(false));
        monitor.progressAndMessage(0.01, "summary_start");
        try {
            formScheme = this.runtimeView.getFormScheme(nodeGatherParam.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new DataGatherException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{nodeGatherParam.getFormSchemeKey()});
        }
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(nodeGatherParam.getTaskKey());
        String dataSchemeKey = taskDefine.getDataScheme();
        String entityId = formScheme.getDw();
        DsContext dsContext = DsContextHolder.getDsContext();
        if (dsContext != null && StringUtils.isNotEmpty((String)dsContext.getContextEntityId())) {
            entityId = dsContext.getContextEntityId();
        }
        String entityDimension = this.entityMetaService.getDimensionName(entityId);
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimension = periodAdapter.getPeriodDimensionName();
        DimensionCollection dimensionCollection = nodeGatherParam.getDimensionCollection();
        List dimensionCombines = dimensionCollection.getDimensionCombinations();
        if (CollectionUtils.isEmpty(dimensionCombines)) {
            monitor.error("no_summary_unit", null);
            logger.debug("\u6c92\u6709\u53ef\u6c47\u603b\u7684\u5355\u4f4d\uff01");
            return;
        }
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        for (DimensionCombination dimensionCombine : dimensionCombines) {
            dimensionValueSets.add(dimensionCombine.toDimensionValueSet());
        }
        DimensionValueSet dimensionSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensionValueSets);
        Object value = dimensionSet.getValue(entityDimension);
        if (value instanceof List) {
            monitor.error("no_support_multi_dw", null);
            logger.debug("\u4e0d\u652f\u6301\u76ee\u6807\u5355\u4f4d\u662f\u591a\u4e2a");
            return;
        }
        String targetKey = String.valueOf(value);
        String periodCode = String.valueOf(dimensionSet.getValue(periodDimension));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(entityId, new String[]{targetKey});
        logDimensionCollection.setPeriod(formScheme.getDateTime(), periodCode);
        if (StringUtils.isEmpty((String)targetKey)) {
            asyncTaskLog.setMessage("\u76ee\u6807\u6c47\u603b\u8282\u70b9\u4e3a\u7a7a\u3002");
            String objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
            logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u83b7\u53d6\u76ee\u6807\u8282\u70b9", "\u4e1a\u52a1\u9519\u8bef\uff1a\u76ee\u6807\u8282\u70b9\u4e3a\u7a7a");
            monitor.error("target_summary_node_null", null, objectToJson);
            return;
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, nodeGatherParam.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        IDataGather dataGather = this.getDataGather(nodeGatherParam, entityDimension);
        monitor.progressAndMessage(0.05, "summary_auth_fliter");
        List<String> formKeys = this.getFormKeys(nodeGatherParam);
        EvaluatorParam evaluatorParam = new EvaluatorParam();
        evaluatorParam.setTaskId(taskDefine.getKey());
        evaluatorParam.setFormSchemeId(nodeGatherParam.getFormSchemeKey());
        DataPermissionEvaluator evaluator = this.providerStore.getDataPermissionEvaluatorFactory().createEvaluator(evaluatorParam, dimensionCollection, formKeys);
        MergeDataPermission dataPermission = evaluator.mergeAccess(dimensionCollection, formKeys, AuthType.SYS_WRITEABLE);
        List accessFormInfos = dataPermission.getAccessResources();
        MonitorEventParam monitorEventParam = new MonitorEventParam(targetKey, nodeGatherParam);
        monitorEventParam.setGatherEntityMap(null);
        monitor.executeBefore(monitorEventParam);
        monitor.progressAndMessage(0.1, "summary_ing");
        if (accessFormInfos.isEmpty()) {
            asyncTaskLog.setMessage("\u76ee\u6807\u5355\u4f4d\u5bf9\u76ee\u6807\u62a5\u8868\u65e0\u6743\u9650\uff0c\u8bf7\u68c0\u67e5\uff01");
            String objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
            logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u5355\u4f4d\u6743\u9650\u5224\u65ad", "\u4e1a\u52a1\u9519\u8bef\uff1a\u76ee\u6807\u5355\u4f4d\u5bf9\u76ee\u6807\u62a5\u8868\u65e0\u6743\u9650\uff0c\u8bf7\u68c0\u67e5\uff01");
            monitor.error("no_summary_access", null, objectToJson);
            return;
        }
        HashSet<String> gatherFormKey = new HashSet<String>();
        for (int formInfoIndex = 0; formInfoIndex < accessFormInfos.size(); ++formInfoIndex) {
            UnitPermission unitPermission = (UnitPermission)accessFormInfos.get(formInfoIndex);
            Map dimensionValue = unitPermission.getMasterKey();
            List accessFormKeys = unitPermission.getResourceIds();
            gatherFormKey.addAll(accessFormKeys);
            ArrayList<GatherTableDefine> gatherTableDefineList = new ArrayList<GatherTableDefine>();
            HashMap tableCache = new HashMap();
            HashSet<String> warnTables = new HashSet<String>();
            accessFormKeys.forEach(formKey -> gatherTableDefineList.addAll(GatherTableUtil.getGatherTables(formKey, null, tableCache, warnTables, false)));
            NodeGatherServiceImpl.printWarnTable(warnTables);
            if (gatherTableDefineList.isEmpty() || StringUtils.isEmpty((String)targetKey)) {
                asyncTaskLog.setMessage("\u672a\u627e\u5230\u53ef\u6c47\u603b\u62a5\u8868\uff0c\u8bf7\u68c0\u67e5\u6c47\u603b\u914d\u7f6e\uff01");
                String objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
                logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u83b7\u53d6\u53ef\u6c47\u603b\u62a5\u8868", "\u4e1a\u52a1\u9519\u8bef\uff1a\u672a\u627e\u5230\u53ef\u6c47\u603b\u62a5\u8868\uff0c\u8bf7\u68c0\u67e5\u6c47\u603b\u914d\u7f6e\uff01");
                monitor.error("summary_warn_info", null, objectToJson);
                return;
            }
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionValue);
            NodeGatherServiceImpl.clearDwAndPeriod(dimensionValueSet, entityDimension, periodDimension);
            DimensionValueSet sourceDimension = nodeGatherParam.getSourceDimension();
            NodeGatherServiceImpl.clearDwAndPeriod(sourceDimension, entityDimension, periodDimension);
            GatherCondition condition = new GatherCondition();
            EntityViewDefine entityViewDefine = this.getEntityViewDefine(entityId, formScheme, dsContext == null ? null : dsContext.getContextFilterExpression());
            this.setGatherCondition(condition, nodeGatherParam, dimensionValueSet, sourceDimension, entityViewDefine, dataSchemeKey, periodCode, false, false, gatherTableDefineList);
            condition.setGatherDimName(nodeGatherParam.getGatherDimName());
            dataGather.setGatherCondition(condition);
            DataGatherParallelMonitor parallelMonitor = new DataGatherParallelMonitor(monitor);
            double formStartProgress = (double)formInfoIndex / (double)accessFormInfos.size() * 0.9 + 0.1;
            double formEndProgress = (double)(formInfoIndex + 1) / (double)accessFormInfos.size() * 0.9 + 0.1;
            parallelMonitor.setStartProgress(formStartProgress, formEndProgress);
            dataGather.setMonitor((IMonitor)parallelMonitor);
            try {
                dataGather.executeNodeGatherByDim((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, targetKey);
            }
            catch (SQLException e) {
                logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u5931\u8d25", "\u6c47\u603b\u5931\u8d25-SQLException-" + e.getMessage());
                monitor.error("summary_fail_info", e);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
                return;
            }
            catch (DataGatherExecption e) {
                logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u5931\u8d25", "\u6c47\u603b\u5931\u8d25-" + e.getMessage());
                monitor.error(e.getMessage(), e);
                return;
            }
            catch (Exception e) {
                logHelper.error(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u5931\u8d25", "\u6c47\u603b\u5931\u8d25-" + e.getMessage());
                monitor.error("summary_fail_info", e);
                return;
            }
            if (!parallelMonitor.isCancel()) continue;
            logHelper.info(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u53d6\u6d88\u5b8c\u6210", "\u8282\u70b9\u6c47\u603b\u53d6\u6d88\u5b8c\u6210");
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
        logHelper.info(nodeGatherParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u6c47\u603b\u670d\u52a1\u5c42\u5b8c\u6210", "\u8282\u70b9\u6c47\u603b\u6309\u60c5\u666f\u6c47\u603b\u7ed3\u675f");
    }

    private List<String> getFormKeys(NodeGatherParam nodeGatherParam) {
        List<String> formKeys = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)nodeGatherParam.getFormKeys())) {
            formKeys = Stream.of(nodeGatherParam.getFormKeys().split(";")).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(formKeys)) {
            formKeys = this.runtimeView.queryAllFormKeysByFormScheme(nodeGatherParam.getFormSchemeKey());
        }
        return formKeys;
    }

    private static void clearDwAndPeriod(DimensionValueSet dimensionValueSet, String entityDimension, String periodDimension) {
        dimensionValueSet.clearValue(entityDimension);
        dimensionValueSet.clearValue(periodDimension);
    }

    private static void printWarnTable(Set<String> warnTables) {
        if (!warnTables.isEmpty()) {
            for (String warnTable : warnTables) {
                logger.warn("\u5b58\u50a8\u8868\u3010".concat(warnTable).concat("\u3011\u8fc7\u6ee4\u6761\u4ef6\u5b58\u5728\u4ea4\u53c9\u573a\u666f\uff0c\u8bf7\u68c0\u67e5\uff01"));
            }
        }
    }

    private void setGatherCondition(GatherCondition condition, NodeGatherParam nodeGatherParam, DimensionValueSet targetDimension, DimensionValueSet sourceDimension, EntityViewDefine entityViewDefine, String dataSchemeKey, String periodCode, boolean isRecursive, boolean isMinus, List<GatherTableDefine> gatherTableDefineList) {
        condition.setSourceDimensions(sourceDimension);
        condition.setTargetDimension(targetDimension);
        condition.setUnitView(entityViewDefine);
        condition.setTaskKey(nodeGatherParam.getTaskKey());
        condition.setDataSchemeKey(dataSchemeKey);
        if (StringUtils.isNotEmpty((String)periodCode)) {
            condition.setPeriodCode(periodCode);
        }
        condition.setRecursive(isRecursive);
        condition.setGatherDirection(isMinus ? GatherDirection.GATHER_TO_MINUS : GatherDirection.GATHER_TO_GROUP);
        condition.setGatherTables(gatherTableDefineList);
    }

    private IDataGather getDataGather(NodeGatherParam nodeGatherParam, String entityDimension) {
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(nodeGatherParam.getFormSchemeKey());
        IDataGather dataGather = this.dataGatherProvider.newDataGather(queryEnvironment);
        this.dataGatherUnitEntityFilterProvider.setGatherParam(nodeGatherParam);
        this.dataGatherUnitEntityFilterProvider.setMainDimensionName(entityDimension);
        dataGather.setEntityFilterProvider((GatherEntityFilterProvider)this.dataGatherUnitEntityFilterProvider);
        return dataGather;
    }

    private List<GatherTableDefine> setFloatTableGatherParam(List<GatherTableDefine> gatherTableDefineList, Map<String, FloatTableGatherSetting> floatTableGatherSettingMap) {
        Set<String> regionKeys = floatTableGatherSettingMap.keySet();
        List<GatherTableDefine> result = gatherTableDefineList.stream().filter(table -> regionKeys.contains(table.getRegionKey())).collect(Collectors.toList());
        for (GatherTableDefine gatherTableDefine : result) {
            FloatTableGatherSetting setting = floatTableGatherSettingMap.get(gatherTableDefine.getRegionKey());
            if (setting != null) {
                gatherTableDefine.setFloatTableGatherSetting(setting);
                continue;
            }
            FloatTableGatherSetting setting1 = new FloatTableGatherSetting();
            gatherTableDefine.setFloatTableGatherSetting(setting1);
        }
        return result;
    }

    private boolean isMinus(EntityUnitNatureGetter gather, IEntityRow row) {
        UnitNature unitNature = gather.getUnitNature(row);
        if (null != unitNature) {
            return unitNature.equals((Object)UnitNature.JTCEB);
        }
        return false;
    }

    private IEntityTable getEntityTable(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, EntityViewDefine unitView, String periodCode, String formSchemeKey) throws Exception {
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(unitView);
        DimensionValueSet masterKeys = new DimensionValueSet();
        if (!StringUtils.isEmpty((String)periodCode)) {
            masterKeys.setValue("DATATIME", (Object)periodCode);
        }
        entityQuery.setMasterKeys(masterKeys);
        executorContext.setVarDimensionValueSet(masterKeys);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeKey);
        executorContext.setPeriodView(formScheme.getDateTime());
        return entityQuery.executeReader((IContext)executorContext);
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

    public void setDataGatherProvider(IDataGatherProvider dataGatherProvider) {
        this.dataGatherProvider = dataGatherProvider;
    }

    public void setDataGatherHandlerList(List<DataGatherHandler> dataGatherHandlerList) {
        this.dataGatherHandlerList = dataGatherHandlerList;
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

    public void setEntityDataService(IEntityDataService entityDataService) {
        this.entityDataService = entityDataService;
    }

    public void setDataServiceLoggerFactory(DataServiceLoggerFactory dataServiceLoggerFactory) {
        this.dataServiceLoggerFactory = dataServiceLoggerFactory;
    }

    public void setiNvwaSystemOptionService(INvwaSystemOptionService iNvwaSystemOptionService) {
        this.iNvwaSystemOptionService = iNvwaSystemOptionService;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setFloatTableGatherHandler(FloatTableGatherHandler floatTableGatherHandler) {
        this.floatTableGatherHandler = floatTableGatherHandler;
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public void setOptionProvider(NodeGatherOptionProvider optionProvider) {
        this.optionProvider = optionProvider;
    }

    public void setGatherTempTableHandler(GatherTempTableHandler gatherTempTableHandler) {
        this.gatherTempTableHandler = gatherTempTableHandler;
    }
}

