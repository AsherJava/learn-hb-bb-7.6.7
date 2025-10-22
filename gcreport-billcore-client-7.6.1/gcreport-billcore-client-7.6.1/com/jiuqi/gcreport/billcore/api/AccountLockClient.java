/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.billcore.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.billcore.vo.AccountLockLogQueryParam;
import com.jiuqi.gcreport.billcore.vo.AccountLockLogVO;
import com.jiuqi.gcreport.billcore.vo.AccountLockVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.common.api.AccountLockClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface AccountLockClient {
    public static final String API_PATH = "/api/gcreport/v1/accountLock/";

    @GetMapping(value={"/api/gcreport/v1/accountLock/getAccountLockList"})
    public BusinessResponseEntity<List<AccountLockVO>> listAccountLocks(int var1);

    @PostMapping(value={"/api/gcreport/v1/accountLock/excuteLock"})
    public BusinessResponseEntity<String> updateLock(@RequestBody List<String> var1, @RequestParam(value="lockFlag") boolean var2, int var3);

    @PostMapping(value={"/api/gcreport/v1/accountLock/getAccountLockLogList"})
    public BusinessResponseEntity<PageInfo<AccountLockLogVO>> listAccountLockLogs(@RequestBody AccountLockLogQueryParam var1);

    @GetMapping(value={"/api/gcreport/v1/accountLock/getLockStatus/{accountType}"})
    public BusinessResponseEntity<String> getLockStatus(@PathVariable String var1, @RequestParam(required=false) Integer var2);
}

