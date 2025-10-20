/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gcreport.reportdatasync.updata;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportUploadTableInit
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(DataSource dataSource) {
        try {
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_DATASYNC_SERVERINFO");
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_DATASYNC_SERVERLIST");
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_PARAMSYNC_RECEIVE");
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_PARAMSYNC_RECEIVE_LOG");
        }
        catch (Exception e) {
            this.logger.error("\u591a\u7ea7\u90e8\u7f72\u521d\u59cb\u5316\u8868\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }
}

