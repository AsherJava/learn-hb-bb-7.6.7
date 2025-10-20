/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  com.jiuqi.va.mapper.domain.UpperKeyMap
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 */
package com.jiuqi.va.bizmeta.dao;

import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO;
import com.jiuqi.va.bizmeta.provider.MetaInfoSqlProvider;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import com.jiuqi.va.mapper.domain.UpperKeyMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface IMetaDataInfoUserDao
extends BaseOptMapper<MetaInfoEditionDO> {
    @SelectProvider(type=MetaInfoSqlProvider.class, method="selectEditionByGroupName")
    public List<MetaInfoEditionDO> selectEditionByGroupName(MetaInfoPageDTO var1);

    @SelectProvider(type=MetaInfoSqlProvider.class, method="selectByIndexes")
    public int selectByIndexes(MetaInfoEditionDO var1);

    @SelectProvider(type=MetaInfoSqlProvider.class, method="getMetaEditionGroup")
    public List<UpperKeyMap> getMetaEditionGroup(MetaInfoPageDTO var1);

    @SelectProvider(type=MetaInfoSqlProvider.class, method="getMetaInfoGroup")
    public List<UpperKeyMap> getMetaInfoGroup(MetaInfoPageDTO var1);

    @SelectProvider(type=MetaInfoSqlProvider.class, method="findMaxVersion")
    public Long findMaxVersion(MetaInfoDO var1);
}

