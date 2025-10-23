/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nrdt.parampacket.manage.bean;

import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacket;
import com.jiuqi.nrdt.parampacket.manage.bean.ParamPacketGroup;

public class TreeNode
extends ParamPacket {
    private boolean isFolder;

    public boolean isFolder() {
        return this.isFolder;
    }

    public void setFolder(boolean folder) {
        this.isFolder = folder;
    }

    public static TreeNode convertToTreeNode(ParamPacket paramPacket) {
        TreeNode node = new TreeNode();
        node.setFolder(false);
        node.setGuid(paramPacket.getGuid());
        node.setCode(paramPacket.getCode());
        node.setTitle(paramPacket.getTitle());
        node.setUpdateTime(paramPacket.getUpdateTime());
        node.setParent(paramPacket.getParent());
        node.setFileKey(paramPacket.getFileKey());
        node.setSchemeKey(paramPacket.getSchemeKey());
        node.setEnable(paramPacket.getEnable());
        node.setTasks(paramPacket.getTasks());
        return node;
    }

    public static TreeNode convertToTreeNode(ParamPacketGroup paramPacketGroup) {
        TreeNode node = new TreeNode();
        node.setFolder(true);
        node.setGuid(paramPacketGroup.getGuid());
        node.setParent(paramPacketGroup.getParent());
        node.setSchemeKey(paramPacketGroup.getSchemeKey());
        node.setTitle(paramPacketGroup.getTitle());
        return node;
    }
}

