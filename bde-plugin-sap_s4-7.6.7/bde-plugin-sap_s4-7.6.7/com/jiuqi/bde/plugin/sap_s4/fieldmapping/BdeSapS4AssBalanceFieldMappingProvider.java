/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.Column
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 */
package com.jiuqi.bde.plugin.sap_s4.fieldmapping;

import com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum;
import com.jiuqi.bde.plugin.sap_s4.fieldmapping.AbstractBdeSapS4FieldMappingProvider;
import com.jiuqi.bde.plugin.sap_s4.util.SapS4FetchUtil;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.Column;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BdeSapS4AssBalanceFieldMappingProvider
extends AbstractBdeSapS4FieldMappingProvider {
    public String getCode() {
        return MemoryBalanceTypeEnum.ASSBALANCE.getCode();
    }

    public String getName() {
        return MemoryBalanceTypeEnum.ASSBALANCE.getBizModel();
    }

    public String getEffectTable() {
        return MemoryBalanceTypeEnum.ASSBALANCE.getCode();
    }

    public List<FieldDTO> listOdsField(DataSchemeDTO dataSchemeDTO) {
        HashMap fieldMap = CollectionUtils.newHashMap();
        List<FieldDTO> fields = this.getAssistList().stream().filter(e -> !StringUtils.isEmpty((String)e.getBalanceTableField())).map(col -> {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setName("T." + col.getBalanceTableField());
            fieldDTO.setTitle(col.getName());
            fieldDTO.setTableName(col.getTableName());
            fieldDTO.setRuleType(RuleType.NONE.getCode());
            fieldDTO.setAdvancedSql(col.getConditionVal());
            fieldMap.put(col.getBalanceTableField(), col);
            return fieldDTO;
        }).collect(Collectors.toList());
        List<Column> odsItemColumns = this.getColumnsByTableName(SapS4FetchUtil.getDbSchemeCode(dataSchemeDTO), dataSchemeDTO.getDataSourceCode(), "ACDOCA");
        odsItemColumns.removeIf(column -> !Objects.isNull(fieldMap.get(column.getName())));
        fields.addAll(this.convert2Field(null, "T", "\u7edf\u4e00\u65e5\u8bb0\u8d26", odsItemColumns));
        return fields;
    }
}

