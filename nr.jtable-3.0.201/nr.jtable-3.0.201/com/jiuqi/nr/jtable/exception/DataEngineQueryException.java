/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class DataEngineQueryException
extends JTableException {
    public DataEngineQueryException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public DataEngineQueryException(String[] datas) {
        super(JtableExceptionCodeCost.UPDATE_CHECKRESULT, datas);
    }
}

