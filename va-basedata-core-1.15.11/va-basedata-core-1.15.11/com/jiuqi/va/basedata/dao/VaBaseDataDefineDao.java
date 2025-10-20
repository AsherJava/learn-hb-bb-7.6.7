/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.basedata.dao;

import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface VaBaseDataDefineDao
extends BaseOptMapper<BaseDataDefineDO> {
    @SelectProvider(method="query", type=BaseDataDefineSqlProvider.class)
    public List<BaseDataDefineDO> list(BaseDataDefineDTO var1);

    @SelectProvider(method="queryCount", type=BaseDataDefineSqlProvider.class)
    public int queryCount(BaseDataDefineDTO var1);

    @SelectProvider(method="queryDataCount", type=BaseDataDefineSqlProvider.class)
    public int queryDataCount(BaseDataDefineDTO var1);

    @Select(value={"select * from BASEDATA_DEFINE where MODIFYTIME>=#{modifytime, jdbcType=TIMESTAMP}"})
    public List<BaseDataDefineDO> selectByStartVer(BaseDataDefineDTO var1);

    @Select(value={"select * from BASEDATA_DEFINE where MODIFYTIME>#{modifytime, jdbcType=TIMESTAMP}"})
    public List<BaseDataDefineDO> selectGreaterVer(BaseDataDefineDTO var1);

    public static class BaseDataDefineSqlProvider {
        public String query(BaseDataDefineDTO basedata) {
            SQL sql = new SQL();
            sql.SELECT("*");
            sql.FROM("BASEDATA_DEFINE");
            this.condition(sql, basedata);
            sql.ORDER_BY("ORDERNUM");
            return sql.toString();
        }

        public String queryCount(BaseDataDefineDTO basedata) {
            SQL sql = new SQL();
            sql.SELECT("count(*)");
            sql.FROM("BASEDATA_DEFINE");
            this.condition(sql, basedata);
            sql.ORDER_BY("ORDERNUM");
            return sql.toString();
        }

        private void condition(SQL sql, BaseDataDefineDTO param) {
            if (param.getId() != null) {
                sql.WHERE("ID=#{id}");
            } else {
                if (StringUtils.hasText(param.getName())) {
                    sql.WHERE("NAME=#{name}");
                }
                if (StringUtils.hasText(param.getGroupname()) && !"-".equals(param.getGroupname())) {
                    sql.WHERE("GROUPNAME=#{groupname}");
                }
                if (StringUtils.hasText(param.getSearchKey())) {
                    String likeSql = "concat(concat('%', #{searchKey, jdbcType=VARCHAR}),'%')";
                    ((SQL)sql.AND()).WHERE("NAME like " + likeSql + " or TITLE like " + likeSql);
                }
            }
        }

        public String queryDataCount(BaseDataDefineDTO basedata) {
            DataModelClient dataModelClient = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
            DataModelDTO dataModelDTO = new DataModelDTO();
            dataModelDTO.setName(basedata.getName());
            dataModelDTO.setDeepClone(Boolean.valueOf(false));
            DataModelDO dataModel = dataModelClient.get(dataModelDTO);
            if (dataModel == null) {
                throw new RuntimeException("\u5efa\u6a21\u4fe1\u606f\u4e0d\u5b58\u5728");
            }
            SQL sql = new SQL();
            sql.SELECT("count(0) as cnt");
            sql.FROM(dataModel.getName());
            sql.WHERE("1=1");
            return sql.toString();
        }
    }
}

