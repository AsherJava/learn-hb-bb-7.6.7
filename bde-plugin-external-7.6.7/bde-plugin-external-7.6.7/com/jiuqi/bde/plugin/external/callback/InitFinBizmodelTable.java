/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.bde.plugin.external.callback;

import com.jiuqi.bde.plugin.external.callback.service.InitFinBizmodelTableService;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitFinBizmodelTable
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(InitFinBizmodelTable.class);

    public void execute(DataSource dataSource) throws Exception {
        InitFinBizmodelTableService finBizModelTableService = (InitFinBizmodelTableService)ApplicationContextRegister.getBean(InitFinBizmodelTableService.class);
        if (finBizModelTableService == null) {
            logger.info("\u6ca1\u6709\u83b7\u53d6\u5230\u63a5\u53e3\u5b9e\u73b0\u7c7b\uff0c\u8df3\u8fc7\u521d\u59cb\u5316\u8d26\u52a1\u6a21\u578b\u6570\u636e");
        } else {
            finBizModelTableService.doInit();
            logger.info("\u521d\u59cb\u5316\u8d26\u52a1\u6a21\u578b\u6570\u636e\u5b8c\u6210");
        }
    }
}

