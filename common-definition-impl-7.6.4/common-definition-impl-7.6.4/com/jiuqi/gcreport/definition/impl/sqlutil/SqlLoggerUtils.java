/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.definition.impl.sqlutil;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlLoggerUtils {
    private static Logger logger = LoggerFactory.getLogger(SqlUtils.class);

    public static void warn(String methodName, String paramName) {
        logger.warn("sql\u9884\u5904\u7406\u65b9\u6cd5\u6709\u7a7a\u53c2\u6570\uff1a \u65b9\u6cd5\u540d:{}\uff0c \u4e3a\u7a7a\u53c2\u6570\u540d:{}\uff0c \u7528\u6237:{}\uff0c \u65f6\u95f4:{}\uff0c \u5806\u6808\uff1a{}", methodName, paramName, SqlLoggerUtils.getUserName(), DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"), Thread.currentThread().getStackTrace());
    }

    private static String getUserName() {
        return NpContextHolder.getContext().getUser() == null ? "" : NpContextHolder.getContext().getUser().getName();
    }
}

