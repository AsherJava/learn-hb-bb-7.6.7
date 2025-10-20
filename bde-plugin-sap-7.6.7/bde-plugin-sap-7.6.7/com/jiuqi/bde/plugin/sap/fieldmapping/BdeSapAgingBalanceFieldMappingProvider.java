/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.SourceDataTypeEnum
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.Column
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.enums.FieldMappingType
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 */
package com.jiuqi.bde.plugin.sap.fieldmapping;

import com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum;
import com.jiuqi.bde.plugin.sap.fieldmapping.AbstractBdeSapFieldMappingProvider;
import com.jiuqi.bde.plugin.sap.util.SapFetchUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.SourceDataTypeEnum;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.Column;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.enums.FieldMappingType;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BdeSapAgingBalanceFieldMappingProvider
extends AbstractBdeSapFieldMappingProvider {
    public String getCode() {
        return MemoryBalanceTypeEnum.ASSAGINGBALANCE.getCode();
    }

    public String getName() {
        return MemoryBalanceTypeEnum.ASSAGINGBALANCE.getBizModel();
    }

    public String getEffectTable() {
        return MemoryBalanceTypeEnum.ASSAGINGBALANCE.getCode();
    }

    public List<FieldDTO> listOdsField(DataSchemeDTO dataSchemeDTO) {
        String dataSourceCode = dataSchemeDTO.getDataSourceCode();
        ArrayList<FieldDTO> fields = new ArrayList<FieldDTO>();
        String dbSchemeCode = SapFetchUtil.getDbSchemeCode(dataSchemeDTO);
        List<Column> bsadColumnList = this.getColumnsByTableName(dbSchemeCode, dataSourceCode, "BSAD");
        List<Column> bsidColumnList = this.getColumnsByTableName(dbSchemeCode, dataSourceCode, "BSID");
        List<Column> bsakColumnList = this.getColumnsByTableName(dbSchemeCode, dataSourceCode, "BSAK");
        List<Column> bsikColumnList = this.getColumnsByTableName(dbSchemeCode, dataSourceCode, "BSIK");
        Set bsadColumnSet = bsadColumnList.stream().map(Column::getName).collect(Collectors.toSet());
        Set bsidColumnSet = bsidColumnList.stream().map(Column::getName).collect(Collectors.toSet());
        Set bsakColumnSet = bsakColumnList.stream().map(Column::getName).collect(Collectors.toSet());
        Set bsikColumnSet = bsikColumnList.stream().map(Column::getName).collect(Collectors.toSet());
        ArrayList<Column> columnList = new ArrayList<Column>(bsadColumnList.size());
        bsadColumnList.forEach(column -> {
            if (bsadColumnSet.contains(column.getName()) && bsidColumnSet.contains(column.getName()) && bsakColumnSet.contains(column.getName()) && bsikColumnSet.contains(column.getName())) {
                columnList.add((Column)column);
            }
        });
        List<Object> fieldDTOS = this.convert2Field(dataSourceCode, "T", "T", columnList);
        fieldDTOS = fieldDTOS.stream().sorted(new Comparator<FieldDTO>(){

            @Override
            public int compare(FieldDTO o1, FieldDTO o2) {
                return o1.getName().compareTo(o2.getName());
            }
        }).collect(Collectors.toList());
        fields.addAll(fieldDTOS);
        FieldDTO customer = new FieldDTO("TRIM(T.KUNNR)", "\u5ba2\u6237");
        FieldDTO supplier = new FieldDTO("TRIM(T.LIFNR)", "\u4f9b\u5e94\u5546");
        fields.add(customer);
        fields.add(supplier);
        for (FieldDTO fieldDTO : fields) {
            fieldDTO.setRuleType(RuleType.NONE.getCode());
        }
        fields.addAll(this.getAssistList().stream().filter(e -> !StringUtils.isEmpty((String)e.getAgingUnClearTableField())).map(col -> {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setName("T." + col.getAgingUnClearTableField());
            fieldDTO.setTitle("T." + col.getName());
            fieldDTO.setTableName(col.getAgingUnClearTableField());
            fieldDTO.setRuleType(RuleType.NONE.getCode());
            fieldDTO.setFieldMappingType(FieldMappingType.SOURCE_FIELD.getCode());
            fieldDTO.setIsolationStrategy(IsolationStrategy.SHARE.getCode());
            fieldDTO.setIsolationStrategyFixedFlag(Boolean.valueOf(false));
            if (SourceDataTypeEnum.DIRECT_TYPE.getCode().equals(dataSchemeDTO.getSourceDataType())) {
                fieldDTO.setAdvancedSql(col.getAdvancedSql());
            } else {
                fieldDTO.setAdvancedSql(col.getAdvancedSql().replace(col.getOdsTableName(), DataRefUtil.getOdsTableName((String)dataSchemeDTO.getCode(), (String)col.getOdsTableName())));
            }
            return fieldDTO;
        }).collect(Collectors.toList()));
        return fields;
    }
}

