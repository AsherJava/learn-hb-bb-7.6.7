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

public class DataEntryNoTaskKeyException
extends JTableException {
    private static final long serialVersionUID = -4388814927466210833L;

    public DataEntryNoTaskKeyException(String[] datas) {
        super(JtableExceptionCodeCost.DATAENTRY_NOTASKKEY, datas);
    }

    public DataEntryNoTaskKeyException(String errCode, String[] datas) {
        super(errCode, datas);
    }
}

