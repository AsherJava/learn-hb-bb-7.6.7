/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 */
package com.jiuqi.va.bizmeta.dao;

import com.jiuqi.va.bizmeta.domain.bizres.BizResGroupDO;
import com.jiuqi.va.bizmeta.domain.bizres.BizResGroupDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IBizResGroupDao
extends BaseOptMapper<BizResGroupDO> {
    @Select(value={"select * from BIZ_RES_GROUP where BIZ_RES_GROUP.PARENTNAME = #{name}"})
    public List<BizResGroupDTO> selectChildren(BizResGroupDO var1);
}

