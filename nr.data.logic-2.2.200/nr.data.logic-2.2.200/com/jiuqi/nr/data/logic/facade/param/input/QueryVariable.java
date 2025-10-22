/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.data.logic.facade.param.input.QueryContext;

public interface QueryVariable {
    public String toSql(QueryContext var1);

    public String toFml(QueryContext var1);
}

