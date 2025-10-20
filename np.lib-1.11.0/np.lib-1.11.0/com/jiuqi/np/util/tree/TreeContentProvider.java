/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.util.tree;

import com.jiuqi.np.util.ContentProvider;
import com.jiuqi.np.util.LabelProvider;

public interface TreeContentProvider
extends ContentProvider,
LabelProvider {
    public Object[] getChildren(Object var1);

    public boolean hasChildren(Object var1);

    public Object getParent(Object var1);
}

