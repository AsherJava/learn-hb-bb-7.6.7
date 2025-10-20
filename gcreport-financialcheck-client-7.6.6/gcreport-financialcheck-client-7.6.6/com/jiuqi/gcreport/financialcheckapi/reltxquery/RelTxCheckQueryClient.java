/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 */
package com.jiuqi.gcreport.financialcheckapi.reltxquery;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryDataVO;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4DataV0;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryLevel4Param;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryParamVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(contextId="com.jiuqi.gcreport.api.financialcheckapi.RelTxCheckQueryClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface RelTxCheckQueryClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/relTxCheckQuery";

    @PostMapping(value={"/api/gcreport/v1/relTxCheckQuery/queryData"})
    public BusinessResponseEntity<RelTxCheckQueryDataVO> queryRelTxCheckData(RelTxCheckQueryParamVO var1);

    @PostMapping(value={"/api/gcreport/v1/relTxCheckQuery/queryLevel4Data"})
    public BusinessResponseEntity<RelTxCheckQueryLevel4DataV0> queryLevel4Data(RelTxCheckQueryLevel4Param var1);
}

