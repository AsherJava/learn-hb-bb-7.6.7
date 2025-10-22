/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  javax.validation.Valid
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.financialcheckapi.dataentry;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputConditionVO;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputVO;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.financialcheckapi.dataentry.GcFinancialCheckDataEntryClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface GcFinancialCheckDataEntryClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/datainput";

    @PostMapping(value={"/api/gcreport/v1/datainput/query"})
    public BusinessResponseEntity<DataInputVO> query(@RequestBody DataInputConditionVO var1);

    @PostMapping(value={"/api/gcreport/v1/datainput/batchDelete"})
    public BusinessResponseEntity<String> deleteRelatedItem(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/datainput/saveVoucherItems"})
    public BusinessResponseEntity<String> saveRelatedItems(@RequestBody @Valid List<GcRelatedItemVO> var1);

    @PostMapping(value={"/api/gcreport/v1/datainput/listByIds"})
    public BusinessResponseEntity<List<GcRelatedItemVO>> listByIds(@RequestBody List<String> var1);
}

