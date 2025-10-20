/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.bill.dao;

import com.jiuqi.va.bill.domain.BillActionAuthDO;
import com.jiuqi.va.bill.domain.BillActionAuthDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface BillActionAuthDAO
extends BaseOptMapper<BillActionAuthDO> {
    @SelectProvider(type=SqlProvider.class, method="listAuthority")
    public List<BillActionAuthDO> listAuthority(BillActionAuthDTO var1);

    public static class SqlProvider {
        public String listAuthority(BillActionAuthDTO param) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("AUTH_BILL_ACTION");
            sql.WHERE("definename = #{definename}");
            if (param.getActname() != null) {
                sql.WHERE("actname = #{actname}");
            }
            if (param.getAuthtype() != null) {
                sql.WHERE("authtype = #{authtype}");
            }
            if (param.getBiztype() != null) {
                sql.WHERE("biztype = #{biztype}");
            }
            if (param.getBizname() != null) {
                sql.WHERE("bizname = #{bizname}");
            }
            if (param.getSqlCondition() != null) {
                sql.WHERE(param.getSqlCondition());
            }
            sql.WHERE("authflag in(1,2)");
            return sql.toString();
        }
    }
}

