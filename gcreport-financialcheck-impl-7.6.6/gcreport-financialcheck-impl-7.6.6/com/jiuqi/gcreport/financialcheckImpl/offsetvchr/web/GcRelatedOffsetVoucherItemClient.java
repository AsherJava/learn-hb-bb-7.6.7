/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam
 *  com.jiuqi.gcreport.financialcheckapi.offsetvoucher.vo.GcRelatedOffsetVoucherInfoVO
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition
 *  com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.financialcheckImpl.offsetvchr.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam;
import com.jiuqi.gcreport.financialcheckapi.offsetvoucher.vo.GcRelatedOffsetVoucherInfoVO;
import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO;
import java.util.List;
import java.util.Set;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.financialcheckImpl.offsetvchr.web.GcRelatedOffsetVoucherItemClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface GcRelatedOffsetVoucherItemClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/relatedoffsetvchr";

    @GetMapping(value={"/api/gcreport/v1/relatedoffsetvchr/queryByCheckId/{checkId}"})
    public BusinessResponseEntity<List<GcRelatedOffsetVoucherInfoVO>> queryByCheckId(@PathVariable(name="checkId") String var1);

    @PostMapping(value={"/api/gcreport/v1/relatedoffsetvchr/queryManualOffsetResult"})
    public BusinessResponseEntity<List<GcRelatedOffsetVoucherInfoVO>> queryManualOffsetResult(@RequestBody ManualCheckParam var1);

    @PostMapping(value={"/api/gcreport/v1/relatedoffsetvchr/queryByClbrCode/{clbrCode}"})
    public BusinessResponseEntity<List<GcRelatedOffsetVoucherInfoVO>> queryByClbrCode(@PathVariable(name="clbrCode") String var1);

    @PostMapping(value={"/api/gcreport/v1/relatedoffsetvchr/saveRelatedOffsetVchrInfo"})
    public BusinessResponseEntity<String> saveRelatedOffsetVchrInfo(@RequestBody List<GcRelatedOffsetVoucherInfoVO> var1);

    @PostMapping(value={"/api/gcreport/v1/relatedoffsetvchr/queryByOffsetCondition"})
    public BusinessResponseEntity<List<GcRelatedOffsetVoucherItemEO>> queryByOffsetCondition(@RequestBody BalanceCondition var1);

    @PostMapping(value={"/api/gcreport/v1/relatedoffsetvchr/queryByIds"})
    public BusinessResponseEntity<List<GcRelatedOffsetVoucherItemEO>> queryByIds(@RequestBody Set<String> var1);
}

