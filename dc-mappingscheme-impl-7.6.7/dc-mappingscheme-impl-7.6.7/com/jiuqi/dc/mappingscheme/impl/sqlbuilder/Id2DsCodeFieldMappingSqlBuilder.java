/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 */
package com.jiuqi.dc.mappingscheme.impl.sqlbuilder;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.dc.mappingscheme.impl.sqlbuilder.AbstractFieldMappingSqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Id2DsCodeFieldMappingSqlBuilder
extends AbstractFieldMappingSqlBuilder {
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private DataSchemeService dataSchemeService;

    @Override
    public RuleType getRuleType() {
        return RuleType.ID_TO_DS_CODE;
    }

    @Override
    public String buildSelectSql(FieldMappingDefineDTO item) {
        String condition = this.sqlHandler.judgeEmpty(this.getTableName(item.getFieldName()) + ".CODE", true);
        String fieldTemplate = "CASE WHEN %1$s THEN '#' ELSE %2$s END AS %3$s, \n";
        return String.format(fieldTemplate, condition, this.sqlHandler.toChar("'" + item.getDataSchemeCode() + "' || " + this.getTableName(item.getFieldName()) + ".CODE"), item.getFieldName());
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

    @Override
    public String buildBaseCodeSql(DataMappingDefineDTO define) {
        String dataSourceCode = this.dataSchemeService.getByCode(define.getDataSchemeCode()).getDataSourceCode();
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(dataSourceCode));
        return String.format(" %1$s AS CODE,", sqlHandler.concat(new String[]{"'" + define.getDataSchemeCode() + "'", "'|'", "ODSTABLE.CODE"}));
    }
}

