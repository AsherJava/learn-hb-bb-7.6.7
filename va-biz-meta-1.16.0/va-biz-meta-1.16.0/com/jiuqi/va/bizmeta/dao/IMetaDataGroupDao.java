/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 */
package com.jiuqi.va.bizmeta.dao;

import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import com.jiuqi.va.bizmeta.provider.MetaGroupSqlProvider;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface IMetaDataGroupDao
extends BaseOptMapper<MetaGroupDO> {
    @SelectProvider(type=MetaGroupSqlProvider.class, method="getChildGroupList")
    public List<MetaGroupDO> getChildGroupList(MetaGroupDO var1);
}

