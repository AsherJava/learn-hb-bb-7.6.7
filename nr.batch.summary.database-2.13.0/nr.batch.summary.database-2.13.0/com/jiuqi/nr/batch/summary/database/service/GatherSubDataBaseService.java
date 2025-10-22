/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.database.service;

import com.jiuqi.nr.batch.summary.database.intf.GatherSubDataBase;
import java.util.Set;

public interface GatherSubDataBaseService {
    public void creatGatherSubDataBase(String var1, String var2, boolean var3) throws Exception;

    public void copyCheckTables(String var1, String var2) throws Exception;

    public GatherSubDataBase query(String var1, String var2);

    public void delete(String var1, String var2) throws Exception;

    public void delete(String var1, String var2, boolean var3) throws Exception;

    public void deleteCheckTables(String var1, String var2) throws Exception;

    public void deleteCheckTables(String var1, String var2, boolean var3) throws Exception;

    public void deleteAndCreateGatherDataBase(String var1, String var2);

    public boolean isExistGatherDataBase(String var1, String var2);

    public Set<String> getTables(String var1, String var2);
}

