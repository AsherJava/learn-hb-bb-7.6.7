/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.dao;

import com.jiuqi.nr.query.block.QueryBlockDefine;
import java.util.List;

public interface IQueryBlockDefineDao {
    public Boolean InsertQueryBlockDefine(QueryBlockDefine var1);

    public Boolean UpdateQueryBlockDefine(QueryBlockDefine var1);

    public Boolean DeleteQueryBlockDefineById(String var1);

    public QueryBlockDefine GetQueryBlockDefineById(String var1);

    public List<QueryBlockDefine> GetQueryBlockDefinesByModelId(String var1);
}

