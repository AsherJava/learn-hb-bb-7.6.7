/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.IMetaGroup
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupDefineImpl
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.task.service.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.IMetaGroup;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupDefineImpl;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.common.TaskI18nUtil;
import com.jiuqi.nr.task.dto.TaskGroupDTO;
import com.jiuqi.nr.task.exception.TaskGroupRuntimeException;
import com.jiuqi.nr.task.service.ITaskGroupService;
import com.jiuqi.nr.task.web.vo.ResourcePath;
import com.jiuqi.nr.task.web.vo.ResourceSearchVO;
import com.jiuqi.nr.task.web.vo.TaskGroupTreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class TaskGroupServiceImpl
implements ITaskGroupService {
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupServiceImpl.class);
    public static final String ALL_TASK_TITLE = "\u5168\u90e8\u4efb\u52a1";
    public static final String ALL_TASK_TITLE_KEY = "00000000-0000-0000-0000-000000000000";
    private static final String GROUP_ICON = "#icon-folder";
    @Autowired
    private IDesignTimeViewController designTimeViewControl;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthority;

    @Override
    public String insertTaskGroup(TaskGroupDTO define) {
        if (!StringUtils.hasLength(define.getParentKey()) || ALL_TASK_TITLE_KEY.equals(define.getParentKey())) {
            define.setParentKey(null);
        }
        if (!StringUtils.hasText(define.getKey())) {
            define.setKey(UUID.randomUUID().toString());
        }
        DesignTaskGroupDefine taskGroupDefine = define.toTaskGroupDefine();
        try {
            this.definitionAuthority.grantAllPrivilegesToTaskGroup(taskGroupDefine.getKey());
            this.designTimeViewControl.insertTaskGroup(taskGroupDefine);
        }
        catch (Exception e) {
            throw new TaskGroupRuntimeException(TaskI18nUtil.getMessage("task.group.error.name.illegal", taskGroupDefine.getTitle()));
        }
        return taskGroupDefine.getKey();
    }

    @Override
    public boolean checkTaskGroup(TaskGroupDTO taskGroupDefine) {
        if (!StringUtils.hasLength(taskGroupDefine.getParentKey()) || ALL_TASK_TITLE_KEY.equals(taskGroupDefine.getParentKey())) {
            taskGroupDefine.setParentKey(null);
        }
        List designTaskGroupDefines = this.designTimeViewControl.listAllTaskGroup();
        Stream<DesignTaskGroupDefine> stream = !StringUtils.hasLength(taskGroupDefine.getParentKey()) ? designTaskGroupDefines.stream().filter(t -> !StringUtils.hasLength(t.getParentKey())) : designTaskGroupDefines.stream().filter(t -> taskGroupDefine.getParentKey().equals(t.getParentKey()));
        boolean flag = stream.filter(t -> !t.getKey().equals(taskGroupDefine.getKey())).anyMatch(t -> t.getTitle().equals(taskGroupDefine.getTitle()));
        return flag;
    }

    @Override
    public void deleteTaskGroup(String taskGroupKey) {
        List designTaskGroupDefines = this.designTimeViewControl.listTaskGroupByParentGroup(taskGroupKey);
        if (!CollectionUtils.isEmpty(designTaskGroupDefines)) {
            throw new TaskGroupRuntimeException(TaskI18nUtil.getMessage("task.group.error.delete.failed.sub", taskGroupKey));
        }
        List designTaskDefines = this.designTimeViewControl.listTaskByTaskGroup(taskGroupKey);
        if (!CollectionUtils.isEmpty(designTaskDefines)) {
            throw new TaskGroupRuntimeException(TaskI18nUtil.getMessage("task.group.error.delete.failed.sub", taskGroupKey));
        }
        this.designTimeViewControl.deleteTaskGroup(new String[]{taskGroupKey});
    }

    @Override
    public void updateTaskGroup(TaskGroupDTO define) {
        DesignTaskGroupDefine taskGroup;
        try {
            taskGroup = this.designTimeViewControl.getTaskGroup(define.getKey());
        }
        catch (Exception e) {
            throw new TaskGroupRuntimeException(TaskI18nUtil.getMessage("task.group.error.query.failed", define.getKey()));
        }
        if (taskGroup == null || !StringUtils.hasLength(taskGroup.getKey())) {
            throw new TaskGroupRuntimeException(TaskI18nUtil.getMessage("task.group.error.notExist", define.getKey()));
        }
        taskGroup.setParentKey(define.getParentKey());
        taskGroup.setTitle(define.getTitle());
        taskGroup.setDescription(define.getDescription());
        if (!StringUtils.hasLength(define.getParentKey()) || ALL_TASK_TITLE_KEY.equals(define.getParentKey())) {
            taskGroup.setParentKey(null);
        }
        try {
            this.designTimeViewControl.updateTaskGroup(taskGroup);
        }
        catch (Exception e) {
            throw new TaskGroupRuntimeException(TaskI18nUtil.getMessage("task.group.error.name.illegal", define.getTitle()));
        }
    }

    @Override
    public void changeTaskGroupOrder(String key, int way) {
        DesignTaskGroupDefine targetGroup;
        if (!StringUtils.hasLength(key)) {
            throw new TaskGroupRuntimeException("\u4efb\u52a1\u5206\u7ec4\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u4efb\u52a1\u5206\u7ec4");
        }
        DesignTaskGroupDefine sourceGroup = this.designTimeViewControl.getTaskGroup(key);
        String sourceParentKey = sourceGroup.getParentKey();
        List childTaskGroups = this.designTimeViewControl.listTaskGroupByParentGroup(sourceParentKey);
        int currentIndex = -1;
        for (int i = 0; i < childTaskGroups.size(); ++i) {
            DesignTaskGroupDefine groupDefine = (DesignTaskGroupDefine)childTaskGroups.get(i);
            if (!groupDefine.getKey().equals(sourceGroup.getKey())) continue;
            currentIndex = i;
            break;
        }
        if (way > 0) {
            if (currentIndex == 0) {
                throw new TaskGroupRuntimeException("\u5f53\u524d\u5206\u7ec4\u6392\u5e8f\u5df2\u6700\u5c0f\uff0c\u65e0\u6cd5\u4e0a\u79fb");
            }
            targetGroup = (DesignTaskGroupDefine)childTaskGroups.get(currentIndex - 1);
        } else {
            if (currentIndex == childTaskGroups.size() - 1) {
                throw new TaskGroupRuntimeException("\u5f53\u524d\u5206\u7ec4\u6392\u5e8f\u5df2\u6700\u5927\uff0c\u65e0\u6cd5\u4e0b\u79fb");
            }
            targetGroup = (DesignTaskGroupDefine)childTaskGroups.get(currentIndex + 1);
        }
        if (targetGroup == null) {
            throw new TaskGroupRuntimeException("\u672a\u627e\u5230\u4ea4\u6362\u7684\u987a\u5e8f\u7684\u5206\u7ec4\uff0c\u65e0\u6cd5\u79fb\u52a8");
        }
        String sourceGroupOrder = sourceGroup.getOrder();
        sourceGroup.setOrder(targetGroup.getOrder());
        targetGroup.setOrder(sourceGroupOrder);
        this.designTimeViewControl.updateTaskGroup(sourceGroup);
        this.designTimeViewControl.updateTaskGroup(targetGroup);
    }

    @Override
    public TaskGroupDTO queryTaskGroup(String key) {
        if (!StringUtils.hasLength(key) || ALL_TASK_TITLE_KEY.equals(key)) {
            return new TaskGroupDTO(ALL_TASK_TITLE_KEY, ALL_TASK_TITLE, "", null, "");
        }
        DesignTaskGroupDefine taskGroup = this.designTimeViewControl.getTaskGroup(key);
        if (!StringUtils.hasLength(taskGroup.getParentKey())) {
            taskGroup.setParentKey(ALL_TASK_TITLE_KEY);
        }
        return new TaskGroupDTO(taskGroup);
    }

    public DesignTaskGroupDefine queryTaskGroupDefine(String key) {
        if (!StringUtils.hasLength(key) || ALL_TASK_TITLE_KEY.equals(key)) {
            DesignTaskGroupDefineImpl designTaskGroupDefine = new DesignTaskGroupDefineImpl();
            designTaskGroupDefine.setKey(ALL_TASK_TITLE_KEY);
            designTaskGroupDefine.setTitle(ALL_TASK_TITLE);
            return designTaskGroupDefine;
        }
        DesignTaskGroupDefine taskGroup = this.designTimeViewControl.getTaskGroup(key);
        if (!StringUtils.hasLength(taskGroup.getParentKey())) {
            taskGroup.setParentKey(ALL_TASK_TITLE_KEY);
        }
        return taskGroup;
    }

    @Override
    public List<UITreeNode<TaskGroupTreeNode>> listChildTaskGroup(String key) {
        String searchKey = key;
        if (!StringUtils.hasLength(key) || ALL_TASK_TITLE_KEY.equals(key)) {
            searchKey = null;
        }
        List designTaskGroupDefines = this.designTimeViewControl.listTaskGroupByParentGroup(searchKey);
        TaskGroupDTO thenTaskGroup = this.queryTaskGroup(key);
        String parentName = thenTaskGroup.getTitle();
        ArrayList<UITreeNode<TaskGroupTreeNode>> childTree = new ArrayList<UITreeNode<TaskGroupTreeNode>>();
        for (DesignTaskGroupDefine designTaskGroupDefine : designTaskGroupDefines) {
            if (!this.definitionAuthority.canReadTaskGroup(designTaskGroupDefine.getKey())) continue;
            if (!StringUtils.hasLength(designTaskGroupDefine.getParentKey())) {
                designTaskGroupDefine.setParentKey(ALL_TASK_TITLE_KEY);
            }
            TaskGroupTreeNode taskGroupTreeNode = new TaskGroupTreeNode(designTaskGroupDefine);
            UITreeNode uiTreeNode = new UITreeNode();
            taskGroupTreeNode.setParentName(parentName);
            uiTreeNode.setData((TreeData)taskGroupTreeNode);
            List child = this.designTimeViewControl.listTaskGroupByParentGroup(designTaskGroupDefine.getKey());
            uiTreeNode.setLeaf(CollectionUtils.isEmpty(child));
            uiTreeNode.setIcon(GROUP_ICON);
            uiTreeNode.setKey(taskGroupTreeNode.getKey());
            uiTreeNode.setParent(key);
            uiTreeNode.setTitle(taskGroupTreeNode.getTitle());
            childTree.add((UITreeNode<TaskGroupTreeNode>)uiTreeNode);
        }
        if (childTree.size() > 0) {
            ((TaskGroupTreeNode)((UITreeNode)childTree.get(0)).getData()).setFirst(true);
            ((TaskGroupTreeNode)((UITreeNode)childTree.get(childTree.size() - 1)).getData()).setLast(true);
        }
        return childTree;
    }

    @Override
    public List<ResourceSearchVO> fuzzySearchTaskGroup(String keyWord) {
        if (!StringUtils.hasLength(keyWord)) {
            throw new TaskGroupRuntimeException("\u4efb\u52a1\u5206\u7ec4\u6a21\u7cca\u641c\u7d22\u5173\u952e\u5b57\u4e0d\u80fd\u4e3a\u7a7a");
        }
        ArrayList<ResourceSearchVO> result = new ArrayList<ResourceSearchVO>();
        List allTaskGroupDefines = this.designTimeViewControl.listAllTaskGroup();
        List associatedTaskGroups = allTaskGroupDefines.stream().filter(taskGroup -> taskGroup.getTitle().toLowerCase().contains(keyWord.toLowerCase()) && this.definitionAuthority.canModeling(taskGroup.getKey())).collect(Collectors.toList());
        for (DesignTaskGroupDefine taskGroupDefine : associatedTaskGroups) {
            ResourceSearchVO taskGroupSearchResourceVo = new ResourceSearchVO(taskGroupDefine);
            if (!StringUtils.hasText(taskGroupSearchResourceVo.getParentKey())) {
                taskGroupSearchResourceVo.setParentKey(ALL_TASK_TITLE_KEY);
            }
            result.add(taskGroupSearchResourceVo);
        }
        return result;
    }

    @Override
    public List<TaskGroupDTO> queryTaskGroupByGroupId(String key, boolean isRecursion) {
        if (!StringUtils.hasLength(key) || ALL_TASK_TITLE_KEY.equals(key)) {
            return this.designTimeViewControl.listTaskGroupByParentGroup(null).stream().filter(t -> this.definitionAuthority.canReadTaskGroup(t.getKey())).map(TaskGroupDTO::new).collect(Collectors.toList());
        }
        List<TaskGroupDTO> list = new ArrayList<TaskGroupDTO>();
        if (isRecursion) {
            List<DesignTaskGroupDefine> designTaskGroupDefines = this.designTimeViewControl.listAllTaskGroup().stream().filter(t -> StringUtils.hasLength(t.getParentKey()) && this.definitionAuthority.canReadTaskGroup(t.getKey())).collect(Collectors.toList());
            this.getChildGroup(key, list, designTaskGroupDefines);
        } else {
            list = this.designTimeViewControl.listTaskGroupByParentGroup(key).stream().filter(t -> this.definitionAuthority.canReadTaskGroup(t.getKey())).map(TaskGroupDTO::new).collect(Collectors.toList());
        }
        return list;
    }

    private void getChildGroup(String parentKey, List<TaskGroupDTO> list, List<DesignTaskGroupDefine> designTaskGroupDefines) {
        ArrayList<DesignTaskGroupDefine> childTaskGroupDefine = new ArrayList<DesignTaskGroupDefine>();
        for (DesignTaskGroupDefine designTaskGroupDefine : designTaskGroupDefines) {
            if (!parentKey.equals(designTaskGroupDefine.getParentKey())) continue;
            childTaskGroupDefine.add(designTaskGroupDefine);
        }
        list.addAll(childTaskGroupDefine.stream().map(TaskGroupDTO::new).collect(Collectors.toList()));
        for (TaskGroupDefine taskGroupDefine : childTaskGroupDefine) {
            this.getChildGroup(taskGroupDefine.getKey(), list, designTaskGroupDefines);
        }
    }

    @Override
    public List<UITreeNode<TaskGroupTreeNode>> getRootGroupTree() {
        List allTaskGroupDefines = this.designTimeViewControl.listAllTaskGroup();
        Map<String, List<DesignTaskGroupDefine>> parentToChild = this.getParentToChild(allTaskGroupDefines);
        List<DesignTaskGroupDefine> rootTaskGroup = parentToChild.get(ALL_TASK_TITLE_KEY);
        ArrayList<UITreeNode<TaskGroupTreeNode>> childTree = new ArrayList<UITreeNode<TaskGroupTreeNode>>();
        int i = 0;
        for (DesignTaskGroupDefine designTaskGroupDefine : rootTaskGroup) {
            TaskGroupTreeNode taskGroupTreeNode = new TaskGroupTreeNode(designTaskGroupDefine);
            if (++i == 1) {
                taskGroupTreeNode.setFirst(true);
            } else if (i == rootTaskGroup.size()) {
                taskGroupTreeNode.setLast(true);
            }
            UITreeNode uiTreeNode = new UITreeNode();
            uiTreeNode.setData((TreeData)taskGroupTreeNode);
            List<DesignTaskGroupDefine> child = parentToChild.get(designTaskGroupDefine.getKey());
            uiTreeNode.setLeaf(CollectionUtils.isEmpty(child));
            uiTreeNode.setIcon(GROUP_ICON);
            uiTreeNode.setKey(taskGroupTreeNode.getKey());
            uiTreeNode.setParent(ALL_TASK_TITLE_KEY);
            uiTreeNode.setTitle(taskGroupTreeNode.getTitle());
            childTree.add((UITreeNode<TaskGroupTreeNode>)uiTreeNode);
        }
        return this.getAllTaskGroupTreeNodeWithChild(childTree);
    }

    private Map<String, List<DesignTaskGroupDefine>> getParentToChild(List<DesignTaskGroupDefine> designTaskGroupDefines) {
        ArrayList rootTaskGroup = new ArrayList();
        Map<String, List<DesignTaskGroupDefine>> parentToChild = designTaskGroupDefines.stream().filter(t -> {
            if (this.definitionAuthority.canReadTaskGroup(t.getKey())) {
                if (!StringUtils.hasLength(t.getParentKey())) {
                    t.setParentKey(ALL_TASK_TITLE_KEY);
                    rootTaskGroup.add(t);
                    return false;
                }
                return true;
            }
            return false;
        }).collect(Collectors.groupingBy(IMetaGroup::getParentKey));
        parentToChild.put(null, rootTaskGroup);
        parentToChild.put("", rootTaskGroup);
        parentToChild.put(ALL_TASK_TITLE_KEY, rootTaskGroup);
        return parentToChild;
    }

    @Override
    public List<UITreeNode<TaskGroupTreeNode>> getAllTaskGroupTree() {
        List allTaskGroupDefines = this.designTimeViewControl.listAllTaskGroup();
        Map<String, List<DesignTaskGroupDefine>> parentToChild = this.getParentToChild(allTaskGroupDefines);
        List<DesignTaskGroupDefine> rootTaskGroup = parentToChild.get(null);
        UITreeNode<TaskGroupTreeNode> allTaskGroupNode = this.getAllTaskGroupTreeNode();
        allTaskGroupNode.setLeaf(CollectionUtils.isEmpty(rootTaskGroup));
        allTaskGroupNode.setIcon(GROUP_ICON);
        allTaskGroupNode.setExpand(true);
        this.setTree(parentToChild, allTaskGroupNode);
        ArrayList<UITreeNode<TaskGroupTreeNode>> allTaskGroupTree = new ArrayList<UITreeNode<TaskGroupTreeNode>>();
        allTaskGroupTree.add(allTaskGroupNode);
        return allTaskGroupTree;
    }

    private void setTree(Map<String, List<DesignTaskGroupDefine>> parentToChild, UITreeNode<TaskGroupTreeNode> thenTreeNode) {
        TaskGroupTreeNode thenTaskGroupTreeNode = (TaskGroupTreeNode)thenTreeNode.getData();
        List<DesignTaskGroupDefine> childTaskGroupDefines = parentToChild.get(thenTaskGroupTreeNode.getKey());
        ArrayList<UITreeNode> childTreeNodes = new ArrayList<UITreeNode>();
        int i = 0;
        for (DesignTaskGroupDefine childTaskGroupDefine : childTaskGroupDefines) {
            UITreeNode uiTreeNode = new UITreeNode();
            TaskGroupTreeNode childTaskGroupTreeNode = new TaskGroupTreeNode(childTaskGroupDefine);
            if (i == 0) {
                childTaskGroupTreeNode.setFirst(true);
            } else if (i == childTaskGroupDefines.size() - 1) {
                childTaskGroupTreeNode.setLast(true);
            }
            childTaskGroupTreeNode.setParentName(thenTaskGroupTreeNode.getTitle());
            uiTreeNode.setData((TreeData)childTaskGroupTreeNode);
            List<DesignTaskGroupDefine> child = parentToChild.get(childTaskGroupDefine.getKey());
            uiTreeNode.setLeaf(CollectionUtils.isEmpty(child));
            uiTreeNode.setIcon(GROUP_ICON);
            uiTreeNode.setKey(childTaskGroupDefine.getKey());
            uiTreeNode.setParent(childTaskGroupDefine.getParentKey());
            uiTreeNode.setTitle(childTaskGroupDefine.getTitle());
            childTreeNodes.add(uiTreeNode);
            ++i;
            if (CollectionUtils.isEmpty(child)) continue;
            this.setTree(parentToChild, (UITreeNode<TaskGroupTreeNode>)uiTreeNode);
        }
        thenTreeNode.setChildren(childTreeNodes);
    }

    private UITreeNode<TaskGroupTreeNode> getAllTaskGroupTreeNode() {
        UITreeNode uiTreeNode = new UITreeNode();
        DesignTaskGroupDefineImpl allTaskGroupDefine = new DesignTaskGroupDefineImpl();
        allTaskGroupDefine.setKey(ALL_TASK_TITLE_KEY);
        allTaskGroupDefine.setTitle(ALL_TASK_TITLE);
        allTaskGroupDefine.setParentKey(null);
        TaskGroupTreeNode taskGroupTreeNode = new TaskGroupTreeNode((DesignTaskGroupDefine)allTaskGroupDefine);
        uiTreeNode.setData((TreeData)taskGroupTreeNode);
        uiTreeNode.setKey(taskGroupTreeNode.getKey());
        uiTreeNode.setParent(null);
        uiTreeNode.setTitle(taskGroupTreeNode.getTitle());
        return uiTreeNode;
    }

    private List<UITreeNode<TaskGroupTreeNode>> getAllTaskGroupTreeNodeWithChild(List<UITreeNode<TaskGroupTreeNode>> child) {
        UITreeNode uiTreeNode = new UITreeNode();
        DesignTaskGroupDefineImpl allTaskGroupDefine = new DesignTaskGroupDefineImpl();
        allTaskGroupDefine.setKey(ALL_TASK_TITLE_KEY);
        allTaskGroupDefine.setTitle(ALL_TASK_TITLE);
        allTaskGroupDefine.setParentKey(null);
        TaskGroupTreeNode taskGroupTreeNode = new TaskGroupTreeNode((DesignTaskGroupDefine)allTaskGroupDefine);
        uiTreeNode.setData((TreeData)taskGroupTreeNode);
        uiTreeNode.setKey(taskGroupTreeNode.getKey());
        uiTreeNode.setParent(null);
        uiTreeNode.setTitle(taskGroupTreeNode.getTitle());
        uiTreeNode.setLeaf(CollectionUtils.isEmpty(child));
        uiTreeNode.setIcon(GROUP_ICON);
        uiTreeNode.setExpand(true);
        uiTreeNode.setChildren(child);
        child.forEach(a -> a.setParent(taskGroupTreeNode.getKey()));
        ArrayList<UITreeNode<TaskGroupTreeNode>> allTaskGroupTree = new ArrayList<UITreeNode<TaskGroupTreeNode>>();
        allTaskGroupTree.add(uiTreeNode);
        return allTaskGroupTree;
    }

    @Override
    public List<TaskGroupDTO> getGroupByTask(String taskKey) {
        List designTaskGroupDefines = this.designTimeViewControl.listTaskGroupByTask(taskKey);
        return designTaskGroupDefines.stream().map(TaskGroupDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<UITreeNode<TaskGroupTreeNode>> locationTaskGroup(String groupKey) {
        if (!StringUtils.hasLength(groupKey) || ALL_TASK_TITLE_KEY.equals(groupKey)) {
            groupKey = null;
        }
        UITreeNode<TaskGroupTreeNode> allTaskGroupNode = this.getAllTaskGroupTreeNode();
        allTaskGroupNode.setIcon(GROUP_ICON);
        List allTaskGroupDefines = this.designTimeViewControl.listAllTaskGroup();
        Map<String, List<DesignTaskGroupDefine>> parentToChild = this.getParentToChild(allTaskGroupDefines);
        List<DesignTaskGroupDefine> rootTaskGroup = parentToChild.get(null);
        if (!StringUtils.hasLength(groupKey)) {
            allTaskGroupNode.setLeaf(CollectionUtils.isEmpty(rootTaskGroup));
            allTaskGroupNode.setSelected(true);
        } else {
            UITreeNode superNode = null;
            List<Object> childrenNodes = new ArrayList();
            TaskGroupDTO superGroupParams = new TaskGroupDTO();
            DesignTaskGroupDefine thisParams = this.designTimeViewControl.getTaskGroup(groupKey);
            UITreeNode thisNode = new UITreeNode((TreeData)new TaskGroupTreeNode(thisParams));
            while (StringUtils.hasLength(((TaskGroupTreeNode)thisNode.getData()).getKey()) && !ALL_TASK_TITLE_KEY.equals(((TaskGroupTreeNode)thisNode.getData()).getKey())) {
                superGroupParams = this.queryTaskGroup(((TaskGroupTreeNode)thisNode.getData()).getParentKey());
                ((TaskGroupTreeNode)thisNode.getData()).setParentName(superGroupParams.getTitle());
                superNode = new UITreeNode((TreeData)new TaskGroupTreeNode(superGroupParams));
                childrenNodes = this.listChildTaskGroup((UITreeNode<TaskGroupTreeNode>)thisNode, parentToChild, groupKey, true);
                thisNode = superNode;
                if (CollectionUtils.isEmpty(childrenNodes)) continue;
                superNode.setChildren(childrenNodes);
                superNode.setIcon(GROUP_ICON);
            }
            allTaskGroupNode.setChildren(thisNode.getChildren());
            allTaskGroupNode.setLeaf(false);
            allTaskGroupNode.setExpand(true);
        }
        ArrayList<UITreeNode<TaskGroupTreeNode>> allTaskGroupTree = new ArrayList<UITreeNode<TaskGroupTreeNode>>();
        allTaskGroupTree.add(allTaskGroupNode);
        return allTaskGroupTree;
    }

    private List<UITreeNode<TaskGroupTreeNode>> listChildTaskGroup(UITreeNode<TaskGroupTreeNode> thisNode, Map<String, List<DesignTaskGroupDefine>> parentToChild, String locationGroupkey, boolean isSelected) {
        String parentkey = ((TaskGroupTreeNode)thisNode.getData()).getParentKey();
        String parentName = ((TaskGroupTreeNode)thisNode.getData()).getParentName();
        List<DesignTaskGroupDefine> designTaskGroupDefines = parentToChild.get(parentkey);
        ArrayList<UITreeNode<TaskGroupTreeNode>> childTree = new ArrayList<UITreeNode<TaskGroupTreeNode>>();
        for (DesignTaskGroupDefine designTaskGroupDefine : designTaskGroupDefines) {
            TaskGroupTreeNode taskGroupTreeNode = new TaskGroupTreeNode(designTaskGroupDefine);
            taskGroupTreeNode.setParentName(parentName);
            UITreeNode uiTreeNode = new UITreeNode();
            uiTreeNode.setData((TreeData)taskGroupTreeNode);
            List<DesignTaskGroupDefine> child = parentToChild.get(designTaskGroupDefine.getKey());
            uiTreeNode.setLeaf(CollectionUtils.isEmpty(child));
            uiTreeNode.setIcon(GROUP_ICON);
            uiTreeNode.setKey(taskGroupTreeNode.getKey());
            uiTreeNode.setParent(parentkey);
            uiTreeNode.setTitle(taskGroupTreeNode.getTitle());
            if (taskGroupTreeNode.getKey().equals(((TaskGroupTreeNode)thisNode.getData()).getKey())) {
                uiTreeNode.setChildren(thisNode.getChildren());
                if (taskGroupTreeNode.getKey().equals(locationGroupkey)) {
                    if (isSelected) {
                        uiTreeNode.setSelected(true);
                    } else {
                        uiTreeNode.setChecked(true);
                    }
                } else {
                    uiTreeNode.setExpand(true);
                }
            }
            childTree.add((UITreeNode<TaskGroupTreeNode>)uiTreeNode);
        }
        if (childTree.size() > 0) {
            ((TaskGroupTreeNode)((UITreeNode)childTree.get(0)).getData()).setFirst(true);
            ((TaskGroupTreeNode)((UITreeNode)childTree.get(childTree.size() - 1)).getData()).setLast(true);
        }
        return childTree;
    }

    @Override
    public List<UITreeNode<TaskGroupTreeNode>> locationByTask(String taskKey) {
        List designTaskGroupDefines = this.designTimeViewControl.listTaskGroupByTask(taskKey);
        if (CollectionUtils.isEmpty(designTaskGroupDefines)) {
            return this.getAllTaskGroupTreeNodeWithChild(this.listChildTaskGroup(null));
        }
        List<Object> result = new ArrayList<UITreeNode<TaskGroupTreeNode>>();
        List allTaskGroupDefines = this.designTimeViewControl.listAllTaskGroup();
        Map<String, List<DesignTaskGroupDefine>> allParentToChild = this.getParentToChild(allTaskGroupDefines);
        Map<String, DesignTaskGroupDefine> keyToTaskGroupMap = allTaskGroupDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a));
        DesignTaskGroupDefineImpl allTaskGroupdefine = new DesignTaskGroupDefineImpl();
        allTaskGroupdefine.setTitle(ALL_TASK_TITLE);
        HashMap pathParentToChild = new HashMap();
        boolean hasTree = false;
        for (DesignTaskGroupDefine thisParam : designTaskGroupDefines) {
            DesignTaskGroupDefineImpl superGroupParams = new DesignTaskGroupDefineImpl();
            UITreeNode superNode = null;
            List<Object> childrenNodes = new ArrayList();
            DesignTaskGroupDefine thisParams = thisParam;
            UITreeNode thisNode = new UITreeNode((TreeData)new TaskGroupTreeNode(thisParams));
            block1: while (StringUtils.hasLength(((TaskGroupTreeNode)thisNode.getData()).getKey()) && !ALL_TASK_TITLE_KEY.equals(((TaskGroupTreeNode)thisNode.getData()).getKey())) {
                if (pathParentToChild.get(((TaskGroupTreeNode)thisNode.getData()).getParentKey()) != null) {
                    List uiTreeNodes = (List)pathParentToChild.get(((TaskGroupTreeNode)thisNode.getData()).getParentKey());
                    for (UITreeNode uiTreeNode : uiTreeNodes) {
                        if (!((TaskGroupTreeNode)uiTreeNode.getData()).getKey().equals(((TaskGroupTreeNode)thisNode.getData()).getKey())) continue;
                        if (((TaskGroupTreeNode)thisNode.getData()).getKey().equals(thisParam.getKey())) {
                            uiTreeNode.setChecked(true);
                            break block1;
                        }
                        uiTreeNode.setExpand(true);
                        uiTreeNode.setChildren(thisNode.getChildren());
                        break block1;
                    }
                    break;
                }
                superGroupParams = keyToTaskGroupMap.get(((TaskGroupTreeNode)thisNode.getData()).getParentKey());
                if (superGroupParams == null) {
                    superGroupParams = allTaskGroupdefine;
                    ((TaskGroupTreeNode)thisNode.getData()).setParentKey(ALL_TASK_TITLE_KEY);
                }
                ((TaskGroupTreeNode)thisNode.getData()).setParentName(superGroupParams.getTitle());
                superNode = new UITreeNode((TreeData)new TaskGroupTreeNode((DesignTaskGroupDefine)superGroupParams));
                childrenNodes = this.listChildTaskGroup((UITreeNode<TaskGroupTreeNode>)thisNode, allParentToChild, thisParam.getKey(), false);
                pathParentToChild.put(((TaskGroupTreeNode)thisNode.getData()).getParentKey(), childrenNodes);
                thisNode = superNode;
                if (CollectionUtils.isEmpty(childrenNodes)) continue;
                superNode.setChildren(childrenNodes);
                superNode.setIcon(GROUP_ICON);
            }
            if (hasTree || !CollectionUtils.isEmpty(result)) continue;
            result = thisNode.getChildren();
            pathParentToChild.put((String)null, result);
            pathParentToChild.put("", result);
            hasTree = true;
        }
        return this.getAllTaskGroupTreeNodeWithChild(result);
    }

    @Override
    public List<ResourcePath> getResourcePath(String groupKey) {
        ArrayList<ResourcePath> resources = new ArrayList<ResourcePath>();
        if (StringUtils.hasLength(groupKey) && !ALL_TASK_TITLE_KEY.equals(groupKey)) {
            DesignTaskGroupDefine thenTaskGroup = this.queryTaskGroupDefine(groupKey);
            ArrayList<DesignTaskGroupDefine> allTaskGroupDefine = new ArrayList<DesignTaskGroupDefine>();
            allTaskGroupDefine.add(thenTaskGroup);
            while (StringUtils.hasLength(thenTaskGroup.getParentKey())) {
                DesignTaskGroupDefine parentTaskGroup = this.queryTaskGroupDefine(thenTaskGroup.getParentKey());
                allTaskGroupDefine.add(parentTaskGroup);
                thenTaskGroup = parentTaskGroup;
            }
            Collections.reverse(allTaskGroupDefine);
            ArrayList<String> path = new ArrayList<String>();
            for (DesignTaskGroupDefine taskGroupDefine : allTaskGroupDefine) {
                path.add(taskGroupDefine.getKey());
                ResourcePath resourcePath = new ResourcePath(taskGroupDefine.getKey(), taskGroupDefine.getTitle(), "", path.toArray(new String[path.size()]));
                resources.add(resourcePath);
            }
        } else {
            resources.add(new ResourcePath(ALL_TASK_TITLE_KEY, ALL_TASK_TITLE, "", ALL_TASK_TITLE_KEY));
        }
        return resources;
    }
}

