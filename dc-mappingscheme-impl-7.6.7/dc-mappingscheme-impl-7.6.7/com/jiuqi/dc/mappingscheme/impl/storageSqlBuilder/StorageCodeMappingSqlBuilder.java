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
import com.jiuqi.dc.mappingscheme.impl.enums.FieldMappingType;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;
import org.springframework.stereotype.Component;

@Component
public class StorageCodeMappingSqlBuilder
implements IStorageFiledMappingSqlBuilder {
    @Override
    public StorageType getStorageType() {
        return StorageType.CODE;
    }

    @Override
    public String buildSelectSql(String dataSourceCode, DimMappingDTO mapping) {
        String fieldTemplate = " %1$s AS %2$s, \n";
        if (FieldMappingType.CONSTANT.getCode().equals(mapping.getFieldMappingType())) {
            fieldTemplate = " '%1$s' AS %2$s, \n";
        }
        return String.format(fieldTemplate, mapping.getOdsFieldName(), DataRefUtil.getFieldName((String)mapping.getCode()));
    }

    @Override
    public String buildJoinSql(String dataSourceCode, DimMappingDTO mapping) {
        return "";
    }

    @Override
    public String buildGroupSql(String dataSourceCode, DimMappingDTO dimMapping) {
        if ("CONSTANT".equals(dimMapping.getFieldMappingType())) {
            return "";
        }
        return dimMapping.getOdsFieldName().concat(",");
    }
}

