/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.datasource.AdhocDataSourceException
 *  com.jiuqi.bi.adhoc.datasource.reader.IDataSourceReader
 *  com.jiuqi.nvwa.bql.datasource.INvwaDataSource
 *  com.jiuqi.nvwa.bql.datasource.NvwaNoSQLDataSource
 */
package com.jiuqi.nr.bql.datasource;

import com.jiuqi.bi.adhoc.datasource.AdhocDataSourceException;
import com.jiuqi.bi.adhoc.datasource.reader.IDataSourceReader;
import com.jiuqi.nr.bql.datasource.ComponentSet;
import com.jiuqi.nr.bql.datasource.DataSchemeDataSourceReader;
import com.jiuqi.nvwa.bql.datasource.INvwaDataSource;
import com.jiuqi.nvwa.bql.datasource.NvwaNoSQLDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSchemeDataSource
extends NvwaNoSQLDataSource
implements INvwaDataSource {
    @Autowired
    private ComponentSet componentSet;

    public boolean support(String dataSourceName) throws AdhocDataSourceException {
        return true;
    }

    public IDataSourceReader createDataReader(String dataSourceName) throws AdhocDataSourceException {
        return new DataSchemeDataSourceReader(this.componentSet);
    }
}

