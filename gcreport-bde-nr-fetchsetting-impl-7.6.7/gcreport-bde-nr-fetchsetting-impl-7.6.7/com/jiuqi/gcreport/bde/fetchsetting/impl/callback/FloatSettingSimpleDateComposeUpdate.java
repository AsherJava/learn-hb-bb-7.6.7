/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.callback;

import com.jiuqi.gcreport.bde.fetchsetting.impl.callback.service.FloatFetchSettingUpdateService;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloatSettingSimpleDateComposeUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FloatSettingSimpleDateComposeUpdate.class);

    public void execute(DataSource dataSource) throws Exception {
        FloatFetchSettingUpdateService floatFetchSettingUpdateService = (FloatFetchSettingUpdateService)ApplicationContextRegister.getBean(FloatFetchSettingUpdateService.class);
        logger.info("\u5f00\u59cb\u4fee\u590d\u8bbe\u8ba1\u671fBDE\u6d6e\u52a8\u533a\u57df\u8bbe\u7f6e\u4e1a\u52a1\u6a21\u578b\u6570\u636e");
        floatFetchSettingUpdateService.updateQueryConfigInfo("BDE_FETCHFLOATSETTING_DES");
        logger.info("\u8bbe\u8ba1\u671f\u6570\u636e\u4fee\u590d\u5b8c\u6210");
        logger.info("\u5f00\u59cb\u4fee\u590d\u8fd0\u884c\u671fBDE\u6d6e\u52a8\u533a\u57df\u8bbe\u7f6e\u4e1a\u52a1\u6a21\u578b\u6570");
        floatFetchSettingUpdateService.updateQueryConfigInfo("BDE_FETCHFLOATSETTING");
        logger.info("\u8fd0\u884c\u671f\u6570\u636e\u4fee\u590d\u5b8c\u6210");
    }
}

