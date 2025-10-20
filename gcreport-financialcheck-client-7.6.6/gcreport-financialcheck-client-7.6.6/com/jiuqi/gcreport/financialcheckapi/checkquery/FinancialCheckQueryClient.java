/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  javax.validation.Valid
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.financialcheckapi.checkquery;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryColumnVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryDataVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryInitDataVO;
import com.jiuqi.gcreport.financialcheckapi.checkquery.vo.FinancialCheckQueryVO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.api.financialcheck.FinancialCheckQueryClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface FinancialCheckQueryClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/query";

    @PostMapping(value={"/api/gcreport/v1/query/initData"})
    public BusinessResponseEntity<FinancialCheckQueryInitDataVO> initData();

    @PostMapping(value={"/api/gcreport/v1/query/queryDefaultNode"})
    public BusinessResponseEntity<FinancialCheckQueryInitDataVO> queryDefaultNode(@RequestBody FinancialCheckQueryVO var1);

    @PostMapping(value={"/api/gcreport/v1/query/queryColumns"})
    public BusinessResponseEntity<List<FinancialCheckQueryColumnVO>> queryColumns(@RequestBody @Valid FinancialCheckQueryVO var1);

    @PostMapping(value={"/api/gcreport/v1/query/queryData"})
    public BusinessResponseEntity<PageInfo<FinancialCheckQueryDataVO>> queryData(@RequestBody @Valid FinancialCheckQueryVO var1);
}

