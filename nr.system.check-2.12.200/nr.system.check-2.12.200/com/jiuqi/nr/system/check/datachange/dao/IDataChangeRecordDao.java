/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.datachange.dao;

import com.jiuqi.nr.system.check.datachange.bean.DataChangeRecord;
import java.util.List;

public interface IDataChangeRecordDao {
    public int insert(DataChangeRecord var1);

    public List<DataChangeRecord> get(String var1);

    public void insert(List<DataChangeRecord> var1);
}

