/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 */
package com.jiuqi.va.bizmeta.dao;

import com.jiuqi.va.bizmeta.domain.metaauth.MetaAuthDO;
import com.jiuqi.va.bizmeta.domain.metaauth.MetaAuthDTO;
import com.jiuqi.va.bizmeta.provider.MetaDataAuthSqlProvider;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface IMetaDataAuthDao
extends BaseOptMapper<MetaAuthDO> {
    @SelectProvider(type=MetaDataAuthSqlProvider.class, method="queryMetaAuth")
    public List<MetaAuthDO> queryMetaAuth(MetaAuthDTO var1);

    @SelectProvider(type=MetaDataAuthSqlProvider.class, method="getMetaAuth")
    public MetaAuthDO getMetaAuth(MetaAuthDTO var1);

    @SelectProvider(type=MetaDataAuthSqlProvider.class, method="listAuthority")
    public List<MetaAuthDO> listAuthority(MetaAuthDTO var1);
}

