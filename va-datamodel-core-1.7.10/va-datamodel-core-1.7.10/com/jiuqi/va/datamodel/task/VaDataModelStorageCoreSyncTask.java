/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.va.datamodel.task;

import com.jiuqi.va.datamodel.storage.DataModelGroupStorage;
import com.jiuqi.va.datamodel.storage.DataModelMaintainStorage;
import com.jiuqi.va.datamodel.storage.DataModelPublishedStorage;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import org.springframework.stereotype.Component;

@Component
public class VaDataModelStorageCoreSyncTask
implements StorageSyncTask {
    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        DataModelPublishedStorage.init(tenantName);
        DataModelMaintainStorage.init(tenantName);
        DataModelGroupStorage.init(tenantName);
    }

    public int getSortNum() {
        return Integer.MIN_VALUE;
    }

    public String getVersion() {
        return "20220705-1603";
    }
}

