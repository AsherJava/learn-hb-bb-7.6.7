/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.basedata.select.service;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;

public interface IBaseDataSelectFilter {
    public String getFilterName();

    public void initFilterParams(Object var1);

    public boolean accept(IEntityRow var1);

    default public List<String> getEntryList() {
        return null;
    }
}

