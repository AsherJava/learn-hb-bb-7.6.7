/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  org.json.JSONObject
 */
package com.jiuqi.nr.itreebase.source.task;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.itreebase.context.ITreeContext;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodemap.TreeBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class TaskNodeDataSource
extends TreeBuilder {
    public static final String TYPE_NAME = "type";
    public static final String TYPE_GROUP = "group";
    public static final String TYPE_TASK = "task";
    protected IRunTimeViewController rtViewService;
    protected DefinitionAuthorityProvider authorityProvider;
    protected ITreeContext context;

    public TaskNodeDataSource(IRunTimeViewController rtViewService, DefinitionAuthorityProvider authorityProvider, ITreeContext context) {
        this.rtViewService = rtViewService;
        this.authorityProvider = authorityProvider;
        this.context = context;
        this.initTree();
    }

    @Override
    public List<IBaseNodeData> getRoots() {
        ArrayList<IBaseNodeData> nodes = new ArrayList<IBaseNodeData>();
        nodes.addAll(this.buildGroupNodes(this.rtViewService.listTaskGroupByParentGroup(null)));
        nodes.addAll(this.buildTaskNodes(this.rtViewService.listTaskByTaskGroup(null)));
        return nodes;
    }

    @Override
    public List<IBaseNodeData> getChildren(IBaseNodeData parent) {
        ArrayList<IBaseNodeData> nodes = new ArrayList<IBaseNodeData>();
        nodes.addAll(this.buildGroupNodes(this.rtViewService.listTaskGroupByParentGroup(parent.getKey())));
        nodes.addAll(this.buildTaskNodes(this.rtViewService.listTaskByTaskGroup(parent.getKey())));
        return nodes;
    }

    @Override
    public List<String> getNodePath(IBaseNodeData data) {
        return this.findTreeNode(data) != null ? Arrays.asList(this.findTreeNode(data).getPath()) : new ArrayList<String>();
    }

    @Override
    public boolean isLeaf(IBaseNodeData data) {
        return TYPE_TASK.equals(data.get(TYPE_NAME));
    }

    private List<IBaseNodeData> buildGroupNodes(List<TaskGroupDefine> groups) {
        if (groups != null && !groups.isEmpty()) {
            groups = groups.stream().filter(group -> this.authorityProvider.canReadTaskGroup(group.getKey())).collect(Collectors.toList());
            return groups.stream().map(this::buildGroupNode).collect(Collectors.toList());
        }
        return new ArrayList<IBaseNodeData>();
    }

    private List<IBaseNodeData> buildTaskNodes(List<TaskDefine> tasks) {
        Object fliterNoWorkflowObj;
        ArrayList<TaskDefine> fliterTask = new ArrayList<TaskDefine>();
        boolean fliterNoWorkflow = false;
        JSONObject customVariable = this.context.getCustomVariable();
        if (customVariable != null && !customVariable.isEmpty() && (fliterNoWorkflowObj = customVariable.get("fliterNoWorkflow")) != null && fliterNoWorkflowObj instanceof Boolean) {
            fliterNoWorkflow = (Boolean)fliterNoWorkflowObj;
        }
        if (tasks != null && !tasks.isEmpty()) {
            for (TaskDefine task : tasks) {
                TaskFlowsDefine flowsSetting;
                if (!this.authorityProvider.canReadTask(task.getKey()) || fliterNoWorkflow && (flowsSetting = task.getFlowsSetting()) != null && flowsSetting.getFlowsType().equals((Object)FlowsType.NOSTARTUP)) continue;
                fliterTask.add(task);
            }
            return fliterTask.stream().map(this::buildTaskNode).collect(Collectors.toList());
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
}

