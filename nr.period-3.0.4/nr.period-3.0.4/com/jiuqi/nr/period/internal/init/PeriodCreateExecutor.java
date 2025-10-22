/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.period.internal.init;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.period.internal.init.PeriodCreateProcessor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeriodCreateExecutor
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        Logger logger = LoggerFactory.getLogger(PeriodCreateProcessor.class);
        logger.info("\u5f00\u59cb\u521d\u59cb\u5316\u65f6\u671f\u6570\u636e");
        PeriodCreateProcessor bean = (PeriodCreateProcessor)SpringBeanUtils.getBean(PeriodCreateProcessor.class);
        bean.createData();
        logger.info("\u65f6\u671f\u6570\u636e\u521d\u59cb\u5316\u5b8c\u6210");
    }
}

