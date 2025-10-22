/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.unit.uselector.source;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;

public interface IUSelectorEntityRowProvider {
    public int getTotalCount();

    public String[] getParentsEntityKeyDataPath(String var1);

    public boolean hasParent(String var1);

    public boolean isLeaf(String var1);

    public IEntityRow findEntityRow(String var1);

    public List<IEntityRow> getAllRows();

    public List<IEntityRow> getAllLeaveRows();

    public List<IEntityRow> getAllNonLeaveRows();

    public List<IEntityRow> getChildRows(List<String> var1);

    public List<IEntityRow> getChildRowsAndSelf(List<String> var1);

    public List<IEntityRow> getAllChildRows(List<String> var1);

    public List<IEntityRow> getAllChildRowsAndSelf(List<String> var1);

    public List<IEntityRow> getAllChildLeaveRows(List<String> var1);

    public List<IEntityRow> getAllChildNonLeaveRows(List<String> var1);

    public List<IEntityRow> getAllParentRows(List<String> var1);

    public List<IEntityRow> getCheckRows(List<String> var1);

    public List<IEntityRow> filterByFormulas(String ... var1);

    public List<IEntityRow> filterByFormulas(IUnitTreeContext var1, String ... var2);

    public List<IEntityRow> getContinueNodeAndAllChildren(List<String> var1);

    public List<IEntityRow> getContinueNode(List<String> var1);
}

