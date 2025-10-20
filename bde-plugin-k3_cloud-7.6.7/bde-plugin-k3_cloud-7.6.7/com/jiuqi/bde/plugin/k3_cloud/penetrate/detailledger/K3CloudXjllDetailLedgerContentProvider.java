/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.XjllDetailLedgerResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.k3_cloud.penetrate.detailledger;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.XjllDetailLedgerResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.k3_cloud.BdeK3CloudPluginType;
import com.jiuqi.bde.plugin.k3_cloud.util.AssistPojo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class K3CloudXjllDetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeK3CloudPluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isNotEmpty((String)condi.getCashCode(), (String)"\u73b0\u6d41\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        String sql = this.getDetailLedgerSql(condi);
        StringBuilder yeAssField = new StringBuilder();
        StringBuilder yePzAssField = new StringBuilder();
        StringBuilder pzAssJoinSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            if (pzAssJoinSql.length() == 0) {
                pzAssJoinSql.append(" INNER JOIN T_BD_FLEXITEMDETAILV FLEXVALUE ON VOUCHERENTRY.FDETAILID =FLEXVALUE.FID  \n");
            }
            yeAssField.append(String.format(", %1$s.CODE AS %1$s, %1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            yePzAssField.append(String.format(", T.%1$s AS %1$s, T.%1$s_NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            pzAssJoinSql.append(String.format("INNER JOIN (%1$s) %2$s ON %2$s.ID = FLEXVALUE.%3$s %4$s \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField(), this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), "EQ")));
        }
        Variable variable = new Variable();
        variable.put("INCLUDESQL", condi.getIncludeUncharged() != false ? "" : " AND VOUCHER.FPOSTED ='1' ");
        variable.put("YEASSFIELD", yeAssField.toString());
        variable.put("YEPZASSFIELD", yePzAssField.toString());
        variable.put("PZASSJOINSQL", pzAssJoinSql.toString());
        variable.put("STARTPERIOD", condi.getStartPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        String querySql = VariableParseUtil.parse((String)sql, (Map)variable.getVariableMap());
        return new QueryParam(querySql, new Object[]{condi.getOrgMapping().getAcctOrgCode(), condi.getAcctYear()}, (ResultSetExtractor)new XjllDetailLedgerResultSetExtractor(condi, assistMappingList));
    }

    private String getDetailLedgerSql(PenetrateBaseDTO condi) {
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       VOUCHERENTRY.FENTRYID AS ID,  \n");
        sql.append("       VOUCHERENTRY.FVOUCHERID AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append("       VOUCHER.FPERIOD AS ACCTPERIOD,  \n");
        sql.append(String.format("       %s AS ACCTDAY,  \n", sqlHandler.day("VOUCHER.FDATE")));
        sql.append(sqlHandler.concatBySeparator("-", new String[]{"VOUCHERGROUP_L.FNAME", String.format("%s", sqlHandler.toChar("VOUCHER.FVOUCHERGROUPNO"))})).append(" AS VCHRTYPE, \n");
        sql.append("       VOUCHERENTRY.FENTRYSEQ AS ORDERID,  \n");
        sql.append("       SUBJECT.FDC AS ORIENT,  \n");
        if (StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append("       SUBJECT.FNUMBER AS MD_ACCTSUBJECT,  \n");
            sql.append("       SUBJECT_L.FNAME AS MD_ACCTSUBJECT_NAME,   \n");
        }
        sql.append("       CASHITEM.FNUMBER AS MD_CFITEM,\n");
        sql.append("       CASHITEM_L.FNAME AS MD_CFITEM_NAME,\n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("VOUCHERENTRY.FEXPLANATION")));
        sql.append("       CASE WHEN VOUCHERENTRY.FDC = 1 THEN CASH.FAMOUNT ELSE 0 END AS DEBIT,  \n");
        sql.append("       CASE WHEN VOUCHERENTRY.FDC = -1 THEN CASH.FAMOUNTFOR ELSE 0 END AS CREDIT,  \n");
        sql.append("       CASE WHEN VOUCHERENTRY.FDC = 1 THEN CASH.FAMOUNT ELSE 0 END AS ORGND,  \n");
        sql.append("       CASE WHEN VOUCHERENTRY.FDC = -1 THEN CASH.FAMOUNTFOR ELSE 0 END AS ORGNC,  \n");
        sql.append("       0 AS YE,  \n");
        sql.append("       0 AS ORGNYE  \n");
        sql.append("       ${YEASSFIELD} \n");
        sql.append("  FROM\n");
        sql.append("          T_GL_CASHFLOWBAL CASH\n");
        sql.append("  INNER JOIN T_GL_CASHFLOW CASHITEM ON\n");
        sql.append("          CASHITEM.FID = CASH.FITEMID\n");
        sql.append("  INNER JOIN T_GL_CASHFLOW_L CASHITEM_L ON\n");
        sql.append("          CASHITEM_L.FID = CASHITEM.FID AND CASHITEM_L.FLOCALEID = 2052\n");
        sql.append("  INNER JOIN T_GL_VOUCHER VOUCHER ON\n");
        sql.append("          CASH.FVOUCHERID = VOUCHER.FVOUCHERID\n");
        sql.append("  INNER JOIN T_GL_VOUCHERENTRY VOUCHERENTRY ON\n");
        sql.append("          CASH.FVCHOPPOENTRYID = VOUCHERENTRY.FENTRYID  \n");
        sql.append(" INNER JOIN T_BD_ACCOUNT SUBJECT ON VOUCHERENTRY.FACCOUNTID = SUBJECT.FACCTID \n");
        sql.append(" INNER JOIN T_BD_ACCOUNT_L SUBJECT_L ON SUBJECT.FACCTID = SUBJECT_L.FACCTID \n");
        sql.append("   AND SUBJECT_L.FLOCALEID = 2052 \n");
        sql.append(" INNER JOIN T_BD_VOUCHERGROUP_L VOUCHERGROUP_L ON VOUCHER.FVOUCHERGROUPID = VOUCHERGROUP_L.FVCHGROUPID \n");
        sql.append("   AND VOUCHERGROUP_L.FLOCALEID = 2052 \n");
        sql.append(" ${PZASSJOINSQL}");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND VOUCHER.FACCOUNTBOOKID = ? \n");
        sql.append("   AND VOUCHER.FYEAR = ? \n");
        sql.append("   AND VOUCHER.FINVALID = '0'  \n");
        sql.append("   AND VOUCHER.FPOSTED = '1'  \n");
        sql.append("   AND SUBJECT.FISDETAIL = '1'  \n");
        sql.append("   AND VOUCHER.FISCASHFLOW='1'\n");
        sql.append("   AND VOUCHER.FPERIOD<=${ENDPERIOD} \n");
        sql.append("   AND VOUCHER.FPERIOD>=${STARTPERIOD} \n");
        sql.append("   ${INCLUDESQL}\n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(this.buildSubjectCondi("SUBJECT", "FNUMBER", condi.getSubjectCode())).append(" \n");
        }
        sql.append(this.buildSubjectCondi("CASHITEM", "FNUMBER", condi.getCashCode()));
        sql.append(" ORDER BY ROWTYPE DESC, ACCTYEAR, ACCTPERIOD, ACCTDAY, VCHRTYPE, ORDERID");
        return sql.toString();
    }
}

