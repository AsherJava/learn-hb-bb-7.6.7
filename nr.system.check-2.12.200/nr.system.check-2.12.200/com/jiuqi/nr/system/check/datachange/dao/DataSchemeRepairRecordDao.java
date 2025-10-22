/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.datachange.dao;

import com.jiuqi.nr.system.check.datachange.bean.DataSchemeRepairRecord;

public interface DataSchemeRepairRecordDao {
    public int insert(DataSchemeRepairRecord var1);

    public DataSchemeRepairRecord getRecordByKey(String var1, String var2);

    public DataSchemeRepairRecord getLastRecord(String var1);

    public void updateRepairRecord(DataSchemeRepairRecord var1);

    public void repairComplete(String var1, String var2);
}

