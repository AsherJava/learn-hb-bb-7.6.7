/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.callback;

import com.jiuqi.gcreport.bde.fetchsetting.impl.callback.service.FetchSchemeOrdinalUpdateService;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FetchSchemeOrdinalUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FetchSchemeOrdinalUpdate.class);

    public void execute(DataSource dataSource) throws Exception {
        logger.info("\u5f00\u59cb\u4fee\u590dBDE\u53d6\u6570\u65b9\u6848\u62a5\u8868\u4e1a\u52a1\u7c7b\u578b\u6392\u5e8f\u5b57\u6bb5");
        ((FetchSchemeOrdinalUpdateService)ApplicationContextRegister.getBean(FetchSchemeOrdinalUpdateService.class)).doUpdate();
        logger.info("BDE\u53d6\u6570\u65b9\u6848\u62a5\u8868\u4e1a\u52a1\u7c7b\u578b\u6392\u5e8f\u5b57\u6bb5\u4fee\u590d\u5b8c\u6210");
    }
}

