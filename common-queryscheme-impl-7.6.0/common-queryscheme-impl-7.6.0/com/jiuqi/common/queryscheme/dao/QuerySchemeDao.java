/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.queryscheme.dao;

import com.jiuqi.common.queryscheme.eo.QuerySchemeEO;
import java.util.List;

public interface QuerySchemeDao {
    public List<QuerySchemeEO> listQuerySchemesByUserName(String var1, String var2, String var3);

    public QuerySchemeEO getQuerySchemeById(String var1);

    public void insert(QuerySchemeEO var1);

    public void update(QuerySchemeEO var1);

    public QuerySchemeEO getPriorQuerySchemeByCondition(String var1, String var2, String var3, String var4);

    public QuerySchemeEO getNextQuerySchemeByCondition(String var1, String var2, String var3, String var4);

    public void removeSelectByCondition(String var1, String var2, String var3);

    public void delete(String var1);

    public void updateSortOrderById(String var1, String var2);

    public void updateSelectFlagById(String var1, boolean var2);

    public void rename(String var1, String var2);

    public QuerySchemeEO getLastQueryScheme(String var1, String var2, String var3);
}

