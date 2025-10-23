/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.core;

import java.io.Serializable;
import java.time.Instant;

public interface Basic
extends Cloneable,
Serializable {
    public String getKey();

    public String getCode();

    public String getTitle();

    public String getDesc();

    public Instant getUpdateTime();
}

