/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.batchupdate.MetaDataBatchUpdateProgress
 */
package com.jiuqi.va.bizmeta.service;

import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.batchupdate.MetaDataBatchUpdateProgress;

public interface MetaDataUpdateService {
    public MetaDataBatchUpdateProgress batchUpdate();

    public MetaDataBatchUpdateProgress getProgress();

    public boolean isUpdating();

    public boolean isDeploying();

    public boolean isControllerUpdating();

    public MetaDataBatchUpdateProgress singleUpdate(MetaInfoDTO var1);
}

