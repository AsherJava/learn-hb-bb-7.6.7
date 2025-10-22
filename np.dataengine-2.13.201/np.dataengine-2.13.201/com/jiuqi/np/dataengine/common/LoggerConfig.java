/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.DataEngineUtil;

public class LoggerConfig {
    private boolean debug = DataEngineUtil.logger.isDebugEnabled() || DataEngineUtil.logger.isTraceEnabled() || "true".equals(System.getProperty("np.dataengine.debug"));
    private boolean fmlplan = this.debug || "true".equals(System.getProperty("np.dataengine.out.fmlplan"));
    private boolean sqllog;
    private boolean outSql = this.debug || "true".equals(System.getProperty("np.dataengine.debug.out.sql"));
    private boolean datachange = "true".equals(System.getProperty("np.dataengine.debug.out.datachange"));

    public boolean isDebug() {
        return this.debug;
    }

    public boolean isFmlplan() {
        return this.fmlplan;
    }

    public boolean isSqllog() {
        return this.sqllog;
    }

    public boolean isOutSql() {
        return this.outSql;
    }

    public boolean isDatachange() {
        return this.datachange;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setFmlplan(boolean fmlplan) {
        this.fmlplan = fmlplan;
    }

    public void setSqllog(boolean sqllog) {
        this.sqllog = sqllog;
    }

    public void setOutSql(boolean outSql) {
        this.outSql = outSql;
    }

    public void setDatachange(boolean datachange) {
        this.datachange = datachange;
    }
}

