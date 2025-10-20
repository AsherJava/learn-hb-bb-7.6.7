/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.billcore.dao;

import com.jiuqi.gcreport.billcore.entity.AccountLockEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.List;

public interface AccountLockDao
extends IDbSqlGenericDAO<AccountLockEO, String> {
    public List<AccountLockEO> listAccountLocks(int var1);

    public void updateLock(List<String> var1, boolean var2);

    public String getLockStatus(String var1, Integer var2);

    public void deleteByAccountType(String var1, int var2);
}

