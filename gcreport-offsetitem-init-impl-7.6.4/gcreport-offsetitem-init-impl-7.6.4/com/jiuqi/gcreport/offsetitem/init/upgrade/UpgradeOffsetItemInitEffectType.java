/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.offsetitem.init.upgrade;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class UpgradeOffsetItemInitEffectType
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(DataSource dataSource) throws Exception {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        this.logger.info("\u5f00\u59cb\u540c\u6b65\u5206\u5f55\u521d\u59cb\u5316\u6570\u636e\u5efa\u6a21");
        ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_OFFSETVCHRITEM_INIT");
        this.logger.info("\u5f00\u59cb\u4fee\u590d\u5f71\u54cd\u4e0b\u5e74\u5386\u53f2\u6570\u636e");
        this.repairEffectTypeData(jdbcTemplate);
    }

    private void repairEffectTypeData(JdbcTemplate jdbcTemplate) {
        String sql = "UPDATE GC_OFFSETVCHRITEM_INIT \n      SET EFFECTTYPE  =  'effectLongTerm' \n    WHERE 1 = 1 ";
        jdbcTemplate.update(sql);
    }
}

