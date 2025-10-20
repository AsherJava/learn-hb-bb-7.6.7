/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.bde.fetch.impl.callback;

import com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum;
import com.jiuqi.bde.fetch.impl.callback.service.FetchBalanceTableCleanServiceImpl;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FetchBalanceTableClean
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FetchBalanceTableClean.class);

    public void execute(DataSource dataSource) throws Exception {
        FetchBalanceTableCleanServiceImpl cleanService = (FetchBalanceTableCleanServiceImpl)ApplicationContextRegister.getBean(FetchBalanceTableCleanServiceImpl.class);
        if (cleanService == null) {
            return;
        }
        for (MemoryBalanceTypeEnum table : MemoryBalanceTypeEnum.values()) {
            logger.info("\u5f00\u59cb\u6e05\u9664\u70ed\u70b9\u8868{}\u6570\u636e", (Object)table.getCode());
            try {
                cleanService.doClean(table);
                logger.info("\u7ed3\u675f\u6e05\u9664\u70ed\u70b9\u8868{}\u6570\u636e", (Object)table.getCode());
            }
            catch (Exception e) {
                logger.error("\u7ed3\u675f\u6e05\u9664\u70ed\u70b9\u8868{}\u6570\u636e\u51fa\u73b0\u9519\u8bef:{}\uff0c\u81ea\u52a8\u8df3\u8fc7", table.getCode(), e.getMessage(), e);
            }
        }
    }
}

