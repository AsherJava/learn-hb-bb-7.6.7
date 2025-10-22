/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.service;

import com.jiuqi.nr.snapshot.input.QueryPeriodDataSourceContext;
import com.jiuqi.nr.snapshot.input.QuerySnapshotDataSourceContext;
import com.jiuqi.nr.snapshot.service.DataSource;

public interface DataSourceBuilder {
    public DataSource querySnapshotDataSource(QuerySnapshotDataSourceContext var1);

    public DataSource queryPeriodDataSource(QueryPeriodDataSourceContext var1);
}

