/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.bizmeta.provider;

import com.jiuqi.va.bizmeta.domain.metaauth.MetaAuthDTO;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class MetaDataAuthSqlProvider {
    public String listAuthority(MetaAuthDTO param) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("AUTH_META");
        if (param.getAuthtype() != null) {
            sql.WHERE("authtype = #{authtype}");
        }
        if (param.getBiztype() != null) {
            sql.WHERE("biztype = #{biztype}");
        }
        if (param.getBizname() != null) {
            sql.WHERE("bizname = #{bizname}");
        }
        if (StringUtils.hasText(param.getMetaType())) {
            sql.WHERE("METATYPE = #{metaType}");
        }
        if (param.getGroupflag() != null) {
            sql.WHERE("GROUPFLAG = #{groupflag}");
        }
        if (param.getSqlCondition() != null) {
            sql.WHERE(param.getSqlCondition());
        }
        sql.WHERE("authflag = 1");
        return sql.toString();
    }

    public String queryMetaAuth(MetaAuthDTO metaAuthDTO) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("AUTH_META");
        if (StringUtils.hasText(metaAuthDTO.getMetaType())) {
            sql.WHERE("METATYPE = #{metaType}");
        }
        if (metaAuthDTO.getGroupflag() != null) {
            sql.WHERE("GROUPFLAG = #{groupflag}");
        }
        if (StringUtils.hasText(metaAuthDTO.getBizname())) {
            sql.WHERE("BIZNAME = #{bizname}");
        }
        if (StringUtils.hasText(metaAuthDTO.getUniqueCode())) {
            sql.WHERE("UNIQUECODE = #{uniqueCode}");
        }
        if (metaAuthDTO.getBiztype() != null) {
            sql.WHERE("BIZTYPE = #{biztype}");
        }
        return sql.toString();
    }

    public String getMetaAuth(MetaAuthDTO metaAuthDTO) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("AUTH_META");
        if (StringUtils.hasText(metaAuthDTO.getMetaType())) {
            sql.WHERE("METATYPE = #{metaType}");
        }
        if (metaAuthDTO.getGroupflag() != null) {
            sql.WHERE("GROUPFLAG = #{groupflag}");
        }
        if (StringUtils.hasText(metaAuthDTO.getBizname())) {
            sql.WHERE("BIZNAME = #{bizname}");
        }
        if (StringUtils.hasText(metaAuthDTO.getUniqueCode())) {
            sql.WHERE("UNIQUECODE = #{uniqueCode}");
        }
        if (metaAuthDTO.getBiztype() != null) {
            sql.WHERE("BIZTYPE = #{biztype}");
        }
        return sql.toString();
    }
}

