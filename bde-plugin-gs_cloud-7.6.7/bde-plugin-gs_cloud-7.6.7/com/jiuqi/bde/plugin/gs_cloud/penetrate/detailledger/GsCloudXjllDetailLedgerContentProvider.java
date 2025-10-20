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
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.gs_cloud.penetrate.detailledger;

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
import com.jiuqi.bde.plugin.gs_cloud.BdeGsCloudPluginType;
import com.jiuqi.bde.plugin.gs_cloud.util.AssistPojo;
import com.jiuqi.bde.plugin.gs_cloud.util.GsCloudTableEnum;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class GsCloudXjllDetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeGsCloudPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

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
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "GSCLOUD" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        IDbSqlHandler dbSqlHandler = this.getDbSqlHandler(this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       VOUCHERDETAIL.ID AS ID,  \n");
        sql.append("       VOUCHERDETAIL.ACCDOCID AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append("       VOUCHER.ACCPERIODCODE AS ACCTPERIOD,  \n");
        sql.append("       SUBSTR(VOUCHER.ACCDOCDATE,7,2) AS ACCTDAY,  \n");
        sql.append("       SUBSTR(VOUCHER.ACCDOCCODE,1,1)  || '-' || SUBSTR(VOUCHER.ACCDOCCODE,2) AS VCHRTYPE,  \n");
        if (assistMappingList.isEmpty()) {
            sql.append("       VOUCHERDETAIL.ACCENTRYCODE AS ORDERID,  \n");
        } else {
            sql.append("       VOUCHERDETAIL.ACCASSCODE AS ORDERID,  \n");
        }
        sql.append("       SUBJECT.ORIENT AS ORIENT,  \n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(String.format("       %s AS MD_ACCTSUBJECT,  \n", sqlHandler.toChar("SUBJECT.CODE")));
            sql.append("       SUBJECT.NAME AS MD_ACCTSUBJECT_NAME,  \n");
        }
        sql.append("       CASHFLOW.CODE AS MD_CFITEM,\n");
        sql.append("       CASHFLOW.NAME_CHS AS MD_CFITEM_NAME,\n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("VOUCHER.ABSTRACT")));
        sql.append("       CASE WHEN VOUCHERITEM.LENDINGDIRECTION = 1 THEN ${ZWXJJG_JE} ELSE 0 END AS DEBIT, \n");
        sql.append("       CASE WHEN VOUCHERITEM.LENDINGDIRECTION = 2 THEN ${ZWXJJG_JE} * -1 ELSE 0 END AS CREDIT, \n");
        sql.append("       CASE WHEN VOUCHERITEM.LENDINGDIRECTION = 1 THEN ${ZWXJJG_JE} ELSE 0 END AS ORGND, \n");
        sql.append("       CASE WHEN VOUCHERITEM.LENDINGDIRECTION = 2 THEN ${ZWXJJG_JE} * -1 ELSE 0 END AS ORGNC, \n");
        sql.append("       0 AS YE,  \n");
        sql.append("       0 AS ORGNYE  \n");
        sql.append("       ${YEASSFIELD} \n");
        sql.append("  FROM (SELECT *  \n");
        sql.append("          FROM FIGLACCOUNTINGDOCUMENT${YEAR} VOUCHER  \n");
        sql.append("         WHERE VOUCHER.${ORGID} = (SELECT ORG.ID  FROM (${EXTERNAL_ORG_SQL}) ORG  \n");
        sql.append("                                          WHERE 1 = 1   \n");
        if (orgMappingType.equals("BOOKCODE")) {
            sql.append("                                          AND ORG.BOOKCODE = ? \n");
            args.add(condi.getOrgMapping().getAcctBookCode());
        } else {
            sql.append("                                          AND ORG.CODE= ? \n");
            args.add(condi.getOrgMapping().getAcctOrgCode());
        }
        sql.append("                          )  \n");
        sql.append("         AND VOUCHER.YEAR = ?   \n");
        args.add(condi.getAcctYear());
        sql.append("         AND VOUCHER.ISCOMPLETE = '1'   \n");
        sql.append("         AND VOUCHER.ISVOID = '0'   \n");
        sql.append("         AND VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' \n");
        sql.append("         AND VOUCHER.ACCPERIODCODE>='${STARTPERIOD}' \n");
        sql.append("                            ) VOUCHER  \n");
        sql.append("       INNER JOIN FIGLACCDOCENTRY${YEAR} VOUCHERITEM ON VOUCHER.ID = VOUCHERITEM.ACCDOCID \n");
        sql.append("       LEFT  JOIN FIGLACCDOCASSISTANCE${YEAR} VOUCHERDETAIL ON VOUCHER.ID = VOUCHERDETAIL.ACCDOCID  AND VOUCHERITEM.ID = VOUCHERDETAIL.ACCDOCENTRYID \n");
        sql.append("       LEFT  JOIN FIZWXJYS${YEAR} XJYS  \n");
        sql.append("               ON (VOUCHERITEM.ID = XJYS.ZWXJYS_FLNM AND VOUCHERDETAIL.ID IS NULL OR VOUCHERDETAIL.ID = XJYS.ZWXJYS_FZID) \n");
        sql.append("       LEFT  JOIN FIZWXJJG${YEAR} XJJG ON XJYS.ID = XJJG.ZWXJJG_YSID \n");
        sql.append("       LEFT  JOIN BFCASHFLOWTYPE CASHFLOW  ON CASHFLOW.ID = XJJG.ZWXJJG_XJXM\n");
        sql.append("       LEFT  JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON VOUCHERDETAIL.FOREIGNCURRENCYID = HB.ID \n");
        sql.append("       INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = VOUCHERITEM.ACCTITLEID AND SUBJECT.ACCOUNTTYPE = '0' and SUBJECT.CASHACCTITLE = 0 \r\n");
        sql.append(" ${PZASSJOINSQL}");
        sql.append(" WHERE 1 = 1 \n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(this.buildSubjectCondi("SUBJECT", "CODE", condi.getSubjectCode())).append(" \n");
        }
        sql.append(this.buildSubjectCondi("CASHFLOW", "CODE", condi.getCashCode()));
        sql.append("   ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" ORDER BY ROWTYPE DESC, ACCTYEAR, ACCTPERIOD, ACCTDAY, VCHRTYPE, ORDERID");
        StringBuilder yeAssField = new StringBuilder();
        StringBuilder yePzAssField = new StringBuilder();
        StringBuilder pzAssJoinSql = new StringBuilder();
        StringBuilder externalAssConfigSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            yeAssField.append(String.format(", %1$s.CODE AS %1$s, %1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            yePzAssField.append(String.format(", T.%1$s AS %1$s, T.%1$s_NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            pzAssJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = VOUCHERDETAIL.%3$s \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
            externalAssConfigSql.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), "EQ"));
        }
        Variable variable = new Variable();
        variable.put("YEASSFIELD", yeAssField.toString());
        variable.put("YEPZASSFIELD", yePzAssField.toString());
        variable.put("PZASSJOINSQL", pzAssJoinSql.toString());
        variable.put("STARTPERIOD", String.format("%02d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%02d", condi.getEndPeriod()));
        variable.put("ZWXJJG_JE", dbSqlHandler.nullToValue("XJJG.ZWXJJG_JE", "0"));
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("ORGID", orgMappingType.equals("GSCLOUD") ? "ACCORGID" : "LEDGER");
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("EXTERNAL_ORG_SQL", schemeMappingProvider.getOrgSql());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("EXTERNAL_CURRENCY_SQL", schemeMappingProvider.getCurrencySql());
        variable.put("EXTERNAL_ASS_CONFIG_SQL", externalAssConfigSql.toString());
        String replaceSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        String executeSql = GsCloudTableEnum.replaceAccYear(replaceSql, condi.getAcctYear());
        return new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new XjllDetailLedgerResultSetExtractor(condi, assistMappingList));
    }
}

