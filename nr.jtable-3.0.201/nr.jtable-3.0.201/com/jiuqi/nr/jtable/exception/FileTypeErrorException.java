/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class FileTypeErrorException
extends JTableException {
    public FileTypeErrorException(String errorCode) {
        super(JtableExceptionCodeCost.EXCEPTION_FILE_TYPE_IS_ERROR);
    }

    public FileTypeErrorException(String errorCode, String[] datas) {
        super(errorCode, datas);
    }
}

