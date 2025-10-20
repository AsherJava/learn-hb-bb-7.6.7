/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.va.openapi.task;

import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jiuqi.va.openapi.storage.OpenApiStorage;
import org.springframework.stereotype.Component;

@Component
public class VaOpenApiStorageSyncTask
implements StorageSyncTask {
    public void execute() {
        OpenApiStorage.init(ShiroUtil.getTenantName());
    }
}

