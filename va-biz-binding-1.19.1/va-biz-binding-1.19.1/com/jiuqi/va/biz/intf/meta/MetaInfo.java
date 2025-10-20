/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.meta;

import java.util.UUID;

public interface MetaInfo {
    public UUID getId();

    public long getVersion();

    public String getName();

    public String getTitle();

    public String getModule();

    public String getMetaType();

    public String getModelType();

    public String getGroupName();
}

