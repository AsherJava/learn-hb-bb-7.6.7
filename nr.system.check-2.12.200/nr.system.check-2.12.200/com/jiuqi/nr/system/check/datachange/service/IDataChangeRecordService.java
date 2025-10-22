/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.datachange.service;

import com.jiuqi.nr.system.check.datachange.bean.DataChangeRecord;
import java.util.List;

public interface IDataChangeRecordService {
    public void insert(DataChangeRecord var1);

    public void insertRecords(List<DataChangeRecord> var1);

    public List<DataChangeRecord> getRecordsByRecordType(String var1);
}

