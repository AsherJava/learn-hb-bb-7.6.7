/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.dc.base.common.enums.LicenceSymbolEnum
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.enums.StorageType
 */
package com.jiuqi.bde.plugin.gcreport;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.gcreport.assist.GcreportAssistPojo;
import com.jiuqi.bde.plugin.gcreport.assist.GcreportAssistProvider;
import com.jiuqi.dc.base.common.enums.LicenceSymbolEnum;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeGcreportPluginType
extends IBdePluginType {
    private static final String SYMBOL = "GCREPORT";
    private static final String TITLE = "\u3010\u4e45\u5176\u3011\u5408\u5e76\u62a5\u8868";
    @Autowired
    private GcreportAssistProvider assistProvider;

    public String getSymbol() {
        return SYMBOL;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getLicenceSymbol() {
        return LicenceSymbolEnum.PROJECT.getSymbol();
    }

    public Integer getOrder() {
        return 260;
    }

    public String storageType() {
        return StorageType.CODE.getCode();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public FieldDTO cfItemField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public List<FieldDTO> listAssistField(DataSchemeDTO dataSchemeDTO) {
        List<GcreportAssistPojo> assistList = this.assistProvider.listAssist(dataSchemeDTO.getDataSourceCode());
        return assistList.stream().map(col -> {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setName(col.getCode());
            fieldDTO.setTitle(col.getName());
            fieldDTO.setRuleType(RuleType.ID_TO_CODE.getCode());
            fieldDTO.setTableName(col.getTableName());
            fieldDTO.setAdvancedSql(String.format("SELECT ID AS ID, CODE AS CODE, NAME AS NAME FROM %1$s ", col.getTableName()));
            return fieldDTO;
        }).collect(Collectors.toList());
    }
}

