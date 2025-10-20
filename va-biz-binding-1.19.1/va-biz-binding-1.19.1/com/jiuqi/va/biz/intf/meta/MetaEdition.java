/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.meta;

import com.jiuqi.va.biz.intf.meta.MetaState;
import java.util.UUID;

public interface MetaEdition {
    public UUID getId();

    public long getVersion();

    public UUID getNewId();

    public MetaState getState();
}

