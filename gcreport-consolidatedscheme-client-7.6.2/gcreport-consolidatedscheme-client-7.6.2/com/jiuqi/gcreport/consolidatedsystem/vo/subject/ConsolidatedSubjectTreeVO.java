/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.subject;

import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeNodeVO;
import java.util.ArrayList;
import java.util.List;

public class ConsolidatedSubjectTreeVO {
    private List<ConsolidatedSubjectTreeNodeVO> tree = new ArrayList<ConsolidatedSubjectTreeNodeVO>();

    public ConsolidatedSubjectTreeVO(ConsolidatedSubjectTreeNodeVO treeRoot) {
        this.tree.add(treeRoot);
    }

    public List<ConsolidatedSubjectTreeNodeVO> getTree() {
        return this.tree;
    }

    public void setTree(List<ConsolidatedSubjectTreeNodeVO> tree) {
        this.tree = tree;
    }
}

