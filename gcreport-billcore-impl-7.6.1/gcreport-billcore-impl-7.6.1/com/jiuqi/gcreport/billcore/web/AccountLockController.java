/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.billcore.api.AccountLockClient
 *  com.jiuqi.gcreport.billcore.vo.AccountLockLogQueryParam
 *  com.jiuqi.gcreport.billcore.vo.AccountLockLogVO
 *  com.jiuqi.gcreport.billcore.vo.AccountLockVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.billcore.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.billcore.api.AccountLockClient;
import com.jiuqi.gcreport.billcore.service.AccountLockService;
import com.jiuqi.gcreport.billcore.vo.AccountLockLogQueryParam;
import com.jiuqi.gcreport.billcore.vo.AccountLockLogVO;
import com.jiuqi.gcreport.billcore.vo.AccountLockVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountLockController
implements AccountLockClient {
    @Autowired
    private AccountLockService accountLockService;

    public BusinessResponseEntity<List<AccountLockVO>> listAccountLocks(int acctYear) {
        return BusinessResponseEntity.ok(this.accountLockService.listAccountLocks(acctYear));
    }

    public BusinessResponseEntity<String> updateLock(List<String> lockAccountTypeList, boolean lockFlag, int acctYear) {
        this.accountLockService.updateLock(lockAccountTypeList, lockFlag, acctYear);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<PageInfo<AccountLockLogVO>> listAccountLockLogs(AccountLockLogQueryParam queryParam) {
        return BusinessResponseEntity.ok(this.accountLockService.listAccountLockLogs(queryParam));
    }

    public BusinessResponseEntity<String> getLockStatus(String accountType, Integer acctYear) {
        return BusinessResponseEntity.ok((Object)this.accountLockService.getLockStatus(accountType, acctYear));
    }
}

