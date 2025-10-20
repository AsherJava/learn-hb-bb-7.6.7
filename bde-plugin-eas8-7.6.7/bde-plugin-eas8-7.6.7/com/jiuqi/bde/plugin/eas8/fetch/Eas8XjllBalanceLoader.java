/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.eas8.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader;
import com.jiuqi.bde.plugin.eas8.Eas8PluginType;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Eas8XjllBalanceLoader
extends BaseXjllDataLoader {
    @Autowired
    private Eas8PluginType eas8PluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public IBdePluginType getPluginType() {
        return this.eas8PluginType;
    }

    protected FetchData queryData(BalanceCondition condi) {
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT \n");
        sql.append("SUM(" + sqlHandler.nullToValue("CASE WHEN PERIOD.FPERIODNUMBER >= 1 AND PERIOD.FPERIODNUMBER <= ${ENDPERIOD} THEN CR.FPRIMARYCOEFFIENT * CR.FLOCALAMOUNT ELSE 0 END ", "0") + ") AS LJNUM,\n");
        sql.append("SUM(" + sqlHandler.nullToValue("CASE WHEN PERIOD.FPERIODNUMBER >= 1 AND PERIOD.FPERIODNUMBER <= ${ENDPERIOD} THEN CR.FPRIMARYCOEFFIENT * CR.FORIGINALAMOUNT ELSE 0 END ", "0") + ") AS WLJNUM,\n");
        sql.append("SUM(" + sqlHandler.nullToValue("CASE WHEN PERIOD.FPERIODNUMBER = ${ENDPERIOD} THEN CR.FPRIMARYCOEFFIENT * CR.FLOCALAMOUNT ELSE 0 END  ", "0") + ") AS BQNUM,\n");
        sql.append("SUM(" + sqlHandler.nullToValue("CASE WHEN PERIOD.FPERIODNUMBER = ${ENDPERIOD} THEN CR.FPRIMARYCOEFFIENT * CR.FORIGINALAMOUNT ELSE 0 END ", "0") + ") AS WBQNUM, \n");
        sql.append("       CI.FNUMBER AS CFITEMCODE,  \n");
        sql.append("       KM.FNUMBER AS SUBJECTCODE,  \n");
        sql.append("       HB.FNUMBER AS CURRENCYCODE,  \n");
        sql.append("       KM.FDC AS ORIENT  \n");
        sql.append("       ${ASSFIELD} \n");
        sql.append("  FROM T_GL_Voucher VOUCHER  \n");
        sql.append("  INNER JOIN T_GL_VOUCHERENTRY V  \n");
        sql.append("    ON VOUCHER.FID = V.FBillID\n");
        sql.append("  INNER JOIN T_BD_ACCOUNTVIEW KM  \n");
        sql.append("    ON KM.FID = V.FACCOUNTID  \n");
        sql.append("  LEFT JOIN T_BD_PERIOD PERIOD  \n");
        sql.append("    ON VOUCHER.FPERIODID = PERIOD.FID  \n");
        sql.append(" LEFT JOIN T_GL_CASHFLOWRECORD CR  \n");
        sql.append("    ON V.FID = CR.FOPPOSINGACCOUNTENTRYID  \n");
        sql.append("  LEFT JOIN T_BD_ASSISTANTHG ASS  \n");
        sql.append("    ON CR.FASSGRPID = ASS.FID  \n");
        sql.append(" LEFT JOIN T_BD_CASHFLOWITEM CI  \n");
        sql.append("    ON CR.FPRIMARYITEMID = CI.FID  \n");
        sql.append(" LEFT JOIN T_ORG_COMPANY ORG  \n");
        sql.append("    ON VOUCHER.FCOMPANYID = ORG.FID  \n");
        sql.append(" LEFT JOIN T_BD_CURRENCY HB  \n");
        sql.append("    ON V.FCURRENCYID = HB.FID  \n");
        sql.append("  ${ASSJOIN} \n");
        sql.append(" WHERE PERIOD.FPERIODYEAR = ${YEAR}  \n");
        sql.append("   AND ORG.FNUMBER = '${UNITCODE}'  \n");
        sql.append("   AND VOUCHER.FCASHFLOWFLAG <> 0  \n");
        sql.append("   AND VOUCHER.FBIZSTATUS <> 0  \n");
        sql.append("   AND VOUCHER.FBIZSTATUS <> 2  \n");
        sql.append("   ${INCLUDECON}\n");
        sql.append(" GROUP BY CI.FNUMBER, KM.FNUMBER, KM.FDC, HB.FNUMBER \n");
        sql.append("          ${GROUPFIELD}  \n");
        sql.append(" UNION ALL  \n");
        sql.append("SELECT \n");
        sql.append("SUM(" + sqlHandler.nullToValue("CASE WHEN PERIOD.FPERIODNUMBER >= 1 AND PERIOD.FPERIODNUMBER <= ${ENDPERIOD} THEN CR.FPRIMARYCOEFFIENT * CR.FLOCALAMOUNT ELSE 0 END ", "0") + ") AS LJNUM,\n");
        sql.append("SUM(" + sqlHandler.nullToValue("CASE WHEN PERIOD.FPERIODNUMBER >= 1 AND PERIOD.FPERIODNUMBER <= ${ENDPERIOD} THEN CR.FPRIMARYCOEFFIENT * CR.FORIGINALAMOUNT ELSE 0 END ", "0") + ") AS WLJNUM,\n");
        sql.append("SUM(" + sqlHandler.nullToValue("CASE WHEN PERIOD.FPERIODNUMBER = ${ENDPERIOD} THEN CR.FPRIMARYCOEFFIENT * CR.FLOCALAMOUNT ELSE 0 END  ", "0") + ") AS BQNUM,\n");
        sql.append("SUM(" + sqlHandler.nullToValue("CASE WHEN PERIOD.FPERIODNUMBER = ${ENDPERIOD} THEN CR.FPRIMARYCOEFFIENT * CR.FORIGINALAMOUNT ELSE 0 END ", "0") + ") AS WBQNUM, \n");
        sql.append("       CI.FNUMBER AS CFITEMCODE,  \n");
        sql.append("       KM.FNUMBER AS SUBJECTCODE,  \n");
        sql.append("       HB.FNUMBER AS CURRENCYCODE,  \n");
        sql.append("       KM.FDC AS ORIENT  \n");
        sql.append("       ${ASSFIELD} \n");
        sql.append("  FROM T_GL_Voucher VOUCHER  \n");
        sql.append("  INNER JOIN T_GL_VOUCHERENTRY V  \n");
        sql.append("    ON VOUCHER.FID = V.FBillID\n");
        sql.append("  INNER JOIN T_BD_ACCOUNTVIEW KM  \n");
        sql.append("    ON KM.FID = V.FACCOUNTID  \n");
        sql.append("  LEFT JOIN T_BD_PERIOD PERIOD  \n");
        sql.append("    ON VOUCHER.FPERIODID = PERIOD.FID  \n");
        sql.append(" LEFT JOIN T_GL_CASHFLOWRECORD CR  \n");
        sql.append("    ON V.FID = CR.FSUPPLEMENTARYITEMID  \n");
        sql.append(" LEFT JOIN T_BD_CASHFLOWITEM CI  \n");
        sql.append("    ON CR.FPRIMARYITEMID = CI.FID  \n");
        sql.append("  LEFT JOIN T_BD_ASSISTANTHG ASS  \n");
        sql.append("    ON CR.FASSGRPID = ASS.FID  \n");
        sql.append(" LEFT JOIN T_ORG_COMPANY ORG  \n");
        sql.append("    ON VOUCHER.FCOMPANYID = ORG.FID  \n");
        sql.append(" LEFT JOIN T_BD_CURRENCY HB  \n");
        sql.append("    ON V.FCURRENCYID = HB.FID  \n");
        sql.append("  ${ASSJOIN} \n");
        sql.append(" WHERE PERIOD.FPERIODYEAR = ${YEAR}  \n");
        sql.append("   AND ORG.FNUMBER = '${UNITCODE}'  \n");
        sql.append("   AND VOUCHER.FCASHFLOWFLAG <> 0  \n");
        sql.append("   AND VOUCHER.FBIZSTATUS <> 0  \n");
        sql.append("   AND VOUCHER.FBIZSTATUS <> 2  \n");
        sql.append("   ${INCLUDECON}\n");
        sql.append(" GROUP BY CI.FNUMBER, KM.FNUMBER, KM.FDC, HB.FNUMBER \n");
        sql.append("          ${GROUPFIELD}  \n");
        Variable variable = new Variable();
        StringBuilder assJoin = new StringBuilder();
        StringBuilder assField = new StringBuilder();
        StringBuilder groupField = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            assJoin.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=ASS.%3$s", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            assField.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            groupField.append(String.format(",%1$s.CODE", assistMapping.getAssistCode()));
        }
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("ASSFIELD", assField.toString());
        variable.put("ASSJOIN", assJoin.toString());
        variable.put("GROUPFIELD", groupField.toString());
        variable.put("INCLUDECON", condi.getIncludeUncharged() != false ? "" : "AND VOUCHER.FBizStatus = 5\n");
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u73b0\u91d1\u6d41\u91cf", (Object)condi, (String)lastSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), lastSql, new Object[0], (ResultSetExtractor)new FetchDataExtractor());
    }
}

