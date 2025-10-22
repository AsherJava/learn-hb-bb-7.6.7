/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.calibre2.domain.CalibreGroupDTO
 *  com.jiuqi.nr.calibre2.vo.GroupNodeVO
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.bi.dataset.remote.service;

import com.jiuqi.nr.calibre2.domain.CalibreGroupDTO;
import com.jiuqi.nr.calibre2.vo.GroupNodeVO;
import com.jiuqi.nr.common.itree.ITree;
import java.util.List;

public interface CaliberTreeService {
    public List<ITree<GroupNodeVO>> initTree();

    public List<ITree<GroupNodeVO>> getChildrenNodes(CalibreGroupDTO var1);

    public List<ITree<GroupNodeVO>> locationTreeNode(String var1);
}

