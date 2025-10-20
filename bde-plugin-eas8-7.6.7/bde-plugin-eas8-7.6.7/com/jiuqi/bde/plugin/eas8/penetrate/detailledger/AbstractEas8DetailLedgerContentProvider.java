/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue
 *  com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.eas8.penetrate.detailledger;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue;
import com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.eas8.Eas8PluginType;
import com.jiuqi.bde.plugin.eas8.util.Eas8FetchUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractEas8DetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private Eas8PluginType eas8PluginType;

    public String getPluginType() {
        return this.eas8PluginType.getSymbol();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        UnitAndBookValue unitAndBookValue = OrgSqlUtil.getUnitAndBookValue((OrgMappingDTO)condi.getOrgMapping());
        ArrayList<Integer> args = new ArrayList<Integer>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 2 AS ROWTYPE,  \n");
        sql.append("       NULL AS ID,  \n");
        sql.append("       NULL AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append(String.format("       %d as ACCTPERIOD,  \n", condi.getStartPeriod()));
        sql.append("       NULL AS ACCTDAY,  \n");
        sql.append("       NULL AS VCHRTYPE,  \n");
        if (!assistMappingList.isEmpty()) {
            sql.append("       NULL AS PZFLORDERID,  \n");
        }
        sql.append("       NULL AS ORDERID,  \n");
        sql.append("       1 AS ORIENT,  \n");
        sql.append("       NULL AS SUBJECTCODE,  \n");
        sql.append("       NULL AS SUBJECTNAME,   \n");
        sql.append("       ${YEASSFIELD} \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("       0 AS DEBIT,  \n");
        sql.append("       0 AS CREDIT,  \n");
        sql.append("       0 AS ORGND,  \n");
        sql.append("       0 ORGNC,  \n");
        sql.append("       SUM(T.FBEGINBALANCELOCAL) AS YE,  \n");
        sql.append("       SUM(T.FBEGINBALANCEFOR) AS ORGNYE  \n");
        sql.append("  FROM ${TABLENAME} T  \n");
        sql.append("       ${YEJOINSQL}");
        sql.append("  INNER JOIN (${EXTERNAL_ORG_SQL}) COMPANY ON T.FORGUNITID= COMPANY.ID \n");
        sql.append("  INNER JOIN (${EXTERNAL_SUBJECT_SQL}) SUBJECT \n");
        sql.append("     ON T.FACCOUNTID = SUBJECT.ID \n");
        sql.append("    AND COMPANY.FACCOUNTTABLEID = SUBJECT.FACCOUNTTABLEID  \n");
        sql.append("    AND SUBJECT.FCOMPANYID = COMPANY.ID  \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("       ${UNITCONDITION}  \n");
        sql.append("   AND T.FPERIODNUMBER = ${STARTPERIOD} \n");
        sql.append("   AND T.FPERIODYEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("       ${YETYPE} \n");
        sql.append(this.buildSubjectCondi("SUBJECT", "CODE", condi.getSubjectCode())).append(" \n");
        sql.append("   ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" GROUP BY T.FPERIODYEAR \n");
        sql.append("       ${YEGROUPFIELD} \n");
        sql.append(" UNION ALL  \n");
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       VOUCHERDETAIL.FID AS ID,  \n");
        sql.append("       VOUCHERDETAIL.FBILLID AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append("       PERIOD.FPERIODNUMBER AS ACCTPERIOD,  \n");
        sql.append(String.format("       %s AS ACCTDAY,  \n", sqlHandler.day("VOUCHER.FBIZDATE")));
        sql.append(sqlHandler.concatBySeparator("-", new String[]{"VOUCHERTYPE.FNUMBER", "VOUCHER.FNUMBER"})).append(" AS VCHRTYPE, \n");
        if (!assistMappingList.isEmpty()) {
            sql.append("       VOUCHERENTRY.FSEQ AS PZFLORDERID,  \n");
        }
        sql.append("       VOUCHERDETAIL.FSEQ AS ORDERID,  \n");
        sql.append("       SUBJECT.ORIENT AS ORIENT,  \n");
        sql.append(String.format("       %s AS SUBJECTCODE,  \n", sqlHandler.toChar("SUBJECT.CODE")));
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,  \n");
        sql.append("       ${YEASSFIELD} \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("VOUCHER.FABSTRACT")));
        sql.append("       CASE WHEN ${VOUCHERDETAIL}.FENTRYDC = 1 THEN VOUCHERDETAIL.FLOCALAMOUNT ELSE 0 END AS DEBIT, \n");
        sql.append("       CASE WHEN ${VOUCHERDETAIL}.FENTRYDC = 0 THEN VOUCHERDETAIL.FLOCALAMOUNT ELSE 0 END AS CREDIT, \n");
        sql.append("       CASE WHEN ${VOUCHERDETAIL}.FENTRYDC = 1 THEN VOUCHERDETAIL.FORIGINALAMOUNT ELSE 0 END AS ORGND, \n");
        sql.append("       CASE WHEN ${VOUCHERDETAIL}.FENTRYDC = 0 THEN VOUCHERDETAIL.FORIGINALAMOUNT ELSE 0 END AS ORGNC, \n");
        sql.append("       0 AS YE,  \n");
        sql.append("       0 AS ORGNYE  \n");
        sql.append("  FROM T_GL_VOUCHER VOUCHER \n");
        if (assistMappingList.isEmpty()) {
            sql.append("  INNER JOIN T_GL_VOUCHERENTRY VOUCHERDETAIL ON VOUCHER.FID = VOUCHERDETAIL.FBILLID \n");
        } else {
            sql.append("  INNER JOIN T_GL_VOUCHERENTRY VOUCHERENTRY ON VOUCHER.FID = VOUCHERENTRY.FBILLID \n");
            sql.append("  INNER JOIN T_GL_VOUCHERASSISTRECORD VOUCHERDETAIL ON VOUCHER.FID = VOUCHERDETAIL.FBILLID AND VOUCHERDETAIL.FENTRYID = VOUCHERENTRY.FID \n");
        }
        sql.append("  INNER JOIN T_BD_PERIOD PERIOD ON VOUCHER.FPERIODID = PERIOD.FID \n");
        sql.append("  INNER JOIN T_BD_VOUCHERTYPES VOUCHERTYPE ON VOUCHER.FVOUCHERTYPEID = VOUCHERTYPE.FID \n");
        sql.append("       ${PZJOINSQL}");
        sql.append("  INNER JOIN (${EXTERNAL_ORG_SQL}) COMPANY ON COMPANY.ID = VOUCHER.FCOMPANYID \n");
        sql.append("  INNER JOIN (${EXTERNAL_SUBJECT_SQL}) SUBJECT \n");
        sql.append("     ON SUBJECT.ID  = ${VOUCHERDETAIL}.FACCOUNTID \n");
        sql.append("    AND COMPANY.FACCOUNTTABLEID = SUBJECT.FACCOUNTTABLEID  \n");
        sql.append("    AND SUBJECT.FCOMPANYID = COMPANY.ID  \n");
        sql.append("  WHERE 1=1 \n");
        sql.append("    AND VOUCHER.FBIZSTATUS<>2 AND VOUCHER.FBIZSTATUS<>0  \n");
        sql.append("       ${PZTYPE} \n");
        sql.append("       ${UNITCONDITION}  \n");
        sql.append("   AND PERIOD.FPERIODYEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("   AND PERIOD.FPERIODNUMBER<=${ENDPERIOD} \n");
        sql.append("   AND PERIOD.FPERIODNUMBER>=${STARTPERIOD} \n");
        sql.append(this.buildSubjectCondi("SUBJECT", "CODE", condi.getSubjectCode())).append(" \n");
        sql.append("   ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" ORDER BY ROWTYPE DESC, ACCTYEAR, ACCTPERIOD, ACCTDAY, VCHRTYPE, ${PZFLORDERID} ORDERID  \n");
        sql.append("   ${ORDERASSFIELD} \n");
        StringBuilder yeAssField = new StringBuilder();
        StringBuilder yeGroupField = new StringBuilder();
        StringBuilder orderAssField = new StringBuilder();
        StringBuilder yeJoinSql = new StringBuilder();
        StringBuilder pzJoinSql = new StringBuilder();
        StringBuilder externalAssConfigSql = new StringBuilder();
        String yeType = "";
        String pzType = "";
        Variable variable = new Variable();
        if (!assistMappingList.isEmpty()) {
            yeJoinSql.append("  LEFT JOIN T_BD_ASSISTANTHG ASS ON T.FASSISTGRPID =ASS.FID \n");
            pzJoinSql.append("  LEFT JOIN T_BD_ASSISTANTHG ASS ON VOUCHERDETAIL.FASSGRPID =ASS.FID \n");
            variable.put("VOUCHERDETAIL", "VOUCHERENTRY");
            variable.put("PZFLORDERID", "PZFLORDERID,");
        } else {
            variable.put("VOUCHERDETAIL", "VOUCHERDETAIL");
            variable.put("PZFLORDERID", null);
        }
        for (AssistMappingBO assistMapping : assistMappingList) {
            yeAssField.append(String.format("%1$s.CODE AS %1$s, %1$s.NAME AS %1$s_NAME, ", assistMapping.getAssistCode()));
            yeJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = ASS.%3$s \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            pzJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = ASS.%3$s \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            yeGroupField.append(String.format(", %1$s.CODE, %1$s.NAME", assistMapping.getAssistCode()));
            orderAssField.append(String.format(", %1$s", assistMapping.getAssistCode()));
            externalAssConfigSql.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), "EQ"));
        }
        if (condi.getIncludeUncharged().booleanValue()) {
            yeType = " AND T.FBALTYPE = 1 ";
        } else {
            yeType = " AND T.FBALTYPE = 5 ";
            pzType = " AND VOUCHER.FBIZSTATUS = 5 ";
        }
        variable.put("YEASSFIELD", yeAssField.toString());
        variable.put("YEJOINSQL", yeJoinSql.toString());
        variable.put("PZJOINSQL", pzJoinSql.toString());
        variable.put("YEGROUPFIELD", yeGroupField.toString());
        variable.put("ORDERASSFIELD", orderAssField.toString());
        variable.put("STARTPERIOD", String.format("%02d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%02d", condi.getEndPeriod()));
        variable.put("TABLENAME", Eas8FetchUtil.getBalanceTableName(!assistMappingList.isEmpty()));
        variable.put("UNITCONDITION", OrgSqlUtil.getOrgConditionSql((String)"COMPANY.CODE", (List)unitAndBookValue.getUnitCodes()));
        variable.put("EXTERNAL_ORG_SQL", schemeMappingProvider.getOrgSql());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("EXTERNAL_CURRENCY_SQL", schemeMappingProvider.getCurrencySql());
        variable.put("EXTERNAL_ASS_CONFIG_SQL", externalAssConfigSql.toString());
        variable.put("SUBJECTCODE", condi.getSubjectCode());
        variable.put("YETYPE", yeType);
        variable.put("PZTYPE", pzType);
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new DetailLedgerResultSetExtractor(assistMappingList));
        return queryParam;
    }
}

