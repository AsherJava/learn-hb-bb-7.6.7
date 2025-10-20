/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.Db2SqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.DmSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.GaussSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.HanaSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.Kingbase8SqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.MySQLSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.OracleSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.PolarDBSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.PostgreSQLSqlHandler
 */
package com.jiuqi.dc.base.common.jdbc.sqlhandler;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.Db2SqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.DmSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.GaussSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.HanaSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.Kingbase8SqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.MySQLSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.OracleSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.PolarDBSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.PostgreSQLSqlHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.dc.base.common"})
public class DcSqlHandlerConfig {
    @Value(value="${spring.datasource.dbType}")
    private String dbType;

    @Bean
    public IDbSqlHandler getSqlHandler() {
        switch (this.dbType) {
            case "dameng": {
                return new DmSqlHandler();
            }
            case "hana": {
                return new HanaSqlHandler();
            }
            case "mysql": {
                return new MySQLSqlHandler();
            }
            case "oracle": {
                return new OracleSqlHandler();
            }
            case "gauss": {
                return new GaussSqlHandler();
            }
            case "polardb": {
                return new PolarDBSqlHandler();
            }
            case "postgresql": {
                return new PostgreSQLSqlHandler();
            }
            case "db2": {
                return new Db2SqlHandler();
            }
            case "kingbase": {
                return new Kingbase8SqlHandler();
            }
        }
        return new OracleSqlHandler();
    }
}

