/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.collection.ArrayMap
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.gather.bean.NodeCheckParam
 *  com.jiuqi.nr.data.gather.bean.NodeCheckResultItem
 *  com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResult
 *  com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResultItemInfo
 *  com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor
 *  com.jiuqi.nr.data.gather.refactor.monitor.impl.DefaultMonitor
 *  com.jiuqi.nr.data.gather.refactor.service.NodeCheckService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.finalaccountsaudit.common.UUIDMerger
 *  com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.multcheck2.bean.MultcheckItem
 *  com.jiuqi.nr.multcheck2.bean.MultcheckScheme
 *  com.jiuqi.nr.multcheck2.common.CheckRestultState
 *  com.jiuqi.nr.multcheck2.provider.CheckItemParam
 *  com.jiuqi.nr.multcheck2.provider.CheckItemResult
 *  com.jiuqi.nr.multcheck2.provider.FailedOrgInfo
 *  com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider
 *  com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO
 *  com.jiuqi.nr.multcheck2.provider.MultcheckContext
 *  com.jiuqi.nr.multcheck2.provider.PluginInfo
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.datacheck.nodecheck;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.collection.ArrayMap;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.gather.bean.NodeCheckParam;
import com.jiuqi.nr.data.gather.bean.NodeCheckResultItem;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResult;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResultItemInfo;
import com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor;
import com.jiuqi.nr.data.gather.refactor.monitor.impl.DefaultMonitor;
import com.jiuqi.nr.data.gather.refactor.service.NodeCheckService;
import com.jiuqi.nr.datacheck.common.SerializeUtil;
import com.jiuqi.nr.datacheck.nodecheck.NodeCheckInfo;
import com.jiuqi.nr.datacheck.nodecheck.bean.NodeCheckResultVO;
import com.jiuqi.nr.datacheck.nodecheck.bean.UnitCheckResultItem;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.finalaccountsaudit.common.UUIDMerger;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckResDao;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.CheckRestultState;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.FailedOrgInfo;
import com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider;
import com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO;
import com.jiuqi.nr.multcheck2.provider.MultcheckContext;
import com.jiuqi.nr.multcheck2.provider.PluginInfo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class NodeCheckProvider
implements IMultcheckItemProvider {
    private static final Logger logger = LoggerFactory.getLogger(NodeCheckProvider.class);
    private static final String TYPE = "nodecheck";
    private static final String selectMode = "allForms";
    @Autowired
    NodeCheckService nodeCheckService;
    @Autowired
    IJtableParamService jtableParamService;
    @Autowired
    DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    MultCheckResDao multCheckResDao;
    @Autowired
    private IEntityDataService entityDataService;
    @Resource
    IRunTimeViewController runtimeView;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;

    public String getType() {
        return TYPE;
    }

    public String getTitle() {
        return "\u8282\u70b9\u68c0\u67e5";
    }

    public double getOrder() {
        return 3.0;
    }

    public PluginInfo getPropertyPluginInfo() {
        return new PluginInfo("@nr", "nr-datacheck-nodecheck-plugin", "Config");
    }

    public String getItemDescribe(String formSchemeKey, MultcheckItem item) {
        if (!StringUtils.hasText(item.getConfig())) {
            return "\u672a\u9009\u62e9\u62a5\u8868";
        }
        try {
            NodeCheckInfo config = SerializeUtil.deserializeFromJson(item.getConfig(), NodeCheckInfo.class);
            if (config.getSelectMode().equals(selectMode)) {
                return "\u9009\u62e9\u62a5\u8868 | \u6240\u6709\u62a5\u8868";
            }
            List<String> formKeys = config.getFormKeys();
            if (!CollectionUtils.isEmpty(formKeys)) {
                return "\u9009\u62e9\u62a5\u8868 | \u5df2\u9009 <span class=\"mtc-item-number-cls\">" + formKeys.size() + "</span>\u5f20\u62a5\u8868";
            }
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\uff1a\u8282\u70b9\u68c0\u67e5\u6a21\u578b\u5f02\u5e38" + item.getKey(), e);
        }
        return "\u9009\u62e9\u62a5\u8868 | \u672a\u9009\u62e9\u62a5\u8868";
    }

    public MultCheckItemDTO copyCheckItem(MultcheckScheme sourceScheme, MultcheckItem sourceItem, String targetFormSchemeKey) {
        MultCheckItemDTO dto = new MultCheckItemDTO();
        BeanUtils.copyProperties(sourceItem, dto);
        return dto;
    }

    public String getRunItemDescribe(String formSchemeKey, MultcheckItem item) {
        return this.getItemDescribe(formSchemeKey, item);
    }

    public PluginInfo getRunPropertyPluginInfo() {
        return new PluginInfo("@nr", "nr-datacheck-nodecheck-plugin", "RunConfig");
    }

    public CheckItemResult runCheck(CheckItemParam param) {
        MultcheckContext context = param.getContext();
        DimensionCollection dims = context.getDims();
        MultcheckItem checkItem = param.getCheckItem();
        CheckItemResult result = new CheckItemResult();
        result.setRunId(param.getRunId());
        result.setRunConfig(checkItem.getConfig());
        NodeCheckInfo checkInfo = null;
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, context.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        IEntityTable entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, dwEntity.getEntityViewDefine(), context.getPeriod(), context.getFormSchemeKey());
        try {
            checkInfo = SerializeUtil.deserializeFromJson(checkItem.getConfig(), NodeCheckInfo.class);
        }
        catch (Exception e) {
            result.setResult(CheckRestultState.FAIL);
            logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\uff1a\u8282\u70b9\u68c0\u67e5\u6a21\u578b\u5f02\u5e38" + checkItem.getKey(), e);
        }
        HashSet<String> successOrgs = new HashSet<String>();
        HashSet<String> failedOrgs = new HashSet<String>();
        HashSet<String> ignoreOrgs = new HashSet<String>();
        ArrayList<UnitCheckResultItem> unitCheckResultItems = new ArrayList<UnitCheckResultItem>();
        if (checkInfo != null) {
            NodeCheckParam nodeCheckParam = new NodeCheckParam();
            nodeCheckParam.setTaskKey(context.getTaskKey());
            nodeCheckParam.setRecursive(checkInfo.isRecursive());
            nodeCheckParam.setFormSchemeKey(context.getFormSchemeKey());
            String formKeys = null;
            if (!CollectionUtils.isEmpty(checkInfo.getFormKeys())) {
                formKeys = String.join((CharSequence)";", checkInfo.getFormKeys());
            }
            nodeCheckParam.setFormKeys(formKeys);
            nodeCheckParam.setErrorRange(checkInfo.getErrorRange());
            nodeCheckParam.setDimensionCollection(dims);
            DefaultMonitor monitor = new DefaultMonitor(param.getAsyncTaskMonitor());
            List nodeCheckResults = this.nodeCheckService.batchNodeCheck(nodeCheckParam, (IGatherServiceMonitor)monitor);
            if (monitor.isCancel()) {
                return null;
            }
            Iterator iterator = nodeCheckResults.iterator();
            while (iterator.hasNext()) {
                NodeCheckResult nodeCheckResult = (NodeCheckResult)iterator.next();
                String mdCode = nodeCheckResult.getUnitKey();
                if (nodeCheckResult.isLeafUnit()) {
                    ignoreOrgs.add(mdCode);
                    continue;
                }
                Set resultItemInfos = nodeCheckResult.getResultItemInfos();
                if (resultItemInfos.isEmpty()) {
                    successOrgs.add(mdCode);
                    continue;
                }
                UnitCheckResultItem unitCheckResultItem = new UnitCheckResultItem();
                unitCheckResultItem.setUnitKey(mdCode);
                unitCheckResultItem.setUnitTitle(entityTable.findByEntityKey(mdCode).getTitle());
                Map<String, String> dimValue = this.getMultiDimValue(nodeCheckResult.getDimensionValueSet());
                unitCheckResultItem.setDimValue(dimValue);
                LinkedList<NodeCheckResultItemInfo> orderResult = new LinkedList<NodeCheckResultItemInfo>();
                if (resultItemInfos.size() > 1) {
                    Object itemInfo2;
                    HashMap<String, NodeCheckResultItemInfo> map = new HashMap<String, NodeCheckResultItemInfo>();
                    for (Object itemInfo2 : resultItemInfos) {
                        String unitKey = ((NodeCheckResultItem)itemInfo2.getNodeCheckResultItems().get(0)).getUnitKey();
                        map.put(unitKey, (NodeCheckResultItemInfo)itemInfo2);
                    }
                    IEntityTable entityTableByOrder = this.getEntityTableByOrder((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, dwEntity, context, new ArrayList<String>(map.keySet()));
                    itemInfo2 = entityTableByOrder.getAllRows().iterator();
                    while (itemInfo2.hasNext()) {
                        IEntityRow row = (IEntityRow)itemInfo2.next();
                        NodeCheckResultItemInfo info = (NodeCheckResultItemInfo)map.get(row.getEntityKeyData());
                        if (info == null) continue;
                        orderResult.add(info);
                    }
                } else {
                    orderResult.addAll(resultItemInfos);
                }
                LinkedList<NodeCheckResultItem> infos = new LinkedList<NodeCheckResultItem>();
                LinkedList<Map<String, DimensionValue>> dimensions = new LinkedList<Map<String, DimensionValue>>();
                for (int i = 0; i < orderResult.size(); ++i) {
                    NodeCheckResultItemInfo nodeCheckResultItemInfo = (NodeCheckResultItemInfo)orderResult.get(i);
                    dimensions.add(nodeCheckResultItemInfo.getDimensionValue());
                    for (NodeCheckResultItem item : nodeCheckResultItemInfo.getNodeCheckResultItems()) {
                        item.setDimensionIndex(i);
                        infos.add(item);
                    }
                }
                unitCheckResultItem.setDimensions(dimensions);
                unitCheckResultItem.setResultItems(infos);
                unitCheckResultItems.add(unitCheckResultItem);
                failedOrgs.add(mdCode);
            }
        }
        NodeCheckResultVO nodeCheckResultVO = new NodeCheckResultVO();
        HashSet temp = new HashSet(successOrgs);
        temp.retainAll(failedOrgs);
        successOrgs.removeAll(temp);
        nodeCheckResultVO.setTotalCheckUnit(successOrgs.size() + failedOrgs.size());
        HashMap<String, FailedOrgInfo> failedOrginfos = new HashMap<String, FailedOrgInfo>();
        for (UnitCheckResultItem item : unitCheckResultItems) {
            LinkedList<NodeCheckResultItem> resultItems = item.getResultItems();
            if (CollectionUtils.isEmpty(resultItems)) continue;
            FailedOrgInfo failedOrgInfo = new FailedOrgInfo();
            failedOrgInfo.setDesc("\u5f53\u524d\u5355\u4f4d\u8282\u70b9\u68c0\u67e5\u5b58\u5728\u9519\u8bef\u6307\u6807\uff01");
            failedOrginfos.put(item.getUnitKey(), failedOrgInfo);
        }
        nodeCheckResultVO.setUnPassUnit(failedOrgs.size());
        nodeCheckResultVO.setResultItems(unitCheckResultItems);
        String info = null;
        try {
            info = SerializeUtil.serializeToJson(nodeCheckResultVO);
        }
        catch (Exception e) {
            result.setResult(CheckRestultState.WARN);
            logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\uff1a\u8282\u70b9\u68c0\u67e5\u6a21\u7ed3\u679c\u5e8f\u5217\u5316\u5f02\u5e38{}", (Object)checkItem.getKey(), (Object)e);
        }
        this.multCheckResDao.insert(UUIDMerger.merge((String)param.getRunId(), (String)checkItem.getKey()), info);
        result.setSuccessOrgs(new ArrayList(successOrgs));
        result.setFailedOrgs(failedOrginfos);
        result.setIgnoreOrgs(new ArrayList(ignoreOrgs));
        if (nodeCheckResultVO.getUnPassUnit() > 0) {
            result.setResult(CheckRestultState.FAIL);
        } else {
            result.setResult(CheckRestultState.SUCCESS);
        }
        return result;
    }

    private Map<String, String> getMultiDimValue(DimensionValueSet dimensionValueSet) {
        ArrayMap result = new ArrayMap();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            result.put(dimensionValueSet.getName(i), String.valueOf(dimensionValueSet.getValue(i)));
        }
        return result;
    }

    public PluginInfo getResultPlugin() {
        return new PluginInfo("@nr", "nr-datacheck-nodecheck-plugin", "Result");
    }

    public MultCheckItemDTO getDefaultCheckItem(String formSchemeKey) {
        MultCheckItemDTO result = new MultCheckItemDTO();
        result.setTitle("\u8282\u70b9\u68c0\u67e5");
        result.setType(TYPE);
        NodeCheckInfo config = new NodeCheckInfo();
        config.setSelectMode(selectMode);
        config.setErrorRange(BigDecimal.ZERO);
        config.setRecursive(false);
        config.setFormKeys(new ArrayList<String>());
        String jsonConfig = null;
        try {
            jsonConfig = SerializeUtil.serializeToJson(config);
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\uff1a\uff1a\u8282\u70b9\u68c0\u67e5\u9ed8\u8ba4\u914d\u7f6e\u5e8f\u5217\u5316\u5f02\u5e38", e);
        }
        result.setConfig(jsonConfig);
        return result;
    }

    public void cleanCheckItemTables(Date date) {
        this.multCheckResDao.deleteByCreatedAt(date);
    }

    private IEntityTable getEntityTable(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, EntityViewDefine unitView, String periodCode, String formSchemeKey) {
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(unitView);
        DimensionValueSet masterKeys = new DimensionValueSet();
        if (!com.jiuqi.bi.util.StringUtils.isEmpty((String)periodCode)) {
            masterKeys.setValue("DATATIME", (Object)periodCode);
        }
        entityQuery.setMasterKeys(masterKeys);
        executorContext.setVarDimensionValueSet(masterKeys);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeKey);
        executorContext.setPeriodView(formScheme.getDateTime());
        IEntityTable entityTable = null;
        try {
            entityTable = entityQuery.executeFullBuild((IContext)executorContext);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5b9e\u4f53\u5931\u8d25\uff01");
        }
        return entityTable;
    }

    private IEntityTable getEntityTableByOrder(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, EntityViewData entityViewData, MultcheckContext context, List<String> entityKeys) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(context.getFormSchemeKey());
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewData.getEntityViewDefine());
        DimensionValueSet masterKeys = new DimensionValueSet();
        if (!com.jiuqi.bi.util.StringUtils.isEmpty((String)context.getPeriod())) {
            masterKeys.setValue("DATATIME", (Object)context.getPeriod());
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

    private boolean isLeafEntity(IEntityTable entityTable, String targetKey) {
        if (entityTable == null) {
            return true;
        }
        return CollectionUtils.isEmpty(entityTable.getChildRows(targetKey));
    }

    public String getEntryView() {
        return "nodeCheckResult2";
    }
}

