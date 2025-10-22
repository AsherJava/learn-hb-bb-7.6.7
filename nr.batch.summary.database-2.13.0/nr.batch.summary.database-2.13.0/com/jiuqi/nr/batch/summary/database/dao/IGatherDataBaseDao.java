/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.batch.summary.database.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.batch.summary.database.intf.GatherSubDataBase;

public interface IGatherDataBaseDao {
    public GatherSubDataBase getGatherSubDataBase(String var1, String var2);

    public void insertGatherSubDataBase(GatherSubDataBase var1) throws DBParaException;

    public void deleteGatherDataBase(String var1, String var2) throws DBParaException;
}

