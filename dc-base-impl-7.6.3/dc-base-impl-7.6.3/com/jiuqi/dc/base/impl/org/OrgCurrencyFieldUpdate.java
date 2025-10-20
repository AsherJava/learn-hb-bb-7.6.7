/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.organization.domain.ZBDTO
 *  com.jiuqi.va.organization.service.OrgCategoryService
 */
package com.jiuqi.dc.base.impl.org;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.dc.base.impl.org.OrgCurrencyFieldInitUtil;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.organization.domain.ZBDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.util.Collection;
import java.util.List;
import javax.sql.DataSource;

public class OrgCurrencyFieldUpdate
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        OrgCurrencyFieldInitUtil.syncOrgCurrencyField();
        OrgCategoryService categoryService = (OrgCategoryService)SpringContextUtils.getBean(OrgCategoryService.class);
        OrgCategoryDO categoryDO = new OrgCategoryDO();
        categoryDO.setName("MD_ORG");
        List orgZbList = categoryService.listZB(categoryDO);
        if (CollectionUtils.isEmpty((Collection)orgZbList)) {
            return;
        }
        for (ZBDTO zbdto : orgZbList) {
            if (!"REPCURRCODE".equals(zbdto.getName()) && !"FINCURR".equals(zbdto.getName()) && !"FINCURR2".equals(zbdto.getName())) continue;
            zbdto.setTitle("\u3010\u5e9f\u5f03\u3011" + zbdto.getTitle());
            categoryService.updateZB(zbdto);
        }
    }
}

