/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.multcheck2.web.vo;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.multcheck2.web.vo.OrgTreeNode;
import com.jiuqi.nr.multcheck2.web.vo.TaskTreeNodeVO;
import java.util.List;

public class SchemeTreeVO {
    private List<ITree<TaskTreeNodeVO>> task;
    private List<ITree<OrgTreeNode>> rule;

    public List<ITree<TaskTreeNodeVO>> getTask() {
        return this.task;
    }

    public void setTask(List<ITree<TaskTreeNodeVO>> task) {
        this.task = task;
    }

    public List<ITree<OrgTreeNode>> getRule() {
        return this.rule;
    }

    public void setRule(List<ITree<OrgTreeNode>> rule) {
        this.rule = rule;
    }
}

