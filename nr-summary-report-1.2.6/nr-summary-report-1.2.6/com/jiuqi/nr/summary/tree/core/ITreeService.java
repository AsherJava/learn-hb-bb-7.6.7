/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.tree.core;

import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import java.util.List;

public interface ITreeService {
    public String getId();

    public List<TreeNode> getRoots(TreeQueryParam var1) throws SummaryCommonException;

    public List<TreeNode> getChilds(TreeQueryParam var1) throws SummaryCommonException;
}

