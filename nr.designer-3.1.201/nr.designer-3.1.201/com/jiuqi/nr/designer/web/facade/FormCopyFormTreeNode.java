/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;

public class FormCopyFormTreeNode
implements INode {
    private String key;
    private String code;
    private String title;
    private String order;
    private String parentId;
    private NodeType type;

    public FormCopyFormTreeNode() {
    }

    public FormCopyFormTreeNode(String key, String code, String title) {
        this.key = key;
        this.code = code;
        this.title = title;
    }

    public FormCopyFormTreeNode(DesignFormGroupDefine formGroup) {
        this.key = formGroup.getKey().toString();
        this.code = formGroup.getCode();
        this.title = formGroup.getTitle();
        this.order = formGroup.getOrder();
        this.parentId = null;
        this.type = NodeType.FORMGROUP;
    }

    public FormCopyFormTreeNode(DesignFormDefine formDefine, String parentId) {
        this.key = formDefine.getKey().toString();
        this.code = formDefine.getFormCode();
        this.title = formDefine.getTitle();
        this.order = formDefine.getOrder();
        this.parentId = parentId;
        this.type = NodeType.FORM;
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

    public static enum NodeType {
        FORMGROUP,
        FORM;

    }
}

