/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.va.bizmeta.task;

import com.jiuqi.va.bizmeta.storage.AMetaStorage;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaBizMetaStorageSyncTask
implements StorageSyncTask {
    @Autowired
    private List<AMetaStorage> metaStorages;

    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        for (AMetaStorage aMetaStorage : this.metaStorages) {
            aMetaStorage.init(tenantName);
        }
    }

    public String getVersion() {
        return "20230512-0945";
    }
}

