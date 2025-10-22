/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.system.check.service;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.system.check.model.response.tree.FormTreeNode;
import java.util.List;

public interface SCFormTreeService {
    public List<ITree<FormTreeNode>> createFormTree(String var1) throws Exception;
}

