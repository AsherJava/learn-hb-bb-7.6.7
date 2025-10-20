/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl.basedatasync.service;

import com.jiuqi.dc.integration.execute.impl.basedatasync.mq.BaseDataSyncParam;

public interface DcBaseDataSyncService {
    public String doSync(BaseDataSyncParam var1);

    public String doSyncByCode(BaseDataSyncParam var1);

    public void updateSyncState(String var1, Integer var2, Integer var3);

    public void deleteSyncInfo(String var1, Integer var2);
}

