/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 */
package com.jiuqi.nvwa.sf.adapter.spring.util.envcheck.impl;

import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.nvwa.sf.adapter.spring.util.envcheck.AbstractLaunchEnvChecker;
import java.sql.Connection;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLaunchEnvCheck
extends AbstractLaunchEnvChecker {
    private final Logger logger = LoggerFactory.getLogger(DatabaseLaunchEnvCheck.class);

    @Override
    public void doCheck() {
        try (Connection connection = GlobalConnectionProviderManager.getConnection();){
            ISQLMetadata metadata = DatabaseManager.getInstance().createMetadata(connection);
            long databaseTimestamp = metadata.getDatabaseTimestamp();
            Date now = new Date();
            long diff = Math.abs(now.getTime() - databaseTimestamp);
            if (diff > 30000L) {
                this.logger.error("\u670d\u52a1\u5668\u65f6\u95f4\u6233\u6821\u9a8c\u3010\u4e0d\u901a\u8fc7\u3011\uff1a\u65f6\u95f4\u8bef\u5dee\u8d85\u8fc730\u79d2\uff0c\u5b9e\u9645\u8bef\u5dee{}\u6beb\u79d2", (Object)diff);
            } else {
                this.logger.info("\u670d\u52a1\u5668\u65f6\u95f4\u6233\u6821\u9a8c\u3010\u901a\u8fc7\u3011\uff1a\u5b9e\u9645\u8bef\u5dee{}\u6beb\u79d2", (Object)diff);
            }
        }
        catch (Exception e) {
            this.logger.error("\u670d\u52a1\u5668\u65f6\u95f4\u6233\u6821\u9a8c\u3010\u4e0d\u901a\u8fc7\u3011\uff1a" + e.getMessage(), e);
        }
    }

    @Override
    public String timeoutMessage() {
        return "\u670d\u52a1\u5668\u65f6\u95f4\u6233\u6821\u9a8c\u3010\u4e0d\u901a\u8fc7\u3011\uff1a\u6570\u636e\u5e93\u8fde\u63a5\u8d85\u65f6\uff0c\u8bf7\u68c0\u67e5\u670d\u52a1\u5668\u4e0e\u6570\u636e\u5e93\u8fde\u63a5\u72b6\u6001";
    }
}

