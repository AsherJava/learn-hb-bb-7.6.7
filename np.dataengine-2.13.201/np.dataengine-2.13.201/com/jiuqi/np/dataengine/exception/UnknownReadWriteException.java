/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.np.dataengine.exception;

import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.parser.ParseException;

public class UnknownReadWriteException
extends ParseException {
    private IFunction func;
    private static final long serialVersionUID = 6647369806037681506L;

    public UnknownReadWriteException(IFunction func) {
        this.func = func;
    }

    public UnknownReadWriteException(String msg) {
        super(msg);
    }

    public UnknownReadWriteException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IFunction getFunc() {
        return this.func;
    }
}

