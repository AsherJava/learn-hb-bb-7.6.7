/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.core;

import java.time.Instant;

public interface DataFieldDeployInfo {
    public String getDataSchemeKey();

    public String getDataTableKey();

    public String getSourceTableKey();

    public String getDataFieldKey();

    public String getTableModelKey();

    public String getColumnModelKey();

    public String getFieldName();

    public String getTableName();

    @Deprecated
    public String getVersion();

    public Instant getUpdateTime();
}

