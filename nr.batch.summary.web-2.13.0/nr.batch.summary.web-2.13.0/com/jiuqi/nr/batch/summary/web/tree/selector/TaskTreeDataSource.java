/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodemap.TreeBuilder
 */
package com.jiuqi.nr.batch.summary.web.tree.selector;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodemap.TreeBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TaskTreeDataSource
extends TreeBuilder {
    public static final String TYPE_NAME = "type";
    public static final String TYPE_GROUP = "group";
    public static final String TYPE_TASK = "task";
    private IRunTimeViewController rtViewService;
    private IRuntimeDataSchemeService dataSchemeService;
    private DefinitionAuthorityProvider authorityProvider;

    public TaskTreeDataSource(IRunTimeViewController rtViewService, IRuntimeDataSchemeService dataSchemeService, DefinitionAuthorityProvider authorityProvider) {
        this.rtViewService = rtViewService;
        this.dataSchemeService = dataSchemeService;
        this.authorityProvider = authorityProvider;
        this.initTree();
    }

    public List<String> getNodePath(IBaseNodeData data) {
        return this.findTreeNode(data) != null ? Arrays.asList(this.findTreeNode(data).getPath()) : new ArrayList<String>();
    }

    public boolean isLeaf(IBaseNodeData data) {
        return TYPE_TASK.equals(data.get(TYPE_NAME));
    }

    public List<IBaseNodeData> getRoots() {
        ArrayList<IBaseNodeData> nodes = new ArrayList<IBaseNodeData>();
        nodes.addAll(this.buildGroupNodes(this.rtViewService.getChildTaskGroups(null, false)));
        nodes.addAll(this.buildTaskNodes(this.rtViewService.getAllRunTimeTasksInGroup(null)));
        return nodes;
    }

    public List<IBaseNodeData> getChildren(IBaseNodeData parent) {
        ArrayList<IBaseNodeData> nodes = new ArrayList<IBaseNodeData>();
        String parentKey = parent.getKey();
        nodes.addAll(this.buildGroupNodes(this.rtViewService.getChildTaskGroups(parentKey, false)));
        nodes.addAll(this.buildTaskNodes(this.rtViewService.getAllRunTimeTasksInGroup(parentKey)));
        return nodes;
    }

    private List<IBaseNodeData> buildGroupNodes(List<TaskGroupDefine> groups) {
        if (groups != null && !groups.isEmpty()) {
            groups = groups.stream().filter(group -> {
                List childTasks = this.rtViewService.getAllRunTimeTasksInGroup(group.getKey());
                return this.filterTasks(childTasks).size() > 0;
            }).collect(Collectors.toList());
            return groups.stream().map(this::buildGroupNode).collect(Collectors.toList());
        }
        return new ArrayList<IBaseNodeData>();
    }

    private IBaseNodeData buildGroupNode(TaskGroupDefine group) {
        BaseNodeDataImpl node = new BaseNodeDataImpl();
        node.setKey(group.getKey());
        node.setCode(group.getCode());
        node.setTitle(group.getTitle());
        node.put(TYPE_NAME, (Object)TYPE_GROUP);
        return node;
    }

    private IBaseNodeData buildTaskNode(TaskDefine task) {
        BaseNodeDataImpl node = new BaseNodeDataImpl();
        node.setKey(task.getKey());
        node.setCode(task.getTaskCode());
        node.setTitle(task.getTitle());
        node.put(TYPE_NAME, (Object)TYPE_TASK);
        return node;
    }

    private List<IBaseNodeData> buildTaskNodes(List<TaskDefine> tasks) {
        return this.filterTasks(tasks).stream().map(this::buildTaskNode).collect(Collectors.toList());
    }

    private List<TaskDefine> filterTasks(List<TaskDefine> tasks) {
        if (tasks != null && !tasks.isEmpty()) {
            tasks = tasks.stream().filter(task -> this.authorityProvider.canReadTask(task.getKey())).collect(Collectors.toList());
            return tasks.stream().filter(task -> {
                DataScheme dataScheme = this.dataSchemeService.getDataScheme(task.getDataScheme());
                return dataScheme != null && dataScheme.getGatherDB() != false;
            }).collect(Collectors.toList());
        }
        return new ArrayList<TaskDefine>();
    }
}

