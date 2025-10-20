/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model.filters;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLFilter;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.util.StringUtils;

public final class ExpressionFilter
implements ISQLFilter {
    private String expression;

    public ExpressionFilter(String expression) {
        if (StringUtils.isEmpty((String)expression)) {
            throw new IllegalArgumentException("\u8fc7\u6ee4\u8868\u8fbe\u5f0f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        this.expression = expression;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        buffer.append(this.expression);
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        return this.expression;
    }

    public String toString() {
        return this.expression;
    }

    public int hashCode() {
        return this.expression.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ExpressionFilter)) {
            return false;
        }
        return this.expression.equals(((ExpressionFilter)obj).expression);
    }
}

