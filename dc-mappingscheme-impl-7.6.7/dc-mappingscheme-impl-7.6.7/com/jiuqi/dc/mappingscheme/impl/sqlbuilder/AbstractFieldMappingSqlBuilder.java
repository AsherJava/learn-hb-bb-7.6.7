/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 */
package com.jiuqi.dc.mappingscheme.impl.sqlbuilder;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingSqlBuilder;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractFieldMappingSqlBuilder
implements IFieldMappingSqlBuilder {
    @Autowired
    private BaseDataRefDefineService baseDataDefineService;
    @Autowired
    protected IDbSqlHandler sqlHandler;
    static final String LEFT_JOIN_SQLTEMPLATE = "LEFT JOIN (#advancedSql#) #odsTableAlias# ON #odsTableAlias#.ID = #bizTableField# ";
    static final String JOIN_SQLTEMPLATE = "JOIN (#advancedSql#) #odsTableAlias# ON #odsTableAlias#.ID = #bizTableField# ";

    protected BaseDataMappingDefineDTO getBaseDataDefine(String dataSchemeCode, String code) {
        return this.baseDataDefineService.getByCode(dataSchemeCode, code);
    }

    protected String getOdsFieldSql(String odsFieldName) {
        Assert.isNotEmpty((String)odsFieldName);
        String[] odsFieldNameArr = odsFieldName.split("/");
        return this.sqlHandler.concatBySeparator("|", odsFieldNameArr);
    }

    @Override
    public String buildOdsFieldSql(FieldMappingDefineDTO item) {
        return this.getOdsFieldSql(item.getOdsFieldName());
    }

    @Override
    public String buildGroupSql(FieldMappingDefineDTO item) {
        return this.buildOdsFieldSql(item).concat(",");
    }

    @Override
    public String buildDirectGroupSql(FieldMappingDefineDTO item) {
        return this.buildGroupSql(item);
    }

    @Override
    public String buildDirectSelectSql(FieldMappingDefineDTO item) {
        return this.buildSelectSql(item);
    }

    @Override
    public String buildDirectJoinSql(FieldMappingDefineDTO item) {
        return this.buildJoinSql(item);
    }

    protected String getRefTableName(String itemFieldName) {
        return DataRefUtil.getRefTableName((String)DataRefUtil.getTableName((String)itemFieldName));
    }

    protected String getTableName(String itemFieldName) {
        return DataRefUtil.getTableName((String)itemFieldName);
    }

    protected String getId2CodeSqlTemplate(String itemFieldName) {
        switch (itemFieldName) {
            case "UNITCODE": 
            case "SUBJECTCODE": 
            case "CURRENCYCODE": {
                return JOIN_SQLTEMPLATE;
            }
        }
        return LEFT_JOIN_SQLTEMPLATE;
    }

    @Override
    public void setSqlHandler(IDbSqlHandler sqlHandler) {
        this.sqlHandler = sqlHandler;
    }

    @Override
    public String buildBaseCodeSql(DataMappingDefineDTO define) {
        return " ODSTABLE.CODE AS CODE,";
    }
}

