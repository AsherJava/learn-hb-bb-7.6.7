/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.np.dataengine.exception;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.definitions.Formula;

public class FormulaParseException
extends SyntaxException {
    private static final long serialVersionUID = 7772697525253819916L;
    private Formula errorFormua;

    public FormulaParseException() {
    }

    public FormulaParseException(String message) {
        super(message);
    }

    public FormulaParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormulaParseException(Throwable cause) {
        super(cause);
    }

    public Formula getErrorFormua() {
        return this.errorFormua;
    }

    public void setErrorFormua(Formula errorFormua) {
        this.errorFormua = errorFormua;
    }
}

