/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class SaveCheckResultException
extends JTableException {
    public SaveCheckResultException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public SaveCheckResultException(String[] datas) {
        super(JtableExceptionCodeCost.UPDATE_CHECKRESULT, datas);
    }
}

