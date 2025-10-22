/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 */
package com.jiuqi.nr.time.setting.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.time.setting.bean.SelectData;
import com.jiuqi.nr.time.setting.bean.SelectDataResult;
import com.jiuqi.nr.time.setting.bean.TimeSettingInfo;
import com.jiuqi.nr.time.setting.bean.TimeSettingResult;
import java.util.List;

public interface ITimeSettingService {
    public String createDeadlineInfo(TimeSettingInfo var1);

    public void saveUnitTime(TimeSettingInfo var1);

    public List<TimeSettingResult> queryTableData(String var1, String var2);

    public List<EntityViewDefine> queryAdminEntitieList(String var1);

    public List<TimeSettingResult> queryUnitOfUserAuth(String var1, String var2, String var3, String var4);

    public TimeSettingInfo getDeadlineInfoOfUnit(String var1, String var2, String var3);

    public TimeSettingInfo getSetTimeInfo(String var1, String var2, String var3, String var4);

    public List<TimeSettingInfo> queryTableData(String var1, String var2, String var3);

    public List<TimeSettingInfo> queryTableData(String var1, DimensionValueSet var2);

    public boolean canOperatorEntity(String var1, String var2, String var3);

    public TimeSettingInfo queryParent(String var1, String var2, String var3);

    public SelectDataResult distinguishData(SelectData var1);
}

