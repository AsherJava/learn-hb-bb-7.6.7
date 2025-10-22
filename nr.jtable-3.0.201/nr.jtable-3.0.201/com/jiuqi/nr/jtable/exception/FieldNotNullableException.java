/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class FieldNotNullableException
extends JTableException {
    public FieldNotNullableException(String[] datas) {
        super(JtableExceptionCodeCost.NOT_FIELD_NULLABLE, datas);
    }

    public FieldNotNullableException(String errCode, String[] datas) {
        super(errCode, datas);
    }
}

