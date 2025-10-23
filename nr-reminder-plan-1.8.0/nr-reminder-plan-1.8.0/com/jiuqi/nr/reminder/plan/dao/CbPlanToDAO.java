/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.plan.dao;

import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanToDO;
import java.util.List;

public interface CbPlanToDAO {
    public void batchInsert(List<CbPlanToDO> var1);

    public void deleteByPlanId(String var1);

    public List<CbPlanToDO> queryByPlanId(String var1);
}

