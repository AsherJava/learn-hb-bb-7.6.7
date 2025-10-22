/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class PeriodFormatException
extends JTableException {
    public PeriodFormatException(String[] datas) {
        super(JtableExceptionCodeCost.EXCEPTION_PERIODFORMAT, datas);
    }
}

