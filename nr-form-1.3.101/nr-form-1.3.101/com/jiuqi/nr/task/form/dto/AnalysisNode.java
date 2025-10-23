/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;

public class AnalysisNode
implements TreeData {
    public static final String FOLDER_NODE_TYPE = "com.jiuqi.nvwa.dataanalyze";
    public static final String FOLDER_NODE_ICON = "#icon16_DH_A_NW_gongnengfenzushouqi";
    public static final String QR_NODE_ICON = "#icon16_SHU_A_NW_kuaisubaobiao";
    public static final String QUICK_REPORT_NODE_TYPE = "QUICK_REPORT_NODE";
    private String key;
    private String title;
    private String parent;
    private String icon;
    private NodeType nodeType;
    private String type;

    public AnalysisNode() {
    }

    public AnalysisNode(ResourceTreeNode resourceTreeNode) {
        String type = resourceTreeNode.getType();
        this.key = resourceTreeNode.getGuid();
        this.title = resourceTreeNode.getTitle();
        this.parent = resourceTreeNode.getParent();
        if ("com.jiuqi.nvwa.quickreport.business".equals(type)) {
            this.nodeType = NodeType.QUICK_REPORT;
            this.icon = QR_NODE_ICON;
        } else if (FOLDER_NODE_TYPE.equals(type)) {
            this.nodeType = NodeType.FOLDER;
            this.icon = FOLDER_NODE_ICON;
        }
        this.type = QUICK_REPORT_NODE_TYPE;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
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

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public NodeType getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static enum NodeType {
        FOLDER,
        QUICK_REPORT;

    }
}

