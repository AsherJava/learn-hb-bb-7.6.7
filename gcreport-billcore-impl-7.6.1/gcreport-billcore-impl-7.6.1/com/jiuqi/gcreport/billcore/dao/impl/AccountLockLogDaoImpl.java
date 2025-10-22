/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.vo.AccountLockLogQueryParam
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.billcore.dao.impl;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.dao.AccountLockLogDao;
import com.jiuqi.gcreport.billcore.entity.AccountLockLogEO;
import com.jiuqi.gcreport.billcore.vo.AccountLockLogQueryParam;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AccountLockLogDaoImpl
extends GcDbSqlGenericDAO<AccountLockLogEO, String>
implements AccountLockLogDao {
    public AccountLockLogDaoImpl() {
        super(AccountLockLogEO.class);
    }

    @Override
    public PageInfo<AccountLockLogEO> listAccountLockLogs(AccountLockLogQueryParam queryParam) {
        ArrayList<String> params = new ArrayList<String>();
        String sql = "select %1$s \n     from GC_ACCOUNTLOCKLOG t \n    where 1=1 \n";
        if (!StringUtils.isEmpty((String)queryParam.getAccountType())) {
            sql = sql + " and t.accountType=?";
            params.add(queryParam.getAccountType());
        }
        sql = sql + " order by t.modifiedTime desc \n";
        int count = this.count(sql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_ACCOUNTLOCKLOG", (String)"t")), params);
        if (count == 0) {
            return PageInfo.empty();
        }
        int pageNum = queryParam.getPageNum();
        int pageSize = queryParam.getPageSize();
        List accountLockLogEOS = this.selectEntityByPaging(sql, (pageNum - 1) * pageSize, pageNum * pageSize, params);
        return PageInfo.of((List)accountLockLogEOS, (int)count);
    }
}

