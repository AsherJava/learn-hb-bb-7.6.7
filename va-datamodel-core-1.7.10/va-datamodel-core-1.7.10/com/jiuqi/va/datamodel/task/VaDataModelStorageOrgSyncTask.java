/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.va.datamodel.task;

import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.task.StorageSyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaDataModelStorageOrgSyncTask
implements StorageSyncTask {
    @Autowired
    private VaDataModelPublishedService publishedService;

    public void execute(String oldVersion) {
        if (oldVersion != null && oldVersion.compareTo("20231211-1637") < 0) {
            DataModelDTO param = new DataModelDTO();
            param.setTenantName(ShiroUtil.getTenantName());
            param.setBiztype(DataModelType.BizType.BASEDATA);
            PageVO<DataModelDO> list = this.publishedService.list(param);
            if (list == null || list.getTotal() == 0) {
                return;
            }
            block0: for (DataModelDO publishedDO : list.getRows()) {
                if (!publishedDO.getName().equals("MD_ORG") && !publishedDO.getName().startsWith("MD_ORG_")) continue;
                for (DataModelColumn column : publishedDO.getColumns()) {
                    if (!column.getColumnName().equals("ORGCODE") || !column.getColumnTitle().equals("\u673a\u6784\u7f16\u7801")) continue;
                    column.setColumnTitle("\u663e\u793a\u4ee3\u7801");
                    this.publishedService.push(publishedDO);
                    continue block0;
                }
            }
        }
    }

    public int getSortNum() {
        return -2147483647;
    }

    public String getVersion() {
        return "20231211-1637";
    }
}

