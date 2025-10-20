/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tree;

import com.jiuqi.bi.util.tree.TreeException;
import java.util.Iterator;

public interface ErrorHandler {
    public void onParentNotFound(Object var1) throws TreeException;

    public void onPathLooped(Iterator var1) throws TreeException;
}

