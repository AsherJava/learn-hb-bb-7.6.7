/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 */
package com.jiuqi.bi.core.jobs.defaultlog;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.defaultlog.DefaultLogDAO;
import com.jiuqi.bi.core.jobs.defaultlog.LogItem;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import java.sql.Connection;
import java.util.Date;
import org.slf4j.LoggerFactory;

public class SyncLogger
extends Logger {
    public SyncLogger(JobContext context) {
        super(context);
    }

    @Override
    void doLog(LogItem item) {
        this.insertLogToDatabase(item);
    }

    private void insertLogToDatabase(LogItem list) {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            DefaultLogDAO.insertLog(conn, list);
        }
        catch (Exception e) {
            org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(this.getClass());
            slf4jLogger.error("\u5199\u5165\u4efb\u52a1\u65e5\u5fd7\u51fa\u73b0\u9519\u8bef", e);
            slf4jLogger.debug(this.printLogItem(list));
        }
    }

    private String printLogItem(LogItem item) {
        return String.format("\u65e0\u6cd5\u5b58\u50a8\u5230\u6570\u636e\u5e93\u4e2d\u7684\u65e5\u5fd7\uff1a[%1$tF %1$tT] %2$s", new Date(item.getTimestamp()), item.getMessage());
    }
}

