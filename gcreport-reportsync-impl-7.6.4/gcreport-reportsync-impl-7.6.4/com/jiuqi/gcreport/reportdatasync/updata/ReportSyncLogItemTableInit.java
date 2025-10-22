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

public class ReportSyncLogItemTableInit
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(DataSource dataSource) {
        try {
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_PARAMSYNC_XF_LOG_ITEM");
        }
        catch (Exception e) {
            this.logger.error("\u56fd\u8d44\u59d4\u53c2\u6570\u540c\u6b65\u65e5\u5fd7\u660e\u7ec6\u8868\u521b\u5efa\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }
}

