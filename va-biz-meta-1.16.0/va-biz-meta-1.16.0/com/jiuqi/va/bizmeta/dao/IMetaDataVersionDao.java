/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 */
package com.jiuqi.va.bizmeta.dao;

import com.jiuqi.va.bizmeta.domain.metaversion.MetaDataVersionDO;
import com.jiuqi.va.bizmeta.domain.metaversion.MetaDataVersionDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IMetaDataVersionDao
extends BaseOptMapper<MetaDataVersionDO> {
    @Select(value={"SELECT MAX(VERSIONNO) FROM META_VERSION"})
    public Long selectMaxVersion(MetaDataVersionDTO var1);
}

