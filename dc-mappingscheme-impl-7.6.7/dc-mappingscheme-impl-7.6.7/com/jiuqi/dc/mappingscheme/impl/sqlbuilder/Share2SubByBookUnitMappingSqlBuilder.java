/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 */
package com.jiuqi.dc.mappingscheme.impl.sqlbuilder;

import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.sqlbuilder.AbstractFieldMappingSqlBuilder;
import org.springframework.stereotype.Component;

@Component
public class Share2SubByBookUnitMappingSqlBuilder
extends AbstractFieldMappingSqlBuilder {
    @Override
    public RuleType getRuleType() {
        return RuleType.SHARE_TO_SUB_BY_BOOKUNIT;
    }

    @Override
    public String buildSelectSql(FieldMappingDefineDTO item) {
        String condition = this.sqlHandler.judgeEmpty(this.getTableName(item.getFieldName()) + ".ID", true);
        String fieldTemplate = "CASE WHEN %1$s THEN '#' ELSE %2$s END AS %3$s, \n";
        return String.format(fieldTemplate, condition, this.sqlHandler.toChar(this.getTableName(item.getFieldName()) + ".ID"), item.getFieldName());
    }

    @Override
    public String buildJoinSql(FieldMappingDefineDTO item) {
        BaseDataMappingDefineDTO baseDataDefine = this.getBaseDataDefine(item.getDataSchemeCode(), this.getTableName(item.getFieldName()));
        StringBuilder sql = new StringBuilder();
        sql.append("LEFT JOIN (#advancedSql#) #odsTableAlias# ON #odsTableAlias#.CODE = #bizTableField# ");
        sql.append(" AND #odsTableAlias#.BOOKCODE = T.BOOKCODE ");
        sql.append(" AND T.UNITCODE IN ");
        sql.append("\t(SELECT CODE FROM MD_ORG ");
        sql.append(" \t  WHERE (CODE = #odsTableAlias#.UNITCODE OR ");
        sql.append(this.sqlHandler.indexStrSql("PARENTS", this.sqlHandler.concat(new String[]{"#odsTableAlias#.UNITCODE", "'/'"}))).append(" = 1 OR ");
        sql.append(this.sqlHandler.indexStrSql("PARENTS", this.sqlHandler.concat(new String[]{"'/'", "#odsTableAlias#.UNITCODE", "'/'"}))).append(" > 0))");
        String sqlTemplate = sql.toString() + " \n";
        sqlTemplate = sqlTemplate.replace("#advancedSql#", baseDataDefine.getAdvancedSql());
        sqlTemplate = sqlTemplate.replace("#odsTableAlias#", this.getTableName(item.getFieldName()));
        sqlTemplate = sqlTemplate.replace("#bizTableField#", this.getOdsFieldSql(item.getOdsFieldName()));
        return sqlTemplate;
    }

    @Override
    public String buildGroupSql(FieldMappingDefineDTO item) {
        String fieldTemplate = "%1$s.ID,";
        return String.format(fieldTemplate, this.getTableName(item.getFieldName()));
    }

    @Override
    public String buildBaseCodeSql(DataMappingDefineDTO define) {
        return " ODSTABLE.ID AS CODE,";
    }
}

