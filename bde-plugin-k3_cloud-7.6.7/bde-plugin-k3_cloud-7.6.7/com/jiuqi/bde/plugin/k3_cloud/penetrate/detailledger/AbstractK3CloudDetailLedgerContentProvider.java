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
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.k3_cloud.penetrate.detailledger;

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
import com.jiuqi.bde.plugin.k3_cloud.BdeK3CloudPluginType;
import com.jiuqi.bde.plugin.k3_cloud.util.AssistPojo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractK3CloudDetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeK3CloudPluginType k3CloudPluginType;

    public String getPluginType() {
        return this.k3CloudPluginType.getSymbol();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder sql = condi.getIncludeUncharged() != false ? this.getIncludeUnchargedSqlTmpl(condi, args) : this.getChargedSqlTmpl(condi, args);
        StringBuilder yeAssField = new StringBuilder();
        StringBuilder yePzAssField = new StringBuilder();
        StringBuilder yeGroupField = new StringBuilder();
        StringBuilder yeOrderField = new StringBuilder();
        StringBuilder masterAssField = new StringBuilder();
        StringBuilder masterOrderAssField = new StringBuilder();
        StringBuilder yeAssJoinSql = new StringBuilder();
        StringBuilder pzAssJoinSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            if (yeAssJoinSql.length() == 0) {
                yeAssJoinSql.append(" INNER JOIN T_BD_FLEXITEMDETAILV FLEXVALUE ON B.FDETAILID =FLEXVALUE.FID  \n");
                pzAssJoinSql.append(" INNER JOIN T_BD_FLEXITEMDETAILV FLEXVALUE ON VOUCHERENTRY.FDETAILID =FLEXVALUE.FID  \n");
            }
            yeAssField.append(String.format(", %1$s.CODE AS %1$s, %1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            yePzAssField.append(String.format(", T.%1$s AS %1$s, T.%1$s_NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            yeAssJoinSql.append(String.format("INNER JOIN (%1$s) %2$s ON %2$s.ID = FLEXVALUE.%3$s %4$s \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField(), this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), "EQ")));
            pzAssJoinSql.append(String.format("INNER JOIN (%1$s) %2$s ON %2$s.ID = FLEXVALUE.%3$s %4$s \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField(), this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), "EQ")));
            yeGroupField.append(String.format(", %1$s.CODE, %1$s.NAME", assistMapping.getAssistCode()));
            yeOrderField.append(String.format(", %1$s", assistMapping.getAssistCode()));
            masterAssField.append(String.format("GROUP BY T.%1$s, T.%1$s_NAME", assistMapping.getAssistCode()));
            masterOrderAssField.append(String.format(", MASTER.%1$s", assistMapping.getAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("YEASSFIELD", yeAssField.toString());
        variable.put("YEPZASSFIELD", yePzAssField.toString());
        variable.put("YEASSJOINSQL", yeAssJoinSql.toString());
        variable.put("PZASSJOINSQL", pzAssJoinSql.toString());
        variable.put("YEGROUPFIELD", yeGroupField.toString());
        variable.put("YEORDERFIELD", yeOrderField.toString());
        variable.put("MASTERASSFIELD", masterAssField.toString());
        variable.put("MASTERORDERASSFIELD", masterOrderAssField.toString());
        variable.put("STARTPERIOD", condi.getStartPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("BALANCEFDETAILIDCONDI", CollectionUtils.isEmpty((Collection)assistMappingList) ? "AND B.FDETAILID = 0" : "AND B.FDETAILID > 0 ");
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new DetailLedgerResultSetExtractor(assistMappingList));
        return queryParam;
    }

    private StringBuilder getChargedSqlTmpl(PenetrateBaseDTO condi, List<Object> args) {
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 2 AS ROWTYPE,  \n");
        sql.append("       NULL AS ID,  \n");
        sql.append("       NULL AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append(String.format("       %d as ACCTPERIOD,  \n", condi.getStartPeriod()));
        sql.append("       NULL AS ACCTDAY,  \n");
        sql.append("       NULL AS VCHRTYPE,  \n");
        sql.append("       NULL AS ORDERID,  \n");
        sql.append("       1 AS ORIENT,  \n");
        sql.append("       NULL AS SUBJECTCODE,  \n");
        sql.append("       NULL AS SUBJECTNAME,   \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("       0 AS DEBIT,  \n");
        sql.append("       0 AS CREDIT,  \n");
        sql.append("       0 AS ORGND,  \n");
        sql.append("       0 AS ORGNC,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIOD=${STARTPERIOD} THEN B.FBEGINBALANCE ELSE 0 END) AS YE,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIOD=${STARTPERIOD} THEN B.FBEGINBALANCEFOR ELSE 0 END) AS ORGNYE  \n");
        sql.append("       ${YEASSFIELD} \n");
        sql.append("  FROM T_GL_BALANCE B  \n");
        sql.append(" INNER JOIN T_BD_ACCOUNT SUBJECT ON B.FACCOUNTID = SUBJECT.FACCTID \n");
        sql.append(" INNER JOIN T_BD_ACCOUNT_L SUBJECT_L ON SUBJECT.FACCTID = SUBJECT_L.FACCTID \n");
        sql.append("   AND SUBJECT_L.FLOCALEID = 2052 \n");
        sql.append(" INNER JOIN T_BD_CURRENCY CURRENCY ON B.FCURRENCYID = CURRENCY.FCURRENCYID \n");
        sql.append(" ${YEASSJOINSQL} ");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   ${BALANCEFDETAILIDCONDI}  \n");
        sql.append("   AND B.FACCOUNTBOOKID = ? \n");
        args.add(condi.getOrgMapping().getAcctOrgCode());
        sql.append("   AND B.FYEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("   AND SUBJECT.FISDETAIL = '1' \n");
        sql.append(this.buildSubjectCondi("SUBJECT", "FNUMBER", condi.getSubjectCode())).append(" \n");
        sql.append(" GROUP BY B.FYEAR \n");
        sql.append("       ${YEGROUPFIELD} \n");
        sql.append(" UNION ALL  \n");
        sql.append("SELECT T.* FROM (  \n");
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       VOUCHERENTRY.FENTRYID AS ID,  \n");
        sql.append("       VOUCHERENTRY.FVOUCHERID AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append("       VOUCHER.FPERIOD AS ACCTPERIOD,  \n");
        sql.append(String.format("       %s AS ACCTDAY,  \n", sqlHandler.day("VOUCHER.FDATE")));
        sql.append(sqlHandler.concatBySeparator("-", new String[]{"VOUCHERGROUP_L.FNAME", String.format("%s", sqlHandler.toChar("VOUCHER.FVOUCHERGROUPNO"))})).append(" AS VCHRTYPE, \n");
        sql.append("       VOUCHERENTRY.FENTRYSEQ AS ORDERID,  \n");
        sql.append("       SUBJECT.FDC AS ORIENT,  \n");
        sql.append("       SUBJECT.FNUMBER AS SUBJECTCODE,  \n");
        sql.append("       SUBJECT_L.FNAME AS SUBJECTNAME,   \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("VOUCHERENTRY.FEXPLANATION")));
        sql.append("       CASE WHEN VOUCHERENTRY.FDC = 1 THEN VOUCHERENTRY.FAMOUNT ELSE 0 END AS DEBIT,  \n");
        sql.append("       CASE WHEN VOUCHERENTRY.FDC = -1 THEN VOUCHERENTRY.FAMOUNT ELSE 0 END AS CREDIT,  \n");
        sql.append("       CASE WHEN VOUCHERENTRY.FDC = 1 THEN VOUCHERENTRY.FAMOUNTFOR ELSE 0 END AS ORGND,  \n");
        sql.append("       CASE WHEN VOUCHERENTRY.FDC = -1 THEN VOUCHERENTRY.FAMOUNTFOR ELSE 0 END AS ORGNC,  \n");
        sql.append("       0 AS YE,  \n");
        sql.append("       0 AS ORGNYE  \n");
        sql.append("       ${YEASSFIELD} \n");
        sql.append("  FROM T_GL_VOUCHER VOUCHER  \n");
        sql.append(" INNER JOIN T_GL_VOUCHERENTRY VOUCHERENTRY ON VOUCHERENTRY.FVOUCHERID = VOUCHER.FVOUCHERID \n");
        sql.append(" INNER JOIN T_BD_ACCOUNT SUBJECT ON VOUCHERENTRY.FACCOUNTID = SUBJECT.FACCTID \n");
        sql.append(" INNER JOIN T_BD_ACCOUNT_L SUBJECT_L ON SUBJECT.FACCTID = SUBJECT_L.FACCTID \n");
        sql.append("   AND SUBJECT_L.FLOCALEID = 2052 \n");
        sql.append(" INNER JOIN T_BD_VOUCHERGROUP_L VOUCHERGROUP_L ON VOUCHER.FVOUCHERGROUPID = VOUCHERGROUP_L.FVCHGROUPID \n");
        sql.append("   AND VOUCHERGROUP_L.FLOCALEID = 2052 \n");
        sql.append(" ${PZASSJOINSQL}");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND VOUCHER.FACCOUNTBOOKID = ? \n");
        args.add(condi.getOrgMapping().getAcctOrgCode());
        sql.append("   AND VOUCHER.FYEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("   AND VOUCHER.FINVALID = '0'  \n");
        sql.append("   AND VOUCHER.FPOSTED = '1'  \n");
        sql.append("   AND SUBJECT.FISDETAIL = '1'  \n");
        sql.append("   AND VOUCHER.FPERIOD<=${ENDPERIOD} \n");
        sql.append("   AND VOUCHER.FPERIOD>=${STARTPERIOD} \n");
        sql.append(this.buildSubjectCondi("SUBJECT", "FNUMBER", condi.getSubjectCode())).append(" \n");
        sql.append(" ) T ORDER BY ROWTYPE DESC, ACCTYEAR, ACCTPERIOD, ACCTDAY, VCHRTYPE, ORDERID \n");
        sql.append("   ${YEORDERFIELD} \n");
        return sql;
    }

    private StringBuilder getIncludeUnchargedSqlTmpl(PenetrateBaseDTO condi, List<Object> args) {
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 2 AS ROWTYPE,  \n");
        sql.append("       NULL AS ID,  \n");
        sql.append("       NULL AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append(String.format("       %d as ACCTPERIOD,  \n", condi.getStartPeriod()));
        sql.append("       NULL AS ACCTDAY,  \n");
        sql.append("       NULL AS VCHRTYPE,  \n");
        sql.append("       NULL AS ORDERID,  \n");
        sql.append("       1 AS ORIENT,  \n");
        sql.append("       NULL AS SUBJECTCODE,  \n");
        sql.append("       NULL AS SUBJECTNAME,   \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("       0 AS DEBIT,  \n");
        sql.append("       0 AS CREDIT,  \n");
        sql.append("       0 AS ORGND,  \n");
        sql.append("       0 ORGNC,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD<${STARTPERIOD} THEN T.DEBIT-T.CREDIT ELSE 0 END) AS YE,  \n");
        sql.append("       SUM(CASE WHEN T.PERIOD<${STARTPERIOD} THEN T.ORGND-T.ORGNC ELSE 0 END) AS ORGNYE  \n");
        sql.append("       ${YEPZASSFIELD} \n");
        sql.append("  FROM \n");
        sql.append("       (SELECT SUBJECT.FNUMBER AS SUBJECTCODE,  \n");
        sql.append("               SUBJECT_L.FNAME AS SUBJECTNAME,   \n");
        sql.append("               0 AS PERIOD,  \n");
        sql.append("               CASE WHEN SUBJECT.FDC = 1 THEN B.FBEGINBALANCE ELSE 0 END AS DEBIT,  \n");
        sql.append("               CASE WHEN SUBJECT.FDC = -1 THEN B.FBEGINBALANCE*-1 ELSE 0 END AS CREDIT,  \n");
        sql.append("               CASE WHEN SUBJECT.FDC = 1 THEN B.FBEGINBALANCEFOR ELSE 0 END AS ORGND,  \n");
        sql.append("               CASE WHEN SUBJECT.FDC = -1 THEN B.FBEGINBALANCEFOR*-1 ELSE 0 END AS ORGNC  \n");
        sql.append("               ${YEASSFIELD} \n");
        sql.append("          FROM T_GL_BALANCE B  \n");
        sql.append("         INNER JOIN T_BD_ACCOUNT SUBJECT ON B.FACCOUNTID = SUBJECT.FACCTID \n");
        sql.append("         INNER JOIN T_BD_ACCOUNT_L SUBJECT_L ON SUBJECT.FACCTID = SUBJECT_L.FACCTID \n");
        sql.append("           AND SUBJECT_L.FLOCALEID = 2052 \n");
        sql.append("         INNER JOIN T_BD_CURRENCY CURRENCY ON B.FCURRENCYID = CURRENCY.FCURRENCYID \n");
        sql.append("         ${YEASSJOINSQL} ");
        sql.append("         WHERE 1 = 1  \n");
        sql.append("           ${BALANCEFDETAILIDCONDI}  \n");
        sql.append("           AND B.FACCOUNTBOOKID = ? \n");
        args.add(condi.getOrgMapping().getAcctOrgCode());
        sql.append("           AND B.FYEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("           AND B.FPERIOD = 1  \n");
        sql.append("           AND SUBJECT.FISDETAIL = '1' \n");
        sql.append(this.buildSubjectCondi("SUBJECT", "FNUMBER", condi.getSubjectCode())).append(" \n");
        sql.append("         UNION ALL \n");
        sql.append("        SELECT SUBJECT.FNUMBER AS SUBJECTCODE,  \n");
        sql.append("               SUBJECT_L.FNAME AS SUBJECTNAME,   \n");
        sql.append("               VOUCHER.FPERIOD AS PERIOD,  \n");
        sql.append("               CASE WHEN VOUCHERENTRY.FDC = 1 THEN VOUCHERENTRY.FAMOUNT ELSE 0 END AS DEBIT,  \n");
        sql.append("               CASE WHEN VOUCHERENTRY.FDC = -1 THEN VOUCHERENTRY.FAMOUNT ELSE 0 END AS CREDIT,  \n");
        sql.append("               CASE WHEN VOUCHERENTRY.FDC = 1 THEN VOUCHERENTRY.FAMOUNTFOR ELSE 0 END AS ORGND,  \n");
        sql.append("               CASE WHEN VOUCHERENTRY.FDC = -1 THEN VOUCHERENTRY.FAMOUNTFOR ELSE 0 END AS ORGNC  \n");
        sql.append("               ${YEASSFIELD} \n");
        sql.append("          FROM T_GL_VOUCHER VOUCHER  \n");
        sql.append("         INNER JOIN T_GL_VOUCHERENTRY VOUCHERENTRY ON VOUCHERENTRY.FVOUCHERID = VOUCHER.FVOUCHERID \n");
        sql.append("         INNER JOIN T_BD_ACCOUNT SUBJECT ON VOUCHERENTRY.FACCOUNTID = SUBJECT.FACCTID \n");
        sql.append("         INNER JOIN T_BD_ACCOUNT_L SUBJECT_L ON SUBJECT.FACCTID = SUBJECT_L.FACCTID \n");
        sql.append("           AND SUBJECT_L.FLOCALEID = 2052 \n");
        sql.append("         INNER JOIN T_BD_CURRENCY CURRENCY ON VOUCHERENTRY.FCURRENCYID = CURRENCY.FCURRENCYID \n");
        sql.append("         ${PZASSJOINSQL} ");
        sql.append("         WHERE 1 = 1  \n");
        sql.append("           AND VOUCHER.FACCOUNTBOOKID = ? \n");
        args.add(condi.getOrgMapping().getAcctOrgCode());
        sql.append("           AND VOUCHER.FYEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("           AND VOUCHER.FPERIOD <= ${ENDPERIOD}  \n");
        sql.append("           AND VOUCHER.FINVALID = '0'  \n");
        sql.append("           AND SUBJECT.FISDETAIL = '1'  \n");
        sql.append(this.buildSubjectCondi("SUBJECT", "FNUMBER", condi.getSubjectCode())).append(" \n");
        sql.append("           ) T \n");
        sql.append("             ${MASTERASSFIELD} \n");
        sql.append(" UNION ALL  \n");
        sql.append("SELECT T.* FROM (  \n");
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       VOUCHERENTRY.FENTRYID AS ID,  \n");
        sql.append("       VOUCHERENTRY.FVOUCHERID AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append("       VOUCHER.FPERIOD AS ACCTPERIOD,  \n");
        sql.append(String.format("       %s AS ACCTDAY,  \n", sqlHandler.day("VOUCHER.FDATE")));
        sql.append(sqlHandler.concatBySeparator("-", new String[]{"VOUCHERGROUP_L.FNAME", String.format("%s", sqlHandler.toChar("VOUCHER.FVOUCHERGROUPNO"))})).append(" AS VCHRTYPE, \n");
        sql.append("       VOUCHERENTRY.FENTRYSEQ AS ORDERID,  \n");
        sql.append("       SUBJECT.FDC AS ORIENT,  \n");
        sql.append("       SUBJECT.FNUMBER AS SUBJECTCODE,  \n");
        sql.append("       SUBJECT_L.FNAME AS SUBJECTNAME,   \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("VOUCHERENTRY.FEXPLANATION")));
        sql.append("       CASE WHEN VOUCHERENTRY.FDC = 1 THEN VOUCHERENTRY.FAMOUNT ELSE 0 END AS DEBIT,  \n");
        sql.append("       CASE WHEN VOUCHERENTRY.FDC = -1 THEN VOUCHERENTRY.FAMOUNT ELSE 0 END AS CREDIT,  \n");
        sql.append("       CASE WHEN VOUCHERENTRY.FDC = 1 THEN VOUCHERENTRY.FAMOUNTFOR ELSE 0 END AS ORGND,  \n");
        sql.append("       CASE WHEN VOUCHERENTRY.FDC = -1 THEN VOUCHERENTRY.FAMOUNTFOR ELSE 0 END AS ORGNC,  \n");
        sql.append("       0 AS YE,  \n");
        sql.append("       0 AS ORGNYE  \n");
        sql.append("       ${YEASSFIELD} \n");
        sql.append("  FROM T_GL_VOUCHER VOUCHER  \n");
        sql.append(" INNER JOIN T_GL_VOUCHERENTRY VOUCHERENTRY ON VOUCHERENTRY.FVOUCHERID = VOUCHER.FVOUCHERID \n");
        sql.append(" INNER JOIN T_BD_ACCOUNT SUBJECT ON VOUCHERENTRY.FACCOUNTID = SUBJECT.FACCTID \n");
        sql.append(" INNER JOIN T_BD_ACCOUNT_L SUBJECT_L ON SUBJECT.FACCTID = SUBJECT_L.FACCTID \n");
        sql.append("   AND SUBJECT_L.FLOCALEID = 2052 \n");
        sql.append(" INNER JOIN T_BD_VOUCHERGROUP_L VOUCHERGROUP_L ON VOUCHER.FVOUCHERGROUPID = VOUCHERGROUP_L.FVCHGROUPID \n");
        sql.append("   AND VOUCHERGROUP_L.FLOCALEID = 2052 \n");
        sql.append(" ${PZASSJOINSQL}");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND VOUCHER.FACCOUNTBOOKID = ? \n");
        args.add(condi.getOrgMapping().getAcctOrgCode());
        sql.append("   AND VOUCHER.FYEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("   AND VOUCHER.FINVALID = '0'  \n");
        sql.append("   AND SUBJECT.FISDETAIL = '1'  \n");
        sql.append("   AND VOUCHER.FPERIOD<=${ENDPERIOD} \n");
        sql.append("   AND VOUCHER.FPERIOD>=${STARTPERIOD} \n");
        sql.append(this.buildSubjectCondi("SUBJECT", "FNUMBER", condi.getSubjectCode())).append(" \n");
        sql.append("   ) T ORDER BY ROWTYPE DESC, ACCTYEAR, ACCTPERIOD, ACCTDAY, VCHRTYPE, ORDERID \n");
        sql.append("   ${YEORDERFIELD} \n");
        return sql;
    }
}

