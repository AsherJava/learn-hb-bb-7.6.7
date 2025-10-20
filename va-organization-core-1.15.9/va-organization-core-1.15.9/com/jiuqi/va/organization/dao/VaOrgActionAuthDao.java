/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.organization.dao;

import com.jiuqi.va.mapper.common.BaseOptMapper;
import com.jiuqi.va.organization.domain.OrgActionAuthDO;
import com.jiuqi.va.organization.domain.OrgActionAuthDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface VaOrgActionAuthDao
extends BaseOptMapper<OrgActionAuthDO> {
    @SelectProvider(type=SqlProvider.class, method="listAuthority")
    public List<OrgActionAuthDO> listAuthority(OrgActionAuthDTO var1);

    public static class SqlProvider {
        public String listAuthority(OrgActionAuthDTO param) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("AUTH_ORG_ACTION");
            sql.WHERE("ORGCATEGORY = #{orgcategory}");
            if (param.getAuthtype() != null) {
                sql.WHERE("AUTHTYPE = #{authtype}");
            }
            if (param.getBiztype() != null) {
                sql.WHERE("BIZTYPE = #{biztype}");
            }
            if (param.getBizname() != null) {
                sql.WHERE("BIZNAME = #{bizname}");
            }
            if (param.getSqlCondition() != null) {
                sql.WHERE(param.getSqlCondition());
            }
            sql.WHERE("AUTHFLAG = 1");
            return sql.toString();
        }
    }
}

