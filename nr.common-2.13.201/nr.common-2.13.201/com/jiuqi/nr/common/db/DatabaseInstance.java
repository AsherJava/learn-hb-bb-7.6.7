/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.util.Version
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.nr.common.db;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.util.Version;
import com.jiuqi.nr.common.db.DataSourceConfig;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Lazy(value=false)
public class DatabaseInstance {
    private static final Logger log = LoggerFactory.getLogger(DatabaseInstance.class);
    private final DataSourceProperties dataSourceProperties;
    private final DataSourceConfig dataSourceConfig;
    private static Version version;
    private static IDatabase database;

    public DatabaseInstance(DataSourceProperties dataSourceProperties, DataSourceConfig dataSourceConfig) {
        this.dataSourceProperties = dataSourceProperties;
        this.dataSourceConfig = dataSourceConfig;
    }

    @PostConstruct
    public void init() {
        String url = this.dataSourceProperties.getUrl();
        DatabaseInstance.setDatabase(DatabaseManager.getInstance().findDatabaseByURL(url));
        String dbVersion = this.dataSourceConfig.getDbVersion();
        if (StringUtils.hasLength(dbVersion)) {
            try {
                version = new Version(dbVersion);
            }
            catch (Exception e) {
                log.warn("Could not parse database version: {}", (Object)dbVersion);
            }
        }
    }

    public static Version getVersion() {
        return version;
    }

    public static IDatabase getDatabase() {
        return database;
    }

    public static void setDatabase(IDatabase database) {
        DatabaseInstance.database = database;
    }
}

