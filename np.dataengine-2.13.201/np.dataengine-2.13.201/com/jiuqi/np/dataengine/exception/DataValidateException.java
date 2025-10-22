/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.exception;

import com.jiuqi.np.dataengine.exception.IncorrectQueryException;

public class DataValidateException
extends IncorrectQueryException {
    private static final long serialVersionUID = 4068223661181452751L;

    public DataValidateException(String arg0) {
        super(arg0);
    }

    public DataValidateException(Throwable arg0) {
        super(arg0);
    }

    public DataValidateException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}

