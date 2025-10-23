/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.internal.dao;

import com.jiuqi.nr.summary.internal.entity.SummaryConfigDO;

public interface ISummaryConfigDao {
    public void insert(SummaryConfigDO var1);

    public void update(SummaryConfigDO var1);

    public SummaryConfigDO queryByMenuId(String var1);
}

