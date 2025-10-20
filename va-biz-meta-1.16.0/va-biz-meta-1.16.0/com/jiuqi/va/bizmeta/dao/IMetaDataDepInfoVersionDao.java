/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.CustomOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.bizmeta.dao;

import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoHistoryDO;
import com.jiuqi.va.mapper.common.CustomOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface IMetaDataDepInfoVersionDao
extends CustomOptMapper {
    @SelectProvider(method="getMetaInfoAllByUniqueCode", type=IMetaDataInfoVersionDaoProvider.class)
    public List<MetaInfoHistoryDO> getMetaInfoAllByUniqueCode(MetaInfoHistoryDO var1);

    public static class IMetaDataInfoVersionDaoProvider {
        public String getMetaInfoAllByUniqueCode(MetaInfoHistoryDO metaInfoHistoryDO) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("META_INFO_VERSION t1");
            sql.LEFT_OUTER_JOIN("META_VERSION t2 on t1.VERSIONNO = t2.VERSIONNO");
            sql.WHERE("t1.UNIQUECODE = #{uniqueCode}");
            sql.ORDER_BY("t1.versionNO DESC");
            return sql.toString();
        }
    }
}

