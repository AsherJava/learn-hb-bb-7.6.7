/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.multcheck2.service.dto;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.multcheck2.web.vo.OrgTreeNode;
import java.util.List;

public class MCOrgTreeDTO {
    private List<ITree<OrgTreeNode>> treeList;
    private int size;

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ITree<OrgTreeNode>> getTreeList() {
        return this.treeList;
    }

    public void setTreeList(List<ITree<OrgTreeNode>> treeList) {
        this.treeList = treeList;
    }
}

