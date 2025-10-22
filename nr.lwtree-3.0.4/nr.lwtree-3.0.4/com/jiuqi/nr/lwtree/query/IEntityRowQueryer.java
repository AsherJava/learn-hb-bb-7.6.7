/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.lwtree.query;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.lwtree.query.IEntityRowCounter;
import java.util.List;

public interface IEntityRowQueryer {
    public IEntityTable getIEntityTable();

    public List<IEntityRow> getAllRows();

    public List<IEntityRow> getRootRows();

    public List<IEntityRow> getChildRows(String var1);

    public List<IEntityRow> getAllChildRows(String var1);

    public IEntityRow findByEntityKey(String var1);

    public IEntityRow findParentEntityRow(IEntityRow var1);

    public IEntityRowCounter getRowCounter();
}

