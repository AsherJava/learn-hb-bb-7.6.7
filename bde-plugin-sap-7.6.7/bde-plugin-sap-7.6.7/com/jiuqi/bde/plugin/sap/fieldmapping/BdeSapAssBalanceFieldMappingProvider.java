/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.Column
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.enums.FieldMappingType
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 */
package com.jiuqi.bde.plugin.sap.fieldmapping;

import com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum;
import com.jiuqi.bde.plugin.sap.fieldmapping.AbstractBdeSapFieldMappingProvider;
import com.jiuqi.bde.plugin.sap.util.SapFetchUtil;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDTO;
import com.jiuqi.dc.mappingscheme.impl.common.Column;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.enums.FieldMappingType;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BdeSapAssBalanceFieldMappingProvider
extends AbstractBdeSapFieldMappingProvider {
    public String getCode() {
        return MemoryBalanceTypeEnum.ASSBALANCE.getCode();
    }

    public String getName() {
        return MemoryBalanceTypeEnum.ASSBALANCE.getBizModel();
    }

    public String getEffectTable() {
        return MemoryBalanceTypeEnum.ASSBALANCE.getCode();
    }

    public List<FieldMappingDTO> listFixedFieldMapping(String dataSchemeCode) {
        return CollectionUtils.newArrayList();
    }

    public List<FieldDTO> listOdsField(DataSchemeDTO dataSchemeDTO) {
        List<FieldDTO> sapFields = this.getAssistList().stream().filter(e -> !StringUtils.isEmpty((String)e.getBalanceTableField())).map(col -> {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setName("T." + col.getVoucherTableField());
            fieldDTO.setTitle("T." + col.getName());
            fieldDTO.setTableName(col.getVoucherTableField());
            fieldDTO.setRuleType(RuleType.NONE.getCode());
            fieldDTO.setAdvancedSql(col.getAdvancedSql());
            fieldDTO.setFieldMappingType(FieldMappingType.SOURCE_FIELD.getCode());
            fieldDTO.setIsolationStrategy(IsolationStrategy.SHARE.getCode());
            fieldDTO.setIsolationStrategyFixedFlag(Boolean.valueOf(false));
            return fieldDTO;
        }).collect(Collectors.toList());
        String virtualTable = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(dataSchemeDTO.getDataSourceCode())).getVirtualTable();
        List<Column> columnList = this.getColumnsByTableName(SapFetchUtil.getDbSchemeCode(dataSchemeDTO), dataSchemeDTO.getDataSourceCode(), "FAGLFLEXT");
        if (!CollectionUtils.isEmpty(columnList)) {
            sapFields.addAll(columnList.stream().filter(e -> sapFields.stream().noneMatch(o -> Objects.equals("T" + e.getName(), o.getName()))).map(e -> {
                FieldDTO fieldDTO = new FieldDTO();
                fieldDTO.setName(String.format("TRIM(%1$s.%2$s)", "T", e.getName()));
                fieldDTO.setTitle(String.format("TRIM(%1$s.%2$s)", "T", e.getName()));
                fieldDTO.setTableName(virtualTable);
                fieldDTO.setRuleType(RuleType.NONE.getCode());
                fieldDTO.setFieldMappingType(FieldMappingType.SOURCE_FIELD.getCode());
                fieldDTO.setIsolationStrategy(IsolationStrategy.SHARE.getCode());
                fieldDTO.setIsolationStrategyFixedFlag(Boolean.valueOf(false));
                fieldDTO.setAdvancedSql("SELECT 'ID' AS ID, 'CODE' AS CODE,'NAME' AS NAME FROM " + virtualTable);
                return fieldDTO;
            }).sorted(new Comparator<FieldDTO>(){

                @Override
                public int compare(FieldDTO o1, FieldDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            }).collect(Collectors.toList()));
        }
        return sapFields;
    }
}

