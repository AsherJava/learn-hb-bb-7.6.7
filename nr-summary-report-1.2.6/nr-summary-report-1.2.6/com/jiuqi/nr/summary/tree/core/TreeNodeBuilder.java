/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.tree.core;

import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import java.util.List;

public interface TreeNodeBuilder<T> {
    public boolean needQuery(TreeQueryParam var1);

    public List<T> queryData(TreeQueryParam var1) throws SummaryCommonException;

    public TreeNode buildTreeNode(T var1, TreeQueryParam var2) throws SummaryCommonException;

    default public int orderForQuery() {
        return 0;
    }
}

