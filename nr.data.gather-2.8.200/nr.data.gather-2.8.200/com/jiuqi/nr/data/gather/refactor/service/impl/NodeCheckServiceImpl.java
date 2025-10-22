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
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.engine.gather.CheckErrorItem
 *  com.jiuqi.nr.data.engine.gather.GatherCondition
 *  com.jiuqi.nr.data.engine.gather.GatherDirection
 *  com.jiuqi.nr.data.engine.gather.GatherEntityMap
 *  com.jiuqi.nr.data.engine.gather.GatherTempTableHandler
 *  com.jiuqi.nr.data.engine.gather.IDataGather
 *  com.jiuqi.nr.data.engine.gather.IDataGatherProvider
 *  com.jiuqi.nr.data.engine.gather.NodeCheckResult
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
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
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
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.engine.gather.CheckErrorItem;
import com.jiuqi.nr.data.engine.gather.GatherCondition;
import com.jiuqi.nr.data.engine.gather.GatherDirection;
import com.jiuqi.nr.data.engine.gather.GatherEntityMap;
import com.jiuqi.nr.data.engine.gather.GatherTempTableHandler;
import com.jiuqi.nr.data.engine.gather.IDataGather;
import com.jiuqi.nr.data.engine.gather.IDataGatherProvider;
import com.jiuqi.nr.data.engine.gather.NotGatherEntityValue;
import com.jiuqi.nr.data.gather.bean.NodeCheckFieldMessage;
import com.jiuqi.nr.data.gather.bean.NodeCheckParam;
import com.jiuqi.nr.data.gather.bean.NodeCheckResultItem;
import com.jiuqi.nr.data.gather.exception.DataGatherException;
import com.jiuqi.nr.data.gather.listener.DataGatherParallelMonitor;
import com.jiuqi.nr.data.gather.refactor.bean.DimCompareObject;
import com.jiuqi.nr.data.gather.refactor.bean.DimsObject;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckInfo;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResult;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResultItemInfo;
import com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor;
import com.jiuqi.nr.data.gather.refactor.monitor.MonitorEventParam;
import com.jiuqi.nr.data.gather.refactor.monitor.impl.DefaultMonitor;
import com.jiuqi.nr.data.gather.refactor.provider.NodeCheckOptionProvider;
import com.jiuqi.nr.data.gather.refactor.service.NodeCheckService;
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
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class NodeCheckServiceImpl
implements NodeCheckService {
    private static final Logger logger = LoggerFactory.getLogger(NodeCheckServiceImpl.class);
    @Resource
    IRunTimeViewController runtimeView;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    IDataGatherProvider dataGatherProvider;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IProviderStore providerStore;
    @Autowired
    private NodeCheckOptionProvider nodeCheckOptionProvider;
    @Autowired
    private GatherTempTableHandler gatherTempTableHandler;
    @Autowired
    DimCollectionBuildUtil dimCollectionBuildUtil;

    @Override
    public NodeCheckResult nodeCheck(NodeCheckParam nodeCheckParam) {
        DefaultMonitor monitor = new DefaultMonitor();
        return this.nodeCheck(nodeCheckParam, monitor);
    }

    @Override
    public NodeCheckResult nodeCheck(NodeCheckParam nodeCheckInfo, IGatherServiceMonitor monitor) {
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("\u6c47\u603b\u670d\u52a1", OperLevel.USER_OPER);
        AsyncTaskLog asyncTaskLog = new AsyncTaskLog(Boolean.valueOf(false));
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(nodeCheckInfo.getFormSchemeKey());
        }
        catch (Exception e2) {
            throw new DataGatherException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{nodeCheckInfo.getFormSchemeKey()});
        }
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(nodeCheckInfo.getTaskKey());
        String dataSchemeKey = taskDefine.getDataScheme();
        String entityId = formScheme.getDw();
        DsContext dsContext = DsContextHolder.getDsContext();
        if (dsContext != null && StringUtils.isNotEmpty((String)dsContext.getContextEntityId())) {
            entityId = dsContext.getContextEntityId();
        }
        String entityDimension = this.entityMetaService.getDimensionName(entityId);
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimension = periodAdapter.getPeriodDimensionName();
        if (null == entityDimension) {
            throw new DataGatherException("\u672a\u627e\u5230\u5355\u4f4d\u4e3b\u4f53");
        }
        NodeCheckResult nodeCheckResultInfo = new NodeCheckResult();
        HashSet<NodeCheckResultItemInfo> nodeCheckResult = new HashSet<NodeCheckResultItemInfo>();
        nodeCheckResultInfo.setResultItemInfos(nodeCheckResult);
        Set<String> ignoreItems = nodeCheckInfo.getIgnoreAccessItems();
        DimensionCollection dimensionCollection = nodeCheckInfo.getDimensionCollection();
        List dimensionCombines = dimensionCollection.getDimensionCombinations();
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        for (DimensionCombination dimensionCombine : dimensionCombines) {
            dimensionValueSets.add(dimensionCombine.toDimensionValueSet());
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensionValueSets);
        String targetKey = String.valueOf(dimensionValueSet.getValue(entityDimension));
        String period = String.valueOf(dimensionValueSet.getValue(periodDimension));
        DimensionValueSet accessDimensionValueSet = new DimensionValueSet(dimensionValueSet);
        Map singleSelectDimCols = this.gatherTempTableHandler.getSingleSelectDimCols(dataSchemeKey, nodeCheckInfo.getTaskKey());
        for (String singleDimName : singleSelectDimCols.keySet()) {
            accessDimensionValueSet.clearValue(singleDimName);
        }
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(entityId, new String[]{targetKey});
        logDimensionCollection.setPeriod(formScheme.getDateTime(), period);
        if (StringUtils.isEmpty((String)targetKey)) {
            asyncTaskLog.setMessage("\u8282\u70b9\u68c0\u67e5\u76ee\u6807\u8282\u70b9\u4e3a\u7a7a\u3002");
            String objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
            logHelper.error(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u68c0\u67e5\u83b7\u53d6\u76ee\u6807\u8282\u70b9", "\u4e1a\u52a1\u9519\u8bef\uff1a\u76ee\u6807\u8282\u70b9\u4e3a\u7a7a");
            monitor.error("target_summary_node_null", null, objectToJson);
            return null;
        }
        if (targetKey.contains(";")) {
            return null;
        }
        nodeCheckResultInfo.setUnitKey(targetKey);
        nodeCheckResultInfo.setDimensionValueSet(dimensionValueSet);
        List<Object> formKeys = new ArrayList();
        if (StringUtils.isNotEmpty((String)nodeCheckInfo.getFormKeys())) {
            formKeys = Stream.of(nodeCheckInfo.getFormKeys().split(";")).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(formKeys)) {
            formKeys = this.runtimeView.queryAllFormKeysByFormScheme(formScheme.getKey());
        }
        EvaluatorParam evaluatorParam = new EvaluatorParam();
        evaluatorParam.setTaskId(taskDefine.getKey());
        evaluatorParam.setFormSchemeId(formScheme.getKey());
        DataPermissionEvaluator evaluator = this.providerStore.getDataPermissionEvaluatorFactory().createEvaluator(evaluatorParam, dimensionCollection, formKeys);
        MergeDataPermission dataPermission = evaluator.mergeAccess(dimensionCollection, formKeys, AuthType.READABLE);
        List accessResources = dataPermission.getAccessResources();
        EntityViewDefine entityViewDefine = entityId.equals(formScheme.getDw()) ? this.runtimeView.getViewByFormSchemeKey(formScheme.getKey()) : this.entityViewRunTimeController.buildEntityView(entityId, dsContext.getContextFilterExpression());
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(nodeCheckInfo.getFormSchemeKey());
        IDataGather dataGather = this.dataGatherProvider.newDataGather(queryEnvironment);
        GatherEntityMap gatherEntityMap = null;
        GatherCondition condition = new GatherCondition();
        condition.setFormSchemeKey(nodeCheckInfo.getFormSchemeKey());
        dataGather.setGatherCondition(condition);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        if (nodeCheckInfo.isRecursive()) {
            try {
                gatherEntityMap = dataGather.getGatherEntityMap(targetKey, false, nodeCheckInfo.isRecursive(), (com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, nodeCheckInfo.getFormSchemeKey(), entityViewDefine, period);
            }
            catch (Exception e3) {
                logHelper.error(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u68c0\u67e5\u529f\u80fd\u51fa\u73b0\u9519\u8bef", "\u4e1a\u52a1\u9519\u8bef\uff1a\u6c47\u603b\u5355\u4f4d\u548c\u4e0b\u7ea7\u5355\u4f4dMap\u6784\u9020\u5931\u8d25\uff01");
                monitor.error("\u83b7\u53d6\u68c0\u67e5\u5355\u4f4d\u548c\u4e0b\u7ea7\u5931\u8d25\uff01", e3);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e3.getMessage(), (Object)e3);
            }
        }
        MonitorEventParam monitorEventParam = new MonitorEventParam(targetKey, nodeCheckInfo);
        monitorEventParam.setGatherEntityMap(gatherEntityMap);
        monitor.executeBefore(monitorEventParam);
        HashMap<String, Set> noAccessEntityForms = new HashMap<String, Set>();
        if (nodeCheckInfo.isRecursive()) {
            if (gatherEntityMap == null || gatherEntityMap.getGatherEntitys() == null || gatherEntityMap.getGatherEntitys().isEmpty()) {
                asyncTaskLog.setMessage("\u6ca1\u6709\u53c2\u4e0e\u8282\u70b9\u68c0\u67e5\u7684\u5355\u4f4d\u3002");
                String objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
                logHelper.error(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u68c0\u67e5\u529f\u80fd\u51fa\u73b0\u9519\u8bef", "\u4e1a\u52a1\u9519\u8bef\uff1a\u53c2\u4e0e\u6c47\u603b\u7684\u5355\u4f4d\u627e\u5230");
                monitor.error("\u6ca1\u6709\u8282\u70b9\u68c0\u67e5\u7684\u5355\u4f4d", null, objectToJson);
                return null;
            }
            List gatherEntities = gatherEntityMap.getGatherEntitys();
            String recursiveUnit = gatherEntities.stream().map(String::valueOf).collect(Collectors.joining(";"));
            accessDimensionValueSet.setValue(entityDimension, (Object)recursiveUnit);
            DimensionCollection newDimension = DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)accessDimensionValueSet, (String)formScheme.getKey());
            MergeDataPermission mergeDataPermission = evaluator.mergeAccess(newDimension, formKeys, AuthType.READABLE, ignoreItems);
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
        }
        IEntityTable entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, entityViewDefine, nodeCheckInfo.getFormSchemeKey(), period);
        Set<String> accessCheckUnits = accessResources.stream().map(e -> ((DimensionValue)e.getMasterKey().get(entityDimension)).getValue()).collect(Collectors.toSet());
        HashSet allCheckUnits = new HashSet();
        accessCheckUnits.forEach(e -> allCheckUnits.addAll(Arrays.asList(e.split(";"))));
        nodeCheckResultInfo.setTotalCheckUnit(allCheckUnits.size());
        HashSet unCheckUnits = new HashSet();
        HashSet<String> gatherFormKey = new HashSet<String>();
        HashSet<NodeCheckResultItemInfo> resultItemInfos = new HashSet<NodeCheckResultItemInfo>();
        Map<String, IEntityTable> dimName2EntityTable = this.initEntityTableMap(formScheme, entityViewDefine, period);
        for (int formInfoIndex = 0; formInfoIndex < accessResources.size(); ++formInfoIndex) {
            if (monitor.isCancel()) {
                return nodeCheckResultInfo;
            }
            UnitPermission unitPermission = (UnitPermission)accessResources.get(formInfoIndex);
            Map dimensionValue = unitPermission.getMasterKey();
            List accessFormKeys = unitPermission.getResourceIds();
            gatherFormKey.addAll(accessFormKeys);
            List smallDimensionList = DimensionValueSetUtil.getDimensionSetList((Map)dimensionValue);
            for (Map smallDimension : smallDimensionList) {
                com.jiuqi.nr.data.engine.gather.NodeCheckResult result;
                DimensionValueSet smallDimensionSet = DimensionValueSetUtil.getDimensionValueSet((Map)smallDimension);
                Map<String, String> dimensionTitle = this.getDimensionTitle(dimName2EntityTable, smallDimensionSet);
                boolean isLeaf = this.isLeafEntity(entityTable, targetKey);
                if (isLeaf) continue;
                smallDimensionSet.clearValue(entityDimension);
                String periodCode = smallDimensionSet.getValue(periodDimension).toString();
                smallDimensionSet.clearValue(periodDimension);
                ArrayList gatherTableDefineList = new ArrayList();
                HashMap nodeCheckfieldMap = new HashMap();
                HashMap tableCache = new HashMap();
                HashSet warnTables = new HashSet();
                accessFormKeys.forEach(formKey -> gatherTableDefineList.addAll(GatherTableUtil.getGatherTables(formKey, nodeCheckfieldMap, tableCache, warnTables)));
                double formStartProgress = (double)formInfoIndex / (double)accessResources.size();
                double formEndProgress = (double)(formInfoIndex + 1) / (double)accessResources.size();
                if (gatherTableDefineList.isEmpty() || StringUtils.isEmpty((String)targetKey)) {
                    monitor.progressAndMessage(formEndProgress, "\u8282\u70b9\u68c0\u67e5\u4e2d");
                    continue;
                }
                DataGatherParallelMonitor parallelMonitor = new DataGatherParallelMonitor(monitor);
                parallelMonitor.setStartProgress(formStartProgress, formEndProgress);
                dataGather.setMonitor((IMonitor)parallelMonitor);
                condition.setFormSchemeKey(nodeCheckInfo.getFormSchemeKey());
                condition.setSourceDimensions(smallDimensionSet);
                condition.setTargetDimension(smallDimensionSet);
                condition.setUnitView(entityViewDefine);
                if (StringUtils.isNotEmpty((String)periodCode)) {
                    condition.setPeriodCode(periodCode);
                }
                condition.setRecursive(nodeCheckInfo.isRecursive());
                condition.setGatherDirection(GatherDirection.GATHER_TO_GROUP);
                condition.setGatherTables(gatherTableDefineList);
                BigDecimal errorRange = nodeCheckInfo.getErrorRange();
                if (errorRange == null) {
                    errorRange = this.nodeCheckOptionProvider.getNodeCheckTolerance(taskDefine.getKey());
                }
                condition.setPrecisionValue(errorRange);
                condition.setTaskKey(nodeCheckInfo.getTaskKey());
                condition.setDataSchemeKey(dataSchemeKey);
                if (!noAccessEntityForms.isEmpty()) {
                    NotGatherEntityValue notGatherEntityValue = new NotGatherEntityValue();
                    notGatherEntityValue.setEntityForms(noAccessEntityForms);
                    condition.setNotGatherEntityValue(notGatherEntityValue);
                }
                dataGather.setGatherCondition(condition);
                ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, nodeCheckInfo.getFormSchemeKey());
                executorContext.setEnv((IFmlExecEnvironment)environment);
                try {
                    result = dataGather.executeNodeCheck((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, targetKey);
                }
                catch (Exception e4) {
                    logHelper.error(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u68c0\u67e5\u5f15\u64ce\u5c42\u8c03\u7528", "\u8282\u70b9\u68c0\u67e5\u5f15\u64ce\u5c42\u51fa\u73b0\u5f02\u5e38" + e4.getMessage());
                    monitor.error(e4.getMessage(), e4);
                    throw new DataGatherException(e4.getMessage());
                }
                Map<String, List<CheckErrorItem>> collect = result.getErrorItems().stream().collect(Collectors.groupingBy(CheckErrorItem::getUnitTitle));
                for (Map.Entry<String, List<CheckErrorItem>> entry : collect.entrySet()) {
                    NodeCheckResultItemInfo nodeCheckResultItemInfo = new NodeCheckResultItemInfo();
                    nodeCheckResultItemInfo.setUnitTitle(entry.getKey());
                    nodeCheckResultItemInfo.setDimensionValue(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)entry.getValue().get(0).getDimension()));
                    ArrayList<NodeCheckResultItem> nodeCheckResultItems = new ArrayList<NodeCheckResultItem>();
                    entry.getValue().forEach(item -> {
                        NodeCheckResultItem nodeCheckResultItem = new NodeCheckResultItem();
                        nodeCheckResultItem.setFieldCode(item.getFieldCode());
                        nodeCheckResultItem.setFieldTitle(item.getFieldTitle());
                        nodeCheckResultItem.setParentValue(item.getParentValue());
                        nodeCheckResultItem.setChildValue(item.getChildValue());
                        nodeCheckResultItem.setMinusValue(item.getMinusValue());
                        nodeCheckResultItem.setUnitTitle(item.getUnitTitle());
                        nodeCheckResultItem.setUnitKey(item.getUnitKey());
                        nodeCheckResultItem.setDimensionTitle(dimensionTitle);
                        nodeCheckResultItem.setBizKeyOrder(item.getBizOrder());
                        nodeCheckResultItem.setRowKeys(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)item.getRowKeys()));
                        Map regionCache = (Map)nodeCheckfieldMap.get(item.getRegionKey());
                        if (regionCache != null) {
                            nodeCheckResultItem.setNodeCheckFieldMessage((NodeCheckFieldMessage)regionCache.get(item.getFieldKey()));
                        }
                        nodeCheckResultItems.add(nodeCheckResultItem);
                        unCheckUnits.add(item.getUnitKey());
                    });
                    nodeCheckResultItems.sort((arg0, arg1) -> {
                        String formOrder1;
                        String formOrder0 = arg0.getNodeCheckFieldMessage().getFormOrder();
                        if (!formOrder0.equals(formOrder1 = arg1.getNodeCheckFieldMessage().getFormOrder())) {
                            return formOrder0.compareTo(formOrder1);
                        }
                        String fieldOrder0 = arg0.getNodeCheckFieldMessage().getFieldOrder();
                        String fieldOrder1 = arg1.getNodeCheckFieldMessage().getFieldOrder();
                        return fieldOrder0.compareTo(fieldOrder1);
                    });
                    nodeCheckResultItemInfo.setNodeCheckResultItems(nodeCheckResultItems);
                    resultItemInfos.add(nodeCheckResultItemInfo);
                }
            }
        }
        nodeCheckResultInfo.setUnPassUnit(unCheckUnits.size());
        nodeCheckResultInfo.setResultItemInfos(resultItemInfos);
        if (monitor.isCancel()) {
            logHelper.info(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u68c0\u67e5\u53d6\u6d88\u6267\u884c", "\u8282\u70b9\u68c0\u67e5\u53d6\u6d88\u5b8c\u6210");
            return nodeCheckResultInfo;
        }
        if (nodeCheckResultInfo.getUnPassUnit() == 0) {
            monitor.finish("\u8282\u70b9\u68c0\u67e5\u65e0\u8bef", null);
        } else {
            monitor.error("\u8282\u70b9\u68c0\u67e5\u5b58\u5728\u9519\u8bef\uff0c\u70b9\u51fb\u67e5\u770b\u7ed3\u679c\u3002", null, null);
        }
        logHelper.info(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u68c0\u67e5\u670d\u52a1\u5c42\u7ed3\u675f", "\u8282\u70b9\u68c0\u67e5\u5b8c\u6210\uff01");
        monitorEventParam.setFormKeys(gatherFormKey);
        monitor.executeAfter(monitorEventParam);
        return nodeCheckResultInfo;
    }

    private NodeCheckResult nodeCheck(NodeCheckInfo nodeCheckInfo, IEntityTable entityTable, IGatherServiceMonitor monitor) {
        NodeCheckResult nodeCheckResultInfo = new NodeCheckResult();
        HashSet<NodeCheckResultItemInfo> nodeCheckResult = new HashSet<NodeCheckResultItemInfo>();
        nodeCheckResultInfo.setResultItemInfos(nodeCheckResult);
        DimensionValueSet dimensionValueSet = nodeCheckInfo.getDimensionValueSet();
        String targetKey = String.valueOf(dimensionValueSet.getValue(nodeCheckInfo.getDwDimName()));
        String period = String.valueOf(dimensionValueSet.getValue(nodeCheckInfo.getPeriodDimName()));
        DimensionValueSet accessDimensionValueSet = new DimensionValueSet(dimensionValueSet);
        Map singleSelectDimCols = this.gatherTempTableHandler.getSingleSelectDimCols(nodeCheckInfo.getDataSchemeKey(), nodeCheckInfo.getTaskKey());
        for (String singleDimName : singleSelectDimCols.keySet()) {
            accessDimensionValueSet.clearValue(singleDimName);
        }
        if (StringUtils.isEmpty((String)targetKey) || targetKey.contains(";")) {
            return null;
        }
        nodeCheckResultInfo.setUnitKey(targetKey);
        nodeCheckResultInfo.setDimensionValueSet(dimensionValueSet);
        boolean isLeaf = this.isLeafEntity(entityTable, targetKey);
        if (isLeaf) {
            nodeCheckResultInfo.setLeafUnit(true);
            return nodeCheckResultInfo;
        }
        List<Object> formKeys = new ArrayList();
        if (StringUtils.isNotEmpty((String)nodeCheckInfo.getFormKeys())) {
            formKeys = Stream.of(nodeCheckInfo.getFormKeys().split(";")).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(formKeys)) {
            formKeys = this.runtimeView.queryAllFormKeysByFormScheme(nodeCheckInfo.getFormSchemeKey());
        }
        DimensionCollection dimensionCollection = DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)dimensionValueSet, (String)nodeCheckInfo.getFormSchemeKey());
        Set<String> ignoreItems = nodeCheckInfo.getIgnoreAccessItems();
        EvaluatorParam evaluatorParam = new EvaluatorParam();
        evaluatorParam.setTaskId(nodeCheckInfo.getTaskKey());
        evaluatorParam.setFormSchemeId(nodeCheckInfo.getFormSchemeKey());
        DataPermissionEvaluator evaluator = this.providerStore.getDataPermissionEvaluatorFactory().createEvaluator(evaluatorParam, dimensionCollection, formKeys);
        MergeDataPermission dataPermission = evaluator.mergeAccess(dimensionCollection, formKeys, AuthType.READABLE);
        List accessResources = dataPermission.getAccessResources();
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(nodeCheckInfo.getFormSchemeKey());
        IDataGather dataGather = this.dataGatherProvider.newDataGather(queryEnvironment);
        GatherEntityMap gatherEntityMap = null;
        GatherCondition condition = new GatherCondition();
        condition.setFormSchemeKey(nodeCheckInfo.getFormSchemeKey());
        dataGather.setGatherCondition(condition);
        if (nodeCheckInfo.isRecursive()) {
            try {
                gatherEntityMap = dataGather.getGatherEntityMap(targetKey, false, nodeCheckInfo.isRecursive(), nodeCheckInfo.getExecutorContext(), nodeCheckInfo.getEntityViewDefine(), entityTable);
            }
            catch (Exception e2) {
                monitor.error("\u83b7\u53d6\u68c0\u67e5\u5355\u4f4d\u548c\u4e0b\u7ea7\u5931\u8d25\uff01", e2);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e2.getMessage(), (Object)e2);
            }
        }
        MonitorEventParam monitorEventParam = new MonitorEventParam(targetKey, nodeCheckInfo);
        monitorEventParam.setGatherEntityMap(gatherEntityMap);
        monitor.executeBefore(monitorEventParam);
        HashMap<String, Set> noAccessEntityForms = new HashMap<String, Set>();
        if (nodeCheckInfo.isRecursive()) {
            if (gatherEntityMap == null || gatherEntityMap.getGatherEntitys() == null || gatherEntityMap.getGatherEntitys().isEmpty()) {
                return null;
            }
            List gatherEntities = gatherEntityMap.getGatherEntitys();
            String recursiveUnit = gatherEntities.stream().map(String::valueOf).collect(Collectors.joining(";"));
            accessDimensionValueSet.setValue(nodeCheckInfo.getDwDimName(), (Object)recursiveUnit);
            DimensionCollection newDimension = DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)accessDimensionValueSet, (String)nodeCheckInfo.getFormSchemeKey());
            MergeDataPermission mergeDataPermission = evaluator.mergeAccess(newDimension, formKeys, AuthType.READABLE, ignoreItems);
            List unAccessResources = mergeDataPermission.getUnAccessResources();
            for (UnitPermission noAccessInfo : unAccessResources) {
                Map masterKey = noAccessInfo.getMasterKey();
                List resourceIds = noAccessInfo.getResourceIds();
                String unitKeys = ((DimensionValue)masterKey.get(nodeCheckInfo.getDwDimName())).getValue();
                ArrayList<String> unitKeyList = new ArrayList<String>(Arrays.asList(unitKeys.split(";")));
                for (String unitKey : unitKeyList) {
                    noAccessEntityForms.computeIfAbsent(unitKey, k -> new HashSet()).addAll(resourceIds);
                }
            }
        }
        Set<String> accessCheckUnits = accessResources.stream().map(e -> ((DimensionValue)e.getMasterKey().get(nodeCheckInfo.getDwDimName())).getValue()).collect(Collectors.toSet());
        HashSet allCheckUnits = new HashSet();
        accessCheckUnits.forEach(e -> allCheckUnits.addAll(Arrays.asList(e.split(";"))));
        nodeCheckResultInfo.setTotalCheckUnit(allCheckUnits.size());
        HashSet unCheckUnits = new HashSet();
        HashSet<String> gatherFormKey = new HashSet<String>();
        HashSet<NodeCheckResultItemInfo> resultItemInfos = new HashSet<NodeCheckResultItemInfo>();
        Map<String, IEntityTable> dimName2EntityTable = this.initEntityTableMap(nodeCheckInfo.getFormSchemeDefine(), nodeCheckInfo.getEntityViewDefine(), period);
        for (int formInfoIndex = 0; formInfoIndex < accessResources.size(); ++formInfoIndex) {
            UnitPermission unitPermission = (UnitPermission)accessResources.get(formInfoIndex);
            Map dimensionValue = unitPermission.getMasterKey();
            List accessFormKeys = unitPermission.getResourceIds();
            gatherFormKey.addAll(accessFormKeys);
            List smallDimensionList = DimensionValueSetUtil.getDimensionSetList((Map)dimensionValue);
            for (Map smallDimension : smallDimensionList) {
                com.jiuqi.nr.data.engine.gather.NodeCheckResult result;
                DimensionValueSet smallDimensionSet = DimensionValueSetUtil.getDimensionValueSet((Map)smallDimension);
                Map<String, String> dimensionTitle = this.getDimensionTitle(dimName2EntityTable, smallDimensionSet);
                smallDimensionSet.clearValue(nodeCheckInfo.getDwDimName());
                String periodCode = smallDimensionSet.getValue(nodeCheckInfo.getPeriodDimName()).toString();
                smallDimensionSet.clearValue(nodeCheckInfo.getPeriodDimName());
                ArrayList gatherTableDefineList = new ArrayList();
                HashMap nodeCheckfieldMap = new HashMap();
                HashMap tableCache = new HashMap();
                HashSet warnTables = new HashSet();
                accessFormKeys.forEach(formKey -> gatherTableDefineList.addAll(GatherTableUtil.getGatherTables(formKey, nodeCheckfieldMap, tableCache, warnTables)));
                if (gatherTableDefineList.isEmpty() || StringUtils.isEmpty((String)targetKey)) continue;
                DataGatherParallelMonitor parallelMonitor = new DataGatherParallelMonitor(monitor);
                dataGather.setMonitor((IMonitor)parallelMonitor);
                condition.setFormSchemeKey(nodeCheckInfo.getFormSchemeKey());
                condition.setSourceDimensions(smallDimensionSet);
                condition.setTargetDimension(smallDimensionSet);
                condition.setUnitView(nodeCheckInfo.getEntityViewDefine());
                if (StringUtils.isNotEmpty((String)periodCode)) {
                    condition.setPeriodCode(periodCode);
                }
                condition.setRecursive(nodeCheckInfo.isRecursive());
                condition.setGatherDirection(GatherDirection.GATHER_TO_GROUP);
                condition.setGatherTables(gatherTableDefineList);
                BigDecimal errorRange = nodeCheckInfo.getErrorRange();
                if (errorRange == null) {
                    errorRange = this.nodeCheckOptionProvider.getNodeCheckTolerance(nodeCheckInfo.getTaskKey());
                }
                condition.setPrecisionValue(errorRange);
                condition.setTaskKey(nodeCheckInfo.getTaskKey());
                condition.setDataSchemeKey(nodeCheckInfo.getDataSchemeKey());
                if (!noAccessEntityForms.isEmpty()) {
                    NotGatherEntityValue notGatherEntityValue = new NotGatherEntityValue();
                    notGatherEntityValue.setEntityForms(noAccessEntityForms);
                    condition.setNotGatherEntityValue(notGatherEntityValue);
                }
                dataGather.setGatherCondition(condition);
                try {
                    result = dataGather.executeNodeCheck(nodeCheckInfo.getExecutorContext(), targetKey);
                }
                catch (Exception e3) {
                    monitor.error(e3.getMessage(), e3);
                    throw new DataGatherException(e3.getMessage());
                }
                if (monitor.isCancel()) {
                    return null;
                }
                Map<String, List<CheckErrorItem>> collect = result.getErrorItems().stream().collect(Collectors.groupingBy(CheckErrorItem::getUnitTitle));
                for (Map.Entry<String, List<CheckErrorItem>> entry : collect.entrySet()) {
                    NodeCheckResultItemInfo nodeCheckResultItemInfo = new NodeCheckResultItemInfo();
                    nodeCheckResultItemInfo.setUnitTitle(entry.getKey());
                    nodeCheckResultItemInfo.setDimensionValue(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)entry.getValue().get(0).getDimension()));
                    ArrayList<NodeCheckResultItem> nodeCheckResultItems = new ArrayList<NodeCheckResultItem>();
                    entry.getValue().forEach(item -> {
                        NodeCheckResultItem nodeCheckResultItem = new NodeCheckResultItem();
                        nodeCheckResultItem.setFieldCode(item.getFieldCode());
                        nodeCheckResultItem.setFieldTitle(item.getFieldTitle());
                        nodeCheckResultItem.setParentValue(item.getParentValue());
                        nodeCheckResultItem.setChildValue(item.getChildValue());
                        nodeCheckResultItem.setMinusValue(item.getMinusValue());
                        nodeCheckResultItem.setUnitTitle(item.getUnitTitle());
                        nodeCheckResultItem.setUnitKey(item.getUnitKey());
                        nodeCheckResultItem.setDimensionTitle(dimensionTitle);
                        nodeCheckResultItem.setRowKeys(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)item.getRowKeys()));
                        nodeCheckResultItem.setBizKeyOrder(item.getBizOrder());
                        Map regionCache = (Map)nodeCheckfieldMap.get(item.getRegionKey());
                        if (regionCache != null) {
                            nodeCheckResultItem.setNodeCheckFieldMessage((NodeCheckFieldMessage)regionCache.get(item.getFieldKey()));
                        }
                        nodeCheckResultItems.add(nodeCheckResultItem);
                        unCheckUnits.add(item.getUnitKey());
                    });
                    nodeCheckResultItems.sort((arg0, arg1) -> {
                        String formOrder1;
                        String formOrder0 = arg0.getNodeCheckFieldMessage().getFormOrder();
                        if (!formOrder0.equals(formOrder1 = arg1.getNodeCheckFieldMessage().getFormOrder())) {
                            return formOrder0.compareTo(formOrder1);
                        }
                        String fieldOrder0 = arg0.getNodeCheckFieldMessage().getFieldOrder();
                        String fieldOrder1 = arg1.getNodeCheckFieldMessage().getFieldOrder();
                        return fieldOrder0.compareTo(fieldOrder1);
                    });
                    nodeCheckResultItemInfo.setNodeCheckResultItems(nodeCheckResultItems);
                    resultItemInfos.add(nodeCheckResultItemInfo);
                }
            }
        }
        nodeCheckResultInfo.setUnPassUnit(unCheckUnits.size());
        nodeCheckResultInfo.setResultItemInfos(resultItemInfos);
        monitorEventParam.setFormKeys(gatherFormKey);
        monitor.executeAfter(monitorEventParam);
        return nodeCheckResultInfo;
    }

    @Override
    public List<NodeCheckResult> batchNodeCheck(NodeCheckParam nodeCheckParam) {
        return this.batchNodeCheck(nodeCheckParam, new DefaultMonitor());
    }

    @Override
    public List<NodeCheckResult> batchNodeCheck(NodeCheckParam nodeCheckParam, IGatherServiceMonitor monitor) {
        FormSchemeDefine formScheme;
        NodeCheckInfo nodeCheckInfo = new NodeCheckInfo(nodeCheckParam);
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("\u6c47\u603b\u670d\u52a1", OperLevel.USER_OPER);
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(nodeCheckParam.getTaskKey());
        nodeCheckInfo.setDataSchemeKey(taskDefine.getDataScheme());
        try {
            formScheme = this.runtimeView.getFormScheme(nodeCheckParam.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new DataGatherException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{nodeCheckParam.getFormSchemeKey()});
        }
        nodeCheckInfo.setFormSchemeDefine(formScheme);
        String entityId = formScheme.getDw();
        DsContext dsContext = DsContextHolder.getDsContext();
        if (dsContext != null && StringUtils.isNotEmpty((String)dsContext.getContextEntityId())) {
            entityId = dsContext.getContextEntityId();
        }
        String entityDimension = this.entityMetaService.getDimensionName(entityId);
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimension = periodAdapter.getPeriodDimensionName();
        if (null == entityDimension) {
            throw new DataGatherException("\u672a\u627e\u5230\u5355\u4f4d\u4e3b\u4f53");
        }
        nodeCheckInfo.setDwDimName(entityDimension);
        nodeCheckInfo.setPeriodDimName(periodDimension);
        EntityViewDefine entityViewDefine = entityId.equals(formScheme.getDw()) ? this.runtimeView.getViewByFormSchemeKey(formScheme.getKey()) : this.entityViewRunTimeController.buildEntityView(entityId, dsContext.getContextFilterExpression());
        nodeCheckInfo.setEntityViewDefine(entityViewDefine);
        DimensionCollection dimensionCollection = nodeCheckParam.getDimensionCollection();
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        ArrayList<NodeCheckResult> results = new ArrayList<NodeCheckResult>();
        if (CollectionUtils.isEmpty(dimensionCombinations)) {
            return results;
        }
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        for (DimensionCombination dimensionCombine : dimensionCombinations) {
            dimensionValueSets.add(dimensionCombine.toDimensionValueSet());
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensionValueSets);
        String period = String.valueOf(dimensionValueSet.getValue(periodDimension));
        Object targetKeyValue = dimensionValueSet.getValue(entityDimension);
        Collection targetKeys = new ArrayList<String>();
        if (targetKeyValue instanceof Collection) {
            targetKeys = (Collection)targetKeyValue;
        } else {
            targetKeys.add(targetKeyValue.toString());
        }
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(entityId, targetKeys.toArray(new String[0]));
        logDimensionCollection.setPeriod(formScheme.getDateTime(), period);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, nodeCheckParam.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        nodeCheckInfo.setExecutorContext((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext);
        Map<DimsObject, List<String>> dims2DwCodesMap = this.getDims2DwCodesMap(entityDimension, dimensionValueSets);
        monitor.progressAndMessage(0.1, "\u6279\u91cf\u8282\u70b9\u68c0\u67e5\u5f00\u59cb");
        IEntityTable allDWEntityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, entityViewDefine, nodeCheckParam.getFormSchemeKey(), period);
        double curPro = 0.1;
        if (!nodeCheckParam.isRecursive()) {
            double i = 0.8 / (double)dimensionValueSets.size();
            for (DimensionValueSet valueSet : dimensionValueSets) {
                nodeCheckInfo.setDimensionValueSet(valueSet);
                NodeCheckResult nodeCheckResult = this.nodeCheck(nodeCheckInfo, allDWEntityTable, monitor);
                if (monitor.isCancel()) {
                    return null;
                }
                if (nodeCheckResult != null) {
                    results.add(nodeCheckResult);
                }
                monitor.progressAndMessage(curPro += i, "");
            }
        } else {
            double split = 0.8 / (double)dims2DwCodesMap.keySet().size();
            for (Map.Entry<DimsObject, List<String>> entry : dims2DwCodesMap.entrySet()) {
                DimsObject dimsObject = entry.getKey();
                List<String> dwCodes = entry.getValue();
                IEntityTable entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, entityViewDefine, entityDimension, dwCodes, period, nodeCheckParam.getFormSchemeKey(), true);
                List rootRows = entityTable.getRootRows();
                Set collect = rootRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet());
                for (IEntityRow row : rootRows) {
                    String[] parentsInPath;
                    for (String parentKey : parentsInPath = row.getParentsEntityKeyDataPath()) {
                        if (!collect.contains(parentKey)) continue;
                        collect.remove(row.getEntityKeyData());
                    }
                }
                ArrayList<NodeCheckResult> nodeCheckResults = new ArrayList<NodeCheckResult>();
                DimensionValueSet dimensionValueSet1 = this.getDimensionValueSet(dimsObject);
                double i = split / (double)collect.size();
                for (String dwCode : collect) {
                    dimensionValueSet1.setValue(entityDimension, (Object)dwCode);
                    nodeCheckInfo.setDimensionValueSet(dimensionValueSet1);
                    NodeCheckResult nodeCheckResult = this.nodeCheck(nodeCheckInfo, allDWEntityTable, monitor);
                    if (monitor.isCancel()) {
                        return null;
                    }
                    if (nodeCheckResult != null) {
                        nodeCheckResults.add(nodeCheckResult);
                    }
                    monitor.progressAndMessage(curPro += i, "");
                }
                HashMap<String, NodeCheckResultItemInfo> resultItemInfoMap = new HashMap<String, NodeCheckResultItemInfo>();
                for (NodeCheckResult nodeCheckResult : nodeCheckResults) {
                    Set<NodeCheckResultItemInfo> resultItemInfos = nodeCheckResult.getResultItemInfos();
                    for (NodeCheckResultItemInfo resultItemInfo : resultItemInfos) {
                        String unitKey = resultItemInfo.getNodeCheckResultItems().get(0).getUnitKey();
                        resultItemInfoMap.put(unitKey, resultItemInfo);
                    }
                }
                List allRows = entityTable.getAllRows();
                for (IEntityRow row : allRows) {
                    NodeCheckResult nodeCheckResult = new NodeCheckResult();
                    dimensionValueSet1.setValue(entityDimension, (Object)row.getEntityKeyData());
                    nodeCheckResult.setDimensionValueSet(dimensionValueSet1);
                    String unitKey = row.getEntityKeyData();
                    nodeCheckResult.setUnitKey(unitKey);
                    if (row.isLeaf()) {
                        nodeCheckResult.setLeafUnit(true);
                        nodeCheckResult.setUnPassUnit(0);
                    } else {
                        nodeCheckResult.setLeafUnit(false);
                        List childRows = entityTable.getAllChildRows(unitKey);
                        childRows.add(row);
                        HashSet<NodeCheckResultItemInfo> resultItemInfos = new HashSet<NodeCheckResultItemInfo>();
                        for (IEntityRow childRow : childRows) {
                            NodeCheckResultItemInfo nodeCheckResultItemInfo = (NodeCheckResultItemInfo)resultItemInfoMap.get(childRow.getEntityKeyData());
                            if (nodeCheckResultItemInfo == null) continue;
                            resultItemInfos.add(nodeCheckResultItemInfo);
                        }
                        nodeCheckResult.setUnPassUnit(resultItemInfos.size());
                        nodeCheckResult.setResultItemInfos(resultItemInfos);
                    }
                    results.add(nodeCheckResult);
                }
            }
        }
        monitor.finish("\u6279\u91cf\u8282\u70b9\u68c0\u67e5\u5b8c\u6210", null);
        logHelper.info(nodeCheckParam.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u68c0\u67e5\u670d\u52a1\u5c42\u7ed3\u675f", "\u6279\u91cf\u8282\u70b9\u68c0\u67e5\u5b8c\u6210\uff01");
        return results;
    }

    private DimensionValueSet getDimensionValueSet(DimsObject dimsObject) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (int i = 0; i < dimsObject.getNames().size(); ++i) {
            dimensionValueSet.setValue(dimsObject.getNames().get(i), dimsObject.getValues().get(i));
        }
        return dimensionValueSet;
    }

    private Map<DimsObject, List<String>> getDims2DwCodesMap(String dwDimName, List<DimensionValueSet> dimensionValueSets) {
        ArrayList<DimCompareObject> allCompareObjects = new ArrayList<DimCompareObject>();
        for (DimensionValueSet dimensionValueSet : dimensionValueSets) {
            DimCompareObject dimCompareObject = new DimCompareObject();
            DimsObject dimsObject = new DimsObject();
            dimCompareObject.setDimsObject(dimsObject);
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                String name = dimensionValueSet.getName(i);
                if (name.equals(dwDimName)) {
                    dimCompareObject.setDwCode(dimensionValueSet.getValue(i).toString());
                    continue;
                }
                dimsObject.getNames().add(name);
                dimsObject.getValues().add(dimensionValueSet.getValue(i));
            }
            allCompareObjects.add(dimCompareObject);
        }
        HashMap<DimsObject, List<String>> dim2DwCode = new HashMap<DimsObject, List<String>>();
        for (DimCompareObject dimCompareObject : allCompareObjects) {
            dim2DwCode.computeIfAbsent(dimCompareObject.getDimsObject(), k -> new ArrayList()).add(dimCompareObject.getDwCode());
        }
        return dim2DwCode;
    }

    private IEntityTable getEntityTable(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, EntityViewDefine entityViewDefine, String dwDimName, List<String> targetKeys, String periodCode, String formSchemeKey, boolean markLeaf) {
        IEntityTable entityTable;
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        DimensionValueSet masterKeys = new DimensionValueSet();
        if (!StringUtils.isEmpty((String)periodCode)) {
            masterKeys.setValue("DATATIME", (Object)periodCode);
        }
        if (!CollectionUtils.isEmpty(targetKeys)) {
            masterKeys.setValue(dwDimName, targetKeys);
        }
        entityQuery.setMasterKeys(masterKeys);
        entityQuery.setAuthorityOperations(AuthorityType.Read);
        if (markLeaf) {
            entityQuery.markLeaf();
        }
        executorContext.setVarDimensionValueSet(masterKeys);
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        executorContext.setPeriodView(formScheme.getDateTime());
        try {
            entityTable = entityQuery.executeFullBuild((IContext)executorContext);
        }
        catch (Exception e) {
            throw new DataGatherException(e.getMessage());
        }
        return entityTable;
    }

    private IEntityTable getEntityTable(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, EntityViewDefine unitView, String formSchemeKey, String periodCode) {
        return this.getEntityTable(executorContext, unitView, null, null, periodCode, formSchemeKey, false);
    }

    private Map<String, IEntityTable> initEntityTableMap(FormSchemeDefine formSchemeDefine, EntityViewDefine dwEntityViewDefine, String periodCode) {
        HashMap<String, IEntityTable> dimName2EntityTable = new HashMap<String, IEntityTable>();
        String dims = formSchemeDefine.getDims();
        Set<Object> entitySet = new HashSet<String>();
        if (StringUtils.isNotEmpty((String)dims)) {
            String[] entityIds = dims.split(";");
            entitySet = Stream.of(entityIds).filter(StringUtils::isNotEmpty).collect(Collectors.toSet());
        }
        entitySet.add(dwEntityViewDefine.getEntityId());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String dimensionName = null;
        for (String string : entitySet) {
            if (periodAdapter.isPeriodEntity(string)) {
                IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(string);
                dimensionName = periodEntity.getDimensionName();
            } else {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(string);
                dimensionName = entityDefine.getDimensionName();
            }
            if (dimensionName == null) continue;
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            EntityViewDefine entityViewDefine = string.equals(formSchemeDefine.getDw()) ? dwEntityViewDefine : this.entityViewRunTimeController.buildEntityView(string);
            IEntityTable entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, entityViewDefine, formSchemeDefine.getKey(), periodCode);
            dimName2EntityTable.put(dimensionName, entityTable);
        }
        return dimName2EntityTable;
    }

    private Map<String, String> getDimensionTitle(Map<String, IEntityTable> map, DimensionValueSet dimensionValueSet) {
        HashMap<String, String> titleMap = new HashMap<String, String>();
        for (String dimName : map.keySet()) {
            String title;
            String dimValue = dimensionValueSet.getValue(dimName).toString();
            IEntityTable entityTable = map.get(dimName);
            IEntityRow entityRow = entityTable.findByEntityKey(dimValue);
            if (entityRow == null) {
                entityRow = entityTable.findByCode(dimValue);
            }
            if ((title = entityRow.getTitle()) == null) continue;
            titleMap.put(dimName, title);
        }
        return titleMap;
    }

    private boolean isLeafEntity(IEntityTable entityTable, String targetKey) {
        if (entityTable == null) {
            return true;
        }
        return CollectionUtils.isEmpty(entityTable.getChildRows(targetKey));
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

    public void setEntityMetaService(IEntityMetaService entityMetaService) {
        this.entityMetaService = entityMetaService;
    }

    public void setPeriodEngineService(PeriodEngineService periodEngineService) {
        this.periodEngineService = periodEngineService;
    }

    public void setDataServiceLoggerFactory(DataServiceLoggerFactory dataServiceLoggerFactory) {
        this.dataServiceLoggerFactory = dataServiceLoggerFactory;
    }

    public void setEntityDataService(IEntityDataService entityDataService) {
        this.entityDataService = entityDataService;
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public void setNodeCheckOptionProvider(NodeCheckOptionProvider nodeCheckOptionProvider) {
        this.nodeCheckOptionProvider = nodeCheckOptionProvider;
    }

    public void setGatherTempTableHandler(GatherTempTableHandler gatherTempTableHandler) {
        this.gatherTempTableHandler = gatherTempTableHandler;
    }

    public void setDimCollectionBuildUtil(DimCollectionBuildUtil dimCollectionBuildUtil) {
        this.dimCollectionBuildUtil = dimCollectionBuildUtil;
    }
}

