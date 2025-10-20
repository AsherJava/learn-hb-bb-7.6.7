/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.rate.client.api.CommonRateClient
 *  com.jiuqi.common.rate.client.dto.RateTypeVO
 *  com.jiuqi.common.rate.client.enums.ApplyRangeEnum
 *  com.jiuqi.common.rate.client.vo.CommonRateInfoVO
 *  com.jiuqi.common.rate.client.vo.RateQueryParam
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.rate.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.rate.client.api.CommonRateClient;
import com.jiuqi.common.rate.client.dto.RateTypeVO;
import com.jiuqi.common.rate.client.enums.ApplyRangeEnum;
import com.jiuqi.common.rate.client.vo.CommonRateInfoVO;
import com.jiuqi.common.rate.client.vo.RateQueryParam;
import com.jiuqi.gcreport.rate.impl.service.CommonRateService;
import com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonRateController
implements CommonRateClient {
    @Autowired
    private CommonRateService commonRateService;

    public BusinessResponseEntity<PageInfo<CommonRateInfoVO>> queryRateList(RateQueryParam rateQueryParam) {
        return BusinessResponseEntity.ok(this.commonRateService.queryRateList(rateQueryParam));
    }

    public BusinessResponseEntity<CommonRateInfoVO> queryRateInfo(String rateSchemeCode, String dataTime, String sourceCurrencyCode, String targetCurrencyCode) {
        return BusinessResponseEntity.ok((Object)this.commonRateService.queryRateInfo(rateSchemeCode, dataTime, sourceCurrencyCode, targetCurrencyCode));
    }

    public BusinessResponseEntity<Boolean> saveRates(List<CommonRateInfoVO> rateItemVoList) {
        this.commonRateService.saveRates(rateItemVoList);
        return BusinessResponseEntity.ok((Object)Boolean.TRUE);
    }

    public BusinessResponseEntity<Boolean> deleteRateInfo(List<CommonRateInfoVO> rateItemVoList) {
        return BusinessResponseEntity.ok((Object)this.commonRateService.deleteRateInfo(rateItemVoList));
    }

    public BusinessResponseEntity<List<BaseDataDO>> getNeedSetValueRateType() {
        return BusinessResponseEntity.ok(this.commonRateService.getNeedSetValueRateType());
    }

    public BusinessResponseEntity<List<RateTypeVO>> getRateType(ApplyRangeEnum type) {
        return BusinessResponseEntity.ok(CommonRateUtils.getShowRateType(type));
    }
}

