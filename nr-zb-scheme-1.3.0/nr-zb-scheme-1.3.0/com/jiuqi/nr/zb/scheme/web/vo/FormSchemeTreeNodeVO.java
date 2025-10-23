/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import com.jiuqi.nr.zb.scheme.internal.tree.FormSchemeTreeNode;

public class FormSchemeTreeNodeVO {
    private String key;
    private String code;
    private String title;
    private int type;

    public static FormSchemeTreeNodeVO valueOf(FormSchemeTreeNode formSchemeTreeNode) {
        FormSchemeTreeNodeVO treeNodeVO = new FormSchemeTreeNodeVO();
        treeNodeVO.setKey(formSchemeTreeNode.getKey());
        treeNodeVO.setCode(formSchemeTreeNode.getCode());
        treeNodeVO.setTitle(formSchemeTreeNode.getTitle());
        treeNodeVO.setType(formSchemeTreeNode.getType());
        return treeNodeVO;
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

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toString() {
        return "FormSchemeTreeNodeVO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", type=" + this.type + '}';
    }
}

