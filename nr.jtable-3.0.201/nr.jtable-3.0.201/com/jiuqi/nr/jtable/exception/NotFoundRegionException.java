/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class NotFoundRegionException
extends JTableException {
    public NotFoundRegionException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public NotFoundRegionException(String[] datas) {
        super(JtableExceptionCodeCost.NOTFOUND_REGION, datas);
    }
}

