/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.plan.dao;

import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanDwDO;
import java.util.List;

public interface CbPlanDwDAO {
    public void insert(CbPlanDwDO var1);

    public void batchInsert(List<CbPlanDwDO> var1);

    public void deleteByPlanId(String var1);

    public List<CbPlanDwDO> queryByPlanId(String var1);

    public List<CbPlanDwDO> batchQueryByPlanId(List<String> var1);
}

