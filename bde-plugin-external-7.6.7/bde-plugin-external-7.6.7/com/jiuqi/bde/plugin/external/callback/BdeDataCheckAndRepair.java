/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.dc.base.common.definition.service.ITableCheckService
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.bde.plugin.external.callback;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.dc.base.common.definition.service.ITableCheckService;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BdeDataCheckAndRepair
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger("com.jiuqi.dc.mappingscheme.impl.service.tableCheckAndSchemeUpdate");

    public void execute(DataSource dataSource) throws Exception {
        try {
            logger.info("BDE\u6267\u884c\u6570\u636e\u68c0\u67e5\u4fee\u590d\uff0c\u5f00\u59cb\u6267\u884c......");
            ITableCheckService tableCheckService = (ITableCheckService)SpringContextUtils.getBean(ITableCheckService.class);
            if (tableCheckService == null) {
                logger.info("BDE\u6267\u884c\u6570\u636e\u68c0\u67e5\u4fee\u590d\uff0c\u6ca1\u6709\u83b7\u53d6\u5230\u4fee\u590d\u4e1a\u52a1\u63a5\u53e3\uff0c\u6267\u884c\u7ed3\u675f......");
                return;
            }
            tableCheckService.tableCheck(null, Boolean.TRUE.booleanValue());
            logger.info("BDE\u6267\u884c\u6570\u636e\u68c0\u67e5\u4fee\u590d\uff0c\u5b8c\u6210\u6267\u884c......");
        }
        catch (Exception e) {
            logger.info("BDE\u6267\u884c\u6570\u636e\u68c0\u67e5\u4fee\u590d\uff0c\u51fa\u73b0\u9519\u8bef\uff0c\u8be6\u7ec6\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
    }
}

