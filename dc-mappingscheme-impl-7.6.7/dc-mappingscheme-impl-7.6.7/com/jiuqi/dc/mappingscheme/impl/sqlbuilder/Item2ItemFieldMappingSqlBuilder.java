/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 */
package com.jiuqi.dc.mappingscheme.impl.sqlbuilder;

import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.sqlbuilder.AbstractFieldMappingSqlBuilder;
import org.springframework.stereotype.Component;

@Component
public class Item2ItemFieldMappingSqlBuilder
extends AbstractFieldMappingSqlBuilder {
    static final String LEFT_JOIN_SQLTEMPLATE = "LEFT JOIN #refTableName# ON #refTableName#.ODS_ID = #bizTableField# ";
    static final String JOIN_SQLTEMPLATE = "JOIN #refTableName# ON #refTableName#.ODS_ID = #bizTableField# ";

    @Override
    public RuleType getRuleType() {
        return RuleType.ITEM_BY_ITEM;
    }

    @Override
    public String buildSelectSql(FieldMappingDefineDTO item) {
        String condition = this.sqlHandler.judgeEmpty(this.getRefTableName(item.getFieldName()) + ".CODE", true);
        String fieldTemplate = "CASE WHEN %1$s THEN '#' ELSE %2$s END AS %3$s, \n";
        return String.format(fieldTemplate, condition, this.sqlHandler.toChar(this.getRefTableName(item.getFieldName()) + ".CODE"), item.getFieldName());
    }

    @Override
    public String buildJoinSql(FieldMappingDefineDTO item) {
        String sqlTemplate = this.getSqlTemplate(item.getFieldName()) + "\n";
        sqlTemplate = sqlTemplate.replace("#refTableName#", this.getRefTableName(item.getFieldName()));
        sqlTemplate = sqlTemplate.replace("#bizTableField#", this.getOdsFieldSql(item.getOdsFieldName()));
        return sqlTemplate;
    }

    @Override
    public String buildGroupSql(FieldMappingDefineDTO item) {
        String fieldTemplate = "%1$s.CODE,";
        return String.format(fieldTemplate, this.getRefTableName(item.getFieldName()));
    }

    @Override
    public String buildDirectJoinSql(FieldMappingDefineDTO item) {
        return "";
    }

    @Override
    public String buildDirectSelectSql(FieldMappingDefineDTO item) {
        String condition = this.sqlHandler.judgeEmpty(this.buildOdsFieldSql(item), true);
        String fieldTemplate = "CASE WHEN %1$s THEN '#' ELSE %2$s END AS %3$s, \n";
        return String.format(fieldTemplate, condition, this.sqlHandler.toChar(this.buildOdsFieldSql(item)), item.getFieldName());
    }

    @Override
    public String buildDirectGroupSql(FieldMappingDefineDTO item) {
        return this.buildOdsFieldSql(item).concat(",");
    }

    public String getSqlTemplate(String itemFieldName) {
        switch (itemFieldName) {
            case "UNITCODE": 
            case "SUBJECTCODE": 
            case "CURRENCYCODE": {
                return JOIN_SQLTEMPLATE;
            }
        }
        return LEFT_JOIN_SQLTEMPLATE;
    }
}

