/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.basedatasync.dao;

import com.jiuqi.gcreport.dimension.basedatasync.entity.BaseDataChangeInfoEO;
import java.util.Date;
import java.util.List;

public interface BaseDataChangeInfoDao {
    public void insertBaseDataChangeInfo(String var1, String var2, String var3, Date var4);

    public int updateSyncState(String var1, int var2, int var3);

    public List<String> getUnSyncBaseCodeList();

    public List<BaseDataChangeInfoEO> listUnSyncInfoByBaseCode(String var1, int var2);

    public void deleteSyncInfo(String var1, Integer var2);

    public void truncateTable();
}

