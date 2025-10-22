/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class QueryCheckResultException
extends JTableException {
    public QueryCheckResultException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public QueryCheckResultException(String[] datas) {
        super(JtableExceptionCodeCost.QUERY_CHECK_RESULT, datas);
    }
}

