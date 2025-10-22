/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.data.logic.facade.param.input.CheckResultObj;
import com.jiuqi.nr.data.logic.facade.param.input.QueryConSql;
import com.jiuqi.nr.data.logic.facade.param.input.QueryContext;

public interface QueryCondition {
    public QueryConSql buildSql(QueryContext var1);

    public String buildFml(QueryContext var1);

    public boolean filter(CheckResultObj var1);
}

