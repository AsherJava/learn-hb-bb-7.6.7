/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.service.entity.dto.EntityDataDTO
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.formula.service;

import com.jiuqi.nr.formula.service.IFormulaDataService;
import com.jiuqi.nr.task.api.service.entity.dto.EntityDataDTO;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import java.util.List;

public interface ICustomFormulaService
extends IFormulaDataService {
    public Boolean existCustomFormula();

    public List<UITreeNode<EntityDataDTO>> initUnitTree(String var1, String var2);

    public List<UITreeNode<EntityDataDTO>> loadChildren(String var1, String var2);
}

