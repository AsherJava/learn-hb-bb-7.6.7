/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nvwa.dataengine.common.DataDefinitionsCache
 */
package com.jiuqi.nr.data.logic.internal.query.parse;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nvwa.dataengine.common.DataDefinitionsCache;
import java.util.HashMap;
import java.util.Map;

public class CKRQueryContext
implements IContext {
    private IDatabase database;
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

    public IDatabase getDatabase() {
        return this.database;
    }

    public void setDatabase(IDatabase database) {
        this.database = database;
    }
}

