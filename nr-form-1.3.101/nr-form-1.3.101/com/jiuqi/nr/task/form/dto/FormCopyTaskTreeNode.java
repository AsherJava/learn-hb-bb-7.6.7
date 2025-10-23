/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.task.api.tree.TreeData
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.task.api.tree.TreeData;

public class FormCopyTaskTreeNode
implements TreeData {
    private String key;
    private String code;
    private String title;
    private String order;
    private String parentId;
    private String parentTitle;
    private NodeType type;

    public FormCopyTaskTreeNode() {
    }

    public FormCopyTaskTreeNode(DesignTaskDefine task) {
        this.key = task.getKey();
        this.code = task.getTaskCode();
        this.title = task.getTitle();
        this.order = task.getOrder();
        this.parentId = null;
        this.type = NodeType.TASK;
    }

    public FormCopyTaskTreeNode(DesignFormSchemeDefine scheme) {
        this.key = scheme.getKey();
        this.code = scheme.getFormSchemeCode();
        this.title = scheme.getTitle();
        this.order = scheme.getOrder();
        this.parentId = scheme.getTaskKey();
        this.type = NodeType.FORMSCHEME;
    }

    public FormCopyTaskTreeNode(DesignFormSchemeDefine scheme, String parentTitle, String taskOrder) {
        this.key = scheme.getKey();
        this.code = scheme.getFormSchemeCode();
        this.title = scheme.getTitle();
        this.order = taskOrder + scheme.getOrder();
        this.parentId = scheme.getTaskKey();
        this.parentTitle = parentTitle;
        this.type = NodeType.FORMSCHEME;
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

    public String getParentTitle() {
        return this.parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public NodeType getType() {
        return this.type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public static enum NodeType {
        TASK,
        FORMSCHEME;

    }
}

