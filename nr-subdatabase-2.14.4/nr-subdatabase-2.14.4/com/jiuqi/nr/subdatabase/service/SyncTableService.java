/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.subdatabase.service;

import com.jiuqi.nr.subdatabase.facade.SubDataBase;

public interface SyncTableService {
    public boolean isNeedSync(SubDataBase var1);

    public void syncTable(SubDataBase var1, boolean var2) throws Exception;
}

