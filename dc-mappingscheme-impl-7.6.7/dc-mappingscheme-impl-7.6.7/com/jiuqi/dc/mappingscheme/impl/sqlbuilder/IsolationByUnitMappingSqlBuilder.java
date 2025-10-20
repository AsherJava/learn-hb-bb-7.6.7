/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 */
package com.jiuqi.dc.mappingscheme.impl.sqlbuilder;

import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.mappingscheme.impl.sqlbuilder.AbstractFieldMappingSqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IsolationByUnitMappingSqlBuilder
extends AbstractFieldMappingSqlBuilder {
    @Autowired
    private DataSchemeService dataSchemeService;

    @Override
    public RuleType getRuleType() {
        return RuleType.ISOLATION_BY_UNIT;
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
        DataSchemeDTO dataSchemeDTO = this.dataSchemeService.getByCode(baseDataDefine.getDataSchemeCode());
        StringBuilder sql = new StringBuilder();
        sql.append("LEFT JOIN (#advancedSql#) #odsTableAlias# ON #odsTableAlias#.CODE = #bizTableField# ");
        sql.append(" AND T.#orgMappingType# = #odsTableAlias#.UNITCODE \n");
        String sqlTemplate = sql.toString();
        sqlTemplate = sqlTemplate.replace("#advancedSql#", baseDataDefine.getAdvancedSql());
        sqlTemplate = sqlTemplate.replace("#odsTableAlias#", this.getTableName(item.getFieldName()));
        sqlTemplate = sqlTemplate.replace("#bizTableField#", this.getOdsFieldSql(item.getOdsFieldName()));
        sqlTemplate = sqlTemplate.replace("#orgMappingType#", dataSchemeDTO.getOrgMappingType());
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

