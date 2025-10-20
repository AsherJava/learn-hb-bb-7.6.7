/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.web.util.BusinessLogUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.organization.service.OrgCategoryService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.org.impl.callback;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.web.util.BusinessLogUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrgTypeIdNameUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(OrgTypeIdNameUpdate.class);

    public void execute(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        OrgCategoryService orgCategoryService = (OrgCategoryService)SpringContextUtils.getBean(OrgCategoryService.class);
        List list = jdbcTemplate.queryForList("select c.name from MD_ORG_CATEGORY c");
        List orgTypes = list.stream().map(o -> (String)o.get("name")).collect(Collectors.toList());
        for (String orgType : orgTypes) {
            OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
            orgCategoryDO.setTenantName("__default_tenant__");
            orgCategoryDO.setName(orgType);
            OrgCategoryDO categoryDO = orgCategoryService.get(orgCategoryDO);
            categoryDO.getZbs().forEach(zb -> {
                if (zb.getName().equalsIgnoreCase("ORGTYPEID")) {
                    zb.setTitle("\u5408\u5e76\u5355\u4f4d\u7c7b\u578b");
                }
            });
            try {
                R update = orgCategoryService.update(categoryDO);
                if (update.getCode() == 0) continue;
                BusinessLogUtils.operate((String)"\u5347\u7ea7\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\u6807\u9898", (String)"\u4fee\u6539\u540d\u79f0", (String)("\u4fee\u6539\u5931\u8d25" + update.getMsg()));
            }
            catch (Exception e) {
                logger.error("\u5347\u7ea7\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\u6807\u9898\u5931\u8d25", e);
                BusinessLogUtils.operate((String)"\u5347\u7ea7\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\u6807\u9898", (String)"\u4fee\u6539\u540d\u79f0", (String)("\u4fee\u6539\u5931\u8d25" + e.getMessage()));
            }
        }
    }
}

