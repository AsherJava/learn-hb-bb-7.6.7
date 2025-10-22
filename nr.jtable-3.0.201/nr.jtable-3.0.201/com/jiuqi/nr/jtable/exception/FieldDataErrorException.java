/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class FieldDataErrorException
extends JTableException {
    public FieldDataErrorException(String[] datas) {
        super(JtableExceptionCodeCost.FIELD_DATA_ERROR, datas);
    }

    public FieldDataErrorException(String errCode, String[] datas) {
        super(errCode, datas);
    }
}

