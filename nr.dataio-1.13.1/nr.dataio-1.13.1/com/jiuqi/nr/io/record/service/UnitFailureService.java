/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.domain.Page
 */
package com.jiuqi.nr.io.record.service;

import com.jiuqi.np.user.domain.Page;
import com.jiuqi.nr.io.record.bean.UnitFailureRecord;
import com.jiuqi.nr.io.record.bean.UnitFailureSubRecord;
import java.util.List;

public interface UnitFailureService {
    public void saveFailureRecords(List<UnitFailureRecord> var1);

    public void saveFailureSubRecords(List<UnitFailureSubRecord> var1);

    public Page<UnitFailureRecord> queryFailureRecords(String var1, int var2, int var3);

    public Page<UnitFailureRecord> queryFailureRecordsByFactory(String var1, String var2, int var3, int var4);
}

