/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.uselector.cache;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.uselector.cache.USelectorResultSet;
import com.jiuqi.nr.uselector.context.USelectorContext;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class USelectorResultSetImpl
implements USelectorResultSet {
    @Override
    public List<String> getFilterSet(String selector) {
        return null;
    }

    @Override
    public List<IEntityRow> getFilterEntityRows(String selector) {
        return null;
    }

    @Override
    public USelectorContext getRunContext(String selector) {
        return null;
    }
}

