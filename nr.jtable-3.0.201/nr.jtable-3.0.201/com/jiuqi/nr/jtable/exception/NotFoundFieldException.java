/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class NotFoundFieldException
extends JTableException {
    public NotFoundFieldException(String[] datas) {
        super(JtableExceptionCodeCost.NOTFOUND_FIELD, datas);
    }

    public NotFoundFieldException(String errCode, String[] datas) {
        super(errCode, datas);
    }
}

