/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.data.estimation.web.tasktree;

import com.jiuqi.nr.common.itree.INode;

public interface EstimationTaskTreeNode
extends INode {
    public static final String typeTask = "task";
    public static final String typeFormScheme = "formScheme";

    public String getNodeType();
}

