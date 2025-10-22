/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class NotFoundTaskFlowException
extends JTableException {
    public NotFoundTaskFlowException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public NotFoundTaskFlowException(String[] datas) {
        super(JtableExceptionCodeCost.NOTFOUND_TASKFLOW, datas);
    }
}

