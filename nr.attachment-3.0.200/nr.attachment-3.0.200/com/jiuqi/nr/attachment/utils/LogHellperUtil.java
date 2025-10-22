/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 */
package com.jiuqi.nr.attachment.utils;

import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogHellperUtil {
    private String startTime;
    private DataServiceLogHelper logHelper;

    public LogHellperUtil(DataServiceLoggerFactory dataServiceLoggerFactory, String module) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        this.startTime = formatter.format(date);
        this.logHelper = dataServiceLoggerFactory.getLogger(module, OperLevel.USER_OPER);
    }

    public void info(String taskKey, LogDimensionCollection dimensionCollection, String title, String message) {
        this.logHelper.info(taskKey, dimensionCollection, title, "\u5f00\u59cb\u65f6\u95f4\uff1a" + this.startTime + "," + message);
    }

    public void error(String taskKey, LogDimensionCollection dimensionCollection, String title, String message) {
        this.logHelper.error(taskKey, dimensionCollection, title, "\u5f00\u59cb\u65f6\u95f4\uff1a" + this.startTime + "," + message);
    }
}

