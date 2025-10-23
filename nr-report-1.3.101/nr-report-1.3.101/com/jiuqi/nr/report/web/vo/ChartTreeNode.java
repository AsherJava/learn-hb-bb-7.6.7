/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nvwa.datav.dashboard.domain.WidgetTreeNode
 */
package com.jiuqi.nr.report.web.vo;

import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nvwa.datav.dashboard.domain.WidgetTreeNode;

public class ChartTreeNode
implements TreeData {
    private String key;
    private String code;
    private String title;
    private String type;
    private String parentId;
    private String parentTitle;
    private String widgetId;
    private String widgetType;
    private boolean isParent;

    public ChartTreeNode() {
    }

    public ChartTreeNode(WidgetTreeNode widgetTreeNode) {
        if (widgetTreeNode != null) {
            this.key = widgetTreeNode.getId();
            this.code = widgetTreeNode.getId();
            this.title = widgetTreeNode.getTitle();
            this.type = widgetTreeNode.getType();
            this.widgetId = widgetTreeNode.getWidgetId();
            this.widgetType = widgetTreeNode.getWidgetType();
            this.isParent = widgetTreeNode.isParent();
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentTitle() {
        return this.parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public String getWidgetId() {
        return this.widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public String getWidgetType() {
        return this.widgetType;
    }

    public void setWidgetType(String widgetType) {
        this.widgetType = widgetType;
    }

    public boolean isParent() {
        return this.isParent;
    }

    public void setParent(boolean parent) {
        this.isParent = parent;
    }
}

