/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.mapping.web;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.mapping.common.MappingErrorEnum;
import com.jiuqi.nr.mapping.util.MappingSchemeUtil;
import com.jiuqi.nr.mapping.web.vo.TaskFormSchemeVO;
import com.jiuqi.nr.mapping.web.vo.TaskTreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/mapping"})
@Api(tags={"\u6620\u5c04\u65b9\u6848\u7ba1\u7406"})
public class MappingSchemeController {
    protected final Logger logger = LoggerFactory.getLogger(MappingSchemeController.class);
    @Autowired
    private IDesignTimeViewController designTime;
    @Autowired
    private IRunTimeViewController runTime;

    @GetMapping(value={"/scheme/init-task-tree/{taskKey}"})
    @ApiOperation(value="\u52a0\u8f7d\u4efb\u52a1\u6811\u5f62")
    public List<ITree<TaskTreeNode>> initTaskTree(@PathVariable String taskKey) {
        ArrayList<ITree<TaskTreeNode>> res = new ArrayList<ITree<TaskTreeNode>>();
        res.add(this.buildRootNode(taskKey));
        return res;
    }

    private ITree<TaskTreeNode> buildRootNode(String taskKey) {
        TaskTreeNode root = new TaskTreeNode();
        root.setKey("00000000000000000000000000000000");
        root.setCode("00000000000000000000000000000000");
        root.setTitle("\u5168\u90e8\u4efb\u52a1");
        root.setType("GROUP");
        ITree node = new ITree((INode)root);
        node.setExpanded(true);
        node.setLeaf(false);
        ArrayList<ITree<TaskTreeNode>> children = new ArrayList<ITree<TaskTreeNode>>();
        this.buildTaskTree(children, taskKey);
        node.setChildren(children);
        return node;
    }

    private void buildTaskTree(List<ITree<TaskTreeNode>> children, String taskKey) {
        List allTaskGroup = this.designTime.getAllTaskGroup();
        List allTaskDefines = this.runTime.getAllTaskDefines();
        Map<String, TaskDefine> allTaskMap = allTaskDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, task -> task));
        HashSet<String> tasksHasGroup = new HashSet<String>();
        this.buildChildTree(null, allTaskGroup, allTaskMap, children, tasksHasGroup, taskKey);
        for (TaskDefine task2 : allTaskDefines) {
            if (tasksHasGroup.contains(task2.getKey())) continue;
            List<FormSchemeDefine> formSchemes = this.getFormScheme(task2.getKey());
            children.add(MappingSchemeUtil.convertTaskTreeNode(task2, formSchemes, taskKey));
        }
    }

    private void buildChildTree(String parentId, List<DesignTaskGroupDefine> allTaskGroup, Map<String, TaskDefine> allTaskMap, List<ITree<TaskTreeNode>> children, Set<String> tasksHasGroup, String taskKey) {
        List links;
        for (DesignTaskGroupDefine group : allTaskGroup) {
            if (!MappingSchemeController.equals(group.getParentKey(), parentId)) continue;
            ITree<TaskTreeNode> node = MappingSchemeUtil.convertGroupTreeNode(group);
            children.add(node);
            ArrayList<ITree<TaskTreeNode>> nodeChildren = new ArrayList<ITree<TaskTreeNode>>();
            this.buildChildTree(group.getKey(), allTaskGroup, allTaskMap, nodeChildren, tasksHasGroup, taskKey);
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
                List<FormSchemeDefine> formSchemes = this.getFormScheme(task.getKey());
                children.add(MappingSchemeUtil.convertTaskTreeNode(task, formSchemes, taskKey));
            }
        }
    }

    @GetMapping(value={"/scheme/get-task-formscheme/{treeNodeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u6811\u548c\u62a5\u8868\u65b9\u6848\u5217\u8868")
    public TaskFormSchemeVO getTaskFormScheme(@PathVariable String treeNodeKey) throws JQException {
        TaskDefine task;
        String taskKey = "";
        FormSchemeDefine formScheme = null;
        TaskFormSchemeVO res = new TaskFormSchemeVO();
        if (treeNodeKey.contains("T@")) {
            taskKey = treeNodeKey.replace("T@", "");
        } else if (treeNodeKey.contains("F@")) {
            String formKey = treeNodeKey.replace("F@", "");
            try {
                formScheme = this.runTime.getFormScheme(formKey);
                taskKey = formScheme.getTaskKey();
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
                throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_000, (Throwable)e);
            }
        }
        try {
            task = this.runTime.queryTaskDefine(taskKey);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)MappingErrorEnum.MAPPING_000, (Throwable)e);
        }
        List<FormSchemeDefine> formSchemes = this.getFormScheme(taskKey);
        res.setTaskKey(taskKey);
        res.setTaskTitle(task.getTitle());
        if (!CollectionUtils.isEmpty(formSchemes)) {
            res.setFormShemeKey(!Objects.isNull(formScheme) ? formScheme.getKey() : formSchemes.get(0).getKey());
            res.setFormShemeTitle(!Objects.isNull(formScheme) ? formScheme.getTitle() : formSchemes.get(0).getTitle());
            res.setFormSchemes(formSchemes.stream().map(MappingSchemeUtil::convertFormScheme).collect(Collectors.toList()));
        }
        return res;
    }

    private List<FormSchemeDefine> getFormScheme(String taskKey) {
        try {
            return this.runTime.queryFormSchemeByTask(taskKey);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    private static boolean equals(String a, String b) {
        if (!StringUtils.hasText(a)) {
            a = null;
        }
        if (!StringUtils.hasText(b)) {
            b = null;
        }
        return a == null ? b == null : a.equals(b);
    }
}

