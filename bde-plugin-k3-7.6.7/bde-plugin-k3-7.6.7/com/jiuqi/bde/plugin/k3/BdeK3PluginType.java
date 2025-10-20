/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 *  com.jiuqi.dc.base.common.enums.LicenceSymbolEnum
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.enums.StorageType
 */
package com.jiuqi.bde.plugin.k3;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.plugin.k3.util.AssistPojo;
import com.jiuqi.bde.plugin.k3.util.K3AssistProvider;
import com.jiuqi.bde.plugin.k3.util.K3FetchUtil;
import com.jiuqi.dc.base.common.enums.LicenceSymbolEnum;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeK3PluginType
extends IBdePluginType {
    private static final String SYMBOL = "K3";
    private static final String TITLE = "\u3010\u91d1\u8776\u3011K3";
    @Autowired
    private K3AssistProvider assistProvider;

    public String getSymbol() {
        return SYMBOL;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getLicenceSymbol() {
        return LicenceSymbolEnum.KINGDEE.getSymbol();
    }

    public Integer getOrder() {
        return 420;
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
        Map<String, AssistPojo> assistMap = this.assistProvider.listAssist(dataSchemeDTO.getDataSourceCode()).stream().collect(Collectors.toMap(BaseAcctAssist::getCode, item -> item, (k1, k2) -> k1));
        for (AssistPojo assistPojo : assistMap.values()) {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setName(assistPojo.getCode());
            fieldDTO.setTitle(assistPojo.getName());
            fieldDTO.setTableName(assistPojo.getTableName());
            fieldDTO.setRuleType(RuleType.ID_TO_CODE.getCode());
            fieldDTO.setAdvancedSql(K3FetchUtil.fetchUtil.getAdvancedSqlByAssistPojo(assistPojo));
            fieldDTOList.add(fieldDTO);
        }
        return fieldDTOList;
    }
}

