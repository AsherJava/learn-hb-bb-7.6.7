/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.database.service;

import com.jiuqi.nr.batch.summary.database.intf.GatherDataBase;

public interface GatherDataBaseService {
    public void creatGatherDataBase(String var1) throws Exception;

    public void createCheckTable(String var1) throws Exception;

    public GatherDataBase query(String var1);

    public void delete(String var1) throws Exception;

    public void delete(String var1, boolean var2) throws Exception;

    public void deleteCheckTable(String var1) throws Exception;

    public void deleteCheckTable(String var1, boolean var2) throws Exception;

    public boolean isExistGatherDataBase(String var1);

    public boolean isExistDataInGB(String var1) throws Exception;
}

