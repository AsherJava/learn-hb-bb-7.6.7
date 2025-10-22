/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.fml.account;

import com.jiuqi.np.dataengine.fml.account.AccountQueryFieldInfo;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import java.util.ArrayList;
import java.util.List;

public class AccountQueryModel {
    private IDataQuery dataQuery;
    private List<AccountQueryFieldInfo> fieldInfos = new ArrayList<AccountQueryFieldInfo>();
    private int queryIndex;

    public AccountQueryModel(IDataQuery dataQuery, int queryIndex) {
        this.dataQuery = dataQuery;
        this.queryIndex = queryIndex;
    }

    public IDataQuery getDataQuery() {
        return this.dataQuery;
    }

    public List<AccountQueryFieldInfo> getFieldInfos() {
        return this.fieldInfos;
    }

    public int getQueryIndex() {
        return this.queryIndex;
    }
}

