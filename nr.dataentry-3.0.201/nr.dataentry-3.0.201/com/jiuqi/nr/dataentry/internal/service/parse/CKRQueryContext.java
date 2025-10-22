/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nvwa.dataengine.common.DataDefinitionsCache
 */
package com.jiuqi.nr.dataentry.internal.service.parse;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nvwa.dataengine.common.DataDefinitionsCache;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class CKRQueryContext
implements IContext {
    private DataDefinitionsCache dataDefinitionsCache;
    private String mainTableName;
    private String dwGroupName;
    private Map<String, String> tableAliaMap = new HashMap<String, String>();

    public DataDefinitionsCache getDataDefinitionsCache() {
        return this.dataDefinitionsCache;
    }

    public void setDataDefinitionsCache(DataDefinitionsCache dataDefinitionsCache) {
        this.dataDefinitionsCache = dataDefinitionsCache;
    }

    public String getMainTableName() {
        return this.mainTableName;
    }

    public void setMainTableName(String mainTableName) {
        this.mainTableName = mainTableName;
    }

    public String getDwGroupName() {
        return this.dwGroupName;
    }

    public void setDwGroupName(String dwGroupName) {
        this.dwGroupName = dwGroupName;
    }

    public Map<String, String> getTableAliaMap() {
        return this.tableAliaMap;
    }

    public void setTableAliaMap(Map<String, String> tableAliaMap) {
        this.tableAliaMap = tableAliaMap;
    }
}

