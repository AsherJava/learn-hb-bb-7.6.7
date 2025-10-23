/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.service.entity.vo;

import com.jiuqi.nr.task.api.service.entity.dto.EntityQueryDTO;

public class EntityDataQueryVO
extends EntityQueryDTO {
    private String locationKey;
    private String groupKey;
    private Integer showContent;
    private boolean expandFirstNode;
    private boolean checkBaseTree;

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

    public Integer getShowContent() {
        return this.showContent;
    }

    public void setShowContent(Integer showContent) {
        this.showContent = showContent;
    }

    public boolean isExpandFirstNode() {
        return this.expandFirstNode;
    }

    public void setExpandFirstNode(boolean expandFirstNode) {
        this.expandFirstNode = expandFirstNode;
    }

    public void setCheckBaseTree(boolean checkBaseTree) {
        this.checkBaseTree = checkBaseTree;
    }

    public boolean isCheckBaseTree() {
        return this.checkBaseTree;
    }
}

