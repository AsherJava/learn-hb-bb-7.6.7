/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.TableDefinitionService
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.dc.integration.execute.impl.executor;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.TableDefinitionService;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitConvertLogEo
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(InitConvertLogEo.class);

    public void execute(DataSource dataSource) throws Exception {
        TableDefinitionService definitionService = (TableDefinitionService)SpringContextUtils.getBean(TableDefinitionService.class);
        try {
            definitionService.initTableDefineByTableName("DC_LOG_DATACONVERT");
        }
        catch (Exception e) {
            logger.error("\u6267\u884cDC_LOG_DATACONVERT\u8868\u5973\u5a32\u5efa\u6a21\u5931\u8d25\uff1a{}", (Object)e.getMessage());
        }
    }
}

