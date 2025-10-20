/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.common.rate.client.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.rate.client.dto.RateTypeVO;
import com.jiuqi.common.rate.client.enums.ApplyRangeEnum;
import com.jiuqi.common.rate.client.vo.CommonRateInfoVO;
import com.jiuqi.common.rate.client.vo.RateQueryParam;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface CommonRateClient {
    public static final String COMMON_RATE_DATA_API = "/api/gcreport/v1/commonRate/rateInfo";

    @PostMapping(value={"/api/gcreport/v1/commonRate/rateInfo/queryRate"})
    public BusinessResponseEntity<PageInfo<CommonRateInfoVO>> queryRateList(@RequestBody RateQueryParam var1);

    @GetMapping(value={"/api/gcreport/v1/commonRate/rateInfo/queryRateInfo"})
    public BusinessResponseEntity<CommonRateInfoVO> queryRateInfo(@RequestParam(value="rateSchemeCode", required=false) String var1, @RequestParam(value="dataTime") String var2, @RequestParam(value="sourceCurrencyCode") String var3, @RequestParam(value="targetCurrencyCode") String var4);

    @PostMapping(value={"/api/gcreport/v1/commonRate/rateInfo/saveRates"})
    public BusinessResponseEntity<Boolean> saveRates(@RequestBody List<CommonRateInfoVO> var1);

    @PostMapping(value={"/api/gcreport/v1/commonRate/rateInfo/deleteRate"})
    public BusinessResponseEntity<Boolean> deleteRateInfo(@RequestBody List<CommonRateInfoVO> var1);

    @GetMapping(value={"/api/gcreport/v1/commonRate/rateInfo/getNeedSetValueRateType"})
    public BusinessResponseEntity<List<BaseDataDO>> getNeedSetValueRateType();

    @GetMapping(value={"/api/gcreport/v1/commonRate/rateInfo/getRateType/{type}"})
    public BusinessResponseEntity<List<RateTypeVO>> getRateType(@PathVariable(value="type") ApplyRangeEnum var1);
}

