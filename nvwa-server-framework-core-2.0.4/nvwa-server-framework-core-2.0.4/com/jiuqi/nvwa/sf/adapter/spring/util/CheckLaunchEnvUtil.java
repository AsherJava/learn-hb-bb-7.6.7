/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nvwa.sf.adapter.spring.util;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.adapter.spring.util.envcheck.AbstractLaunchEnvChecker;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckLaunchEnvUtil {
    private static final Logger logger = LoggerFactory.getLogger(CheckLaunchEnvUtil.class);

    private CheckLaunchEnvUtil() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void check() {
        Map<String, AbstractLaunchEnvChecker> beansOfType = SpringBeanUtils.getApplicationContext().getBeansOfType(AbstractLaunchEnvChecker.class);
        CountDownLatch latch = new CountDownLatch(beansOfType.size());
        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            logger.info("\u5f00\u59cb\u68c0\u6d4b\u670d\u52a1\u8fd0\u884c\u73af\u5883");
            HashMap futureAbstractLaunchEnvCheckerMap = new HashMap();
            for (AbstractLaunchEnvChecker value : beansOfType.values()) {
                value.setLatch(latch);
                futureAbstractLaunchEnvCheckerMap.put(executor.submit(value), value);
            }
            boolean await = latch.await(5L, TimeUnit.SECONDS);
            if (!await) {
                for (Map.Entry futureAbstractLaunchEnvCheckerEntry : futureAbstractLaunchEnvCheckerMap.entrySet()) {
                    if (((Future)futureAbstractLaunchEnvCheckerEntry.getKey()).isDone()) continue;
                    String timeoutMessage = ((AbstractLaunchEnvChecker)futureAbstractLaunchEnvCheckerEntry.getValue()).timeoutMessage();
                    logger.error(timeoutMessage);
                    ((Future)futureAbstractLaunchEnvCheckerEntry.getKey()).cancel(true);
                }
            }
        }
        catch (InterruptedException e) {
            logger.error("\u542f\u52a8\u73af\u5883\u6821\u9a8c\u5f02\u5e38" + e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        finally {
            executor.shutdown();
        }
        logger.info("\u670d\u52a1\u8fd0\u884c\u73af\u5883\u68c0\u6d4b\u5b8c\u6bd5");
    }
}

