/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.bizmodel.define.config;

import com.jiuqi.common.base.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class BdeBizModelConfig {
    private static String defaultDelimiter = "~";
    private static Integer requestRetryLimit = 3;
    private static Integer cacheValidTime = 120;
    private static String sqlServerCollation = "Chinese_PRC_CI_AS";
    private static Integer debugTimeoutMinutes = 60;
    private static Boolean parseFetchResultByDb = true;

    public static String getDefaultDelimiter() {
        return defaultDelimiter;
    }

    @Value(value="${jiuqi.bde.default-delimiter:~}")
    public void setDefaultDelimiter(String defaultDelimiter) {
        if (!StringUtils.isEmpty((String)defaultDelimiter)) {
            BdeBizModelConfig.defaultDelimiter = defaultDelimiter;
        }
    }

    public static Integer getRequestRetryLimit() {
        return requestRetryLimit;
    }

    @Value(value="${jiuqi.bde.request.retrylimit:10}")
    public void setRequestRetryLimit(Integer requestRetryLimit) {
        if (requestRetryLimit != null) {
            BdeBizModelConfig.requestRetryLimit = Math.max(requestRetryLimit, 1);
        }
    }

    public static Integer getValidTime() {
        return cacheValidTime;
    }

    @Value(value="${bde.cache.valitime:120}")
    public static void setValidTime(Integer cacheValidTime) {
        if (cacheValidTime != null) {
            BdeBizModelConfig.cacheValidTime = cacheValidTime;
        }
    }

    public static String getSqlServerCollation() {
        return sqlServerCollation;
    }

    @Value(value="${jiuqi.bde.db.sqlserver.collation:Chinese_PRC_CI_AS}")
    public void setSqlServerCollation(String sqlServerCollation) {
        if (!StringUtils.isEmpty((String)sqlServerCollation)) {
            BdeBizModelConfig.sqlServerCollation = sqlServerCollation;
        }
    }

    public static Integer getDebugTimeoutMinutes() {
        return debugTimeoutMinutes;
    }

    @Value(value="${jiuqi.bde.debug.timout-minutes:60}")
    public void setDebugTimeoutMinutes(Integer debugTimeoutMinutes) {
        if (debugTimeoutMinutes != null && debugTimeoutMinutes >= 10 && debugTimeoutMinutes <= 10080) {
            BdeBizModelConfig.debugTimeoutMinutes = debugTimeoutMinutes;
        }
    }

    public static Boolean getParseFetchResultByDb() {
        return parseFetchResultByDb;
    }

    @Value(value="${jiuqi.bde.fetch.custom.parse-by-db:true}")
    public void setParseFetchResultByDb(Boolean parseFetchResultByDb) {
        BdeBizModelConfig.parseFetchResultByDb = parseFetchResultByDb;
    }
}

