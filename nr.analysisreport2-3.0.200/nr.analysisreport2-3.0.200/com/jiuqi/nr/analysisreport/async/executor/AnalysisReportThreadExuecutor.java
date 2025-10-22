/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.analysisreport.async.executor;

import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class AnalysisReportThreadExuecutor {
    private static final Logger logger = LoggerFactory.getLogger(AnalysisReportThreadExuecutor.class);
    private static NpApplication npApplication = null;

    public static void run(List<Callable> callables) {
        if (CollectionUtils.isEmpty(callables)) {
            return;
        }
        long startTime = System.currentTimeMillis();
        logger.info("\u5206\u6790\u62a5\u544a-\u53d8\u91cf\u89e3\u6790\u7ebf\u7a0b\u5f00\u59cb\u5f02\u6b65\u6267\u884c\uff0c\u5f00\u59cb\u65f6\u95f4\uff1a" + startTime);
        ArrayList<Future> futures = new ArrayList<Future>();
        if (npApplication == null) {
            npApplication = (NpApplication)SpringBeanUtils.getBean(NpApplication.class);
        }
        for (int i = 0; i < callables.size(); ++i) {
            try {
                Future future = npApplication.asyncRunAsContext(NpContextHolder.getContext(), callables.get(i));
                futures.add(future);
                continue;
            }
            catch (Exception var6) {
                logger.error("\u5206\u6790\u62a5\u544a\u53d8\u91cf\u89e3\u6790\u51fa\u9519" + var6.getMessage());
            }
        }
        logger.info("futrue\u6570\u91cf" + futures.size());
        AnalysisReportThreadExuecutor.waitAllFinish(futures);
        long endTime = System.currentTimeMillis();
        logger.info("\u5206\u6790\u62a5\u544a-\u53d8\u91cf\u89e3\u6790\u7ebf\u7a0b\u7ed3\u675f\u5f02\u6b65\u6267\u884c\uff0c\u6267\u884c\u603b\u8017\u65f6\uff1a" + (endTime - startTime));
    }

    public static void waitAllFinish(List<Future> futures) {
        do {
            Iterator<Future> iterator = futures.iterator();
            while (iterator.hasNext()) {
                Future future = iterator.next();
                if (!future.isDone()) continue;
                iterator.remove();
            }
        } while (!CollectionUtils.isEmpty(futures));
    }
}

