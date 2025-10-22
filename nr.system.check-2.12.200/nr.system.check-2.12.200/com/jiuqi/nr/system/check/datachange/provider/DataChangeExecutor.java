/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.datachange.provider;

import com.jiuqi.nr.system.check.datachange.bean.DataChangeRecord;
import com.jiuqi.nr.system.check.datachange.bean.RepairParam;
import java.util.List;

public interface DataChangeExecutor {
    public float getOrder();

    public String getExecutorType();

    public List<DataChangeRecord> execute(RepairParam var1);
}

