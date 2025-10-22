/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.billcore.vo.AccountLockLogQueryParam
 *  com.jiuqi.gcreport.billcore.vo.AccountLockLogVO
 *  com.jiuqi.gcreport.billcore.vo.AccountLockVO
 */
package com.jiuqi.gcreport.billcore.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.billcore.vo.AccountLockLogQueryParam;
import com.jiuqi.gcreport.billcore.vo.AccountLockLogVO;
import com.jiuqi.gcreport.billcore.vo.AccountLockVO;
import java.util.List;

public interface AccountLockService {
    public List<AccountLockVO> listAccountLocks(int var1);

    public void updateLock(List<String> var1, boolean var2, int var3);

    public PageInfo<AccountLockLogVO> listAccountLockLogs(AccountLockLogQueryParam var1);

    public String getLockStatus(String var1, Integer var2);
}

