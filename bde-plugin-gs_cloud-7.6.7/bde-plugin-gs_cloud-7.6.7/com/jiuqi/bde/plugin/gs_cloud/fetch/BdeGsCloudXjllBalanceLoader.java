/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.gs_cloud.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader;
import com.jiuqi.bde.plugin.gs_cloud.BdeGsCloudPluginType;
import com.jiuqi.bde.plugin.gs_cloud.util.AssistPojo;
import com.jiuqi.bde.plugin.gs_cloud.util.GsCloudTableEnum;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BdeGsCloudXjllBalanceLoader
extends BaseXjllDataLoader {
    @Autowired
    private BdeGsCloudPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    protected FetchData queryData(BalanceCondition condi) {
        IDbSqlHandler dbSqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "GSCLOUD" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUBJECT.CODE AS SUBJECTCODE, \n");
        sql.append("       CASHFLOW.CODE AS CFITEMCODE, \n");
        sql.append("       HB.CODE AS CURRENCYCODE, \n");
        sql.append("       SUBJECT.ORIENT AS ORIENT, \n");
        sql.append("       ${YEASSFIELD} \n");
        sql.append("       SUM(CASE WHEN VOUCHER.ACCPERIODCODE='${ENDPERIOD}'  THEN ${ZWXJJG_JE} ELSE 0 END) AS BQNUM, \n");
        sql.append("       SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' THEN ${ZWXJJG_JE} ELSE 0 END) AS LJNUM, \n");
        sql.append("       SUM(CASE WHEN VOUCHER.ACCPERIODCODE='${ENDPERIOD}'  THEN ${ZWXJJG_JE} ELSE 0 END) AS WBQNUM, \n");
        sql.append("       SUM(CASE WHEN VOUCHER.ACCPERIODCODE<='${ENDPERIOD}' THEN ${ZWXJJG_JE} ELSE 0 END) AS WLJNUM \n");
        sql.append("  FROM (SELECT *  \n");
        sql.append("          FROM FIGLACCOUNTINGDOCUMENT${YEAR} VOUCHER  \n");
        sql.append("         WHERE VOUCHER.${ORGID} = (SELECT ORG.ID  FROM (${EXTERNAL_ORG_SQL}) ORG  \n");
        sql.append("                                          WHERE 1 = 1   \n");
        if (orgMappingType.equals("BOOKCODE")) {
            sql.append("                                        AND ORG.BOOKCODE='${BOOKCODE}' \n");
        } else {
            sql.append("                                        AND ORG.CODE='${UNITCODE}' \n");
        }
        sql.append("                          )  \n");
        sql.append("         AND VOUCHER.YEAR = ${YEAR}   \n");
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
        sql.append("       ${PZJOINSQL} \n");
        sql.append(" WHERE 1=1 \n");
        sql.append(" AND CASHFLOW.CODE IS NOT NULL \n");
        sql.append(" GROUP BY SUBJECT.CODE, CASHFLOW.CODE, HB.CODE, SUBJECT.ORIENT \n");
        sql.append("       ${YEGROUPFIELD} \n");
        StringBuilder yeAssField = new StringBuilder();
        StringBuilder yeGroupField = new StringBuilder();
        StringBuilder masterAssField = new StringBuilder();
        StringBuilder yeJoinSql = new StringBuilder();
        StringBuilder pzJoinSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            yeAssField.append(String.format("%1$s.CODE AS %1$s,", assistMapping.getAssistCode()));
            yeJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON B.%3$s = %2$s.ID \n", assistMapping.getAssistSql(), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
            pzJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON VOUCHERDETAIL.%3$s = %2$s.ID \n", assistMapping.getAssistSql(), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
            yeGroupField.append(String.format(", %1$s.CODE", assistMapping.getAssistCode()));
            masterAssField.append(String.format(", MASTER.%1$s", assistMapping.getAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("YEASSFIELD", yeAssField.toString());
        variable.put("YEJOINSQL", yeJoinSql.toString());
        variable.put("PZJOINSQL", pzJoinSql.toString());
        variable.put("YEGROUPFIELD", yeGroupField.toString());
        variable.put("MASTERASSFIELD", masterAssField.toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("ORGID", orgMappingType.equals("GSCLOUD") ? "ACCORGID" : "LEDGER");
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("PREVIOUSPERIOD", String.format("%02d", condi.getEndPeriod() - 1));
        variable.put("STARTPERIOD", String.format("%02d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%02d", condi.getEndPeriod()));
        variable.put("ZWXJJG_JE", dbSqlHandler.nullToValue("XJJG.ZWXJJG_JE", "0"));
        variable.put("EXTERNAL_ORG_SQL", schemeMappingProvider.getOrgSql());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("EXTERNAL_CURRENCY_SQL", schemeMappingProvider.getCurrencySql());
        String replaceSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        String lastSql = GsCloudTableEnum.replaceAccYear(replaceSql, condi.getAcctYear());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u73b0\u91d1\u6d41\u91cf", (Object)condi, (String)lastSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), lastSql, null, (ResultSetExtractor)new FetchDataExtractor());
    }
}

