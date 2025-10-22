/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.sub.database.dao;

import com.jiuqi.nr.data.estimation.sub.database.entity.DataSchemeSubDatabaseDefine;

public interface IDataSchemeSubDatabaseDao {
    public DataSchemeSubDatabaseDefine findRecord(String var1, String var2);

    public int insertRecord(DataSchemeSubDatabaseDefine var1);

    public int deleteRecord(DataSchemeSubDatabaseDefine var1);
}

