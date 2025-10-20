/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    private static Logger logger = LoggerFactory.getLogger(LogUtil.class);

    static void log(String e) {
        logger.info(e);
    }

    static void log(Throwable e) {
        logger.error(e.getMessage());
    }
}

