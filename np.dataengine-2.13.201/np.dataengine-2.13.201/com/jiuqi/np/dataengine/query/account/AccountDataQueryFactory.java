/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.query.account;

import com.jiuqi.np.dataengine.IDataQueryFactory;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.query.account.AccountDataQueryImpl;

public class AccountDataQueryFactory
implements IDataQueryFactory {
    @Override
    public IDataQuery getDataQuery(QueryEnvironment queryEnvironment) {
        return new AccountDataQueryImpl();
    }

    @Override
    public IGroupingQuery getGroupingQuery(QueryEnvironment queryEnvironment) {
        return null;
    }
}

