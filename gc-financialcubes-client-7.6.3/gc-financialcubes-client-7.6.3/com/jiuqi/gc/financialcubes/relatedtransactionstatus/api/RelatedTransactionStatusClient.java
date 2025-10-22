/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusParam
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialUnitStatusVO
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gc.financialcubes.relatedtransactionstatus.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusParam;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialUnitStatusVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gc.financialcubes.relatedtransactionstatus.api.RelatedTransactionStatusClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface RelatedTransactionStatusClient {
    public static final String RELATED_TRANSACTION_STATUS_API_BASE_PATH = "/api/gcreport/v1/relatedTransactionsStatus";

    @PostMapping(value={"/api/gcreport/v1/relatedTransactionsStatus/openUnit"})
    public BusinessResponseEntity<String> openUnit(@RequestBody FinancialStatusParam var1);

    @PostMapping(value={"/api/gcreport/v1/relatedTransactionsStatus/closeUnit"})
    public BusinessResponseEntity<String> closeUnit(@RequestBody FinancialStatusParam var1);

    @PostMapping(value={"/api/gcreport/v1/relatedTransactionsStatus/listCloseUnit"})
    public BusinessResponseEntity<PageInfo<FinancialUnitStatusVO>> listCloseUnit(@RequestBody FinancialStatusParam var1);

    @PostMapping(value={"/api/gcreport/v1/relatedTransactionsStatus/listOpenUnit"})
    public BusinessResponseEntity<PageInfo<FinancialUnitStatusVO>> listOpenUnit(@RequestBody FinancialStatusParam var1);
}

