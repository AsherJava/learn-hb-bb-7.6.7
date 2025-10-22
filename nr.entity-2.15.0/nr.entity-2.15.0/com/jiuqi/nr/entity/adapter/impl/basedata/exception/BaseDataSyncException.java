/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.exception;

import com.jiuqi.nr.entity.engine.exception.EntityUpdateException;

public class BaseDataSyncException
extends EntityUpdateException {
    public static final String BASEDATA_SYNC_ERROR = "\u57fa\u7840\u6570\u636e\u6570\u636e\u66f4\u65b0\u5f02\u5e38";
    private static final long serialVersionUID = 2051515387118282454L;

    public BaseDataSyncException() {
        super(BASEDATA_SYNC_ERROR);
    }

    public BaseDataSyncException(String arg0) {
        super(arg0);
    }

    public BaseDataSyncException(Exception ex) {
        super(ex);
    }
}

