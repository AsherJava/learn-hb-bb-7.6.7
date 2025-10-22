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

public class CustomFetchOptimizeRuleUpdate
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(DataSource dataSource) throws Exception {
        FloatFetchSettingUpdateService floatFetchSettingUpdateService = (FloatFetchSettingUpdateService)ApplicationContextRegister.getBean(FloatFetchSettingUpdateService.class);
        this.logger.error("\u4fee\u590d\u8bbe\u8ba1\u671f\u6570\u636e");
        floatFetchSettingUpdateService.updateCustomFetchOptionByTableName("BDE_FETCHSETTING_DES");
        this.logger.error("\u4fee\u590d\u8fd0\u884c\u671f\u6570\u636e");
        floatFetchSettingUpdateService.updateCustomFetchOptionByTableName("BDE_FETCHSETTING");
    }
}

