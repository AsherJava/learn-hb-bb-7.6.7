/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nc6.penetrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.nc6.BdeNc6PluginType;
import com.jiuqi.bde.plugin.nc6.assist.Nc6AssistPojo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractNc6BalanceContentProvider
extends AbstractBalanceContentProvider {
    @Autowired
    private BdeNc6PluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        UnitAndBookValue unitAndBookValue = OrgSqlUtil.getUnitAndBookValue((OrgMappingDTO)condi.getOrgMapping());
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        boolean containAssist = !CollectionUtils.isEmpty((Collection)assistMappingList) && !CollectionUtils.isEmpty((Collection)condi.getAssTypeList());
        StringBuilder sql = null;
        sql = condi.getIncludeUncharged() != false ? this.getIncludeUnchargedSql(condi, containAssist) : this.getNotIncludeUnchargedSql(condi, containAssist);
        StringBuilder externalAllSelectSql = new StringBuilder();
        StringBuilder externalSelectSql = new StringBuilder();
        StringBuilder externalGroupSql = new StringBuilder();
        StringBuilder externalAllGroupSql = new StringBuilder();
        StringBuilder yeExternalAssistSql = new StringBuilder();
        StringBuilder voucherExternalAssistSql = new StringBuilder();
        StringBuilder assistJoinSql = new StringBuilder();
        String mainDocFreeTable = null;
        HashSet<String> docFreeSet = new HashSet<String>(3);
        for (AssistMappingBO assistMapping : assistMappingList) {
            externalAllSelectSql.append(String.format(",T.%1$s AS %1$s,T.%1$s_NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            externalSelectSql.append(String.format(",%1$s.CODE AS %1$s,%1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            externalGroupSql.append(String.format(",%1$s.CODE,%1$s.NAME", assistMapping.getAssistCode()));
            externalAllGroupSql.append(String.format(",T.%1$s,T.%1$s_NAME", assistMapping.getAssistCode()));
            assistJoinSql.append(String.format(" INNER JOIN (%1$s) %2$s ON %2$s.ID = %3$s.F%4$d %5$s ", schemeMappingProvider.buildAssistSql(assistMapping), assistMapping.getAssistCode(), ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName(), ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableNum(), this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule())));
            if (mainDocFreeTable == null) {
                mainDocFreeTable = ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName();
                yeExternalAssistSql.append(String.format(" LEFT JOIN %1$s %1$s ON %1$s.ASSID=YE.ASSID\t\n", mainDocFreeTable));
                voucherExternalAssistSql.append(String.format(" LEFT JOIN %1$s %1$s ON %1$s.ASSID=PZX.ASSID  \n", mainDocFreeTable));
                docFreeSet.add(mainDocFreeTable);
                continue;
            }
            if (docFreeSet.contains(((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName())) continue;
            yeExternalAssistSql.append(String.format(" LEFT JOIN %1$s %1$s ON %1$s.ASSID = YE.ASSID \r\n", ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName()));
            voucherExternalAssistSql.append(String.format(" LEFT JOIN %1$s %1$s ON %1$s.ASSID = PZX.ASSID \r\n", ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName()));
            docFreeSet.add(((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName());
        }
        Variable variable = new Variable();
        variable.put("ASSIST_DIM_JOIN_SQL", assistJoinSql.toString());
        variable.put("EXTERNAL_ALL_SELECT_SQL", externalAllSelectSql.toString());
        variable.put("EXTERNAL_SELECT_SQL", externalSelectSql.toString());
        variable.put("EXTERNAL_GROUP_SQL", externalGroupSql.toString());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("EXTERNAL_CURRENCY_SQL", schemeMappingProvider.getCurrencySql());
        variable.put("YE_EXTERNAL_ASSIT_SQL", yeExternalAssistSql.toString());
        variable.put("VOUCHER_EXTERNAL_ASSIT_SQL", voucherExternalAssistSql.toString());
        variable.put("EXTERNAL_ALL_GROUP_SQL", externalAllGroupSql.toString());
        variable.put("START_PERIOD", ModelExecuteUtil.lpadPeriod((Integer)condi.getStartPeriod()));
        variable.put("END_PERIOD", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod()));
        variable.put("BOOKCODE", OrgSqlUtil.getOrgConditionSql((String)"MD_ACCTSUBJECT.BOOKCODE", (List)unitAndBookValue.getBookCodes()));
        variable.put("ACCTYEAR", String.valueOf(condi.getAcctYear()));
        variable.put("ORGCODECONDITION", !orgMappingType.equals("DEFAULT") ? OrgSqlUtil.getOrgConditionSql((String)"OORG.CODE", (List)unitAndBookValue.getUnitCodes()) : "");
        variable.put("YEORGJOINSQL", !orgMappingType.equals("DEFAULT") ? "JOIN ORG_ORGS OORG ON YE.${ORGRELATEFIELD} = OORG.PK_ORG" : "");
        variable.put("VOUCHERORGJOINSQL", !orgMappingType.equals("DEFAULT") ? "JOIN ORG_ORGS OORG ON PZX.${ORGRELATEFIELD} = OORG.PK_ORG" : "");
        variable.put("VOUCHERBOOKCODECONDITION", !orgMappingType.equals("UNIT") ? "AND VCHR.PK_ACCOUNTINGBOOK IN (SELECT PK_ACCOUNTINGBOOK FROM ORG_ACCOUNTINGBOOK WHERE 1=1 ${BOOKCONDITON})" : "");
        variable.put("ORGRELATEFIELD", orgMappingType.equals("BUSINESSUNIT") ? "PK_UNIT" : "PK_ORG");
        variable.put("YEBOOKCODECONDITION", !orgMappingType.equals("UNIT") ? "AND YE.PK_ACCOUNTINGBOOK IN (SELECT PK_ACCOUNTINGBOOK FROM ORG_ACCOUNTINGBOOK WHERE 1=1 ${BOOKCONDITON})" : "");
        variable.put("BOOKCONDITON", OrgSqlUtil.getOrgConditionSql((String)"CODE", (List)unitAndBookValue.getBookCodes()));
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(PenetrateUtil.replaceContext((String)querySql, (PenetrateBaseDTO)condi), new Object[0], (ResultSetExtractor)new BalanceResultSetExtractor(assistMappingList));
        return queryParam;
    }

    private StringBuilder getIncludeUnchargedSql(PenetrateBaseDTO condi, boolean containAssist) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ORIENT AS ORIENT,                \n");
        sql.append("       T.SUBJECTCODE,  \n");
        sql.append("       T.SUBJECTNAME,  \n");
        sql.append("       T.CURRENCYCODE  \n");
        sql.append("       ${EXTERNAL_ALL_SELECT_SQL}  \n");
        sql.append("       ,SUM(CASE WHEN T.PERIOD = '00' THEN T.JF - T.DF ELSE 0 END) AS NC,  \n");
        if (condi.getStartPeriod() == 1) {
            sql.append("       SUM(CASE WHEN T.PERIOD = '00' THEN T.JF - T.DF ELSE 0 END) AS QC,  \n");
        } else {
            sql.append(String.format("       SUM(CASE WHEN T.PERIOD <= '%s' THEN T.JF - T.DF ELSE 0 END) AS QC,  \n", AbstractNc6BalanceContentProvider.lpadPeriod((Integer)(condi.getStartPeriod() - 1))));
        }
        sql.append("       SUM(CASE WHEN T.PERIOD >= '${START_PERIOD}' AND T.PERIOD <= '${END_PERIOD}' THEN T.JF ELSE 0 END) AS DEBIT,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD >= '${START_PERIOD}' AND T.PERIOD <= '${END_PERIOD}' THEN T.DF ELSE 0 END) AS CREDIT,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD > '00' AND T.PERIOD <= '${END_PERIOD}' THEN T.JF ELSE 0 END) AS DSUM,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD > '00' AND T.PERIOD <= '${END_PERIOD}' THEN T.DF ELSE 0 END) AS CSUM,  \n");
        sql.append("       SUM(T.JF - T.DF) AS YE,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD = '00' THEN T.WJF - T.WDF ELSE 0 END) AS ORGNNC,  \n");
        if (condi.getStartPeriod() == 1) {
            sql.append("       SUM(CASE WHEN T.PERIOD = '00' THEN T.WJF - T.WDF ELSE 0 END) AS ORGNQC,  \n");
        } else {
            sql.append(String.format("       SUM(CASE WHEN T.PERIOD <= '%s' THEN T.WJF - T.WDF ELSE 0 END) AS ORGNQC,  \n", AbstractNc6BalanceContentProvider.lpadPeriod((Integer)(condi.getStartPeriod() - 1))));
        }
        sql.append("       SUM(CASE WHEN T.PERIOD >= '${START_PERIOD}' AND T.PERIOD <= '${END_PERIOD}' THEN T.WJF ELSE 0 END) AS ORGND,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD >= '${START_PERIOD}' AND T.PERIOD <= '${END_PERIOD}' THEN T.WDF ELSE 0 END) AS ORGNC,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD > '00' AND T.PERIOD <= '${END_PERIOD}' THEN T.WJF ELSE 0 END) AS ORGNDSUM,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD > '00' AND T.PERIOD <= '${END_PERIOD}' THEN T.WDF ELSE 0 END) AS ORGNCSUM,  \n");
        sql.append("       SUM(T.WJF - T.WDF) AS ORGNYE  \n");
        sql.append("  FROM (SELECT '00' PERIOD,  \n");
        sql.append("               MD_ACCTSUBJECT.CODE SUBJECTCODE,  \n");
        sql.append("               MD_ACCTSUBJECT.NAME SUBJECTNAME,  \n");
        sql.append("               MD_ACCTSUBJECT.ORIENT,  \n");
        sql.append("               HB.CODE CURRENCYCODE  \n");
        sql.append("               ${EXTERNAL_SELECT_SQL}  \n");
        sql.append("               ,SUM(YE.LOCALDEBITAMOUNT) JF,  \n");
        sql.append("               SUM(YE.LOCALCREDITAMOUNT) DF,  \n");
        sql.append("               SUM(YE.DEBITAMOUNT) WJF,  \n");
        sql.append("               SUM(YE.CREDITAMOUNT) WDF,  \n");
        sql.append("               SUM(YE.DEBITQUANTITY) SJF,  \n");
        sql.append("               SUM(YE.CREDITQUANTITY) SDF  \n");
        sql.append("          FROM GL_BALANCE YE  \n");
        sql.append("         INNER JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = YE.PK_ACCASOA  \n");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("\t\t${BOOKCODE} \n");
        }
        if (containAssist) {
            sql.append("         ${YE_EXTERNAL_ASSIT_SQL} \n");
            sql.append("         ${ASSIST_DIM_JOIN_SQL} \n");
        }
        sql.append("         ${YEORGJOINSQL} \n");
        sql.append("         INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON YE.PK_CURRTYPE = HB.ID \n");
        sql.append("         WHERE YE.YEAR = '${ACCTYEAR}'  \n");
        sql.append("           AND YE.PERIOD = '00'  \n");
        sql.append("\t\t   ${YEBOOKCODECONDITION}  \n");
        sql.append("           ${ORGCODECONDITION}  \n");
        sql.append("           AND YE.PK_UNIT != 'N/A'  \n");
        sql.append(this.buildSubjectCondi("MD_ACCTSUBJECT", "CODE", condi.getSubjectCode()));
        sql.append(this.buildExcludeCondi("MD_ACCTSUBJECT", "CODE", condi.getExcludeSubjectCode()));
        sql.append("         GROUP BY MD_ACCTSUBJECT.CODE,  \n");
        sql.append("                  MD_ACCTSUBJECT.NAME,  \n");
        sql.append("                  MD_ACCTSUBJECT.ORIENT  \n");
        sql.append("                  ${EXTERNAL_GROUP_SQL}  \n");
        sql.append("                  ,HB.CODE  \n");
        sql.append("        UNION ALL  \n");
        sql.append("        SELECT PZX.PERIODV PERIOD,  \n");
        sql.append("               MD_ACCTSUBJECT.CODE SUBJECTCODE,  \n");
        sql.append("               MD_ACCTSUBJECT.NAME SUBJECTNAME,  \n");
        sql.append("               MD_ACCTSUBJECT.ORIENT,  \n");
        sql.append("               HB.CODE CURRENCYCODE  \n");
        sql.append("               ${EXTERNAL_SELECT_SQL}  \n");
        sql.append("              ,SUM(PZX.LOCALDEBITAMOUNT) JF,  \n");
        sql.append("               SUM(PZX.LOCALCREDITAMOUNT) DF,  \n");
        sql.append("               SUM(PZX.DEBITAMOUNT) WJF,  \n");
        sql.append("               SUM(PZX.CREDITAMOUNT) WDF,  \n");
        sql.append("               SUM(PZX.DEBITQUANTITY) SJF,  \n");
        sql.append("               SUM(PZX.CREDITQUANTITY) SDF  \n");
        sql.append("          FROM GL_DETAIL PZX  \n");
        sql.append("         INNER JOIN (SELECT VCHR.PK_VOUCHER  \n");
        sql.append("                      FROM GL_VOUCHER VCHR  \n");
        sql.append("                     WHERE 1=1 \n");
        sql.append("                       AND VCHR.DISCARDFLAG = 'N'  \n");
        sql.append("                       AND VCHR.DR = 0  \n");
        sql.append("                       AND VCHR.VOUCHERKIND <> 255  \n");
        sql.append("\t\t\t\t\t   ${VOUCHERBOOKCODECONDITION}");
        sql.append(this.getErrmessageCondi());
        sql.append("                       AND VCHR.YEAR = '${ACCTYEAR}'  \n");
        sql.append("                       AND VCHR.PERIOD <= '${END_PERIOD}'  \n");
        sql.append("                       AND VCHR.PERIOD > '00') PZ  \n");
        sql.append("            ON PZ.PK_VOUCHER = PZX.PK_VOUCHER  \n");
        sql.append("            ${VOUCHERORGJOINSQL} \n");
        sql.append("         INNER JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = PZX.PK_ACCASOA ");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("\t\t${BOOKCODE} \n");
        }
        if (containAssist) {
            sql.append("         ${VOUCHER_EXTERNAL_ASSIT_SQL} \n");
            sql.append("         ${ASSIST_DIM_JOIN_SQL} \n");
        }
        sql.append("         INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON PZX.PK_CURRTYPE = HB.ID \n");
        sql.append("         WHERE 1 = 1  \n");
        sql.append("           \t\t\t   ${ORGCODECONDITION}  \n");
        sql.append(this.buildSubjectCondi("MD_ACCTSUBJECT", "CODE", condi.getSubjectCode()));
        sql.append(this.buildExcludeCondi("MD_ACCTSUBJECT", "CODE", condi.getExcludeSubjectCode()));
        sql.append("         GROUP BY PZX.PERIODV,MD_ACCTSUBJECT.CODE,MD_ACCTSUBJECT.NAME,MD_ACCTSUBJECT.ORIENT  \n");
        sql.append("                  ${EXTERNAL_GROUP_SQL}  \n");
        sql.append("                  ,HB.CODE ) T  \n");
        sql.append(" GROUP BY                 \n");
        sql.append("          T.SUBJECTCODE,  \n");
        sql.append("          T.SUBJECTNAME,  \n");
        sql.append("          T.ORIENT  \n");
        sql.append("          ${EXTERNAL_ALL_GROUP_SQL}  \n");
        sql.append("          ,T.CURRENCYCODE  \n");
        sql.append(" ORDER BY T.SUBJECTCODE,   \n");
        sql.append(" \t\t  T.SUBJECTNAME    \n");
        return sql;
    }

    private StringBuilder getNotIncludeUnchargedSql(PenetrateBaseDTO condi, boolean containAssist) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ORIENT AS ORIENT,                \n");
        sql.append("       T.SUBJECTCODE,  \n");
        sql.append("       T.SUBJECTNAME,  \n");
        sql.append("       T.CURRENCYCODE  \n");
        sql.append("       ${EXTERNAL_ALL_SELECT_SQL}  \n");
        sql.append("       ,SUM(CASE WHEN T.PERIOD = '00' THEN T.JF - T.DF ELSE 0 END) AS NC,  \n");
        if (condi.getStartPeriod() == 1) {
            sql.append("       SUM(CASE WHEN T.PERIOD = '00' THEN T.JF - T.DF ELSE 0 END) AS QC,  \n");
        } else {
            sql.append(String.format("       SUM(CASE WHEN T.PERIOD <= '%s' THEN T.JF - T.DF ELSE 0 END) AS QC,  \n", AbstractNc6BalanceContentProvider.lpadPeriod((Integer)(condi.getStartPeriod() - 1))));
        }
        sql.append("       SUM(CASE WHEN T.PERIOD >= '${START_PERIOD}' AND T.PERIOD <= '${END_PERIOD}' THEN T.JF ELSE 0 END) AS DEBIT,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD >= '${START_PERIOD}' AND T.PERIOD <= '${END_PERIOD}' THEN T.DF ELSE 0 END) AS CREDIT,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD > '00' AND T.PERIOD <= '${END_PERIOD}' THEN T.JF ELSE 0 END) AS DSUM,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD > '00' AND T.PERIOD <= '${END_PERIOD}' THEN T.DF ELSE 0 END) AS CSUM,  \n");
        sql.append("       SUM(T.JF - T.DF) AS YE,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD = '00' THEN T.WJF - T.WDF ELSE 0 END) AS ORGNNC,  \n");
        if (condi.getStartPeriod() == 1) {
            sql.append("       SUM(CASE WHEN T.PERIOD = '00' THEN T.WJF - T.WDF ELSE 0 END) AS ORGNQC,  \n");
        } else {
            sql.append(String.format("       SUM(CASE WHEN T.PERIOD <= '%s' THEN T.WJF - T.WDF ELSE 0 END) AS ORGNQC,  \n", AbstractNc6BalanceContentProvider.lpadPeriod((Integer)(condi.getStartPeriod() - 1))));
        }
        sql.append("       SUM(CASE WHEN T.PERIOD >= '${START_PERIOD}' AND T.PERIOD <= '${END_PERIOD}' THEN T.WJF ELSE 0 END) AS ORGND,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD >= '${START_PERIOD}' AND T.PERIOD <= '${END_PERIOD}' THEN T.WDF ELSE 0 END) AS ORGNC,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD > '00' AND T.PERIOD <= '${END_PERIOD}' THEN T.WJF ELSE 0 END) AS ORGNDSUM,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD > '00' AND T.PERIOD <= '${END_PERIOD}' THEN T.WDF ELSE 0 END) AS ORGNCSUM,  \n");
        sql.append("       SUM(T.WJF - T.WDF) AS ORGNYE  \n");
        sql.append("  FROM (SELECT YE.PERIOD PERIOD,  \n");
        sql.append("               MD_ACCTSUBJECT.CODE SUBJECTCODE,  \n");
        sql.append("               MD_ACCTSUBJECT.NAME SUBJECTNAME,  \n");
        sql.append("               MD_ACCTSUBJECT.ORIENT,  \n");
        sql.append("               HB.CODE CURRENCYCODE  \n");
        sql.append("               ${EXTERNAL_SELECT_SQL}  \n");
        sql.append("               ,SUM(YE.LOCALDEBITAMOUNT) JF,  \n");
        sql.append("               SUM(YE.LOCALCREDITAMOUNT) DF,  \n");
        sql.append("               SUM(YE.DEBITAMOUNT) WJF,  \n");
        sql.append("               SUM(YE.CREDITAMOUNT) WDF,  \n");
        sql.append("               SUM(YE.DEBITQUANTITY) SJF,  \n");
        sql.append("               SUM(YE.CREDITQUANTITY) SDF  \n");
        sql.append("          FROM GL_BALANCE YE  \n");
        sql.append("         INNER JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = YE.PK_ACCASOA ");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("\t\t${BOOKCODE} \n");
        }
        if (containAssist) {
            sql.append("         ${YE_EXTERNAL_ASSIT_SQL} \n");
            sql.append("         ${ASSIST_DIM_JOIN_SQL} \n");
        }
        sql.append("         ${YEORGJOINSQL} \n");
        sql.append("         INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON YE.PK_CURRTYPE = HB.ID \n");
        sql.append("         WHERE YE.YEAR = '${ACCTYEAR}'  \n");
        sql.append("\t\t   ${YEBOOKCODECONDITION}  \n");
        sql.append("           ${ORGCODECONDITION}  \n");
        sql.append("           AND YE.PERIOD <= '${END_PERIOD}'  \n");
        sql.append("           AND YE.PK_UNIT != 'N/A'  \n");
        sql.append(this.buildSubjectCondi("MD_ACCTSUBJECT", "CODE", condi.getSubjectCode()));
        sql.append(this.buildExcludeCondi("MD_ACCTSUBJECT", "CODE", condi.getExcludeSubjectCode()));
        sql.append("         GROUP BY YE.PERIOD,  \n");
        sql.append("                  MD_ACCTSUBJECT.CODE,  \n");
        sql.append("                  MD_ACCTSUBJECT.NAME,  \n");
        sql.append("                  MD_ACCTSUBJECT.ORIENT  \n");
        sql.append("                  ${EXTERNAL_GROUP_SQL}  \n");
        sql.append("                  ,HB.CODE  \n");
        sql.append("                           ) T  \n");
        sql.append(" GROUP BY                 \n");
        sql.append("          T.SUBJECTCODE,  \n");
        sql.append("          T.SUBJECTNAME,  \n");
        sql.append("          T.ORIENT  \n");
        sql.append("          ${EXTERNAL_ALL_GROUP_SQL}  \n");
        sql.append("          ,T.CURRENCYCODE  \n");
        sql.append(" ORDER BY T.SUBJECTCODE,   \n");
        sql.append(" \t\t  T.SUBJECTNAME    \n");
        return sql;
    }

    protected String getErrmessageCondi() {
        return "                       AND VCHR.ERRMESSAGE = '~'  \n";
    }
}

