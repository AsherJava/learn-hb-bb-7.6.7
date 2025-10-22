/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class NotFoundDataLinkException
extends JTableException {
    public NotFoundDataLinkException(String[] datas) {
        super(JtableExceptionCodeCost.NOTFOUND_DATAINK, datas);
    }

    public NotFoundDataLinkException(String errCode, String[] datas) {
        super(errCode, datas);
    }
}

