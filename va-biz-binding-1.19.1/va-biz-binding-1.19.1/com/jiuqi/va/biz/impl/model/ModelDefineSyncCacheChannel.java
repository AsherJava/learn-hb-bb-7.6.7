/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.context.DataModelSyncCacheChannel
 *  com.jiuqi.va.context.DataModelSyncCacheDTO
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.biz.impl.model;

import com.jiuqi.va.biz.cache.BaseDataDefineCache;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.context.DataModelSyncCacheChannel;
import com.jiuqi.va.context.DataModelSyncCacheDTO;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value="vaModelDefineSyncCacheChannel")
public class ModelDefineSyncCacheChannel
implements DataModelSyncCacheChannel {
    private static final Logger log = LoggerFactory.getLogger(ModelDefineSyncCacheChannel.class);

    public void execute(DataModelSyncCacheDTO dmsc) {
        DataModelDO dataModel = dmsc.getDataModel();
        if (dataModel == null) {
            return;
        }
        try {
            ModelDefineService modelDefineService = (ModelDefineService)ApplicationContextRegister.getBean(ModelDefineService.class);
            modelDefineService.clearCache(dmsc.getTenantName(), dmsc.getDataModel().getName());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        BaseDataDefineCache.updateBaseDataDefineCache(dmsc.getTenantName(), dataModel);
    }
}

