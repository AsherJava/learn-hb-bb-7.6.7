/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.dao;

import com.jiuqi.nr.transmission.data.domain.SyncSchemeGroupDO;
import java.util.List;

public interface ISchemeGroupDao {
    public int add(SyncSchemeGroupDO var1);

    public int delete(String var1);

    public int update(SyncSchemeGroupDO var1);

    public int updates(String var1, String var2, String var3);

    public SyncSchemeGroupDO get(String var1);

    public SyncSchemeGroupDO getByTitle(String var1);

    public List<SyncSchemeGroupDO> list();

    public List<SyncSchemeGroupDO> listByParent(String var1);

    public List<SyncSchemeGroupDO> listByName(String var1);
}

