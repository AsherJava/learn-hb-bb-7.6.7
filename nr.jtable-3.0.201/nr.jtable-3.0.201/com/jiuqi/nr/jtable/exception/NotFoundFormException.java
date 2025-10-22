/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class NotFoundFormException
extends JTableException {
    public NotFoundFormException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public NotFoundFormException(String[] datas) {
        super(JtableExceptionCodeCost.NOTFOUND_FORM, datas);
    }
}

