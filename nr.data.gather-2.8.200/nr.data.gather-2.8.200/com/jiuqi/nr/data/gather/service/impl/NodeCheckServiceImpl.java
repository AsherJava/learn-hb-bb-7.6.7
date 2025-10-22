/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
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
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$NoAccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
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
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.gather.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
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
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.engine.gather.CheckErrorItem;
import com.jiuqi.nr.data.engine.gather.GatherCondition;
import com.jiuqi.nr.data.engine.gather.GatherDirection;
import com.jiuqi.nr.data.engine.gather.GatherEntityMap;
import com.jiuqi.nr.data.engine.gather.GatherTempTableHandler;
import com.jiuqi.nr.data.engine.gather.IDataGather;
import com.jiuqi.nr.data.engine.gather.IDataGatherProvider;
import com.jiuqi.nr.data.engine.gather.NodeCheckResult;
import com.jiuqi.nr.data.engine.gather.NotGatherEntityValue;
import com.jiuqi.nr.data.gather.bean.NodeCheckFieldMessage;
import com.jiuqi.nr.data.gather.bean.NodeCheckParam;
import com.jiuqi.nr.data.gather.bean.NodeCheckResultInfo;
import com.jiuqi.nr.data.gather.bean.NodeCheckResultItem;
import com.jiuqi.nr.data.gather.exception.DataGatherException;
import com.jiuqi.nr.data.gather.listener.DataGatherParallelMonitor;
import com.jiuqi.nr.data.gather.service.INodeCheckService;
import com.jiuqi.nr.data.gather.util.GatherTableUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service(value="nodeCheckService")
public class NodeCheckServiceImpl
implements INodeCheckService {
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
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private GatherTempTableHandler gatherTempTableHandler;
    private final BiFunction<List<NodeCheckResultItem>, List<NodeCheckResultItem>, List<NodeCheckResultItem>> nodeCheckResultMerge = (oldValue, newValue) -> {
        newValue.addAll(oldValue);
        return newValue;
    };

    @Override
    public NodeCheckResultInfo nodeCheck(NodeCheckParam nodeCheckParam) {
        return this.asyncNodeCheck(nodeCheckParam, null);
    }

    @Override
    public NodeCheckResultInfo asyncNodeCheck(NodeCheckParam nodeCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
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
        NodeCheckResultInfo nodeCheckResultInfo = new NodeCheckResultInfo();
        HashMap<String, List<NodeCheckResultItem>> nodeCheckResult = new HashMap<String, List<NodeCheckResultItem>>();
        nodeCheckResultInfo.setNodeCheckResult(nodeCheckResult);
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
        logHelper.info(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u68c0\u67e5\u670d\u52a1\u5c42\u5f00\u59cb", "\u8282\u70b9\u68c0\u67e5\u5f00\u59cb");
        List<Object> formKeys = new ArrayList();
        if (StringUtils.isNotEmpty((String)nodeCheckInfo.getFormKeys())) {
            formKeys = Stream.of(nodeCheckInfo.getFormKeys().split(";")).collect(Collectors.toList());
        }
        IDataAccessFormService dataAccessFormService = this.dataAccessServiceProvider.getDataAccessFormService();
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(dimensionCollection);
        accessFormParam.setTaskKey(nodeCheckInfo.getTaskKey());
        accessFormParam.setIgnoreAccessItems(ignoreItems);
        accessFormParam.setFormSchemeKey(nodeCheckInfo.getFormSchemeKey());
        accessFormParam.setFormKeys(formKeys);
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_READ);
        DimensionAccessFormInfo dimensionAccessInfos = dataAccessFormService.getBatchAccessForms(accessFormParam);
        List accessFormInfos = dimensionAccessInfos.getAccessForms();
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
                if (asyncTaskMonitor != null) {
                    asyncTaskMonitor.error("\u83b7\u53d6\u68c0\u67e5\u5355\u4f4d\u548c\u4e0b\u7ea7\u5931\u8d25\uff01", (Throwable)e3);
                }
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e3.getMessage(), e3);
            }
        }
        HashMap noAccessEntityForms = new HashMap();
        if (nodeCheckInfo.isRecursive()) {
            if (gatherEntityMap == null || gatherEntityMap.getGatherEntitys() == null || gatherEntityMap.getGatherEntitys().isEmpty()) {
                asyncTaskLog.setMessage("\u6ca1\u6709\u53c2\u4e0e\u8282\u70b9\u68c0\u67e5\u7684\u5355\u4f4d\u3002");
                String objectToJson = JsonUtil.objectToJson((Object)asyncTaskLog);
                logHelper.error(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u68c0\u67e5\u529f\u80fd\u51fa\u73b0\u9519\u8bef", "\u4e1a\u52a1\u9519\u8bef\uff1a\u53c2\u4e0e\u6c47\u603b\u7684\u5355\u4f4d\u627e\u5230");
                if (asyncTaskMonitor != null) {
                    asyncTaskMonitor.error("\u6ca1\u6709\u8282\u70b9\u68c0\u67e5\u7684\u5355\u4f4d", null, objectToJson);
                }
                return null;
            }
            List gatherEntities = gatherEntityMap.getGatherEntitys();
            String recursiveUnit = gatherEntities.stream().map(String::valueOf).collect(Collectors.joining(";"));
            accessDimensionValueSet.setValue(entityDimension, (Object)recursiveUnit);
            accessFormParam.setCollectionMasterKey(DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)accessDimensionValueSet, (String)formScheme.getKey()));
            DimensionAccessFormInfo dimensionAccessFormInfos = dataAccessFormService.getBatchAccessForms(accessFormParam);
            List noAccessFormInfo = dimensionAccessFormInfos.getNoAccessForms();
            for (DimensionAccessFormInfo.NoAccessFormInfo noAccessInfo : noAccessFormInfo) {
                String formKey2 = noAccessInfo.getFormKey();
                Map dimensionValue = noAccessInfo.getDimensions();
                String unitKey = ((DimensionValue)dimensionValue.get(entityDimension)).getValue();
                Set<String> formKeySet = null;
                if (noAccessEntityForms.containsKey(unitKey)) {
                    formKeySet = (Set)noAccessEntityForms.get(unitKey);
                    formKeySet.add(formKey2);
                    continue;
                }
                formKeySet = new HashSet();
                formKeySet.add(formKey2);
                noAccessEntityForms.put(unitKey, formKeySet);
            }
        }
        IEntityTable entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, entityViewDefine, nodeCheckInfo.getFormSchemeKey(), period);
        ArrayList<Map> subDimensionValueList = new ArrayList<Map>();
        ArrayList allDimensionValueList = new ArrayList();
        Set accessCheckUnits = accessFormInfos.stream().map(e -> ((DimensionValue)e.getDimensions().get(entityDimension)).getValue()).collect(Collectors.toSet());
        HashSet allCheckUnits = new HashSet();
        accessCheckUnits.stream().forEach(e -> allCheckUnits.addAll(Arrays.asList(e.split(";"))));
        nodeCheckResultInfo.setTotalCheckUnit(allCheckUnits.size());
        HashSet<String> unCheckUnits = new HashSet<String>();
        for (int formInfoIndex = 0; formInfoIndex < accessFormInfos.size(); ++formInfoIndex) {
            if (asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
                return nodeCheckResultInfo;
            }
            DimensionAccessFormInfo.AccessFormInfo dimensionValueFormInfo = (DimensionAccessFormInfo.AccessFormInfo)accessFormInfos.get(formInfoIndex);
            Map dimensionValue = dimensionValueFormInfo.getDimensions();
            List accessFormKeys = dimensionValueFormInfo.getFormKeys();
            List smallDimensionList = DimensionValueSetUtil.getDimensionSetList((Map)dimensionValue);
            subDimensionValueList.addAll(smallDimensionList);
            allDimensionValueList.addAll(DimensionValueSetUtil.getDimensionValueSetList((Map)dimensionValue));
            for (Map smallDimension : smallDimensionList) {
                String value;
                DimensionValueSet smallDimensionSet = DimensionValueSetUtil.getDimensionValueSet((Map)smallDimension);
                Map<String, String> dimensionTitle = this.getDimensionTitle(dsContext, formScheme, smallDimensionSet, periodDimension, period);
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
                double formStartProgress = (double)formInfoIndex / (double)accessFormInfos.size();
                double formEndProgress = (double)(formInfoIndex + 1) / (double)accessFormInfos.size();
                if (gatherTableDefineList.isEmpty() || StringUtils.isEmpty((String)targetKey)) {
                    if (asyncTaskMonitor == null) continue;
                    asyncTaskMonitor.progressAndMessage(formEndProgress, "\u8282\u70b9\u68c0\u67e5\u4e2d");
                    continue;
                }
                DataGatherParallelMonitor monitor = new DataGatherParallelMonitor(asyncTaskMonitor);
                monitor.setStartProgress(formStartProgress, formEndProgress);
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
                if (errorRange == null && StringUtils.isNotEmpty((String)(value = this.iTaskOptionController.getValue(taskDefine.getKey(), "NODE_CHECK_TOLERANCE")))) {
                    errorRange = new BigDecimal(value);
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
                dataGather.setMonitor((IMonitor)monitor);
                ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, nodeCheckInfo.getFormSchemeKey());
                executorContext.setEnv((IFmlExecEnvironment)environment);
                NodeCheckResult result = null;
                logHelper.info(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u68c0\u67e5\u5f15\u64ce\u5c42\u8c03\u7528", "\u5f15\u64ce\u5c42\u8c03\u7528\u5f00\u59cb");
                try {
                    result = dataGather.executeNodeCheck((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, targetKey);
                }
                catch (Exception e4) {
                    logHelper.error(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u68c0\u67e5\u5f15\u64ce\u5c42\u8c03\u7528", "\u8282\u70b9\u68c0\u67e5\u5f15\u64ce\u5c42\u51fa\u73b0\u5f02\u5e38" + e4.getMessage());
                    if (asyncTaskMonitor != null) {
                        asyncTaskMonitor.error(e4.getMessage(), (Throwable)e4);
                    }
                    throw new DataGatherException(e4.getMessage());
                }
                logHelper.info(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u68c0\u67e5\u5f15\u64ce\u5c42\u8c03\u7528", "\u5f15\u64ce\u5c42\u8c03\u7528\u7ed3\u675f");
                ArrayList<NodeCheckResultItem> nodeCheckResultItems = new ArrayList<NodeCheckResultItem>();
                for (CheckErrorItem item : result.getErrorItems()) {
                    DimensionValueSet dimensionKey;
                    NodeCheckResultItem nodeCheckResultItem = new NodeCheckResultItem();
                    nodeCheckResultItem.setFieldCode(item.getFieldCode());
                    nodeCheckResultItem.setFieldTitle(item.getFieldTitle());
                    nodeCheckResultItem.setParentValue(item.getParentValue());
                    nodeCheckResultItem.setChildValue(item.getChildValue());
                    nodeCheckResultItem.setMinusValue(item.getMinusValue());
                    nodeCheckResultItem.setUnitTitle(item.getUnitTitle());
                    nodeCheckResultItem.setUnitKey(item.getUnitKey());
                    Map regionCache = (Map)nodeCheckfieldMap.get(item.getRegionKey());
                    if (regionCache != null) {
                        nodeCheckResultItem.setNodeCheckFieldMessage((NodeCheckFieldMessage)regionCache.get(item.getFieldKey()));
                    }
                    if (allDimensionValueList.contains(dimensionKey = item.getDimension())) {
                        nodeCheckResultItem.setDimensionIndex(allDimensionValueList.indexOf(dimensionKey));
                    } else {
                        nodeCheckResultItem.setDimensionIndex(subDimensionValueList.size());
                        subDimensionValueList.add(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionKey));
                    }
                    nodeCheckResultItem.setDimensionTitle(dimensionTitle);
                    nodeCheckResultItems.add(nodeCheckResultItem);
                    unCheckUnits.add(item.getUnitKey());
                }
                Collections.sort(nodeCheckResultItems, (arg0, arg1) -> {
                    String formOrder1;
                    String formOrder0 = arg0.getNodeCheckFieldMessage().getFormOrder();
                    if (!formOrder0.equals(formOrder1 = arg1.getNodeCheckFieldMessage().getFormOrder())) {
                        return formOrder0.compareTo(formOrder1);
                    }
                    String fieldOrder0 = arg0.getNodeCheckFieldMessage().getFieldOrder();
                    String fieldOrder1 = arg1.getNodeCheckFieldMessage().getFieldOrder();
                    return fieldOrder0.compareTo(fieldOrder1);
                });
                if (nodeCheckResultItems.size() <= 0) continue;
                if (nodeCheckInfo.isRecursive()) {
                    nodeCheckResultItems.stream().collect(Collectors.groupingBy(NodeCheckResultItem::getUnitTitle, Collectors.toList())).forEach((name, list) -> nodeCheckResult.merge((String)name, (List<NodeCheckResultItem>)list, this.nodeCheckResultMerge));
                    continue;
                }
                nodeCheckResult.merge(((NodeCheckResultItem)nodeCheckResultItems.get(0)).getUnitTitle(), nodeCheckResultItems, this.nodeCheckResultMerge);
            }
        }
        nodeCheckResultInfo.setUnPassUnit(unCheckUnits.size());
        nodeCheckResultInfo.getDimensionList().addAll(subDimensionValueList);
        if (asyncTaskMonitor != null) {
            String objectToJson = JsonUtil.objectToJson((Object)nodeCheckResultInfo);
            if (nodeCheckResultInfo.getUnPassUnit() == 0) {
                asyncTaskMonitor.finish("\u8282\u70b9\u68c0\u67e5\u65e0\u8bef", (Object)objectToJson);
            } else {
                asyncTaskMonitor.error("\u8282\u70b9\u68c0\u67e5\u5b58\u5728\u9519\u8bef\uff0c\u70b9\u51fb\u67e5\u770b\u7ed3\u679c\u3002", null, objectToJson);
            }
        }
        logHelper.info(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u8282\u70b9\u68c0\u67e5\u670d\u52a1\u5c42\u7ed3\u675f", "\u8282\u70b9\u68c0\u67e5\u5b8c\u6210\uff01");
        return nodeCheckResultInfo;
    }

    @Override
    public NodeCheckResultInfo batchNodeCheck(NodeCheckParam nodeCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("\u6c47\u603b\u670d\u52a1", OperLevel.USER_OPER);
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(nodeCheckInfo.getFormSchemeKey());
        }
        catch (Exception e2) {
            throw new DataGatherException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{nodeCheckInfo.getFormSchemeKey()});
        }
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
        NodeCheckResultInfo nodeCheckResultInfo = new NodeCheckResultInfo();
        HashMap<String, List<NodeCheckResultItem>> nodeCheckResult = new HashMap<String, List<NodeCheckResultItem>>();
        nodeCheckResultInfo.setNodeCheckResult(nodeCheckResult);
        IDataAccessFormService dataAccessFormService = this.dataAccessServiceProvider.getDataAccessFormService();
        List formKeys = Stream.of(nodeCheckInfo.getFormKeys().split(";")).collect(Collectors.toList());
        Set<String> ignoreItems = nodeCheckInfo.getIgnoreAccessItems();
        DimensionCollection dimensionCollection = nodeCheckInfo.getDimensionCollection();
        List dimensionCombines = dimensionCollection.getDimensionCombinations();
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        for (DimensionCombination dimensionCombine : dimensionCombines) {
            dimensionValueSets.add(dimensionCombine.toDimensionValueSet());
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dimensionValueSets);
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(dimensionCollection);
        accessFormParam.setTaskKey(nodeCheckInfo.getTaskKey());
        accessFormParam.setIgnoreAccessItems(ignoreItems);
        accessFormParam.setFormSchemeKey(nodeCheckInfo.getFormSchemeKey());
        accessFormParam.setFormKeys(formKeys);
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_READ);
        DimensionAccessFormInfo dimensionAccessInfos = dataAccessFormService.getBatchAccessForms(accessFormParam);
        List accessFormInfos = dimensionAccessInfos.getAccessForms();
        EntityViewDefine entityViewDefine = entityId.equals(formScheme.getDw()) ? this.runtimeView.getViewByFormSchemeKey(formScheme.getKey()) : this.entityViewRunTimeController.buildEntityView(entityId, dsContext.getContextFilterExpression());
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        String targetKey = String.valueOf(dimensionValueSet.getValue(entityDimension));
        String period = String.valueOf(dimensionValueSet.getValue(periodDimension));
        IEntityTable entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, entityViewDefine, nodeCheckInfo.getFormSchemeKey(), period);
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(entityId, new String[]{targetKey});
        logDimensionCollection.setPeriod(formScheme.getDateTime(), period);
        for (int formInfoIndex = 0; formInfoIndex < accessFormInfos.size(); ++formInfoIndex) {
            NodeCheckResult result;
            if (asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
                return nodeCheckResultInfo;
            }
            DimensionAccessFormInfo.AccessFormInfo dimensionValueFormInfo = (DimensionAccessFormInfo.AccessFormInfo)accessFormInfos.get(formInfoIndex);
            Map dimensionValue = dimensionValueFormInfo.getDimensions();
            List accessFormKeys = dimensionValueFormInfo.getFormKeys();
            List splitDimensions = dimensionCollection.getDimensionCombinations();
            List splitDimensionValueSets = splitDimensions.stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
            List subDimensionValueList = splitDimensionValueSets.stream().map(DimensionValueSetUtil::getDimensionSet).collect(Collectors.toList());
            DimensionValueSet accessDimension = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionValue);
            DimensionValueSet combineDimension = dimensionCollection.combineWithoutVarDim();
            accessDimension.combine(combineDimension);
            Set targetKeys = subDimensionValueList.stream().map(e -> ((DimensionValue)e.get(entityDimension)).getValue()).collect(Collectors.toSet());
            List dimensionList = subDimensionValueList.stream().map(e -> ((DimensionValue)e.get(entityDimension)).getValue()).collect(Collectors.toList());
            Map<String, String> dimensionTitle = this.getDimensionTitle(dsContext, formScheme, accessDimension, periodDimension, period);
            List targetKeyList = targetKeys.stream().filter(target -> !this.isLeafEntity(entityTable, (String)target)).collect(Collectors.toList());
            accessDimension.clearValue(entityDimension);
            String periodCode = accessDimension.getValue(periodDimension).toString();
            accessDimension.clearValue(periodDimension);
            ArrayList gatherTableDefineList = new ArrayList();
            HashMap nodeCheckfieldMap = new HashMap();
            HashMap tableCache = new HashMap();
            HashSet warnTables = new HashSet();
            accessFormKeys.forEach(formKey -> gatherTableDefineList.addAll(GatherTableUtil.getGatherTables(formKey, nodeCheckfieldMap, tableCache, warnTables)));
            double formStartProgress = (double)formInfoIndex / (double)accessFormInfos.size();
            double formEndProgress = (double)(formInfoIndex + 1) / (double)accessFormInfos.size();
            if (gatherTableDefineList.isEmpty() || CollectionUtils.isEmpty(targetKeys)) {
                if (asyncTaskMonitor == null) continue;
                asyncTaskMonitor.progressAndMessage(formEndProgress, "\u6279\u91cf\u8282\u70b9\u68c0\u67e5\u4e2d");
                continue;
            }
            DataGatherParallelMonitor monitor = new DataGatherParallelMonitor();
            monitor.setStartProgress(formStartProgress, formEndProgress);
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            queryEnvironment.setFormSchemeKey(nodeCheckInfo.getFormSchemeKey());
            IDataGather dataGather = this.dataGatherProvider.newDataGather(queryEnvironment);
            GatherCondition condition = new GatherCondition();
            condition.setFormSchemeKey(nodeCheckInfo.getFormSchemeKey());
            condition.setSourceDimensions(accessDimension);
            condition.setTargetDimension(accessDimension);
            condition.setUnitView(entityViewDefine);
            if (StringUtils.isNotEmpty((String)periodCode)) {
                condition.setPeriodCode(periodCode);
            }
            condition.setRecursive(nodeCheckInfo.isRecursive());
            condition.setGatherDirection(GatherDirection.GATHER_TO_GROUP);
            condition.setGatherTables(gatherTableDefineList);
            condition.setPrecisionValue(nodeCheckInfo.getErrorRange());
            condition.setTaskKey(nodeCheckInfo.getTaskKey());
            dataGather.setGatherCondition(condition);
            dataGather.setMonitor((IMonitor)monitor);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, nodeCheckInfo.getFormSchemeKey());
            executorContext.setEnv((IFmlExecEnvironment)environment);
            try {
                logHelper.info(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u6279\u91cf\u8282\u70b9\u68c0\u67e5", "--\u8c03\u7528\u5f15\u64ce--");
                result = dataGather.executeBatchNodeCheck((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, targetKeyList);
            }
            catch (Exception e3) {
                logHelper.error(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u6279\u91cf\u8282\u70b9\u68c0\u67e5", "\u8282\u70b9\u68c0\u67e5\u5931\u8d25--" + e3.getMessage());
                if (asyncTaskMonitor != null) {
                    asyncTaskMonitor.error(e3.getMessage(), (Throwable)e3);
                }
                throw new DataGatherException(e3.getMessage());
            }
            nodeCheckResultInfo.setTotalCheckUnit(nodeCheckResultInfo.getTotalCheckUnit() + result.getCheckedNodeCount());
            nodeCheckResultInfo.setUnPassUnit(nodeCheckResultInfo.getUnPassUnit() + result.getUnpassedNodeCount());
            ArrayList<NodeCheckResultItem> nodeCheckResultItems = new ArrayList<NodeCheckResultItem>();
            for (CheckErrorItem item : result.getErrorItems()) {
                NodeCheckResultItem nodeCheckResultItem = new NodeCheckResultItem();
                nodeCheckResultItem.setFieldCode(item.getFieldCode());
                nodeCheckResultItem.setFieldTitle(item.getFieldTitle());
                nodeCheckResultItem.setParentValue(item.getParentValue());
                nodeCheckResultItem.setChildValue(item.getChildValue());
                nodeCheckResultItem.setMinusValue(item.getMinusValue());
                nodeCheckResultItem.setUnitTitle(item.getUnitTitle());
                nodeCheckResultItem.setUnitKey(item.getUnitKey());
                Map regionCache = (Map)nodeCheckfieldMap.get(item.getRegionKey());
                if (regionCache != null) {
                    nodeCheckResultItem.setNodeCheckFieldMessage((NodeCheckFieldMessage)regionCache.get(item.getFieldKey()));
                }
                nodeCheckResultItem.setDimensionIndex(dimensionList.indexOf(item.getUnitKey()));
                nodeCheckResultItem.setDimensionTitle(dimensionTitle);
                nodeCheckResultItems.add(nodeCheckResultItem);
            }
            nodeCheckResultItems.sort((arg0, arg1) -> {
                String formOrder1;
                String formOrder0 = arg0.getNodeCheckFieldMessage().getFormOrder();
                if (!formOrder0.equals(formOrder1 = arg1.getNodeCheckFieldMessage().getFormOrder())) {
                    return formOrder0.compareTo(formOrder1);
                }
                return 0;
            });
            if (nodeCheckResultItems.size() > 0) {
                nodeCheckResultItems.stream().collect(Collectors.groupingBy(NodeCheckResultItem::getUnitTitle, Collectors.toList())).forEach((name, list) -> nodeCheckResult.merge((String)name, (List<NodeCheckResultItem>)list, this.nodeCheckResultMerge));
            }
            nodeCheckResultInfo.getDimensionList().addAll(subDimensionValueList);
        }
        if (asyncTaskMonitor != null) {
            String objectToJson = JsonUtil.objectToJson((Object)nodeCheckResultInfo);
            if (nodeCheckResultInfo.getUnPassUnit() == 0) {
                asyncTaskMonitor.finish("\u8282\u70b9\u68c0\u67e5\u65e0\u8bef", (Object)objectToJson);
            } else {
                asyncTaskMonitor.error("\u8282\u70b9\u68c0\u67e5\u5b58\u5728\u9519\u8bef\uff0c\u70b9\u51fb\u67e5\u770b\u7ed3\u679c\u3002", null, objectToJson);
            }
        }
        logHelper.info(nodeCheckInfo.getTaskKey(), logDimensionCollection, "\u6279\u91cf\u8282\u70b9\u68c0\u67e5", "--\u8282\u70b9\u68c0\u67e5\u670d\u52a1\u5c42\u7ed3\u675f--");
        return nodeCheckResultInfo;
    }

    @Override
    public NodeCheckResultInfo getNodeCheckResult(String asyncTaskID) {
        Optional detail = this.getDetail(asyncTaskID);
        return detail.orElse(null);
    }

    private Map<String, String> getDimensionTitle(DsContext dsContext, FormSchemeDefine formSchemeDefine, DimensionValueSet dimensionValueSet, String periodDimension, String periodCode) {
        HashMap<String, String> titleMap = new HashMap<String, String>();
        titleMap.put(periodDimension, periodCode);
        String dwEntityId = formSchemeDefine.getDw();
        if (dsContext != null && StringUtils.isNotEmpty((String)dsContext.getContextEntityId())) {
            dwEntityId = dsContext.getContextEntityId();
        }
        String dims = formSchemeDefine.getDims();
        Set<String> entitySet = new HashSet<String>();
        if (StringUtils.isNotEmpty((String)dims)) {
            String[] entityIds = dims.split(";");
            entitySet = Stream.of(entityIds).filter(StringUtils::isNotEmpty).collect(Collectors.toSet());
        }
        entitySet.add(dwEntityId);
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String dimensionName = null;
        for (String entityId : entitySet) {
            String title;
            EntityViewDefine entityViewDefine;
            ExecutorContext executorContext;
            IEntityTable entityTable;
            if (periodAdapter.isPeriodEntity(entityId)) {
                IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(entityId);
                dimensionName = periodEntity.getDimensionName();
            } else {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
                dimensionName = entityDefine.getDimensionName();
            }
            if (dimensionName == null || !dimensionValueSet.hasValue(dimensionName) || (entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)(executorContext = new ExecutorContext(this.dataDefinitionRuntimeController)), entityViewDefine = entityId.equals(formSchemeDefine.getDw()) ? this.runtimeView.getViewByFormSchemeKey(formSchemeDefine.getKey()) : this.entityViewRunTimeController.buildEntityView(entityId, dsContext.getContextFilterExpression()), formSchemeDefine.getKey(), periodCode)) == null) continue;
            IEntityRow entityRow = entityTable.findByEntityKey(String.valueOf(dimensionValueSet.getValue(dimensionName)));
            if (entityRow == null) {
                entityRow = entityTable.findByCode(String.valueOf(dimensionValueSet.getValue(dimensionName)));
            }
            if ((title = entityRow.getTitle()) == null) continue;
            titleMap.put(dimensionName, title);
        }
        return titleMap;
    }

    private IEntityTable getEntityTable(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, EntityViewDefine unitView, String formSchemeKey, String periodCode) {
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
        IEntityTable entityTable = null;
        try {
            entityTable = entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5b9e\u4f53\u5931\u8d25\uff01");
        }
        return entityTable;
    }

    private boolean isLeafEntity(IEntityTable entityTable, String targetKey) {
        if (entityTable == null) {
            return true;
        }
        return CollectionUtils.isEmpty(entityTable.getChildRows(targetKey));
    }
}

