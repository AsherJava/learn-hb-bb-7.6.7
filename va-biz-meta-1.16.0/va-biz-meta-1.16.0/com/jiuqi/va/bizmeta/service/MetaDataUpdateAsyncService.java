/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.meta.batchupdate.MetaDataBatchUpdateProgress
 */
package com.jiuqi.va.bizmeta.service;

import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.meta.batchupdate.MetaDataBatchUpdateProgress;
import java.util.List;

public interface MetaDataUpdateAsyncService {
    public void asyncHandleUpdate(List<MetaInfoDO> var1, List<MetaInfoEditionDO> var2, MetaDataBatchUpdateProgress var3);

    public void syncHandleUpdate(List<MetaInfoDO> var1, List<MetaInfoEditionDO> var2, MetaDataBatchUpdateProgress var3);
}

