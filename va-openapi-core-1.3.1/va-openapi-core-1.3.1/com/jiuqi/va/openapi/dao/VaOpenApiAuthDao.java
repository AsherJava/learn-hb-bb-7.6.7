/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.openapi.dao;

import com.jiuqi.va.mapper.common.BaseOptMapper;
import com.jiuqi.va.openapi.domain.OpenApiAuthDO;
import com.jiuqi.va.openapi.domain.OpenApiAuthDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface VaOpenApiAuthDao
extends BaseOptMapper<OpenApiAuthDO> {
    @SelectProvider(type=SqlProvider.class, method="countByCondition")
    public int count(OpenApiAuthDTO var1);

    @SelectProvider(type=SqlProvider.class, method="findByCondition")
    public List<OpenApiAuthDO> list(OpenApiAuthDTO var1);

    public static class SqlProvider {
        public String findByCondition(OpenApiAuthDTO param) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("openapi_auth");
            this.condition(sql, param);
            sql.ORDER_BY("clientid,openid");
            return sql.toString();
        }

        public String countByCondition(OpenApiAuthDTO param) {
            SQL sql = new SQL();
            sql.SELECT("count(0)");
            sql.FROM("openapi_auth");
            this.condition(sql, param);
            return sql.toString();
        }

        public void condition(SQL sql, OpenApiAuthDTO param) {
            if (param.getId() != null) {
                sql.WHERE("id = #{id}");
            }
            if (StringUtils.hasText(param.getClientid())) {
                sql.WHERE("clientid = #{clientid}");
            }
            if (StringUtils.hasText(param.getOpenid())) {
                sql.WHERE("openid = #{openid}");
            }
            if (StringUtils.hasText(param.getRandomcode())) {
                sql.WHERE("randomcode = #{randomcode}");
            }
            if (param.getStopflag() != null) {
                sql.WHERE("stopflag = #{stopflag}");
            }
            if (StringUtils.hasText(param.getSearchKey())) {
                String likeSql = "concat(concat('%', #{searchKey, jdbcType=VARCHAR}),'%')";
                ((SQL)sql.AND()).WHERE("clientid like " + likeSql + " or clienttitle like " + likeSql);
            }
        }
    }
}

