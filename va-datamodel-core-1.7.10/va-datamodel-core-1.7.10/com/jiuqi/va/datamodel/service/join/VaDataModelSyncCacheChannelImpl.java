/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.context.DataModelSyncCacheChannel
 *  com.jiuqi.va.context.DataModelSyncCacheDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.datamodel.service.join;

import com.jiuqi.va.context.DataModelSyncCacheChannel;
import com.jiuqi.va.context.DataModelSyncCacheDTO;
import com.jiuqi.va.datamodel.service.impl.help.VaDataModelCacheService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=-2147483648)
public class VaDataModelSyncCacheChannelImpl
implements DataModelSyncCacheChannel {
    private VaDataModelCacheService dataModelCacheService;

    public void execute(DataModelSyncCacheDTO dmsc) {
        if (this.dataModelCacheService == null) {
            this.dataModelCacheService = (VaDataModelCacheService)ApplicationContextRegister.getBean(VaDataModelCacheService.class);
        }
        if (this.dataModelCacheService == null) {
            return;
        }
        this.dataModelCacheService.handleSyncCacheMsg(dmsc);
    }
}

