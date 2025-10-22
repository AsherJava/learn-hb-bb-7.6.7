/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.datachange.service;

import com.jiuqi.nr.system.check.datachange.bean.DataSchemeRepairRecord;

public interface DataSchemeRepairRecordService {
    public void insertRepairRecord(DataSchemeRepairRecord var1);

    public DataSchemeRepairRecord getDataSchemeRepairRecord(String var1, String var2);

    public DataSchemeRepairRecord getLastRepairRecord(String var1);

    public void updateRepairRecord(DataSchemeRepairRecord var1);

    public void repairComplete(String var1, String var2);
}

