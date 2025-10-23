/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.nr.datascheme.api.DataTableRel;
import java.util.List;

public interface DataTableRelService {
    public DataTableRel getBySrcTable(String var1);

    public List<DataTableRel> getByDesTable(String var1);
}

