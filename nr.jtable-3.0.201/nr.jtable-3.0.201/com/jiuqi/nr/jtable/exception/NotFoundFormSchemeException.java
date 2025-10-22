/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class NotFoundFormSchemeException
extends JTableException {
    public NotFoundFormSchemeException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public NotFoundFormSchemeException(String[] datas) {
        super(JtableExceptionCodeCost.NOTFOUND_FORMSHCEME, datas);
    }
}

