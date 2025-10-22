/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.unit.uselector.cacheset;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;

public interface USelectorResultSet {
    public List<String> getFilterSet(String var1);

    public List<IEntityRow> getFilterEntityRows(String var1);

    public IUnitTreeContext getRunContext(String var1);
}

