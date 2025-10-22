/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.uselector.cache;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.uselector.context.USelectorContext;
import java.util.List;

public interface USelectorResultSet {
    public List<String> getFilterSet(String var1);

    public List<IEntityRow> getFilterEntityRows(String var1);

    public USelectorContext getRunContext(String var1);
}

