/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 */
package com.jiuqi.va.bizmeta.dao;

import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoFilterDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO;
import com.jiuqi.va.bizmeta.provider.MetaInfoSqlProvider;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface IMetaDataInfoDao
extends BaseOptMapper<MetaInfoDO> {
    @SelectProvider(type=MetaInfoSqlProvider.class, method="selectByGroupName")
    public List<MetaInfoDO> selectByGroupName(MetaInfoPageDTO var1);

    @SelectProvider(type=MetaInfoSqlProvider.class, method="getMetaListFilter")
    public List<MetaInfoDTO> getMetaListFilter(MetaInfoFilterDTO var1);

    @SelectProvider(type=MetaInfoSqlProvider.class, method="getNotIncludedDesignMetaList")
    public List<MetaInfoDTO> getNotIncludedDesignMetaList(MetaInfoFilterDTO var1);

    @SelectProvider(type=MetaInfoSqlProvider.class, method="getMetaInfo")
    public MetaInfoDTO getMetaInfo(MetaInfoFilterDTO var1);
}

