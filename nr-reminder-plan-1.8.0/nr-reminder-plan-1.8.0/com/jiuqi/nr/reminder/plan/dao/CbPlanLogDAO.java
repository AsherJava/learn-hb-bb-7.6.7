/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.plan.dao;

import com.jiuqi.nr.reminder.plan.dao.impl.CbPlanLogDO;
import java.util.List;

public interface CbPlanLogDAO {
    public void insert(CbPlanLogDO var1);

    public void batchInsert(List<CbPlanLogDO> var1);

    public void deleteByLogId(String var1);

    public List<CbPlanLogDO> queryLogByLogId(String var1);

    public List<CbPlanLogDO> queryLogByLogId(String var1, int var2, int var3);

    public int count(String var1);
}

