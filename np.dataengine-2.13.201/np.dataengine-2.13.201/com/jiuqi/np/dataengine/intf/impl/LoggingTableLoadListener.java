/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.sql.loader.ILoadListener
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.database.sql.loader.ILoadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingTableLoadListener
implements ILoadListener {
    private static final Logger logger = LoggerFactory.getLogger(LoggingTableLoadListener.class);

    public void createProcedure(String procedure) {
        logger.debug("\u521b\u5efa\u5b58\u50a8\u8fc7\u7a0b\uff1a" + procedure);
    }

    public void executeProcedure(String procedure) {
        logger.debug("\u6267\u884c\u5b58\u50a8\u8fc7\u7a0b\uff1a" + procedure);
    }

    public void executeSQL(String sql) {
        logger.debug("\u6267\u884cSQL\uff1a" + sql);
    }
}

