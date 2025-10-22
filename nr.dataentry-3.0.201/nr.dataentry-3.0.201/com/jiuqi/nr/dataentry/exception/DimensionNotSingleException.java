/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.exception.JTableException
 */
package com.jiuqi.nr.dataentry.exception;

import com.jiuqi.nr.jtable.exception.JTableException;

public class DimensionNotSingleException
extends JTableException {
    private static final long serialVersionUID = 1L;

    public DimensionNotSingleException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public DimensionNotSingleException(String[] datas) {
        super("EXCEPTION_DIMENSIONNOTSINGLE_EXCEPTION", datas);
    }
}

