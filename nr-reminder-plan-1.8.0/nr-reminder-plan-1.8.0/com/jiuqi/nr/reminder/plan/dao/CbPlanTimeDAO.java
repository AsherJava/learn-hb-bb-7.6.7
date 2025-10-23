/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.plan.dao;

import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanTimeDO;
import java.util.List;

public interface CbPlanTimeDAO {
    public int insert(CbPlanTimeDO var1);

    public void batchInsert(List<CbPlanTimeDO> var1);

    public void deleteByPlanId(String var1);

    public List<CbPlanTimeDO> queryByPlanId(String var1);
}

