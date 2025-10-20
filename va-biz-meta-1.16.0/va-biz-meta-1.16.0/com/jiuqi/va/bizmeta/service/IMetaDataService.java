/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.MetaDataDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaDesignDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.bizmeta.service;

import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataEditionDO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataHistoryDO;
import com.jiuqi.va.domain.biz.MetaDataDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaDesignDTO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestBody;

public interface IMetaDataService {
    public com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO getMetaDataById(UUID var1);

    public com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO updateMetaData(com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO var1);

    public R checkMetaData(MetaDataDTO var1);

    public int deleteMetaDataById(UUID var1);

    public com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO createMetaData(com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO var1);

    public com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO getMetaDataHistoryById(UUID var1);

    public MetaDataEditionDO getMetaDataUserById(UUID var1);

    public int deleteMetaDataVById(UUID var1);

    public MetaDataDO createMetaVData(MetaDataDO var1);

    public MetaDataHistoryDO createMetaHData(MetaDataHistoryDO var1);

    public com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO getMetaDataVById(UUID var1);

    public R getDatasByMetaType(@RequestBody TenantDO var1);

    public List<String> getPublishedDatasByMetaType(@RequestBody TenantDO var1);

    public MetaInfoDTO getMetaDesignByUniqueCode(MetaDesignDTO var1) throws Exception;
}

