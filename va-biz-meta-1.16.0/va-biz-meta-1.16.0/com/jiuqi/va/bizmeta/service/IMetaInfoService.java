/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.MetaDesignDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.metadeploy.MetaDataDeployDim
 *  com.jiuqi.va.mapper.domain.UpperKeyMap
 */
package com.jiuqi.va.bizmeta.service;

import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoFilterDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoHistoryDO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO;
import com.jiuqi.va.bizmeta.domain.metamodel.MetaModelDTO;
import com.jiuqi.va.domain.meta.MetaDesignDTO;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.metadeploy.MetaDataDeployDim;
import com.jiuqi.va.mapper.domain.UpperKeyMap;
import java.util.List;
import java.util.UUID;

public interface IMetaInfoService {
    public MetaInfoDTO createMeta(String var1, MetaInfoDTO var2);

    public MetaInfoDTO createMeta(String var1, MetaInfoDTO var2, MetaDataDTO var3);

    public MetaInfoDTO copyMedta(UUID var1, MetaInfoDTO var2);

    public boolean checkMetaName(MetaInfoDTO var1);

    public MetaInfoEditionDO deleteMetaById(String var1, UUID var2);

    public void deleteMetaByGroup(String var1, MetaInfoDTO var2);

    public MetaInfoDTO updateMeta(String var1, MetaInfoDTO var2, MetaDataDTO var3);

    public MetaInfoDTO updateMeta(String var1, MetaInfoDTO var2);

    public MetaInfoEditionDO updateMeta(String var1, MetaDataDTO var2);

    public MetaInfoDTO findMeta(String var1, String var2, String var3);

    public MetaInfoDTO findMetaById(UUID var1);

    public MetaInfoDO findMetaEditionById(UUID var1);

    public List<MetaInfoDTO> getMetaList(MetaInfoPageDTO var1);

    public List<MetaInfoEditionDO> getMetaEditionList(MetaModelDTO var1);

    public List<MetaInfoDTO> getMetaEditionListByModule(String var1);

    public List<MetaInfoDO> getMetaListByMetaType(String var1);

    public List<MetaInfoDO> getMetaListFilter(MetaInfoFilterDTO var1);

    public List<MetaInfoDO> getMetaInfoList(MetaInfoDO var1);

    public List<MetaInfoEditionDO> getMetaEditionList(String var1, String var2, String var3);

    public List<MetaInfoHistoryDO> getMetaHistoryList(String var1, String var2, String var3);

    public void deployMetaById(String var1, MetaDataDeployDim var2, long var3);

    public int queryCountByGroupId(UUID var1);

    public List<MetaInfoDO> getMetaVList(MetaInfoPageDTO var1);

    public List<MetaInfoEditionDO> getMetaEditionList(MetaInfoPageDTO var1);

    public MetaInfoDTO getMetaInfoByUniqueCode(String var1);

    public List<MetaInfoHistoryDO> listMetaInfoHis(MetaInfoHistoryDO var1);

    public MetaInfoDTO getMetaInfoHisByCodeAndVer(MetaDesignDTO var1);

    public List<MetaInfoDTO> getMetaInfoListByMetaType(String var1, String var2);

    public MetaInfoDTO findMetaData(String var1, String var2, String var3);

    public Integer restoreMetaInfo(String var1, Integer var2, MetaInfoDTO var3);

    public MetaInfoDTO getMetaInfoAndData(MetaInfoDTO var1);

    public List<UpperKeyMap> getMetaEditionGroup(MetaInfoPageDTO var1);

    public List<UpperKeyMap> getMetaInfoGroup(MetaInfoPageDTO var1);

    public String getFirstPublishedByModelName(MetaInfoHistoryDO var1);
}

