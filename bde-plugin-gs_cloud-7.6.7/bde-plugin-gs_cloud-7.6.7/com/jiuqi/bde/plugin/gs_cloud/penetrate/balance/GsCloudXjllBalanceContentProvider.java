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
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractXjllBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.XjllBalanceResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.gs_cloud.penetrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractXjllBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.XjllBalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.gs_cloud.BdeGsCloudPluginType;
import com.jiuqi.bde.plugin.gs_cloud.util.GsCloudTableEnum;
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
public class GsCloudXjllBalanceContentProvider
extends AbstractXjllBalanceContentProvider {
    @Autowired
    private BdeGsCloudPluginType gsCloudPluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public String getPluginType() {
        return this.gsCloudPluginType.getSymbol();
    }

    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        IDbSqlHandler dbSqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "GSCLOUD" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT\n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append("          SUBJECT.CODE AS MD_ACCTSUBJECT,\n");
            sql.append("          SUBJECT.NAME AS MD_ACCTSUBJECT_NAME,\n");
        }
        sql.append("          CASHFLOW.CODE AS MD_CFITEM,\n");
        sql.append("          CASHFLOW.NAME_CHS AS MD_CFITEM_NAME,\n");
        sql.append("          SUM(CASE WHEN VOUCHER.ACCPERIODCODE='${ENDPERIOD}'  THEN ${ZWXJJG_JE} ELSE 0 END) AS BQNUM, \n");
        sql.append("          SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' THEN ${ZWXJJG_JE} ELSE 0 END) AS LJNUM, \n");
        sql.append("          SUM(CASE WHEN VOUCHER.ACCPERIODCODE='${ENDPERIOD}'  THEN ${ZWXJJG_JE} ELSE 0 END) AS WBQNUM, \n");
        sql.append("          SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' THEN ${ZWXJJG_JE} ELSE 0 END) AS WLJNUM \n");
        sql.append("          ${INASSFIELD} \n");
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
        sql.append("                            ) VOUCHER  \n");
        sql.append("       INNER JOIN FIGLACCDOCENTRY${YEAR} VOUCHERITEM ON VOUCHER.ID = VOUCHERITEM.ACCDOCID \n");
        sql.append("       LEFT  JOIN FIGLACCDOCASSISTANCE${YEAR} VOUCHERDETAIL ON VOUCHER.ID = VOUCHERDETAIL.ACCDOCID  AND VOUCHERITEM.ID = VOUCHERDETAIL.ACCDOCENTRYID \n");
        sql.append("       LEFT  JOIN FIZWXJYS${YEAR} XJYS  \n");
        sql.append("               ON (VOUCHERITEM.ID = XJYS.ZWXJYS_FLNM AND VOUCHERDETAIL.ID IS NULL OR VOUCHERDETAIL.ID = XJYS.ZWXJYS_FZID) \n");
        sql.append("       LEFT  JOIN FIZWXJJG${YEAR} XJJG ON XJYS.ID = XJJG.ZWXJJG_YSID \n");
        sql.append("       LEFT  JOIN BFCASHFLOWTYPE CASHFLOW  ON CASHFLOW.ID = XJJG.ZWXJJG_XJXM\n");
        sql.append("       LEFT  JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON VOUCHERDETAIL.FOREIGNCURRENCYID = HB.ID \n");
        sql.append("       INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = VOUCHERITEM.ACCTITLEID AND SUBJECT.ACCOUNTTYPE = '0' and SUBJECT.CASHACCTITLE = 0 \r\n");
        sql.append("    ${PZASSJOINSQL} \n");
        sql.append("   WHERE 1 = 1 \n");
        sql.append(this.buildCfItemCondi("CASHFLOW", "CODE", condi.getCashCode()));
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(this.buildSubjectCondi("SUBJECT", "CODE", condi.getSubjectCode()));
        }
        sql.append("          ${ASSCONDITION}\n");
        sql.append("  GROUP BY CASHFLOW.CODE, CASHFLOW.NAME_CHS\n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(", SUBJECT.CODE, SUBJECT.NAME \n");
        }
        sql.append("           ${INASSFIELDGROUP} \n");
        sql.append("  ORDER BY CASHFLOW.CODE, CASHFLOW.NAME_CHS\n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(", SUBJECT.CODE, SUBJECT.NAME \n");
        }
        StringBuilder pzAssJoinSql = new StringBuilder();
        StringBuilder inAssFieldGroup = new StringBuilder();
        StringBuilder inAssField = new StringBuilder();
        StringBuilder assistCondition = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            pzAssJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON VOUCHERDETAIL.%3$s = %2$s.ID \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            inAssField.append(String.format(",%1$s.CODE AS %1$s ,%1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            inAssFieldGroup.append(String.format(",%1$s.CODE,%1$s.NAME", assistMapping.getAssistCode()));
            assistCondition.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule()));
        }
        Variable variable = new Variable();
        variable.put("STARTPERIOD", String.format("%02d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%02d", condi.getEndPeriod()));
        variable.put("ZWXJJG_JE", dbSqlHandler.nullToValue("XJJG.ZWXJJG_JE", "0"));
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("ORGID", orgMappingType.equals("GSCLOUD") ? "ACCORGID" : "LEDGER");
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("PZASSJOINSQL", pzAssJoinSql.toString());
        variable.put("INASSFIELDGROUP", inAssFieldGroup.toString());
        variable.put("ASSCONDITION", assistCondition.toString());
        variable.put("INASSFIELD", inAssField.toString());
        variable.put("EXTERNAL_ORG_SQL", schemeMappingProvider.getOrgSql());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("EXTERNAL_CURRENCY_SQL", schemeMappingProvider.getCurrencySql());
        String replaceSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        String executeSql = GsCloudTableEnum.replaceAccYear(replaceSql, condi.getAcctYear());
        return new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new XjllBalanceResultSetExtractor(condi, assistMappingList));
    }
}

