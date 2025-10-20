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
package com.jiuqi.bde.plugin.eas8.penetrate.detailledger;

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
import com.jiuqi.bde.plugin.eas8.Eas8PluginType;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Eas8XjllDetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private Eas8PluginType pluginType;

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
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       V.FID AS ID,  \n");
        sql.append("       V.FBILLID AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append("       PERIOD.FPERIODNUMBER AS ACCTPERIOD,  \n");
        sql.append(String.format("       %s AS ACCTDAY,  \n", sqlHandler.day("VOUCHER.FBIZDATE")));
        sql.append("       TYPE.FNUMBER  || '-' || VOUCHER.FNUMBER AS VCHRTYPE,  \n");
        sql.append("       V.FSEQ AS ORDERID,  \n");
        sql.append("       KM.FDC AS ORIENT,  \n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append("          KM.FNUMBER AS MD_ACCTSUBJECT,  \n");
            sql.append("          KM.FNAME_L1 AS MD_ACCTSUBJECT_NAME,\n");
        }
        sql.append("       CI.FNUMBER AS MD_CFITEM,  \n");
        sql.append("       CI.FNAME_L2 AS MD_CFITEM_NAME,\n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("VOUCHER.FABSTRACT")));
        sql.append("       CASE WHEN CR.FENTRYDC = 1 THEN CR.FORIGINALAMOUNT ELSE 0 END AS DEBIT, \n");
        sql.append("       CASE WHEN CR.FENTRYDC = 0 THEN CR.FORIGINALAMOUNT ELSE 0 END AS CREDIT, \n");
        sql.append("       CASE WHEN CR.FENTRYDC = 1 THEN CR.FORIGINALAMOUNT ELSE 0 END AS ORGND, \n");
        sql.append("       CASE WHEN CR.FENTRYDC = 0 THEN CR.FORIGINALAMOUNT ELSE 0 END AS ORGNC, \n");
        sql.append("       0 AS YE,  \n");
        sql.append("       0 AS ORGNYE  \n");
        sql.append("       ${ASSFIELD} \n");
        sql.append("  FROM T_GL_VOUCHER VOUCHER  \n");
        sql.append("  INNER JOIN T_GL_VOUCHERENTRY V  \n");
        sql.append("    ON VOUCHER.FID = V.FBillID\n");
        sql.append("  INNER JOIN T_BD_VOUCHERTYPES TYPE  \n");
        sql.append("    ON VOUCHER.FVOUCHERTYPEID = TYPE.FID\n");
        sql.append("  INNER JOIN T_BD_ACCOUNTVIEW KM  \n");
        sql.append("    ON KM.FID = V.FACCOUNTID  \n");
        sql.append("  LEFT JOIN T_BD_PERIOD PERIOD  \n");
        sql.append("    ON VOUCHER.FPERIODID = PERIOD.FID  \n");
        sql.append(" LEFT JOIN T_GL_CASHFLOWRECORD CR  \n");
        sql.append("    ON (V.FID = CR.FOPPOSINGACCOUNTENTRYID OR V.FID = CR.FSUPPLEMENTARYITEMID)  \n");
        sql.append("  LEFT JOIN T_BD_ASSISTANTHG ASS  \n");
        sql.append("    ON CR.FASSGRPID = ASS.FID  \n");
        sql.append(" LEFT JOIN T_BD_CASHFLOWITEM CI  \n");
        sql.append("    ON CR.FPRIMARYITEMID = CI.FID  \n");
        sql.append(" LEFT JOIN T_ORG_COMPANY ORG  \n");
        sql.append("    ON VOUCHER.FCOMPANYID = ORG.FID  \n");
        sql.append(" LEFT JOIN T_BD_CURRENCY HB  \n");
        sql.append("    ON V.FCURRENCYID = HB.FID  \n");
        sql.append("  ${ASSJOIN} \n");
        sql.append(" WHERE PERIOD.FPERIODYEAR = ?  \n");
        args.add(condi.getAcctYear());
        sql.append("   AND ORG.FNUMBER = ?  \n");
        args.add(condi.getOrgMapping().getAcctOrgCode());
        sql.append("   AND PERIOD.FPERIODNUMBER<='${ENDPERIOD}' \n");
        sql.append("   AND PERIOD.FPERIODNUMBER>='${STARTPERIOD}' \n");
        sql.append(this.buildCfItemCondi("CI", "FNUMBER", condi.getCashCode()));
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(this.buildSubjectCondi("KM", "FNUMBER", condi.getSubjectCode()));
        }
        sql.append("   ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append("   AND VOUCHER.FCASHFLOWFLAG <> 0  \n");
        sql.append("   AND VOUCHER.FBIZSTATUS <> 0  \n");
        sql.append("   AND VOUCHER.FBIZSTATUS <> 2  \n");
        sql.append("   ${INCLUDECON}\n");
        sql.append(" ORDER BY ROWTYPE DESC, ACCTYEAR, ACCTPERIOD, ACCTDAY, VCHRTYPE, ORDERID");
        StringBuilder assJoin = new StringBuilder();
        StringBuilder assField = new StringBuilder();
        StringBuilder externalAssConfigSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            assField.append(String.format(", %1$s.CODE AS %1$s, %1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            assJoin.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=ASS.%3$s", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            externalAssConfigSql.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), "EQ"));
        }
        Variable variable = new Variable();
        variable.put("ASSFIELD", assField.toString());
        variable.put("ASSJOIN", assJoin.toString());
        variable.put("STARTPERIOD", condi.getStartPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("EXTERNAL_ASS_CONFIG_SQL", externalAssConfigSql.toString());
        variable.put("INCLUDECON", condi.getIncludeUncharged() != false ? "" : "AND VOUCHER.FBizStatus = 5\n");
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        return new QueryParam(querySql, args.toArray(), (ResultSetExtractor)new XjllDetailLedgerResultSetExtractor(condi, assistMappingList));
    }
}

