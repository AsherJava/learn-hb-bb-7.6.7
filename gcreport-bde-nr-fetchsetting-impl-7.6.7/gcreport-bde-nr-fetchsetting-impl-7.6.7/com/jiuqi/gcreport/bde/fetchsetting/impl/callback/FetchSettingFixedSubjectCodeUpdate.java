/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.callback;

import com.jiuqi.gcreport.bde.fetchsetting.impl.callback.service.FetchSettingFixedSubjectCodeUpdateService;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FetchSettingFixedSubjectCodeUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FetchSettingFixedSubjectCodeUpdate.class);

    public void execute(DataSource dataSource) throws Exception {
        logger.info("\u5f00\u59cb\u4fee\u590d\u8bbe\u8ba1\u671fBDE\u53d6\u6570\u8bbe\u7f6e\u79d1\u76ee\u8303\u56f4");
        ((FetchSettingFixedSubjectCodeUpdateService)ApplicationContextRegister.getBean(FetchSettingFixedSubjectCodeUpdateService.class)).doUpdate("BDE_FETCHSETTING_DES");
        logger.info("\u8bbe\u8ba1\u671fBDE\u53d6\u6570\u8bbe\u7f6e\u79d1\u76ee\u8303\u56f4\u4fee\u590d\u5b8c\u6210");
        logger.info("\u5f00\u59cb\u4fee\u590d\u8fd0\u884c\u671fBDE\u53d6\u6570\u8bbe\u7f6e\u79d1\u76ee\u8303\u56f4");
        ((FetchSettingFixedSubjectCodeUpdateService)ApplicationContextRegister.getBean(FetchSettingFixedSubjectCodeUpdateService.class)).doUpdate("BDE_FETCHSETTING");
        logger.info("\u8fd0\u884c\u671fBDE\u53d6\u6570\u8bbe\u7f6e\u79d1\u76ee\u8303\u56f4\u4fee\u590d\u5b8c\u6210");
    }
}

