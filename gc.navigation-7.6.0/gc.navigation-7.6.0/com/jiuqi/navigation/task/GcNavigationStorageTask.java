/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.navigation.task;

import com.jiuqi.navigation.storage.NavigationTableStorage;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GcNavigationStorageTask
implements StorageSyncTask {
    private Logger logger = LoggerFactory.getLogger(GcNavigationStorageTask.class);

    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        try {
            NavigationTableStorage.init(tenantName);
        }
        catch (Exception e) {
            this.logger.error("\u529f\u80fd\u5bfc\u822a\u8868\u521d\u59cb\u5316\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }

    public int getSortNum() {
        return Integer.MAX_VALUE;
    }

    public String getVersion() {
        return "20220802-1601";
    }
}

