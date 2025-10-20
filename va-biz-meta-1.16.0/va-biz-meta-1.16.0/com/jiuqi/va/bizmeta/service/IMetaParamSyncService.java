/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.MetaDataDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMainfestDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMetaGroupDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncParamDO
 *  com.jiuqi.va.paramsync.domain.VaParamSyncResponseDO
 */
package com.jiuqi.va.bizmeta.service;

import com.jiuqi.va.domain.biz.MetaDataDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncMainfestDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncMetaGroupDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncParamDO;
import com.jiuqi.va.paramsync.domain.VaParamSyncResponseDO;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IMetaParamSyncService {
    public VaParamSyncResponseDO export(VaParamSyncParamDO var1);

    public List<VaParamSyncMetaGroupDO> getImportGroups(String var1, VaParamSyncMainfestDO var2);

    public R importParam(VaParamSyncParamDO var1, Map<String, byte[]> var2);

    public void updateByPrimaryKeySelective(MetaInfoEditionDO var1);

    public MetaInfoEditionDO getMetaInfoEdition(MetaInfoEditionDO var1);

    public MetaInfoDO queryMetaInfo(MetaInfoDO var1);

    public void insertInfoEdition(MetaInfoEditionDO var1);

    public void doPublish(UUID var1, MetaInfoDO var2, MetaInfoEditionDO var3);

    public MetaDataDTO createMetaData(MetaInfoDTO var1);

    public void updateMetaData(MetaInfoDTO var1);
}

