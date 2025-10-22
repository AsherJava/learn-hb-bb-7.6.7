/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.core.QuartzJobThreadFactory
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.analysisreport.async.executor;

import com.jiuqi.bi.core.jobs.core.QuartzJobThreadFactory;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.analysisreport.async.executor.ContextCopyingDecorator;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class AnalysisVarThreadExuecutor {
    public static ThreadPoolTaskExecutor init(String resourceName) {
        try {
            ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
            int maxThreadCount = AnalysisVarThreadExuecutor.getMaxThreadCount();
            threadPoolTaskExecutor.setCorePoolSize(maxThreadCount);
            threadPoolTaskExecutor.setKeepAliveSeconds(60);
            threadPoolTaskExecutor.setMaxPoolSize(maxThreadCount);
            threadPoolTaskExecutor.setThreadFactory((ThreadFactory)new QuartzJobThreadFactory(resourceName));
            threadPoolTaskExecutor.setTaskDecorator(new ContextCopyingDecorator());
            threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            threadPoolTaskExecutor.initialize();
            return threadPoolTaskExecutor;
        }
        catch (Exception e) {
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a", "\u7ebf\u7a0b\u6c60\u521d\u59cb\u5316\u5931\u8d25" + e.getMessage(), AnalysisReportLogHelper.LOGLEVEL_ERROR);
            return null;
        }
    }

    public static int getMaxThreadCount() {
        int listSize = 5;
        try {
            INvwaSystemOptionService nvwaSystemOptionService = (INvwaSystemOptionService)SpringBeanUtils.getBean(INvwaSystemOptionService.class);
            String option = nvwaSystemOptionService.get("nr-audit-group", "MAX_THREAD_COUNT");
            if (StringUtils.isNotEmpty((String)option)) {
                listSize = Integer.parseInt(option);
                return listSize;
            }
        }
        catch (Exception exception) {}
        finally {
            return listSize;
        }
    }
}

