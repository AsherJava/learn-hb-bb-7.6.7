/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.va.billcode.task;

import com.jiuqi.va.billcode.storage.AStorage;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaBillCodeStorageSyncTask
implements StorageSyncTask {
    @Autowired
    private List<AStorage> billCodeStorages;

    public String getVersion() {
        return "20201127-1352";
    }

    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        for (AStorage aStorage : this.billCodeStorages) {
            aStorage.init(tenantName);
        }
    }
}

