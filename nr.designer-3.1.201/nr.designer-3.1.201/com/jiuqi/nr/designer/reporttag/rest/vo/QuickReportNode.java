/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 */
package com.jiuqi.nr.designer.reporttag.rest.vo;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;

public class QuickReportNode
implements INode {
    public static final String FOLDER_NODE_TYPE = "com.jiuqi.nvwa.dataanalyze";
    public static final String FOLDER_NODE_ICON = "#icon16_DH_A_NW_gongnengfenzushouqi";
    public static final String QR_NODE_ICON = "#icon16_SHU_A_NW_kuaisubaobiao";
    private String key;
    private String code;
    private String title;
    private String parent;
    private String icon;
    private NodeType nodeType;

    public QuickReportNode(ResourceTreeNode resourceTreeNode) {
        String type = resourceTreeNode.getType();
        this.key = resourceTreeNode.getGuid();
        this.code = resourceTreeNode.getName();
        this.title = resourceTreeNode.getTitle();
        this.parent = resourceTreeNode.getParent();
        if ("com.jiuqi.nvwa.quickreport.business".equals(type)) {
            this.nodeType = NodeType.QUICK_REPORT;
            this.icon = QR_NODE_ICON;
        } else if (FOLDER_NODE_TYPE.equals(type)) {
            this.nodeType = NodeType.FOLDER;
            this.icon = FOLDER_NODE_ICON;
        }
    }

    public void setKey(String key) {
        this.key = key;
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

    public NodeType getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static enum NodeType {
        FOLDER,
        QUICK_REPORT;

    }
}

