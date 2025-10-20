/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.basedata.dao;

import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface VaBaseDataGroupDao
extends BaseOptMapper<BaseDataGroupDO> {
    @SelectProvider(type=BaseDataGroupSqlProvider.class, method="count")
    public int count(BaseDataGroupDTO var1);

    @SelectProvider(type=BaseDataGroupSqlProvider.class, method="query")
    public List<BaseDataGroupDO> list(BaseDataGroupDTO var1);

    public static class BaseDataGroupSqlProvider {
        public String count(BaseDataGroupDTO param) {
            SQL sql = new SQL();
            sql.SELECT("count(ID) as cnt");
            sql.FROM("BASEDATA_GROUP");
            this.condition(sql, param);
            sql.ORDER_BY("ORDERNUM");
            return sql.toString();
        }

        public String query(BaseDataGroupDTO param) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("BASEDATA_GROUP");
            this.condition(sql, param);
            sql.ORDER_BY("ORDERNUM");
            return sql.toString();
        }

        private void condition(SQL sql, BaseDataGroupDTO param) {
            if (param.getId() != null) {
                sql.WHERE("ID=#{id}");
            }
            if (param.getName() != null) {
                sql.WHERE("NAME=#{name}");
            }
            if (param.getParentname() != null) {
                if ("-".equals(param.getParentname())) {
                    ((SQL)sql.AND()).WHERE("PARENTNAME='root' or parentname='-'");
                } else {
                    sql.WHERE("PARENTNAME=#{parentname}");
                }
            }
            if (StringUtils.hasText(param.getSearchKey())) {
                String likeSql = "concat(concat('%', #{searchKey, jdbcType=VARCHAR}),'%')";
                ((SQL)sql.AND()).WHERE("NAME like " + likeSql + " or TITLE like " + likeSql);
            }
        }
    }
}

