/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.plan.dao;

import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanFormDO;
import java.util.List;

public interface CbPlanFDAO {
    public int insert(CbPlanFormDO var1);

    public void batchInsert(List<CbPlanFormDO> var1);

    public void update(CbPlanFormDO var1);

    public void deleteByPlanId(String var1);

    public List<CbPlanFormDO> queryByPlanId(String var1);
}

