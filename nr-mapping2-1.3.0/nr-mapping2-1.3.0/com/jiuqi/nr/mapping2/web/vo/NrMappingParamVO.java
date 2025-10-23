/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.mapping2.web.vo;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.mapping2.web.vo.LabelVO;
import com.jiuqi.nr.mapping2.web.vo.TaskTreeNode;
import java.util.List;

public class NrMappingParamVO {
    List<ITree<TaskTreeNode>> taskTree;
    List<LabelVO> types;

    public List<ITree<TaskTreeNode>> getTaskTree() {
        return this.taskTree;
    }

    public void setTaskTree(List<ITree<TaskTreeNode>> taskTree) {
        this.taskTree = taskTree;
    }

    public List<LabelVO> getTypes() {
        return this.types;
    }

    public void setTypes(List<LabelVO> types) {
        this.types = types;
    }
}

