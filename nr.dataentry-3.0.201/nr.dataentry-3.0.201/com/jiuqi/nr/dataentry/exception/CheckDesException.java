/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.exception.JTableException
 *  com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost
 */
package com.jiuqi.nr.dataentry.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class CheckDesException
extends JTableException {
    private static final long serialVersionUID = 1L;

    public CheckDesException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public CheckDesException(String[] datas) {
        super(JtableExceptionCodeCost.EXCEPTION_CHECK_DESCRIPTION, datas);
    }
}

