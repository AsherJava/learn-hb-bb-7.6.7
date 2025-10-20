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
package com.jiuqi.bde.plugin.gs5;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.gs5.util.AssistPojo;
import com.jiuqi.bde.plugin.gs5.util.Gs5AssistProvider;
import com.jiuqi.dc.base.common.enums.LicenceSymbolEnum;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeGs5PluginType
extends IBdePluginType {
    private static final String SYMBOL = "GS5";
    private static final String TITLE = "\u3010\u6d6a\u6f6e\u3011GS5";
    @Autowired
    private Gs5AssistProvider assistProvider;

    public String getSymbol() {
        return SYMBOL;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getLicenceSymbol() {
        return LicenceSymbolEnum.GS.getSymbol();
    }

    public Integer getOrder() {
        return 610;
    }

    public String storageType() {
        return StorageType.ID.getCode();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        return this.buildBasicField("MD_ACCTSUBJECT", "\u79d1\u76ee");
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        return this.buildBasicField("MD_CURRENCY", "\u5e01\u522b");
    }

    public FieldDTO cfItemField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public List<FieldDTO> listAssistField(DataSchemeDTO dataSchemeDTO) {
        ArrayList<FieldDTO> fieldDTOList = new ArrayList<FieldDTO>();
        List<AssistPojo> assistPojoList = this.assistProvider.listAssist(dataSchemeDTO.getDataSourceCode());
        for (AssistPojo assistPojo : assistPojoList) {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setName(assistPojo.getCode());
            fieldDTO.setTitle(assistPojo.getName());
            fieldDTO.setTableName(assistPojo.getOdsTableName());
            fieldDTO.setRuleType(RuleType.ID_TO_CODE.getCode());
            fieldDTO.setAdvancedSql(assistPojo.getAssSql());
            fieldDTOList.add(fieldDTO);
        }
        return fieldDTOList;
    }
}

