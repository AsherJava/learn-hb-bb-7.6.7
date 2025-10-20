/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tree;

import com.jiuqi.bi.util.tree.ErrorHandler;
import com.jiuqi.bi.util.tree.TreeException;
import com.jiuqi.bi.util.tree.TreeNode;
import java.util.Iterator;

public interface TreeBuilder {
    public static final int SORT_MODE_NONE = 0;
    public static final int SORT_MODE_ORIGINAL = 1;
    public static final int SORT_MODE_CODE = 2;

    public TreeNode build(Iterator var1) throws TreeException;

    public void setErrorHandler(ErrorHandler var1);

    public void setSortMode(int var1);

    public int getSortMode();
}

