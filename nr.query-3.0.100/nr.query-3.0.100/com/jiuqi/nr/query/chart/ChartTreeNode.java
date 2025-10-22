/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.query.chart;

import com.jiuqi.nr.common.itree.INode;

public class ChartTreeNode
implements INode {
    private String key;
    private String code;
    private String title;
    private String type;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static ChartTreeNode buildTreeNodeData(String key, String title, boolean isleaf, String type) {
        ChartTreeNode node = new ChartTreeNode();
        if (key != null) {
            node.setKey(key);
        }
        node.setTitle(title);
        node.setTitle(title);
        node.setType(type);
        return node;
    }
}

