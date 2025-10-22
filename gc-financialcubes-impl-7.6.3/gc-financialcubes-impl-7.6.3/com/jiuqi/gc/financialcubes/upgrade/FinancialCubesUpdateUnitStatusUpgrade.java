/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gc.financialcubes.upgrade;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;

public class FinancialCubesUpdateUnitStatusUpgrade
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        DefinitionAutoCollectionService definitionAutoCollectionService = (DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class);
        definitionAutoCollectionService.initTableDefineByTableName("GC_FINANCIAL_UNIT_STATUS");
        definitionAutoCollectionService.initTableDefineByTableName("GC_FINANCIAL_GROUP_STATUS");
    }
}

