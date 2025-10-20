/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.bizmeta.provider;

import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class MetaGroupSqlProvider {
    public String getChildGroupList(MetaGroupDO paramGroupDO) {
        SQL sql = new SQL();
        sql.SELECT("*");
        sql.FROM("META_GROUP");
        if (StringUtils.hasText(paramGroupDO.getMetaType())) {
            sql.WHERE("METATYPE=#{metaType}");
        }
        if (StringUtils.hasText(paramGroupDO.getModuleName())) {
            sql.WHERE("MODULENAME=#{moduleName}");
        }
        if (paramGroupDO.getId() != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" PARENTNAME = ( SELECT NAME FROM META_GROUP WHERE ID = '").append(paramGroupDO.getId().toString()).append("')");
            sql.WHERE(stringBuilder.toString());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" PARENTNAME IS NULL ");
            sql.WHERE(stringBuilder.toString());
        }
        return sql.toString();
    }
}

