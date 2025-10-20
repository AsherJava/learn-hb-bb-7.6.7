/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dynamic.temptable.framework.connection.ConnectionManager
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.common.temptable.dynamic.connection;

import com.jiuqi.nr.dynamic.temptable.framework.connection.ConnectionManager;
import java.sql.Connection;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

@Component
public class DynamicTempTableConnectionManager
implements ConnectionManager {
    @Autowired
    private DataSource dataSource;

    public Connection getConnection() {
        return DataSourceUtils.getConnection((DataSource)this.dataSource);
    }

    public void releaseConnection(Connection connection) {
        DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
    }
}

