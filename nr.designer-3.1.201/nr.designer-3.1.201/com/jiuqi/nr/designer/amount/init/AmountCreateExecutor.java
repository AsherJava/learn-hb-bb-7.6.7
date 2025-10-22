/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.util.SpringUtil
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.designer.amount.init;

import com.jiuqi.np.definition.internal.util.SpringUtil;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.designer.amount.init.AmountCreateProcessor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmountCreateExecutor
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        Logger logger = LoggerFactory.getLogger(AmountCreateProcessor.class);
        logger.info("\u5f00\u59cb\u521d\u59cb\u5316\u91cf\u7eb2\u6570\u636e");
        AmountCreateProcessor bean = (AmountCreateProcessor)SpringUtil.getBean(AmountCreateProcessor.class);
        bean.createData();
        logger.info("\u91cf\u7eb2\u6570\u636e\u521d\u59cb\u5316\u5b8c\u6210");
    }
}

