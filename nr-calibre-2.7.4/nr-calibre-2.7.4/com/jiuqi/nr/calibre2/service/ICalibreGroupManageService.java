/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.calibre2.service;

import com.jiuqi.nr.calibre2.domain.CalibreGroupDTO;
import com.jiuqi.nr.calibre2.vo.GroupNodeVO;
import com.jiuqi.nr.common.itree.ITree;
import java.util.List;

public interface ICalibreGroupManageService {
    public List<ITree<GroupNodeVO>> initTree();

    public List<ITree<GroupNodeVO>> getChildrenNodes(CalibreGroupDTO var1);

    public List<ITree<GroupNodeVO>> locationTreeNode(CalibreGroupDTO var1);
}

