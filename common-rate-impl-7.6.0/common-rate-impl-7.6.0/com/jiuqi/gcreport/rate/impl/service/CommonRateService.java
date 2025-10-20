/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.rate.client.vo.CommonRateInfoVO
 *  com.jiuqi.common.rate.client.vo.RateQueryParam
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 */
package com.jiuqi.gcreport.rate.impl.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.rate.client.vo.CommonRateInfoVO;
import com.jiuqi.common.rate.client.vo.RateQueryParam;
import com.jiuqi.gcreport.rate.impl.dto.RateBatchData;
import com.jiuqi.gcreport.rate.impl.dto.RateBatchParam;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import java.util.Collection;
import java.util.List;

public interface CommonRateService {
    public PageInfo<CommonRateInfoVO> queryRateList(RateQueryParam var1);

    public CommonRateInfoVO queryRateInfo(String var1, String var2, String var3, String var4);

    public List<CommonRateInfoVO> queryRateByYear(String var1, int var2, String var3, String var4);

    public Collection<RateBatchData> queryRateBySubject(RateBatchParam var1);

    public List<CommonRateInfoVO> queryRateByDataTime(String var1, String var2, String var3, String var4);

    public Boolean saveRates(List<CommonRateInfoVO> var1);

    public Boolean deleteRateInfo(List<CommonRateInfoVO> var1);

    public List<BaseDataDO> getNeedSetValueRateType();
}

