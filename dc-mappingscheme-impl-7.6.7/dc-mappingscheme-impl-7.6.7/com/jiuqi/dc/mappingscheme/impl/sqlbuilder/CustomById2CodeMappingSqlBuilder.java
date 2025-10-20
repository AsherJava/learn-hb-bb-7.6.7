/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 */
package com.jiuqi.dc.mappingscheme.impl.sqlbuilder;

import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import com.jiuqi.dc.mappingscheme.impl.sqlbuilder.AbstractFieldMappingSqlBuilder;
import org.springframework.stereotype.Component;

@Component
public class CustomById2CodeMappingSqlBuilder
extends AbstractFieldMappingSqlBuilder {
    @Override
    public IRuleType getRuleType() {
        return RuleType.CUSTOM_BY_ID_TO_CODE;
    }

    @Override
    public String buildSelectSql(FieldMappingDefineDTO item) {
        String condition = this.sqlHandler.judgeEmpty(this.getTableName(item.getFieldName()) + ".CODE", true);
        String fieldTemplate = "CASE WHEN %1$s THEN '#' ELSE %2$s END AS %3$s, \n";
        return String.format(fieldTemplate, condition, this.sqlHandler.toChar(this.getTableName(item.getFieldName()) + ".CODE"), item.getFieldName());
    }

    @Override
    public String buildJoinSql(FieldMappingDefineDTO item) {
        BaseDataMappingDefineDTO baseDataDefine = this.getBaseDataDefine(item.getDataSchemeCode(), this.getTableName(item.getFieldName()));
        String sqlTemplate = this.getId2CodeSqlTemplate(item.getFieldName()) + "\n";
        sqlTemplate = sqlTemplate.replace("#advancedSql#", baseDataDefine.getAdvancedSql());
        sqlTemplate = sqlTemplate.replace("#odsTableAlias#", this.getTableName(item.getFieldName()));
        sqlTemplate = sqlTemplate.replace("#bizTableField#", this.getOdsFieldSql(item.getOdsFieldName()));
        return sqlTemplate;
    }

    @Override
    public String buildGroupSql(FieldMappingDefineDTO item) {
        String fieldTemplate = "%1$s.CODE,";
        return String.format(fieldTemplate, this.getTableName(item.getFieldName()));
    }
}

