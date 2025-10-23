/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.service;

import com.jiuqi.nr.period.select.vo.AdjustData;
import com.jiuqi.nr.period.select.vo.CompreData;
import com.jiuqi.nr.period.select.vo.ModuleObj;
import com.jiuqi.nr.period.select.vo.PeriodData;
import java.util.List;

public interface IDesignPeriodModuleService {
    public String queryPeriodType(ModuleObj var1);

    public boolean queryAdjustStatus(ModuleObj var1);

    public List<AdjustData> queryAdjustData(ModuleObj var1);

    public CompreData queryData(ModuleObj var1);

    public List<PeriodData> queryPeriodDataByPage(ModuleObj var1);
}

