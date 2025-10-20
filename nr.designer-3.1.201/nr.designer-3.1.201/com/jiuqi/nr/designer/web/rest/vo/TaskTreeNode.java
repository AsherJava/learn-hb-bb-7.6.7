/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;

public class TaskTreeNode
implements INode {
    private String key;
    private String code;
    private String title;
    private String order;
    private String parentId;
    private NodeType type;
    private String dataScheme;

    public TaskTreeNode() {
    }

    public TaskTreeNode(DesignTaskDefine task) {
        this.key = task.getKey().toString();
        this.code = task.getTaskCode();
        this.title = task.getTitle();
        this.order = task.getOrder();
        this.parentId = null;
        this.type = NodeType.TASK;
        this.dataScheme = task.getDataScheme();
    }

    public TaskTreeNode(DesignFormSchemeDefine scheme) {
        this.key = scheme.getKey().toString();
        this.code = scheme.getOrder();
        this.title = scheme.getTitle();
        this.order = scheme.getOrder();
        this.parentId = scheme.getTaskKey().toString();
        this.type = NodeType.SCHEME;
    }

    public TaskTreeNode(DesignTaskGroupDefine group) {
        this.key = group.getKey();
        this.code = group.getCode();
        this.title = group.getTitle();
        this.order = group.getOrder();
        this.parentId = group.getParentKey();
        this.type = NodeType.TASK_GROUP;
    }

    public NodeType getType() {
        return this.type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public static enum NodeType {
        TASK,
        SCHEME,
        TASK_GROUP;

    }
}

