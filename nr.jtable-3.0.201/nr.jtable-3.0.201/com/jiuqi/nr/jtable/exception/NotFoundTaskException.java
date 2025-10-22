/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class NotFoundTaskException
extends JTableException {
    public NotFoundTaskException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public NotFoundTaskException(String[] datas) {
        super(JtableExceptionCodeCost.NOTFOUND_TASK, datas);
    }
}

