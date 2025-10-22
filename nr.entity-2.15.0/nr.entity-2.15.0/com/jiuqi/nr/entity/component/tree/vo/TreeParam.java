/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.component.tree.vo;

import com.jiuqi.nr.entity.common.NodeType;
import java.io.Serializable;
import java.util.List;

public class TreeParam
implements Serializable {
    private static final long serialVersionUID = -2810579657134724451L;
    private String keyWords;
    private String locationKey;
    private String groupKey;
    private NodeType nodeType;
    private Integer showContent;
    private List<String> selectedKeys;
    private boolean checkBaseTree;

    public String getKeyWords() {
        return this.keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getLocationKey() {
        return this.locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public NodeType getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public List<String> getSelectedKeys() {
        return this.selectedKeys;
    }

    public void setSelectedKeys(List<String> selectedKeys) {
        this.selectedKeys = selectedKeys;
    }

    public Integer getShowContent() {
        return this.showContent;
    }

    public void setShowContent(Integer showContent) {
        this.showContent = showContent;
    }

    public boolean isCheckBaseTree() {
        return this.checkBaseTree;
    }

    public void setCheckBaseTree(boolean checkBaseTree) {
        this.checkBaseTree = checkBaseTree;
    }
}

