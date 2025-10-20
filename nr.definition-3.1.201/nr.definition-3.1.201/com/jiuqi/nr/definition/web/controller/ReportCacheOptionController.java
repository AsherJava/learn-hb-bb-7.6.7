/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.definition.web.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.option.dao.ReportCacheOptionDao;
import com.jiuqi.nr.definition.option.dto.ReportCacheOption;
import com.jiuqi.nr.definition.web.vo.TaskTreeData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/cache/option"})
@Api(value="\u62a5\u8868\u7f13\u5b58\u914d\u7f6e")
public class ReportCacheOptionController {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ReportCacheOptionDao cacheOptionDao;
    private static final String ROOT_NAME = "\u5168\u90e8\u4efb\u52a1";
    private static final String ROOT_KEY = "00000000-0000-0000-0000-000000000000";

    @ApiOperation(value="\u4efb\u52a1\u6811")
    @GetMapping(value={"/task/tree/{optionType}"})
    public List<ITree<TaskTreeData>> getTaskTree(@PathVariable Integer optionType) {
        List<ReportCacheOption> reportCacheOptions = this.cacheOptionDao.listByOptionType(optionType);
        Set<String> haveOptionTaskKeys = reportCacheOptions.stream().map(ReportCacheOption::getTask).collect(Collectors.toSet());
        List<ITree<TaskTreeData>> treeData = this.buildTaskTree(haveOptionTaskKeys);
        return treeData;
    }

    private List<ITree<TaskTreeData>> buildTaskTree(Set<String> haveOptionTaskKeys) {
        ArrayList<ITree<TaskTreeData>> taskTree = new ArrayList<ITree<TaskTreeData>>();
        ITree<TaskTreeData> rootNode = this.buildNode();
        taskTree.add(rootNode);
        List<TaskGroupDefine> groupDefines = this.runTimeViewController.listAllTaskGroup();
        for (TaskGroupDefine group : groupDefines) {
            ITree<TaskTreeData> groupNode = this.buildNode(group);
            taskTree.add(groupNode);
            List<TaskDefine> taskDefines = this.runTimeViewController.listTaskByTaskGroup(group.getKey());
            if (CollectionUtils.isEmpty(taskDefines)) continue;
            ArrayList<ITree<TaskTreeData>> children = groupNode.getChildren();
            for (TaskDefine task : taskDefines) {
                ITree<TaskTreeData> taskNode = this.buildNode(task, group.getKey());
                if (CollectionUtils.isEmpty(children)) {
                    children = new ArrayList<ITree<TaskTreeData>>();
                    children.add(taskNode);
                    groupNode.setChildren(children);
                    continue;
                }
                children.add(taskNode);
            }
        }
        List<TaskDefine> taskWithoutGroup = this.runTimeViewController.listTaskByTaskGroup("");
        for (TaskDefine task : taskWithoutGroup) {
            ITree<TaskTreeData> taskNode = this.buildNode(task, ROOT_KEY);
            taskTree.add(taskNode);
        }
        return this.buildTree(taskTree, haveOptionTaskKeys);
    }

    private ITree<TaskTreeData> buildNode() {
        ITree root = new ITree((INode)new TaskTreeData());
        TaskTreeData data = (TaskTreeData)root.getData();
        root.setKey(ROOT_KEY);
        root.setTitle(ROOT_NAME);
        root.setExpanded(true);
        data.setKey(ROOT_KEY);
        data.setTitle(ROOT_NAME);
        data.setParentId(null);
        data.setOrder("0");
        return root;
    }

    private ITree<TaskTreeData> buildNode(TaskGroupDefine groupDefine) {
        ITree node = new ITree((INode)new TaskTreeData());
        TaskTreeData data = (TaskTreeData)node.getData();
        node.setKey(groupDefine.getKey());
        node.setTitle(groupDefine.getTitle());
        data.setKey(groupDefine.getKey());
        data.setTitle(groupDefine.getTitle());
        if (StringUtils.hasText(groupDefine.getParentKey())) {
            data.setParentId(groupDefine.getParentKey());
        } else {
            data.setParentId(ROOT_KEY);
        }
        data.setOrder(groupDefine.getOrder());
        return node;
    }

    private ITree<TaskTreeData> buildNode(TaskDefine taskDefine, String parentId) {
        ITree node = new ITree((INode)new TaskTreeData());
        node.setKey(taskDefine.getKey());
        node.setTitle(taskDefine.getTitle());
        node.setLeaf(true);
        TaskTreeData data = (TaskTreeData)node.getData();
        data.setKey(taskDefine.getKey());
        data.setTitle(taskDefine.getTitle());
        data.setCode(taskDefine.getTaskCode());
        data.setParentId(parentId);
        data.setOrder(taskDefine.getOrder());
        return node;
    }

    private List<ITree<TaskTreeData>> buildTree(List<ITree<TaskTreeData>> taskTreeNodes, Set<String> haveOptionTaskKeys) {
        if (CollectionUtils.isEmpty(taskTreeNodes)) {
            return Collections.emptyList();
        }
        Map<String, ITree> treeNodeMap = taskTreeNodes.stream().collect(Collectors.toMap(ITree::getKey, v -> v));
        LinkedHashMap treeMapTemp = new LinkedHashMap();
        List taskGroupTreeDatas = taskTreeNodes.stream().filter(node -> !node.isLeaf()).map(ITree::getData).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(taskGroupTreeDatas)) {
            taskGroupTreeDatas.stream().sorted(Comparator.comparing(TaskTreeData::getOrder)).forEachOrdered(e -> {
                ITree cfr_ignored_0 = (ITree)treeMapTemp.put(e.getKey(), treeNodeMap.get(e.getKey()));
            });
        }
        List taskTreeDatas = taskTreeNodes.stream().filter(node -> node.isLeaf()).map(ITree::getData).collect(Collectors.toList());
        taskTreeDatas.stream().sorted(Comparator.comparing(TaskTreeData::getOrder).reversed()).forEachOrdered(e -> {
            ITree cfr_ignored_0 = (ITree)treeMapTemp.put(e.getKey(), treeNodeMap.get(e.getKey()));
        });
        ITree rootNode = new ITree();
        for (ITree node2 : treeMapTemp.values()) {
            if (null == node2) continue;
            String parent = ((TaskTreeData)node2.getData()).getParentId();
            if (treeMapTemp.containsKey(parent)) {
                ITree parentNode = (ITree)treeMapTemp.get(parent);
                if (null == parentNode) continue;
                this.addChild((ITree<TaskTreeData>)parentNode, (ITree<TaskTreeData>)node2, haveOptionTaskKeys);
                continue;
            }
            this.addChild((ITree<TaskTreeData>)rootNode, (ITree<TaskTreeData>)node2, haveOptionTaskKeys);
        }
        return rootNode.getChildren();
    }

    private void addChild(ITree<TaskTreeData> parentNode, ITree<TaskTreeData> node, Set<String> haveOptionTaskKeys) {
        ArrayList<ITree<TaskTreeData>> children = parentNode.getChildren();
        if (null == children) {
            children = new ArrayList<ITree<TaskTreeData>>();
            children.add(node);
            parentNode.setChildren(children);
        } else {
            children.add(node);
        }
    }
}

