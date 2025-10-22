/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchMergeAccess;

public class CanAccessBatchMergeAccess
implements IBatchMergeAccess {
    private final String name;

    public CanAccessBatchMergeAccess(String name) {
        this.name = name;
    }

    @Override
    public AccessCode getAccessCode(IAccessFormMerge merge) {
        return new AccessCode(this.name);
    }
}

