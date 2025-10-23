/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.tree;

import com.jiuqi.nr.zb.scheme.common.NodeType;
import com.jiuqi.nr.zb.scheme.core.ZbScheme;
import com.jiuqi.nr.zb.scheme.internal.tree.INode;

public class ZbSchemeNode
implements INode {
    private String key;
    private String parentKey;
    private String title;

    public ZbSchemeNode() {
    }

    public ZbSchemeNode(ZbScheme zbScheme) {
        if (zbScheme != null) {
            this.key = zbScheme.getKey();
            this.parentKey = zbScheme.getParentKey();
            this.title = zbScheme.getTitle();
        }
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.ZB_SCHEME;
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
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getCode() {
        return null;
    }
}

