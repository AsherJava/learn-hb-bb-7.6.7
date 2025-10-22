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

public class NodeCheckException
extends JTableException {
    private static final long serialVersionUID = 1L;

    public NodeCheckException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public NodeCheckException(String[] datas) {
        super(JtableExceptionCodeCost.EXCEPTION_NODE_CHECK, datas);
    }
}

