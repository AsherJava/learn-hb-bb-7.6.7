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
package com.jiuqi.bde.plugin.bip_flagship;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.plugin.bip_flagship.util.AssistPojo;
import com.jiuqi.bde.plugin.bip_flagship.util.BipFlagAssistProvider;
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
public class BdeBipFlagShipPluginType
extends IBdePluginType {
    private static final String SYMBOL = "BIP_FLAGSHIP";
    private static final String TITLE = "\u3010\u7528\u53cb\u3011BIP_\u65d7\u8230\u7248";
    private static final String SQL_TEMPLATE = " SELECT %1$s AS ID,%2$s AS CODE,%3$s AS NAME FROM %4$s ";
    private static final String DOC_SQL_TEMPLATE = "SELECT DOC.ID as ID,DOC.CODE as CODE,DOC.NAME as NAME FROM BD_CUST_DOC_DEF DEF INNER JOIN BD_CUST_DOC DOC on DOC.CUSTDOCDEFID = DEF.ID where DEF.CODE = '%1$s'";
    private static final String ATTRIBUTE_SQL_TEMPLATE = " SELECT ASS2.%1$s AS ID,ASS1.%2$s AS CODE,ASS1.%3$s AS NAME FROM %4$s ASS1 INNER JOIN %5$s ASS2 ON ASS1.%6$s=ASS2.%7$s ";
    @Autowired
    private BipFlagAssistProvider assistProvider;

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
        return 350;
    }

    public String storageType() {
        return StorageType.ID.getCode();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO subjectField = this.buildBasicField("MD_ACCTSUBJECT", "\u79d1\u76ee");
        subjectField.setRuleType(RuleType.ID_TO_CODE.getCode());
        StringBuilder subjectSql = new StringBuilder();
        subjectSql.append(" SELECT\n");
        subjectSql.append("     SUBJECT.ID AS ID,\n");
        subjectSql.append("     SUBJECT.CODE AS CODE,\n");
        subjectSql.append("     SUBJECT.NAME AS NAME,\n");
        subjectSql.append("     CASE\n");
        subjectSql.append("         WHEN DIRECTION = 'DEBIT' THEN 1\n");
        subjectSql.append("         ELSE -1\n");
        subjectSql.append("     END AS ORIENT,\n");
        subjectSql.append("     BOOK.CODE AS BOOKCODE\n");
        subjectSql.append(" FROM\n");
        subjectSql.append("     EPUB_ACCSUBJECT SUBJECT\n");
        subjectSql.append(" JOIN EPUB_ACCSUBJECTCHART SCHART ON\n");
        subjectSql.append("     SCHART.ID = SUBJECT.ACCSUBJECTCHART\n");
        subjectSql.append(" JOIN EPUB_ACCOUNTBOOK BOOK ON\n");
        subjectSql.append("     SCHART.ID = BOOK.ACCSUBJECTCHART\n");
        subjectSql.append(" WHERE\n");
        subjectSql.append("     SCHART.STOPSTATUS = 0\n");
        subjectField.setAdvancedSql(subjectSql.toString());
        return subjectField;
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO currencyField = this.buildBasicField("MD_CURRENCY", "\u5e01\u522b");
        currencyField.setRuleType(RuleType.ID_TO_CODE.getCode());
        currencyField.setAdvancedSql("SELECT ID AS ID ,CODE AS CODE,NAME AS NAME FROM  BD_CURRENCY_TENANT");
        return currencyField;
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
            fieldDTO.setTableName(assistPojo.getTableName() == null ? "" : assistPojo.getTableName());
            fieldDTO.setRuleType(RuleType.ID_TO_CODE.getCode());
            String sql = !StringUtils.isEmpty((String)assistPojo.getTableName()) ? (!StringUtils.isEmpty((String)assistPojo.getPkField()) ? String.format(ATTRIBUTE_SQL_TEMPLATE, assistPojo.getMainTableId(), assistPojo.getCodeField(), assistPojo.getNameField(), assistPojo.getTableName(), assistPojo.getMainTableName(), assistPojo.getMainTableId(), assistPojo.getPkField()) : String.format(SQL_TEMPLATE, assistPojo.getTableId(), assistPojo.getCodeField(), assistPojo.getNameField(), assistPojo.getTableName())) : (assistPojo.getCode().toUpperCase().startsWith("UCFDEF_") ? String.format(DOC_SQL_TEMPLATE, assistPojo.getCode().substring(assistPojo.getCode().indexOf("_") + 1)) : String.format("SELECT ID,CODE,NAME FROM %1$s", assistPojo.getCode()));
            fieldDTO.setAdvancedSql(sql);
            fieldDTOList.add(fieldDTO);
        }
        return fieldDTOList;
    }
}

