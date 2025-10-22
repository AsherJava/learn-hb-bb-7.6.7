/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgCategoryDTO
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.system.check.datachange.provider.org.impl;

import com.jiuqi.nr.system.check.common.DBUtils;
import com.jiuqi.nr.system.check.datachange.provider.org.OrgDataUpper;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgCategoryDTO;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrgDataSQLUpper
implements OrgDataUpper {
    private static final Logger logger = LoggerFactory.getLogger(OrgDataSQLUpper.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private OrgDataClient orgDataClient;

    @Override
    public void doUpper() {
        OrgCategoryDO category = new OrgCategoryDO();
        PageVO list = this.orgCategoryClient.list(category);
        List rows = list.getRows();
        int count = rows.size();
        int i = 1;
        for (OrgCategoryDO orgCategory : rows) {
            logger.info("\u673a\u6784\u4ee3\u7801\u8f6c\u5927\u5199\u5f00\u59cb\u6267\u884c\uff0c\u5f53\u524d\uff1a{}\uff0c \u7b2c{}/{}", orgCategory.getName(), i++, count);
            String tableName = orgCategory.getName();
            try {
                this.upperOrgData(tableName, "code");
                this.upperOrgData(tableName, "orgcode");
                this.upperOrgData(tableName, "parentcode");
                this.upperOrgData(tableName, "parents");
            }
            catch (Exception e) {
                logger.error("\u673a\u6784{}\u5347\u7ea7\u5f02\u5e38:{}", orgCategory.getName(), e.getMessage(), e);
            }
            logger.info("\u673a\u6784{}\u4ee3\u7801\u8f6c\u6362\u5b8c\u6bd5", (Object)orgCategory.getName());
        }
        OrgCategoryDTO orgCatDTO = new OrgCategoryDTO();
        this.orgDataClient.cleanCache(orgCatDTO);
    }

    private void upperOrgData(String tableName, String filed) {
        String upper_code = String.format("UPDATE %s SET %s = UPPER(%s) where %s", tableName, filed, filed, DBUtils.buildCondition(filed));
        logger.info("\u5347\u7ea7\u5c5e\u6027{},SQL:{}", (Object)filed, (Object)upper_code);
        int update = this.jdbcTemplate.update(upper_code);
        logger.info("\u5c5e\u6027{}\u5347\u7ea7\u7ed3\u679c:{}", (Object)filed, (Object)update);
    }
}

