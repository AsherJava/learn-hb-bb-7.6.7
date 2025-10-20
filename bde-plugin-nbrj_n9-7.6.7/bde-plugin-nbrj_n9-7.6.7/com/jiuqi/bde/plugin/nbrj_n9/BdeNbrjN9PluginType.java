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
package com.jiuqi.bde.plugin.nbrj_n9;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.nbrj_n9.util.AssistPojo;
import com.jiuqi.bde.plugin.nbrj_n9.util.NbrjN9AssistProvider;
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
public class BdeNbrjN9PluginType
extends IBdePluginType {
    private static final String SYMBOL = "NBRJ_N9";
    private static final String TITLE = "\u3010\u5357\u5317\u8f6f\u4ef6\u3011N9";
    @Autowired
    private NbrjN9AssistProvider assistProvider;

    public String getSymbol() {
        return SYMBOL;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getLicenceSymbol() {
        return LicenceSymbolEnum.NBRJ.getSymbol();
    }

    public Integer getOrder() {
        return 810;
    }

    public String storageType() {
        return StorageType.ID.getCode();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO subjectField = this.buildBasicField("MD_ACCTSUBJECT", "\u79d1\u76ee");
        subjectField.setRuleType(RuleType.ID_TO_CODE.getCode());
        subjectField.setAdvancedSql("SELECT ACODE AS ID, ACODE AS CODE, LANGNAME1 AS NAME, CASE WHEN DC = 1 THEN 1 ELSE -1 END AS ORIENT FROM ACODE");
        return subjectField;
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        return this.buildBasicField("MD_CURRENCY", "\u5e01\u522b");
    }

    public FieldDTO cfItemField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public List<FieldDTO> listAssistField(DataSchemeDTO dataSchemeDTO) {
        ArrayList<FieldDTO> fieldDTOList = new ArrayList<FieldDTO>();
        List<AssistPojo> assistPojoList = this.assistProvider.listAssist("");
        for (AssistPojo assistPojo : assistPojoList) {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setTableName(assistPojo.getTableName());
            fieldDTO.setName(assistPojo.getCode());
            fieldDTO.setTitle(assistPojo.getName());
            fieldDTO.setRuleType(RuleType.ID_TO_CODE.getCode());
            fieldDTO.setAdvancedSql(assistPojo.getAdvancedSql());
            fieldDTOList.add(fieldDTO);
        }
        return fieldDTOList;
    }
}

