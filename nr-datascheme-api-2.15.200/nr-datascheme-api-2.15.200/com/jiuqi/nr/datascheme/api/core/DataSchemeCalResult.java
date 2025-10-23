/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.core;

import com.jiuqi.nr.datascheme.api.core.CalResult;
import java.time.Instant;

public interface DataSchemeCalResult {
    public String getKey();

    public String getDataSchemeKey();

    public CalResult getCalResult();

    public String getMessage();

    public Instant getUpdateTime();
}

