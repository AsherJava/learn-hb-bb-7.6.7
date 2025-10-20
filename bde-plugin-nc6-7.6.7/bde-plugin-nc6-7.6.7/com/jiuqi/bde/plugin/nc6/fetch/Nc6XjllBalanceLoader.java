/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nc6.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader;
import com.jiuqi.bde.plugin.nc6.BdeNc6PluginType;
import com.jiuqi.bde.plugin.nc6.assist.Nc6AssistPojo;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Nc6XjllBalanceLoader
extends BaseXjllDataLoader {
    @Autowired
    private BdeNc6PluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    protected FetchData queryData(BalanceCondition condi) {
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        UnitAndBookValue unitAndBookValue = OrgSqlUtil.getUnitAndBookValue((OrgMappingDTO)condi.getOrgMapping());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MD_ACCTSUBJECT.ORIENT ,  \n");
        sql.append("       MD_CFITEM.CODE AS CFITEMCODE,  \n");
        sql.append("       ${SUBJECTFIELD} AS SUBJECTCODE,  \n");
        sql.append("       HB.CODE AS CURRENCYCODE  \n");
        sql.append("               ${EXTERNAL_SELECT_SQL}  \n");
        sql.append("       ,SUM(CASE WHEN PZ.PERIOD >= '${START_PERIOD}' AND PZ.PERIOD <= '${END_PERIOD}' THEN CASHFLOWCASE.MONEYMAIN ELSE 0 END) AS BQNUM,  \n");
        sql.append("       SUM(CASE WHEN PZ.PERIOD > '00' AND PZ.PERIOD <= '${END_PERIOD}' THEN CASHFLOWCASE.MONEYMAIN  ELSE 0 END) AS LJNUM,  \n");
        sql.append("       SUM(CASE WHEN PZ.PERIOD >= '${START_PERIOD}' AND PZ.PERIOD <= '${END_PERIOD}' THEN CASHFLOWCASE.MONEYMAIN ELSE 0 END) AS WBQNUM,  \n");
        sql.append("       SUM(CASE WHEN PZ.PERIOD > '00' AND PZ.PERIOD <= '${END_PERIOD}' THEN CASHFLOWCASE.MONEYMAIN ELSE 0 END) AS WLJNUM  \n");
        sql.append("  FROM GL_DETAIL PZX  \n");
        sql.append(" INNER JOIN (SELECT VCHR.PK_VOUCHER,VCHR.PERIOD,VCHR.PK_ORG  \n");
        sql.append("               FROM GL_VOUCHER VCHR  \n");
        sql.append("              WHERE 1=1 \n");
        sql.append("\t\t\t\t\t${VOUCHERBOOKCODECONDITION}");
        if (!condi.isIncludeUncharged().booleanValue()) {
            sql.append("                AND VCHR.PK_MANAGER <>'N/A'  \n");
        }
        sql.append("                AND VCHR.DISCARDFLAG = 'N'  \n");
        sql.append("                AND VCHR.DR = 0  \n");
        sql.append("                AND VCHR.VOUCHERKIND <> 255  \n");
        sql.append(this.getErrmessageCondi());
        sql.append("                AND VCHR.YEAR = '${ACCTYEAR}'  \n");
        sql.append("                AND VCHR.PERIOD <= '${END_PERIOD}'  \n");
        sql.append("                AND VCHR.PERIOD > '00') PZ  \n");
        sql.append("    ON PZ.PK_VOUCHER = PZX.PK_VOUCHER  \n");
        sql.append("   ${VOUCHERORGJOINSQL}");
        sql.append("  JOIN GL_CASHFLOWCASE CASHFLOWCASE  \n");
        sql.append("    ON CASHFLOWCASE.PK_DETAIL = PZX.PK_DETAIL  \n");
        sql.append("  JOIN BD_CASHFLOW MD_CFITEM  \n");
        sql.append("    ON MD_CFITEM.PK_CASHFLOW = CASHFLOWCASE.PK_CASHFLOW  \n");
        sql.append("         JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = PZX.PK_ACCASOA  \n");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append(" \t\t${BOOKCODE} \n");
        }
        sql.append("         INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON PZX.PK_CURRTYPE = HB.ID \n");
        sql.append("\t\t ${VOUCHER_EXTERNAL_ASSIT_SQL} \n");
        sql.append("         ${ASS_JOIN_SQL}  \n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append("\t\t${ORGCONDITION}");
        sql.append(" GROUP BY PZ.PERIOD,  \n");
        sql.append("          ${SUBJECTFIELD},  \n");
        sql.append("          MD_ACCTSUBJECT.ORIENT,  \n");
        sql.append("          MD_CFITEM.CODE  \n");
        sql.append("          ${EXTERNAL_GROUP_SQL}  \n");
        sql.append("          ,HB.CODE  \n");
        StringBuilder externalSelectSql = new StringBuilder();
        StringBuilder externalGroupSql = new StringBuilder();
        StringBuilder voucherExternalAssistSql = new StringBuilder();
        StringBuilder assistSelectSql = new StringBuilder();
        StringBuilder assistJoinSql = new StringBuilder();
        String mainDocFreeTable = null;
        HashSet<String> docFreeSet = new HashSet<String>(3);
        for (AssistMappingBO assistMapping : assistMappingList) {
            externalSelectSql.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            externalGroupSql.append(String.format(",%1$s.CODE", assistMapping.getAssistCode()));
            assistJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID = %3$s.F%4$d ", schemeMappingProvider.buildAssistSql(assistMapping), assistMapping.getAssistCode(), ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName(), ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableNum()));
            assistSelectSql.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            if (mainDocFreeTable == null) {
                mainDocFreeTable = ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName();
                voucherExternalAssistSql.append(String.format(" LEFT JOIN %1$s %1$s ON %1$s.ASSID=PZX.ASSID  \n", mainDocFreeTable));
                docFreeSet.add(mainDocFreeTable);
                continue;
            }
            if (docFreeSet.contains(((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName())) continue;
            voucherExternalAssistSql.append(String.format("  LEFT JOIN %1$s %1$s ON %1$s.ASSID = PZX.ASSID \r\n", ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName()));
            docFreeSet.add(((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName());
        }
        Variable variable = new Variable();
        variable.put("ASSIST_DIM_SQL", assistSelectSql.toString());
        variable.put("VOUCHER_EXTERNAL_ASSIT_SQL", voucherExternalAssistSql.toString());
        variable.put("ASS_JOIN_SQL", assistJoinSql.toString());
        variable.put("EXTERNAL_SELECT_SQL", externalSelectSql.toString());
        variable.put("EXTERNAL_GROUP_SQL", externalGroupSql.toString());
        variable.put("SUBJECTFIELD", "MD_ACCTSUBJECT.CODE");
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("EXTERNAL_CURRENCY_SQL", schemeMappingProvider.getCurrencySql());
        variable.put("START_PERIOD", ModelExecuteUtil.lpadPeriod((Integer)condi.getStartPeriod()));
        variable.put("END_PERIOD", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod()));
        variable.put("VOUCHERORGJOINSQL", !orgMappingType.equals("DEFAULT") ? "JOIN ORG_ORGS OORG ON PZX.${ORGRELATEFIELD} = OORG.PK_ORG" : "");
        variable.put("ORGRELATEFIELD", orgMappingType.equals("BUSINESSUNIT") ? "PK_UNIT" : "PK_ORG");
        variable.put("VOUCHERBOOKCODECONDITION", !orgMappingType.equals("UNIT") ? "AND VCHR.PK_ACCOUNTINGBOOK IN (SELECT PK_ACCOUNTINGBOOK FROM ORG_ACCOUNTINGBOOK WHERE 1=1 ${BOOKCONDITON})" : "");
        variable.put("BOOKCODE", OrgSqlUtil.getOrgConditionSql((String)"MD_ACCTSUBJECT.BOOKCODE", (List)unitAndBookValue.getBookCodes()));
        variable.put("BOOKCONDITION", OrgSqlUtil.getOrgConditionSql((String)"BOOK.CODE", (List)unitAndBookValue.getBookCodes()));
        variable.put("ACCTYEAR", String.valueOf(condi.getAcctYear()));
        variable.put("ORGCONDITION", !orgMappingType.equals("DEFAULT") ? OrgSqlUtil.getOrgConditionSql((String)"OORG.CODE", (List)unitAndBookValue.getUnitCodes()) : "");
        variable.put("BOOKCONDITON", OrgSqlUtil.getOrgConditionSql((String)"CODE", (List)unitAndBookValue.getBookCodes()));
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u73b0\u91d1\u6d41\u91cf", (Object)new Object[]{condi}, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), ModelExecuteUtil.replaceContext((String)querySql, (BalanceCondition)condi), null, (ResultSetExtractor)new FetchDataExtractor());
    }

    protected String getErrmessageCondi() {
        return "                AND VCHR.ERRMESSAGE = '~'  \n";
    }
}

