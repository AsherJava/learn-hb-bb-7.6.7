/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataQueryFactory
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.query.account.AccountDataQueryImpl
 */
package com.jiuqi.nr.bql.dataengine.query.account;

import com.jiuqi.np.dataengine.IDataQueryFactory;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.query.account.AccountDataQueryImpl;

public class AccountDataQueryFactory
implements IDataQueryFactory {
    public IDataQuery getDataQuery(QueryEnvironment queryEnvironment) {
        return new AccountDataQueryImpl();
    }

    public IGroupingQuery getGroupingQuery(QueryEnvironment queryEnvironment) {
        return null;
    }
}

