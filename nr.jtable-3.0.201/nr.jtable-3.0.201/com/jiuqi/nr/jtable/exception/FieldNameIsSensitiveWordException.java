/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class FieldNameIsSensitiveWordException
extends JTableException {
    public FieldNameIsSensitiveWordException(String[] datas) {
        super(JtableExceptionCodeCost.EXCEPTION_FILE_NAME_IS_SENSITIVE, datas);
    }

    public FieldNameIsSensitiveWordException(String errCode, String[] datas) {
        super(errCode, datas);
    }
}

