/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;

public class FormCopyTaskTreeNode
implements INode {
    private String key;
    private String code;
    private String title;
    private String order;
    private String parentId;
    private String parentTitle;
    private boolean efdcSwitch;
    private NodeType type;

    public FormCopyTaskTreeNode() {
    }

    public FormCopyTaskTreeNode(DesignTaskDefine task) {
        this.key = task.getKey().toString();
        this.code = task.getTaskCode();
        this.title = task.getTitle();
        this.order = task.getOrder();
        this.efdcSwitch = task.getEfdcSwitch();
        this.parentId = null;
        this.type = NodeType.TASK;
    }

    public FormCopyTaskTreeNode(DesignFormSchemeDefine scheme) {
        this.key = scheme.getKey().toString();
        this.code = scheme.getFormSchemeCode();
        this.title = scheme.getTitle();
        this.order = scheme.getOrder();
        this.parentId = scheme.getTaskKey().toString();
        this.type = NodeType.FORMSCHEME;
    }

    public FormCopyTaskTreeNode(DesignFormSchemeDefine scheme, String parentTitle, String taskOrder, boolean efdcSwitch) {
        this.key = scheme.getKey().toString();
        this.code = scheme.getFormSchemeCode();
        this.title = scheme.getTitle();
        this.order = taskOrder + scheme.getOrder();
        this.parentId = scheme.getTaskKey().toString();
        this.parentTitle = parentTitle;
        this.efdcSwitch = efdcSwitch;
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

    public boolean isEfdcSwitch() {
        return this.efdcSwitch;
    }

    public void setEfdcSwitch(boolean efdcSwitch) {
        this.efdcSwitch = efdcSwitch;
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

