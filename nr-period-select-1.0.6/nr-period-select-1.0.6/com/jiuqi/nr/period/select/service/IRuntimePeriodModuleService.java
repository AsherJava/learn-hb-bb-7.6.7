/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.common.rest.PeriodDataSelectObject
 */
package com.jiuqi.nr.period.select.service;

import com.jiuqi.nr.period.common.rest.PeriodDataSelectObject;
import com.jiuqi.nr.period.select.common.RunType;
import com.jiuqi.nr.period.select.page.Page;
import com.jiuqi.nr.period.select.vo.AdjustData;
import com.jiuqi.nr.period.select.vo.CompreData;
import com.jiuqi.nr.period.select.vo.ModuleObj;
import com.jiuqi.nr.period.select.vo.PageData;
import com.jiuqi.nr.period.select.vo.PeriodData;
import com.jiuqi.nr.period.select.vo.RangeData;
import com.jiuqi.nr.period.select.vo.SelectData;
import com.jiuqi.nr.period.select.web.param.LoadPageData;
import com.jiuqi.nr.period.select.web.param.LoadYearData;
import com.jiuqi.nr.period.select.web.param.LoadYearsData;
import com.jiuqi.nr.period.select.web.param.ModeSelectData;
import java.util.List;

public interface IRuntimePeriodModuleService {
    public String queryPeriodType(ModuleObj var1);

    public List<PeriodDataSelectObject> queryPeriodData(String var1, boolean var2);

    public boolean queryAdjustStatus(ModuleObj var1);

    public List<AdjustData> queryAdjustData(ModuleObj var1);

    public CompreData queryData(ModuleObj var1);

    public String queryOffsetPeriod(String var1, RunType var2);

    public List<PeriodData> queryPeriodDataByPage(ModuleObj var1);

    public PageData initData(ModeSelectData var1);

    public Page loadDataByYear(LoadYearData var1);

    public List<Page> loadDataByOtherPage(LoadPageData var1);

    public SelectData getSelectData(ModeSelectData var1);

    public List<Page> loadDataByYears(LoadYearsData var1);

    public RangeData loadDataByRange(ModeSelectData var1);
}

