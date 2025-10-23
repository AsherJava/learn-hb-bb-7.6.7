/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.plan.dao;

import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanDO;
import java.util.List;

public interface CbPlanDAO {
    public int insertCbPlan(CbPlanDO var1);

    public int updateCbPlanById(CbPlanDO var1);

    public CbPlanDO queryCbPlanById(String var1);

    public List<CbPlanDO> queryAllCbPlans();

    public List<CbPlanDO> queryCbPlans(int var1, int var2);

    public int deleteCbPlanById(String var1);

    public int countPlan();
}

