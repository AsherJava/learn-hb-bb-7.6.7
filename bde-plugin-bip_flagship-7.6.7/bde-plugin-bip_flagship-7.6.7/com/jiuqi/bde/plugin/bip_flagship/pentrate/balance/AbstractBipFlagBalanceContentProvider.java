/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.bip_flagship.pentrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.bip_flagship.BdeBipFlagShipPluginType;
import com.jiuqi.bde.plugin.bip_flagship.util.AssistPojo;
import com.jiuqi.bde.plugin.bip_flagship.util.BipFlagShipFetchUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractBipFlagBalanceContentProvider
extends AbstractBalanceContentProvider {
    @Autowired
    private BdeBipFlagShipPluginType plugin;

    public String getPluginType() {
        return this.plugin.getSymbol();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        Variable variable = new Variable();
        StringBuilder assJoinSql = new StringBuilder();
        StringBuilder outSelectField = new StringBuilder();
        StringBuilder outGroupField = new StringBuilder();
        StringBuilder inSelectField = new StringBuilder();
        StringBuilder inGroupField = new StringBuilder();
        StringBuilder assCondition = new StringBuilder();
        StringBuilder adjustPeriod = new StringBuilder();
        StringBuilder endAdjustPeriod = new StringBuilder();
        if (!StringUtils.isEmpty((String)condi.getStartAdjustPeriod()) && !StringUtils.isEmpty((String)condi.getEndAdjustPeriod())) {
            adjustPeriod.append(" OR (T.PERIODUNION >= '${STARTADJUSTPERIOD}' AND T.PERIODUNION <= '${ENDADJUSTPERIOD}')");
            endAdjustPeriod.append(" OR T.PERIODUNION <= '${ENDADJUSTPERIOD}'");
        }
        for (AssistMappingBO assistMapping : assistMappingList) {
            assJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=ASSTABLE.DEF%3$s \n", assistMapping.getAssistSql(), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getDocNum()));
            outSelectField.append(String.format(",T.%1$s AS %1$S,T.%1$s_NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            outGroupField.append(String.format(",T.%1$S,T.%1$s_NAME", assistMapping.getAssistCode()));
            inSelectField.append(String.format(",%1$s.CODE AS %1$s,%1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            inGroupField.append(String.format(",%1$s.CODE,%1$s.NAME", assistMapping.getAssistCode()));
            assCondition.append(this.matchByRule("T", assistMapping.getAssistCode(), assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule()));
        }
        variable.put("EXTERNAL_SUBJECT_SQL", ModelExecuteUtil.replaceContext((String)schemeMappingProvider.getSubjectSql(), (BalanceCondition)fetchCondi));
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
        variable.put("ASSCONDITION", assCondition.toString());
        String executeSql = VariableParseUtil.parse((String)this.getQuerySql(condi), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(executeSql, new Object[0], (ResultSetExtractor)new BalanceResultSetExtractor(assistMappingList));
        return queryParam;
    }

    private String getQuerySql(PenetrateBaseDTO condi) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("          T.SUBJECTCODE,\n");
        query.append("          T.SUBJECTNAME,\n");
        query.append("          T.ORIENT,\n");
        query.append("          T.CURRENCYCODE,\n");
        query.append("          SUM(CASE WHEN T.PERIODUNION='${NCPERIODUNION}' THEN T.JF-T.DF ELSE 0 END) AS NC,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION='${NCPERIODUNION}' THEN T.WJF-T.WDF ELSE 0 END) AS ORGNNC,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION<'${STARTPERIODUNION}' THEN T.JF-T.DF ELSE 0 END) AS QC,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION<'${STARTPERIODUNION}' THEN T.WJF-T.WDF ELSE 0 END) AS ORGNQC,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>='${STARTPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORADJUSTPERIOD} THEN T.JF ELSE 0 END) AS DEBIT,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>='${STARTPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORADJUSTPERIOD} THEN T.WJF ELSE 0 END) AS ORGND,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>'${NCPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN T.JF ELSE 0 END) AS DSUM,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>='${STARTPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORADJUSTPERIOD} THEN T.WDF ELSE 0 END) AS ORGNC,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>='${STARTPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORADJUSTPERIOD} THEN T.DF ELSE 0 END) AS CREDIT,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>'${NCPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN T.DF ELSE 0 END) AS CSUM,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN T.JF-T.DF  ELSE 0 END) AS YE,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN T.WJF-T.WDF  ELSE 0 END) AS ORGNYE,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>'${NCPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN T.WJF ELSE 0 END) AS ORGNDSUM,  \n");
        query.append("          SUM(CASE WHEN T.PERIODUNION>'${NCPERIODUNION}' AND T.PERIODUNION<='${ENDPERIODUNION}' ${ORENDADJUSTPERIOD} THEN T.WDF ELSE 0 END) AS ORGNCSUM  \n");
        query.append("          ${OUTSELECTFIELD}\n");
        query.append("  FROM\n");
        query.append("          (SELECT\n");
        query.append("              SUBJECT.CODE AS SUBJECTCODE,\n");
        query.append("              SUBJECT.NAME AS SUBJECTNAME,\n");
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
        query.append("           ").append(BipFlagShipFetchUtil.buildUnitSql("EPUB_ACCOUNTBOOK.CODE", condi.getOrgMapping())).append(") BOOK ON\n");
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
        query.append("              B.PERIODUNION,\n");
        query.append("              CURRENCY.CODE\n");
        query.append("              ${INGROUPTFIELD}");
        query.append("          UNION ALL");
        query.append("          SELECT\n");
        query.append("              SUBJECT.CODE AS SUBJECTCODE,\n");
        query.append("              SUBJECT.NAME AS SUBJECTNAME,\n");
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
        query.append("              VOUCHER.PERIODUNION,\n");
        query.append("              CURRENCY.CODE\n");
        query.append("              ${INGROUPTFIELD}");
        query.append("                           ) T\n");
        query.append("  WHERE 1=1");
        query.append(this.buildSubjectCondi("T", "SUBJECTCODE", condi.getSubjectCode()));
        query.append(this.buildExcludeCondi("T", "SUBJECTCODE", condi.getExcludeSubjectCode()));
        query.append("        ${ASSCONDITION}");
        query.append("  GROUP BY\n");
        query.append("          T.SUBJECTCODE,\n");
        query.append("          T.SUBJECTNAME,\n");
        query.append("          T.ORIENT,\n");
        query.append("          T.CURRENCYCODE");
        query.append("          ${OUTGROUPFIELD}\n");
        query.append(" ORDER BY  T.SUBJECTCODE");
        return query.toString();
    }
}

