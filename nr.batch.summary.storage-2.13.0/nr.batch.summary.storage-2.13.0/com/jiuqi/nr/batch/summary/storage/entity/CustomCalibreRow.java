/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.entity;

import com.jiuqi.nr.batch.summary.storage.entity.impl.CustomCalibreValue;

public interface CustomCalibreRow {
    public String getKey();

    public String getCode();

    public String getTitle();

    public String getScheme();

    public CustomCalibreValue getValue();

    public String getParentCode();

    public String getOrdinal();

    public boolean isValidRow();
}

