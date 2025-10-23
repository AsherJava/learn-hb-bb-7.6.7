/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.tree.core;

import com.jiuqi.nr.summary.tree.core.TreeNodeBuilder;
import java.util.Comparator;

public class CustomDataProviderComparator
implements Comparator<TreeNodeBuilder<?>> {
    @Override
    public int compare(TreeNodeBuilder<?> o1, TreeNodeBuilder<?> o2) {
        return o1.orderForQuery() - o2.orderForQuery();
    }
}

