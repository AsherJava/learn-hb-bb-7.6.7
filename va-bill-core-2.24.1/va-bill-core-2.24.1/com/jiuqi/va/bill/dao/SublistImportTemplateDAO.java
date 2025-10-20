/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.InsertProvider
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.bill.dao;

import com.jiuqi.va.bill.domain.SublistImportTemplateDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

@Deprecated
public interface SublistImportTemplateDAO
extends BaseOptMapper<SublistImportTemplateDTO> {
    @SelectProvider(type=SublistTemplateSqlProvider.class, method="selectSublistTemplate")
    public List<SublistImportTemplateDTO> selectSublistTemplate(SublistImportTemplateDTO var1);

    @InsertProvider(type=SublistTemplateSqlProvider.class, method="insertSublistTemplate")
    public int insertSublistTemplate(SublistImportTemplateDTO var1);

    @UpdateProvider(type=SublistTemplateSqlProvider.class, method="updateSublistTemplate")
    public int updateSublistTemplate(SublistImportTemplateDTO var1);

    public static class SublistTemplateSqlProvider {
        public String selectSublistTemplate(SublistImportTemplateDTO param) {
            SQL sql = (SQL)((SQL)((SQL)new SQL().SELECT("TEMPLATEDATA")).FROM("BIZ_SUBLIST_IMPORT_TEMPLATE")).WHERE(" BILLTYPE = #{billType} and TABLENAME = #{tableName}");
            return sql.toString();
        }

        public String insertSublistTemplate(SublistImportTemplateDTO param) {
            SQL sql = (SQL)((SQL)new SQL().INSERT_INTO("BIZ_SUBLIST_IMPORT_TEMPLATE")).VALUES("ID,VER,BILLTYPE,TABLENAME,TEMPLATEDATA", "#{id},#{ver},#{billType},#{tableName},#{templateData}");
            return sql.toString();
        }

        public String updateSublistTemplate(SublistImportTemplateDTO param) {
            SQL sql = (SQL)((SQL)((SQL)new SQL().UPDATE("BIZ_SUBLIST_IMPORT_TEMPLATE")).SET("TEMPLATEDATA = #{templateData}")).WHERE(" BILLTYPE = #{billType} and TABLENAME = #{tableName}");
            return sql.toString();
        }
    }
}

