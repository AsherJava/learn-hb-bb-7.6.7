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
 *  com.jiuqi.nr.common.asynctask.AsyncTaskFuture
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.engine.gather.CheckErrorItem
 *  com.jiuqi.nr.data.engine.gather.GatherCondition
 *  com.jiuqi.nr.data.engine.gather.GatherDirection
 *  com.jiuqi.nr.data.engine.gather.GatherTableDefine
 *  com.jiuqi.nr.data.engine.gather.IDataGather
 *  com.jiuqi.nr.data.engine.gather.IDataGatherProvider
 *  com.jiuqi.nr.data.engine.gather.NodeCheckResult
 *  com.jiuqi.nr.data.gather.bean.NodeCheckParam
 *  com.jiuqi.nr.data.gather.bean.NodeCheckResultInfo
 *  com.jiuqi.nr.data.gather.bean.NodeCheckResultItem
 *  com.jiuqi.nr.data.gather.bean.NodeGatherParam
 *  com.jiuqi.nr.data.gather.bean.SelectDataGatherParam
 *  com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResult
 *  com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResultItemInfo
 *  com.jiuqi.nr.data.gather.refactor.factory.IDataGatherServiceFactory
 *  com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor
 *  com.jiuqi.nr.data.gather.refactor.monitor.impl.DefaultMonitor
 *  com.jiuqi.nr.data.gather.refactor.service.NodeCheckService
 *  com.jiuqi.nr.data.gather.service.IDataSelectGatherService
 *  com.jiuqi.nr.data.gather.service.INodeCheckService
 *  com.jiuqi.nr.data.gather.service.INodeGatherService
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
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
 *  com.jiuqi.nr.jtable.exception.NotFoundEntityException
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.EntityDataLoader
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.internal.service;

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
import com.jiuqi.nr.common.asynctask.AsyncTaskFuture;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.engine.gather.CheckErrorItem;
import com.jiuqi.nr.data.engine.gather.GatherCondition;
import com.jiuqi.nr.data.engine.gather.GatherDirection;
import com.jiuqi.nr.data.engine.gather.GatherTableDefine;
import com.jiuqi.nr.data.engine.gather.IDataGather;
import com.jiuqi.nr.data.engine.gather.IDataGatherProvider;
import com.jiuqi.nr.data.gather.bean.NodeCheckParam;
import com.jiuqi.nr.data.gather.bean.NodeCheckResultItem;
import com.jiuqi.nr.data.gather.bean.NodeGatherParam;
import com.jiuqi.nr.data.gather.bean.SelectDataGatherParam;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResult;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResultItemInfo;
import com.jiuqi.nr.data.gather.refactor.factory.IDataGatherServiceFactory;
import com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor;
import com.jiuqi.nr.data.gather.refactor.monitor.impl.DefaultMonitor;
import com.jiuqi.nr.data.gather.refactor.service.NodeCheckService;
import com.jiuqi.nr.data.gather.service.IDataSelectGatherService;
import com.jiuqi.nr.data.gather.service.INodeCheckService;
import com.jiuqi.nr.data.gather.service.INodeGatherService;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.bean.NodeCheckFieldMessage;
import com.jiuqi.nr.dataentry.bean.NodeCheckInfo;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultInfo;
import com.jiuqi.nr.dataentry.bean.OrderedNodeCheckResultInfo;
import com.jiuqi.nr.dataentry.exception.NodeCheckException;
import com.jiuqi.nr.dataentry.internal.service.BatchParallelMonitor;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchDimensionValueFormInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo;
import com.jiuqi.nr.dataentry.paramInfo.DimensionValueFormInfo;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.service.IBatchDataSumService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataentry.util.ExcelErrorUtil;
import com.jiuqi.nr.dataentry.util.GatherTableUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
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
import com.jiuqi.nr.jtable.exception.NotFoundEntityException;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.EntityDataLoader;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BatchDataSumServiceImpl
implements IBatchDataSumService {
    private static final Logger logger = LoggerFactory.getLogger(BatchDataSumServiceImpl.class);
    @Resource
    IRunTimeViewController runtimeView;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    IDataGatherProvider dataGatherProvider;
    @Autowired
    IJtableParamService jtableParamService;
    @Autowired
    FormGroupProvider formGroupProvider;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private DimensionValueProvider dimensionValueProvider;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private INodeGatherService nodeGatherService;
    @Autowired
    private INodeCheckService nodeCheckService;
    @Autowired
    private IDataSelectGatherService selectGatherService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private DataEntityFullService dataEntityFullService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataGatherServiceFactory iDataGatherServiceFactory;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    private final BiFunction<List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>, List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>, List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>> nodeCheckResultMerge = (oldValue, newValue) -> {
        newValue.addAll(oldValue);
        return newValue;
    };

    @Override
    public void batchDataSumForm(BatchDataSumInfo batchDataSumInfo, AsyncTaskMonitor asyncTaskMonitor, float progressPer) {
        JtableContext jtableContext = batchDataSumInfo.getContext();
        Map dimensionSet = batchDataSumInfo.getContext().getDimensionSet();
        DimensionCollection collection = this.dimCollectionBuildUtil.buildDimensionCollection(dimensionSet, jtableContext.getFormSchemeKey());
        HashSet<String> ignoreItems = new HashSet<String>();
        if (batchDataSumInfo.isIgnoreAuth()) {
            ignoreItems.add("ALL");
        } else {
            String value = this.taskOptionController.getValue(jtableContext.getTaskKey(), "GATHER_NO_CONDITION");
            if ("1".equals(value)) {
                ignoreItems.add("formCondition");
            }
            if (batchDataSumInfo.isIgnoreWorkFlow()) {
                ignoreItems.add("upload");
            }
        }
        List<String> sourceKeys = batchDataSumInfo.getSourceKeys();
        if (CollectionUtils.isEmpty(sourceKeys)) {
            UnitNature targetUnitNature;
            NodeGatherParam nodeGatherParam = new NodeGatherParam();
            nodeGatherParam.setTaskKey(batchDataSumInfo.getContext().getTaskKey());
            nodeGatherParam.setDifference(batchDataSumInfo.isDifference());
            nodeGatherParam.setRecursive(batchDataSumInfo.isRecursive());
            nodeGatherParam.setFormSchemeKey(batchDataSumInfo.getContext().getFormSchemeKey());
            nodeGatherParam.setFormKeys(batchDataSumInfo.getFormKeys());
            nodeGatherParam.getIgnoreAccessItems().addAll(ignoreItems);
            nodeGatherParam.setDimensionCollection(collection);
            this.nodeGatherService.asyncBatchDataGather(nodeGatherParam, asyncTaskMonitor);
            if (batchDataSumInfo.isDifference() && (targetUnitNature = this.getTargetUnitNature(jtableContext.getFormSchemeKey(), collection)) != null && targetUnitNature.equals((Object)UnitNature.BZHZB)) {
                nodeGatherParam.setBZHZBGather(true);
                nodeGatherParam.setDifference(false);
                this.nodeGatherService.asyncBatchDataGather(nodeGatherParam, asyncTaskMonitor);
            }
        } else {
            SelectDataGatherParam selectDataGatherParam = new SelectDataGatherParam();
            selectDataGatherParam.setTaskKey(batchDataSumInfo.getContext().getTaskKey());
            selectDataGatherParam.setSourceKeys(sourceKeys);
            selectDataGatherParam.setFormSchemeKey(batchDataSumInfo.getContext().getFormSchemeKey());
            selectDataGatherParam.setFormKeys(batchDataSumInfo.getFormKeys());
            selectDataGatherParam.getIgnoreAccessItems().addAll(ignoreItems);
            selectDataGatherParam.setDimensionCollection(collection);
            this.selectGatherService.asyncDataSelectGather(selectDataGatherParam, asyncTaskMonitor);
        }
    }

    @Override
    public NodeCheckResultInfo nodeCheck(NodeCheckInfo nodeCheckInfo) {
        return this.nodeCheck(nodeCheckInfo, null);
    }

    @Override
    public NodeCheckResultInfo nodeCheck(NodeCheckInfo nodeCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        NodeCheckParam nodeCheckParam = new NodeCheckParam();
        nodeCheckParam.setTaskKey(nodeCheckInfo.getContext().getTaskKey());
        nodeCheckParam.setRecursive(nodeCheckInfo.isRecursive());
        nodeCheckParam.setFormSchemeKey(nodeCheckInfo.getContext().getFormSchemeKey());
        nodeCheckParam.setFormKeys(nodeCheckInfo.getFormKeys());
        nodeCheckParam.setErrorRange(nodeCheckInfo.getErrorRange());
        Map dimensionSet = nodeCheckInfo.getContext().getDimensionSet();
        DimensionCollection collection = this.dimCollectionBuildUtil.buildDimensionCollection(dimensionSet, nodeCheckInfo.getContext().getFormSchemeKey());
        nodeCheckParam.setDimensionCollection(collection);
        NodeCheckService nodeCheckService = this.iDataGatherServiceFactory.getNodeCheckService();
        DefaultMonitor monitor = new DefaultMonitor(asyncTaskMonitor);
        NodeCheckResult result = nodeCheckService.nodeCheck(nodeCheckParam, (IGatherServiceMonitor)monitor);
        return this.convertNodeCheckResultToNodeCheckResultInfo(result);
    }

    public NodeCheckResultInfo convertNodeCheckResultToNodeCheckResultInfo(NodeCheckResult nodeCheckResult) {
        NodeCheckResultInfo checkResultInfo = new NodeCheckResultInfo();
        checkResultInfo.setTotalCheckUnit(nodeCheckResult.getTotalCheckUnit());
        checkResultInfo.setUnPassUnit(nodeCheckResult.getUnPassUnit());
        HashMap<String, List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>> nodeCheckResultItemList = new HashMap<String, List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>>();
        ArrayList<Map<String, DimensionValue>> dimensionList = new ArrayList<Map<String, DimensionValue>>();
        int index = 0;
        ArrayList<String> formKeys = new ArrayList<String>();
        ArrayList period = new ArrayList();
        HashSet<String> mdCodes = new HashSet<String>();
        for (NodeCheckResultItemInfo nodeCheckResultItemInfo : nodeCheckResult.getResultItemInfos()) {
            dimensionList.add(nodeCheckResultItemInfo.getDimensionValue());
            List nodeCheckResultItems = nodeCheckResultItemInfo.getNodeCheckResultItems();
            ArrayList<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem> items = new ArrayList<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>();
            for (NodeCheckResultItem nodeCheckResultItem : nodeCheckResultItems) {
                mdCodes.add(nodeCheckResultItem.getUnitKey());
                com.jiuqi.nr.dataentry.bean.NodeCheckResultItem item = new com.jiuqi.nr.dataentry.bean.NodeCheckResultItem();
                item.setUnitKey(nodeCheckResultItem.getUnitKey());
                item.setUnitTitle(nodeCheckResultItem.getUnitTitle());
                item.setMinusValue(nodeCheckResultItem.getMinusValue());
                item.setChildValue(nodeCheckResultItem.getChildValue());
                item.setParentValue(nodeCheckResultItem.getParentValue());
                item.setDimensionIndex(index);
                item.setFieldCode(nodeCheckResultItem.getFieldCode());
                item.setFieldTitle(nodeCheckResultItem.getFieldTitle());
                item.setDimensionTitle(nodeCheckResultItem.getDimensionTitle());
                item.setBizKeyOrder(nodeCheckResultItem.getBizKeyOrder());
                item.setRowKeys(nodeCheckResultItem.getRowKeys());
                if (StringUtils.isNotEmpty((String)nodeCheckResultItem.getBizKeyOrder())) {
                    item.setBizKeyOrder(this.buildRowId(nodeCheckResultItem));
                }
                NodeCheckFieldMessage checkMsg = new NodeCheckFieldMessage();
                BeanUtils.copyProperties(nodeCheckResultItem.getNodeCheckFieldMessage(), checkMsg);
                item.setNodeCheckFieldMessage(checkMsg);
                items.add(item);
                formKeys.add(checkMsg.getFormKey());
                period.add(nodeCheckResultItem.getDimensionTitle().get("DATATIME"));
            }
            if (nodeCheckResultItemList.containsKey(nodeCheckResultItemInfo.getUnitTitle())) {
                ((List)nodeCheckResultItemList.get(nodeCheckResultItemInfo.getUnitTitle())).addAll(items);
            } else {
                nodeCheckResultItemList.put(nodeCheckResultItemInfo.getUnitTitle(), items);
            }
            ++index;
        }
        checkResultInfo.setDimensionList(dimensionList);
        checkResultInfo.setNodeCheckResult(nodeCheckResultItemList);
        return checkResultInfo;
    }

    private NodeCheckResultInfo convertNodeCheckResult(com.jiuqi.nr.data.gather.bean.NodeCheckResultInfo nodeCheckResultInfo) {
        OrderedNodeCheckResultInfo checkResultInfo = null;
        if (nodeCheckResultInfo != null) {
            checkResultInfo = new OrderedNodeCheckResultInfo();
            checkResultInfo.setTotalCheckUnit(nodeCheckResultInfo.getTotalCheckUnit());
            checkResultInfo.setUnPassUnit(nodeCheckResultInfo.getUnPassUnit());
            checkResultInfo.setDimensionList(nodeCheckResultInfo.getDimensionList());
            Map gatherCheckResultItem = nodeCheckResultInfo.getNodeCheckResult();
            HashMap checkResultItems = new HashMap();
            ArrayList formKeys = new ArrayList();
            ArrayList period = new ArrayList();
            HashSet mdCodes = new HashSet();
            gatherCheckResultItem.forEach((k, v) -> {
                ArrayList<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem> items = new ArrayList<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>();
                for (NodeCheckResultItem nodeCheckResultItem : v) {
                    mdCodes.add(nodeCheckResultItem.getUnitKey());
                    com.jiuqi.nr.dataentry.bean.NodeCheckResultItem item = new com.jiuqi.nr.dataentry.bean.NodeCheckResultItem();
                    item.setUnitKey(nodeCheckResultItem.getUnitKey());
                    item.setUnitTitle(nodeCheckResultItem.getUnitTitle());
                    item.setMinusValue(nodeCheckResultItem.getMinusValue());
                    item.setDimensionIndex(nodeCheckResultItem.getDimensionIndex());
                    item.setFieldCode(nodeCheckResultItem.getFieldCode());
                    item.setFieldTitle(nodeCheckResultItem.getFieldTitle());
                    item.setChildValue(nodeCheckResultItem.getChildValue());
                    item.setParentValue(nodeCheckResultItem.getParentValue());
                    item.setDimensionTitle(nodeCheckResultItem.getDimensionTitle());
                    NodeCheckFieldMessage checkMsg = new NodeCheckFieldMessage();
                    BeanUtils.copyProperties(nodeCheckResultItem.getNodeCheckFieldMessage(), checkMsg);
                    item.setNodeCheckFieldMessage(checkMsg);
                    items.add(item);
                    formKeys.add(checkMsg.getFormKey());
                    period.add(nodeCheckResultItem.getDimensionTitle().get("DATATIME"));
                }
                checkResultItems.put(k, items);
            });
            try {
                LinkedHashMap<String, List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>> finalResult = new LinkedHashMap<String, List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>>();
                if (formKeys != null && formKeys.size() > 0) {
                    IEntityTable entityTableByOrder;
                    FormSchemeDefine formSchemeDefine = (FormSchemeDefine)this.runtimeView.queryFormSchemeByForm((String)formKeys.get(0)).get(0);
                    ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                    ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeDefine.getKey());
                    executorContext.setEnv((IFmlExecEnvironment)environment);
                    EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchemeDefine.getKey());
                    if (!mdCodes.isEmpty() && (entityTableByOrder = this.getEntityTableByOrder((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, dwEntity, formSchemeDefine.getKey(), (String)period.get(0), new ArrayList<String>(mdCodes))) != null) {
                        List allRows = entityTableByOrder.getAllRows();
                        for (IEntityRow row : allRows) {
                            List nodeCheckResultItems = (List)checkResultItems.get(row.getTitle());
                            if (CollectionUtils.isEmpty(nodeCheckResultItems)) continue;
                            finalResult.put(((com.jiuqi.nr.dataentry.bean.NodeCheckResultItem)nodeCheckResultItems.get(0)).getUnitTitle(), nodeCheckResultItems);
                        }
                    }
                }
                checkResultInfo.setNodeCheckResult(finalResult);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return checkResultInfo;
    }

    private OrderedNodeCheckResultInfo convertNodeCheckResult(NodeCheckResultInfo nodeCheckResultInfo) {
        OrderedNodeCheckResultInfo checkResultInfo = null;
        if (nodeCheckResultInfo != null) {
            checkResultInfo = new OrderedNodeCheckResultInfo();
            checkResultInfo.setTotalCheckUnit(nodeCheckResultInfo.getTotalCheckUnit());
            checkResultInfo.setUnPassUnit(nodeCheckResultInfo.getUnPassUnit());
            checkResultInfo.setDimensionList(nodeCheckResultInfo.getDimensionList());
            checkResultInfo.setNodeCheckResult(nodeCheckResultInfo.getNodeCheckResult());
            Map<String, List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>> checkResultItems = nodeCheckResultInfo.getNodeCheckResult();
            ArrayList formKeys = new ArrayList();
            ArrayList period = new ArrayList();
            HashSet mdCodes = new HashSet();
            checkResultItems.forEach((k, v) -> {
                for (com.jiuqi.nr.dataentry.bean.NodeCheckResultItem nodeCheckResultItem : v) {
                    mdCodes.add(nodeCheckResultItem.getUnitKey());
                    NodeCheckFieldMessage checkMsg = new NodeCheckFieldMessage();
                    BeanUtils.copyProperties(nodeCheckResultItem.getNodeCheckFieldMessage(), checkMsg);
                    formKeys.add(checkMsg.getFormKey());
                    period.add(nodeCheckResultItem.getDimensionTitle().get("DATATIME"));
                }
            });
            try {
                IEntityTable entityTableByOrder;
                FormSchemeDefine formSchemeDefine = (FormSchemeDefine)this.runtimeView.queryFormSchemeByForm((String)formKeys.get(0)).get(0);
                ArrayList<List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>> finalResult = new ArrayList<List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>>();
                ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeDefine.getKey());
                executorContext.setEnv((IFmlExecEnvironment)environment);
                EntityViewData dwEntity = this.jtableParamService.getDwEntity(formSchemeDefine.getKey());
                if (!mdCodes.isEmpty() && (entityTableByOrder = this.getEntityTableByOrder((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, dwEntity, formSchemeDefine.getKey(), (String)period.get(0), new ArrayList<String>(mdCodes))) != null) {
                    List allRows = entityTableByOrder.getAllRows();
                    for (IEntityRow row : allRows) {
                        List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem> nodeCheckResultItems = checkResultItems.get(row.getTitle());
                        if (CollectionUtils.isEmpty(nodeCheckResultItems)) continue;
                        finalResult.add(nodeCheckResultItems);
                    }
                }
                checkResultInfo.setOrderedNodeCheckResult(finalResult);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return checkResultInfo;
    }

    private String buildRowId(NodeCheckResultItem nodeCheckResultItem) {
        FormDefine formDefine = this.runtimeView.queryFormById(nodeCheckResultItem.getNodeCheckFieldMessage().getFormKey());
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(formDefine.getFormScheme());
        List fieldDataList = this.jtableDataEngineService.getBizKeyOrderFieldList(nodeCheckResultItem.getNodeCheckFieldMessage().getRegionKey(), jtableContext);
        LinkedHashMap<String, FieldData> dimNameField = new LinkedHashMap<String, FieldData>();
        List list = (List)fieldDataList.get(0);
        for (FieldData fieldData : list) {
            String dimName = this.jtableDataEngineService.getDimensionName(fieldData);
            dimNameField.put(dimName, fieldData);
        }
        HashMap<String, DimensionValue> rowKeys = new HashMap<String, DimensionValue>(nodeCheckResultItem.getRowKeys());
        DimensionValue recordkey = new DimensionValue();
        recordkey.setName("RECORDKEY");
        recordkey.setValue(nodeCheckResultItem.getBizKeyOrder());
        rowKeys.put("RECORDKEY", recordkey);
        StringBuilder floatRowID = new StringBuilder();
        for (Map.Entry nameEntry : dimNameField.entrySet()) {
            String dimensionValue = ((DimensionValue)rowKeys.get(nameEntry.getKey())).getValue();
            if (dimensionValue == null || StringUtils.isEmpty((String)dimensionValue.toString())) {
                dimensionValue = "-";
            }
            if (floatRowID.length() > 0) {
                floatRowID.append("#^$");
            }
            floatRowID.append((Object)dimensionValue);
        }
        return floatRowID.toString();
    }

    private IEntityTable getEntityTableByOrder(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, EntityViewData entityViewData, String formSchemeKey, String period, List<String> entityKeys) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeKey);
        IEntityDataService entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
        IEntityQuery entityQuery = entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewData.getEntityViewDefine());
        DimensionValueSet masterKeys = new DimensionValueSet();
        if (!StringUtils.isEmpty((String)period)) {
            masterKeys.setValue("DATATIME", (Object)period);
        }
        if (!CollectionUtils.isEmpty(entityKeys)) {
            masterKeys.setValue(entityViewData.getDimensionName(), entityKeys);
        }
        entityQuery.setMasterKeys(masterKeys);
        executorContext.setVarDimensionValueSet(masterKeys);
        executorContext.setPeriodView(formScheme.getDateTime());
        entityQuery.sorted(true);
        entityQuery.sortedByQuery(false);
        IEntityTable entityTable = null;
        try {
            entityTable = entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5b9e\u4f53\u5931\u8d25\uff01");
        }
        return entityTable;
    }

    @Override
    public NodeCheckResultInfo batchNodeCheck(NodeCheckInfo nodeCheckInfo, AsyncTaskMonitor asyncTaskMonitor) {
        JtableContext jtableContext = nodeCheckInfo.getContext();
        BatchReturnInfo batchReturnInfo = new BatchReturnInfo();
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        }
        catch (Exception e2) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey()});
        }
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(formScheme.getKey());
        EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(formScheme.getKey());
        if (null == targetEntityInfo || null == periodEntityInfo) {
            throw new NotFoundEntityException(new String[]{null == targetEntityInfo ? "\u672a\u627e\u5230\u5355\u4f4d\u4e3b\u4f53" : "\u672a\u627e\u5230\u65f6\u671f\u4e3b\u4f53"});
        }
        NodeCheckResultInfo nodeCheckResultInfo = new NodeCheckResultInfo();
        HashMap<String, List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>> nodeCheckResult = new HashMap<String, List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>>();
        nodeCheckResultInfo.setNodeCheckResult(nodeCheckResult);
        BatchDimensionValueFormInfo batchDimensionValueFormInfo = this.formGroupProvider.getForms(jtableContext, nodeCheckInfo.getFormKeys(), Consts.FormAccessLevel.FORM_READ);
        List<DimensionValueFormInfo> acessFormInfos = batchDimensionValueFormInfo.getAccessFormInfos();
        HashMap entityDataMap = new HashMap();
        HashMap<String, EntityDataLoader> entityLoaderMap = new HashMap<String, EntityDataLoader>();
        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        entityQueryByKeyInfo.setContext(jtableContext);
        for (EntityViewData entity : this.jtableParamService.getEntityList(formScheme.getKey())) {
            entityQueryByKeyInfo.setEntityViewKey(entity.getKey());
            EntityDataLoader entityDataLoader = this.jtableEntityService.getEntityDataLoader(entityQueryByKeyInfo);
            entityLoaderMap.put(entity.getDimensionName(), entityDataLoader);
        }
        for (int formInfoIndex = 0; formInfoIndex < acessFormInfos.size(); ++formInfoIndex) {
            if (asyncTaskMonitor != null && asyncTaskMonitor.isCancel()) {
                return nodeCheckResultInfo;
            }
            DimensionValueFormInfo dimensionValueFormInfo = acessFormInfos.get(formInfoIndex);
            Map<String, DimensionValue> dimensionValue = dimensionValueFormInfo.getDimensionValue();
            List<String> forms = dimensionValueFormInfo.getForms();
            JtableContext jtableContextInfo = new JtableContext();
            jtableContextInfo.setDimensionSet(dimensionValue);
            jtableContextInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
            jtableContextInfo.setTaskKey(jtableContext.getTaskKey());
            List<Map<String, DimensionValue>> subDimensionValueList = this.dimensionValueProvider.splitDimensionValueList(jtableContext, new ArrayList<String>(), true);
            Set targetKeys = subDimensionValueList.stream().map(e -> ((DimensionValue)e.get(targetEntityInfo.getDimensionName())).getValue()).collect(Collectors.toSet());
            List dimensionList = subDimensionValueList.stream().map(e -> ((DimensionValue)e.get(targetEntityInfo.getDimensionName())).getValue()).collect(Collectors.toList());
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimensionValue);
            Map dimensionTitle = DimensionValueSetUtil.getDimensionTitle((DimensionValueSet)dimensionValueSet, (String)formScheme.getKey(), entityDataMap, entityLoaderMap);
            List targetKeyList = targetKeys.stream().filter(target -> !this.isLeafEntity((String)target, targetEntityInfo, jtableContext)).collect(Collectors.toList());
            dimensionValueSet.clearValue(targetEntityInfo.getDimensionName());
            String periodCode = dimensionValueSet.getValue(periodEntityInfo.getDimensionName()).toString();
            dimensionValueSet.clearValue(periodEntityInfo.getDimensionName());
            ArrayList<GatherTableDefine> gatherTableDefineList = new ArrayList<GatherTableDefine>();
            HashMap<String, HashMap<String, NodeCheckFieldMessage>> nodeCheckfieldMap = new HashMap<String, HashMap<String, NodeCheckFieldMessage>>();
            HashMap<String, HashSet<String>> tableCache = new HashMap<String, HashSet<String>>();
            HashSet<String> warnTables = new HashSet<String>();
            for (String formKey : forms) {
                gatherTableDefineList.addAll(GatherTableUtil.getGatherTables(formKey, nodeCheckfieldMap, tableCache, warnTables));
            }
            double formStartProgress = (double)formInfoIndex / (double)acessFormInfos.size();
            double formEndProgress = (double)(formInfoIndex + 1) / (double)acessFormInfos.size();
            if (gatherTableDefineList.isEmpty() || CollectionUtils.isEmpty(targetKeys)) {
                if (asyncTaskMonitor == null) continue;
                String nodeCheck = "node_check_ing";
                asyncTaskMonitor.progressAndMessage(formEndProgress, nodeCheck);
                continue;
            }
            BatchParallelMonitor monitor = new BatchParallelMonitor(asyncTaskMonitor);
            monitor.setStartProgress(formStartProgress, formEndProgress);
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            queryEnvironment.setFormSchemeKey(jtableContext.getFormSchemeKey());
            IDataGather dataGather = this.dataGatherProvider.newDataGather(queryEnvironment);
            EntityViewDefine entityViewDefine = targetEntityInfo.getEntityViewDefine();
            GatherCondition condition = new GatherCondition();
            condition.setFormSchemeKey(jtableContext.getFormSchemeKey());
            condition.setSourceDimensions(dimensionValueSet);
            condition.setTargetDimension(dimensionValueSet);
            condition.setUnitView(entityViewDefine);
            if (StringUtils.isNotEmpty((String)periodCode)) {
                condition.setPeriodCode(periodCode);
            }
            condition.setRecursive(nodeCheckInfo.isRecursive());
            condition.setGatherDirection(GatherDirection.GATHER_TO_GROUP);
            condition.setGatherTables(gatherTableDefineList);
            condition.setPrecisionValue(nodeCheckInfo.getErrorRange());
            condition.setTaskKey(jtableContext.getTaskKey());
            dataGather.setGatherCondition(condition);
            dataGather.setMonitor((IMonitor)monitor);
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, jtableContext.getFormSchemeKey());
            executorContext.setEnv((IFmlExecEnvironment)environment);
            com.jiuqi.nr.data.engine.gather.NodeCheckResult result = null;
            try {
                result = dataGather.executeBatchNodeCheck((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, targetKeyList);
            }
            catch (Exception e3) {
                throw new NodeCheckException(new String[]{e3.getMessage()});
            }
            nodeCheckResultInfo.setTotalCheckUnit(nodeCheckResultInfo.getTotalCheckUnit() + result.getCheckedNodeCount());
            nodeCheckResultInfo.setUnPassUnit(nodeCheckResultInfo.getUnPassUnit() + result.getUnpassedNodeCount());
            ArrayList<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem> nodeCheckResultItems = new ArrayList<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>();
            for (CheckErrorItem item : result.getErrorItems()) {
                com.jiuqi.nr.dataentry.bean.NodeCheckResultItem nodeCheckResultItem = new com.jiuqi.nr.dataentry.bean.NodeCheckResultItem();
                nodeCheckResultItem.setFieldCode(item.getFieldCode());
                nodeCheckResultItem.setFieldTitle(item.getFieldTitle());
                nodeCheckResultItem.setParentValue(item.getParentValue());
                nodeCheckResultItem.setChildValue(item.getChildValue());
                nodeCheckResultItem.setMinusValue(item.getMinusValue());
                nodeCheckResultItem.setUnitTitle(item.getUnitTitle());
                nodeCheckResultItem.setUnitKey(item.getUnitKey());
                HashMap regionCache = (HashMap)nodeCheckfieldMap.get(item.getRegionKey());
                if (regionCache != null) {
                    nodeCheckResultItem.setNodeCheckFieldMessage((NodeCheckFieldMessage)regionCache.get(item.getFieldKey()));
                }
                nodeCheckResultItem.setDimensionIndex(dimensionList.indexOf(item.getUnitKey()));
                nodeCheckResultItem.setDimensionTitle(dimensionTitle);
                nodeCheckResultItems.add(nodeCheckResultItem);
            }
            Collections.sort(nodeCheckResultItems, new Comparator<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>(){

                @Override
                public int compare(com.jiuqi.nr.dataentry.bean.NodeCheckResultItem arg0, com.jiuqi.nr.dataentry.bean.NodeCheckResultItem arg1) {
                    String formOrder1;
                    String formOrder0 = arg0.getNodeCheckFieldMessage().getFormOrder();
                    if (!formOrder0.equals(formOrder1 = arg1.getNodeCheckFieldMessage().getFormOrder())) {
                        return formOrder0.compareTo(formOrder1);
                    }
                    return 0;
                }
            });
            if (nodeCheckResultItems.size() > 0) {
                nodeCheckResultItems.stream().collect(Collectors.groupingBy(com.jiuqi.nr.dataentry.bean.NodeCheckResultItem::getUnitTitle, Collectors.toList())).forEach((name, list) -> nodeCheckResult.merge((String)name, (List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>)list, this.nodeCheckResultMerge));
            }
            nodeCheckResultInfo.setDimensionList(subDimensionValueList);
        }
        if (asyncTaskMonitor != null) {
            String nodecheckSuccess = "node_check_success_info";
            String nodecheckFail = "node_check_fail_info";
            String objectToJson = JsonUtil.objectToJson((Object)nodeCheckResultInfo);
            if (nodeCheckResultInfo.getUnPassUnit() == 0) {
                asyncTaskMonitor.finish(nodecheckSuccess, (Object)objectToJson);
            } else {
                asyncTaskMonitor.error(nodecheckFail, null, objectToJson);
            }
        }
        return nodeCheckResultInfo;
    }

    private boolean isLeafEntity(String targetKey, EntityViewData targetEntityInfo, JtableContext jtableContext) {
        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        entityQueryByKeyInfo.setContext(jtableContext);
        entityQueryByKeyInfo.setEntityViewKey(targetEntityInfo.getKey());
        entityQueryByKeyInfo.setEntityKey(targetKey);
        EntityByKeyReturnInfo entityData = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
        if (entityData == null || entityData.getEntity() == null) {
            return false;
        }
        return entityData.getEntity().isLeaf();
    }

    @Override
    public NodeCheckResultInfo nodecheckResult(String asyncTaskID) {
        com.jiuqi.nr.data.gather.bean.NodeCheckResultInfo result = this.nodeCheckService.getNodeCheckResult(asyncTaskID);
        return this.convertNodeCheckResult(result);
    }

    @Override
    public OrderedNodeCheckResultInfo nodecheckOrderResult(String asyncTaskID) {
        AsyncTaskFuture taskFeture = (AsyncTaskFuture)BeanUtil.getBean(AsyncTaskFuture.class);
        NodeCheckResultInfo result = null;
        if (taskFeture != null) {
            Object detail = taskFeture.getDetail(asyncTaskID, NodeCheckResultInfo.class);
            result = (NodeCheckResultInfo)detail;
        }
        return this.convertNodeCheckResult(result);
    }

    @Override
    public ExportData nodecheckExport(String id) {
        NodeCheckResultInfo nodecheckResult = this.nodecheckResult(id);
        if (null != nodecheckResult) {
            Workbook nodeCheckExport = ExcelErrorUtil.nodeCheckExport(nodecheckResult);
            if (null != nodeCheckExport) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                try {
                    nodeCheckExport.write(os);
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                byte[] byteArray = os.toByteArray();
                ExportData formulaExportData = new ExportData("\u8282\u70b9\u68c0\u67e5\u7ed3\u679c.xlsx", byteArray);
                return formulaExportData;
            }
            return null;
        }
        return null;
    }

    private UnitNature getTargetUnitNature(String formSchemeKey, DimensionCollection collection) {
        IEntityTable entityTable;
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{formSchemeKey});
        }
        String entityId = formScheme.getDw();
        String entityDimension = this.entityMetaService.getDimensionName(entityId);
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String periodDimension = periodAdapter.getPeriodDimensionName();
        List dimensionCombines = collection.getDimensionCombinations();
        DimensionValueSet dimensionSet = collection.combineWithoutVarDim();
        String targetKey = String.valueOf(dimensionSet.getValue(entityDimension));
        String periodCode = String.valueOf(dimensionSet.getValue(periodDimension));
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        EntityViewDefine entityViewDefine = this.runtimeView.getViewByFormSchemeKey(formSchemeKey);
        try {
            entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, entityViewDefine, periodCode, formSchemeKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        IEntityRow entityRow = entityTable.findByEntityKey(targetKey);
        IFormTypeApplyService formTypeApplyService = (IFormTypeApplyService)BeanUtil.getBean(IFormTypeApplyService.class);
        EntityUnitNatureGetter entityFormGather = formTypeApplyService.getEntityFormTypeGetter(entityId);
        if (entityFormGather != null) {
            return entityFormGather.getUnitNature(entityRow);
        }
        return null;
    }

    private IEntityTable getEntityTable(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, EntityViewDefine unitView, String periodCode, String formSchemeKey) throws Exception {
        IEntityDataService entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
        IEntityQuery entityQuery = entityDataService.newEntityQuery();
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
        ExecutorContext executorContextInfo = new ExecutorContext(executorContext.getRuntimeController());
        executorContextInfo.setDefaultGroupName(executorContext.getDefaultGroupName());
        executorContextInfo.setJQReportModel(executorContext.isJQReportModel());
        executorContextInfo.setVarDimensionValueSet(executorContext.getVarDimensionValueSet());
        executorContextInfo.setEnv(executorContext.getEnv());
        executorContextInfo.setPeriodView(executorContext.getPeriodView());
        IEntityTable entityTable = this.dataEntityFullService.executeEntityReader(entityQuery, executorContextInfo, unitView, formSchemeKey).getEntityTable();
        return entityTable;
    }
}

