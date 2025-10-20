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
package com.jiuqi.bde.plugin.sap;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.sap.option.SapDbSchemeOption;
import com.jiuqi.bde.plugin.sap.option.SapIncludePlDataOption;
import com.jiuqi.bde.plugin.sap.util.SapAssistUtil;
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
public class BdeSapPluginType
extends IBdePluginType {
    private static final String SYMBOL = "SAP";
    private static final String TITLE = "\u3010SAP\u3011ECC";
    @Autowired
    private SapIncludePlDataOption includePlDataOption;
    @Autowired
    private SapDbSchemeOption sapDbSchemeOption;
    @Autowired
    private SapAssistUtil assistUtil;

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
        return 110;
    }

    public List<IDataSchemeOption> getExternalOptionList() {
        ArrayList optionList = CollectionUtils.newArrayList();
        optionList.add(this.includePlDataOption);
        optionList.add(this.sapDbSchemeOption);
        return optionList;
    }

    public String storageType() {
        return StorageType.CODE.getCode();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO subjectField = this.buildBasicField("MD_ACCTSUBJECT", "\u79d1\u76ee");
        subjectField.setRuleType(RuleType.NONE.getCode());
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT SKAT.MANDT AS MANDT,  ");
        sql.append("       SKAT.SAKNR AS ID,  ");
        sql.append("       SKAT.SAKNR AS CODE,  ");
        sql.append("       MAX(SKAT.TXT20) AS NAME  ");
        sql.append("  FROM SKAT  ");
        sql.append("  JOIN SKA1  ");
        sql.append("    ON SKA1.MANDT = SKAT.MANDT  ");
        sql.append("   AND SKA1.KTOPL = SKAT.KTOPL  ");
        sql.append("   AND SKA1.SAKNR = SKAT.SAKNR  ");
        sql.append(" WHERE 1 = 1 AND SKAT.SPRAS ='1' ");
        sql.append(" GROUP BY SKAT.MANDT,SKAT.SAKNR ");
        subjectField.setAdvancedSql(sql.toString());
        return subjectField;
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO currencyField = this.buildBasicField("MD_CURRENCY", "\u5e01\u522b");
        currencyField.setRuleType(RuleType.NONE.getCode());
        currencyField.setAdvancedSql("SELECT WAERS AS ID ,WAERS AS CODE, MAX(KTEXT) AS NAME FROM TCURT GROUP BY WAERS");
        return currencyField;
    }

    public FieldDTO cfItemField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO cfItemField = this.buildBasicField("MD_CFITEM", "\u73b0\u6d41\u9879\u76ee");
        cfItemField.setRuleType(RuleType.NONE.getCode());
        cfItemField.setAdvancedSql("SELECT RSTGR AS ID, RSTGR AS CODE, MAX(TXT20) AS NAME FROM T053S GROUP BY RSTGR");
        return cfItemField;
    }

    public List<FieldDTO> listAssistField(DataSchemeDTO dataSchemeDTO) {
        List<FieldDTO> fields = this.assistUtil.getDefaultAssistList().stream().filter(e -> !StringUtils.isEmpty((String)e.getVoucherTableField()) && !StringUtils.isEmpty((String)e.getAdvancedSql())).map(col -> {
            FieldDTO fieldDTO = this.buildBasicField("T." + col.getVoucherTableField(), col.getName());
            fieldDTO.setAdvancedSql(col.getAdvancedSql());
            return fieldDTO;
        }).collect(Collectors.toList());
        return fields;
    }

    public boolean assistFieldFlag() {
        return false;
    }
}

