/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.unit.uselector.filter.listselect;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.unit.uselector.dataio.IRowData;

public interface FilterDataRule {
    public boolean matchRowWithCondition(IRowData var1, IEntityRow var2);

    public boolean isCodeExactMatch();

    public boolean isTitleExactMatch();
}

