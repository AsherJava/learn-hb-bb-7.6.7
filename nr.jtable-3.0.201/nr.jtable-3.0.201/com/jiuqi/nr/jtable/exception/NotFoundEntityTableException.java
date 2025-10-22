/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class NotFoundEntityTableException
extends JTableException {
    public NotFoundEntityTableException(String[] datas) {
        super(JtableExceptionCodeCost.NOTFOUND_ENTITYTABLE, datas);
    }
}

