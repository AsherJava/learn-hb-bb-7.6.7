/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
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
package com.jiuqi.bde.plugin.gs_cloud.penetrate.detailledger;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.gs_cloud.BdeGsCloudPluginType;
import com.jiuqi.bde.plugin.gs_cloud.util.AssistPojo;
import com.jiuqi.bde.plugin.gs_cloud.util.GsCloudTableEnum;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractGsCloudDetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeGsCloudPluginType gsCloudPluginType;

    public String getPluginType() {
        return this.gsCloudPluginType.getSymbol();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "GSCLOUD" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder sql = condi.getIncludeUncharged() != false ? this.getIncludeUnchargedSqlTmpl(condi, args, orgMappingType, assistMappingList) : this.getChargedSqlTmpl(condi, args, orgMappingType, assistMappingList);
        StringBuilder yeAssField = new StringBuilder();
        StringBuilder yePzAssField = new StringBuilder();
        StringBuilder yeGroupField = new StringBuilder();
        StringBuilder masterAssField = new StringBuilder();
        StringBuilder orderAssField = new StringBuilder();
        StringBuilder yeJoinSql = new StringBuilder();
        StringBuilder pzJoinSql = new StringBuilder();
        StringBuilder externalAssConfigSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            yeAssField.append(String.format("%1$s.CODE AS %1$s, %1$s.NAME AS %1$s_NAME, ", assistMapping.getAssistCode()));
            yePzAssField.append(String.format("B.%1$s AS %1$s, B.%1$s_NAME AS %1$s_NAME, ", assistMapping.getAssistCode()));
            yeJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = T.%3$s \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
            pzJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = VOUCHERDETAIL.%3$s \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
            yeGroupField.append(String.format(", %1$s.CODE, %1$s.NAME", assistMapping.getAssistCode()));
            masterAssField.append(String.format(", B.%1$s, B.%1$s_NAME", assistMapping.getAssistCode()));
            orderAssField.append(String.format(", %1$s", assistMapping.getAssistCode()));
            externalAssConfigSql.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), "EQ"));
        }
        Variable variable = new Variable();
        variable.put("YEASSFIELD", yeAssField.toString());
        variable.put("YEPZASSFIELD", yePzAssField.toString());
        variable.put("YEJOINSQL", yeJoinSql.toString());
        variable.put("PZJOINSQL", pzJoinSql.toString());
        variable.put("YEGROUPFIELD", yeGroupField.toString());
        variable.put("MASTERASSFIELD", masterAssField.toString());
        variable.put("ORDERASSFIELD", orderAssField.toString());
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
        variable.put("EXTERNAL_ASS_CONFIG_SQL", externalAssConfigSql.toString());
        variable.put("SUBJECTCODE", condi.getSubjectCode());
        String replaceSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        String executeSql = GsCloudTableEnum.replaceAccYear(replaceSql, condi.getAcctYear());
        QueryParam queryParam = new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new DetailLedgerResultSetExtractor(assistMappingList));
        return queryParam;
    }

    private StringBuilder getChargedSqlTmpl(PenetrateBaseDTO condi, List<Object> args, String orgMappingType, List<AssistMappingBO<AssistPojo>> assistMappingList) {
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 2 AS ROWTYPE,  \n");
        sql.append("       NULL AS ID,  \n");
        sql.append("       NULL AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append(String.format("       '%d' as ACCTPERIOD,  \n", condi.getStartPeriod()));
        sql.append("       NULL AS ACCTDAY,  \n");
        sql.append("       NULL AS VCHRDATE,  \n");
        sql.append("       NULL AS VCHRTYPE,  \n");
        sql.append("       NULL AS ORDERID,  \n");
        sql.append("       1 AS ORIENT,  \n");
        sql.append(String.format("       %s AS SUBJECTCODE,  \n", sqlHandler.toChar("SUBJECT.CODE")));
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,   \n");
        sql.append("       ${YEASSFIELD} \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("       SUM(T.DEBITAMT) AS DEBIT,  \n");
        sql.append("       SUM(T.CREDITAMT) AS CREDIT,  \n");
        sql.append("       SUM(T.DEBITFOR) AS ORGND,  \n");
        sql.append("       SUM(T.CREDITFOR) AS ORGNC,  \n");
        sql.append("       SUM(CASE WHEN T.ACCPERIODCODE='${STARTPERIOD}' THEN T.ENDBALANCEAMT - T.DEBITAMT + T.CREDITAMT ELSE 0 END) AS YE,  \n");
        sql.append("       SUM(CASE WHEN T.ACCPERIODCODE='${STARTPERIOD}' THEN T.ENDBALANCEFOR - T.DEBITFOR + T.CREDITFOR ELSE 0 END) AS ORGNYE  \n");
        if (assistMappingList.isEmpty()) {
            sql.append("  FROM FIGLACCOUNTBALANCE${YEAR}  T \n");
        } else {
            sql.append("  FROM FIGLASSBALANCE${YEAR}  T \n");
        }
        sql.append("       ${YEJOINSQL}");
        sql.append("  INNER JOIN (${EXTERNAL_ORG_SQL}) ORG ON ORG.ID = T.${ORGID} \n");
        sql.append("  INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = T.ACCTITLEID \n");
        sql.append(" WHERE 1 = 1 \n");
        if (orgMappingType.equals("BOOKCODE")) {
            sql.append("   AND ORG.BOOKCODE = ? \n");
            args.add(condi.getOrgMapping().getAcctBookCode());
        } else {
            sql.append("   AND ORG.CODE= ? \n");
            args.add(condi.getOrgMapping().getAcctOrgCode());
        }
        sql.append("   AND T.ACCPERIODCODE >= '${STARTPERIOD}' \n");
        sql.append("   AND T.ACCPERIODCODE <= '${ENDPERIOD}' \n");
        sql.append("   AND T.YEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("   AND SUBJECT.CODE = '${SUBJECTCODE}' \n");
        sql.append("   ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" GROUP BY T.YEAR, SUBJECT.CODE, SUBJECT.NAME \n");
        sql.append("       ${YEGROUPFIELD} \n");
        sql.append(" UNION ALL  \n");
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       VOUCHERDETAIL.ID AS ID,  \n");
        sql.append("       VOUCHERDETAIL.ACCDOCID AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append("       VOUCHER.ACCPERIODCODE AS ACCTPERIOD,  \n");
        sql.append("       SUBSTR(VOUCHER.ACCDOCDATE,7,2) AS ACCTDAY,  \n");
        sql.append("       VOUCHER.ACCDOCDATE AS VCHRDATE,  \n");
        sql.append("       SUBSTR(VOUCHER.ACCDOCCODE,1,1)  || '-' || SUBSTR(VOUCHER.ACCDOCCODE,2) AS VCHRTYPE,  \n");
        if (assistMappingList.isEmpty()) {
            sql.append("       VOUCHERDETAIL.ACCENTRYCODE AS ORDERID,  \n");
        } else {
            sql.append("       VOUCHERDETAIL.ACCASSCODE AS ORDERID,  \n");
        }
        sql.append("       SUBJECT.ORIENT AS ORIENT,  \n");
        sql.append(String.format("       %s AS SUBJECTCODE,  \n", sqlHandler.toChar("SUBJECT.CODE")));
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,  \n");
        sql.append("       ${YEASSFIELD} \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("VOUCHER.ABSTRACT")));
        sql.append("       CASE WHEN VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.AMOUNT ELSE 0 END AS DEBIT, \n");
        sql.append("       CASE WHEN VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.AMOUNT ELSE 0 END AS CREDIT, \n");
        sql.append("       CASE WHEN VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.FOREIGNCURRENCY ELSE 0 END AS ORGND, \n");
        sql.append("       CASE WHEN VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.FOREIGNCURRENCY ELSE 0 END AS ORGNC, \n");
        sql.append("       0 AS YE,  \n");
        sql.append("       0 AS ORGNYE  \n");
        sql.append("  FROM FIGLACCOUNTINGDOCUMENT${YEAR} VOUCHER \n");
        if (assistMappingList.isEmpty()) {
            sql.append("  INNER JOIN FIGLACCDOCENTRY${YEAR} VOUCHERDETAIL ON VOUCHER.ACCORGID = VOUCHERDETAIL.ACCORGID AND VOUCHER.ID = VOUCHERDETAIL.ACCDOCID \n");
        } else {
            sql.append("  INNER JOIN FIGLACCDOCASSISTANCE${YEAR} VOUCHERDETAIL ON VOUCHER.ACCORGID = VOUCHERDETAIL.ACCORGID AND VOUCHER.ID = VOUCHERDETAIL.ACCDOCID \n");
        }
        sql.append("       ${PZJOINSQL}");
        sql.append("  INNER JOIN (${EXTERNAL_ORG_SQL}) ORG ON ORG.ID = VOUCHERDETAIL.${ORGID} \n");
        sql.append("  INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = VOUCHERDETAIL.ACCTITLEID \n");
        sql.append(" WHERE 1=1 \n");
        sql.append("   AND VOUCHER.ISCOMPLETE = '1'\n");
        sql.append("   AND VOUCHER.ISVOID = '0'\n");
        sql.append("   AND VOUCHER.ISBOOK = '1'\n");
        if (orgMappingType.equals("BOOKCODE")) {
            sql.append("   AND ORG.BOOKCODE = ? \n");
            args.add(condi.getOrgMapping().getAcctBookCode());
        } else {
            sql.append("   AND ORG.CODE= ? \n");
            args.add(condi.getOrgMapping().getAcctOrgCode());
        }
        sql.append("   AND VOUCHER.YEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("   AND VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' \n");
        sql.append("   AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' \n");
        sql.append("   AND SUBJECT.CODE = '${SUBJECTCODE}' \n");
        sql.append("   ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" ORDER BY ROWTYPE DESC, VCHRDATE, VCHRTYPE, ORDERID  \n");
        sql.append("   ${ORDERASSFIELD} \n");
        return sql;
    }

    private StringBuilder getIncludeUnchargedSqlTmpl(PenetrateBaseDTO condi, List<Object> args, String orgMappingType, List<AssistMappingBO<AssistPojo>> assistMappingList) {
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 2 AS ROWTYPE,  \n");
        sql.append("       NULL AS ID,  \n");
        sql.append("       NULL AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append(String.format("       '%d' as ACCTPERIOD,  \n", condi.getStartPeriod()));
        sql.append("       NULL AS ACCTDAY,  \n");
        sql.append("       NULL AS VCHRDATE,  \n");
        sql.append("       NULL AS VCHRTYPE,  \n");
        sql.append("       NULL AS ORDERID,  \n");
        sql.append("       1 AS ORIENT,  \n");
        sql.append(String.format("       %s AS SUBJECTCODE,  \n", sqlHandler.toChar("B.SUBJECTCODE")));
        sql.append("       B.SUBJECTNAME AS SUBJECTNAME,   \n");
        sql.append("       ${YEPZASSFIELD} \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("       SUM(DEBIT) AS DEBIT, \n");
        sql.append("       SUM(CREDIT) AS CREDIT, \n");
        sql.append("       SUM(ORGND) AS ORGND, \n");
        sql.append("       SUM(ORGNC) AS ORGNC, \n");
        sql.append("       SUM(YE) AS YE, \n");
        sql.append("       SUM(ORGNYE) AS ORGNYE \n");
        sql.append("  FROM \n");
        sql.append(String.format("       (SELECT %s AS SUBJECTCODE,  \n", sqlHandler.toChar("SUBJECT.CODE")));
        sql.append("               SUBJECT.NAME AS SUBJECTNAME,   \n");
        sql.append("               ${YEASSFIELD} \n");
        sql.append("               SUM(T.DEBITAMT) AS DEBIT, \n");
        sql.append("               SUM(T.CREDITAMT) AS CREDIT, \n");
        sql.append("               SUM(T.DEBITFOR) AS ORGND, \n");
        sql.append("               SUM(T.CREDITFOR) AS ORGNC, \n");
        sql.append("               SUM(CASE WHEN T.ACCPERIODCODE='${STARTPERIOD}' THEN T.ENDBALANCEAMT - T.DEBITAMT + T.CREDITAMT ELSE 0 END) AS YE,  \n");
        sql.append("               SUM(CASE WHEN T.ACCPERIODCODE='${STARTPERIOD}' THEN T.ENDBALANCEFOR - T.DEBITFOR + T.CREDITFOR ELSE 0 END) AS ORGNYE  \n");
        if (assistMappingList.isEmpty()) {
            sql.append("          FROM FIGLACCOUNTBALANCE${YEAR}  T \n");
        } else {
            sql.append("          FROM FIGLASSBALANCE${YEAR}  T \n");
        }
        sql.append("               ${YEJOINSQL}");
        sql.append("               INNER JOIN (${EXTERNAL_ORG_SQL}) ORG ON ORG.ID = T.${ORGID} \n");
        sql.append("               INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = T.ACCTITLEID \n");
        sql.append("         WHERE 1 = 1 \n");
        if (orgMappingType.equals("BOOKCODE")) {
            sql.append("   AND ORG.BOOKCODE = ? \n");
            args.add(condi.getOrgMapping().getAcctBookCode());
        } else {
            sql.append("   AND ORG.CODE= ? \n");
            args.add(condi.getOrgMapping().getAcctOrgCode());
        }
        sql.append("           AND T.YEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("           AND T.ACCPERIODCODE>='${STARTPERIOD}' \n");
        sql.append("           AND T.ACCPERIODCODE<='${ENDPERIOD}' \n");
        sql.append("           AND SUBJECT.CODE = '${SUBJECTCODE}' \n");
        sql.append("           ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append("         GROUP BY T.YEAR, SUBJECT.CODE, SUBJECT.NAME \n");
        sql.append("           ${YEGROUPFIELD} \n");
        sql.append("         UNION ALL \n");
        sql.append(String.format("        SELECT %s AS SUBJECTCODE,  \n", sqlHandler.toChar("SUBJECT.CODE")));
        sql.append("               SUBJECT.NAME AS SUBJECTNAME,   \n");
        sql.append("               ${YEASSFIELD} \n");
        sql.append("               SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.AMOUNT ELSE 0 END) AS DEBIT, \n");
        sql.append("               SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.AMOUNT ELSE 0 END) AS CREDIT, \n");
        sql.append("               SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.FOREIGNCURRENCY ELSE 0 END) AS ORGND, \n");
        sql.append("               SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.FOREIGNCURRENCY ELSE 0 END) AS ORGNC, \n");
        if (condi.getStartPeriod() != 1) {
            sql.append("               SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${PREVIOUSPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.AMOUNT WHEN VOUCHER.ACCPERIODCODE<='${PREVIOUSPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.AMOUNT * -1 ELSE 0 END) AS YE, \n");
            sql.append("               SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${PREVIOUSPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.FOREIGNCURRENCY WHEN VOUCHER.ACCPERIODCODE<='${PREVIOUSPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.FOREIGNCURRENCY * -1 ELSE 0 END) AS ORGNYE \n");
        } else {
            sql.append("               0 AS YE, \n");
            sql.append("               0 AS ORGNYE \n");
        }
        sql.append("          FROM FIGLACCOUNTINGDOCUMENT${YEAR} VOUCHER \n");
        if (assistMappingList.isEmpty()) {
            sql.append("          INNER JOIN FIGLACCDOCENTRY${YEAR} VOUCHERDETAIL ON VOUCHER.ACCORGID = VOUCHERDETAIL.ACCORGID AND VOUCHER.ID = VOUCHERDETAIL.ACCDOCID \n");
        } else {
            sql.append("          INNER JOIN FIGLACCDOCASSISTANCE${YEAR} VOUCHERDETAIL ON VOUCHER.ACCORGID = VOUCHERDETAIL.ACCORGID AND VOUCHER.ID = VOUCHERDETAIL.ACCDOCID \n");
        }
        sql.append("               ${PZJOINSQL}");
        sql.append("              INNER JOIN (${EXTERNAL_ORG_SQL}) ORG ON ORG.ID = VOUCHERDETAIL.${ORGID} \n");
        sql.append("              INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = VOUCHERDETAIL.ACCTITLEID \n");
        sql.append("             WHERE 1 = 1 \n");
        sql.append("               AND VOUCHER.ISCOMPLETE = '1'\n");
        sql.append("               AND VOUCHER.ISVOID = '0'\n");
        sql.append("               AND VOUCHER.ISBOOK = '0'\n");
        if (orgMappingType.equals("BOOKCODE")) {
            sql.append("   AND ORG.BOOKCODE = ? \n");
            args.add(condi.getOrgMapping().getAcctBookCode());
        } else {
            sql.append("   AND ORG.CODE= ? \n");
            args.add(condi.getOrgMapping().getAcctOrgCode());
        }
        sql.append("               AND VOUCHER.YEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("               AND SUBJECT.CODE = '${SUBJECTCODE}' \n");
        sql.append("               ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append("             GROUP BY VOUCHERDETAIL.YEAR, SUBJECT.CODE, SUBJECT.NAME \n");
        sql.append("               ${YEGROUPFIELD} ) B \n");
        sql.append("             GROUP BY B.SUBJECTCODE, B.SUBJECTNAME  \n");
        sql.append("             ${MASTERASSFIELD} \n");
        sql.append(" UNION ALL  \n");
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       VOUCHERDETAIL.ID AS ID,  \n");
        sql.append("       VOUCHERDETAIL.ACCDOCID AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append("       VOUCHER.ACCPERIODCODE AS ACCTPERIOD,  \n");
        sql.append("       SUBSTR(VOUCHER.ACCDOCDATE,7,2) AS ACCTDAY,  \n");
        sql.append("       VOUCHER.ACCDOCDATE AS VCHRDATE,  \n");
        sql.append("       SUBSTR(VOUCHER.ACCDOCCODE,1,1)  || '-' || SUBSTR(VOUCHER.ACCDOCCODE,2) AS VCHRTYPE,  \n");
        if (assistMappingList.isEmpty()) {
            sql.append("       VOUCHERDETAIL.ACCENTRYCODE AS ORDERID,  \n");
        } else {
            sql.append("       VOUCHERDETAIL.ACCASSCODE AS ORDERID,  \n");
        }
        sql.append("       SUBJECT.ORIENT AS ORIENT,  \n");
        sql.append(String.format("       %s AS SUBJECTCODE,  \n", sqlHandler.toChar("SUBJECT.CODE")));
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,  \n");
        sql.append("       ${YEASSFIELD} \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("VOUCHER.ABSTRACT")));
        sql.append("       CASE WHEN VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.AMOUNT ELSE 0 END AS DEBIT, \n");
        sql.append("       CASE WHEN VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.AMOUNT ELSE 0 END AS CREDIT, \n");
        sql.append("       CASE WHEN VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.FOREIGNCURRENCY ELSE 0 END AS ORGND, \n");
        sql.append("       CASE WHEN VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.FOREIGNCURRENCY ELSE 0 END AS ORGNC, \n");
        sql.append("       0 AS YE,  \n");
        sql.append("       0 AS ORGNYE  \n");
        sql.append("  FROM FIGLACCOUNTINGDOCUMENT${YEAR} VOUCHER \n");
        if (assistMappingList.isEmpty()) {
            sql.append("  INNER JOIN FIGLACCDOCENTRY${YEAR} VOUCHERDETAIL ON VOUCHER.ACCORGID = VOUCHERDETAIL.ACCORGID AND VOUCHER.ID = VOUCHERDETAIL.ACCDOCID \n");
        } else {
            sql.append("  INNER JOIN FIGLACCDOCASSISTANCE${YEAR} VOUCHERDETAIL ON VOUCHER.ACCORGID = VOUCHERDETAIL.ACCORGID AND VOUCHER.ID = VOUCHERDETAIL.ACCDOCID \n");
        }
        sql.append("       ${PZJOINSQL}");
        sql.append("  INNER JOIN (${EXTERNAL_ORG_SQL}) ORG ON ORG.ID = VOUCHERDETAIL.${ORGID} \n");
        sql.append("  INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = VOUCHERDETAIL.ACCTITLEID \n");
        sql.append(" WHERE 1=1 \n");
        sql.append("   AND VOUCHER.ISCOMPLETE = '1'\n");
        sql.append("   AND VOUCHER.ISVOID = '0'\n");
        if (orgMappingType.equals("BOOKCODE")) {
            sql.append("   AND ORG.BOOKCODE = ? \n");
            args.add(condi.getOrgMapping().getAcctBookCode());
        } else {
            sql.append("   AND ORG.CODE= ? \n");
            args.add(condi.getOrgMapping().getAcctOrgCode());
        }
        sql.append("   AND VOUCHER.YEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("   AND VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' \n");
        sql.append("   AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' \n");
        sql.append("   AND SUBJECT.CODE = '${SUBJECTCODE}' \n");
        sql.append(" ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" ORDER BY ROWTYPE DESC, VCHRDATE, VCHRTYPE, ORDERID  \n");
        sql.append(" ${ORDERASSFIELD} \n");
        return sql;
    }
}

