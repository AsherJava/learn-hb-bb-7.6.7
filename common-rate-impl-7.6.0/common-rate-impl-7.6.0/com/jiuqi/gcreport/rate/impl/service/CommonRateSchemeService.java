/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.rate.client.vo.CommonRateSchemeVO
 */
package com.jiuqi.gcreport.rate.impl.service;

import com.jiuqi.common.rate.client.vo.CommonRateSchemeVO;
import java.util.List;

public interface CommonRateSchemeService {
    public List<CommonRateSchemeVO> listAllRateScheme();

    public CommonRateSchemeVO queryRateScheme(String var1);

    public Boolean saveRateScheme(CommonRateSchemeVO var1);

    public Boolean deleteRateScheme(String var1);

    public CommonRateSchemeVO getRateSchemeByTitle(String var1);
}

