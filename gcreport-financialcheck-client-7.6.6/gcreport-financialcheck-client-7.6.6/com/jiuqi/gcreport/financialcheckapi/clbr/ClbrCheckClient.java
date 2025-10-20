/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 */
package com.jiuqi.gcreport.financialcheckapi.clbr;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckapi.clbr.vo.ClbrVoucherItemVO;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(contextId="com.jiuqi.gcreport.api.financialcheck.ClbrCheckClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface ClbrCheckClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/clbrCheck";

    @PostMapping(value={"/api/gcreport/v1/clbrCheck/updateVoucherGcNumber"})
    public BusinessResponseEntity updateVoucherGcNumber(List<ClbrVoucherItemVO> var1);
}

