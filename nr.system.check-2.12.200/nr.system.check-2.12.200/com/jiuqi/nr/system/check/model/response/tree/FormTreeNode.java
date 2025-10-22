/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 */
package com.jiuqi.nr.system.check.model.response.tree;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.system.check.model.response.tree.NodeType;

public class FormTreeNode
implements INode {
    private String key;
    private String code;
    private String title;
    private String parentKey;
    private NodeType type;

    public FormTreeNode() {
    }

    public FormTreeNode(FormGroupDefine formGroupDefine) {
        if (formGroupDefine != null) {
            this.key = formGroupDefine.getKey();
            this.code = formGroupDefine.getCode();
            this.title = formGroupDefine.getTitle();
            this.type = NodeType.FORM_GROUP;
        }
    }

    public FormTreeNode(FormDefine formDefine, String formGroupKey) {
        if (formDefine != null) {
            this.key = formDefine.getKey();
            this.code = formDefine.getFormCode();
            this.title = formDefine.getTitle();
            this.parentKey = formGroupKey;
            this.type = NodeType.FORM;
        }
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public NodeType getType() {
        return this.type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }
}

