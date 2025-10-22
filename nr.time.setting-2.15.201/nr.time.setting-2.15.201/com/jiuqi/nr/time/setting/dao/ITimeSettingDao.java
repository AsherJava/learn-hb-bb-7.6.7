/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.time.setting.dao;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.time.setting.bean.TimeSettingDao;
import com.jiuqi.nr.time.setting.bean.TimeSettingInfo;
import java.util.List;

public interface ITimeSettingDao {
    public void saveDeadlineInfo(TimeSettingDao var1);

    public void saveDeadlineInfo(List<TimeSettingDao> var1);

    public void updateDeadlineInfo(List<TimeSettingDao> var1);

    public List<TimeSettingInfo> queryTableData(String var1, String var2);

    public List<TimeSettingInfo> queryTableData(String var1, DimensionValueSet var2);

    public TimeSettingInfo queryDeadTime(String var1, DimensionValueSet var2, String var3);
}

