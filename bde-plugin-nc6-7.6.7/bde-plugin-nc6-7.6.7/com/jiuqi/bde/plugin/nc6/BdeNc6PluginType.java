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
package com.jiuqi.bde.plugin.nc6;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.plugin.nc6.assist.Nc6AssistPojo;
import com.jiuqi.bde.plugin.nc6.assist.Nc6AssistProvider;
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
public class BdeNc6PluginType
extends IBdePluginType {
    private static final String SYMBOL = "NC6X";
    private static final String TITLE = "\u3010\u7528\u53cb\u3011NC6X/NCC";
    @Autowired
    private Nc6AssistProvider assistProvider;

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
        return 320;
    }

    public String storageType() {
        return StorageType.ID.getCode();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO subjectField = this.buildBasicField("MD_ACCTSUBJECT", "\u79d1\u76ee");
        subjectField.setTableName("BD_ACCASOA");
        subjectField.setRuleType(RuleType.NONE.getCode());
        String advancedSql = "SELECT SUBJECTITEM.PK_ACCASOA AS ID,\n       SUBJECT.CODE AS CODE,\n       SUBJECTITEM.DISPNAME AS NAME,\n       CASE\n         WHEN SUBJECT.BALANORIENT = 0 THEN\n          1\n         ELSE\n          -1\n       END AS ORIENT,\n       SUBJECTCHART.BEGINPERIOD BEGINPERIOD,\n       SUBJECTCHART.ENDPERIOD ENDPERIOD,\n       ACCTBOOK.CODE AS BOOKCODE\n  FROM BD_ACCASOA SUBJECTITEM\n  JOIN BD_ACCOUNT SUBJECT\n    ON SUBJECTITEM.PK_ACCOUNT = SUBJECT.PK_ACCOUNT\n  JOIN BD_ACCCHART SUBJECTCHART\n    ON SUBJECTCHART.PK_ACCCHART = SUBJECTITEM.PK_ACCCHART\n  JOIN ORG_ACCOUNTINGBOOK ACCTBOOK\n    ON ACCTBOOK.PK_CURRACCCHART = SUBJECTCHART.ORIGINALCHART\nWHERE 1 = 1\n   AND SUBJECTCHART.TEMPVERSIONFLAG <> 'Y'";
        subjectField.setAdvancedSql(advancedSql);
        return subjectField;
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO currencyField = this.buildBasicField("MD_CURRENCY", "\u5e01\u522b");
        currencyField.setTableName("BD_CURRTYPE");
        currencyField.setRuleType(RuleType.ID_TO_CODE.getCode());
        currencyField.setAdvancedSql("SELECT PK_CURRTYPE AS ID ,CODE AS CODE,NAME AS NAME FROM BD_CURRTYPE");
        return currencyField;
    }

    public FieldDTO cfItemField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public List<FieldDTO> listAssistField(DataSchemeDTO dataSchemeDTO) {
        ArrayList<FieldDTO> fieldDTOList = new ArrayList<FieldDTO>();
        Map<String, Nc6AssistPojo> assistMap = this.assistProvider.listAssist(dataSchemeDTO.getDataSourceCode()).stream().collect(Collectors.toMap(BaseAcctAssist::getCode, item -> item, (k1, k2) -> k1));
        for (Nc6AssistPojo assistPojo : assistMap.values()) {
            FieldDTO fieldDTO = this.buildBasicField(assistPojo.getCode(), assistPojo.getName());
            fieldDTO.setTableName(assistPojo.getTableName());
            String assistField = "PK_" + assistPojo.getTableName().substring(assistPojo.getTableName().indexOf("_") + 1);
            if ("\u5ba2\u5546".equals(assistPojo.getName()) || "\u5185\u90e8\u5ba2\u5546".equals(assistPojo.getName())) {
                fieldDTO.setAdvancedSql("SELECT PK_CUST_SUP AS ID, CODE AS CODE, NAME AS NAME FROM " + assistPojo.getTableName());
            } else if ("\u94f6\u884c\u8d26\u6237".equals(assistPojo.getName())) {
                fieldDTO.setAdvancedSql("SELECT PK_BANKACCBAS AS ID, CODE AS CODE, NAME AS NAME FROM " + assistPojo.getTableName());
            } else if ("\u94f6\u884c\u7c7b\u522b".equals(assistPojo.getName())) {
                fieldDTO.setAdvancedSql("SELECT PK_BANKTYPE AS ID, CODE AS CODE, NAME AS NAME FROM " + assistPojo.getTableName());
            } else if ("bd_defdoc".equals(assistPojo.getTableName())) {
                fieldDTO.setAdvancedSql("SELECT " + assistField + " AS ID, CODE AS CODE, NAME AS NAME FROM BD_DEFDOC DOC WHERE DOC.PK_DEFDOCLIST IN (SELECT DOCLIST.PK_DEFDOCLIST FROM BD_DEFDOCLIST DOCLIST WHERE  1=1 AND CODE = '" + assistPojo.getCode() + "')");
            } else if ("bd_project".equals(assistPojo.getTableName()) || "\u9879\u76ee".equals(assistPojo.getName())) {
                fieldDTO.setAdvancedSql("SELECT  PK_PROJECT AS ID, PROJECT_CODE AS CODE, PROJECT_NAME AS NAME FROM BD_PROJECT");
            } else {
                fieldDTO.setAdvancedSql("SELECT " + assistField + " AS ID,CODE AS CODE,NAME AS NAME FROM " + assistPojo.getTableName());
            }
            fieldDTOList.add(fieldDTO);
        }
        return fieldDTOList;
    }
}

