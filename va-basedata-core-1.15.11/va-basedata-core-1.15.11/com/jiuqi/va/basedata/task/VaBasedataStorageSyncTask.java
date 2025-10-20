/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.va.basedata.task;

import com.jiuqi.va.basedata.storage.BaseDataCommonlyUsedStorage;
import com.jiuqi.va.basedata.storage.BaseDataInfoStorage;
import com.jiuqi.va.basedata.storage.BasedataImportTemplateStorage;
import com.jiuqi.va.basedata.storage.EnumDataStorage;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import org.springframework.stereotype.Component;

@Component
public class VaBasedataStorageSyncTask
implements StorageSyncTask {
    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        EnumDataStorage.init(tenantName);
        BaseDataInfoStorage.init(tenantName);
        BasedataImportTemplateStorage.init(tenantName);
        BaseDataCommonlyUsedStorage.init(tenantName);
    }

    public int getSortNum() {
        return -2147481648;
    }

    public String getVersion() {
        return "20240412-0915";
    }
}

