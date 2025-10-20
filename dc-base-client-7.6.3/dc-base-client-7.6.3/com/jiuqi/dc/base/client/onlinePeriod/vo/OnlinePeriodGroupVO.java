/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.client.onlinePeriod.vo;

import java.util.List;

public class OnlinePeriodGroupVO {
    private String id;
    private String title;
    private String nodeType;
    private Boolean expand;
    private String moduleCode;
    private List<OnlinePeriodGroupVO> children;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Boolean getExpand() {
        return this.expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public List<OnlinePeriodGroupVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<OnlinePeriodGroupVO> children) {
        this.children = children;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
}

