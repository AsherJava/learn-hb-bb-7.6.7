/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataresource.DataResourceNode
 *  com.jiuqi.nr.dataresource.NodeType
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.common.NodeIconGetter
 */
package com.jiuqi.nr.dafafill.tree;

import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import java.util.List;

public class SearchTreeNode {
    private String key;
    private String code;
    private String icon;
    private String title;
    private String titlePath;
    private List<String> keyPath;

    public SearchTreeNode() {
    }

    public SearchTreeNode(DataResourceNode dataResourceNode) {
        this.title = dataResourceNode.getTitle();
        if (dataResourceNode.getType() == com.jiuqi.nr.dataresource.NodeType.RESOURCE_GROUP.getValue()) {
            this.icon = NodeIconGetter.getIconByType((NodeType)NodeType.GROUP);
        } else if (dataResourceNode.getType() == com.jiuqi.nr.dataresource.NodeType.DIM_GROUP.getValue()) {
            this.icon = NodeIconGetter.getIconByType((NodeType)NodeType.DIM);
        } else if (dataResourceNode.getType() == com.jiuqi.nr.dataresource.NodeType.TABLE_DIM_GROUP.getValue()) {
            this.icon = NodeIconGetter.getIconByType((NodeType)NodeType.DIM);
        }
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

    public String getTitlePath() {
        return this.titlePath;
    }

    public void setTitlePath(String titlePath) {
        this.title = titlePath;
    }

    public List<String> getKeyPath() {
        return this.keyPath;
    }

    public void setKeyPath(List<String> keyPath) {
        this.keyPath = keyPath;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

