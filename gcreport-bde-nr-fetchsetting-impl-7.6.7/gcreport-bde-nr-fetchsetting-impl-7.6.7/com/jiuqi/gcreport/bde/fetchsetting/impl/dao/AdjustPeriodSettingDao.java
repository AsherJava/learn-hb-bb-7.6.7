/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dao;

import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.AdjustPeriodSettingEO;
import java.util.List;

public interface AdjustPeriodSettingDao {
    public List<AdjustPeriodSettingEO> loadAll();

    public List<AdjustPeriodSettingEO> listAdjustPeriodSettingByFetchSchemeId(String var1);

    public void deleteByFetchSchemeId(String var1);

    public void update(AdjustPeriodSettingEO var1);

    public void addBatch(List<AdjustPeriodSettingEO> var1);
}

