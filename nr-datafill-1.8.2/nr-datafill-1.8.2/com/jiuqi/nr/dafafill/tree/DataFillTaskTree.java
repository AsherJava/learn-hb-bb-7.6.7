/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.common.NodeIconGetter
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.dafafill.tree;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.EditorType;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.tree.ResourceNode;
import com.jiuqi.nr.dafafill.tree.SearchTreeNode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DataFillTaskTree {
    protected final Logger logger = LoggerFactory.getLogger(DataFillTaskTree.class);
    @Autowired
    private IDesignTimeViewController designTime;
    @Autowired
    private RunTimeAuthViewController runTime;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataModelService dataModelService;

    public ITree<ResourceNode> buildRootNode(String taskCode) {
        if (StringUtils.hasText(taskCode)) {
            return this.buildRootNodeByTask(taskCode);
        }
        ResourceNode root = new ResourceNode();
        root.setKey("ROOT");
        root.setCode("ROOT");
        root.setTitle("nr.dataentry.allTask");
        root.setNodeType(999);
        ITree node = new ITree((INode)root);
        node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)NodeType.SCHEME_GROUP)});
        node.setExpanded(true);
        node.setSelected(true);
        node.setLeaf(false);
        node.setDisabled(true);
        ArrayList<ITree<ResourceNode>> children = new ArrayList<ITree<ResourceNode>>();
        this.buildTaskTree(children);
        node.setChildren(children);
        return node;
    }

    private ITree<ResourceNode> buildRootNodeByTask(String taskCode) {
        try {
            TaskDefine task = this.runTime.queryTaskDefineByCode(taskCode);
            ITree<ResourceNode> taskNode = this.convertTaskToResourceNode(task);
            List<ITree<ResourceNode>> masterNodes = this.buildChildrenNodes(task.getKey(), 998, task.getTaskCode());
            taskNode.setExpanded(true);
            taskNode.setChildren(masterNodes);
            return taskNode;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    private void buildTaskTree(List<ITree<ResourceNode>> children) {
        List allTaskGroup = this.designTime.getAllTaskGroup();
        List allTaskDefines = this.runTime.getAllTaskDefines();
        Map<String, TaskDefine> allTaskMap = allTaskDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, task -> task));
        HashSet<String> tasksHasGroup = new HashSet<String>();
        this.buildChildTree(null, allTaskGroup, allTaskMap, children, tasksHasGroup);
        for (TaskDefine task2 : allTaskDefines) {
            if (tasksHasGroup.contains(task2.getKey())) continue;
            children.add(this.convertTaskToResourceNode(task2));
        }
    }

    private void buildChildTree(String parentId, List<DesignTaskGroupDefine> allTaskGroup, Map<String, TaskDefine> allTaskMap, List<ITree<ResourceNode>> children, Set<String> tasksHasGroup) {
        List links;
        for (DesignTaskGroupDefine group : allTaskGroup) {
            if (!DataFillTaskTree.equals(group.getParentKey(), parentId)) continue;
            ITree<ResourceNode> node = this.convertGroupToResourceNode(group);
            children.add(node);
            ArrayList<ITree<ResourceNode>> nodeChildren = new ArrayList<ITree<ResourceNode>>();
            this.buildChildTree(group.getKey(), allTaskGroup, allTaskMap, nodeChildren, tasksHasGroup);
            node.setChildren(nodeChildren);
        }
        boolean isRoot = !StringUtils.hasText(parentId);
        List taskList = null;
        if (!isRoot && !CollectionUtils.isEmpty(links = this.designTime.getGroupLinkByGroupKey(parentId))) {
            taskList = links.stream().map(link -> link.getTaskKey()).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(taskList)) {
            tasksHasGroup.addAll(taskList);
            for (String key : taskList) {
                TaskDefine task = allTaskMap.get(key);
                if (task == null) continue;
                children.add(this.convertTaskToResourceNode(task));
            }
        }
    }

    private ITree<ResourceNode> convertTaskToResourceNode(TaskDefine task) {
        ResourceNode r = new ResourceNode();
        r.setKey(task.getKey());
        r.setTitle(task.getTitle());
        r.setNodeType(998);
        r.setCode(task.getTaskCode());
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)NodeType.SCHEME)});
        node.setLeaf(false);
        node.setDisabled(true);
        return node;
    }

    private ITree<ResourceNode> convertGroupToResourceNode(DesignTaskGroupDefine group) {
        ResourceNode r = new ResourceNode();
        r.setKey(group.getKey());
        r.setTitle(group.getTitle());
        r.setNodeType(999);
        r.setCode(group.getCode());
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)NodeType.SCHEME_GROUP)});
        node.setLeaf(false);
        node.setDisabled(true);
        return node;
    }

    public List<ITree<ResourceNode>> buildChildrenNodes(String key, int nodeType, String taskCode) {
        ArrayList<ITree<ResourceNode>> nodeChildren = new ArrayList<ITree<ResourceNode>>();
        if (998 == nodeType) {
            try {
                TaskDefine taskDefine = this.runTime.queryTaskDefine(key);
                if (taskDefine != null) {
                    String enittyId = taskDefine.getDw();
                    IEntityDefine entity = this.entityMetaService.queryEntity(enittyId);
                    ResourceNode r = new ResourceNode();
                    r.setKey(taskDefine.getTaskCode() + "." + enittyId);
                    r.setTitle(entity.getTitle());
                    r.setCode(enittyId);
                    r.setDataSchemeCode(taskDefine.getTaskCode());
                    r.setNodeType(997);
                    ITree node = new ITree((INode)r);
                    node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)NodeType.FMDM_TABLE)});
                    node.setLeaf(false);
                    nodeChildren.add((ITree<ResourceNode>)node);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else if (997 == nodeType) {
            try {
                IEntityModel entityModel = this.entityMetaService.getEntityModel(key);
                List zbs = entityModel.getShowFields();
                for (IEntityAttribute zb : zbs) {
                    ResourceNode r = new ResourceNode();
                    StringBuffer fullCode = new StringBuffer();
                    fullCode.append(entityModel.getEntityId()).append(".").append(zb.getCode());
                    r.setKey(taskCode + "." + fullCode.toString());
                    r.setTitle(zb.getTitle());
                    r.setCode(zb.getName());
                    r.setDataSchemeCode(taskCode);
                    r.setNodeType(996);
                    r.setField(this.convertZbToQueryField(entityModel, zb, taskCode));
                    ITree node = new ITree((INode)r);
                    node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)NodeType.FIELD_ZB)});
                    node.setLeaf(true);
                    nodeChildren.add((ITree<ResourceNode>)node);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return nodeChildren;
    }

    public QueryField convertZbToQueryField(IEntityModel entityModel, IEntityAttribute zb, String taskCode) {
        QueryField field = new QueryField();
        field.setId(zb.getID());
        field.setCode(zb.getName());
        StringBuffer fullCode = new StringBuffer();
        fullCode.append(entityModel.getEntityId()).append(".").append(zb.getName());
        field.setFullCode(fullCode.toString());
        field.setTitle(zb.getTitle());
        field.setDataSchemeCode(taskCode);
        field.setDataType(this.convertDataType(zb.getColumnType()));
        field.setFieldType(FieldType.ZB);
        if (zb.getColumnType() == ColumnModelType.STRING && StringUtils.hasText(zb.getReferTableID()) && StringUtils.hasText(zb.getReferColumnID())) {
            String referTableID = zb.getReferTableID();
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(referTableID);
            String entityId = this.entityMetaService.getEntityIdByCode(tableModelDefine.getCode());
            field.setExpression(entityId);
            field.setEditorType(EditorType.DROPDOWN);
        }
        return field;
    }

    private DataFieldType convertDataType(ColumnModelType columnType) {
        switch (columnType) {
            case BOOLEAN: {
                return DataFieldType.BOOLEAN;
            }
            case INTEGER: {
                return DataFieldType.INTEGER;
            }
            case DOUBLE: 
            case BIGDECIMAL: {
                return DataFieldType.BIGDECIMAL;
            }
            case DATETIME: {
                return DataFieldType.DATE;
            }
            case CLOB: 
            case BLOB: {
                return DataFieldType.CLOB;
            }
            case UUID: {
                return DataFieldType.UUID;
            }
        }
        return DataFieldType.STRING;
    }

    public List<SearchTreeNode> search(String fuzzyKey) {
        String _fuzzyKey = fuzzyKey.toUpperCase();
        ArrayList<SearchTreeNode> res = new ArrayList<SearchTreeNode>();
        List allTaskGroup = this.designTime.getAllTaskGroup();
        List allTaskDefines = this.runTime.getAllTaskDefines();
        Map<String, TaskDefine> allTaskMap = allTaskDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, task -> task));
        HashSet<String> tasksHasGroup = new HashSet<String>();
        ArrayList<String> paths = new ArrayList<String>();
        paths.add("ROOT");
        ArrayList<String> titlePaths = new ArrayList<String>();
        this.buildSearchNode(res, null, allTaskGroup, allTaskMap, _fuzzyKey, paths, titlePaths, tasksHasGroup);
        for (TaskDefine task2 : allTaskDefines) {
            if (tasksHasGroup.contains(task2.getKey())) continue;
            String enittyId = task2.getDw();
            this.convertEntityToSearchNode(res, _fuzzyKey, paths, titlePaths, task2, enittyId);
            this.convertEntityAttributeToSourceNode(res, _fuzzyKey, paths, titlePaths, task2, enittyId);
        }
        return res;
    }

    private void buildSearchNode(List<SearchTreeNode> res, String parentId, List<DesignTaskGroupDefine> allTaskGroup, Map<String, TaskDefine> allTaskMap, String fuzzyKey, List<String> parentPaths, List<String> parentTitlePaths, Set<String> tasksHasGroup) {
        List links;
        for (DesignTaskGroupDefine group : allTaskGroup) {
            if (!DataFillTaskTree.equals(group.getParentKey(), parentId)) continue;
            ArrayList<String> paths = new ArrayList<String>();
            paths.addAll(parentPaths);
            paths.add(group.getKey());
            ArrayList<String> titlePaths = new ArrayList<String>();
            titlePaths.addAll(parentTitlePaths);
            titlePaths.add(group.getTitle());
            this.buildSearchNode(res, group.getKey(), allTaskGroup, allTaskMap, fuzzyKey, paths, titlePaths, tasksHasGroup);
        }
        boolean isRoot = !StringUtils.hasText(parentId);
        List taskList = null;
        if (!isRoot && !CollectionUtils.isEmpty(links = this.designTime.getGroupLinkByGroupKey(parentId))) {
            taskList = links.stream().map(link -> link.getTaskKey()).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(taskList)) {
            tasksHasGroup.addAll(taskList);
            for (String key : taskList) {
                TaskDefine taskDefine = allTaskMap.get(key);
                if (taskDefine == null) {
                    this.logger.warn("\u641c\u7d22\u65f6\u901a\u8fc7\u4efb\u52a1key\uff1a" + key + " \u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5b9a\u4e49");
                    continue;
                }
                String enittyId = taskDefine.getDw();
                this.convertEntityToSearchNode(res, fuzzyKey, parentPaths, parentTitlePaths, taskDefine, enittyId);
                this.convertEntityAttributeToSourceNode(res, fuzzyKey, parentPaths, parentTitlePaths, taskDefine, enittyId);
            }
        }
    }

    private void convertEntityAttributeToSourceNode(List<SearchTreeNode> res, String fuzzyKey, List<String> parentPaths, List<String> parentTitlePaths, TaskDefine taskDefine, String enittyId) {
        try {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(enittyId);
            IEntityModel entityModel = this.entityMetaService.getEntityModel(enittyId);
            ArrayList<String> _parentpaths = new ArrayList<String>();
            _parentpaths.addAll(parentPaths);
            _parentpaths.add(taskDefine.getKey());
            _parentpaths.add(taskDefine.getTaskCode() + "." + enittyId);
            ArrayList<String> _titlePaths = new ArrayList<String>();
            _titlePaths.add(entityDefine.getTitle());
            _titlePaths.add(taskDefine.getTitle());
            List zbs = entityModel.getShowFields();
            for (IEntityAttribute zb : zbs) {
                if (zb.getCode().toUpperCase().indexOf(fuzzyKey) <= -1 && zb.getTitle().toUpperCase().indexOf(fuzzyKey) <= -1) continue;
                ArrayList<String> paths = new ArrayList<String>();
                paths.addAll(_parentpaths);
                paths.add(enittyId + "." + zb.getCode());
                ArrayList<String> titlePaths = new ArrayList<String>();
                titlePaths.add(zb.getTitle());
                titlePaths.addAll(_titlePaths);
                SearchTreeNode s = new SearchTreeNode();
                s.setKey(zb.getID());
                s.setCode(zb.getCode());
                s.setTitle(zb.getTitle());
                s.setIcon(NodeIconGetter.getIconByType((NodeType)NodeType.FIELD_ZB));
                s.setKeyPath(paths);
                s.setTitlePath(titlePaths.stream().collect(Collectors.joining("/")));
                res.add(s);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void convertEntityToSearchNode(List<SearchTreeNode> res, String fuzzyKey, List<String> parentPaths, List<String> parentTitlePaths, TaskDefine taskDefine, String enittyId) {
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(enittyId);
        if (entityDefine.getCode().toUpperCase().indexOf(fuzzyKey) > -1 || entityDefine.getTitle().toUpperCase().indexOf(fuzzyKey) > -1) {
            ArrayList<String> paths = new ArrayList<String>();
            paths.addAll(parentPaths);
            paths.add(taskDefine.getKey());
            paths.add(taskDefine.getTaskCode() + "." + enittyId);
            ArrayList<String> titlePaths = new ArrayList<String>();
            titlePaths.add(entityDefine.getTitle());
            titlePaths.add(taskDefine.getTitle());
            SearchTreeNode s = new SearchTreeNode();
            s.setKey(taskDefine.getTaskCode() + "@" + enittyId);
            s.setCode(entityDefine.getCode());
            s.setTitle(entityDefine.getTitle());
            s.setIcon(NodeIconGetter.getIconByType((NodeType)NodeType.FMDM_TABLE));
            s.setKeyPath(paths);
            s.setTitlePath(titlePaths.stream().collect(Collectors.joining("/")));
            res.add(s);
        }
    }

    private static boolean equals(String a, String b) {
        return a == null ? b == null : a.equals(b);
    }

    public List<String> locate(String id) {
        ArrayList<String> paths = new ArrayList<String>();
        try {
            String taskCode = id.split("@@")[0];
            String attributeName = id.split("@@")[1];
            TaskDefine task = this.runTime.queryTaskDefineByCode(taskCode);
            IEntityModel entityModel = this.entityMetaService.getEntityModel(task.getDw());
            IEntityAttribute attribute = entityModel.getAttribute(attributeName);
            List links = this.designTime.getGroupLinkByTaskKey(task.getKey());
            ArrayList<String> groupPaths = new ArrayList<String>();
            if (!CollectionUtils.isEmpty(links)) {
                this.getParentGroupKey(((DesignTaskGroupLink)links.get(0)).getGroupKey(), groupPaths);
            }
            paths.add("ROOT");
            paths.addAll(groupPaths);
            paths.add(task.getKey());
            paths.add(task.getTaskCode() + "." + task.getDw());
            paths.add(task.getDw() + "." + attribute.getName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return paths;
    }

    private void getParentGroupKey(String groupKey, List<String> groupPaths) {
        if (!StringUtils.hasText(groupKey)) {
            return;
        }
        DesignTaskGroupDefine group = this.designTime.queryTaskGroupDefine(groupKey);
        if (group != null) {
            this.getParentGroupKey(group.getParentKey(), groupPaths);
            groupPaths.add(groupKey);
        }
    }
}

