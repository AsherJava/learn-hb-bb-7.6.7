/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 */
package com.jiuqi.gcreport.rate.impl.dao;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.rate.impl.entity.CommonRateInfoEO;
import java.util.List;

public interface CommonRateDao {
    public PageInfo<CommonRateInfoEO> queryRateList(String var1, String var2, String var3, String var4, String var5, Integer var6, Integer var7);

    public CommonRateInfoEO queryRateInfo(String var1, String var2, String var3, String var4);

    public Boolean saveRates(String var1, List<CommonRateInfoEO> var2);

    public Boolean deleteRateInfos(List<String> var1);

    public void deleteRateInfoBySchemeCode(String var1);
}

