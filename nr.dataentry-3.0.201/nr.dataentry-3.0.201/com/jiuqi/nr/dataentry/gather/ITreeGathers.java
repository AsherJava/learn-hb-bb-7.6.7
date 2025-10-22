/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.tree.Tree
 */
package com.jiuqi.nr.dataentry.gather;

import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.dataentry.gather.IGathers;

public interface ITreeGathers<T>
extends IGathers<T> {
    public Tree<T> gather();
}

