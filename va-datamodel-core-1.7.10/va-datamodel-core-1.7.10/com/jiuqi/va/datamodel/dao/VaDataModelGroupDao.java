/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.datamodel.dao;

import com.jiuqi.va.datamodel.domain.DataModelGroupDO;
import com.jiuqi.va.datamodel.domain.DataModelGroupDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface VaDataModelGroupDao
extends BaseOptMapper<DataModelGroupDO> {
    @SelectProvider(type=DataModelGroupProvider.class, method="findByCondition")
    public List<DataModelGroupDO> list(DataModelGroupDTO var1);

    public static class DataModelGroupProvider {
        public String findByCondition(DataModelGroupDTO dataModelGroupDTO) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("DATAMODEL_GROUP");
            this.condition(sql, dataModelGroupDTO);
            sql.ORDER_BY("ORDERNUM");
            return sql.toString();
        }

        public void condition(SQL sql, DataModelGroupDTO dataModelGroupDTO) {
            if (StringUtils.hasText(dataModelGroupDTO.getName())) {
                sql.WHERE("NAME = #{name}");
            }
            if (StringUtils.hasText(dataModelGroupDTO.getTitle())) {
                sql.WHERE("TITLE = #{title}");
            }
            if (StringUtils.hasText(dataModelGroupDTO.getBiztype())) {
                sql.WHERE("BIZTYPE = #{biztype}");
            }
            if (StringUtils.hasText(dataModelGroupDTO.getParentname())) {
                sql.WHERE("PARENTNAME = #{parentname}");
            }
            if (StringUtils.hasText(dataModelGroupDTO.getSearchKey())) {
                String likeSql = "concat(concat('%', #{searchKey, jdbcType=VARCHAR}),'%')";
                ((SQL)sql.AND()).WHERE("TITLE like " + likeSql);
            }
        }
    }
}

