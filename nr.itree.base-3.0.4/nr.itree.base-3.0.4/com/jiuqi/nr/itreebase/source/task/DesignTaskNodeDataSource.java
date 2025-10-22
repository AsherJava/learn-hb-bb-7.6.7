/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 */
package com.jiuqi.nr.itreebase.source.task;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodemap.TreeBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DesignTaskNodeDataSource
extends TreeBuilder {
    public static final String TYPE_NAME = "type";
    public static final String TYPE_GROUP = "group";
    public static final String TYPE_TASK = "task";
    protected IDesignTimeViewController designTimeViewController;
    protected DefinitionAuthorityProvider authorityProvider;

    public DesignTaskNodeDataSource(IDesignTimeViewController designTimeViewController, DefinitionAuthorityProvider authorityProvider) {
        this.designTimeViewController = designTimeViewController;
        this.authorityProvider = authorityProvider;
        this.initTree();
    }

    @Override
    public List<IBaseNodeData> getRoots() {
        ArrayList<IBaseNodeData> nodes = new ArrayList<IBaseNodeData>();
        nodes.addAll(this.buildGroupNodes(this.designTimeViewController.listTaskGroupByParentGroup(null)));
        nodes.addAll(this.buildTaskNodes(this.designTimeViewController.listTaskByTaskGroup(null)));
        return nodes;
    }

    @Override
    public List<IBaseNodeData> getChildren(IBaseNodeData parent) {
        ArrayList<IBaseNodeData> nodes = new ArrayList<IBaseNodeData>();
        nodes.addAll(this.buildGroupNodes(this.designTimeViewController.listTaskGroupByParentGroup(parent.getKey())));
        nodes.addAll(this.buildTaskNodes(this.designTimeViewController.listTaskByTaskGroup(parent.getKey())));
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

    private List<IBaseNodeData> buildGroupNodes(List<DesignTaskGroupDefine> groups) {
        if (groups != null && !groups.isEmpty()) {
            groups = groups.stream().filter(group -> this.authorityProvider.canReadTaskGroup(group.getKey())).collect(Collectors.toList());
            return groups.stream().map(this::buildGroupNode).collect(Collectors.toList());
        }
        return new ArrayList<IBaseNodeData>();
    }

    private List<IBaseNodeData> buildTaskNodes(List<DesignTaskDefine> tasks) {
        if (tasks != null && !tasks.isEmpty()) {
            tasks = tasks.stream().filter(task -> this.authorityProvider.canReadTask(task.getKey())).collect(Collectors.toList());
            return tasks.stream().map(this::buildTaskNode).collect(Collectors.toList());
        }
        return new ArrayList<IBaseNodeData>();
    }

    private IBaseNodeData buildGroupNode(DesignTaskGroupDefine group) {
        BaseNodeDataImpl node = new BaseNodeDataImpl();
        node.setKey(group.getKey());
        node.setCode(group.getCode());
        node.setTitle(group.getTitle());
        node.put(TYPE_NAME, (Object)TYPE_GROUP);
        return node;
    }

    private IBaseNodeData buildTaskNode(DesignTaskDefine task) {
        BaseNodeDataImpl node = new BaseNodeDataImpl();
        node.setKey(task.getKey());
        node.setCode(task.getTaskCode());
        node.setTitle(task.getTitle());
        node.put(TYPE_NAME, (Object)TYPE_TASK);
        return node;
    }
}

