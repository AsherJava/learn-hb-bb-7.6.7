/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.api;

import java.io.Serializable;
import java.time.Instant;

public interface BasicGetter
extends Cloneable,
Serializable {
    public String getKey();

    public String getName();

    public String getTitle();

    public String getDesc();

    public Instant getModifyTime();
}

