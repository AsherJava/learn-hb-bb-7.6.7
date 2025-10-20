/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.dc.base.common.enums.LicenceSymbolEnum
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.enums.StorageType
 */
package com.jiuqi.bde.plugin.gs_cloud;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.plugin.gs_cloud.util.AssistPojo;
import com.jiuqi.bde.plugin.gs_cloud.util.GsCloudAssistProvider;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.dc.base.common.enums.LicenceSymbolEnum;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeGsCloudPluginType
extends IBdePluginType {
    private static final String SYMBOL = "GS_CLOUD";
    private static final String TITLE = "\u3010\u6d6a\u6f6e\u3011GSCloud";
    @Autowired
    private GsCloudAssistProvider assistProvider;

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
        return 620;
    }

    public String storageType() {
        return StorageType.ID.getCode();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO subjectField = this.buildBasicField("MD_ACCTSUBJECT", "\u79d1\u76ee");
        subjectField.setRuleType(RuleType.ID_TO_CODE.getCode());
        StringBuilder subjectSql = new StringBuilder();
        subjectSql.append("SELECT T.ID AS ID,   \n");
        subjectSql.append("       T.CODE AS CODE,  \n");
        subjectSql.append("       T.NAME_CHS AS NAME,  \n");
        subjectSql.append("       CASE WHEN T.BALANCEDIR = 1 THEN 1 ELSE -1 END AS ORIENT,  \n");
        subjectSql.append("       T.ACCOUNTTYPE,  \n");
        subjectSql.append("       T.CASHACCTITLE  \n");
        subjectSql.append(String.format("  FROM BFACCOUNTTITLE%s T \n", DateUtils.getYearOfDate((Date)new Date())));
        subjectSql.append(" WHERE 1=1 \n");
        subjectSql.append("   AND (T.ACCORGID IS NULL OR T.ACCORGID = '' OR T.ACCORGID = (SELECT ID FROM BFACCOUNTINGORGANIZATION WHERE CODE = '${UNITCODE}')) \n");
        subjectField.setAdvancedSql(subjectSql.toString());
        return subjectField;
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO currencyField = this.buildBasicField("MD_CURRENCY", "\u5e01\u522b");
        currencyField.setRuleType(RuleType.ID_TO_CODE.getCode());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ID AS ID,   \n");
        sql.append("       T.CODE AS CODE,  \n");
        sql.append("       T.NAME_CHS AS NAME  \n");
        sql.append("  FROM BFCURRENCY T  \n");
        currencyField.setAdvancedSql(sql.toString());
        return currencyField;
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

