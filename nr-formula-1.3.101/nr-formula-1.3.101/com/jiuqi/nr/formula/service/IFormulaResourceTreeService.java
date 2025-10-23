/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.formula.service;

import com.jiuqi.nr.formula.dto.FormulaFormTreeDTO;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import java.util.List;

public interface IFormulaResourceTreeService {
    public List<UITreeNode<FormulaFormTreeDTO>> initFormulaResourceTree(String var1);

    public List<UITreeNode<FormulaFormTreeDTO>> initUnitTree(String var1, String var2);

    public List<UITreeNode<FormulaFormTreeDTO>> initTreeAndLocate(String var1, String var2);
}

