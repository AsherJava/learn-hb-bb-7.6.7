/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.BooleanValEnum
 *  com.jiuqi.dc.base.common.env.EnvCenter
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.Column
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingProvider
 *  com.jiuqi.dc.mappingscheme.impl.enums.FieldMappingType
 *  com.jiuqi.va.mapper.common.JDialectUtil
 */
package com.jiuqi.bde.bizmodel.define.adaptor;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.BooleanValEnum;
import com.jiuqi.dc.base.common.env.EnvCenter;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDTO;
import com.jiuqi.dc.mappingscheme.impl.common.Column;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingProvider;
import com.jiuqi.dc.mappingscheme.impl.enums.FieldMappingType;
import com.jiuqi.va.mapper.common.JDialectUtil;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractFieldMappingProvider
implements IFieldMappingProvider {
    protected FieldMappingDTO buildFieldMapping(String fieldName, String fieldTitle) {
        return this.buildFieldMapping(fieldName, fieldTitle, FieldMappingType.SOURCE_FIELD, null, null, null);
    }

    protected FieldMappingDTO buildFieldMapping(String fieldName, String fieldTitle, FieldMappingType fieldMappingType, String odsFieldName, String odsFieldTitle, RuleType ruleType) {
        return this.buildFieldMapping(fieldName, fieldTitle, fieldMappingType, odsFieldName, odsFieldTitle, ruleType, BooleanValEnum.NO.getCode());
    }

    protected FieldMappingDTO buildFieldMapping(String fieldName, String fieldTitle, FieldMappingType fieldMappingType, String odsFieldName, String odsFieldTitle, RuleType ruleType, Integer fixedFlag) {
        Assert.isNotEmpty((String)fieldName);
        Assert.isNotEmpty((String)fieldTitle);
        FieldMappingDTO item = new FieldMappingDTO();
        item.setFieldName(fieldName.toUpperCase());
        item.setFieldTitle(fieldTitle.toUpperCase());
        item.setFieldMappingType(fieldMappingType != null ? fieldMappingType.getCode() : null);
        item.setOdsFieldName(odsFieldName != null ? odsFieldName.toUpperCase() : null);
        item.setOdsFieldTitle(odsFieldTitle != null ? odsFieldTitle.toUpperCase() : odsFieldTitle);
        item.setRuleType(ruleType != null ? ruleType.getCode() : null);
        item.setFixedFlag(fixedFlag);
        return item;
    }

    protected List<Column> listTableColumn(String tableName) {
        Assert.isNotEmpty((String)tableName);
        List vchrColumns = JDialectUtil.getInstance().getTableColumns(EnvCenter.getTenantName(), tableName);
        if (CollectionUtils.isEmpty((Collection)vchrColumns)) {
            return CollectionUtils.newArrayList();
        }
        return vchrColumns.stream().map(item -> {
            Column column = new Column();
            column.setName(StringUtils.isEmpty((String)item.getColumnName()) ? "#" : item.getColumnName().toUpperCase());
            column.setTitle(column.getName());
            return column;
        }).collect(Collectors.toList());
    }

    protected List<FieldDTO> convert2Field(String dataSourceCode, String tableName, String tableTitle, List<Column> columns) {
        Assert.isNotEmpty((String)tableName);
        Assert.isNotEmpty((String)tableTitle);
        if (CollectionUtils.isEmpty(columns)) {
            return CollectionUtils.newArrayList();
        }
        String upperTableName = tableName.toUpperCase();
        String upperTableTitle = tableTitle.toUpperCase();
        return columns.stream().map(col -> {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setName(upperTableName + "." + col.getName());
            fieldDTO.setTitle(upperTableTitle + "." + col.getTitle());
            return fieldDTO;
        }).collect(Collectors.toList());
    }

    public Integer showOrder() {
        return 0;
    }
}

