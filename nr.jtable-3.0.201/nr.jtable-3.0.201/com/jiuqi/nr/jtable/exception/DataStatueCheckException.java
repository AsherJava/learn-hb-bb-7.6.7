/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;

public class DataStatueCheckException
extends JTableException {
    private static final long serialVersionUID = 1L;

    public DataStatueCheckException(String errorCode) {
        super(errorCode);
    }

    public DataStatueCheckException(String errorCode, String[] datas) {
        super(errorCode, datas);
    }
}

