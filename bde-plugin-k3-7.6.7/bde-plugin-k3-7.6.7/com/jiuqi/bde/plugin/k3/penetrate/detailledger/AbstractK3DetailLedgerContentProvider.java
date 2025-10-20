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
package com.jiuqi.bde.plugin.k3.penetrate.detailledger;

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
import com.jiuqi.bde.plugin.k3.BdeK3PluginType;
import com.jiuqi.bde.plugin.k3.util.AssistPojo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractK3DetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeK3PluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder yeAssJoinSql = new StringBuilder();
        StringBuilder pzAssJoinSql = new StringBuilder();
        StringBuilder inAssFieldGroup = new StringBuilder();
        StringBuilder inAssField = new StringBuilder();
        StringBuilder assistCondition = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            if (yeAssJoinSql.length() == 0) {
                yeAssJoinSql.append(" INNER JOIN T_ITEMDETAIL FLEXVALUE ON B.FDETAILID =FLEXVALUE.FID  \n");
                pzAssJoinSql.append(" INNER JOIN T_ITEMDETAIL FLEXVALUE ON VOUCHERENTRY.FDETAILID =FLEXVALUE.FID  \n");
            }
            yeAssJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=FLEXVALUE.F%3$s ", assistMapping.getAssistSql(), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssId()));
            pzAssJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=FLEXVALUE.F%3$s ", assistMapping.getAssistSql(), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssId()));
            inAssField.append(String.format(",%1$s.CODE AS %1$s,%1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            inAssFieldGroup.append(String.format(",%1$s.CODE,%1$s.NAME", assistMapping.getAssistCode()));
            assistCondition.append(this.matchByRule(assistMapping.getExecuteDim().getDimCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), "EQ"));
        }
        String sql = this.getQuerySql(condi);
        Variable variable = new Variable();
        variable.put("STARTPERIOD", condi.getEndPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("BALANCEFDETAILIDCONDI", CollectionUtils.isEmpty((Collection)assistMappingList) ? "AND B.FDetailID = 0" : "AND B.FDetailID > 0 \n");
        variable.put("YEASSJOINSQL", yeAssJoinSql.toString());
        variable.put("ASSCONDITION", assistCondition.toString());
        variable.put("PZASSJOINSQL", pzAssJoinSql.toString());
        variable.put("INASSFIELDGROUP", inAssFieldGroup.toString());
        variable.put("INASSFIELD", inAssField.toString());
        String lastSql = VariableParseUtil.parse((String)sql, (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(lastSql, new Object[0], (ResultSetExtractor)new DetailLedgerResultSetExtractor(assistMappingList));
        return queryParam;
    }

    private String getQuerySql(PenetrateBaseDTO condi) {
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT 2 AS ROWTYPE,  \n");
        sql.append("           NULL AS ID,  \n");
        sql.append("           NULL AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append(String.format("       %d as ACCTPERIOD,  \n", condi.getStartPeriod()));
        sql.append("           NULL AS ACCTDAY,  \n");
        sql.append("           NULL AS VCHRTYPE,  \n");
        sql.append("           NULL AS ORDERID,  \n");
        sql.append("           1 AS ORIENT,  \n");
        sql.append("           SUBJECT.FNUMBER AS SUBJECTCODE,  \n");
        sql.append("           SUBJECT.FNAME AS SUBJECTNAME,  \n");
        sql.append("           '\u671f\u521d\u4f59\u989d' AS DIGEST,  \n");
        sql.append("           0 AS DEBIT,  \n");
        sql.append("           0 AS CREDIT,  \n");
        sql.append("           0 AS ORGND,  \n");
        sql.append("           0 AS ORGNC,  \n");
        sql.append("           SUM(B.FBEGINBALANCE*SUBJECT.FDC) AS YE,\n");
        sql.append("           SUM(B.FBEGINBALANCEFOR*SUBJECT.FDC) AS ORGNYE,\n");
        sql.append("           ${INASSFIELD}");
        sql.append("      FROM T_BALANCE B  \n");
        sql.append("     INNER JOIN T_ACCOUNT SUBJECT  \n");
        sql.append("        ON B.FACCOUNTID = SUBJECT.FACCTID  \n");
        sql.append("     INNER JOIN T_CURRENCY CURRENCY  \n");
        sql.append("        ON B.FCURRENCYID = CURRENCY.FCURRENCYID  \n");
        sql.append("     INNER JOIN T_GR_FRAMEWORK ORG    \n");
        sql.append("        ON B.FFRAMEWORKID = ORG.FFRAMEWORKID  \n");
        sql.append("     ${YEASSJOINSQL} \n");
        sql.append("     WHERE 1 = 1  \n");
        sql.append("       ${BALANCEFDETAILIDCONDI}  \n");
        sql.append("       AND ORG.FNUMBER='${UNITCODE}'\n");
        sql.append("       AND SUBJECT.FDETAIL = 1  \n");
        sql.append("       AND B.FYEAR = ${YEAR}  \n");
        sql.append("       AND B.FPERIOD = 1  \n");
        sql.append("     GROUP BY SUBJECT.FNUMBER,SUBJECT.FNAME  \n");
        sql.append("           ${INASSFIELDGROUP}  \n");
        sql.append("    UNION ALL ");
        sql.append("    SELECT 0 AS ROWTYPE,  \n");
        sql.append("           VOUCHERENTRY.FENTRYID AS ID,  \n");
        sql.append("           VOUCHER.FVOUCHERID AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append("           VOUCHER.FPERIOD AS ACCTPERIOD,  \n");
        sql.append(String.format("       %s AS ACCTDAY,  \n", sqlHandler.day("VOUCHER.FDATE")));
        sql.append(sqlHandler.concatBySeparator("-", new String[]{"VOUCHERGROUP.FNAME", sqlHandler.toChar("VOUCHERGROUP.FBRNO")})).append(" AS VCHRTYPE,\n");
        sql.append("           VOUCHERENTRY.FENTRYID AS ORDERID, \n");
        sql.append("           SUBJECT.FDC AS ORIENT, \n");
        sql.append("           SUBJECT.FNUMBER AS SUBJECTCODE, \n");
        sql.append("           SUBJECT.FNAME AS SUBJECTNAME, \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("VOUCHERENTRY.FEXPLANATION")));
        sql.append("           CASE WHEN VOUCHERENTRY.FDC = 1 THEN VOUCHERENTRY.FAMOUNT ELSE 0 END AS DEBIT,  \n");
        sql.append("           CASE WHEN VOUCHERENTRY.FDC = -1 THEN VOUCHERENTRY.FAMOUNT ELSE 0 END AS CREDIT,  \n");
        sql.append("           CASE WHEN VOUCHERENTRY.FDC = 1 THEN VOUCHERENTRY.FAMOUNTFOR ELSE 0 END AS ORGND,  \n");
        sql.append("           CASE WHEN VOUCHERENTRY.FDC = -1 THEN VOUCHERENTRY.FAMOUNTFOR ELSE 0 END AS ORGNC,  \n");
        sql.append("           0 AS YE, \n");
        sql.append("           0 AS ORGNYE, \n");
        sql.append("           ${INASSFIELD}");
        sql.append("      FROM T_GL_VOUCHER VOUCHER \n");
        sql.append("      INNER JOIN T_GL_VOUCHERENTRY VOUCHERENTRY ON VOUCHERENTRY.FVOUCHERID = VOUCHER.FVOUCHERID \n");
        sql.append("      INNER JOIN T_BD_ACCOUNT SUBJECT ON VOUCHERENTRY.FACCOUNTID = SUBJECT.FACCTID \n");
        sql.append("      INNER JOIN T_VOUCHERGROUP VOUCHERGROUP ON VOUCHERGROUP.FGROUPID=VOUCHER.FGROUPID \n");
        sql.append("      INNER JOIN T_GR_FRAMEWORK ORG ON VOUCHER.FFRAMEWORKID = ORG.FFRAMEWORKID \n");
        sql.append("  ${PZASSJOINSQL} \n");
        sql.append("  WHERE 1=1 \n");
        sql.append("    AND ORG.FNUMBER='${UNITCODE}'\n");
        if (!condi.getIncludeUncharged().booleanValue()) {
            sql.append("    AND VOUCHER.FPOSTED = '1'  \n");
        }
        sql.append("    AND VOUCHER.FYEAR = ${YEAR}\n");
        sql.append("    AND SUBJECT.FDETAIL = 1  \n");
        sql.append(this.buildSubjectCondi("SUBJECT", "FNUMBER", condi.getSubjectCode())).append(" \n");
        return sql.toString();
    }
}

