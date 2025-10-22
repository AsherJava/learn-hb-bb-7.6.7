/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class AutoSumSaveException
extends JTableException {
    public AutoSumSaveException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public AutoSumSaveException(String[] datas) {
        super(JtableExceptionCodeCost.DATAENGINE_AUTOSUM_SAVE_EXCEPTION, datas);
    }
}

