/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.authz.bean;

import com.jiuqi.nr.authz.bean.RoleWebImpl;
import com.jiuqi.nr.common.itree.ITree;
import java.util.List;

public class RoleSelectBO {
    private int all;
    private List<ITree<RoleWebImpl>> nodes;

    public int getAll() {
        return this.all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public List<ITree<RoleWebImpl>> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<ITree<RoleWebImpl>> nodes) {
        this.nodes = nodes;
    }
}

