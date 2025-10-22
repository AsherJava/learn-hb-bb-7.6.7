/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 */
package com.jiuqi.nr.datacheck.dataanalyze.vo;

import com.jiuqi.nr.datacheck.dataanalyze.vo.CheckModelState;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;

public class ResourceNodeVO {
    private String guid;
    private String title;
    private String type;
    private CheckModelState state;

    public ResourceNodeVO() {
    }

    public ResourceNodeVO(ResourceTreeNode node) {
        this.guid = node.getGuid();
        this.title = node.getTitle();
        this.type = node.getType();
    }

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CheckModelState getState() {
        return this.state;
    }

    public void setState(CheckModelState state) {
        this.state = state;
    }
}

