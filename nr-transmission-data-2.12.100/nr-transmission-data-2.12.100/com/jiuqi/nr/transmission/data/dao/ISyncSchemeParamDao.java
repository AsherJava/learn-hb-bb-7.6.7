/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.dao;

import com.jiuqi.nr.transmission.data.domain.SyncSchemeParamDO;
import java.util.List;

public interface ISyncSchemeParamDao {
    public int[] batchAdd(List<SyncSchemeParamDO> var1);

    public int add(SyncSchemeParamDO var1);

    public int delete(String var1);

    public int update(SyncSchemeParamDO var1);

    public int[] batchUpdate(List<SyncSchemeParamDO> var1);

    public SyncSchemeParamDO get(String var1);

    public List<SyncSchemeParamDO> list();

    public List<SyncSchemeParamDO> listByGroup(List<String> var1);
}

