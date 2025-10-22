/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 */
package com.jiuiqi.nr.unit.treebase.entity.provider;

import com.jiuiqi.nr.unit.treebase.entity.counter.IUnitTreeEntityRowCounter;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import java.util.List;

public interface IUnitTreeEntityRowProvider
extends IUnitTreeEntityRowCounter {
    public String[] getNodePath(IBaseNodeData var1);

    public IEntityRow findEntityRow(IBaseNodeData var1);

    public List<IEntityRow> getAllRows();

    public List<IEntityRow> getRootRows();

    public List<IEntityRow> getChildRows(IBaseNodeData var1);

    default public IEntityTable getStructTreeRows() {
        return null;
    }
}

