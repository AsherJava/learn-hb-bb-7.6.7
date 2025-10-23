/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.dao;

import com.jiuqi.nr.transmission.data.domain.SyncSchemeDO;
import java.util.List;

public interface ISyncSchemeDao {
    public int add(SyncSchemeDO var1);

    public int delete(String var1);

    public int update(SyncSchemeDO var1);

    public int updates(String var1, String var2, String var3);

    public SyncSchemeDO get(String var1);

    public List<SyncSchemeDO> list();

    public List<SyncSchemeDO> listByGroup(String var1);
}

