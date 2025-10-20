/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.dc.mappingscheme.impl.service.executor;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.dc.mappingscheme.impl.service.executor.DataMappingUpdateService;
import com.jiuqi.dc.mappingscheme.impl.service.executor.MappingSchemeUpdateService;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataMappingSchemeUpgrade
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger("com.jiuqi.dc.mappingscheme.impl.service.tableCheckAndSchemeUpdate");

    public void execute(DataSource dataSource) throws Exception {
        logger.info("7.6.0\u6570\u636e\u6620\u5c04\u65b9\u6848\u5347\u7ea7\u5f00\u59cb");
        MappingSchemeUpdateService mappingSchemeUpdateInit = (MappingSchemeUpdateService)SpringContextUtils.getBean(MappingSchemeUpdateService.class);
        logger.info("1.\u6570\u636e\u6620\u5c04\u65b9\u6848\u5347\u7ea7\u5f00\u59cb");
        mappingSchemeUpdateInit.execute(null);
        DataMappingUpdateService dataMappingUpdateInit = (DataMappingUpdateService)SpringContextUtils.getBean(DataMappingUpdateService.class);
        logger.info("2.\u6570\u636e\u6620\u5c04\u5347\u7ea7\u5f00\u59cb");
        dataMappingUpdateInit.execute(null);
        logger.info("7.6.0\u6570\u636e\u6620\u5c04\u65b9\u6848\u5347\u7ea7\u7ed3\u675f");
    }
}

