/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.dsproxy;

import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.DataSourceDecorator;
import javax.sql.DataSource;

public class DataSourceDecorationStage {
    private final String beanName;
    private final DataSourceDecorator dataSourceDecorator;
    private final DataSource dataSource;

    public DataSourceDecorationStage(String beanName, DataSourceDecorator dataSourceDecorator, DataSource dataSource) {
        this.beanName = beanName;
        this.dataSourceDecorator = dataSourceDecorator;
        this.dataSource = dataSource;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public DataSourceDecorator getDataSourceDecorator() {
        return this.dataSourceDecorator;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }
}

