/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bizmeta.service;

import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.domain.metaupdate.MetaDataUpdateDTO;
import com.jiuqi.va.bizmeta.domain.metaversion.MetaDataVersionDTO;
import com.jiuqi.va.bizmeta.domain.metaversion.MetaVersionManageDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import java.util.UUID;

public interface IMetaVersionService {
    public long getMaxVersion(MetaDataVersionDTO var1);

    public MetaDataVersionDTO addMetaVersionInfo(MetaDataVersionDTO var1);

    public R updatePublished(MetaDataDTO var1, MetaInfoDTO var2);

    public List<MetaInfoDTO> getMetaInfoAllByUniqueCode(String var1);

    public R gatherMetaInfoVersionNos(TenantDO var1);

    public MetaDataDTO getDesignDataById(TenantDO var1);

    public void updatePublished(UUID var1, MetaDataDTO var2) throws Exception;

    public MetaDataUpdateDTO updateMetaHistoryBatch(TenantDO var1) throws Exception;

    public List<MetaVersionManageDTO> getMetaVersionInfos(String var1);
}

