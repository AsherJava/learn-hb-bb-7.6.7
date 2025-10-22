/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.dashboard.defines;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.dashboard.defines.Dashboard;
import com.jiuqi.nr.dashboard.deserializer.DashBoardTreeNodeDeserializer;
import com.jiuqi.nr.dashboard.serializer.DashboradNodeSerializer;

@JsonSerialize(using=DashboradNodeSerializer.class)
@JsonDeserialize(using=DashBoardTreeNodeDeserializer.class)
public class DashboardTreeNode
implements INode {
    public static final String DASHBOARDTREENODE_NODE_DATA_ID = "guid";
    public static final String DASHBOARDTREENODE_NODE_DATA_NAME = "code";
    public static final String DASHBOARDTREENODE_NODE_DATA_TITLE = "title";
    public static final String DASHBOARDTREENODE_NODE_DATA_PARENTID = "parentid";
    public static final String DASHBOARDTREENODE_NODE_DATA_ISGROUP = "isgroup";
    private String id;
    private String code;
    private String title;
    private String parent;
    private Boolean isgroup;

    public Boolean getIsgroup() {
        return this.isgroup;
    }

    public void setIsgroup(Boolean isgroup) {
        this.isgroup = isgroup;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public static DashboardTreeNode buildTreeNodeData(Dashboard dashboard, Boolean isgroup) {
        DashboardTreeNode node = new DashboardTreeNode();
        node.setCode(dashboard.getGuid());
        node.setId(dashboard.getGuid());
        node.setIsgroup(isgroup);
        node.setParent(dashboard.getParentGuid());
        node.setTitle(dashboard.getTitle());
        return node;
    }

    public String getKey() {
        return this.id;
    }

    public String getCode() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }
}

