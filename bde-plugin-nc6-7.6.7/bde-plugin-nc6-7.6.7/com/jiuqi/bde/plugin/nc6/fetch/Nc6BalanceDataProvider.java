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
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil
 *  com.jiuqi.bde.bizmodel.execute.util.TempTableUtils
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.bde.plugin.common.service.FetchResultConvert
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nc6.fetch;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.service.FetchResultConvert;
import com.jiuqi.bde.plugin.nc6.assist.Nc6AssistPojo;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Nc6BalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private FetchResultConvert fetchResultConvert;

    protected FetchData queryData(BalanceCondition condi) {
        if (StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode()) && StringUtils.isEmpty((String)condi.getOrgMapping().getAcctOrgCode())) {
            throw new BusinessRuntimeException("\u8d26\u7c3f\u4ee3\u7801\u548c\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u4e0d\u80fd\u540c\u65f6\u4e3a\u7a7a");
        }
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        boolean containAssist = !CollectionUtils.isEmpty((Collection)assistMappingList);
        Boolean subjectIsMapping = this.fetchResultConvert.subjectIsItemMapping(condi.getOrgMapping().getDataSchemeCode());
        StringBuilder sql = new StringBuilder();
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        sql = condi.isIncludeUncharged() != false ? sql.append((CharSequence)this.getIncludeUnchargedSql(condi, containAssist, subjectIsMapping)) : sql.append((CharSequence)this.getNotIncludeUnchargedSql(condi, containAssist, subjectIsMapping));
        UnitAndBookValue unitAndBookValue = OrgSqlUtil.getUnitAndBookValue((OrgMappingDTO)condi.getOrgMapping());
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
            externalAllSelectSql.append(String.format(",T.%1$s AS %1$s", assistMapping.getAssistCode()));
            externalSelectSql.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            externalGroupSql.append(String.format(",%1$s.CODE", assistMapping.getAssistCode()));
            externalAllGroupSql.append(String.format(",T.%1$s", assistMapping.getAssistCode()));
            String docFreeTableName = ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName();
            assistJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID = %3$s.F%4$d ", schemeMappingProvider.buildAssistSql(assistMapping), assistMapping.getAssistCode(), docFreeTableName, ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableNum()));
            if (mainDocFreeTable == null) {
                mainDocFreeTable = docFreeTableName;
                yeExternalAssistSql.append(String.format(" LEFT JOIN %1$s %1$s ON %1$s.ASSID=YE.ASSID\t\n", mainDocFreeTable));
                voucherExternalAssistSql.append(String.format(" LEFT JOIN %1$s %1$s ON %1$s.ASSID=PZX.ASSID  \n", mainDocFreeTable));
                docFreeSet.add(mainDocFreeTable);
                continue;
            }
            if (docFreeSet.contains(docFreeTableName)) continue;
            yeExternalAssistSql.append(String.format(" LEFT JOIN %1$s %1$s ON %1$s.ASSID = YE.ASSID \r\n", docFreeTableName));
            voucherExternalAssistSql.append(String.format(" LEFT JOIN %1$s %1$s ON %1$s.ASSID = PZX.ASSID \r\n", docFreeTableName));
            docFreeSet.add(docFreeTableName);
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
        variable.put("START_ADJUST_PERIOD", condi.getStartAdjustPeriod());
        variable.put("END_ADJUST_PERIOD", condi.getEndAdjustPeriod());
        variable.put("SUBJECTFIELD", subjectIsMapping != false ? "MD_ACCTSUBJECT.ID" : "MD_ACCTSUBJECT.CODE");
        variable.put("BOOKCODE", OrgSqlUtil.getOrgConditionSql((String)"MD_ACCTSUBJECT.BOOKCODE", (List)unitAndBookValue.getBookCodes()));
        variable.put("ACCTYEAR", String.valueOf(condi.getAcctYear()));
        variable.put("ORGCONDITION", !orgMappingType.equals("DEFAULT") ? OrgSqlUtil.getOrgConditionSql((String)"OORG.CODE", (List)unitAndBookValue.getUnitCodes()) : "");
        variable.put("YEORGJOINSQL", !orgMappingType.equals("DEFAULT") ? "JOIN ORG_ORGS OORG ON YE.${ORGRELATEFIELD} = OORG.PK_ORG" : "");
        variable.put("VOUCHERORGJOINSQL", !orgMappingType.equals("DEFAULT") ? "INNER JOIN ORG_ORGS OORG ON PZX.${ORGRELATEFIELD} = OORG.PK_ORG" : "");
        variable.put("ORGRELATEFIELD", orgMappingType.equals("BUSINESSUNIT") ? "PK_UNIT" : "PK_ORG");
        variable.put("VOUCHERBOOKCODECONDITION", !orgMappingType.equals("UNIT") ? "AND VCHR.PK_ACCOUNTINGBOOK IN (SELECT PK_ACCOUNTINGBOOK FROM ORG_ACCOUNTINGBOOK WHERE 1=1 ${BOOKCONDITON})" : "");
        variable.put("YEBOOKCODECONDITION", !orgMappingType.equals("UNIT") ? "AND YE.PK_ACCOUNTINGBOOK IN (SELECT PK_ACCOUNTINGBOOK FROM ORG_ACCOUNTINGBOOK WHERE 1=1 ${BOOKCONDITON})" : "");
        variable.put("BOOKCONDITON", OrgSqlUtil.getOrgConditionSql((String)"CODE", (List)unitAndBookValue.getBookCodes()));
        variable.put("LJ_PERIOD", Nc6BalanceDataProvider.isNotAdjustPeriod(condi) ? " T.PERIOD > '00' AND T.PERIOD <= '${END_PERIOD}'" : " (T.PERIOD > '00' AND T.PERIOD <= '${END_PERIOD}') OR (T.PERIOD >= '${START_ADJUST_PERIOD}' AND T.PERIOD <= '${END_ADJUST_PERIOD}')");
        variable.put("BQ_PERIOD", Nc6BalanceDataProvider.isNotAdjustPeriod(condi) ? " T.PERIOD >= '${START_PERIOD}' AND T.PERIOD <= '${END_PERIOD}'" : " (T.PERIOD >= '${START_PERIOD}' AND T.PERIOD <= '${END_PERIOD}') OR (T.PERIOD >= '${START_ADJUST_PERIOD}' AND T.PERIOD <= '${END_ADJUST_PERIOD}') ");
        variable.put("YE_PERIOD_CONDITION", Nc6BalanceDataProvider.isNotAdjustPeriod(condi) ? " AND YE.PERIOD <= '${END_PERIOD}' " : " AND (YE.PERIOD <= '${END_PERIOD}') OR (YE.PERIOD >= '${START_ADJUST_PERIOD}' AND YE.PERIOD <= '${END_ADJUST_PERIOD}')");
        variable.put("VCHR_PERIOD_CONDITION", Nc6BalanceDataProvider.isNotAdjustPeriod(condi) ? " AND VCHR.PERIOD > '00' AND VCHR.PERIOD <= '${END_PERIOD}'" : " AND ( VCHR.PERIOD > '00' AND VCHR.PERIOD <= '${END_PERIOD}') OR (VCHR.PERIOD >= '${START_ADJUST_PERIOD}' AND VCHR.PERIOD <= '${END_ADJUST_PERIOD}')");
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u79d1\u76ee\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)condi, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), ModelExecuteUtil.replaceContext((String)querySql, (BalanceCondition)condi), null, (ResultSetExtractor)new FetchDataExtractor());
    }

    private StringBuilder getIncludeUnchargedSql(BalanceCondition condi, boolean containAssist, Boolean subjectIsMapping) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ORIENT AS ORIENT,                \n");
        sql.append("       T.SUBJECTCODE,  \n");
        sql.append("       T.CURRENCYCODE  \n");
        sql.append("       ${EXTERNAL_ALL_SELECT_SQL}  \n");
        sql.append("       ,SUM(CASE WHEN T.PERIOD = '00' THEN T.JF - T.DF ELSE 0 END) AS NC,  \n");
        if (condi.getStartPeriod() == 1) {
            sql.append("       SUM(CASE WHEN T.PERIOD = '00' THEN T.JF - T.DF ELSE 0 END) AS C,  \n");
        } else {
            sql.append(String.format("       SUM(CASE WHEN T.PERIOD <= '%s' THEN T.JF - T.DF ELSE 0 END) AS C,  \n", ModelExecuteUtil.lpadPeriod((Integer)(condi.getStartPeriod() - 1))));
        }
        sql.append("       SUM(CASE WHEN ${BQ_PERIOD} THEN T.JF ELSE 0 END) AS JF,  \n");
        sql.append("       SUM(CASE WHEN ${BQ_PERIOD} THEN T.DF ELSE 0 END) AS DF,  \n");
        sql.append("       SUM(CASE WHEN ${LJ_PERIOD} THEN T.JF ELSE 0 END) AS JL,  \n");
        sql.append("       SUM(CASE WHEN ${LJ_PERIOD} THEN T.DF ELSE 0 END) AS DL,  \n");
        sql.append("       SUM(T.JF - T.DF) AS YE,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD = '00' THEN T.WJF - T.WDF ELSE 0 END) AS WNC,  \n");
        if (condi.getStartPeriod() == 1) {
            sql.append("       SUM(CASE WHEN T.PERIOD = '00' THEN T.WJF - T.WDF ELSE 0 END) AS WC,  \n");
        } else {
            sql.append(String.format("       SUM(CASE WHEN T.PERIOD <= '%s' THEN T.WJF - T.WDF ELSE 0 END) AS WC,  \n", ModelExecuteUtil.lpadPeriod((Integer)(condi.getStartPeriod() - 1))));
        }
        sql.append("       SUM(CASE WHEN ${BQ_PERIOD} THEN T.WJF ELSE 0 END) AS WJF,  \n");
        sql.append("       SUM(CASE WHEN ${BQ_PERIOD} THEN T.WDF ELSE 0 END) AS WDF,  \n");
        sql.append("       SUM(CASE WHEN ${LJ_PERIOD} THEN T.WJF ELSE 0 END) AS WJL,  \n");
        sql.append("       SUM(CASE WHEN ${LJ_PERIOD} THEN T.WDF ELSE 0 END) AS WDL,  \n");
        sql.append("       SUM(T.WJF - T.WDF) AS WYE  \n");
        sql.append("  FROM (SELECT '00' PERIOD,  \n");
        sql.append("               ${SUBJECTFIELD} SUBJECTCODE,  \n");
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
        sql.append("         INNER JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = YE.PK_ACCASOA \n");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("         ${BOOKCODE} \n");
        }
        if (containAssist) {
            sql.append("\t\t ${YE_EXTERNAL_ASSIT_SQL}  \n");
            sql.append("\t\t ${ASSIST_DIM_JOIN_SQL} \n");
        }
        sql.append("         ${YEORGJOINSQL} \n");
        sql.append("         INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON YE.PK_CURRTYPE = HB.ID \n");
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"MD_ACCTSUBJECT.CODE"));
        sql.append("         WHERE YE.YEAR = '${ACCTYEAR}'  \n");
        sql.append("           AND YE.PERIOD = '00'  \n");
        sql.append("\t\t${ORGCONDITION}");
        sql.append("           AND YE.PK_UNIT != 'N/A'  \n");
        sql.append("\t\t${YEBOOKCODECONDITION}");
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"MD_ACCTSUBJECT.CODE"));
        sql.append("         GROUP BY ${SUBJECTFIELD},  \n");
        sql.append("                  MD_ACCTSUBJECT.ORIENT  \n");
        sql.append("                  ${EXTERNAL_GROUP_SQL}  \n");
        sql.append("                  ,HB.CODE  \n");
        sql.append("        UNION ALL  \n");
        sql.append("        SELECT PZX.PERIODV PERIOD,  \n");
        sql.append("               ${SUBJECTFIELD} SUBJECTCODE,  \n");
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
        sql.append("         ${VOUCHERORGJOINSQL} \n");
        sql.append("         INNER JOIN (SELECT VCHR.PK_VOUCHER  \n");
        sql.append("                      FROM GL_VOUCHER VCHR  \n");
        sql.append("\t\t\t\t\t WHERE 1=1");
        sql.append("                       AND VCHR.DISCARDFLAG = 'N'  \n");
        sql.append("                       AND VCHR.DR = 0  \n");
        sql.append("\t\t\t\t\t   ${VOUCHERBOOKCODECONDITION}");
        sql.append("                       AND VCHR.VOUCHERKIND <> 255  \n");
        sql.append(this.getErrmessageCondi());
        sql.append("                       AND VCHR.YEAR = '${ACCTYEAR}'  \n");
        sql.append("\t\t\t\t\t   ${VCHR_PERIOD_CONDITION}");
        sql.append("\t\t\t\t\t   ) PZ ");
        sql.append("            ON PZ.PK_VOUCHER = PZX.PK_VOUCHER  \n");
        sql.append("         INNER JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = PZX.PK_ACCASOA  \n");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("         ${BOOKCODE} \n");
        }
        if (containAssist) {
            sql.append("\t\t ${VOUCHER_EXTERNAL_ASSIT_SQL} \n");
            sql.append("\t\t ${ASSIST_DIM_JOIN_SQL} \n");
        }
        sql.append("         INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON PZX.PK_CURRTYPE = HB.ID \n");
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"MD_ACCTSUBJECT.CODE"));
        sql.append("         WHERE 1 = 1  \n");
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"MD_ACCTSUBJECT.CODE"));
        sql.append("           \t\t\t   ${ORGCONDITION}  \n");
        sql.append("         GROUP BY PZX.PERIODV,${SUBJECTFIELD},MD_ACCTSUBJECT.ORIENT  \n");
        sql.append("                  ${EXTERNAL_GROUP_SQL}  \n");
        sql.append("                  ,HB.CODE ) T  \n");
        sql.append(" GROUP BY T.PERIOD,  \n");
        sql.append("          T.SUBJECTCODE,  \n");
        sql.append("          T.ORIENT  \n");
        sql.append("          ${EXTERNAL_ALL_GROUP_SQL}  \n");
        sql.append("          ,T.CURRENCYCODE  \n");
        return sql;
    }

    private static boolean isNotAdjustPeriod(BalanceCondition condi) {
        return StringUtils.isEmpty((String)condi.getStartAdjustPeriod()) || StringUtils.isEmpty((String)condi.getEndAdjustPeriod());
    }

    private StringBuilder getNotIncludeUnchargedSql(BalanceCondition condi, boolean containAssist, Boolean subjectIsMapping) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ORIENT AS ORIENT,                \n");
        sql.append("       T.SUBJECTCODE,  \n");
        sql.append("       T.CURRENCYCODE  \n");
        sql.append("       ${EXTERNAL_ALL_SELECT_SQL}  \n");
        sql.append("       ,SUM(CASE WHEN T.PERIOD = '00' THEN T.JF - T.DF ELSE 0 END) AS NC,  \n");
        if (condi.getStartPeriod() == 1) {
            sql.append("       SUM(CASE WHEN T.PERIOD = '00' THEN T.JF - T.DF ELSE 0 END) AS C,  \n");
        } else {
            sql.append(String.format("       SUM(CASE WHEN T.PERIOD <= '%s' THEN T.JF - T.DF ELSE 0 END) AS C,  \n", ModelExecuteUtil.lpadPeriod((Integer)(condi.getStartPeriod() - 1))));
        }
        sql.append("       SUM(CASE WHEN ${BQ_PERIOD} THEN T.JF ELSE 0 END) AS JF,  \n");
        sql.append("       SUM(CASE WHEN ${BQ_PERIOD} THEN T.DF ELSE 0 END) AS DF,  \n");
        sql.append("       SUM(CASE WHEN ${LJ_PERIOD} THEN T.JF ELSE 0 END) AS JL,  \n");
        sql.append("       SUM(CASE WHEN ${LJ_PERIOD} THEN T.DF ELSE 0 END) AS DL,  \n");
        sql.append("       SUM(T.JF - T.DF) AS YE,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD = '00' THEN T.WJF - T.WDF ELSE 0 END) AS WNC,  \n");
        if (condi.getStartPeriod() == 1) {
            sql.append("       SUM(CASE WHEN T.PERIOD = '00' THEN T.WJF - T.WDF ELSE 0 END) AS WC,  \n");
        } else {
            sql.append(String.format("       SUM(CASE WHEN T.PERIOD <= '%s' THEN T.WJF - T.WDF ELSE 0 END) AS WC,  \n", ModelExecuteUtil.lpadPeriod((Integer)(condi.getStartPeriod() - 1))));
        }
        sql.append("       SUM(CASE WHEN ${BQ_PERIOD} THEN T.WJF ELSE 0 END) AS WJF,  \n");
        sql.append("       SUM(CASE WHEN ${BQ_PERIOD} THEN T.WDF ELSE 0 END) AS WDF,  \n");
        sql.append("       SUM(CASE WHEN ${LJ_PERIOD} THEN T.WJF ELSE 0 END) AS WJL,  \n");
        sql.append("       SUM(CASE WHEN ${LJ_PERIOD} THEN T.WDF ELSE 0 END) AS WDL,  \n");
        sql.append("       SUM(T.WJF - T.WDF) AS WYE  \n");
        sql.append("  FROM (SELECT YE.PERIOD PERIOD,  \n");
        sql.append("               ${SUBJECTFIELD} SUBJECTCODE,  \n");
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
        sql.append("         INNER JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = YE.PK_ACCASOA \n");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("         ${BOOKCODE} \n");
        }
        if (containAssist) {
            sql.append("\t\t ${YE_EXTERNAL_ASSIT_SQL} \n");
            sql.append("\t\t ${ASSIST_DIM_JOIN_SQL} \n");
        }
        sql.append("         ${YEORGJOINSQL} \n");
        sql.append("         INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON YE.PK_CURRTYPE = HB.ID \n");
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"MD_ACCTSUBJECT.CODE"));
        sql.append("         WHERE YE.YEAR = '${ACCTYEAR}'  \n");
        sql.append("\t\t   ${YE_PERIOD_CONDITION}");
        sql.append("           AND YE.PK_UNIT != 'N/A'  \n");
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"MD_ACCTSUBJECT.CODE"));
        sql.append("\t\t   ${YEBOOKCODECONDITION}");
        sql.append("           ${ORGCONDITION}  \n");
        sql.append("         GROUP BY YE.PERIOD,  \n");
        sql.append("                  ${SUBJECTFIELD},  \n");
        sql.append("                  MD_ACCTSUBJECT.ORIENT  \n");
        sql.append("                  ${EXTERNAL_GROUP_SQL}  \n");
        sql.append("                  ,HB.CODE ) T \n");
        sql.append(" GROUP BY T.PERIOD,  \n");
        sql.append("          T.SUBJECTCODE,  \n");
        sql.append("          T.ORIENT  \n");
        sql.append("          ${EXTERNAL_ALL_GROUP_SQL}  \n");
        sql.append("          ,T.CURRENCYCODE  \n");
        return sql;
    }

    protected String getErrmessageCondi() {
        return "                       AND VCHR.ERRMESSAGE = '~'  \n";
    }
}

