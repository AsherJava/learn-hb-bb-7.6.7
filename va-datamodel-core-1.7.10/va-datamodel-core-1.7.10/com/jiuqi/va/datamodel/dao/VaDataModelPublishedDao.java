/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.datamodel.dao;

import com.jiuqi.va.datamodel.domain.DataModelPublishedDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface VaDataModelPublishedDao
extends BaseOptMapper<DataModelPublishedDO> {
    @Select(value={"select GROUPCODE from DATAMODEL_DEFINE where BIZTYPE=#{biztype} group by GROUPCODE"})
    public List<String> listGroup(DataModelDTO var1);

    @Select(value={"select * from DATAMODEL_DEFINE where VER >= #{ver}"})
    public List<DataModelPublishedDO> selectByStartVer(DataModelDTO var1);

    @SelectProvider(type=DataModelProvider.class, method="findByCondition")
    public List<DataModelPublishedDO> list(DataModelDTO var1);

    @SelectProvider(type=DataModelProvider.class, method="countByCondition")
    public int count(DataModelDTO var1);

    public static class DataModelProvider {
        public String findByCondition(DataModelDTO dataModelDTO) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("DATAMODEL_DEFINE");
            this.condition(sql, dataModelDTO);
            sql.ORDER_BY("NAME");
            return sql.toString();
        }

        public String countByCondition(DataModelDTO dataModelDTO) {
            SQL sql = new SQL();
            sql.SELECT("count(0)");
            sql.FROM("DATAMODEL_DEFINE");
            this.condition(sql, dataModelDTO);
            return sql.toString();
        }

        public void condition(SQL sql, DataModelDTO dataModelDTO) {
            if (StringUtils.hasText(dataModelDTO.getName())) {
                sql.WHERE("NAME = #{name}");
            }
            if (StringUtils.hasText(dataModelDTO.getTitle())) {
                sql.WHERE("TITLE = #{title}");
            }
            if (dataModelDTO.getBiztype() != null) {
                sql.WHERE("BIZTYPE = #{biztype}");
            }
            if (StringUtils.hasText(dataModelDTO.getGroupcode())) {
                sql.WHERE("GROUPCODE = #{groupcode}");
            }
            if (StringUtils.hasText(dataModelDTO.getSearchKey())) {
                dataModelDTO.setSearchKey(dataModelDTO.getSearchKey().toUpperCase());
                String likeSql = "concat(concat('%', #{searchKey, jdbcType=VARCHAR}),'%')";
                ((SQL)sql.AND()).WHERE("upper(NAME) like " + likeSql + " or upper(TITLE) like " + likeSql);
            }
        }
    }
}

