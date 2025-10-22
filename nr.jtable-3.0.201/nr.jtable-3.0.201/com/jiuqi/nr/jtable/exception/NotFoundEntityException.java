/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class NotFoundEntityException
extends JTableException {
    public NotFoundEntityException(String[] datas) {
        super(JtableExceptionCodeCost.NOTFOUND_ENTITY, datas);
    }

    public NotFoundEntityException(String errCode, String[] datas) {
        super(errCode, datas);
    }
}

