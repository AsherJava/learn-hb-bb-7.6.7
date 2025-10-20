/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.dc.base.common.enums.LicenceSymbolEnum
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.enums.StorageType
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 */
package com.jiuqi.bde.plugin.va6_40;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.plugin.va6_40.util.Va6_40AssistPojo;
import com.jiuqi.bde.plugin.va6_40.util.Va6_40AssistProvider;
import com.jiuqi.bde.plugin.va6_40.util.Va6_40FetchUtil;
import com.jiuqi.dc.base.common.enums.LicenceSymbolEnum;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeVa6_40PluginType
extends IBdePluginType {
    private static final String SYMBOL = "VA6_40";
    private static final String TITLE = "\u3010\u4e45\u5176\u3011VA6_4.0";
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private Va6_40AssistProvider assistProvider;

    public String getSymbol() {
        return SYMBOL;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getLicenceSymbol() {
        return LicenceSymbolEnum.JIUQI.getSymbol();
    }

    public Integer getOrder() {
        return 210;
    }

    public String storageType() {
        return StorageType.ID.getCode();
    }

    public FieldDTO subjectField(DataSchemeDTO dataSchemeDTO) {
        FieldDTO subjectField = this.buildBasicField("MD_ACCTSUBJECT", "\u79d1\u76ee");
        subjectField.setRuleType(RuleType.NONE.getCode());
        Variable variable = new Variable();
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(dataSchemeDTO.getDataSourceCode()));
        StringBuilder subjectSql = new StringBuilder();
        subjectSql.append("SELECT ${RECID} AS ID,   \n");
        subjectSql.append("       T.STDCODE AS CODE,  \n");
        subjectSql.append("       T.STDNAME AS NAME,  \n");
        subjectSql.append("       (CASE WHEN T.ORIENT = HEXTORAW('00000000000000000000000000000001') THEN 1 ELSE -1 END) AS ORIENT  \n");
        subjectSql.append("  FROM ${TABLENAME} T  \n");
        subjectSql.append("  LEFT JOIN MD_FINORG ORG ON T.UNITID = ORG.RECID  \n");
        subjectSql.append(" WHERE 1 = 1  \n");
        subjectSql.append("   AND (  \n");
        subjectSql.append("            T.UNITID = ${EMPTYUUUID}  \n");
        subjectSql.append("         OR ORG.STDCODE = '#unitCode#' \n");
        subjectSql.append("       )  \n");
        subjectSql.append("   AND T.VALIDTIME <= ${VALIDTIME} AND T.INVALIDTIME > ${INVALIDTIME} \n");
        variable = new Variable();
        variable.put("RECID", sqlHandler.hex("T.RECID", false));
        variable.put("TABLENAME", "MD_ACCOUNTSUBJECT");
        variable.put("EMPTYUUUID", sqlHandler.unHex("00000000000000000000000000000000", true));
        variable.put("VALIDTIME", sqlHandler.toDate("'#year#-01-01 00:00:00'", "'" + sqlHandler.hyphenTimeStampFmt() + "'"));
        variable.put("INVALIDTIME", sqlHandler.toDate("'#year#-12-31 00:00:00'", "'" + sqlHandler.hyphenTimeStampFmt() + "'"));
        subjectField.setAdvancedSql(VariableParseUtil.parse((String)subjectSql.toString(), (Map)variable.getVariableMap()));
        return subjectField;
    }

    public FieldDTO currencyField(DataSchemeDTO dataSchemeDTO) {
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(dataSchemeDTO.getDataSourceCode()));
        FieldDTO currencyField = this.buildBasicField("MD_CURRENCY", "\u5e01\u522b");
        currencyField.setRuleType(RuleType.NONE.getCode());
        currencyField.setAdvancedSql(Va6_40FetchUtil.getBaseDataSql(sqlHandler, "MD_CURRENCY"));
        return currencyField;
    }

    public FieldDTO cfItemField(DataSchemeDTO dataSchemeDTO) {
        return null;
    }

    public List<FieldDTO> listAssistField(DataSchemeDTO dataSchemeDTO) {
        ArrayList<FieldDTO> fieldDTOList = new ArrayList<FieldDTO>();
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(dataSchemeDTO.getDataSourceCode()));
        Map<String, Va6_40AssistPojo> assistMap = this.assistProvider.listAssist(dataSchemeDTO.getDataSourceCode()).stream().collect(Collectors.toMap(BaseAcctAssist::getCode, item -> item, (k1, k2) -> k1));
        for (Va6_40AssistPojo assistPojo : assistMap.values()) {
            FieldDTO fieldDTO = this.buildBasicField(assistPojo.getCode(), assistPojo.getName());
            fieldDTO.setTableName(assistPojo.getTableName());
            fieldDTO.setRuleType(RuleType.ID_TO_CODE.getCode());
            fieldDTO.setAdvancedSql(Va6_40FetchUtil.getBaseDataSql(sqlHandler, assistPojo.getTableName()));
            fieldDTOList.add(fieldDTO);
        }
        return fieldDTOList;
    }
}

