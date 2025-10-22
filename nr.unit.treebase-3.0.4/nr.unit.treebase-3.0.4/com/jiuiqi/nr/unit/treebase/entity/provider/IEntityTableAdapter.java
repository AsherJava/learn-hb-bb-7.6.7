/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuiqi.nr.unit.treebase.entity.provider;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;

public interface IEntityTableAdapter {
    public int getDirectChildCount(String var1);

    public int getAllChildCount(String var1);

    public String[] getNodePath(String var1);

    public List<IEntityRow> getAllRows();

    public List<IEntityRow> getRootRows();

    public List<IEntityRow> getChildRows(String var1);

    public List<IEntityRow> getAllChildRows(String var1);
}

