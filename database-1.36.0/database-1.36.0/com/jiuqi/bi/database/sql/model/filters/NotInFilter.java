/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.filters;

import com.jiuqi.bi.database.sql.model.filters.InFilter;

public class NotInFilter
extends InFilter {
    @Override
    protected String getOperator() {
        return "NOT IN";
    }
}

