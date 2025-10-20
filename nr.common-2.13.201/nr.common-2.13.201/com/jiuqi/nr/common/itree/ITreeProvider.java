/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.itree;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import java.util.List;

public interface ITreeProvider<E extends INode> {
    public List<ITree<E>> getRoot();

    public List<ITree<E>> getChildren(String var1);
}

