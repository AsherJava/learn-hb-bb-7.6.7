/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 */
package com.jiuqi.gc.financial.financialstatus.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusManageConfigVo;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusManageVo;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId="com.jiuqi.gc.financialcubes.accountingperiod.api.SwitchAccountingManageClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface FinancialStatusManageClient {
    public static final String ACCOUNTING_MANAGE_API_BASE_PATH = "/api/gcreport/v1/accounting/Manage";

    @GetMapping(value={"/api/gcreport/v1/accounting/Manage/listAllPlugin"})
    public BusinessResponseEntity<List<FinancialStatusManageVo>> listAllPlugin();

    @GetMapping(value={"/api/gcreport/v1/accounting/Manage/Config/listPluginConfig"})
    public BusinessResponseEntity<List<FinancialStatusManageConfigVo>> listPluginConfig();
}

