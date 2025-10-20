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
package com.jiuqi.bde.plugin.u8;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.u8.assist.U8AssistPojo;
import com.jiuqi.bde.plugin.u8.assist.U8AssistProvider;
import com.jiuqi.bde.plugin.u8.util.BdeU8FetchUtil;
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
public class BdeU8PluginType
extends IBdePluginType {
    private static final String SYMBOL = "U8";
    private static final String TITLE = "\u3010\u7528\u53cb\u3011U8";
    @Autowired
    private U8AssistProvider assistProvider;

    public String getSymbol() {
        return SYMBOL;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getLicenceSymbol() {
        return LicenceSymbolEnum.YONYOU.getSymbol();
    }

    public Integer getOrder() {
        return 330;
    }

    public String storageType() {
        return StorageType.ID.getCode();
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
        ArrayList<FieldDTO> fieldDTOList = new ArrayList<FieldDTO>();
        List<U8AssistPojo> u8AssistPojoList = this.assistProvider.listAssist(dataSchemeDTO.getDataSourceCode());
        for (U8AssistPojo u8AssistPojo : u8AssistPojoList) {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setName(u8AssistPojo.getOdsTableName());
            fieldDTO.setTitle(u8AssistPojo.getName());
            fieldDTO.setTableName(u8AssistPojo.getOdsTableName());
            fieldDTO.setRuleType(RuleType.ID_TO_CODE.getCode());
            String advancedSql = String.format(BdeU8FetchUtil.SQL_TEMP, u8AssistPojo.getTablePk(), u8AssistPojo.getNameField(), u8AssistPojo.getOdsTableName());
            fieldDTO.setAdvancedSql(advancedSql);
            fieldDTOList.add(fieldDTO);
        }
        return fieldDTOList;
    }
}

