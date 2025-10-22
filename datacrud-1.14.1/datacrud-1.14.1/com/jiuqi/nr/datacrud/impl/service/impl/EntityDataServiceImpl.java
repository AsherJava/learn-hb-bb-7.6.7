/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IEntityViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.var.ReferRelation
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.datacrud.impl.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.datacrud.impl.EnumFillNode;
import com.jiuqi.nr.datacrud.impl.EnumLinkDTO;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.service.EntityDataService;
import com.jiuqi.nr.datacrud.spi.IEnumFillNode;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IEntityViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class EntityDataServiceImpl
implements EntityDataService {
    private static final Logger logger = LoggerFactory.getLogger(EntityDataServiceImpl.class);
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired(required=false)
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRunTimeViewController controller;
    @Autowired
    protected IEntityViewController entityViewController;

    @Override
    public IEntityQuery getEntityQuery() {
        return this.entityDataService.newEntityQuery();
    }

    @Override
    public ExecutorContext getExecutorContext() {
        ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
        executorContext.setJQReportModel(true);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.controller, this.runtimeController, this.entityViewRunTimeController);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        return executorContext;
    }

    @Override
    public IEntityTable getEntityTable(String entityId, DimensionCombination combination, String periodView) {
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        ExecutorContext executorContext = this.getExecutorContext();
        if (combination != null) {
            entityQuery.setMasterKeys(combination.toDimensionValueSet());
            executorContext.setVarDimensionValueSet(entityQuery.getMasterKeys());
        }
        if (periodView != null) {
            executorContext.setPeriodView(periodView);
        }
        try {
            return entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            logger.error("\u5b9e\u4f53\u6846\u67b6\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u5931\u8d25", e);
            throw new CrudException(4201);
        }
    }

    @Override
    public List<List<String>> fillEnum(RegionRelation regionRelation, DimensionCombination combination) {
        return this.fillEnum(regionRelation, combination, null);
    }

    @Override
    public List<List<String>> fillEnum(RegionRelation regionRelation, DimensionCombination combination, List<IEnumFillNode> filterNodes) {
        List<MetaData> filledEnumLinks = regionRelation.getFilledEnumLinks();
        if (CollectionUtils.isEmpty(filledEnumLinks)) {
            return Collections.emptyList();
        }
        List<EnumFillNode> root = this.buildTree(regionRelation, combination, filterNodes);
        return this.fillTree(root, filledEnumLinks.size());
    }

    public List<EnumFillNode> buildTree(RegionRelation regionRelation, DimensionCombination combination, List<IEnumFillNode> filterNodes) {
        List<MetaData> filledEnumLinks = regionRelation.getFilledEnumLinks();
        if (CollectionUtils.isEmpty(filledEnumLinks)) {
            return Collections.emptyList();
        }
        EnumFillNode root = new EnumFillNode(null, null);
        ArrayList<EnumFillNode> parentEnumValues = new ArrayList<EnumFillNode>();
        if (filterNodes != null) {
            for (IEnumFillNode rootNode : filterNodes) {
                EnumFillNode enumFillNode = new EnumFillNode(rootNode);
                parentEnumValues.add(enumFillNode);
                while (enumFillNode.getLeft() != null) {
                    EnumFillNode leftNode = new EnumFillNode(enumFillNode.getLeft());
                    enumFillNode.setLeftNode(leftNode);
                    enumFillNode = leftNode;
                }
                root.getChildren().add(enumFillNode);
            }
        } else {
            parentEnumValues.add(root);
        }
        for (MetaData metaData : filledEnumLinks) {
            DataField dataField = metaData.getDataField();
            String entityId = null;
            if (dataField != null) {
                entityId = dataField.getRefDataEntityKey();
            }
            if (entityId == null) continue;
            EnumLinkDTO enumLink = metaData.getEnumLink();
            boolean allChildren = true;
            if (enumLink != null) {
                allChildren = "1".equals(enumLink.getType());
            }
            String parentKey = null;
            List<ReferRelation> referRelations = this.buildReferRelations(metaData, regionRelation);
            List<String> cacheMetaValues = null;
            ArrayList<EnumFillNode> parentEnumValuesTemp = parentEnumValues;
            parentEnumValues = new ArrayList();
            for (EnumFillNode currentValue : parentEnumValuesTemp) {
                List<String> metaValues;
                if (currentValue.getKey() != null && !allChildren) {
                    parentKey = currentValue.getKey();
                }
                if (cacheMetaValues != null) {
                    metaValues = cacheMetaValues;
                } else {
                    List<ReferRelation> reReferRelations = this.reBuildReferRelations(referRelations, currentValue);
                    metaValues = this.getEnumValues(regionRelation, metaData, combination, reReferRelations, parentKey, allChildren);
                    if (currentValue.getKey() == null && allChildren) {
                        cacheMetaValues = metaValues;
                    }
                }
                if (CollectionUtils.isEmpty(metaValues)) continue;
                for (String metaValue : metaValues) {
                    EnumFillNode node = new EnumFillNode(entityId, metaValue);
                    parentEnumValues.add(node);
                    node.setLeftNode(currentValue);
                    currentValue.getChildren().add(node);
                }
            }
        }
        return root.getChildren();
    }

    private List<ReferRelation> reBuildReferRelations(List<ReferRelation> referRelations, EnumFillNode parentNode) {
        if (parentNode == null) {
            return new ArrayList<ReferRelation>();
        }
        ArrayList<ReferRelation> relations = new ArrayList<ReferRelation>();
        for (ReferRelation referRelation : referRelations) {
            referRelation.setRange(null);
            boolean add = false;
            for (IEnumFillNode parent = parentNode; parent != null && parent.getKey() != null && parent.getEntityId() != null; parent = parent.getLeft()) {
                if (!parent.getEntityId().equals(referRelation.getViewDefine().getEntityId())) continue;
                referRelation.addRange(parent.getKey());
                add = true;
            }
            if (!add) continue;
            relations.add(referRelation);
        }
        return relations;
    }

    private List<ReferRelation> buildReferRelations(MetaData dataLink, RegionRelation regionRelation) {
        List<String> preLinks;
        ArrayList<ReferRelation> referRelations = new ArrayList<ReferRelation>();
        EnumLinkDTO enumLink = dataLink.getEnumLink();
        if (enumLink != null && !CollectionUtils.isEmpty(preLinks = enumLink.getPreLinks())) {
            for (String preLink : preLinks) {
                MetaData metaDataByLink = regionRelation.getMetaDataByLink(preLink);
                if (metaDataByLink == null) continue;
                ReferRelation relation = new ReferRelation();
                DataLinkDefine link = metaDataByLink.getDataLinkDefine();
                EntityViewDefine viewDefine = this.controller.getViewByLinkDefineKey(link.getKey());
                relation.setViewDefine(viewDefine);
                referRelations.add(relation);
            }
        }
        return referRelations;
    }

    private List<List<String>> fillTree(List<EnumFillNode> root, int maxDeepSize) {
        if (root == null) {
            return Collections.emptyList();
        }
        ArrayList<List<String>> allRows = new ArrayList<List<String>>();
        for (EnumFillNode node : root) {
            List<List<String>> path = this.getPath(node, maxDeepSize, 1);
            allRows.addAll(path);
        }
        return allRows;
    }

    private List<List<String>> getPath(EnumFillNode node, int maxDepth, int currentDepth) {
        ArrayList<List<String>> paths = new ArrayList<List<String>>();
        if (node.getChildren().isEmpty()) {
            ArrayList<String> list = new ArrayList<String>(maxDepth);
            list.add(node.getKey());
            for (int i = currentDepth; i < maxDepth; ++i) {
                list.add("");
            }
            paths.add(list);
        } else {
            for (EnumFillNode child : node.getChildren()) {
                List<List<String>> childPaths = this.getPath(child, maxDepth, currentDepth + 1);
                for (List<String> path : childPaths) {
                    path.add(0, node.getKey());
                }
                paths.addAll(childPaths);
            }
        }
        return paths;
    }

    private List<String> getEnumValues(RegionRelation regionRelation, MetaData dataLink, DimensionCombination combination, List<ReferRelation> referRelations, String parentKey, boolean allChildren) {
        IEntityTable iEntityTable;
        String dimName;
        DataLinkDefine linkDefine = dataLink.getDataLinkDefine();
        DataField dataField = dataLink.getDataField();
        if (dataField == null) {
            throw new CrudException(4201);
        }
        IEntityQuery entityQuery = this.getEntityQuery();
        EntityViewDefine entityViewDefine = this.controller.getViewByLinkDefineKey(linkDefine.getKey());
        entityQuery.setEntityView(entityViewDefine);
        if (referRelations != null) {
            for (ReferRelation referRelation : referRelations) {
                entityQuery.addReferRelation(referRelation);
            }
        }
        DimensionValueSet masterSet = combination.toDimensionValueSet();
        String entityId = entityViewDefine.getEntityId();
        try {
            dimName = this.entityViewController.getDimensionNameByViewKey(entityId);
        }
        catch (Exception e) {
            logger.error("\u7ef4\u5ea6\u67e5\u8be2\u5931\u8d25,\u8bf7\u68c0\u67e5\u53c2\u6570 {}", (Object)entityId, (Object)e);
            throw new CrudException(4302, "\u7ef4\u5ea6\u67e5\u8be2\u5931\u8d25,\u8bf7\u68c0\u67e5\u53c2\u6570" + entityId);
        }
        if (regionRelation.getDwDimName().equals(dimName)) {
            masterSet.clearValue(dimName);
        }
        entityQuery.setMasterKeys(masterSet);
        FormSchemeDefine schemeDefine = regionRelation.getFormSchemeDefine();
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(schemeDefine.getDw());
        if (combination.hasValue(entityDefine.getDimensionName())) {
            Object value = combination.getValue(entityDefine.getDimensionName());
            entityQuery.setIsolateCondition(value.toString());
        }
        if (combination.hasValue("DATATIME")) {
            Object period = combination.getValue("DATATIME");
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(schemeDefine.getDateTime());
            try {
                Date versionDate = periodProvider.getPeriodDateRegion(period.toString())[1];
                entityQuery.setQueryVersionDate(versionDate);
            }
            catch (Exception e) {
                logger.warn("\u83b7\u53d6\u65f6\u671f\u5931\u8d25", e);
            }
        }
        entityQuery.sorted(true);
        ExecutorContext context = new ExecutorContext(this.runtimeController);
        context.setJQReportModel(true);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.controller, this.runtimeController, this.entityViewRunTimeController, regionRelation.getFormSchemeDefine().getKey());
        context.setEnv((IFmlExecEnvironment)environment);
        context.setPeriodView(regionRelation.getFormSchemeDefine().getDateTime());
        context.setVarDimensionValueSet(entityQuery.getMasterKeys());
        try {
            iEntityTable = allChildren ? entityQuery.executeFullBuild((IContext)context) : entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            logger.warn("\u5b9e\u4f53\u67e5\u8be2\u51fa\u9519", e);
            throw new CrudException(4201);
        }
        ArrayList<String> list = new ArrayList<String>();
        if (StringUtils.hasLength(parentKey)) {
            this.appendChildren(list, iEntityTable, parentKey, allChildren);
        } else {
            List rootRows = iEntityTable.getRootRows();
            for (IEntityRow rootRow : rootRows) {
                list.add(rootRow.getEntityKeyData());
                if (!allChildren) continue;
                this.appendChildren(list, iEntityTable, rootRow.getEntityKeyData(), true);
            }
        }
        return list;
    }

    private void appendChildren(List<String> list, IEntityTable iEntityTable, String entityKeyData, boolean allChildren) {
        List childRows = iEntityTable.getChildRows(entityKeyData);
        for (IEntityRow childRow : childRows) {
            list.add(childRow.getEntityKeyData());
            if (!allChildren) continue;
            this.appendChildren(list, iEntityTable, childRow.getEntityKeyData(), true);
        }
    }
}

