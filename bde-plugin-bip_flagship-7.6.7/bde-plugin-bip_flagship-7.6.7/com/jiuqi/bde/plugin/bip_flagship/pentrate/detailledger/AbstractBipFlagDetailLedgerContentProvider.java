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
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.bip_flagship.pentrate.detailledger;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.bip_flagship.BdeBipFlagShipPluginType;
import com.jiuqi.bde.plugin.bip_flagship.util.AssistPojo;
import com.jiuqi.bde.plugin.bip_flagship.util.BipFlagShipFetchUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractBipFlagDetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeBipFlagShipPluginType plugin;

    public String getPluginType() {
        return this.plugin.getSymbol();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        Variable variable = new Variable();
        StringBuilder assJoinSql = new StringBuilder();
        StringBuilder inSelectField = new StringBuilder();
        StringBuilder inGroupField = new StringBuilder();
        StringBuilder assCondition = new StringBuilder();
        StringBuilder adjustPeriod = new StringBuilder();
        StringBuilder endAdjustPeriod = new StringBuilder();
        if (!StringUtils.isEmpty((String)condi.getStartAdjustPeriod()) && !StringUtils.isEmpty((String)condi.getEndAdjustPeriod())) {
            adjustPeriod.append(" OR (VOUCHER.PERIODUNION >= '${STARTADJUSTPERIOD}' AND VOUCHER.PERIODUNION <= '${ENDADJUSTPERIOD}')");
            endAdjustPeriod.append(" OR VOUCHER.PERIODUNION <= '${ENDADJUSTPERIOD}'");
        }
        for (AssistMappingBO assistMapping : assistMappingList) {
            assJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=ASSTABLE.DEF%3$s \n", assistMapping.getAssistSql(), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getDocNum()));
            inSelectField.append(String.format(",%1$s.CODE AS %1$s,%1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            inGroupField.append(String.format(",%1$s.CODE,%1$s.NAME", assistMapping.getAssistCode()));
            assCondition.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule()));
        }
        variable.put("EXTERNAL_SUBJECT_SQL", ModelExecuteUtil.replaceContext((String)schemeMappingProvider.getSubjectSql(), (BalanceCondition)fetchCondi));
        variable.put("STARTPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), condi.getStartPeriod()));
        variable.put("ENDPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), condi.getEndPeriod()));
        variable.put("QCPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), condi.getStartPeriod()));
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
        variable.put("ASSCONDITION", assCondition.toString());
        String executeSql = VariableParseUtil.parse((String)this.getDetailLedgerSql(condi), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(PenetrateUtil.replaceContext((String)executeSql, (PenetrateBaseDTO)condi), new Object[0], (ResultSetExtractor)new DetailLedgerResultSetExtractor(assistMappingList));
        return queryParam;
    }

    private String getDetailLedgerSql(PenetrateBaseDTO condi) {
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        StringBuilder querySql = new StringBuilder();
        querySql.append(" SELECT\n");
        querySql.append("          2 AS ROWTYPE,\n");
        querySql.append("          NULL AS ID,\n");
        querySql.append("          NULL AS VCHRID,\n");
        querySql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        querySql.append(String.format("       %d as ACCTPERIOD,  \n", condi.getStartPeriod()));
        querySql.append("          NULL AS ACCTDAY,  \n");
        querySql.append("          NULL AS VCHRTYPE,  \n");
        querySql.append("          1 AS ORDERID,  \n");
        querySql.append("          1 AS ORIENT,  \n");
        querySql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        querySql.append("          SUBJECT.CODE AS SUBJECTCODE,\n");
        querySql.append("          SUBJECT.NAME AS SUBJECTNAME,\n");
        querySql.append("          0 AS DEBIT,  \n");
        querySql.append("          0 AS CREDIT,  \n");
        querySql.append("          0 AS ORGND,  \n");
        querySql.append("          0 AS ORGNC,  \n");
        querySql.append("          SUM(B.LOCALDEBITAMOUNT-B.LOCALCREDITAMOUNT) AS YE,\n");
        querySql.append("          SUM(B.DEBITAMOUNT-B.CREDITAMOUNT) AS ORGNYE\n");
        querySql.append("          ${INSELECTFIELD}");
        querySql.append(" FROM\n");
        querySql.append("          FI_BALANCE B\n");
        querySql.append(" JOIN (SELECT * FROM EPUB_ACCOUNTBOOK WHERE 1 = 1\n");
        querySql.append("  ").append(BipFlagShipFetchUtil.buildUnitSql("EPUB_ACCOUNTBOOK.CODE", condi.getOrgMapping())).append(") BOOK ON\n");
        querySql.append("          BOOK.ID = B.ACCBOOK\n");
        querySql.append(" JOIN (${EXTERNAL_SUBJECT_SQL}) SUBJECT ON\n");
        querySql.append("          SUBJECT.ID = B.ACCSUBJECT AND SUBJECT.BOOKCODE = BOOK.CODE\n");
        querySql.append(" JOIN BD_CURRENCY_TENANT CURRENCY ON\n");
        querySql.append("          CURRENCY.ID = B.CURRENCY\n");
        querySql.append(" ${BALANCEASSJOINSQL}");
        querySql.append(" ${ASSTABLEJOIN}");
        querySql.append(" WHERE\n");
        querySql.append("           1 = 1\n");
        querySql.append("           AND B.PERIODUNION < '${QCPERIODUNION}'\n");
        querySql.append("           ${ASSCONDITION}\n");
        querySql.append(this.buildSubjectCondi("SUBJECT", "CODE", condi.getSubjectCode()));
        querySql.append(" GROUP BY\n");
        querySql.append("           SUBJECT.CODE,SUBJECT.NAME\n");
        querySql.append("           ${INGROUPTFIELD}");
        querySql.append(" UNION ALL");
        querySql.append(" SELECT\n");
        querySql.append("          0 AS ROWTYPE,\n");
        querySql.append("          DETAIL.ID AS ID,\n");
        querySql.append("          VOUCHER.ID AS VCHRID,\n");
        querySql.append(String.format("          %1$s AS ACCTYEAR,\n", sqlHandler.SubStr("DETAIL.PERIOD", "1", "4")));
        querySql.append(String.format("          %1$s AS ACCTPERIOD,\n", sqlHandler.SubStr("DETAIL.PERIOD", "6", "2")));
        querySql.append(String.format("          %1$s AS ACCTDAY,\n", sqlHandler.SubStr("DETAIL.BUSIDATE", "9", "2")));
        querySql.append(sqlHandler.concatBySeparator("-", new String[]{"VOUCHERTYPE.NAME", String.format("%s", sqlHandler.toChar("VOUCHER.BILLCODE"))})).append(" AS VCHRTYPE, \n");
        querySql.append("          DETAIL.ID AS ORDERID,  \n");
        querySql.append("          SUBJECT.ORIENT AS ORIENT,\n");
        querySql.append("          DETAIL.DESCRIPTION AS DIGEST,\n");
        querySql.append("          SUBJECT.CODE AS SUBJECTCODE,\n");
        querySql.append("          SUBJECT.NAME AS SUBJECTNAME,\n");
        querySql.append("          DETAIL.DEBIT_ORG AS DEBIT,  \n");
        querySql.append("          DETAIL.CREDIT_ORG AS CREDIT,  \n");
        querySql.append("          DETAIL.DEBIT_ORIGINAL AS ORGND,  \n");
        querySql.append("          DETAIL.CREDIT_ORIGINAL AS ORGNC,  \n");
        querySql.append("          0 AS YE,\n");
        querySql.append("          0 AS ORGNYE\n");
        querySql.append("          ${INSELECTFIELD}");
        querySql.append(" FROM\n");
        querySql.append("          (SELECT FI_VOUCHER.PERIODUNION,FI_VOUCHER.ID,FI_VOUCHER.VOUCHERTYPE, BOOK.CODE AS BOOKCODE,FI_VOUCHER.BILLCODE  \n");
        querySql.append("            FROM  FI_VOUCHER  \n");
        querySql.append("           INNER JOIN EPUB_ACCOUNTBOOK BOOK \n");
        querySql.append("                 ON BOOK.ID = FI_VOUCHER.ACCBOOK");
        querySql.append(BipFlagShipFetchUtil.buildUnitSql("BOOK.CODE", condi.getOrgMapping())).append("\n");
        querySql.append("           WHERE 1=1  \n");
        if (condi.getIncludeUncharged().booleanValue()) {
            querySql.append("                  AND FI_VOUCHER.VOUCHERSTATUS IN('04','01','03') \n");
        } else {
            querySql.append("                  AND FI_VOUCHER.VOUCHERSTATUS = '04' \n");
        }
        querySql.append("                 AND FI_VOUCHER.VOUCHERSTATUS<>'05') VOUCHER  \n");
        querySql.append(" JOIN EPUB_VOUCHERTYPE VOUCHERTYPE ON VOUCHERTYPE.ID = VOUCHER.VOUCHERTYPE");
        querySql.append(" JOIN FI_VOUCHER_B DETAIL ON\n");
        querySql.append("          DETAIL.VOUCHERID = VOUCHER.ID\n");
        querySql.append(" JOIN (${EXTERNAL_SUBJECT_SQL}) SUBJECT ON\n");
        querySql.append("          SUBJECT.ID = DETAIL.ACCSUBJECT AND SUBJECT.BOOKCODE = VOUCHER.BOOKCODE\n");
        querySql.append(" JOIN BD_CURRENCY_TENANT CURRENCY ON\n");
        querySql.append("          CURRENCY.ID = DETAIL.CURRENCY\n");
        querySql.append(" ${VOUCHERASSJOINSQL}");
        querySql.append(" ${ASSTABLEJOIN}");
        querySql.append(" WHERE\n");
        querySql.append("          1 = 1\n");
        querySql.append("          AND VOUCHER.PERIODUNION like '${YEAR}%'\n");
        querySql.append("          AND VOUCHER.PERIODUNION<='${ENDPERIODUNION}'\n");
        querySql.append("          AND VOUCHER.PERIODUNION>='${STARTPERIODUNION}'\n");
        querySql.append("          ${ORADJUSTPERIOD} \n");
        querySql.append("          ${ASSCONDITION}\n");
        querySql.append(this.buildSubjectCondi("SUBJECT", "CODE", condi.getSubjectCode()));
        querySql.append(" ORDER BY ORDERID");
        return querySql.toString();
    }
}

