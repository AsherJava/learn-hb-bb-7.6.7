/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  org.quartz.JobDataMap
 */
package com.jiuqi.bi.core.jobs.core;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import org.quartz.JobDataMap;
import org.slf4j.MDC;

public class MDCUtil {
    private MDCUtil() {
    }

    public static void generateMDC(JobDataMap dataMap) {
        String traceId = (String)dataMap.get((Object)"__sys_mdc_traceid");
        if (StringUtils.isEmpty((String)traceId)) {
            traceId = OrderGenerator.newOrder();
            dataMap.put("__sys_mdc_traceid", traceId);
        }
        MDC.put("traceid", traceId);
    }
}

