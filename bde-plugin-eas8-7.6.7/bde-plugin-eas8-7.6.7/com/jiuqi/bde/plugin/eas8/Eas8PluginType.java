/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.LicenceSymbolEnum
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.enums.StorageType
 */
package com.jiuqi.bde.plugin.eas8;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.plugin.eas8.util.AssistPojo;
import com.jiuqi.bde.plugin.eas8.util.Eas8AssistProvider;
import com.jiuqi.common.base.util.StringUtils;
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
public class Eas8PluginType
extends IBdePluginType {
    private static final String SYMBOL = "EAS8X";
    private static final String TITLE = "\u3010\u91d1\u8776\u3011EAS8.0";
    @Autowired
    private Eas8AssistProvider assistProvider;

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
        return 410;
    }

    public String storageType() {
        return StorageType.ID.getCode();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO subjectField = this.buildBasicField("MD_ACCTSUBJECT", "\u79d1\u76ee");
        subjectField.setRuleType(RuleType.ID_TO_CODE.getCode());
        subjectField.setAdvancedSql("SELECT FID AS ID, FNUMBER AS CODE, FNAME_L2 AS NAME, CASE WHEN FDC = 1 THEN 1 ELSE -1 END AS ORIENT, FACCOUNTTABLEID, FCOMPANYID, FISLEAF FROM T_BD_ACCOUNTVIEW");
        return subjectField;
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO currencyField = this.buildBasicField("MD_CURRENCY", "\u5e01\u522b");
        currencyField.setRuleType(RuleType.ID_TO_CODE.getCode());
        currencyField.setAdvancedSql("SELECT FID AS ID, FNUMBER AS CODE, FNAME_L2 AS NAME FROM T_BD_CURRENCY");
        return currencyField;
    }

    public FieldDTO cfItemField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public List<FieldDTO> listAssistField(DataSchemeDTO dataSchemeDTO) {
        ArrayList<FieldDTO> fieldDTOList = new ArrayList<FieldDTO>();
        Map<String, AssistPojo> assistMap = this.assistProvider.listAssist(dataSchemeDTO.getDataSourceCode()).stream().collect(Collectors.toMap(BaseAcctAssist::getName, item -> item, (k1, k2) -> k1));
        for (AssistPojo assistPojo : assistMap.values()) {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setTableName(assistPojo.getTableName());
            fieldDTO.setName(assistPojo.getCode());
            fieldDTO.setTitle(assistPojo.getName());
            fieldDTO.setRuleType(RuleType.ID_TO_CODE.getCode());
            if (StringUtils.isEmpty((String)assistPojo.getGroupId())) {
                fieldDTO.setAdvancedSql(String.format("SELECT FID AS ID, FNAME_L2 AS NAME, FNUMBER AS CODE FROM %1$s ", assistPojo.getTableName()));
            } else {
                fieldDTO.setAdvancedSql(String.format("SELECT FID AS ID, FNAME_L2 AS NAME, FNUMBER AS CODE FROM %1$s WHERE FGROUPID = '%2$s' ", assistPojo.getTableName(), assistPojo.getGroupId()));
            }
            fieldDTOList.add(fieldDTO);
        }
        return fieldDTOList;
    }
}

