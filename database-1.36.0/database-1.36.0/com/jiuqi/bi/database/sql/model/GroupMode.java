/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.IGroupConvertor;
import com.jiuqi.bi.database.sql.model.SQLModelException;

public enum GroupMode {
    NONE,
    GROUP,
    MAX,
    MIN,
    COUNT,
    COUNT_DISTINCT,
    SUM,
    AVG,
    ACC;

    public static final IGroupConvertor DEFAULT_CONVERTOR;
    private static volatile IGroupConvertor globalConvertor;
    private static final ThreadLocal<IGroupConvertor> curConvertor;

    public static void setGlobalConvertor(IGroupConvertor convertor) {
        if (convertor == null) {
            throw new IllegalArgumentException("null");
        }
        globalConvertor = convertor;
    }

    public static void setCurrentConvertor(IGroupConvertor convertor) {
        curConvertor.set(convertor);
    }

    public static void toSQL(StringBuilder buffer, GroupMode mode, IDatabase database, String expression) throws SQLModelException {
        GroupMode.convertor().toSQL(buffer, mode, database, expression);
    }

    private static IGroupConvertor convertor() {
        IGroupConvertor convertor = curConvertor.get();
        return convertor == null ? globalConvertor : convertor;
    }

    static {
        globalConvertor = DEFAULT_CONVERTOR = new IGroupConvertor(){

            @Override
            public void toSQL(StringBuilder buffer, GroupMode mode, IDatabase database, String expression) throws SQLModelException {
                if (mode == null || mode == NONE) {
                    buffer.append(expression);
                    return;
                }
                switch (mode) {
                    case SUM: 
                    case MAX: 
                    case MIN: 
                    case COUNT: 
                    case AVG: {
                        buffer.append((Object)mode).append('(').append(expression).append(')');
                        break;
                    }
                    case COUNT_DISTINCT: {
                        buffer.append("COUNT(DISTINCT ").append(expression).append(')');
                        break;
                    }
                    case GROUP: {
                        buffer.append(expression);
                        break;
                    }
                    default: {
                        throw new SQLModelException("\u672a\u652f\u6301\u7684\u805a\u5408\u65b9\u5f0f\uff1a" + (Object)((Object)mode));
                    }
                }
            }
        };
        curConvertor = new ThreadLocal();
    }
}

