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
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.k3_cloud.penetrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.k3_cloud.BdeK3CloudPluginType;
import com.jiuqi.bde.plugin.k3_cloud.util.AssistPojo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractK3CloudBalanceContentProvider
extends AbstractBalanceContentProvider {
    @Autowired
    private BdeK3CloudPluginType k3CloudPluginType;

    public String getPluginType() {
        return this.k3CloudPluginType.getSymbol();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder sql = condi.getIncludeUncharged() != false ? this.getIncludeUnchargedSqlTmpl(condi, args) : this.getChargedSqlTmpl(condi, args);
        StringBuilder yeAssField = new StringBuilder();
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
            yeAssJoinSql.append(String.format("INNER JOIN (%1$s) %2$s ON %2$s.ID = FLEXVALUE.%3$s %4$s \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField(), this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule())));
            pzAssJoinSql.append(String.format("INNER JOIN (%1$s) %2$s ON %2$s.ID = FLEXVALUE.%3$s %4$s \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField(), this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule())));
            yeGroupField.append(String.format(", %1$s.CODE, %1$s.NAME", assistMapping.getAssistCode()));
            yeOrderField.append(String.format(", %1$s", assistMapping.getAssistCode()));
            masterAssField.append(String.format(", MASTER.%1$s, MASTER.%1$s_NAME", assistMapping.getAssistCode()));
            masterOrderAssField.append(String.format(", MASTER.%1$s", assistMapping.getAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("YEASSFIELD", yeAssField.toString());
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
        QueryParam queryParam = new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new BalanceResultSetExtractor(assistMappingList));
        return queryParam;
    }

    private StringBuilder getChargedSqlTmpl(PenetrateBaseDTO condi, List<Object> args) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT SUBJECT.FNUMBER AS SUBJECTCODE, \n");
        sql.append("        SUBJECT_L.FNAME AS SUBJECTNAME, \n");
        sql.append("        SUM(CASE WHEN B.FPERIOD=1 THEN B.FBEGINBALANCE ELSE 0 END) AS NC, \n");
        sql.append("        SUM(CASE WHEN B.FPERIOD=1 THEN B.FBEGINBALANCEFOR ELSE 0 END) AS ORGNNC, \n");
        sql.append("        SUM(CASE WHEN B.FPERIOD=${STARTPERIOD} THEN B.FBEGINBALANCE ELSE 0 END) AS QC, \n");
        sql.append("        SUM(CASE WHEN B.FPERIOD=${STARTPERIOD} THEN B.FBEGINBALANCEFOR ELSE 0 END) AS ORGNQC, \n");
        sql.append("        SUM(CASE WHEN B.FPERIOD>=${STARTPERIOD} AND B.FPERIOD<=${ENDPERIOD} THEN B.FDEBIT ELSE 0 END) AS DEBIT, \n");
        sql.append("        SUM(CASE WHEN B.FPERIOD>=${STARTPERIOD} AND B.FPERIOD<=${ENDPERIOD} THEN B.FDEBITFOR ELSE 0 END) AS ORGND, \n");
        sql.append("        SUM(CASE WHEN B.FPERIOD>=${STARTPERIOD} AND B.FPERIOD<=${ENDPERIOD} THEN B.FCREDIT ELSE 0 END) AS CREDIT, \n");
        sql.append("        SUM(CASE WHEN B.FPERIOD>=${STARTPERIOD} AND B.FPERIOD<=${ENDPERIOD} THEN B.FCREDITFOR ELSE 0 END) AS ORGNC, \n");
        sql.append("        SUM(CASE WHEN B.FPERIOD=${ENDPERIOD} THEN B.FYTDDEBIT ELSE 0 END) AS DSUM, \n");
        sql.append("        SUM(CASE WHEN B.FPERIOD=${ENDPERIOD} THEN B.FYTDDEBITFOR ELSE 0 END) AS ORGNDSUM, \n");
        sql.append("        SUM(CASE WHEN B.FPERIOD=${ENDPERIOD} THEN B.FYTDCREDIT ELSE 0 END) AS CSUM, \n");
        sql.append("        SUM(CASE WHEN B.FPERIOD=${ENDPERIOD} THEN B.FYTDCREDITFOR ELSE 0 END) AS ORGNCSUM, \n");
        sql.append("        SUM(CASE WHEN B.FPERIOD=${ENDPERIOD} THEN B.FENDBALANCE ELSE 0 END) AS YE, \n");
        sql.append("        SUM(CASE WHEN B.FPERIOD=${ENDPERIOD} THEN B.FENDBALANCEFOR ELSE 0 END) AS ORGNYE \n");
        sql.append("        ${YEASSFIELD} \n");
        sql.append("   FROM T_GL_BALANCE B  \n");
        sql.append("  INNER JOIN T_BD_ACCOUNT SUBJECT ON B.FACCOUNTID = SUBJECT.FACCTID \n");
        sql.append("  INNER JOIN T_BD_ACCOUNT_L SUBJECT_L ON SUBJECT.FACCTID = SUBJECT_L.FACCTID \n");
        sql.append("    AND SUBJECT_L.FLOCALEID = 2052 \n");
        sql.append("  INNER JOIN T_BD_CURRENCY CURRENCY ON B.FCURRENCYID =CURRENCY.FCURRENCYID \n");
        sql.append("  ${YEASSJOINSQL} ");
        sql.append("  WHERE 1 = 1 \n");
        sql.append("    ${BALANCEFDETAILIDCONDI}  \n");
        sql.append("    AND B.FACCOUNTBOOKID = ? \n");
        args.add(condi.getOrgMapping().getAcctOrgCode());
        sql.append("    AND B.FYEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("    AND SUBJECT.FISDETAIL = '1' \n");
        sql.append(this.buildSubjectCondi("SUBJECT", "FNUMBER", condi.getSubjectCode())).append(" \n");
        sql.append(this.buildExcludeCondi("SUBJECT", "FNUMBER", condi.getExcludeSubjectCode())).append(" \n");
        sql.append("  GROUP BY SUBJECT.FNUMBER, SUBJECT_L.FNAME \n");
        sql.append("        ${YEGROUPFIELD} \n");
        sql.append("  ORDER BY SUBJECT.FNUMBER, SUBJECT_L.FNAME \n");
        sql.append("        ${YEORDERFIELD} \n");
        return sql;
    }

    private StringBuilder getIncludeUnchargedSqlTmpl(PenetrateBaseDTO condi, List<Object> args) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT MASTER.SUBJECTCODE AS SUBJECTCODE,   \n");
        sql.append("        MASTER.SUBJECTNAME AS SUBJECTNAME, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD=0 THEN MASTER.DEBIT-MASTER.CREDIT ELSE 0 END) AS NC,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD=0 THEN MASTER.ORGND-MASTER.ORGNC ELSE 0 END) AS ORGNNC,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<${STARTPERIOD} THEN MASTER.DEBIT-MASTER.CREDIT ELSE 0 END) AS QC,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<${STARTPERIOD} THEN MASTER.ORGND-MASTER.ORGNC ELSE 0 END) AS ORGNQC,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.DEBIT ELSE 0 END) AS DEBIT,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.ORGND ELSE 0 END) AS ORGND,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.CREDIT ELSE 0 END) AS CREDIT,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.ORGNC ELSE 0 END) AS ORGNC,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>0 AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.DEBIT ELSE 0 END) AS DSUM,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>0 AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.ORGND ELSE 0 END) AS ORGNDSUM,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>0 AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.CREDIT ELSE 0 END) AS CSUM,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>0 AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.ORGNC ELSE 0 END) AS ORGNCSUM,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.DEBIT-MASTER.CREDIT  ELSE 0 END) AS YE,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.ORGND-MASTER.ORGNC  ELSE 0 END) AS ORGNYE  \n");
        sql.append("        ${MASTERASSFIELD} \n");
        sql.append("  FROM  \n");
        sql.append("        (SELECT SUBJECT.FNUMBER AS SUBJECTCODE,  \n");
        sql.append("                SUBJECT_L.FNAME AS SUBJECTNAME,  \n");
        sql.append("                0 AS PERIOD,  \n");
        sql.append("                CASE WHEN SUBJECT.FDC = 1 THEN B.FBEGINBALANCE ELSE 0 END AS DEBIT,  \n");
        sql.append("                CASE WHEN SUBJECT.FDC = -1 THEN B.FBEGINBALANCE*-1 ELSE 0 END AS CREDIT,  \n");
        sql.append("                CASE WHEN SUBJECT.FDC = 1 THEN B.FBEGINBALANCEFOR ELSE 0 END AS ORGND,  \n");
        sql.append("                CASE WHEN SUBJECT.FDC = -1 THEN B.FBEGINBALANCEFOR*-1 ELSE 0 END AS ORGNC  \n");
        sql.append("                ${YEASSFIELD} \n");
        sql.append("           FROM T_GL_BALANCE B  \n");
        sql.append("          INNER JOIN T_BD_ACCOUNT SUBJECT ON B.FACCOUNTID = SUBJECT.FACCTID \n");
        sql.append("          INNER JOIN T_BD_ACCOUNT_L SUBJECT_L ON SUBJECT.FACCTID = SUBJECT_L.FACCTID \n");
        sql.append("            AND SUBJECT_L.FLOCALEID = 2052 \n");
        sql.append("          INNER JOIN T_BD_CURRENCY CURRENCY ON B.FCURRENCYID = CURRENCY.FCURRENCYID \n");
        sql.append("          ${YEASSJOINSQL} ");
        sql.append("          WHERE 1 = 1  \n");
        sql.append("            ${BALANCEFDETAILIDCONDI}  \n");
        sql.append("            AND B.FACCOUNTBOOKID = ? \n");
        args.add(condi.getOrgMapping().getAcctOrgCode());
        sql.append("            AND B.FYEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("            AND B.FPERIOD = 1  \n");
        sql.append("            AND SUBJECT.FISDETAIL = '1' \n");
        sql.append(this.buildSubjectCondi("SUBJECT", "FNUMBER", condi.getSubjectCode())).append(" \n");
        sql.append(this.buildExcludeCondi("SUBJECT", "FNUMBER", condi.getExcludeSubjectCode())).append(" \n");
        sql.append("          UNION ALL  \n");
        sql.append("         SELECT SUBJECT.FNUMBER AS SUBJECTCODE,  \n");
        sql.append("                SUBJECT_L.FNAME AS SUBJECTNAME,  \n");
        sql.append("                VOUCHER.FPERIOD AS PERIOD,  \n");
        sql.append("                CASE WHEN VOUCHERENTRY.FDC = 1 THEN VOUCHERENTRY.FAMOUNT ELSE 0 END AS DEBIT,  \n");
        sql.append("                CASE WHEN VOUCHERENTRY.FDC = -1 THEN VOUCHERENTRY.FAMOUNT ELSE 0 END AS CREDIT,  \n");
        sql.append("                CASE WHEN VOUCHERENTRY.FDC = 1 THEN VOUCHERENTRY.FAMOUNTFOR ELSE 0 END AS ORGND,  \n");
        sql.append("                CASE WHEN VOUCHERENTRY.FDC = -1 THEN VOUCHERENTRY.FAMOUNTFOR ELSE 0 END AS ORGNC  \n");
        sql.append("                ${YEASSFIELD} \n");
        sql.append("           FROM T_GL_VOUCHER VOUCHER  \n");
        sql.append("          INNER JOIN T_GL_VOUCHERENTRY VOUCHERENTRY ON VOUCHERENTRY.FVOUCHERID = VOUCHER.FVOUCHERID \n");
        sql.append("          INNER JOIN T_BD_ACCOUNT SUBJECT ON VOUCHERENTRY.FACCOUNTID = SUBJECT.FACCTID \n");
        sql.append("          INNER JOIN T_BD_ACCOUNT_L SUBJECT_L ON SUBJECT.FACCTID = SUBJECT_L.FACCTID \n");
        sql.append("            AND SUBJECT_L.FLOCALEID = 2052 \n");
        sql.append("          INNER JOIN T_BD_CURRENCY CURRENCY ON VOUCHERENTRY.FCURRENCYID = CURRENCY.FCURRENCYID \n");
        sql.append("          ${PZASSJOINSQL} ");
        sql.append("          WHERE 1 = 1  \n");
        sql.append("            AND VOUCHER.FACCOUNTBOOKID = ? \n");
        args.add(condi.getOrgMapping().getAcctOrgCode());
        sql.append("            AND VOUCHER.FYEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("            AND VOUCHER.FPERIOD <= ${ENDPERIOD}  \n");
        sql.append("            AND VOUCHER.FINVALID = '0'  \n");
        sql.append("            AND SUBJECT.FISDETAIL = '1'  \n");
        sql.append(this.buildSubjectCondi("SUBJECT", "FNUMBER", condi.getSubjectCode())).append(" \n");
        sql.append(this.buildExcludeCondi("SUBJECT", "FNUMBER", condi.getExcludeSubjectCode())).append(" \n");
        sql.append("            ) MASTER \n");
        sql.append(" GROUP BY MASTER.SUBJECTCODE, MASTER.SUBJECTNAME \n");
        sql.append("       ${MASTERASSFIELD} \n");
        sql.append(" ORDER BY MASTER.SUBJECTCODE \n");
        sql.append("       ${MASTERORDERASSFIELD} \n");
        return sql;
    }
}

