/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.zbselector.service;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.zbselector.bean.TaskTreeNode;
import com.jiuqi.nr.zbselector.bean.impl.TaskLinkImpl;
import java.util.List;

public interface IZBSelectorBaseService {
    public List<ITree<TaskTreeNode>> getRootTaskTreeNodes(boolean var1);

    public List<ITree<TaskTreeNode>> getChildTaskTreeNodes(String var1);

    public String getPeriodObjByTask(String var1, String var2);

    public List<TaskLinkImpl> getTaskLinksByFormScheme(String var1);
}

