/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue
 *  com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil
 *  com.jiuqi.bde.bizmodel.execute.util.TempTableUtils
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.eas8.fetch;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue;
import com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.eas8.util.Eas8FetchUtil;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Eas8BalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;

    protected FetchData queryData(BalanceCondition condi) {
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        UnitAndBookValue unitAndBookValue = OrgSqlUtil.getUnitAndBookValue((OrgMappingDTO)condi.getOrgMapping());
        if (condi.isIncludeUncharged() == null) {
            condi.setIncludeUncharged(Boolean.valueOf(true));
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUBJECT.FNUMBER AS SUBJECTCODE,  \n");
        sql.append("       CURRENCY.FNUMBER AS CURRENCYCODE,  \n");
        sql.append("       SUBJECT.FDC AS ORIENT,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = 1 THEN B.FBEGINBALANCELOCAL ELSE 0 END) AS NC,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = 1 THEN B.FBEGINBALANCEFOR ELSE 0 END) AS WNC,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FYEARDEBITLOCAL ELSE 0 END) AS JL,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FYEARDEBITFOR ELSE 0 END) AS WJL,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FYEARCREDITLOCAL ELSE 0 END) AS DL,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FYEARCREDITFOR ELSE 0 END) AS WDL,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FDEBITLOCAL ELSE 0 END) AS JF,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FDEBITFOR ELSE 0 END) AS WJF,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FCREDITLOCAL ELSE 0 END) AS DF,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FCREDITFOR ELSE 0 END) AS WDF,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FBEGINBALANCELOCAL ELSE 0 END) AS C,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FBEGINBALANCEFOR ELSE 0 END) AS WC,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FENDBALANCELOCAL ELSE 0 END) AS YE,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FENDBALANCEFOR ELSE 0 END) AS WYE  \n");
        sql.append("       ${ASSFIELD}  FROM ${TABLENAME} B  \n");
        sql.append("  LEFT JOIN T_BD_CURRENCY CURRENCY  \n");
        sql.append("    ON CURRENCY.FID = B.FCURRENCYID  \n");
        sql.append("  LEFT JOIN T_ORG_COMPANY COMPANY  \n");
        sql.append("    ON B.FORGUNITID=COMPANY.FID \n");
        sql.append("  INNER JOIN T_BD_ACCOUNTVIEW SUBJECT  \n");
        sql.append("    ON B.FACCOUNTID = SUBJECT.FID  \n");
        sql.append("    AND COMPANY.FACCOUNTTABLEID = SUBJECT.FACCOUNTTABLEID  \n");
        sql.append("    AND SUBJECT.FCompanyID = COMPANY.FID  \n");
        sql.append("  ${ASSJOIN} \n");
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"SUBJECT.FNUMBER"));
        sql.append(" WHERE B.FPERIODYEAR = ?  \n");
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"SUBJECT.FNUMBER"));
        sql.append("   ${YETYPE} \n");
        sql.append("   ${UNITCONDITION}  \n");
        sql.append("   AND CURRENCY.FNUMBER = 'GLC' \n");
        sql.append("   AND SUBJECT.FISLEAF = 1 \n");
        sql.append(" GROUP BY SUBJECT.FNUMBER, CURRENCY.FNUMBER, SUBJECT.FDC  \n");
        sql.append("          ${GROUPFIELD}  \n");
        StringBuilder assJoin = new StringBuilder();
        StringBuilder assField = new StringBuilder();
        StringBuilder groupField = new StringBuilder();
        String yeType = "";
        for (AssistMappingBO assistMapping : assistMappingList) {
            if (assJoin.length() == 0) {
                assJoin.append("  LEFT JOIN T_BD_ASSISTANTHG ASS ON B.FASSISTGRPID =ASS.FID \n");
            }
            assJoin.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=ASS.%3$s", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            assField.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            groupField.append(String.format(",%1$s.CODE", assistMapping.getAssistCode()));
        }
        yeType = condi.isIncludeUncharged() == false ? " AND B.FBalType = 5 " : " AND B.FBalType = 1 ";
        Object[] args = new Object[]{condi.getAcctYear()};
        Variable variable = new Variable();
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("ASSFIELD", assField.toString());
        variable.put("ASSJOIN", assJoin.toString());
        variable.put("TABLENAME", Eas8FetchUtil.getBalanceTableName(!assistMappingList.isEmpty()));
        variable.put("GROUPFIELD", groupField.toString());
        variable.put("YETYPE", yeType);
        variable.put("UNITCONDITION", OrgSqlUtil.getOrgConditionSql((String)"COMPANY.FNUMBER", (List)unitAndBookValue.getUnitCodes()));
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u79d1\u76ee\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)new Object[]{args, condi}, (String)lastSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), lastSql, args, (ResultSetExtractor)new FetchDataExtractor());
    }
}

