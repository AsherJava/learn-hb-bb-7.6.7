/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DataTableDesignService {
    public <E extends DesignDataTable> String[] insertDataTables(List<E> var1);

    public <E extends DesignDataTable> void updateDataTables(List<E> var1);

    @Deprecated
    public <DM extends DesignDataDimension> Map<DesignDataDimension, String> initCoverTable(DesignDataScheme var1, Collection<DM> var2);

    public List<DesignDataTable> searchBy(String var1, String var2, int var3);

    public List<DesignDataTable> searchBy(List<String> var1, String var2, int var3);
}

