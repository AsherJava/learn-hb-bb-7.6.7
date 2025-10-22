/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.utils;

import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class logUtil {
    static Logger logger = LoggerFactory.getLogger(logUtil.class);

    public static void info(String msg, String ... infos) {
        StringBuffer buf = new StringBuffer();
        buf.append(msg);
        for (String info : infos) {
            buf.append(info).append("-");
        }
        logger.info(buf.toString());
    }

    public static void info(String msg, PeriodDataDefineImpl periodDataDefineImpl) {
        StringBuffer buf = new StringBuffer();
        buf.append(msg);
        buf.append(periodDataDefineImpl);
        logger.info(buf.toString());
    }
}

