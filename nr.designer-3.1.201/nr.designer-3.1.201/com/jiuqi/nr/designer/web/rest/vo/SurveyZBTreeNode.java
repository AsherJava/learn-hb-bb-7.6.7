/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.core.NodeType;

public class SurveyZBTreeNode
implements INode {
    private String key;
    private String code;
    private String title;
    private String zbCode;
    private NodeType nodeType;
    private DesignDataField zb;

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

    public String getZbCode() {
        return this.zbCode;
    }

    public void setZbCode(String zbCode) {
        this.zbCode = zbCode;
    }

    public NodeType getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public DesignDataField getZb() {
        return this.zb;
    }

    public void setZb(DesignDataField zb) {
        this.zb = zb;
    }
}

