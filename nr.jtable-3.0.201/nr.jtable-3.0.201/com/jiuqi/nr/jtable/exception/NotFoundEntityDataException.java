/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class NotFoundEntityDataException
extends JTableException {
    public NotFoundEntityDataException(String[] datas) {
        super(JtableExceptionCodeCost.NOTFOUND_ENTITY_DATA, datas);
    }

    public NotFoundEntityDataException(String errCode, String[] datas) {
        super(errCode, datas);
    }
}

