/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 */
package com.jiuqi.dc.mappingscheme.impl.sqlbuilder;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.dc.mappingscheme.impl.sqlbuilder.AbstractFieldMappingSqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Code2DsCodeFieldMappingSqlBuilder
extends AbstractFieldMappingSqlBuilder {
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private DataSchemeService dataSchemeService;

    @Override
    public RuleType getRuleType() {
        return RuleType.CODE_TO_DS_CODE;
    }

    @Override
    public String buildSelectSql(FieldMappingDefineDTO item) {
        String field = this.sqlHandler.concatBySeparator("|", new String[]{"'" + item.getDataSchemeCode() + "'", this.getOdsFieldSql(item.getOdsFieldName())});
        String condition = this.sqlHandler.judgeEmpty(this.getOdsFieldSql(item.getOdsFieldName()), true);
        String fieldTemplate = "CASE WHEN %1$s THEN '#' ELSE %2$s END AS %3$s, \n";
        return String.format(fieldTemplate, condition, this.sqlHandler.toChar(field), item.getFieldName());
    }

    @Override
    public String buildJoinSql(FieldMappingDefineDTO item) {
        return "";
    }

    @Override
    public String buildBaseCodeSql(DataMappingDefineDTO define) {
        String dataSourceCode = this.dataSchemeService.getByCode(define.getDataSchemeCode()).getDataSourceCode();
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(dataSourceCode));
        return String.format(" %1$s AS CODE,", sqlHandler.concat(new String[]{"'" + define.getDataSchemeCode() + "'", "'|'", "ODSTABLE.CODE"}));
    }
}

