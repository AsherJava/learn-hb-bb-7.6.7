/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.meta;

import java.util.UUID;
import java.util.stream.Stream;

public interface MetaObject {
    public UUID getId();

    public String getTitle();

    public UUID getOwnerId();

    public Stream<MetaObject> getChildList();
}

