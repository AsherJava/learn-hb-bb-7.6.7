/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gcreport.reportdatasync.runner;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpgradeParamSyncTable
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(DataSource dataSource) {
        try {
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_PARAMSYNC_SCHEME");
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_PARAMSYNC_XF_LOG");
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_PARAMSYNC_RECEIVE");
        }
        catch (Exception e) {
            this.logger.error("\u6570\u636e\u540c\u6b65\u8868\u91cd\u6784\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }
}

