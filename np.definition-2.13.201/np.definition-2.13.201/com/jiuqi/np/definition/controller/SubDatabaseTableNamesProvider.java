/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.definition.controller;

import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.List;
import java.util.Map;

public interface SubDatabaseTableNamesProvider {
    public String getSubDatabaseTableName(String var1, String var2);

    default public List<ColumnModelDefine> getSubDatabaseTableColumns(String schemeKey, List<String> fieldKeys) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    default public Map<String, List<ColumnModelDefine>> getSubDatabaseFieldKey2Columns(String schemeKey, List<String> fieldKeys) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

