/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.bizmodel.execute.util.TempTableUtils
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.bip_flagship.fetch;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.bip_flagship.util.AssistPojo;
import com.jiuqi.bde.plugin.bip_flagship.util.BipFlagShipFetchUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BdeBipFlagShipBalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;

    protected FetchData queryData(BalanceCondition condi) {
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        Variable variable = new Variable();
        StringBuilder assJoinSql = new StringBuilder();
        StringBuilder outSelectField = new StringBuilder();
        StringBuilder outGroupField = new StringBuilder();
        StringBuilder inSelectField = new StringBuilder();
        StringBuilder inGroupField = new StringBuilder();
        StringBuilder adjustPeriod = new StringBuilder();
        StringBuilder endAdjustPeriod = new StringBuilder();
        if (!StringUtils.isEmpty((String)condi.getStartAdjustPeriod()) && !StringUtils.isEmpty((String)condi.getEndAdjustPeriod())) {
            adjustPeriod.append(" OR (T.PERIODUNION >= '${STARTADJUSTPERIOD}' AND T.PERIODUNION <= '${ENDADJUSTPERIOD}')");
            endAdjustPeriod.append(" OR T.PERIODUNION <= '${ENDADJUSTPERIOD}'");
        }
        for (AssistMappingBO assistMapping : assistMappingList) {
            assJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=ASSTABLE.DEF%3$s \n", assistMapping.getAssistSql(), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getDocNum()));
            outSelectField.append(String.format(",T.%1$S AS %1$S", assistMapping.getAssistCode()));
            outGroupField.append(String.format(",T.%1$S", assistMapping.getAssistCode()));
            inSelectField.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            inGroupField.append(String.format(",%1$s.CODE", assistMapping.getAssistCode()));
        }
        variable.put("EXTERNAL_SUBJECT_SQL", ModelExecuteUtil.replaceContext((String)schemeMappingProvider.getSubjectSql(), (BalanceCondition)condi));
        variable.put("STARTPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), condi.getStartPeriod()));
        variable.put("ENDPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), condi.getEndPeriod()));
        variable.put("NCPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), 0));
        variable.put("ORADJUSTPERIOD", adjustPeriod.toString());
        variable.put("ORENDADJUSTPERIOD", endAdjustPeriod.toString());
        variable.put("STARTADJUSTPERIOD", BipFlagShipFetchUtil.getAdjustPeriodUnion(condi.getAcctYear(), condi.getStartAdjustPeriod()));
        variable.put("ENDADJUSTPERIOD", BipFlagShipFetchUtil.getAdjustPeriodUnion(condi.getAcctYear(), condi.getEndAdjustPeriod()));
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("BALANCEASSJOINSQL", " LEFT JOIN FI_AUXILIARY ASSTABLE ON B.AUXILIARY=ASSTABLE.ID ");
        variable.put("VOUCHERASSJOINSQL", " LEFT JOIN FI_AUXILIARY ASSTABLE ON DETAIL.AUXILIARY=ASSTABLE.ID ");
        variable.put("ASSTABLEJOIN", assJoinSql.toString());
        variable.put("INSELECTFIELD", inSelectField.toString());
        variable.put("INGROUPTFIELD", inGroupField.toString());
        variable.put("OUTSELECTFIELD", outSelectField.toString());
        variable.put("OUTGROUPFIELD", outGroupField.toString());
        String lastSql = VariableParseUtil.parse((String)this.getQuerySql(condi), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u79d1\u76ee\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)condi, (String)lastSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), lastSql, new Object[0], (ResultSetExtractor)new FetchDataExtractor());
    }

    private String getQuerySql(BalanceCondition condi) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("          T.SUBJECTCODE,\n");
        query.append("          T.ORIENT,\n");
        query.append("          T.CURRENCYCODE,\n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>='${STARTPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORADJUSTPERIOD} THEN T.JF ELSE 0 END) AS JF,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>'${NCPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN T.JF ELSE 0 END) AS JL,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>='${STARTPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORADJUSTPERIOD} THEN T.DF ELSE 0 END) AS DF,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>'${NCPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN T.DF ELSE 0 END) AS DL,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION='${NCPERIODUNION}' THEN T.JF-T.DF ELSE 0 END) AS NC,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION='${NCPERIODUNION}' THEN T.WJF-T.WDF ELSE 0 END) AS WNC,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION<'${STARTPERIODUNION}' THEN T.JF-T.DF ELSE 0 END) AS C,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION<'${STARTPERIODUNION}' THEN T.WJF-T.WDF ELSE 0 END) AS WC,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN T.JF-T.DF  ELSE 0 END) AS YE,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN T.WJF-T.WDF  ELSE 0 END) AS WYE,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>='${STARTPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORADJUSTPERIOD} THEN T.WJF ELSE 0 END) AS WJF,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>'${NCPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN T.WJF ELSE 0 END) AS WJL,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>='${STARTPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORADJUSTPERIOD} THEN T.WDF ELSE 0 END) AS WDF,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>'${NCPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN T.WDF ELSE 0 END) AS WDL  \n");
        query.append("          ${OUTSELECTFIELD}\n");
        query.append("  FROM\n");
        query.append("          (SELECT\n");
        query.append("              SUBJECT.CODE AS SUBJECTCODE,\n");
        query.append("              SUBJECT.ORIENT AS ORIENT,\n");
        query.append("              SUM(B.LOCALDEBITAMOUNT) AS JF,\n");
        query.append("              SUM(B.LOCALCREDITAMOUNT) AS DF,\n");
        query.append("              SUM(B.DEBITAMOUNT) AS WJF,\n");
        query.append("              SUM(B.CREDITAMOUNT) AS WDF,\n");
        query.append("              B.PERIODUNION AS PERIODUNION,\n");
        query.append("              CURRENCY.CODE AS CURRENCYCODE\n");
        query.append("              ${INSELECTFIELD}");
        query.append("          FROM\n");
        query.append("              FI_BALANCE B\n");
        query.append("          JOIN (SELECT * FROM EPUB_ACCOUNTBOOK WHERE 1 = 1\n");
        query.append("          ").append(BipFlagShipFetchUtil.buildUnitSql("EPUB_ACCOUNTBOOK.CODE", condi.getOrgMapping())).append(") BOOK ON\n");
        query.append("              BOOK.ID = B.ACCBOOK\n");
        query.append("          JOIN (${EXTERNAL_SUBJECT_SQL}) SUBJECT ON\n");
        query.append("              SUBJECT.ID = B.ACCSUBJECT AND SUBJECT.BOOKCODE = BOOK.CODE\n");
        query.append("          JOIN BD_CURRENCY_TENANT CURRENCY ON\n");
        query.append("              CURRENCY.ID = B.CURRENCY\n");
        query.append("          ${BALANCEASSJOINSQL}");
        query.append("          ${ASSTABLEJOIN}");
        query.append("          WHERE\n");
        query.append("              1 = 1\n");
        query.append("              AND B.PERIODUNION = '${NCPERIODUNION}'\n");
        query.append("          GROUP BY\n");
        query.append("              SUBJECT.CODE,\n");
        query.append("              SUBJECT.ORIENT,\n");
        query.append("              B.PERIODUNION,\n");
        query.append("              CURRENCY.CODE\n");
        query.append("              ${INGROUPTFIELD}");
        query.append("          UNION ALL");
        query.append("          SELECT\n");
        query.append("              SUBJECT.CODE AS SUBJECTCODE,\n");
        query.append("              SUBJECT.ORIENT AS ORIENT,\n");
        query.append("              SUM(DETAIL.DEBIT_ORG) AS JF,\n");
        query.append("              SUM(DETAIL.CREDIT_ORG) AS DF,\n");
        query.append("              SUM(DETAIL.DEBIT_ORIGINAL) AS WJF,\n");
        query.append("              SUM(DETAIL.CREDIT_ORIGINAL) AS WDF,\n");
        query.append("              VOUCHER.PERIODUNION AS PERIODUNION,\n");
        query.append("              CURRENCY.CODE AS CURRENCYCODE\n");
        query.append("              ${INSELECTFIELD}");
        query.append("          FROM\n");
        query.append("              (SELECT FI_VOUCHER.PERIODUNION,FI_VOUCHER.ID, BOOK.CODE AS BOOKCODE  \n");
        query.append("               FROM FI_VOUCHER  \n");
        query.append("               INNER JOIN EPUB_ACCOUNTBOOK BOOK \n");
        query.append("                     ON BOOK.ID = FI_VOUCHER.ACCBOOK");
        query.append(BipFlagShipFetchUtil.buildUnitSql("BOOK.CODE", condi.getOrgMapping())).append("\n");
        query.append("               WHERE 1=1  \n");
        if (condi.getIncludeUncharged().booleanValue()) {
            query.append("                      AND FI_VOUCHER.VOUCHERSTATUS IN('04','01','03') \n");
        } else {
            query.append("                      AND FI_VOUCHER.VOUCHERSTATUS = '04' \n");
        }
        query.append("                     AND FI_VOUCHER.VOUCHERSTATUS<>'05') VOUCHER  \n");
        query.append("          JOIN FI_VOUCHER_B DETAIL ON\n");
        query.append("              DETAIL.VOUCHERID = VOUCHER.ID\n");
        query.append("          JOIN (${EXTERNAL_SUBJECT_SQL}) SUBJECT ON\n");
        query.append("              SUBJECT.ID = DETAIL.ACCSUBJECT AND SUBJECT.BOOKCODE = VOUCHER.BOOKCODE\n");
        query.append("          JOIN BD_CURRENCY_TENANT CURRENCY ON\n");
        query.append("              CURRENCY.ID = DETAIL.CURRENCY\n");
        query.append("          ${VOUCHERASSJOINSQL}");
        query.append("          ${ASSTABLEJOIN}");
        query.append("          WHERE\n");
        query.append("              1 = 1\n");
        query.append("              AND VOUCHER.PERIODUNION like '${YEAR}%'\n");
        query.append("          GROUP BY\n");
        query.append("              SUBJECT.CODE,\n");
        query.append("              SUBJECT.ORIENT,\n");
        query.append("              VOUCHER.PERIODUNION,\n");
        query.append("              CURRENCY.CODE\n");
        query.append("              ${INGROUPTFIELD}");
        query.append("                           ) T\n");
        query.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"T.SUBJECTCODE"));
        query.append("  WHERE 1=1");
        query.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"T.SUBJECTCODE"));
        query.append("  GROUP BY\n");
        query.append("          T.SUBJECTCODE,\n");
        query.append("          T.ORIENT,\n");
        query.append("          T.CURRENCYCODE");
        query.append("          ${OUTGROUPFIELD}\n");
        return query.toString();
    }
}

