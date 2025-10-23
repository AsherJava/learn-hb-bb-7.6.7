/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.entity.bo.EntitySearchBO
 *  com.jiuqi.nr.entity.common.NodeType
 *  com.jiuqi.nr.entity.config.IsolatingBaseDataConfig
 *  com.jiuqi.nr.entity.internal.service.AdapterService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityGroup
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.task.api.service.entity.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.entity.bo.EntitySearchBO;
import com.jiuqi.nr.entity.common.NodeType;
import com.jiuqi.nr.entity.config.IsolatingBaseDataConfig;
import com.jiuqi.nr.entity.internal.service.AdapterService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityGroup;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.task.api.service.entity.IEntityTreeQueryService;
import com.jiuqi.nr.task.api.service.entity.vo.EntityDataQueryVO;
import com.jiuqi.nr.task.api.service.entity.vo.EntityTreeNode;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class EntityTreeServiceImpl
implements IEntityTreeQueryService {
    private static final Logger logger = LoggerFactory.getLogger(EntityTreeServiceImpl.class);
    private static final Integer SHOW_ALL = 1;
    private static final Integer SHOW_CUMULATIVE = 2;
    private static final Integer SHOW_UN_CUMULATIVE = 3;
    @Autowired
    private IEntityMetaService metaService;
    @Autowired
    private AdapterService adapterService;
    @Autowired
    private IsolatingBaseDataConfig isolatingBaseData;

    @Override
    public List<UITreeNode<EntityTreeNode>> treeInit(EntityDataQueryVO actionVo) {
        this.filterExistSelectedKeys(actionVo);
        return this.buildRootNode(actionVo);
    }

    private List<UITreeNode<EntityTreeNode>> buildRootNode(EntityDataQueryVO actionVo) {
        ArrayList<UITreeNode<EntityTreeNode>> res = new ArrayList<UITreeNode<EntityTreeNode>>();
        List<String> pathKeys = this.getAllPathKeys(actionVo.getSelectedKeys());
        List rootEntityGroups = this.metaService.getRootEntityGroups();
        List<IEntityGroup> filterRootEntityGroups = this.filterEntityGroups(rootEntityGroups, actionVo);
        int flag = 0;
        for (IEntityGroup rootGroup : filterRootEntityGroups) {
            UITreeNode<EntityTreeNode> rootTreeNode = this.buildGroupNode(rootGroup);
            List childrenGroup = this.getChildrenGroups(rootGroup.getId(), pathKeys, actionVo.getShowContent());
            if (!CollectionUtils.isEmpty(childrenGroup)) {
                rootTreeNode.setChildren(childrenGroup);
                if (flag == 0 && actionVo.isExpandFirstNode()) {
                    rootTreeNode.setExpand(true);
                }
            } else {
                rootTreeNode.setLeaf(false);
            }
            ++flag;
            res.add(rootTreeNode);
        }
        return res;
    }

    private void filterExistSelectedKeys(EntityDataQueryVO actionVo) {
        List<String> selectedKeys = actionVo.getSelectedKeys();
        if (!CollectionUtils.isEmpty(selectedKeys)) {
            ArrayList<String> filterExistKeys = new ArrayList<String>(selectedKeys.size());
            for (String selectedKey : selectedKeys) {
                if (!this.existEntity(selectedKey)) continue;
                filterExistKeys.add(selectedKey);
            }
            actionVo.setSelectedKeys(filterExistKeys);
        }
    }

    private boolean existEntity(String selectedKey) {
        return this.metaService.queryEntity(selectedKey) != null;
    }

    private List<UITreeNode<EntityTreeNode>> getChildrenGroups(String groupKey, List<String> allPathKeys, Integer showContent) {
        ArrayList<UITreeNode<EntityTreeNode>> treeNodes = new ArrayList<UITreeNode<EntityTreeNode>>();
        if (!this.adapterService.judgementGroupId(groupKey)) {
            return treeNodes;
        }
        List childrenGroup = this.metaService.getChildrenGroup(groupKey);
        if (!CollectionUtils.isEmpty(childrenGroup)) {
            for (IEntityGroup group : childrenGroup) {
                List entities;
                UITreeNode<EntityTreeNode> treeNode = this.buildGroupNode(group);
                List childrenGroupNextLevel = this.metaService.getChildrenGroup(group.getId());
                treeNode.setLeaf(CollectionUtils.isEmpty(childrenGroupNextLevel));
                if (treeNode.isLeaf() && !CollectionUtils.isEmpty(entities = this.metaService.getEntitiesInGroup(group.getId()))) {
                    if (showContent != null && SHOW_CUMULATIVE.equals(showContent)) {
                        treeNode.setLeaf(entities.stream().noneMatch(e -> e.getDimensionFlag() == 1));
                    } else {
                        treeNode.setLeaf(false);
                    }
                }
                if (allPathKeys.contains(treeNode.getKey())) {
                    treeNode.setChecked(true);
                }
                treeNodes.add(treeNode);
            }
        }
        return treeNodes;
    }

    private UITreeNode<EntityTreeNode> buildGroupNode(IEntityGroup group) {
        EntityTreeNode node = new EntityTreeNode();
        node.setKey(group.getId());
        node.setTitle(group.getTitle());
        node.setParent(group.getParentId());
        node.setNodeType(NodeType.GROUP);
        UITreeNode<EntityTreeNode> groupNode = new UITreeNode<EntityTreeNode>(node);
        return groupNode;
    }

    private List<IEntityGroup> filterEntityGroups(List<IEntityGroup> groups, EntityDataQueryVO actionVo) {
        if (actionVo.getShowContent() == null) {
            return Collections.emptyList();
        }
        switch (actionVo.getShowContent()) {
            case 2: {
                return groups.stream().filter(group -> group.getId().split("_")[0].equals("ORG")).collect(Collectors.toList());
            }
            case 3: {
                return groups.stream().filter(group -> group.getId().split("_")[0].equals("BASE")).collect(Collectors.toList());
            }
        }
        return groups;
    }

    @Override
    public List<UITreeNode<EntityTreeNode>> loadChildren(EntityDataQueryVO actionVo) {
        this.filterExistSelectedKeys(actionVo);
        List<String> allPathKeys = this.getAllPathKeys(actionVo.getSelectedKeys());
        List<UITreeNode<EntityTreeNode>> childrenGroups = this.getChildrenGroups(actionVo.getGroupKey(), allPathKeys, actionVo.getShowContent());
        List<UITreeNode<EntityTreeNode>> entityNodes = this.getChildrenEntities(actionVo);
        for (UITreeNode<EntityTreeNode> node : childrenGroups) {
            if (!allPathKeys.contains(node.getKey())) continue;
            node.setChecked(true);
        }
        for (UITreeNode<EntityTreeNode> entityNode : entityNodes) {
            if (!actionVo.getSelectedKeys().contains(entityNode.getKey())) continue;
            entityNode.setChecked(true);
        }
        childrenGroups.addAll(entityNodes);
        return childrenGroups;
    }

    private List<UITreeNode<EntityTreeNode>> getChildrenEntities(EntityDataQueryVO actionVo) {
        ArrayList<UITreeNode<EntityTreeNode>> nodes = new ArrayList<UITreeNode<EntityTreeNode>>();
        List entities = null;
        entities = this.adapterService.judgementGroupId(actionVo.getGroupKey()) ? this.metaService.getEntitiesInGroup(actionVo.getGroupKey()) : this.metaService.getSubTreeEntities(actionVo.getGroupKey());
        if (!CollectionUtils.isEmpty(entities)) {
            List filterEntitiesList = entities.stream().filter(e -> {
                if (SHOW_CUMULATIVE.equals(actionVo.getShowContent())) {
                    return e.getDimensionFlag() == 1 && (e.getIsolation() == 0 || e.getIsolation() > 0 && this.isolatingBaseData.isEnableIsolation());
                }
                if (SHOW_UN_CUMULATIVE.equals(actionVo.getShowContent())) {
                    return e.getDimensionFlag() == 0;
                }
                return true;
            }).collect(Collectors.toList());
            for (IEntityDefine entity : filterEntitiesList) {
                boolean isSubTree;
                EntityTreeNode nodeData = this.buildNode(entity);
                UITreeNode<EntityTreeNode> entityNode = new UITreeNode<EntityTreeNode>(nodeData);
                boolean bl = isSubTree = entity.getIncludeSubTreeEntity() == 0;
                if (!isSubTree) {
                    isSubTree = CollectionUtils.isEmpty(this.metaService.getSubTreeEntities(entity.getId()));
                }
                if (isSubTree) {
                    entityNode.setLeaf(true);
                } else {
                    entityNode.setLeaf(false);
                    entityNode.getData().setNodeType(actionVo.isCheckBaseTree() ? NodeType.BASE_NODE : NodeType.GROUP);
                }
                nodes.add(entityNode);
            }
        }
        return nodes;
    }

    private EntityTreeNode buildNode(IEntityDefine entity) {
        EntityTreeNode node = new EntityTreeNode();
        node.setKey(entity.getId());
        node.setTitle(entity.getTitle());
        node.setCode(entity.getCode());
        node.setNodeType(NodeType.SUB_NODE);
        return node;
    }

    @Override
    public List<EntityTreeNode> treeSearch(EntityDataQueryVO actionVo) {
        Integer showContent = actionVo.getShowContent();
        List entityDefines = new ArrayList();
        if (SHOW_ALL.equals(actionVo.getShowContent())) {
            EntitySearchBO bo = new EntitySearchBO();
            bo.setKeyWords(actionVo.getKeyWords());
            bo.setDimensionFlag(1);
            List defines = this.metaService.fuzzySearchEntity(bo);
            entityDefines.addAll(defines);
            bo.setDimensionFlag(0);
            defines = this.metaService.fuzzySearchEntity(bo);
            entityDefines.addAll(defines);
        } else {
            EntitySearchBO bo = new EntitySearchBO();
            bo.setKeyWords(actionVo.getKeyWords());
            bo.setDimensionFlag(SHOW_ALL.equals(actionVo.getShowContent()) ? 1 : 0);
            entityDefines = this.metaService.fuzzySearchEntity(bo);
        }
        return entityDefines.stream().map(e -> this.buildNode((IEntityDefine)e)).collect(Collectors.toList());
    }

    @Override
    public List<UITreeNode<EntityTreeNode>> treeLocate(EntityDataQueryVO actionVo) {
        List<String> path = this.getNodePath(actionVo.getLocationKey());
        List<UITreeNode<EntityTreeNode>> rootTree = this.buildRootNode(actionVo);
        if (!this.existEntity(actionVo.getLocationKey())) {
            return rootTree;
        }
        List<String> allPathKeys = this.getAllPathKeys(actionVo.getSelectedKeys());
        for (UITreeNode<EntityTreeNode> iTree : rootTree) {
            this.findNode(path, 0, iTree, actionVo, allPathKeys);
        }
        return rootTree;
    }

    private List<String> getAllPathKeys(List<String> selectedKeys) {
        ArrayList<String> pathKeys = new ArrayList<String>();
        for (String selectedKey : selectedKeys) {
            List<String> path = this.getNodePath(selectedKey);
            if (CollectionUtils.isEmpty(path)) continue;
            pathKeys.addAll(path);
        }
        return pathKeys;
    }

    private List<String> getNodePath(String key) {
        List queryPath = this.metaService.getPath(key);
        IEntityDefine entityDefine = this.metaService.queryEntity(key);
        if (entityDefine.getIncludeSubTreeEntity() == 0) {
            IEntityDefine baseTreeEntity = this.metaService.queryBaseTreeEntityBySubTreeEntityId(key);
            queryPath.add(baseTreeEntity.getId());
        }
        return queryPath;
    }

    private void findNode(List<String> path, int index, UITreeNode<EntityTreeNode> node, EntityDataQueryVO actionVO, List<String> allPathKeys) {
        if (node.getKey().equals(path.get(index))) {
            node.setExpand(true);
            node.setChecked(true);
            try {
                this.expandGroup(path, index, node, actionVO, allPathKeys);
                this.expandAndCheckNode(node, path, index, allPathKeys, actionVO);
            }
            catch (JQException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void expandGroup(List<String> path, int index, UITreeNode<EntityTreeNode> node, EntityDataQueryVO actionVO, List<String> allPathKeys) throws JQException {
        List<UITreeNode<EntityTreeNode>> childrenGroups = this.getChildrenGroups(node.getKey(), allPathKeys, actionVO.getShowContent());
        if (!CollectionUtils.isEmpty(childrenGroups)) {
            for (UITreeNode<EntityTreeNode> childrenGroup : childrenGroups) {
                int indexTemp = index;
                this.appendChildren(node, childrenGroup);
                if (indexTemp == path.size() - 1) continue;
                this.findNode(path, ++indexTemp, childrenGroup, actionVO, allPathKeys);
            }
        }
    }

    private void expandAndCheckNode(UITreeNode<EntityTreeNode> node, List<String> path, int index, List<String> allPathKeys, EntityDataQueryVO actionVO) {
        List<UITreeNode<EntityTreeNode>> childrenEntities = this.getChildrenEntities(actionVO);
        if (!CollectionUtils.isEmpty(childrenEntities)) {
            for (UITreeNode<EntityTreeNode> childEntity : childrenEntities) {
                if (childEntity.getKey().equals(actionVO.getLocationKey())) {
                    childEntity.setSelected(true);
                    childEntity.setChecked(true);
                }
                if (actionVO.getSelectedKeys().contains(childEntity.getKey()) || allPathKeys.contains(childEntity.getKey())) {
                    childEntity.setChecked(true);
                }
                this.appendChildren(node, childEntity);
                int indexTemp = index;
                if (childEntity.isLeaf() || indexTemp == path.size() - 1) continue;
                childEntity.setExpand(true);
                this.expandAndCheckNode(childEntity, path, ++indexTemp, allPathKeys, actionVO);
            }
        }
    }

    private void appendChildren(UITreeNode<EntityTreeNode> node, UITreeNode<EntityTreeNode> needSetNode) {
        List<UITreeNode<EntityTreeNode>> children = node.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            children = new ArrayList<UITreeNode<EntityTreeNode>>();
            children.add(needSetNode);
        } else {
            children.add(needSetNode);
        }
    }
}

