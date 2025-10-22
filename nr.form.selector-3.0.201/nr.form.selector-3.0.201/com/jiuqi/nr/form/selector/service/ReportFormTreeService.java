/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.form.selector.service;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.form.selector.context.FormTreeContextImpl;
import com.jiuqi.nr.form.selector.tree.IReportTreeNode;
import java.util.List;

public interface ReportFormTreeService {
    public List<ITree<IReportTreeNode>> getReportTree(FormTreeContextImpl var1) throws Exception;
}

