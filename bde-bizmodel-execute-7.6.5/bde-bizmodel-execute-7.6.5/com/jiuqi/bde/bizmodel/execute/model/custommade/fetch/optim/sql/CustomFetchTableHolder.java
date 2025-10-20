/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.ContextVariableParseUtil
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.sql;

import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.ContextVariableParseUtil;

public class CustomFetchTableHolder {
    private String fetchTable;

    public CustomFetchTableHolder(String fetchTable) {
        this.fetchTable = fetchTable;
    }

    public String buildSql(FetchTaskContext fetchTaskContext) {
        return ContextVariableParseUtil.parse((String)this.fetchTable, (FetchTaskContext)fetchTaskContext);
    }
}

