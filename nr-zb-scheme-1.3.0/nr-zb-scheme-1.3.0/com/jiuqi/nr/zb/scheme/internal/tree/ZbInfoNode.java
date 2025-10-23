/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.tree;

import com.jiuqi.nr.zb.scheme.common.NodeType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.internal.tree.INode;

public class ZbInfoNode
implements INode {
    private String key;
    private String parentKey;
    private String code;
    private String title;
    private ZbType zbType;

    public ZbInfoNode() {
    }

    public ZbInfoNode(ZbInfo zbInfo) {
        if (zbInfo != null) {
            this.key = zbInfo.getKey();
            this.parentKey = zbInfo.getParentKey();
            this.code = zbInfo.getCode();
            this.title = zbInfo.getTitle();
            this.zbType = zbInfo.getType();
        }
    }

    public ZbType getZbType() {
        return this.zbType;
    }

    public void setZbType(ZbType zbType) {
        this.zbType = zbType;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.ZB_INFO;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

