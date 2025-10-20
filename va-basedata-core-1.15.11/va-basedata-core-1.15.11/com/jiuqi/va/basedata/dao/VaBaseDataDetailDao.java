/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDetailDO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.InsertProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.basedata.dao;

import com.jiuqi.va.basedata.common.FormatValidationUtil;
import com.jiuqi.va.domain.basedata.BaseDataDetailDO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface VaBaseDataDetailDao
extends BaseOptMapper<BaseDataDetailDO> {
    @SelectProvider(type=BaseDataDetailSqlProvider.class, method="list")
    public List<BaseDataDetailDO> list(BaseDataDetailDO var1);

    @SelectProvider(type=BaseDataDetailSqlProvider.class, method="queryValus")
    public List<String> queryValus(BaseDataDetailDO var1);

    @InsertProvider(type=BaseDataDetailSqlProvider.class, method="add")
    public int add(BaseDataDetailDO var1);

    @DeleteProvider(type=BaseDataDetailSqlProvider.class, method="deleteByTableField")
    public int deleteByTableField(BaseDataDetailDO var1);

    public static class BaseDataDetailSqlProvider {
        public String list(BaseDataDetailDO param) {
            SQL sql = new SQL();
            sql.SELECT("ID,MASTERID,FIELDNAME,FIELDVALUE,ORDERNUM");
            sql.FROM(FormatValidationUtil.checkInjection(param.getTablename().toUpperCase()));
            if (param.getMasterid() != null) {
                sql.WHERE("MASTERID = #{masterid, jdbcType=VARCHAR}");
            }
            if (param.getFieldname() != null) {
                sql.WHERE("FIELDNAME = #{fieldname, jdbcType=VARCHAR}");
            }
            sql.ORDER_BY("ORDERNUM");
            return sql.toString();
        }

        public String queryValus(BaseDataDetailDO param) {
            SQL sql = new SQL();
            sql.SELECT("FIELDVALUE");
            sql.FROM(FormatValidationUtil.checkInjection(param.getTablename().toUpperCase()));
            sql.WHERE("MASTERID = #{masterid, jdbcType=VARCHAR}");
            sql.WHERE("FIELDNAME = #{fieldname, jdbcType=VARCHAR}");
            sql.ORDER_BY("ORDERNUM");
            return sql.toString();
        }

        public String add(BaseDataDetailDO param) {
            SQL sql = new SQL();
            sql.INSERT_INTO(FormatValidationUtil.checkInjection(param.getTablename().toUpperCase()));
            sql.INTO_COLUMNS(new String[]{"ID", "MASTERID", "FIELDNAME", "FIELDVALUE", "ORDERNUM"});
            sql.INTO_VALUES(new String[]{"#{id, jdbcType=VARCHAR}", "#{masterid, jdbcType=VARCHAR}", "#{fieldname, jdbcType=VARCHAR}", "#{fieldvalue, jdbcType=VARCHAR}", "#{ordernum, jdbcType=NUMERIC}"});
            return sql.toString();
        }

        public String deleteByTableField(BaseDataDetailDO param) {
            SQL sql = new SQL();
            sql.DELETE_FROM(FormatValidationUtil.checkInjection(param.getTablename().toUpperCase()));
            sql.WHERE("MASTERID = #{masterid, jdbcType=VARCHAR}");
            sql.WHERE("FIELDNAME = #{fieldname, jdbcType=VARCHAR}");
            return sql.toString();
        }
    }
}

