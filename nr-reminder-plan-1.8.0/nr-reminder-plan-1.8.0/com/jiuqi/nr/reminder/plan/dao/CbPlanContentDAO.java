/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.plan.dao;

import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanContentDO;
import java.util.List;

public interface CbPlanContentDAO {
    public int insert(CbPlanContentDO var1);

    public int updateById(CbPlanContentDO var1);

    public List<CbPlanContentDO> queryAll();

    public void delete(String var1);
}

