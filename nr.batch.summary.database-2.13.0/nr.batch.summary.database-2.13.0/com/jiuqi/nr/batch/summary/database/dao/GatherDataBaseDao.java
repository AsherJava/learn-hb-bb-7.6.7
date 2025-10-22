/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.batch.summary.database.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.batch.summary.database.intf.GatherDataBase;

public interface GatherDataBaseDao {
    public GatherDataBase getGatherDataBase(String var1);

    public void insertGatherDataBase(GatherDataBase var1) throws DBParaException;

    public void deleteGatherDataBase(String var1) throws DBParaException;

    public void deleteTableData(String var1);
}

