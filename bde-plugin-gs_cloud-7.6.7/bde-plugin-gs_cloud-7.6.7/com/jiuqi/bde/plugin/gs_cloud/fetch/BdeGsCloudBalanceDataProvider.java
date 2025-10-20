/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.util.TempTableUtils
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.gs_cloud.fetch;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.gs_cloud.util.AssistPojo;
import com.jiuqi.bde.plugin.gs_cloud.util.GsCloudTableEnum;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BdeGsCloudBalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;

    protected FetchData queryData(BalanceCondition condi) {
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "GSCLOUD" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder sql = condi.getIncludeUncharged() != false ? this.getIncludeUncharged(condi, assistMappingList, orgMappingType) : this.getNotIncludeUncharged(condi, assistMappingList, orgMappingType);
        StringBuilder yeAssField = new StringBuilder();
        StringBuilder yeGroupField = new StringBuilder();
        StringBuilder masterAssField = new StringBuilder();
        StringBuilder yeJoinSql = new StringBuilder();
        StringBuilder pzJoinSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            yeAssField.append(String.format(", %1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            yeJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON B.%3$s = %2$s.ID \n", assistMapping.getAssistSql(), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
            pzJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON VOUCHERDETAIL.%3$s = %2$s.ID \n", assistMapping.getAssistSql(), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
            yeGroupField.append(String.format(", %1$s.CODE", assistMapping.getAssistCode()));
            masterAssField.append(String.format(", MASTER.%1$s", assistMapping.getAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("YEASSFIELD", yeAssField.toString());
        variable.put("YEJOINSQL", yeJoinSql.toString());
        variable.put("PZJOINSQL", pzJoinSql.toString());
        variable.put("YEGROUPFIELD", yeGroupField.toString());
        variable.put("MASTERASSFIELD", masterAssField.toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("ORGID", orgMappingType.equals("GSCLOUD") ? "ACCORGID" : "LEDGER");
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("PREVIOUSPERIOD", String.format("%02d", condi.getStartPeriod() - 1));
        variable.put("STARTPERIOD", String.format("%02d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%02d", condi.getEndPeriod()));
        variable.put("EXTERNAL_ORG_SQL", schemeMappingProvider.getOrgSql());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("EXTERNAL_CURRENCY_SQL", schemeMappingProvider.getCurrencySql());
        String replaceSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        String lastSql = GsCloudTableEnum.replaceAccYear(replaceSql, condi.getAcctYear());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)condi, (String)lastSql);
        FetchData fetchData = (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), lastSql, new Object[0], (ResultSetExtractor)new FetchDataExtractor());
        return this.filterNotLeaf(fetchData);
    }

    private StringBuilder getNotIncludeUncharged(BalanceCondition condi, List<AssistMappingBO<AssistPojo>> assistMappingList, String orgMappingType) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUBJECT.CODE AS SUBJECTCODE, \n");
        sql.append("       HB.CODE AS CURRENCYCODE, \n");
        sql.append("       SUBJECT.ORIENT AS ORIENT, \n");
        sql.append("       SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.BEGINBALANCEAMT ELSE 0 END) AS NC, \n");
        sql.append("       SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.ENDBALANCEAMT ELSE 0 END) AS YE, \n");
        sql.append("       SUM(B.DEBITAMT) AS JF, \n");
        sql.append("       SUM(B.CREDITAMT) AS DF, \n");
        sql.append("       SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.CURCUMDRAMT ELSE 0 END) AS JL, \n");
        sql.append("       SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.CURCUMCRAMT ELSE 0 END) AS DL, \n");
        sql.append("       SUM(CASE WHEN B.ACCPERIODCODE='${STARTPERIOD}' THEN B.ENDBALANCEAMT - B.DEBITAMT + B.CREDITAMT ELSE 0 END) AS C, \n");
        sql.append("       SUM(CASE WHEN B.ACCPERIODCODE='${STARTPERIOD}' THEN B.ENDBALANCEFOR - B.DEBITFOR + B.CREDITFOR ELSE 0 END) AS WC, \n");
        sql.append("       SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.BEGINBALANCEFOR ELSE 0 END) AS WNC, \n");
        sql.append("       SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.ENDBALANCEFOR ELSE 0 END) AS WYE, \n");
        sql.append("       SUM(B.DEBITFOR) AS WJF, \n");
        sql.append("       SUM(B.CREDITFOR) AS WDF, \n");
        sql.append("       SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.CURCUMDRFOR ELSE 0 END) AS WJL, \n");
        sql.append("       SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.CURCUMCRFOR ELSE 0 END) AS WDL\n");
        sql.append("       ${YEASSFIELD} \n");
        if (assistMappingList.isEmpty()) {
            sql.append("  FROM FIGLACCOUNTBALANCE${YEAR}  B \n");
        } else {
            sql.append("  FROM FIGLASSBALANCE${YEAR}  B \n");
        }
        sql.append("       ${YEJOINSQL}");
        sql.append(" INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON HB.ID = B.CURRENCYID \n");
        sql.append(" INNER JOIN (${EXTERNAL_ORG_SQL}) ORG ON ORG.ID = B.${ORGID} \n");
        sql.append(" INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = B.ACCTITLEID \n");
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"SUBJECT.CODE"));
        sql.append(" WHERE 1 = 1 \n");
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"SUBJECT.CODE"));
        sql.append("   AND B.ACCPERIODCODE >= '${STARTPERIOD}' \n");
        sql.append("   AND B.ACCPERIODCODE <= '${ENDPERIOD}' \n");
        sql.append("   AND B.YEAR = '${YEAR}' \n");
        if (orgMappingType.equals("BOOKCODE")) {
            sql.append("   AND ORG.BOOKCODE='${BOOKCODE}' \n");
        } else {
            sql.append("   AND ORG.CODE='${UNITCODE}' \n");
        }
        sql.append(" GROUP BY SUBJECT.CODE,HB.CODE, SUBJECT.ORIENT  \n");
        sql.append("       ${YEGROUPFIELD} \n");
        return sql;
    }

    private StringBuilder getIncludeUncharged(BalanceCondition condi, List<AssistMappingBO<AssistPojo>> assistMappingList, String orgMappingType) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  \n");
        sql.append("        MASTER.SUBJECTCODE AS SUBJECTCODE, \n");
        sql.append("        MASTER.CURRENCYCODE AS CURRENCYCODE, \n");
        sql.append("        MASTER.ORIENT AS ORIENT, \n");
        sql.append("        SUM(NC) AS NC, \n");
        sql.append("        SUM(WNC) AS WNC, \n");
        sql.append("        SUM(C) AS C, \n");
        sql.append("        SUM(WC) AS WC, \n");
        sql.append("        SUM(JF) AS JF, \n");
        sql.append("        SUM(WJF) AS WJF, \n");
        sql.append("        SUM(JL) AS JL, \n");
        sql.append("        SUM(WJL) AS WJL, \n");
        sql.append("        SUM(DF) AS DF, \n");
        sql.append("        SUM(WDF) AS WDF, \n");
        sql.append("        SUM(DL) AS DL, \n");
        sql.append("        SUM(WDL) AS WDL, \n");
        sql.append("        SUM(YE) AS YE, \n");
        sql.append("        SUM(WYE) AS WYE \n");
        sql.append("        ${MASTERASSFIELD} \n");
        sql.append(" FROM  \n");
        sql.append("     (SELECT SUBJECT.CODE AS SUBJECTCODE, \n");
        sql.append("             HB.CODE AS CURRENCYCODE, \n");
        sql.append("             SUBJECT.ORIENT AS ORIENT, \n");
        sql.append("             SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.BEGINBALANCEAMT ELSE 0 END) AS NC, \n");
        sql.append("             SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.BEGINBALANCEFOR ELSE 0 END) AS WNC, \n");
        sql.append("             SUM(CASE WHEN B.ACCPERIODCODE='${STARTPERIOD}' THEN B.ENDBALANCEAMT - B.DEBITAMT + B.CREDITAMT ELSE 0 END) AS C, \n");
        sql.append("             SUM(CASE WHEN B.ACCPERIODCODE='${STARTPERIOD}' THEN B.ENDBALANCEFOR - B.DEBITFOR + B.CREDITFOR ELSE 0 END) AS WC, \n");
        sql.append("             SUM(B.DEBITAMT) AS JF, \n");
        sql.append("             SUM(B.CREDITAMT) AS DF, \n");
        sql.append("             SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.CURCUMDRAMT ELSE 0 END) AS JL, \n");
        sql.append("             SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.CURCUMCRAMT ELSE 0 END) AS DL, \n");
        sql.append("             SUM(B.DEBITFOR) AS WJF, \n");
        sql.append("             SUM(B.CREDITFOR) AS WDF, \n");
        sql.append("             SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.CURCUMDRFOR ELSE 0 END) AS WJL, \n");
        sql.append("             SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.CURCUMCRFOR ELSE 0 END) AS WDL, \n");
        sql.append("             SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.ENDBALANCEAMT ELSE 0 END) AS YE, \n");
        sql.append("             SUM(CASE WHEN B.ACCPERIODCODE='${ENDPERIOD}' THEN B.ENDBALANCEFOR ELSE 0 END) AS WYE \n");
        sql.append("             ${YEASSFIELD} \n");
        if (assistMappingList.isEmpty()) {
            sql.append("        FROM FIGLACCOUNTBALANCE${YEAR}  B \n");
        } else {
            sql.append("        FROM FIGLASSBALANCE${YEAR}  B \n");
        }
        sql.append("       ${YEJOINSQL}");
        sql.append("       INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON HB.ID = B.CURRENCYID \n");
        sql.append("       INNER JOIN (${EXTERNAL_ORG_SQL}) ORG ON ORG.ID = B.${ORGID} \n");
        sql.append("       INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = B.ACCTITLEID \n");
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"SUBJECT.CODE"));
        sql.append(" WHERE 1 = 1 \n");
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"SUBJECT.CODE"));
        if (orgMappingType.equals("BOOKCODE")) {
            sql.append("   AND ORG.BOOKCODE='${BOOKCODE}' \n");
        } else {
            sql.append("   AND ORG.CODE='${UNITCODE}' \n");
        }
        sql.append("         AND B.ACCPERIODCODE>='${STARTPERIOD}' \n");
        sql.append("         AND B.ACCPERIODCODE<='${ENDPERIOD}' \n");
        sql.append("         AND B.YEAR = '${YEAR}' \n");
        sql.append("       GROUP BY SUBJECT.CODE, HB.CODE, SUBJECT.ORIENT \n");
        sql.append("       ${YEGROUPFIELD} \n");
        sql.append("   UNION ALL \n");
        sql.append("      SELECT \n");
        sql.append("             SUBJECT.CODE AS SUBJECTCODE, \n");
        sql.append("             HB.CODE AS CURRENCYCODE, \n");
        sql.append("             SUBJECT.ORIENT AS ORIENT, \n");
        sql.append("             0 AS NC, \n");
        sql.append("             0 AS WNC, \n");
        if (condi.getStartPeriod() != 1) {
            sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${PREVIOUSPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.AMOUNT WHEN VOUCHER.ACCPERIODCODE<='${PREVIOUSPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.AMOUNT * -1 ELSE 0 END) AS C, \n");
            sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${PREVIOUSPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.FOREIGNCURRENCY WHEN VOUCHER.ACCPERIODCODE<='${PREVIOUSPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.FOREIGNCURRENCY * -1 ELSE 0 END) AS WC, \n");
        } else {
            sql.append("             0 AS C, \n");
            sql.append("             0 AS wC, \n");
        }
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.AMOUNT ELSE 0 END) AS JF, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.AMOUNT ELSE 0 END) AS DF, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.AMOUNT ELSE 0 END) AS JL, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.AMOUNT ELSE 0 END) AS DL, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.FOREIGNCURRENCY ELSE 0 END) AS WJF, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.FOREIGNCURRENCY ELSE 0 END) AS WDF, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.FOREIGNCURRENCY ELSE 0 END) AS WJL, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.FOREIGNCURRENCY ELSE 0 END) AS WDL, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.AMOUNT WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.AMOUNT * -1 ELSE 0 END) AS YE, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.FOREIGNCURRENCY WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.FOREIGNCURRENCY * -1 ELSE 0 END) AS WYE \n");
        sql.append("             ${YEASSFIELD} \n");
        sql.append("     FROM\n");
        sql.append("             FIGLACCOUNTINGDOCUMENT${YEAR} VOUCHER \n");
        if (assistMappingList.isEmpty()) {
            sql.append("       INNER JOIN FIGLACCDOCENTRY${YEAR} VOUCHERDETAIL ON VOUCHER.ACCORGID = VOUCHERDETAIL.ACCORGID AND VOUCHER.ID = VOUCHERDETAIL.ACCDOCID \n");
            sql.append("       INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON VOUCHERDETAIL.CURRENCYID = HB.ID \n");
        } else {
            sql.append("       INNER JOIN FIGLACCDOCASSISTANCE${YEAR} VOUCHERDETAIL ON VOUCHER.ACCORGID = VOUCHERDETAIL.ACCORGID AND VOUCHER.ID = VOUCHERDETAIL.ACCDOCID \n");
            sql.append("       INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON VOUCHERDETAIL.FOREIGNCURRENCYID = HB.ID \n");
        }
        sql.append("       ${PZJOINSQL} \n");
        sql.append("       INNER JOIN (${EXTERNAL_ORG_SQL}) ORG ON ORG.ID = VOUCHERDETAIL.${ORGID} \n");
        sql.append("       INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = VOUCHERDETAIL.ACCTITLEID \n");
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"SUBJECT.CODE"));
        sql.append(" WHERE 1 = 1 \n");
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"SUBJECT.CODE"));
        sql.append("         AND VOUCHER.ISCOMPLETE = '1'\n");
        sql.append("         AND VOUCHER.ISVOID = '0'\n");
        sql.append("         AND VOUCHER.ISBOOK = '0'\n");
        if (orgMappingType.equals("BOOKCODE")) {
            sql.append("   AND ORG.BOOKCODE='${BOOKCODE}' \n");
        } else {
            sql.append("   AND ORG.CODE='${UNITCODE}' \n");
        }
        sql.append("         AND VOUCHER.YEAR = '${YEAR}' \n");
        sql.append("       GROUP BY SUBJECT.CODE, HB.CODE, SUBJECT.ORIENT \n");
        sql.append("       ${YEGROUPFIELD} ) MASTER \n");
        sql.append("       GROUP BY MASTER.SUBJECTCODE,MASTER.CURRENCYCODE,MASTER.ORIENT \n");
        sql.append("       ${MASTERASSFIELD} \n");
        return sql;
    }

    private FetchData filterNotLeaf(FetchData fetchData) {
        if (fetchData.getColumns().isEmpty() || fetchData.getRowDatas().isEmpty()) {
            return fetchData;
        }
        HashSet<String> subjectCodeSet = new HashSet<String>();
        List rowDatas = fetchData.getRowDatas();
        Integer subjectCodeIdx = (Integer)fetchData.getColumns().get("SUBJECTCODE");
        for (Object rowData : rowDatas) {
            subjectCodeSet.add((String)rowData[subjectCodeIdx]);
        }
        HashSet<String> leafSubjectCodeSet = new HashSet<String>();
        for (String subjectCode : subjectCodeSet) {
            boolean containsLeaf = false;
            for (String otherData : subjectCodeSet) {
                if (otherData.equals(subjectCode) || !otherData.startsWith(subjectCode)) continue;
                containsLeaf = true;
                break;
            }
            if (containsLeaf) continue;
            leafSubjectCodeSet.add(subjectCode);
        }
        ArrayList<Object[]> leafRowDatas = new ArrayList<Object[]>();
        for (Object[] rowData : rowDatas) {
            if (!leafSubjectCodeSet.contains((String)rowData[subjectCodeIdx])) continue;
            leafRowDatas.add(rowData);
        }
        fetchData.setRowDatas(leafRowDatas);
        return fetchData;
    }
}

