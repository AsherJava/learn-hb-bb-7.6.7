/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.creator;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaExtDataSourceProperties;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceCreationException;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

public interface IDataSourceLifecycleAdaptor {
    public static final String NVWA_DATASOURCES_PREFIX = "jiuqi.nvwa.datasources.";
    public static final String SPRING_DATASOURCE_PREFIX = "spring.datasource";

    public DataSource createSystemDataSource(String var1, DataSourceProperties var2) throws DataSourceCreationException;

    public DataSource createExtDataSource(String var1, NvwaExtDataSourceProperties var2) throws DataSourceCreationException;

    public void terminate(DataSource var1);

    public boolean supports(Class<? extends DataSource> var1);
}

