/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.meta;

import com.jiuqi.va.biz.intf.meta.MetaGroupHistory;
import com.jiuqi.va.biz.intf.meta.MetaState;

public interface MetaGroupEdition
extends MetaGroupHistory {
    public long getOrgVersion();

    public String getUserName();

    public MetaState getState();
}

