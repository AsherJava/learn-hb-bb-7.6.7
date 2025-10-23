/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.calibre2.vo;

import com.jiuqi.nr.calibre2.vo.GroupNodeVO;
import com.jiuqi.nr.common.itree.ITree;
import java.util.ArrayList;
import java.util.List;

public class LocationParam {
    ITree<GroupNodeVO> selectNode = new ITree();
    List<ITree<GroupNodeVO>> treeObjs = new ArrayList<ITree<GroupNodeVO>>();

    public void setSelectNode(ITree<GroupNodeVO> selectNode) {
        this.selectNode = selectNode;
    }

    public void setTreeObjs(List<ITree<GroupNodeVO>> treeObjs) {
        this.treeObjs = treeObjs;
    }

    public ITree<GroupNodeVO> getSelectNode() {
        return this.selectNode;
    }

    public List<ITree<GroupNodeVO>> getTreeObjs() {
        return this.treeObjs;
    }
}

