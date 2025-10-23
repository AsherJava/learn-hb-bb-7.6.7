/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.plan.dao;

import com.jiuqi.nr.reminder.plan.dao.impl.CbExecLogDO;
import java.util.List;

public interface CbExecLogDAO {
    public int insert(CbExecLogDO var1);

    public void update(CbExecLogDO var1);

    public void deleteByPlanId(String var1);

    public CbExecLogDO queryLatestLogByPlanId(String var1);

    public List<CbExecLogDO> queryLogByPlanId(String var1);

    public int countByPlanId(String var1);
}

