/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.impl.org.exception;

import com.jiuqi.nr.entity.engine.exception.EntityUpdateException;

public class OrgDataSyncException
extends EntityUpdateException {
    private static final long serialVersionUID = -4760335476375889452L;
    public static final String ORG_SYNC_ERROR = "\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u66f4\u65b0\u5f02\u5e38";

    public OrgDataSyncException() {
        super(ORG_SYNC_ERROR);
    }

    public OrgDataSyncException(String arg0) {
        super(arg0);
    }

    public OrgDataSyncException(Exception ex) {
        super(ex);
    }
}

