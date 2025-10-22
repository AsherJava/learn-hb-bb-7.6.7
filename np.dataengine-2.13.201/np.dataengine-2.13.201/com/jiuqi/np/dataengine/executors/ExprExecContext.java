/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.common.NameValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;

public class ExprExecContext {
    public QueryContext GlobalContext;
    public Object LocalContext;
    public NameValueSet CurrentDimValues;

    public ExprExecContext(QueryContext context) {
        this.GlobalContext = context;
    }
}

