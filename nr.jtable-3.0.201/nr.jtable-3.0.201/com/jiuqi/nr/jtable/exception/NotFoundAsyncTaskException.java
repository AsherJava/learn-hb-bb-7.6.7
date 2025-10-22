/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.jtable.exception.JTableException;

public class NotFoundAsyncTaskException
extends JTableException {
    public NotFoundAsyncTaskException(String errCode, String[] datas) {
        super(errCode, datas);
    }

    public NotFoundAsyncTaskException(String[] datas) {
        super(ExceptionCodeCost.NOTFOUND_ASYNCTASK, datas);
    }
}

