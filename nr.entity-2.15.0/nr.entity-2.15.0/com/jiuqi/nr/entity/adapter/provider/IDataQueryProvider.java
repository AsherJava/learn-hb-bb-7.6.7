/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.provider;

import com.jiuqi.nr.entity.adapter.provider.QueryOptions;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import java.util.Map;

public interface IDataQueryProvider {
    public EntityResultSet getAllData();

    public EntityResultSet getRootData();

    public EntityResultSet getChildData(QueryOptions.TreeType var1, String ... var2);

    public EntityResultSet findByEntityKeys();

    public String getParent(String var1);

    public String getParent(Map<String, Object> var1);

    public int getMaxDepth();

    public int getMaxDepthByEntityKey(String var1);

    public EntityResultSet findByCodes();

    public int getChildCount(String var1, QueryOptions.TreeType var2);

    public Map<String, Integer> getChildCountByParent(String var1, QueryOptions.TreeType var2);

    public String[] getParentsEntityKeyDataPath(String var1);

    public String[] getParentsEntityKeyDataPath(Map<String, Object> var1);

    public int getTotalCount();

    public EntityResultSet simpleQueryByKeys();
}

