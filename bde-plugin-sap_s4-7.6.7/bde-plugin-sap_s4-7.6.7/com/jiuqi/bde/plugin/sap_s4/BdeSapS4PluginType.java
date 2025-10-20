/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.LicenceSymbolEnum
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption
 *  com.jiuqi.dc.mappingscheme.impl.enums.StorageType
 */
package com.jiuqi.bde.plugin.sap_s4;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.sap_s4.option.SapS4DbSchemeOption;
import com.jiuqi.bde.plugin.sap_s4.util.SapS4AssistUtil;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.LicenceSymbolEnum;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeOption;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeSapS4PluginType
extends IBdePluginType {
    private static final String SYMBOL = "SAP_S4";
    private static final String TITLE = "\u3010SAP\u3011S4";
    @Autowired
    private SapS4AssistUtil sapS4AssistUtil;
    @Autowired
    private SapS4DbSchemeOption sapS4DbSchemeOption;

    public String getSymbol() {
        return SYMBOL;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getLicenceSymbol() {
        return LicenceSymbolEnum.SAP.getSymbol();
    }

    public Integer getOrder() {
        return 450;
    }

    public List<IDataSchemeOption> getExternalOptionList() {
        ArrayList optionList = CollectionUtils.newArrayList();
        optionList.add(this.sapS4DbSchemeOption);
        return optionList;
    }

    public String storageType() {
        return StorageType.CODE.getCode();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO subjectField = this.buildBasicField("MD_ACCTSUBJECT", "\u79d1\u76ee");
        subjectField.setRuleType(RuleType.NONE.getCode());
        subjectField.setAdvancedSql("SELECT SAKNR AS ID ,SAKNR AS CODE, MAX(TXT50) AS NAME FROM SKAT GROUP BY SAKNR ");
        return subjectField;
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO currencyField = this.buildBasicField("MD_CURRENCY", "\u5e01\u522b");
        currencyField.setRuleType(RuleType.NONE.getCode());
        currencyField.setAdvancedSql("SELECT WAERS AS ID, WAERS AS CODE, MAX(KTEXT) AS NAME FROM TCURT GROUP BY WAERS ");
        return currencyField;
    }

    public FieldDTO cfItemField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO cfItemField = this.buildBasicField("MD_CFITEM", "\u73b0\u6d41\u9879\u76ee");
        cfItemField.setRuleType(RuleType.NONE.getCode());
        cfItemField.setAdvancedSql("SELECT RSTGR AS ID, RSTGR AS CODE, MAX(TXT20) AS NAME FROM T053S GROUP BY RSTGR");
        return cfItemField;
    }

    public List<FieldDTO> listAssistField(DataSchemeDTO dataSchemeDTO) {
        List<FieldDTO> fields = this.sapS4AssistUtil.getDefaultAssistList().stream().filter(e -> !StringUtils.isEmpty((String)e.getVoucherTableField()) && !StringUtils.isEmpty((String)e.getConditionVal())).map(col -> {
            FieldDTO fieldDTO = this.buildBasicField("T." + col.getVoucherTableField(), col.getName());
            fieldDTO.setAdvancedSql(col.getConditionVal());
            return fieldDTO;
        }).collect(Collectors.toList());
        return fields;
    }

    public boolean assistFieldFlag() {
        return false;
    }
}

