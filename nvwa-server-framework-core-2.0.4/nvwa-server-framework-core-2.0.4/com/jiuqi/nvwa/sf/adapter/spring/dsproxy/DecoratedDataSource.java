/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.datasource.DelegatingDataSource
 */
package com.jiuqi.nvwa.sf.adapter.spring.dsproxy;

import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.DataSourceDecorationStage;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DelegatingDataSource;

public class DecoratedDataSource
extends DelegatingDataSource {
    private final DataSource realDataSource;
    private final DataSource decoratedDataSource;
    private final List<DataSourceDecorationStage> decoratingChain;

    DecoratedDataSource(DataSource realDataSource, DataSource decoratedDataSource, List<DataSourceDecorationStage> decoratingChain) {
        super(decoratedDataSource);
        this.realDataSource = realDataSource;
        this.decoratedDataSource = decoratedDataSource;
        this.decoratingChain = decoratingChain;
    }

    public DataSource getRealDataSource() {
        return this.realDataSource;
    }

    public DataSource getDecoratedDataSource() {
        return this.decoratedDataSource;
    }

    public List<DataSourceDecorationStage> getDecoratingChain() {
        return this.decoratingChain;
    }

    public String toString() {
        return this.decoratingChain.stream().map(entry -> " [" + entry.getDataSource().getClass().getName() + "]").collect(Collectors.joining(" -> ")) + " ->  [" + this.realDataSource.getClass().getName() + "]";
    }
}

