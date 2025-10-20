/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.budget.component.domain;

import com.jiuqi.budget.component.domain.FunctionNode;
import com.jiuqi.nr.common.itree.ITree;
import java.util.List;

public class FunctionVO {
    private List<String> keyWords;
    private List<ITree<FunctionNode>> functionTree;

    public List<String> getKeyWords() {
        return this.keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public List<ITree<FunctionNode>> getFunctionTree() {
        return this.functionTree;
    }

    public void setFunctionTree(List<ITree<FunctionNode>> functionTree) {
        this.functionTree = functionTree;
    }

    public String toString() {
        return "FunctionVO{keyWords=" + this.keyWords + ", functionTree=" + this.functionTree + '}';
    }
}

