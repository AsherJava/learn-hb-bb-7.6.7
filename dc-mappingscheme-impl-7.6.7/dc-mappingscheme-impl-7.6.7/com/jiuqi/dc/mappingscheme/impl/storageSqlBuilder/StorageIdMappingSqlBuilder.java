/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.DimMappingDTO
 */
package com.jiuqi.dc.mappingscheme.impl.storageSqlBuilder;

import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.client.dto.DimMappingDTO;
import com.jiuqi.dc.mappingscheme.impl.define.IStorageFiledMappingSqlBuilder;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StorageIdMappingSqlBuilder
implements IStorageFiledMappingSqlBuilder {
    @Autowired
    private BaseDataRefDefineService baseDataDefineService;
    static final String LEFT_JOIN_SQLTEMPLATE = "LEFT JOIN (#advancedSql#) #odsTableAlias# ON #odsTableAlias#.ID = #bizTableField# ";
    static final String JOIN_SQLTEMPLATE = "JOIN (#advancedSql#) #odsTableAlias# ON #odsTableAlias#.ID = #bizTableField# ";

    @Override
    public StorageType getStorageType() {
        return StorageType.ID;
    }

    @Override
    public String buildSelectSql(String dataSourceCode, DimMappingDTO mapping) {
        String fieldTemplate = " %1$s AS %2$s, \n";
        return String.format(fieldTemplate, mapping.getCode() + ".CODE", DataRefUtil.getFieldName((String)mapping.getCode()));
    }

    @Override
    public String buildJoinSql(String dataSourceCode, DimMappingDTO mapping) {
        String sqlTemplate = this.getId2CodeSqlTemplate(mapping.getCode()) + "\n";
        sqlTemplate = sqlTemplate.replace("#advancedSql#", mapping.getAdvancedSql());
        sqlTemplate = sqlTemplate.replace("#odsTableAlias#", mapping.getCode());
        sqlTemplate = sqlTemplate.replace("#bizTableField#", mapping.getOdsFieldName());
        return sqlTemplate;
    }

    @Override
    public String buildGroupSql(String dataSourceCode, DimMappingDTO mapping) {
        if ("CONSTANT".equals(mapping.getFieldMappingType())) {
            return "";
        }
        String fieldTemplate = "%1$s.CODE,";
        return String.format(fieldTemplate, mapping.getCode());
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
}

