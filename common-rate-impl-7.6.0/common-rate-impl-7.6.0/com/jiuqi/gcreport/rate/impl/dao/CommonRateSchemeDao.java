/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.rate.impl.dao;

import com.jiuqi.gcreport.rate.impl.entity.CommonRateSchemeEO;
import java.util.List;

public interface CommonRateSchemeDao {
    public List<CommonRateSchemeEO> listAllRateScheme();

    public Boolean saveRateScheme(CommonRateSchemeEO var1);

    public CommonRateSchemeEO getRateSchemeById(String var1);

    public CommonRateSchemeEO getRateSchemeByCode(String var1);

    public Boolean deleteRateScheme(String var1);

    public CommonRateSchemeEO getRateSchemeByTitle(String var1);
}

