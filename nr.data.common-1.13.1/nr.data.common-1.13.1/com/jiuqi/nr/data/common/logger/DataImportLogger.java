/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.log.UnitReportLog
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 */
package com.jiuqi.nr.data.common.logger;

import com.jiuqi.nr.common.log.UnitReportLog;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataImportLogger {
    private long startTime = System.currentTimeMillis();
    private final String taskKey;
    private final DataServiceLogHelper dataServiceLogHelper;
    private final UnitReportLog unitReportLog;
    private final LogDimensionCollection defaultLogDimensionCollection;
    private boolean isAddInfos;
    private boolean isCancled;

    public DataImportLogger(String taskKey, DataServiceLogHelper dataServiceLogHelper, UnitReportLog unitReportLog, LogDimensionCollection defaultLogDimensionCollection) {
        this.taskKey = taskKey;
        this.dataServiceLogHelper = dataServiceLogHelper;
        this.unitReportLog = unitReportLog;
        this.defaultLogDimensionCollection = defaultLogDimensionCollection;
    }

    public void addFormToUnit(String unitCode, String reportKey) {
        this.isAddInfos = true;
        this.unitReportLog.addFormToUnit(unitCode, reportKey);
    }

    public void addTableToUnit(String unitCode, String reportKey) {
        this.isAddInfos = true;
        this.unitReportLog.addTableToUnit(unitCode, reportKey);
    }

    public void startImport() {
        this.startTime = System.currentTimeMillis();
    }

    public void finishImport() {
        if (!this.isCancled) {
            this.info("\u5bfc\u5165\u7ed3\u675f", "", true);
        }
    }

    public void cancelImport() {
        if (!this.isCancled) {
            this.isCancled = true;
            this.info("\u5bfc\u5165\u53d6\u6d88", "", true);
        }
    }

    public void importError(String message) {
        this.error("\u5bfc\u5165\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38", message, true);
    }

    public void importError(String message, LogDimensionCollection errorDimensionCollection) {
        this.error("\u5bfc\u5165\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38", message, errorDimensionCollection == null ? this.defaultLogDimensionCollection : errorDimensionCollection, false);
    }

    public void info(String title, String message, boolean needRecordInfos) {
        this.info(title, message, this.defaultLogDimensionCollection, needRecordInfos);
    }

    public void info(String title, String message, LogDimensionCollection logDimensionCollection, boolean needRecordInfos) {
        if (message == null) {
            message = title;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String startTimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(this.startTime));
        stringBuilder.append("\n\u5bfc\u5165\u5f00\u59cb\u4e0e\u4e8e\u3010").append(startTimeString).append("\u3011\uff0c\u603b\u5386\u65f6\u3010").append((System.currentTimeMillis() - this.startTime) / 1000L).append("\u3011\u79d2\u3002\n").append(message);
        if (needRecordInfos && this.isAddInfos) {
            this.dataServiceLogHelper.info(this.taskKey, logDimensionCollection, this.unitReportLog, title, stringBuilder.toString());
        } else {
            this.dataServiceLogHelper.info(this.taskKey, logDimensionCollection, title, stringBuilder.toString());
        }
    }

    public void error(String title, String message, boolean needRecordInfos) {
        this.error(title, message, this.defaultLogDimensionCollection, needRecordInfos);
    }

    public void error(String title, String message, LogDimensionCollection logDimensionCollection, boolean needRecordInfos) {
        if (message == null) {
            message = title;
        }
        if (needRecordInfos && this.isAddInfos) {
            this.dataServiceLogHelper.error(this.taskKey, logDimensionCollection, this.unitReportLog, title, message);
        } else {
            this.dataServiceLogHelper.error(this.taskKey, logDimensionCollection, title, message);
        }
    }
}

