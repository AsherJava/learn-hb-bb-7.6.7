/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.bill.bd.core.service;

import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapDO;
import com.jiuqi.va.domain.common.R;
import java.util.List;

public interface ApplyRegMapService {
    public List<ApplyRegMapDO> getMap(ApplyRegMapDO var1);

    public R createMap(ApplyRegMapDO var1);

    public R updateMap(ApplyRegMapDO var1);

    public R deleteMap(ApplyRegMapDO var1);
}

