/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.exception.JTableException
 */
package com.jiuqi.nr.dataentry.exception;

import com.jiuqi.nr.jtable.exception.JTableException;

public class DataEntryFunctionException
extends JTableException {
    private static final long serialVersionUID = -4388814927466210833L;

    public DataEntryFunctionException(String message) {
        super(message);
    }

    public DataEntryFunctionException(int errorCode, String message) {
        this(message);
    }
}

