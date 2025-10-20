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
 *  com.jiuqi.bde.penetrate.impl.common.RowTypeEnum
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.gs_cloud.penetrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.RowTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.gs_cloud.BdeGsCloudPluginType;
import com.jiuqi.bde.plugin.gs_cloud.util.AssistPojo;
import com.jiuqi.bde.plugin.gs_cloud.util.GsCloudTableEnum;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.va.domain.common.PageVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractGsCloudBalanceContentProvider
extends AbstractBalanceContentProvider {
    @Autowired
    private BdeGsCloudPluginType gsCloudPluginType;

    public String getPluginType() {
        return this.gsCloudPluginType.getSymbol();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "GSCLOUD" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder sql = condi.getIncludeUncharged() != false ? this.getIncludeUnchargedSqlTmpl(condi, args, orgMappingType, assistMappingList) : this.getChargedSqlTmpl(condi, args, orgMappingType, assistMappingList);
        StringBuilder yeAssField = new StringBuilder();
        StringBuilder yeGroupField = new StringBuilder();
        StringBuilder yeOrderField = new StringBuilder();
        StringBuilder masterAssField = new StringBuilder();
        StringBuilder masterOrderAssField = new StringBuilder();
        StringBuilder yeJoinSql = new StringBuilder();
        StringBuilder pzJoinSql = new StringBuilder();
        StringBuilder externalAssConfigSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            yeAssField.append(String.format(", %1$s.CODE AS %1$s, %1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            yeJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = T.%3$s \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
            pzJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = VOUCHERDETAIL.%3$s \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
            yeGroupField.append(String.format(", %1$s.CODE, %1$s.NAME", assistMapping.getAssistCode()));
            yeOrderField.append(String.format(", %1$s", assistMapping.getAssistCode()));
            masterAssField.append(String.format(", MASTER.%1$s, MASTER.%1$s_NAME", assistMapping.getAssistCode()));
            masterOrderAssField.append(String.format(", MASTER.%1$s", assistMapping.getAssistCode()));
            externalAssConfigSql.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule()));
        }
        Variable variable = new Variable();
        variable.put("YEASSFIELD", yeAssField.toString());
        variable.put("YEJOINSQL", yeJoinSql.toString());
        variable.put("PZJOINSQL", pzJoinSql.toString());
        variable.put("YEGROUPFIELD", yeGroupField.toString());
        variable.put("YEORDERFIELD", yeOrderField.toString());
        variable.put("MASTERASSFIELD", masterAssField.toString());
        variable.put("MASTERORDERASSFIELD", masterOrderAssField.toString());
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
        String replaceSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        String executeSql = GsCloudTableEnum.replaceAccYear(replaceSql, condi.getAcctYear());
        QueryParam queryParam = new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new BalanceResultSetExtractor(assistMappingList));
        return queryParam;
    }

    protected PageVO<PenetrateBalance> processResult(PenetrateBaseDTO condi, PageVO<PenetrateBalance> queryResult) {
        PenetrateBalance total = new PenetrateBalance();
        total.setRowType(Integer.valueOf(RowTypeEnum.TOTAL.ordinal()));
        total.setSubjectCode(GcI18nUtil.getMessage((String)"bde.accountant.total"));
        Map<Boolean, List<PenetrateBalance>> partitioned = queryResult.getRows().stream().collect(Collectors.partitioningBy(penetrateBalance -> GcI18nUtil.getMessage((String)"bde.accountant.total").equals(penetrateBalance.getSubjectCode())));
        List<PenetrateBalance> detailList = partitioned.get(false);
        List<PenetrateBalance> totalList = partitioned.get(true);
        List<PenetrateBalance> leafDetailList = this.filterNotLeaf(detailList);
        queryResult.setTotal(leafDetailList.size());
        leafDetailList.forEach(item -> {
            total.setNc(NumberUtils.sum((BigDecimal)item.getNc(), (BigDecimal)total.getNc()));
            total.setOrgnNc(NumberUtils.sum((BigDecimal)item.getOrgnNc(), (BigDecimal)total.getOrgnNc()));
            total.setQc(NumberUtils.sum((BigDecimal)item.getQc(), (BigDecimal)total.getQc()));
            total.setOrgnQc(NumberUtils.sum((BigDecimal)item.getOrgnQc(), (BigDecimal)total.getOrgnQc()));
            total.setDebit(NumberUtils.sum((BigDecimal)item.getDebit(), (BigDecimal)total.getDebit()));
            total.setOrgnd(NumberUtils.sum((BigDecimal)item.getOrgnd(), (BigDecimal)total.getOrgnd()));
            total.setCredit(NumberUtils.sum((BigDecimal)item.getCredit(), (BigDecimal)total.getCredit()));
            total.setOrgnc(NumberUtils.sum((BigDecimal)item.getOrgnc(), (BigDecimal)total.getOrgnc()));
            total.setDsum(NumberUtils.sum((BigDecimal)item.getDsum(), (BigDecimal)total.getDsum()));
            total.setOrgnDsum(NumberUtils.sum((BigDecimal)item.getOrgnDsum(), (BigDecimal)total.getOrgnDsum()));
            total.setCsum(NumberUtils.sum((BigDecimal)item.getCsum(), (BigDecimal)total.getCsum()));
            total.setOrgnCsum(NumberUtils.sum((BigDecimal)item.getOrgnCsum(), (BigDecimal)total.getOrgnCsum()));
            total.setYe(NumberUtils.sum((BigDecimal)item.getYe(), (BigDecimal)total.getYe()));
            total.setOrgnYe(NumberUtils.sum((BigDecimal)item.getOrgnYe(), (BigDecimal)total.getOrgnYe()));
            this.formatBalanceOrient((PenetrateBalance)item);
        });
        this.formatBalanceOrient(total);
        ArrayList result = CollectionUtils.newArrayList((Object[])new PenetrateBalance[]{total});
        if (totalList != null && !totalList.isEmpty()) {
            result.addAll(totalList);
        }
        List<PenetrateBalance> detailData = Boolean.FALSE.equals(condi.getPagination()) ? leafDetailList : leafDetailList.stream().skip(condi.getOffset().intValue()).limit(condi.getLimit().intValue()).collect(Collectors.toList());
        result.addAll(detailData);
        queryResult.setRows((List)result);
        return queryResult;
    }

    private StringBuilder getChargedSqlTmpl(PenetrateBaseDTO condi, List<Object> args, String orgMappingType, List<AssistMappingBO<AssistPojo>> assistMappingList) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUBJECT.CODE AS SUBJECTCODE, \n");
        sql.append("       SUBJECT.NAME AS SUBJECTNAME, \n");
        sql.append("       HB.CODE AS CURRENCYCODE, \n");
        sql.append("       SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.BEGINBALANCEAMT ELSE 0 END) AS NC, \n");
        sql.append("       SUM(CASE WHEN T.ACCPERIODCODE='${STARTPERIOD}' THEN T.ENDBALANCEAMT - T.DEBITAMT + T.CREDITAMT ELSE 0 END) AS QC, \n");
        sql.append("       SUM(T.DEBITAMT) AS DEBIT, \n");
        sql.append("       SUM(T.CREDITAMT) AS CREDIT, \n");
        sql.append("       SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.CURCUMDRAMT ELSE 0 END) AS DSUM, \n");
        sql.append("       SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.CURCUMCRAMT ELSE 0 END) AS CSUM, \n");
        sql.append("       SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.ENDBALANCEAMT ELSE 0 END) AS YE, \n");
        sql.append("       SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.BEGINBALANCEFOR ELSE 0 END) AS ORGNNC, \n");
        sql.append("       SUM(CASE WHEN T.ACCPERIODCODE='${STARTPERIOD}' THEN T.ENDBALANCEFOR - T.DEBITFOR + T.CREDITFOR ELSE 0 END) AS ORGNQC, \n");
        sql.append("       SUM(T.DEBITFOR) AS ORGND, \n");
        sql.append("       SUM(T.CREDITFOR) AS ORGNC, \n");
        sql.append("       SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.CURCUMDRFOR ELSE 0 END) AS ORGNDSUM, \n");
        sql.append("       SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.CURCUMCRFOR ELSE 0 END) AS ORGNCSUM,\n");
        sql.append("       SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.ENDBALANCEFOR ELSE 0 END) AS ORGNYE \n");
        sql.append("       ${YEASSFIELD} \n");
        if (assistMappingList.isEmpty()) {
            sql.append("  FROM FIGLACCOUNTBALANCE${YEAR}  T \n");
        } else {
            sql.append("  FROM FIGLASSBALANCE${YEAR}  T \n");
        }
        sql.append("       ${YEJOINSQL}");
        sql.append("  INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON HB.ID = T.CURRENCYID \n");
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
        sql.append(this.buildSubjectCondi("SUBJECT", "CODE", condi.getSubjectCode())).append(" \n");
        sql.append(this.buildExcludeCondi("SUBJECT", "CODE", condi.getExcludeSubjectCode())).append(" \n");
        sql.append("   ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" GROUP BY SUBJECT.CODE, SUBJECT.NAME, HB.CODE \n");
        sql.append("       ${YEGROUPFIELD} \n");
        sql.append(" ORDER BY SUBJECT.CODE, HB.CODE \n");
        sql.append("       ${YEORDERFIELD} \n");
        return sql;
    }

    private StringBuilder getIncludeUnchargedSqlTmpl(PenetrateBaseDTO condi, List<Object> args, String orgMappingType, List<AssistMappingBO<AssistPojo>> assistMappingList) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  \n");
        sql.append("        MASTER.SUBJECTCODE AS SUBJECTCODE, \n");
        sql.append("        MASTER.SUBJECTNAME AS SUBJECTNAME, \n");
        sql.append("        MASTER.CURRENCYCODE AS CURRENCYCODE, \n");
        sql.append("        SUM(NC) AS NC, \n");
        sql.append("        SUM(ORGNNC) AS ORGNNC, \n");
        sql.append("        SUM(QC) AS QC, \n");
        sql.append("        SUM(ORGNQC) AS ORGNQC, \n");
        sql.append("        SUM(DEBIT) AS DEBIT, \n");
        sql.append("        SUM(CREDIT) AS CREDIT, \n");
        sql.append("        SUM(DSUM) AS DSUM, \n");
        sql.append("        SUM(CSUM) AS CSUM, \n");
        sql.append("        SUM(ORGND) AS ORGND, \n");
        sql.append("        SUM(ORGNC) AS ORGNC, \n");
        sql.append("        SUM(ORGNDSUM) AS ORGNDSUM, \n");
        sql.append("        SUM(ORGNCSUM) AS ORGNCSUM, \n");
        sql.append("        SUM(YE) AS YE, \n");
        sql.append("        SUM(ORGNYE) AS ORGNYE \n");
        sql.append("        ${MASTERASSFIELD} \n");
        sql.append(" FROM  \n");
        sql.append("     (SELECT SUBJECT.CODE AS SUBJECTCODE, \n");
        sql.append("             SUBJECT.NAME AS SUBJECTNAME, \n");
        sql.append("             HB.CODE AS CURRENCYCODE, \n");
        sql.append("             SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.BEGINBALANCEAMT ELSE 0 END) AS NC, \n");
        sql.append("             SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.BEGINBALANCEFOR ELSE 0 END) AS ORGNNC, \n");
        sql.append("             SUM(CASE WHEN T.ACCPERIODCODE='${STARTPERIOD}' THEN T.ENDBALANCEAMT - T.DEBITAMT + T.CREDITAMT ELSE 0 END) AS QC, \n");
        sql.append("             SUM(CASE WHEN T.ACCPERIODCODE='${STARTPERIOD}' THEN T.ENDBALANCEFOR - T.DEBITFOR + T.CREDITFOR ELSE 0 END) AS ORGNQC, \n");
        sql.append("             SUM(T.DEBITAMT) AS DEBIT, \n");
        sql.append("             SUM(T.CREDITAMT) AS CREDIT, \n");
        sql.append("             SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.CURCUMDRAMT ELSE 0 END) AS DSUM, \n");
        sql.append("             SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.CURCUMCRAMT ELSE 0 END) AS CSUM, \n");
        sql.append("             SUM(T.DEBITFOR) AS ORGND, \n");
        sql.append("             SUM(T.CREDITFOR) AS ORGNC, \n");
        sql.append("             SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.CURCUMDRFOR ELSE 0 END) AS ORGNDSUM, \n");
        sql.append("             SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.CURCUMCRFOR ELSE 0 END) AS ORGNCSUM, \n");
        sql.append("             SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.ENDBALANCEAMT ELSE 0 END) AS YE, \n");
        sql.append("             SUM(CASE WHEN T.ACCPERIODCODE='${ENDPERIOD}' THEN T.ENDBALANCEFOR ELSE 0 END) AS ORGNYE \n");
        sql.append("             ${YEASSFIELD} \n");
        if (assistMappingList.isEmpty()) {
            sql.append("        FROM FIGLACCOUNTBALANCE${YEAR}  T \n");
        } else {
            sql.append("        FROM FIGLASSBALANCE${YEAR}  T \n");
        }
        sql.append("        ${YEJOINSQL}");
        sql.append("        INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON HB.ID = T.CURRENCYID \n");
        sql.append("        INNER JOIN (${EXTERNAL_ORG_SQL}) ORG ON ORG.ID = T.${ORGID} \n");
        sql.append("        INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = T.ACCTITLEID \n");
        sql.append("       WHERE 1=1 \n");
        if (orgMappingType.equals("BOOKCODE")) {
            sql.append("   AND ORG.BOOKCODE = ? \n");
            args.add(condi.getOrgMapping().getAcctBookCode());
        } else {
            sql.append("   AND ORG.CODE= ? \n");
            args.add(condi.getOrgMapping().getAcctOrgCode());
        }
        sql.append("         AND T.ACCPERIODCODE>='${STARTPERIOD}' \n");
        sql.append("         AND T.ACCPERIODCODE<='${ENDPERIOD}' \n");
        sql.append("         AND T.YEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append(this.buildSubjectCondi("SUBJECT", "CODE", condi.getSubjectCode())).append(" \n");
        sql.append(this.buildExcludeCondi("SUBJECT", "CODE", condi.getExcludeSubjectCode())).append(" \n");
        sql.append("       ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append("       GROUP BY SUBJECT.CODE, SUBJECT.NAME, HB.CODE \n");
        sql.append("       ${YEGROUPFIELD} \n");
        sql.append("   UNION ALL \n");
        sql.append("      SELECT \n");
        sql.append("             SUBJECT.CODE AS SUBJECTCODE, \n");
        sql.append("             SUBJECT.NAME AS SUBJECTNAME, \n");
        sql.append("             HB.CODE AS CURRENCYCODE, \n");
        sql.append("             0 AS NC, \n");
        sql.append("             0 AS ORGNNC, \n");
        if (condi.getStartPeriod() != 1) {
            sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${PREVIOUSPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.AMOUNT WHEN VOUCHER.ACCPERIODCODE<='${PREVIOUSPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.AMOUNT * -1 ELSE 0 END) AS QC, \n");
            sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${PREVIOUSPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.FOREIGNCURRENCY WHEN VOUCHER.ACCPERIODCODE<='${PREVIOUSPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.FOREIGNCURRENCY * -1 ELSE 0 END) AS ORGNQC, \n");
        } else {
            sql.append("             0 AS QC, \n");
            sql.append("             0 AS ORGNQC, \n");
        }
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.AMOUNT ELSE 0 END) AS DEBIT, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.AMOUNT ELSE 0 END) AS CREDIT, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.AMOUNT ELSE 0 END) AS DSUM, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.AMOUNT ELSE 0 END) AS CSUM, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.FOREIGNCURRENCY ELSE 0 END) AS ORGND, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.FOREIGNCURRENCY ELSE 0 END) AS ORGNC, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.FOREIGNCURRENCY ELSE 0 END) AS ORGNDSUM, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.FOREIGNCURRENCY ELSE 0 END) AS ORGNCSUM, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.AMOUNT WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.AMOUNT * -1 ELSE 0 END) AS YE, \n");
        sql.append("             SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 1 THEN VOUCHERDETAIL.FOREIGNCURRENCY WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' AND VOUCHERDETAIL.LENDINGDIRECTION = 2 THEN VOUCHERDETAIL.FOREIGNCURRENCY * -1 ELSE 0 END) AS ORGNYE \n");
        sql.append("             ${YEASSFIELD} \n");
        sql.append("        FROM FIGLACCOUNTINGDOCUMENT${YEAR} VOUCHER \n");
        if (assistMappingList.isEmpty()) {
            sql.append("        INNER JOIN FIGLACCDOCENTRY${YEAR} VOUCHERDETAIL ON VOUCHER.ACCORGID = VOUCHERDETAIL.ACCORGID AND VOUCHER.ID = VOUCHERDETAIL.ACCDOCID \n");
            sql.append("        INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON VOUCHERDETAIL.CURRENCYID = HB.ID \n");
        } else {
            sql.append("        INNER JOIN FIGLACCDOCASSISTANCE${YEAR} VOUCHERDETAIL ON VOUCHER.ACCORGID = VOUCHERDETAIL.ACCORGID AND VOUCHER.ID = VOUCHERDETAIL.ACCDOCID \n");
            sql.append("        INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON VOUCHERDETAIL.FOREIGNCURRENCYID = HB.ID \n");
        }
        sql.append("        ${PZJOINSQL} \n");
        sql.append("        INNER JOIN (${EXTERNAL_ORG_SQL}) ORG ON ORG.ID = VOUCHERDETAIL.${ORGID} \n");
        sql.append("        INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = VOUCHERDETAIL.ACCTITLEID \n");
        sql.append("       WHERE 1=1 \n");
        sql.append("         AND VOUCHER.ISCOMPLETE = '1'\n");
        sql.append("         AND VOUCHER.ISVOID = '0'\n");
        sql.append("         AND VOUCHER.ISBOOK = '0'\n");
        if (orgMappingType.equals("BOOKCODE")) {
            sql.append("   AND ORG.BOOKCODE = ? \n");
            args.add(condi.getOrgMapping().getAcctBookCode());
        } else {
            sql.append("   AND ORG.CODE= ? \n");
            args.add(condi.getOrgMapping().getAcctOrgCode());
        }
        sql.append("         AND VOUCHER.YEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append(this.buildSubjectCondi("SUBJECT", "CODE", condi.getSubjectCode())).append(" \n");
        sql.append(this.buildExcludeCondi("SUBJECT", "CODE", condi.getExcludeSubjectCode())).append(" \n");
        sql.append("       ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append("       GROUP BY SUBJECT.CODE, SUBJECT.NAME, HB.CODE \n");
        sql.append("       ${YEGROUPFIELD} ) MASTER \n");
        sql.append(" GROUP BY MASTER.SUBJECTCODE, MASTER.SUBJECTNAME, MASTER.CURRENCYCODE \n");
        sql.append("       ${MASTERASSFIELD} \n");
        sql.append(" ORDER BY MASTER.SUBJECTCODE, MASTER.CURRENCYCODE \n");
        sql.append("       ${MASTERORDERASSFIELD} \n");
        return sql;
    }

    public List<PenetrateBalance> filterNotLeaf(List<PenetrateBalance> detailList) {
        if (detailList == null || detailList.isEmpty()) {
            return detailList;
        }
        HashSet<String> subjectCodeSet = new HashSet<String>();
        for (int i = 0; i <= detailList.size() - 1; ++i) {
            Iterator subjectCode = detailList.get(i).get((Object)"SUBJECTCODE");
            subjectCodeSet.add((String)((Object)subjectCode));
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
        ArrayList<PenetrateBalance> leafRowDatas = new ArrayList<PenetrateBalance>();
        for (PenetrateBalance rowData : detailList) {
            Object subjectCode = rowData.get((Object)"SUBJECTCODE");
            if (!leafSubjectCodeSet.contains(subjectCode)) continue;
            leafRowDatas.add(rowData);
        }
        return leafRowDatas;
    }

    private void formatBalanceOrient(PenetrateBalance item) {
        item.setNcOrient(this.formatOrient(item.getNc()));
        item.setNc(item.getNc().abs());
        item.setOrgnNc(item.getOrgnNc().abs());
        item.setQcOrient(this.formatOrient(item.getQc()));
        item.setQc(item.getQc().abs());
        item.setOrgnQc(item.getOrgnQc().abs());
        item.setYeOrient(this.formatOrient(item.getYe()));
        item.setYe(item.getYe().abs());
        item.setOrgnYe(item.getOrgnYe().abs());
    }
}

