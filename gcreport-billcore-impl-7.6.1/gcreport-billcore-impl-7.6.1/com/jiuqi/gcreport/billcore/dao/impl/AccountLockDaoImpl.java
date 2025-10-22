/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.billcore.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.billcore.dao.AccountLockDao;
import com.jiuqi.gcreport.billcore.entity.AccountLockEO;
import com.jiuqi.gcreport.billcore.enums.AccountLockInfoEnum;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AccountLockDaoImpl
extends GcDbSqlGenericDAO<AccountLockEO, String>
implements AccountLockDao {
    public AccountLockDaoImpl() {
        super(AccountLockEO.class);
    }

    @Override
    public List<AccountLockEO> listAccountLocks(int acctYear) {
        String sql = "select %1$s \n     from GC_ACCOUNTLOCK t \n    where 1=1  and accountType in ('asset', 'lease') or (accountType='invest' and acctYear=?)  \n    order by t.MODIFIEDTIME desc \n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_ACCOUNTLOCK", (String)"t"));
        List accountLockEOs = this.selectEntity(sql, new Object[]{acctYear});
        return accountLockEOs;
    }

    @Override
    public void updateLock(List<String> lockAccountTypeList, boolean lockFlag) {
        if (CollectionUtils.isEmpty(lockAccountTypeList)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(lockAccountTypeList, (String)"ACCOUNTTYPE");
        String lockStatus = lockFlag ? "1" : "";
        String sql = "update GC_ACCOUNTLOCK set status=?, MODIFIEDUSER=?, modifiedTime=?  where " + inSql;
        this.execute(sql, new Object[]{lockStatus, NpContextHolder.getContext().getUserName(), new Date()});
    }

    @Override
    public String getLockStatus(String accountType, Integer acctYear) {
        String sql = "select status from GC_ACCOUNTLOCK t \n where t.accountType=? \n";
        if (accountType.equals(AccountLockInfoEnum.INVEST.getCode())) {
            sql = sql + "and t.acctYear=?";
            return (String)this.selectFirst(String.class, sql, new Object[]{accountType, acctYear});
        }
        return (String)this.selectFirst(String.class, sql, new Object[]{accountType});
    }

    @Override
    public void deleteByAccountType(String accountType, int acctYear) {
        ArrayList<Object> params = new ArrayList<Object>();
        String sql = "delete from GC_ACCOUNTLOCK t \n where t.accountType=? \n";
        params.add(accountType);
        if (accountType.equals(AccountLockInfoEnum.INVEST.getCode())) {
            sql = sql + "and acctYear=?";
            params.add(acctYear);
        }
        this.execute(sql, params);
    }
}

