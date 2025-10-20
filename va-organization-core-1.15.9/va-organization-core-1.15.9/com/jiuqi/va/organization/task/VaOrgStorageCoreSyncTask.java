/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.task.StorageSyncTask
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.organization.task;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.storage.InitMdOrgStorage;
import com.jiuqi.va.organization.storage.OrgActionAuthStorage;
import com.jiuqi.va.organization.storage.OrgAuthStorage;
import com.jiuqi.va.organization.storage.OrgCategoryStorage;
import com.jiuqi.va.organization.storage.OrgImportTemplateStorage;
import com.jiuqi.va.organization.storage.OrgVersionStorage;
import org.springframework.stereotype.Component;

@Component
public class VaOrgStorageCoreSyncTask
implements StorageSyncTask {
    public void execute(String oldVersion) {
        this.initStructure();
        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        this.initData(oldVersion);
    }

    public void initStructure() {
        String tenantName = ShiroUtil.getTenantName();
        OrgCategoryStorage.init(tenantName);
        OrgVersionStorage.init(tenantName);
        OrgAuthStorage.init(tenantName);
        OrgActionAuthStorage.init(tenantName);
        OrgImportTemplateStorage.init(tenantName);
    }

    public void initData(String oldVersion) {
        String tenantName = ShiroUtil.getTenantName();
        if (oldVersion == null) {
            InitMdOrgStorage.init(tenantName);
        }
        if (oldVersion != null && oldVersion.compareTo("20240709-1531") < 0) {
            OrgCategoryService orgCategoryService = (OrgCategoryService)ApplicationContextRegister.getBean(OrgCategoryService.class);
            JDialectUtil jDialectUtil = JDialectUtil.getInstance();
            JTableModel jtm = new JTableModel(tenantName, null);
            OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
            orgCategoryDO.setTenantName(tenantName);
            PageVO<OrgCategoryDO> list = orgCategoryService.list(orgCategoryDO);
            for (OrgCategoryDO data : list.getRows()) {
                jtm.setTableName(data.getName());
                jtm.column("SHORTNAME").NVARCHAR(Integer.valueOf(100));
                try {
                    jDialectUtil.updateTable(jtm);
                }
                catch (JTableException jTableException) {}
            }
        }
    }

    public int getSortNum() {
        return -2147482648;
    }

    public String getVersion() {
        return "20240709-1531";
    }
}

