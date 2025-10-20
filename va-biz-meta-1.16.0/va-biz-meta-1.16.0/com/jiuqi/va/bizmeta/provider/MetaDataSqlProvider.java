/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.bizmeta.provider;

import com.jiuqi.va.domain.meta.MetaInfoDTO;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class MetaDataSqlProvider {
    public String getMetaDataByMetaType(MetaInfoDTO metaInfoDTO) {
        SQL sql = new SQL();
        sql.SELECT("DESIGNDATA");
        sql.FROM("META_DESIGN t");
        sql.JOIN("META_INFO ti on t.ID=ti.ID");
        if (!StringUtils.isEmpty(metaInfoDTO.getMetaType())) {
            sql.WHERE("ti.METATYPE = #{metaType}");
        }
        return sql.toString();
    }
}

