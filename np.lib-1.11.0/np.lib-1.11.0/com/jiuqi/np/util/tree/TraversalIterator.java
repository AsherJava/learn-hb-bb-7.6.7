/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.util.tree;

import com.jiuqi.np.util.tree.Tree;
import java.util.Iterator;

public interface TraversalIterator<T>
extends Iterator<Tree<T>> {
    public void skip();
}

