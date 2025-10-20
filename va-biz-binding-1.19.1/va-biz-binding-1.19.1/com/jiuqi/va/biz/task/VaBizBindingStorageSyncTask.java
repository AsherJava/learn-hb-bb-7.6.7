/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.va.biz.task;

import com.jiuqi.va.biz.storage.ABizStorage;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaBizBindingStorageSyncTask
implements StorageSyncTask {
    @Autowired
    private List<ABizStorage> bizStorages;

    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        for (ABizStorage aStorage : this.bizStorages) {
            aStorage.init(tenantName);
        }
    }
}

