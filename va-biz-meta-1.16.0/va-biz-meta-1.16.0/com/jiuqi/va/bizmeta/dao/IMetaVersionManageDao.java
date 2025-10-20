/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.mapper.common.CustomOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.bizmeta.dao;

import com.jiuqi.va.bizmeta.domain.metaversion.MetaVersionManageDTO;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.mapper.common.CustomOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface IMetaVersionManageDao
extends CustomOptMapper {
    @SelectProvider(method="getMetaVersionInfos", type=IMetaVersionManageDaoProvider.class)
    public List<MetaVersionManageDTO> getMetaVersionInfos(MetaInfoDO var1);

    public static class IMetaVersionManageDaoProvider {
        public String getMetaVersionInfos(MetaInfoDO param) {
            SQL sql = new SQL();
            sql.SELECT("a.ID, b.CREATETIME, b.USERNAME, b.INFO");
            sql.FROM("META_INFO_VERSION a");
            sql.LEFT_OUTER_JOIN("META_VERSION b on a.VERSIONNO = b.VERSIONNO");
            sql.WHERE("a.UNIQUECODE = #{uniqueCode}");
            sql.ORDER_BY("b.CREATETIME DESC");
            return sql.toString();
        }
    }
}

