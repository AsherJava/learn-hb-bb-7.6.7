/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.util.TempTableUtils
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.k3_cloud.fetch;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.k3_cloud.util.AssistPojo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BdeK3CloudBalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;

    protected FetchData queryData(BalanceCondition condi) {
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder yeAssJoinSql = new StringBuilder();
        StringBuilder pzAssJoinSql = new StringBuilder();
        StringBuilder inAssFieldGroup = new StringBuilder();
        StringBuilder inAssField = new StringBuilder();
        StringBuilder outAssFieldGroup = new StringBuilder();
        StringBuilder outAssField = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            if (yeAssJoinSql.length() == 0) {
                yeAssJoinSql.append(" INNER JOIN T_BD_FLEXITEMDETAILV FLEXVALUE ON B.FDETAILID =FLEXVALUE.FID  \n");
                pzAssJoinSql.append(" INNER JOIN T_BD_FLEXITEMDETAILV FLEXVALUE ON VOUCHERENTRY.FDETAILID =FLEXVALUE.FID  \n");
            }
            yeAssJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=FLEXVALUE.%3$s ", assistMapping.getAssistSql(), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
            pzAssJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=FLEXVALUE.%3$s ", assistMapping.getAssistSql(), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
            inAssField.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            outAssField.append(String.format(",MASTER.%1$s AS %1$s", assistMapping.getAssistCode()));
            inAssFieldGroup.append(String.format(",%1$s.CODE", assistMapping.getAssistCode()));
            outAssFieldGroup.append(String.format(",MASTER.%1$s", assistMapping.getAssistCode()));
        }
        String sql = condi.getIncludeUncharged() != false ? this.getIncludeSql(condi) : this.getNotIncludeSql(condi);
        Variable variable = new Variable();
        variable.put("STARTPERIOD", condi.getStartPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("BALANCEFDETAILIDCONDI", CollectionUtils.isEmpty((Collection)assistMappingList) ? "AND B.FDetailID = 0" : "AND B.FDetailID > 0 \n");
        variable.put("YEASSJOINSQL", yeAssJoinSql.toString());
        variable.put("PZASSJOINSQL", pzAssJoinSql.toString());
        variable.put("INASSFIELDGROUP", inAssFieldGroup.toString());
        variable.put("INASSFIELD", inAssField.toString());
        variable.put("OUTASSFIELDGROUP", outAssFieldGroup.toString());
        variable.put("OUTASSFIELD", outAssField.toString());
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u79d1\u76ee\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)condi, (String)lastSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), lastSql, new Object[0], (ResultSetExtractor)new FetchDataExtractor());
    }

    private String getIncludeSql(BalanceCondition condi) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT MASTER.SUBJECTCODE AS SUBJECTCODE,   \n");
        sql.append("        MASTER.CURRENCYCODE AS CURRENCYCODE,  \n");
        sql.append("        MASTER.ORIENT AS ORIENT,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.JF ELSE 0 END) AS JF,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>0 AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.JF ELSE 0 END) AS JL,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.DF ELSE 0 END) AS DF,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>0 AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.DF ELSE 0 END) AS DL,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD=0 THEN MASTER.JF-MASTER.DF ELSE 0 END) AS NC,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD=0 THEN MASTER.WJF-MASTER.WDF ELSE 0 END) AS WNC,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<${STARTPERIOD} THEN MASTER.JF-MASTER.DF ELSE 0 END) AS C,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<${STARTPERIOD} THEN MASTER.WJF-MASTER.WDF ELSE 0 END) AS WC,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.JF-MASTER.DF  ELSE 0 END) AS YE,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.WJF-MASTER.WDF  ELSE 0 END) AS WYE,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.WJF ELSE 0 END) AS WJF,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>0 AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.WJF ELSE 0 END) AS WJL,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.WDF ELSE 0 END) AS WDF,  \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>0 AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.WDF ELSE 0 END) AS WDL  \n");
        sql.append("        ${OUTASSFIELD} \n");
        sql.append("  FROM  \n");
        sql.append("        (SELECT CURRENCY.FCODE AS CURRENCYCODE,  \n");
        sql.append("               SUBJECT.FNUMBER AS SUBJECTCODE,  \n");
        sql.append("               SUBJECT.FDC AS ORIENT,  \n");
        sql.append("               0 AS PERIOD,  \n");
        sql.append("               CASE WHEN SUBJECT.FDC = 1 THEN B.FBEGINBALANCE ELSE 0 END AS JF,  \n");
        sql.append("               CASE WHEN SUBJECT.FDC = -1 THEN B.FBEGINBALANCE*-1 ELSE 0 END AS DF,  \n");
        sql.append("               CASE WHEN SUBJECT.FDC = 1 THEN B.FBEGINBALANCEFOR ELSE 0 END AS WJF,  \n");
        sql.append("               CASE WHEN SUBJECT.FDC = -1 THEN B.FBEGINBALANCEFOR*-1 ELSE 0 END AS WDF  \n");
        sql.append("               ${INASSFIELD}");
        sql.append("          FROM T_GL_BALANCE B  \n");
        sql.append("         INNER JOIN T_BD_ACCOUNT SUBJECT  \n");
        sql.append("            ON B.FACCOUNTID = SUBJECT.FACCTID  \n");
        sql.append("         INNER JOIN T_BD_CURRENCY CURRENCY  \n");
        sql.append("            ON B.FCURRENCYID = CURRENCY.FCURRENCYID  \n");
        sql.append("         ${YEASSJOINSQL} \n");
        sql.append("         WHERE 1 = 1  \n");
        sql.append("           ${BALANCEFDETAILIDCONDI}  \n");
        sql.append("           AND B.FACCOUNTBOOKID = ${UNITCODE}  \n");
        sql.append("           AND SUBJECT.FISDETAIL = 1  \n");
        sql.append("           AND B.FYEAR = ${YEAR}  \n");
        sql.append("           AND B.FPERIOD = 1  \n");
        sql.append("        UNION ALL  \n");
        sql.append("        SELECT CURRENCY.FCODE AS CURRENCYCODE,  \n");
        sql.append("               SUBJECT.FNUMBER AS SUBJECTCODE,  \n");
        sql.append("               SUBJECT.FDC AS ORIENT,  \n");
        sql.append("               VOUCHER.FPERIOD AS PERIOD,  \n");
        sql.append("               CASE WHEN VOUCHERENTRY.FDC = 1 THEN VOUCHERENTRY.FAMOUNT ELSE 0 END AS JF,  \n");
        sql.append("               CASE WHEN VOUCHERENTRY.FDC = -1 THEN VOUCHERENTRY.FAMOUNT ELSE 0 END AS DF,  \n");
        sql.append("               CASE WHEN VOUCHERENTRY.FDC = 1 THEN VOUCHERENTRY.FAMOUNTFOR ELSE 0 END AS WJF,  \n");
        sql.append("               CASE WHEN VOUCHERENTRY.FDC = -1 THEN VOUCHERENTRY.FAMOUNTFOR ELSE 0 END AS WDF  \n");
        sql.append("               ${INASSFIELD} \n");
        sql.append("          FROM T_GL_VOUCHER VOUCHER  \n");
        sql.append("         INNER JOIN T_GL_VOUCHERENTRY VOUCHERENTRY  \n");
        sql.append("            ON VOUCHERENTRY.FVOUCHERID = VOUCHER.FVOUCHERID  \n");
        sql.append("         INNER JOIN T_BD_CURRENCY CURRENCY  \n");
        sql.append("            ON VOUCHERENTRY.FCURRENCYID = CURRENCY.FCURRENCYID  \n");
        sql.append("         INNER JOIN T_BD_ACCOUNT SUBJECT  \n");
        sql.append("            ON VOUCHERENTRY.FACCOUNTID = SUBJECT.FACCTID  \n");
        sql.append("         ${PZASSJOINSQL} \n");
        sql.append("         WHERE 1 = 1  \n");
        sql.append("           AND SUBJECT.FISDETAIL = 1  \n");
        sql.append("           AND VOUCHER.FACCOUNTBOOKID = ${UNITCODE}  \n");
        sql.append("           AND VOUCHER.FINVALID = '0'  \n");
        sql.append("           AND VOUCHER.FPERIOD <= ${ENDPERIOD}  \n");
        sql.append("           AND VOUCHER.FYEAR = ${YEAR} ) MASTER \n");
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"MASTER.SUBJECTCODE"));
        sql.append(" WHERE 1 = 1 \n");
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"MASTER.SUBJECTCODE"));
        sql.append(" GROUP BY MASTER.CURRENCYCODE, \n");
        sql.append("          MASTER.SUBJECTCODE, \n");
        sql.append("          MASTER.ORIENT \n");
        sql.append("          ${OUTASSFIELDGROUP} \n");
        return sql.toString();
    }

    private String getNotIncludeSql(BalanceCondition condi) {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT  CURRENCY.FCODE AS CURRENCYCODE,\n");
        query.append("         SUBJECT.FNUMBER AS SUBJECTCODE,\n");
        query.append("         SUBJECT.FDC AS ORIENT,\n");
        query.append("         SUM(CASE WHEN B.FPERIOD=1 THEN B.FBEGINBALANCEFOR ELSE 0 END) AS WNC,\n");
        query.append("         SUM(CASE WHEN B.FPERIOD=1 THEN B.FBEGINBALANCE ELSE 0 END) AS NC,\n");
        query.append("         SUM(CASE WHEN B.FPERIOD=${STARTPERIOD} THEN B.FBEGINBALANCEFOR ELSE 0 END) AS WC,\n");
        query.append("         SUM(CASE WHEN B.FPERIOD=${STARTPERIOD} THEN B.FBEGINBALANCE ELSE 0 END) AS C,\n");
        query.append("         SUM(CASE WHEN B.FPERIOD>=${STARTPERIOD} AND B.FPERIOD<=${ENDPERIOD} THEN B.FDEBITFOR ELSE 0 END) AS WJF,\n");
        query.append("         SUM(CASE WHEN B.FPERIOD>=${STARTPERIOD} AND B.FPERIOD<=${ENDPERIOD} THEN B.FDEBIT ELSE 0 END) AS JF,\n");
        query.append("         SUM(CASE WHEN B.FPERIOD>=${STARTPERIOD} AND B.FPERIOD<=${ENDPERIOD} THEN B.FCREDITFOR ELSE 0 END) AS WDF,\n");
        query.append("         SUM(CASE WHEN B.FPERIOD>=${STARTPERIOD} AND B.FPERIOD<=${ENDPERIOD} THEN B.FCREDIT ELSE 0 END) AS DF,\n");
        query.append("         SUM(CASE WHEN B.FPERIOD=${ENDPERIOD} THEN B.FYTDDEBITFOR ELSE 0 END) AS WJL,\n");
        query.append("         SUM(CASE WHEN B.FPERIOD=${ENDPERIOD} THEN B.FYTDDEBIT ELSE 0 END) AS JL,\n");
        query.append("         SUM(CASE WHEN B.FPERIOD=${ENDPERIOD} THEN B.FYTDCREDITFOR ELSE 0 END) AS WDL,\n");
        query.append("         SUM(CASE WHEN B.FPERIOD=${ENDPERIOD} THEN B.FYTDCREDIT ELSE 0 END) AS DL,\n");
        query.append("         SUM(CASE WHEN B.FPERIOD=${ENDPERIOD} THEN B.FENDBALANCEFOR ELSE 0 END) AS WYE,\n");
        query.append("         SUM(CASE WHEN B.FPERIOD=${ENDPERIOD} THEN B.FENDBALANCE ELSE 0 END) AS YE\n");
        query.append("         ${INASSFIELD}");
        query.append(" FROM    T_GL_BALANCE B  \n");
        query.append(" INNER JOIN T_BD_ACCOUNT SUBJECT ON B.FACCOUNTID = SUBJECT.FACCTID\n");
        query.append(" INNER JOIN T_BD_CURRENCY CURRENCY ON B.FCURRENCYID =CURRENCY.FCURRENCYID\n");
        query.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"SUBJECT.FNUMBER"));
        query.append(" ${YEASSJOINSQL} \n");
        query.append(" WHERE 1 = 1 \n");
        query.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"SUBJECT.FNUMBER"));
        query.append("         ${BALANCEFDETAILIDCONDI}  \n");
        query.append("         AND B.FACCOUNTBOOKID=${UNITCODE}\n");
        query.append("         AND B.FYEAR=${YEAR}\n");
        query.append("         AND SUBJECT.FISDETAIL =1 \n");
        query.append(" GROUP BY CURRENCY.FCODE,SUBJECT.FNUMBER,SUBJECT.FDC\n");
        query.append("          ${INASSFIELDGROUP}\n");
        return query.toString();
    }
}

