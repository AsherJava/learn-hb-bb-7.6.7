/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Delete
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 */
package com.jiuqi.va.bizmeta.dao;

import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupEditionDO;
import com.jiuqi.va.bizmeta.provider.MetaInfoSqlProvider;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IMetaDataGroupUserDao
extends BaseOptMapper<MetaGroupEditionDO> {
    @DeleteProvider(type=MetaInfoSqlProvider.class, method="deleteGroupByName")
    public int deleteGroupByName(MetaGroupDTO var1);

    @Select(value={"select * from META_GROUP_USER where ID = #{id}"})
    public MetaGroupEditionDO selectById(MetaGroupEditionDO var1);

    @Delete(value={"delete from META_GROUP_USER where ID = #{id}"})
    public int deleteEGroupById(MetaGroupEditionDO var1);
}

