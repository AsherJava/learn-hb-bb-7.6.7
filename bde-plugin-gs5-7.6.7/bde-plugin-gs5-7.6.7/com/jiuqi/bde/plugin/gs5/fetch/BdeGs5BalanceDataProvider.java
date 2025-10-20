/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.util.TempTableUtils
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.gs5.fetch;

import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.gs5.util.AssistPojo;
import com.jiuqi.bde.plugin.gs5.util.Gs5FetchUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
class BdeGs5BalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;

    BdeGs5BalanceDataProvider() {
    }

    FetchData queryData(BalanceCondition condi) {
        String sql;
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        Variable variable = new Variable();
        if (CollectionUtils.isEmpty((Collection)assistMappingList)) {
            sql = condi.getIncludeUncharged() != false ? this.getIncludeSql(condi) : this.getNotIncludeSql(condi);
        } else {
            sql = condi.getIncludeUncharged() != false ? this.getIncludeAssSql(condi) : this.getNotIncludeAssSql(condi);
            StringBuilder yeAssField = new StringBuilder();
            StringBuilder yeGroupField = new StringBuilder();
            StringBuilder masterGroupField = new StringBuilder();
            StringBuilder pzAssField = new StringBuilder();
            StringBuilder masterAssField = new StringBuilder();
            StringBuilder yeJoinSql = new StringBuilder();
            StringBuilder pzJoinSql = new StringBuilder();
            StringBuilder gxCondition = new StringBuilder();
            for (AssistMappingBO assistMapping : assistMappingList) {
                if (!"XMBH".equals(((AssistPojo)assistMapping.getAccountAssist()).getAssField())) {
                    yeAssField.append(String.format(",B.ZWFZYE_%1$s AS %2$s", ((AssistPojo)assistMapping.getAccountAssist()).getAssField(), assistMapping.getAssistCode()));
                    pzAssField.append(String.format(",VOUCHERDETAIL.ZWFZYS_%1$s AS %2$s", ((AssistPojo)assistMapping.getAccountAssist()).getAssField(), assistMapping.getAssistCode()));
                    yeGroupField.append(String.format(",B.ZWFZYE_%1$s", ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
                    masterAssField.append(String.format(",MASTER.%1$s AS %1$s", assistMapping.getAssistCode()));
                    masterGroupField.append(String.format(",MASTER.%1$s", assistMapping.getAssistCode()));
                    continue;
                }
                Boolean hasAss = (Boolean)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), String.format("SELECT COUNT(*) FROM %1$s WHERE LSXMLB_LBMC = ?", Gs5FetchUtil.getTableName()), new Object[]{((AssistPojo)assistMapping.getAccountAssist()).getName()}, rs -> rs.next());
                if (!hasAss.booleanValue()) {
                    throw new BusinessRuntimeException(String.format("\u672a\u67e5\u8be2\u5230\u5bf9\u5e94\u7684\u8f85\u52a9\u9879\u3010%1$s\u3011", ((AssistPojo)assistMapping.getAccountAssist()).getName()));
                }
                if (yeJoinSql.length() == 0) {
                    yeJoinSql.append(" LEFT JOIN ZWHSGX${YEAR} GX ON GX.ZWHSGX_ZJBH = '%' AND GX.ZWHSGX_DWBH='${UNITCODE}' AND GX.ZWHSGX_KMBH = B.ZWFZYE_KMBH AND (${GXCONDITION}) \n");
                    pzJoinSql.append(" LEFT JOIN ZWHSGX${YEAR} GX ON GX.ZWHSGX_ZJBH = '%' AND GX.ZWHSGX_DWBH='${UNITCODE}' AND GX.ZWHSGX_KMBH = VOUCHERDETAIL.ZWFZYS_KMBH AND (${GXCONDITION}) \n");
                    gxCondition.append(String.format("GX.ZWHSGX_ZXHS LIKE '%1$s'", ((AssistPojo)assistMapping.getAccountAssist()).getCode()));
                } else {
                    gxCondition.append(String.format(" OR GX.ZWHSGX_ZXHS LIKE '%1$s'", ((AssistPojo)assistMapping.getAccountAssist()).getCode()));
                }
                yeAssField.append(String.format(", %1$s AS %2$s", sqlHandler.SubStr("B.ZWFZYE_XMBH", String.format("(%1$d-1)*6+%1$d", Integer.valueOf(((AssistPojo)assistMapping.getAccountAssist()).getCode())), "6"), assistMapping.getAssistCode()));
                pzAssField.append(String.format(", %1$s AS %2$s", sqlHandler.SubStr("VOUCHERDETAIL.ZWFZYS_XMBH", String.format("(%1$d-1)*6+%1$d", Integer.valueOf(((AssistPojo)assistMapping.getAccountAssist()).getCode())), "6"), assistMapping.getAssistCode()));
                yeGroupField.append(",").append(sqlHandler.SubStr("B.ZWFZYE_XMBH", String.format("(%1$d-1)*6+%1$d", Integer.valueOf(((AssistPojo)assistMapping.getAccountAssist()).getCode())), "6"));
                masterAssField.append(String.format(",MASTER.%1$s AS %1$s", assistMapping.getAssistCode()));
                masterGroupField.append(String.format(",MASTER.%1$s", assistMapping.getAssistCode()));
            }
            variable.put("YEASSFIELD", yeAssField.toString());
            variable.put("YEJOINSQL", yeJoinSql.toString());
            variable.put("GXCONDITION", gxCondition.toString());
            variable.put("PZASSFIELD", pzAssField.toString());
            variable.put("YEGROUPFIELD", yeGroupField.toString());
            variable.put("MASTERGROUPFIELD", masterGroupField.toString());
            variable.put("MASTERASSFIELD", masterAssField.toString());
            variable.put("PZJOINSQL", pzJoinSql.toString());
        }
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("SUBTRACTENDMONTH", String.format("%02d", condi.getStartPeriod() - 1));
        variable.put("STARTPERIOD", String.format("%02d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%02d", condi.getEndPeriod()));
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u79d1\u76ee\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)condi, (String)lastSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), lastSql, new Object[0], (ResultSetExtractor)new FetchDataExtractor());
    }

    private String getNotIncludeSql(BalanceCondition condi) {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT  \n");
        sql.append("            HB.LSWBZD_BZBH AS CURRENCYCODE,\n");
        sql.append("            B.ZWKMYE_KMBH AS SUBJECTCODE,\n");
        sql.append("            SUBJECT.KMORIENT AS ORIENT,\n");
        sql.append("            SUM(CASE WHEN B.ZWKMYE_KJQJ='${ENDPERIOD}' THEN B.ZWKMYE_NCYE ELSE 0 END) AS NC,\n");
        sql.append("            SUM(CASE WHEN B.ZWKMYE_KJQJ='${ENDPERIOD}' THEN B.ZWKMYE_DQYE ELSE 0 END) AS YE,\n");
        sql.append("            SUM(CASE WHEN B.ZWKMYE_KJQJ='${ENDPERIOD}' THEN B.ZWKMYE_JFFS ELSE 0 END) AS JF,\n");
        sql.append("            SUM(CASE WHEN B.ZWKMYE_KJQJ='${ENDPERIOD}' THEN B.ZWKMYE_DFFS ELSE 0 END) AS DF,\n");
        sql.append("            SUM(CASE WHEN B.ZWKMYE_KJQJ='${ENDPERIOD}' THEN B.ZWKMYE_JFLJ ELSE 0 END) AS JL,\n");
        sql.append("            SUM(CASE WHEN B.ZWKMYE_KJQJ='${ENDPERIOD}' THEN B.ZWKMYE_DFLJ ELSE 0 END) AS DL,\n");
        if (condi.getEndPeriod() != 1) {
            sql.append("SUM(CASE WHEN B.ZWKMYE_KJQJ='${SUBTRACTENDMONTH}' THEN B.ZWKMYE_DQYE ELSE 0 END) AS C,");
            sql.append("SUM(CASE WHEN B.ZWKMYE_KJQJ='${SUBTRACTENDMONTH}' THEN B.ZWKMYE_WBDQYE ELSE 0 END) AS WC,");
        } else {
            sql.append("SUM(CASE WHEN B.ZWKMYE_KJQJ='${ENDPERIOD}' THEN B.ZWKMYE_NCYE ELSE 0 END) AS C,");
            sql.append("SUM(CASE WHEN B.ZWKMYE_KJQJ='${ENDPERIOD}' THEN B.ZWKMYE_WBNCYE ELSE 0 END) AS WC,");
        }
        sql.append("            SUM(CASE WHEN B.ZWKMYE_KJQJ='${ENDPERIOD}' THEN B.ZWKMYE_WBNCYE ELSE 0 END) AS WNC,\n");
        sql.append("            SUM(CASE WHEN B.ZWKMYE_KJQJ='${ENDPERIOD}' THEN B.ZWKMYE_WBDQYE ELSE 0 END) AS WYE,\n");
        sql.append("            SUM(CASE WHEN B.ZWKMYE_KJQJ='${ENDPERIOD}' THEN B.ZWKMYE_WBJFFS ELSE 0 END) AS WJF,\n");
        sql.append("            SUM(CASE WHEN B.ZWKMYE_KJQJ='${ENDPERIOD}' THEN B.ZWKMYE_WBDFFS ELSE 0 END) AS WDF,\n");
        sql.append("            SUM(CASE WHEN B.ZWKMYE_KJQJ='${ENDPERIOD}' THEN B.ZWKMYE_WBJFLJ ELSE 0 END) AS WJL,\n");
        sql.append("            SUM(CASE WHEN B.ZWKMYE_KJQJ='${ENDPERIOD}' THEN B.ZWKMYE_WBDFLJ ELSE 0 END) AS WDL   \n");
        sql.append("      FROM   ZWKMYE${YEAR}  B \n");
        sql.append("     INNER JOIN LSWBZD HB ON HB.LSWBZD_BZBH=B.ZWKMYE_WBBH \n");
        sql.append("     INNER JOIN (SELECT  ZWKMZD_KMBH AS SUBJECTCODE,  \n");
        sql.append("                         CASE WHEN ZWKMZD_YEFX = 1 THEN 1 ELSE -1 END AS KMORIENT , ZWKMZD_DWBH  \n");
        sql.append("                  FROM   ZWKMZD${YEAR}          ) SUBJECT ON SUBJECT.SUBJECTCODE=B.ZWKMYE_KMBH  \n");
        sql.append("                 AND (SUBJECT.ZWKMZD_DWBH=' ' OR SUBJECT.ZWKMZD_DWBH='${UNITCODE}') \n");
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"B.ZWKMYE_KMBH"));
        sql.append(" WHERE 1=1   \n");
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"B.ZWKMYE_KMBH"));
        sql.append("             AND   B.ZWKMYE_KJND='${YEAR}' \n \n");
        sql.append("             AND B.ZWKMYE_DWBH='${UNITCODE}' \n");
        sql.append("     GROUP BY HB.LSWBZD_BZBH,B.ZWKMYE_KMBH,SUBJECT.KMORIENT  \n");
        return sql.toString();
    }

    private String getIncludeSql(BalanceCondition condi) {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT  \n");
        query.append("        MASTER.SUBJECTCODE AS SUBJECTCODE, \n");
        query.append("        MASTER.CURRENCYCODE AS CURRENCYCODE, \n");
        query.append("        MASTER.ORIENT AS ORIENT, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD='00' THEN NC ELSE 0 END) AS NC, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD='00' THEN WNC ELSE 0 END) AS WNC, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' AND MASTER.PERIOD>='${STARTPERIOD}' THEN JF ELSE 0 END) AS JF, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' AND MASTER.PERIOD>='${STARTPERIOD}' THEN WJF ELSE 0 END) AS WJF, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' THEN WJF ELSE 0 END) AS JL, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' THEN WJF ELSE 0 END) AS WJL, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' AND MASTER.PERIOD>='${STARTPERIOD}' THEN DF ELSE 0 END) AS DF, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' AND MASTER.PERIOD>='${STARTPERIOD}' THEN WDF ELSE 0 END) AS WDF, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' THEN WDF ELSE 0 END) AS DL, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' THEN WDF ELSE 0 END) AS WDL, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' THEN NC+JF-DF ELSE 0 END) AS YE, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' THEN WNC+WJF-WDF ELSE 0 END) AS WYE, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${SUBTRACTENDMONTH}' THEN NC+JF-DF ELSE 0 END) AS C, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${SUBTRACTENDMONTH}' THEN WNC+WJF-WDF ELSE 0 END) AS WC \n");
        query.append(" FROM  \n");
        query.append("     (SELECT  \n");
        query.append("             HB.LSWBZD_BZBH AS CURRENCYCODE,\n");
        query.append("             B.ZWKMYE_KMBH AS SUBJECTCODE,\n");
        query.append("             SUBJECT.KMORIENT AS ORIENT,\n");
        query.append("             '00' AS PERIOD, \n");
        query.append("             B.ZWKMYE_NCYE AS NC,\n");
        query.append("             B.ZWKMYE_WBNCYE AS WNC,\n");
        query.append("             0 AS JF,\n");
        query.append("             0 AS DF,\n");
        query.append("             0 AS WJF,\n");
        query.append("             0 AS WDF\n");
        query.append("       FROM   ZWKMYE${YEAR}  B \n");
        query.append("      INNER JOIN LSWBZD HB ON HB.LSWBZD_BZBH=B.ZWKMYE_WBBH \n");
        query.append("      INNER JOIN (SELECT  ZWKMZD_KMBH AS SUBJECTCODE,  \n");
        query.append("                          CASE WHEN ZWKMZD_YEFX = 1 THEN 1 ELSE -1 END AS KMORIENT,ZWKMZD_DWBH  \n");
        query.append("                   FROM   ZWKMZD${YEAR}          ) SUBJECT ON SUBJECT.SUBJECTCODE=B.ZWKMYE_KMBH  \n");
        query.append("                  AND (SUBJECT.ZWKMZD_DWBH=' ' OR SUBJECT.ZWKMZD_DWBH='${UNITCODE}') \n");
        query.append("      WHERE    B.ZWKMYE_KJND='${YEAR}' \n");
        query.append("               AND B.ZWKMYE_DWBH='${UNITCODE}' \n");
        query.append("               AND B.ZWKMYE_KJQJ='01' \n");
        query.append("     UNION ALL \n");
        query.append("     SELECT\n");
        query.append("             HB.LSWBZD_BZBH AS CURRENCYCODE,\n");
        query.append("             VOUCHERDETAIL.ZWPZFL_KMBH AS SUBJECTCODE,\n");
        query.append("             SUBJECT.KMORIENT AS ORIENT,\n");
        query.append("             VOUCHER.ZWPZK_KJQJ AS PERIOD,\n");
        query.append("             0 AS NC,\n");
        query.append("             0 AS WNC,\n");
        query.append("             CASE WHEN VOUCHERDETAIL.ZWPZFL_JZFX=1 THEN VOUCHERDETAIL.ZWPZFL_JE ELSE 0 END AS JF,\n");
        query.append("             CASE WHEN VOUCHERDETAIL.ZWPZFL_JZFX=2 THEN VOUCHERDETAIL.ZWPZFL_JE ELSE 0 END AS DF,\n");
        query.append("             CASE WHEN VOUCHERDETAIL.ZWPZFL_JZFX=1 THEN VOUCHERDETAIL.ZWPZFL_WB ELSE 0 END AS WJF,\n");
        query.append("             CASE WHEN VOUCHERDETAIL.ZWPZFL_JZFX=2 THEN VOUCHERDETAIL.ZWPZFL_WB ELSE 0 END AS WDF\n");
        query.append("     FROM\n");
        query.append("             ZWPZK${YEAR} VOUCHER \n");
        query.append("             INNER JOIN ZWPZFL${YEAR} VOUCHERDETAIL ON VOUCHER.ZWPZK_DWBH = VOUCHERDETAIL.ZWPZFL_DWBH AND VOUCHER.ZWPZK_PZNM = VOUCHERDETAIL.ZWPZFL_PZNM AND VOUCHER.ZWPZK_DWBH = VOUCHERDETAIL.ZWPZFL_DWBH\n");
        query.append("             INNER JOIN LSWBZD HB ON VOUCHERDETAIL.ZWPZFL_WBBH = HB.LSWBZD_BZBH\n");
        query.append("             INNER JOIN (SELECT  ZWKMZD_KMBH AS SUBJECTCODE,  \n");
        query.append("                           CASE WHEN ZWKMZD_YEFX = 1 THEN 1 ELSE -1 END AS KMORIENT,ZWKMZD_DWBH  \n");
        query.append("                    FROM   ZWKMZD${YEAR}          ) SUBJECT ON SUBJECT.SUBJECTCODE=VOUCHERDETAIL.ZWPZFL_KMBH  \n");
        query.append("                   AND (SUBJECT.ZWKMZD_DWBH=' ' OR SUBJECT.ZWKMZD_DWBH='${UNITCODE}') \n");
        query.append("    WHERE    1=1 \n");
        query.append("             AND VOUCHER.ZWPZK_WZF = 1\n");
        query.append("             AND VOUCHER.ZWPZK_ZFF = 0\n");
        query.append("             AND VOUCHERDETAIL.ZWPZFL_DWBH = '${UNITCODE}') MASTER\n");
        query.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"B.ZWKMYE_KMBH"));
        query.append(" WHERE 1=1   \n");
        query.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"B.ZWKMYE_KMBH"));
        query.append("    GROUP BY MASTER.SUBJECTCODE,MASTER.CURRENCYCODE,MASTER.ORIENT \n");
        return query.toString();
    }

    private String getIncludeAssSql(BalanceCondition condi) {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT  \n");
        query.append("        MASTER.SUBJECTCODE AS SUBJECTCODE, \n");
        query.append("        MASTER.CURRENCYCODE AS CURRENCYCODE, \n");
        query.append("        MASTER.ORIENT AS ORIENT, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD='00' THEN NC ELSE 0 END) AS NC, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD='00' THEN WNC ELSE 0 END) AS WNC, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' AND MASTER.PERIOD>='${STARTPERIOD}' THEN JF ELSE 0 END) AS JF, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' AND MASTER.PERIOD>='${STARTPERIOD}' THEN WJF ELSE 0 END) AS WJF, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' THEN WJF ELSE 0 END) AS JL, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' THEN WJF ELSE 0 END) AS WJL, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' AND MASTER.PERIOD>='${STARTPERIOD}' THEN DF ELSE 0 END) AS DF, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' AND MASTER.PERIOD>='${STARTPERIOD}' THEN WDF ELSE 0 END) AS WDF, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' THEN WDF ELSE 0 END) AS DL, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' THEN WDF ELSE 0 END) AS WDL, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' THEN NC+JF-DF ELSE 0 END) AS YE, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${ENDPERIOD}' THEN WNC+WJF-WDF ELSE 0 END) AS WYE, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${SUBTRACTENDMONTH}' THEN NC+JF-DF ELSE 0 END) AS C, \n");
        query.append("        SUM(CASE WHEN MASTER.PERIOD<='${SUBTRACTENDMONTH}' THEN WNC+WJF-WDF ELSE 0 END) AS WC \n");
        query.append("        ${MASTERASSFIELD} \n");
        query.append(" FROM  \n");
        query.append("     (SELECT  \n");
        query.append("             HB.LSWBZD_BZBH AS CURRENCYCODE,\n");
        query.append("             B.ZWFZYE_KMBH AS SUBJECTCODE,\n");
        query.append("             '00' AS PERIOD, \n");
        query.append("             SUBJECT.KMORIENT AS ORIENT,\n");
        query.append("             B.ZWFZYE_NCYE AS NC,\n");
        query.append("             B.ZWFZYE_WBNCYE AS WNC,\n");
        query.append("             0 AS JF,\n");
        query.append("             0 AS DF,\n");
        query.append("             0 AS WJF,\n");
        query.append("             0 AS WDF\n");
        query.append("             ${YEASSFIELD} \n");
        query.append("       FROM   ZWFZYE${YEAR}  B \n");
        query.append("      ${YEJOINSQL} \n");
        query.append("      INNER JOIN LSWBZD HB ON HB.LSWBZD_BZBH=B.ZWFZYE_WBBH \n");
        query.append("      INNER JOIN (SELECT  ZWKMZD_KMBH AS SUBJECTCODE,  \n");
        query.append("                          CASE WHEN ZWKMZD_YEFX = 1 THEN 1 ELSE -1 END AS KMORIENT,ZWKMZD_DWBH  \n");
        query.append("                   FROM   ZWKMZD${YEAR}          ) SUBJECT ON SUBJECT.SUBJECTCODE=B.ZWFZYE_KMBH  \n");
        query.append("                  AND (SUBJECT.ZWKMZD_DWBH=' ' OR SUBJECT.ZWKMZD_DWBH='${UNITCODE}') \n");
        query.append("      WHERE    B.ZWFZYE_KJND='${YEAR}' \n");
        query.append("               AND B.ZWFZYE_DWBH='${UNITCODE}' \n");
        query.append("               AND B.ZWFZYE_KJQJ='01' \n");
        query.append("     UNION ALL \n");
        query.append("     SELECT\n");
        query.append("             HB.LSWBZD_BZBH AS CURRENCYCODE,\n");
        query.append("             VOUCHERDETAIL.ZWFZYS_KMBH AS SUBJECTCODE,\n");
        query.append("             VOUCHER.ZWPZK_KJQJ AS PERIOD,\n");
        query.append("             SUBJECT.KMORIENT AS ORIENT,\n");
        query.append("             0 AS NC,\n");
        query.append("             0 AS WNC,\n");
        query.append("             CASE WHEN VOUCHERDETAIL.ZWFZYS_JZFX=1 THEN VOUCHERDETAIL.ZWFZYS_JE ELSE 0 END AS JF,\n");
        query.append("             CASE WHEN VOUCHERDETAIL.ZWFZYS_JZFX=2 THEN VOUCHERDETAIL.ZWFZYS_JE ELSE 0 END AS DF,\n");
        query.append("             CASE WHEN VOUCHERDETAIL.ZWFZYS_JZFX=1 THEN VOUCHERDETAIL.ZWFZYS_WB ELSE 0 END AS WJF,\n");
        query.append("             CASE WHEN VOUCHERDETAIL.ZWFZYS_JZFX=2 THEN VOUCHERDETAIL.ZWFZYS_WB ELSE 0 END AS WDF\n");
        query.append("             ${PZASSFIELD} \n");
        query.append("     FROM\n");
        query.append("             ZWPZK${YEAR} VOUCHER \n");
        query.append("             INNER JOIN ZWFZYS${YEAR} VOUCHERDETAIL ON VOUCHER.ZWPZK_DWBH = VOUCHERDETAIL.ZWFZYS_DWBH AND VOUCHER.ZWPZK_PZNM = VOUCHERDETAIL.ZWFZYS_PZNM AND VOUCHER.ZWPZK_DWBH = VOUCHERDETAIL.ZWFZYS_DWBH\n");
        query.append("             ${PZJOINSQL} \n");
        query.append("             INNER JOIN LSWBZD HB ON VOUCHERDETAIL.ZWFZYS_WBBH = HB.LSWBZD_BZBH\n");
        query.append("             INNER JOIN (SELECT  ZWKMZD_KMBH AS SUBJECTCODE,  \n");
        query.append("                           CASE WHEN ZWKMZD_YEFX = 1 THEN 1 ELSE -1 END AS KMORIENT,ZWKMZD_DWBH  \n");
        query.append("                    FROM   ZWKMZD${YEAR}          ) SUBJECT ON SUBJECT.SUBJECTCODE=VOUCHERDETAIL.ZWFZYS_KMBH  \n");
        query.append("                   AND (SUBJECT.ZWKMZD_DWBH=' ' OR SUBJECT.ZWKMZD_DWBH='${UNITCODE}') \n");
        query.append("    WHERE    1=1\n");
        query.append("             AND VOUCHER.ZWPZK_WZF = 1\n");
        query.append("             AND VOUCHER.ZWPZK_ZFF = 0\n");
        query.append("             AND VOUCHERDETAIL.ZWFZYS_DWBH = '${UNITCODE}') MASTER\n");
        query.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"B.ZWKMYE_KMBH"));
        query.append(" WHERE 1=1   \n");
        query.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"B.ZWKMYE_KMBH"));
        query.append("    GROUP BY MASTER.SUBJECTCODE,MASTER.CURRENCYCODE,MASTER.ORIENT \n");
        query.append("    ${MASTERGROUPFIELD} \n");
        return query.toString();
    }

    private String getNotIncludeAssSql(BalanceCondition condi) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("          HB.LSWBZD_BZBH AS CURRENCYCODE,\n");
        query.append("          B.ZWFZYE_KMBH AS SUBJECTCODE,\n");
        query.append("          SUBJECT.KMORIENT AS ORIENT,\n");
        query.append("          SUM(CASE WHEN B.ZWFZYE_KJQJ='${ENDPERIOD}' THEN B.ZWFZYE_NCYE ELSE 0 END) AS NC,\n");
        query.append("          SUM(CASE WHEN B.ZWFZYE_KJQJ='${ENDPERIOD}' THEN B.ZWFZYE_DQYE ELSE 0 END) AS YE,\n");
        query.append("          SUM(CASE WHEN B.ZWFZYE_KJQJ>='${ENDPERIOD}' AND B.ZWFZYE_KJQJ<='${ENDPERIOD}' THEN B.ZWFZYE_JFFS ELSE 0 END) AS JF,\n");
        query.append("          SUM(CASE WHEN B.ZWFZYE_KJQJ>='${ENDPERIOD}' AND B.ZWFZYE_KJQJ<='${ENDPERIOD}' THEN B.ZWFZYE_DFFS ELSE 0 END) AS DF,\n");
        query.append("          SUM(CASE WHEN B.ZWFZYE_KJQJ='${ENDPERIOD}' THEN B.ZWFZYE_JFLJ ELSE 0 END) AS JL,\n");
        query.append("          SUM(CASE WHEN B.ZWFZYE_KJQJ='${ENDPERIOD}' THEN B.ZWFZYE_DFLJ ELSE 0 END) AS DL,\n");
        if (condi.getEndPeriod() != 1) {
            query.append("SUM(CASE WHEN B.ZWFZYE_KJQJ='${SUBTRACTENDMONTH}' THEN B.ZWFZYE_DQYE ELSE 0 END) AS C, \n");
            query.append("SUM(CASE WHEN B.ZWFZYE_KJQJ='${SUBTRACTENDMONTH}' THEN B.ZWFZYE_WBDQYE ELSE 0 END) AS WC, \n");
        } else {
            query.append("SUM(CASE WHEN B.ZWFZYE_KJQJ='${ENDPERIOD}' THEN B.ZWFZYE_NCYE ELSE 0 END) AS C, \n");
            query.append("SUM(CASE WHEN B.ZWFZYE_KJQJ='${ENDPERIOD}' THEN B.ZWFZYE_WBNCYE ELSE 0 END) AS WC, \n");
        }
        query.append("          SUM(CASE WHEN B.ZWFZYE_KJQJ='${ENDPERIOD}' THEN B.ZWFZYE_WBNCYE ELSE 0 END) AS WNC,\n");
        query.append("          SUM(CASE WHEN B.ZWFZYE_KJQJ='${ENDPERIOD}' THEN B.ZWFZYE_WBDQYE ELSE 0 END) AS WYE,\n");
        query.append("          SUM(CASE WHEN B.ZWFZYE_KJQJ>='${ENDPERIOD}' AND B.ZWFZYE_KJQJ<='${ENDPERIOD}' THEN B.ZWFZYE_WBJFFS ELSE 0 END) AS WJF,\n");
        query.append("          SUM(CASE WHEN B.ZWFZYE_KJQJ>='${ENDPERIOD}' AND B.ZWFZYE_KJQJ<='${ENDPERIOD}' THEN  B.ZWFZYE_WBDFFS ELSE 0 END) AS WDF,\n");
        query.append("          SUM(CASE WHEN B.ZWFZYE_KJQJ='${ENDPERIOD}' THEN B.ZWFZYE_WBJFLJ ELSE 0 END) AS WJL,\n");
        query.append("          SUM(CASE WHEN B.ZWFZYE_KJQJ='${ENDPERIOD}' THEN B.ZWFZYE_WBDFLJ ELSE 0 END) AS WDL\n");
        query.append("          ${YEASSFIELD} \n");
        query.append("  FROM\n");
        query.append("          ZWFZYE${YEAR} B\n");
        query.append("     ${YEJOINSQL}");
        query.append("     INNER JOIN LSWBZD HB ON HB.LSWBZD_BZBH=B.ZWFZYE_WBBH \n");
        query.append("     INNER JOIN (SELECT  ZWKMZD_KMBH AS KMCODE,  \n");
        query.append("                         CASE WHEN ZWKMZD_YEFX = 1 THEN 1 ELSE -1 END AS KMORIENT,ZWKMZD_DWBH  \n");
        query.append("                  FROM   ZWKMZD${YEAR}          ) SUBJECT ON SUBJECT.KMCODE=B.ZWFZYE_KMBH  \n");
        query.append("                 AND (ZWKMZD_DWBH=' ' OR ZWKMZD_DWBH='${UNITCODE}') \n");
        query.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"B.ZWKMYE_KMBH"));
        query.append(" WHERE 1=1   \n");
        query.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"B.ZWKMYE_KMBH"));
        query.append("          AND  B.ZWFZYE_DWBH='${UNITCODE}'\n");
        query.append("          AND  B.ZWFZYE_KJND = '${YEAR}'\n");
        query.append(" GROUP BY HB.LSWBZD_BZBH,B.ZWFZYE_KMBH,SUBJECT.KMORIENT \n");
        query.append(" ${YEGROUPFIELD} \n");
        return query.toString();
    }
}

