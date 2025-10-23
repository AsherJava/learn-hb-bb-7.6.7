/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.task.api.service.entity.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.task.api.service.entity.IEntityDataQueryService;
import com.jiuqi.nr.task.api.service.entity.dto.EntityDataDTO;
import com.jiuqi.nr.task.api.service.entity.dto.EntityQueryDTO;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class EntityDataServiceImpl
implements IEntityDataQueryService {
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController runTimeViewController;

    @Override
    public List<UITreeNode<EntityDataDTO>> initEntityTree(String entityId) {
        IEntityTable entityTable;
        ArrayList<UITreeNode<EntityDataDTO>> treeData = new ArrayList<UITreeNode<EntityDataDTO>>();
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        iEntityQuery.setEntityView(entityViewDefine);
        iEntityQuery.lazyQuery();
        iEntityQuery.sorted(true);
        ExecutorContext executorContext = new ExecutorContext(this.runTimeViewController);
        try {
            entityTable = iEntityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef", e);
        }
        List rootRows = entityTable.getRootRows();
        if (CollectionUtils.isEmpty(rootRows)) {
            return treeData;
        }
        for (IEntityRow rootRow : rootRows) {
            UITreeNode<EntityDataDTO> node = this.buildNode(rootRow);
            treeData.add(node);
        }
        return treeData;
    }

    private UITreeNode<EntityDataDTO> buildNode(IEntityRow entityRow) {
        EntityDataDTO entityDataDTO = this.getDataDTO(entityRow);
        UITreeNode<EntityDataDTO> node = new UITreeNode<EntityDataDTO>(entityDataDTO);
        if (entityRow.hasChildren()) {
            node.setLeaf(false);
        }
        node.setKey(entityRow.getEntityKeyData());
        node.setTitle(entityRow.getCode() + " | " + entityRow.getTitle());
        return node;
    }

    private EntityDataDTO getDataDTO(IEntityRow entityRow) {
        EntityDataDTO entityDataDTO = new EntityDataDTO();
        entityDataDTO.setKey(entityRow.getEntityKeyData());
        entityDataDTO.setCode(entityRow.getCode());
        entityDataDTO.setTitle(entityRow.getTitle());
        entityDataDTO.setParent(entityRow.getParentEntityKey());
        entityDataDTO.setPath(Arrays.asList(entityRow.getParentsEntityKeyDataPath()));
        return entityDataDTO;
    }

    @Override
    public List<UITreeNode<EntityDataDTO>> loadChildren(EntityQueryDTO queryDTO) {
        IEntityTable entityTable;
        ArrayList<UITreeNode<EntityDataDTO>> treeData = new ArrayList<UITreeNode<EntityDataDTO>>();
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(queryDTO.getEntityId());
        iEntityQuery.setEntityView(entityViewDefine);
        iEntityQuery.lazyQuery();
        iEntityQuery.sorted(true);
        ExecutorContext executorContext = new ExecutorContext(this.runTimeViewController);
        try {
            entityTable = iEntityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef", e);
        }
        List entityRows = entityTable.getChildRows(queryDTO.getEntityKey());
        if (CollectionUtils.isEmpty(entityRows)) {
            return treeData;
        }
        for (IEntityRow entityRow : entityRows) {
            UITreeNode<EntityDataDTO> node = this.buildNode(entityRow);
            if (!CollectionUtils.isEmpty(queryDTO.getSelectedKeys()) && queryDTO.getSelectedKeys().contains(entityRow.getEntityKeyData())) {
                node.setChecked(true);
            }
            if (StringUtils.hasText(queryDTO.getEntityKey()) && queryDTO.getEntityKey().equals(entityRow.getEntityKeyData())) {
                node.setSelected(true);
            }
            treeData.add(node);
        }
        return treeData;
    }

    @Override
    public List<EntityDataDTO> searchEntityData(EntityQueryDTO queryDTO) {
        IEntityTable entityTable;
        ArrayList<EntityDataDTO> searchResult = new ArrayList<EntityDataDTO>();
        StringBuilder expression = new StringBuilder();
        String keyWords = queryDTO.getKeyWords();
        if (StringUtils.hasText(keyWords)) {
            IEntityModel entityModel = this.entityMetaService.getEntityModel(queryDTO.getEntityId());
            IEntityAttribute nameField = entityModel.getNameField();
            IEntityAttribute codeField = entityModel.getCodeField();
            expression.append(nameField.getCode()).append(" LIKE '%").append(keyWords).append("%' ").append(" OR ").append(codeField.getCode()).append(" LIKE '%").append(keyWords).append("%'");
        }
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(queryDTO.getEntityId(), expression.toString());
        iEntityQuery.setEntityView(entityViewDefine);
        iEntityQuery.lazyQuery();
        iEntityQuery.sorted(true);
        ExecutorContext executorContext = new ExecutorContext(this.runTimeViewController);
        try {
            entityTable = iEntityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef", e);
        }
        List allRows = entityTable.getAllRows();
        for (IEntityRow entityRow : allRows) {
            searchResult.add(this.getDataDTO(entityRow));
        }
        return searchResult;
    }

    @Override
    public List<UITreeNode<EntityDataDTO>> locationEntityDataTree(EntityQueryDTO queryDTO) {
        IEntityTable entityTable;
        String entityKey = queryDTO.getEntityKey();
        if (!StringUtils.hasText(entityKey)) {
            return this.initEntityTree(queryDTO.getEntityId());
        }
        ArrayList<UITreeNode<EntityDataDTO>> treeData = new ArrayList<UITreeNode<EntityDataDTO>>();
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(queryDTO.getEntityId());
        iEntityQuery.setEntityView(entityViewDefine);
        iEntityQuery.lazyQuery();
        iEntityQuery.sorted(true);
        ExecutorContext executorContext = new ExecutorContext(this.runTimeViewController);
        try {
            entityTable = iEntityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef", e);
        }
        List<String> locationPath = queryDTO.getLocationPath();
        if (locationPath == null) {
            IEntityRow entityRow = entityTable.findByEntityKey(queryDTO.getEntityKey());
            locationPath = Arrays.stream(entityRow.getParentsEntityKeyDataPath()).collect(Collectors.toList());
        }
        List rootRows = entityTable.getRootRows();
        for (IEntityRow rootRow : rootRows) {
            UITreeNode<EntityDataDTO> node = this.buildNode(rootRow);
            if (rootRow.hasChildren()) {
                node.setLeaf(false);
            }
            if (!CollectionUtils.isEmpty(locationPath) && locationPath.contains(rootRow.getEntityKeyData())) {
                List allChildRows = entityTable.getAllChildRows(rootRow.getEntityKeyData());
                Map<String, List<IEntityRow>> parentMap = allChildRows.stream().collect(Collectors.groupingBy(IEntityItem::getParentEntityKey));
                locationPath.remove(0);
                List childrenRows = this.expandAndLocationNode(rootRow, parentMap, locationPath, queryDTO.getSelectedKeys(), queryDTO.getEntityKey());
                node.setChildren(childrenRows);
                node.setExpand(true);
            }
            if (rootRow.getEntityKeyData().equals(queryDTO.getEntityKey())) {
                node.setSelected(true);
            }
            treeData.add(node);
        }
        return treeData;
    }

    @Override
    public List<EntityDataDTO> query(EntityQueryDTO queryDTO) {
        IEntityTable entityTable;
        ArrayList<EntityDataDTO> queryResult = new ArrayList<EntityDataDTO>();
        String dimensionName = this.entityMetaService.getDimensionName(queryDTO.getEntityId());
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(queryDTO.getEntityId());
        iEntityQuery.setEntityView(entityViewDefine);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(dimensionName, queryDTO.getSelectedKeys());
        iEntityQuery.setMasterKeys(dimensionValueSet);
        ExecutorContext executorContext = new ExecutorContext(this.runTimeViewController);
        try {
            entityTable = iEntityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException("\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef", e);
        }
        List allRows = entityTable.getAllRows();
        for (IEntityRow entityRow : allRows) {
            queryResult.add(this.getDataDTO(entityRow));
        }
        return queryResult;
    }

    private List<UITreeNode<EntityDataDTO>> expandAndLocationNode(IEntityRow entityRow, Map<String, List<IEntityRow>> parentMap, List<String> locationPath, List<String> selectedKeys, String locationKey) {
        ArrayList<UITreeNode<EntityDataDTO>> nodes = new ArrayList<UITreeNode<EntityDataDTO>>();
        List<IEntityRow> childrenRows = parentMap.get(entityRow.getEntityKeyData());
        for (IEntityRow childrenRow : childrenRows) {
            UITreeNode<EntityDataDTO> node = this.buildNode(childrenRow);
            if (childrenRow.hasChildren()) {
                node.setLeaf(false);
            }
            if (!CollectionUtils.isEmpty(selectedKeys) && selectedKeys.contains(childrenRow.getEntityKeyData())) {
                node.setChecked(true);
            }
            if (!CollectionUtils.isEmpty(locationPath) && locationPath.get(0).equals(childrenRow.getEntityKeyData())) {
                locationPath.remove(0);
                List childrenNodes = this.expandAndLocationNode(childrenRow, parentMap, locationPath, selectedKeys, locationKey);
                node.setChildren(childrenNodes);
                node.setExpand(true);
            }
            if (childrenRow.getEntityKeyData().equals(locationKey)) {
                node.setSelected(true);
            }
            nodes.add(node);
        }
        return nodes;
    }
}

