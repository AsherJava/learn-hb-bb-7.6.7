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
package com.jiuqi.va.organization.dao;

import com.jiuqi.va.mapper.common.CustomOptMapper;
import com.jiuqi.va.organization.domain.OrgImportTemplateDO;
import com.jiuqi.va.organization.domain.OrgImportTemplateDTO;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

@Mapper
public interface VaOrgImportTemplateDao
extends CustomOptMapper {
    @InsertProvider(type=OrgImportTemplateSqlProvider.class, method="add")
    public int add(OrgImportTemplateDO var1);

    @UpdateProvider(type=OrgImportTemplateSqlProvider.class, method="update")
    public int update(OrgImportTemplateDTO var1);

    @DeleteProvider(type=OrgImportTemplateSqlProvider.class, method="delete")
    public int delete(OrgImportTemplateDTO var1);

    @SelectProvider(type=OrgImportTemplateSqlProvider.class, method="query")
    public List<OrgImportTemplateDO> list(OrgImportTemplateDTO var1);

    public static class OrgImportTemplateSqlProvider {
        private String tablename = "ORG_IMPORT_TEMPLATE";

        public String add() {
            SQL sql = new SQL();
            sql.INSERT_INTO(this.tablename);
            sql.INTO_COLUMNS(new String[]{"ID", "CODE", "NAME", "ORDERNUM", "TEMPLATEDATA"});
            sql.INTO_VALUES(new String[]{"#{id, jdbcType=VARCHAR}", "#{code, jdbcType=VARCHAR}", "#{name, jdbcType=VARCHAR}", "#{ordernum, jdbcType=NUMERIC}", "#{templatedata, jdbcType=CLOB}"});
            return sql.toString();
        }

        public String update() {
            SQL sql = new SQL();
            sql.UPDATE(this.tablename);
            sql.SET("CODE = #{code, jdbcType=VARCHAR}");
            sql.SET("NAME = #{name, jdbcType=VARCHAR}");
            sql.SET("TEMPLATEDATA = #{templatedata, jdbcType=CLOB}");
            ((SQL)sql.AND()).WHERE("ID = #{id}");
            return sql.toString();
        }

        public String delete(OrgImportTemplateDTO orgImportTemplateDTO) {
            SQL sql = new SQL();
            sql.DELETE_FROM(this.tablename);
            this.setCondition(sql, orgImportTemplateDTO);
            return sql.toString();
        }

        public String query(OrgImportTemplateDTO orgImportTemplateDTO) {
            SQL sql = new SQL();
            sql.SELECT("ID, CODE, NAME, TEMPLATEDATA");
            sql.FROM(this.tablename);
            this.setCondition(sql, orgImportTemplateDTO);
            return sql.toString();
        }

        private void setCondition(SQL sql, OrgImportTemplateDTO orgImportTemplateDTO) {
            if (orgImportTemplateDTO.getId() != null) {
                ((SQL)sql.AND()).WHERE("ID = #{id}");
            } else {
                if (StringUtils.hasText(orgImportTemplateDTO.getCode())) {
                    ((SQL)sql.AND()).WHERE("CODE = #{code}");
                }
                if (StringUtils.hasText(orgImportTemplateDTO.getName())) {
                    ((SQL)sql.AND()).WHERE("NAME = #{name}");
                }
            }
            sql.ORDER_BY("ORDERNUM");
        }
    }
}

