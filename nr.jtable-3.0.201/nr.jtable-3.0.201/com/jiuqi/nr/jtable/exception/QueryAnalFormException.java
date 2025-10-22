/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class QueryAnalFormException
extends JTableException {
    public QueryAnalFormException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public QueryAnalFormException(String[] datas) {
        super(JtableExceptionCodeCost.ANALY_FORM_QUERY_EXCEPTION, datas);
    }
}

