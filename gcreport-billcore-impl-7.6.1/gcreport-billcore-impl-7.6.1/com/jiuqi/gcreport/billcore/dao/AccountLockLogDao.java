/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.billcore.vo.AccountLockLogQueryParam
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.billcore.dao;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.billcore.entity.AccountLockLogEO;
import com.jiuqi.gcreport.billcore.vo.AccountLockLogQueryParam;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;

public interface AccountLockLogDao
extends IDbSqlGenericDAO<AccountLockLogEO, String> {
    public PageInfo<AccountLockLogEO> listAccountLockLogs(AccountLockLogQueryParam var1);
}

