/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 */
package com.jiuqi.va.bizmeta.dao;

import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoHistoryDO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IMetaDataInfoVersionDao
extends BaseOptMapper<MetaInfoHistoryDO> {
    @Select(value={"select * from META_INFO_VERSION where UNIQUECODE=#{uniqueCode} ORDER BY versionNO DESC"})
    public List<MetaInfoHistoryDO> getMetaInfoAllByUniqueCode(MetaInfoHistoryDO var1);

    @Select(value={"SELECT MIN(VERSIONNO) AS VERSIONNO, UNIQUECODE FROM META_INFO_VERSION where UNIQUECODE in (SELECT UNIQUECODE FROM META_INFO where MODELNAME = #{modelName}) group by UNIQUECODE ORDER BY VERSIONNO"})
    public List<MetaInfoHistoryDO> selectFirstPublishedByModelName(MetaInfoHistoryDO var1);
}

