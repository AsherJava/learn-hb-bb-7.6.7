/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.organization.storage;

import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.service.OrgCategoryService;

public class InitMdOrgStorage {
    public static void init(String tenantName) {
        OrgCategoryService orgCategoryService = (OrgCategoryService)ApplicationContextRegister.getBean(OrgCategoryService.class);
        OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
        orgCategoryDO.setTenantName(tenantName);
        orgCategoryDO.setName("MD_ORG");
        OrgCategoryDO oldOrgCategoryDO = orgCategoryService.get(orgCategoryDO);
        if (oldOrgCategoryDO == null) {
            orgCategoryDO.setTitle("\u884c\u653f\u7ec4\u7ec7");
            orgCategoryService.add(orgCategoryDO);
        } else {
            orgCategoryService.update(oldOrgCategoryDO);
        }
    }
}

