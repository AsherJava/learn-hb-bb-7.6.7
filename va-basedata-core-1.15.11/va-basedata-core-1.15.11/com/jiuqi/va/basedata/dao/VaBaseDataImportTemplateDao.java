/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.CustomOptMapper
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.InsertProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.basedata.dao;

import com.jiuqi.va.basedata.domain.BaseDataImportTemplateDO;
import com.jiuqi.va.basedata.domain.BaseDataImportTemplateDTO;
import com.jiuqi.va.mapper.common.CustomOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface VaBaseDataImportTemplateDao
extends CustomOptMapper {
    @InsertProvider(type=BaseDataImportTemplateSqlProvider.class, method="add")
    public int add(BaseDataImportTemplateDO var1);

    @UpdateProvider(type=BaseDataImportTemplateSqlProvider.class, method="update")
    public int update(BaseDataImportTemplateDTO var1);

    @DeleteProvider(type=BaseDataImportTemplateSqlProvider.class, method="delete")
    public int delete(BaseDataImportTemplateDTO var1);

    @SelectProvider(type=BaseDataImportTemplateSqlProvider.class, method="query")
    public List<BaseDataImportTemplateDO> list(BaseDataImportTemplateDTO var1);

    public static class BaseDataImportTemplateSqlProvider {
        private String tablename = "BASEDATA_IMPORT_TEMPLATE";

        public String add() {
            SQL sql = new SQL();
            sql.INSERT_INTO(this.tablename);
            sql.INTO_COLUMNS(new String[]{"ID", "CODE", "NAME", "FIXED", "ORDERNUM", "TEMPLATEDATA"});
            sql.INTO_VALUES(new String[]{"#{id, jdbcType=VARCHAR}", "#{code, jdbcType=VARCHAR}", "#{name, jdbcType=VARCHAR}", "#{fixed, jdbcType=INTEGER}", "#{ordernum, jdbcType=NUMERIC}", "#{templatedata, jdbcType=CLOB}"});
            return sql.toString();
        }

        public String update() {
            SQL sql = new SQL();
            sql.UPDATE(this.tablename);
            sql.SET("code = #{code, jdbcType=VARCHAR}");
            sql.SET("name=#{name, jdbcType=VARCHAR}");
            sql.SET("fixed=#{fixed, jdbcType=INTEGER}");
            sql.SET("templatedata=#{templatedata, jdbcType=CLOB}");
            ((SQL)sql.AND()).WHERE("id = #{id}");
            return sql.toString();
        }

        public String delete(BaseDataImportTemplateDTO baseDataImportTemplateDTO) {
            SQL sql = new SQL();
            sql.DELETE_FROM(this.tablename);
            this.setCondition(sql, baseDataImportTemplateDTO);
            return sql.toString();
        }

        public String query(BaseDataImportTemplateDTO baseDataImportTemplateDTO) {
            SQL sql = new SQL();
            sql.SELECT("ID, CODE, NAME, FIXED, TEMPLATEDATA");
            sql.FROM(this.tablename);
            this.setCondition(sql, baseDataImportTemplateDTO);
            return sql.toString();
        }

        private void setCondition(SQL sql, BaseDataImportTemplateDTO baseDataImportTemplateDTO) {
            if (baseDataImportTemplateDTO.getId() != null) {
                ((SQL)sql.AND()).WHERE("ID = #{id}");
            } else {
                if (StringUtils.hasText(baseDataImportTemplateDTO.getCode())) {
                    ((SQL)sql.AND()).WHERE("CODE = #{code}");
                }
                if (StringUtils.hasText(baseDataImportTemplateDTO.getName())) {
                    ((SQL)sql.AND()).WHERE("NAME = #{name}");
                }
                if (baseDataImportTemplateDTO.getFixed() != null && baseDataImportTemplateDTO.getFixed() == 1) {
                    ((SQL)sql.AND()).WHERE("FIXED = #{fixed}");
                } else {
                    ((SQL)sql.AND()).WHERE("FIXED = 0 or FIXED is null");
                }
            }
            sql.ORDER_BY("ORDERNUM");
        }
    }
}

