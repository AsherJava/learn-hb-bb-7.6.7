/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.meta;

import com.jiuqi.va.biz.intf.meta.MetaInfoHistory;
import com.jiuqi.va.biz.intf.meta.MetaState;

public interface MetaInfoEdition
extends MetaInfoHistory {
    public long getOrgVersion();

    public String getUserName();

    public MetaState getState();
}

