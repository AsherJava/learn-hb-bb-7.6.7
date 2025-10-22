/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.period.internal.fieldinit;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.period.internal.fieldinit.PeriodFieldInitProcessor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeriodFieldInitExecutor
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        Logger logger = LoggerFactory.getLogger(PeriodFieldInitProcessor.class);
        logger.info("\u5f00\u59cb\u521d\u59cb\u531613\u671f\u8865\u5145\u5b57\u6bb5");
        PeriodFieldInitProcessor bean = (PeriodFieldInitProcessor)SpringBeanUtils.getBean(PeriodFieldInitProcessor.class);
        bean.createData();
        logger.info("13\u671f\u8865\u5145\u5b57\u6bb5\u521d\u59cb\u5316\u5b8c\u6210");
    }
}

