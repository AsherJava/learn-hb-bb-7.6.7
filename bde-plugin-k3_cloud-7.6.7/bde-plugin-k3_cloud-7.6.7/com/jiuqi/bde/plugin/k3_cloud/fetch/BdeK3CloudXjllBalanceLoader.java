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
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.k3_cloud.fetch;

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
import com.jiuqi.bde.plugin.k3_cloud.BdeK3CloudPluginType;
import com.jiuqi.bde.plugin.k3_cloud.util.AssistPojo;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BdeK3CloudXjllBalanceLoader
extends BaseXjllDataLoader {
    @Autowired
    private BdeK3CloudPluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    protected FetchData queryData(BalanceCondition condi) {
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("          CURRENCY.FCODE AS CURRENCYCODE,\n");
        query.append("          SUBJECT.FNUMBER AS SUBJECTCODE,\n");
        query.append("          SUBJECT.FDC AS ORIENT,\n");
        query.append("          CASHITEM.FNUMBER AS CFITEMCODE,\n");
        query.append("          SUM(CASE WHEN VOUCHER.FPERIOD = ${ENDPERIOD} THEN CASH.FAMOUNT ELSE 0 END) AS BQNUM,\n");
        query.append("          SUM(CASE WHEN VOUCHER.FPERIOD = ${ENDPERIOD} THEN CASH.FAMOUNTFOR ELSE 0 END) AS WBQNUM,\n");
        query.append("          SUM(CASE WHEN VOUCHER.FPERIOD <= ${ENDPERIOD} THEN CASH.FAMOUNT ELSE 0 END) AS LJNUM,\n");
        query.append("          SUM(CASE WHEN VOUCHER.FPERIOD <= ${ENDPERIOD} THEN CASH.FAMOUNTFOR ELSE 0 END) AS WLJNUM\n");
        query.append("          ${INASSFIELD} \n");
        query.append("  FROM\n");
        query.append("          T_GL_CASHFLOWBAL CASH\n");
        query.append("  INNER JOIN T_GL_CASHFLOW CASHITEM ON\n");
        query.append("          CASHITEM.FID = CASH.FITEMID\n");
        query.append("  INNER JOIN T_GL_VOUCHER VOUCHER ON\n");
        query.append("          CASH.FVOUCHERID = VOUCHER.FVOUCHERID\n");
        query.append("  INNER JOIN T_GL_VOUCHERENTRY VOUCHERENTRY ON\n");
        query.append("          CASH.FVCHOPPOENTRYID = VOUCHERENTRY.FENTRYID  \n");
        query.append("  INNER JOIN T_BD_CURRENCY CURRENCY ON\n");
        query.append("          CASH.FCURRENCYID = CURRENCY.FCURRENCYID\n");
        query.append("  INNER JOIN T_BD_ACCOUNT SUBJECT ON\n");
        query.append("          VOUCHERENTRY.FACCOUNTID = SUBJECT.FACCTID\n");
        query.append("  ${PZASSJOINSQL} \n");
        query.append("  WHERE\n");
        query.append("          1 = 1\n");
        query.append("          AND SUBJECT.FISDETAIL = 1\n");
        query.append("          AND VOUCHER.FYEAR = ${YEAR}\n");
        query.append("          AND CASHITEM.FNUMBER<'CI05'\n");
        query.append("          AND VOUCHER.FISCASHFLOW='1'\n");
        query.append("          AND CASH.FACCTBOOKID=${UNITCODE}\n");
        query.append("          ${INCLUDESQL}\n");
        query.append("  GROUP BY CURRENCY.FCODE,SUBJECT.FNUMBER,SUBJECT.FDC,CASHITEM.FNUMBER\n");
        query.append("           ${INASSFIELDGROUP} \n");
        query.append("  UNION ALL \n");
        query.append("  SELECT\n");
        query.append("          CURRENCY.FCODE AS CURRENCYCODE,\n");
        query.append("          SUBJECT.FNUMBER AS SUBJECTCODE,\n");
        query.append("          SUBJECT.FDC AS ORIENT,\n");
        query.append("          CASHITEM.FNUMBER AS CFITEMCODE,\n");
        query.append("          SUM(CASE WHEN VOUCHER.FPERIOD = ${ENDPERIOD} THEN VOUCHERENTRY.FAMOUNT ELSE 0 END) AS BQNUM,\n");
        query.append("          SUM(CASE WHEN VOUCHER.FPERIOD = ${ENDPERIOD} THEN VOUCHERENTRY.FAMOUNTFOR ELSE 0 END) AS WBQNUM,\n");
        query.append("          SUM(CASE WHEN VOUCHER.FPERIOD <= ${ENDPERIOD} THEN VOUCHERENTRY.FAMOUNT ELSE 0 END) AS LJNUM,\n");
        query.append("          SUM(CASE WHEN VOUCHER.FPERIOD <= ${ENDPERIOD} THEN VOUCHERENTRY.FAMOUNTFOR ELSE 0 END) AS WLJNUM\n");
        query.append("          ${INASSFIELD} \n");
        query.append("  FROM\n");
        query.append("          T_GL_CASHFLOWBAL CASH\n");
        query.append("  INNER JOIN T_GL_CASHFLOW CASHITEM ON\n");
        query.append("          CASHITEM.FID = CASH.FITEMID\n");
        query.append("  INNER JOIN T_GL_VOUCHER VOUCHER ON\n");
        query.append("          CASH.FVOUCHERID = VOUCHER.FVOUCHERID\n");
        query.append("  INNER JOIN T_GL_VOUCHERENTRY VOUCHERENTRY ON\n");
        query.append("          CASH.FVCHOPPOENTRYID = VOUCHERENTRY.FENTRYID  \n");
        query.append("  INNER JOIN T_BD_CURRENCY CURRENCY ON\n");
        query.append("          CASH.FCURRENCYID = CURRENCY.FCURRENCYID\n");
        query.append("  INNER JOIN T_BD_ACCOUNT SUBJECT ON\n");
        query.append("          VOUCHERENTRY.FACCOUNTID = SUBJECT.FACCTID\n");
        query.append("  ${PZASSJOINSQL} \n");
        query.append("  WHERE\n");
        query.append("          1 = 1\n");
        query.append("          AND SUBJECT.FISDETAIL = 1\n");
        query.append("          AND VOUCHER.FYEAR = ${YEAR}\n");
        query.append("          AND CASHITEM.FNUMBER>='CI05'\n");
        query.append("          AND VOUCHER.FISCASHFLOW='1'\n");
        query.append("          AND VOUCHER.FINVALID = '0'  \n");
        query.append("          AND CASH.FACCTBOOKID=${UNITCODE}\n");
        query.append("          ${INCLUDESQL}\n");
        query.append("  GROUP BY CURRENCY.FCODE,SUBJECT.FNUMBER,SUBJECT.FDC,CASHITEM.FNUMBER\n");
        query.append("          ${INASSFIELDGROUP} \n");
        StringBuilder pzAssJoinSql = new StringBuilder();
        StringBuilder inAssFieldGroup = new StringBuilder();
        StringBuilder inAssField = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            if (pzAssJoinSql.length() == 0) {
                pzAssJoinSql.append(" INNER JOIN T_BD_FLEXITEMDETAILV FLEXVALUE ON VOUCHERENTRY.FDETAILID =FLEXVALUE.FID  \n");
            }
            pzAssJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=FLEXVALUE.%3$s ", assistMapping.getAssistSql(), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
            inAssField.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            inAssFieldGroup.append(String.format(",%1$s.CODE", assistMapping.getAssistCode()));
        }
        String includeSql = condi.getIncludeUncharged() != false ? "" : " AND VOUCHER.FPOSTED ='1' ";
        Variable variable = new Variable();
        variable.put("STARTPERIOD", condi.getEndPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("INCLUDESQL", includeSql);
        variable.put("PZASSJOINSQL", pzAssJoinSql.toString());
        variable.put("INASSFIELDGROUP", inAssFieldGroup.toString());
        variable.put("INASSFIELD", inAssField.toString());
        String querySql = VariableParseUtil.parse((String)query.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u73b0\u91d1\u6d41\u91cf", (Object)new Object[]{condi}, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), querySql, null, (ResultSetExtractor)new FetchDataExtractor());
    }
}

