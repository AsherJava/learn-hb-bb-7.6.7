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
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nbrj_n9.fetch;

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
import com.jiuqi.bde.plugin.nbrj_n9.util.AssistPojo;
import com.jiuqi.bde.plugin.nbrj_n9.util.NbrjN9FetchUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
class BdeNbrjN9BalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;

    BdeNbrjN9BalanceDataProvider() {
    }

    FetchData queryData(BalanceCondition condi) {
        IDbSqlHandler SQL_HANDLER = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT MASTER.SUBJECTCODE AS SUBJECTCODE, \n");
        sql.append("        ORIENT AS ORIENT, \n");
        sql.append(SQL_HANDLER.nullToValue("MASTER.CURRENCYCODE", "'CNY'")).append(" AS CURRENCYCODE, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.JF ELSE 0 END) AS JF, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.DF ELSE 0 END) AS DF, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.YE ELSE 0 END) AS YE, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD = 0 THEN MASTER.NC ELSE 0 END) AS NC, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<${STARTPERIOD} THEN MASTER.C ELSE 0 END) AS C, \n");
        sql.append("        SUM(MASTER.JF) AS JL, \n");
        sql.append("        SUM(MASTER.DF) AS DL, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.WJF ELSE 0 END) AS WJF, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.WDF ELSE 0 END) AS WDF, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<=${ENDPERIOD} THEN WYE ELSE 0 END) AS WYE, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD=0 THEN MASTER.WNC ELSE 0 END) AS WNC, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<${STARTPERIOD} THEN MASTER.WC ELSE 0 END)AS WC, \n");
        sql.append("        SUM(WJF)AS WJL, \n");
        sql.append("        SUM(WDF)AS WDL \n");
        sql.append("        ${ASSFIELDWRAPPER} \n");
        sql.append(" FROM ( \n");
        sql.append("    SELECT SUBJECT.ACODE as SUBJECTCODE,  \n");
        sql.append("           SUBJECT.DC AS ORIENT, \n");
        sql.append("           0 AS JF, \n");
        sql.append("           0 AS DF,\n");
        sql.append("           SUM(").append(SQL_HANDLER.nullToValue("B.SCYBALANCE", "0")).append("*SUBJECT.DC) AS YE,\n");
        sql.append("           SUM(").append(SQL_HANDLER.nullToValue("B.SCYBALANCE", "0")).append("*SUBJECT.DC) AS NC,\n");
        sql.append("           0 AS WJF,\n");
        sql.append("           0 AS WDF,\n");
        sql.append("           SUM(").append(SQL_HANDLER.nullToValue("B.FCYBALANCE", "0")).append("*SUBJECT.DC) AS WYE,\n");
        sql.append("           SUM(").append(SQL_HANDLER.nullToValue("B.FCYBALANCE", "0")).append("*SUBJECT.DC) AS WNC,\n");
        sql.append("           SUM(").append(SQL_HANDLER.nullToValue("B.SCYBALANCE", "0")).append("*SUBJECT.DC) AS C,\n");
        sql.append("           SUM(").append(SQL_HANDLER.nullToValue("B.FCYBALANCE", "0")).append("*SUBJECT.DC) AS WC,\n");
        sql.append("           0 AS PERIOD, \n");
        sql.append("           B.CODE04 AS CURRENCYCODE \n");
        sql.append("           ${ASSFIELD} \n");
        sql.append("    FROM  BALANCE B LEFT JOIN ACODE SUBJECT ON SUBJECT.ACODE = B.CODE00  \n");
        sql.append("         ${BALANCEASSTABLE} \n");
        sql.append("    WHERE  1 = 1  \n");
        sql.append(NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "B."));
        sql.append("\t        AND B.YEAR = ${YEAR}  \n");
        sql.append("            AND B.MONTH = (SELECT MIN(MONTH)  \n");
        sql.append("                           FROM MONTHLOGB  \n");
        sql.append("                     \t  WHERE 1 = 1 " + NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "") + " AND YEAR = ${YEAR})  \n");
        sql.append("    GROUP BY \n");
        sql.append("\t    SUBJECT.ACODE, \n");
        sql.append("        SUBJECT.DC, \n");
        sql.append("        B.CODE04 \n");
        sql.append("        ${ASSGROUPFIELD} \n");
        sql.append("    UNION ALL   \n");
        sql.append("    SELECT SUBJECT.ACODE as SUBJECTCODE,\n");
        sql.append("           SUBJECT.DC AS ORIENT,  \n");
        sql.append("           SUM(CASE WHEN VI.VDC = 1 THEN ").append(SQL_HANDLER.nullToValue("VI.SCY", "0")).append("  ELSE 0 END) JF, \n");
        sql.append("           SUM(CASE WHEN VI.VDC = -1 THEN ").append(SQL_HANDLER.nullToValue("VI.SCY", "0")).append(" ELSE 0 END) DF, \n");
        sql.append("           SUM(").append(SQL_HANDLER.nullToValue("VI.SCY", "0")).append(") * VI.VDC  AS YE, \n");
        sql.append("           0 AS NC, \n");
        sql.append("           SUM(CASE WHEN VI.VDC = 1 THEN ").append(SQL_HANDLER.nullToValue("VI.FCY", "0")).append(" ELSE 0 END) WJF,\n");
        sql.append("           SUM(CASE WHEN VI.VDC = -1 THEN ").append(SQL_HANDLER.nullToValue("VI.FCY", "0")).append(" ELSE 0 END) WDF,\n");
        sql.append("           SUM(").append(SQL_HANDLER.nullToValue("VI.FCY", "0")).append(") * VI.VDC AS WYE,\n");
        sql.append("           0 AS WNC,\n");
        sql.append("           SUM(").append(SQL_HANDLER.nullToValue("VI.SCY", "0")).append(") * VI.VDC AS C,\n");
        sql.append("           SUM(").append(SQL_HANDLER.nullToValue("VI.FCY", "0")).append(") * VI.VDC AS WC, \n");
        sql.append("           VI.MONTH AS PERIOD, \n");
        sql.append("           VI.CODE04 AS CURRENCYCODE \n");
        sql.append("           ${ASSFIELD} \n");
        sql.append("    FROM IVOUCHER VI LEFT JOIN ACODE SUBJECT ON SUBJECT.ACODE = VI.CODE00 \n");
        sql.append("         ${VOUCHERASSTABLE} \n");
        sql.append("    WHERE  1 = 1  \n");
        sql.append(NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "VI."));
        sql.append("          AND VI.YEAR = ${YEAR} \n");
        sql.append("          AND VI.MONTH > (SELECT MIN(MONTH) \n");
        sql.append("                     \t\tFROM MONTHLOGB  \n");
        sql.append("                     \t  WHERE 1 = 1 " + NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "") + " AND YEAR = ${YEAR})  \n");
        sql.append("    GROUP BY   \n");
        sql.append("\t    SUBJECT.ACODE, \n");
        sql.append("        SUBJECT.DC, \n");
        sql.append("        VI.CODE04, \n");
        sql.append("        VI.VDC, \n");
        sql.append("        VI.MONTH \n");
        sql.append("        ${ASSGROUPFIELD} \n");
        sql.append("    ) MASTER  \n");
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"MASTER.SUBJECTCODE"));
        sql.append(" WHERE MASTER.SUBJECTCODE is NOT NULL  \n");
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"MASTER.SUBJECTCODE"));
        sql.append(" GROUP BY MASTER.CURRENCYCODE, \n");
        sql.append("          MASTER.SUBJECTCODE, \n");
        sql.append("          MASTER.ORIENT \n");
        sql.append("          ${ASSGROUPFIELDWARPPER} \n");
        Variable variable = new Variable();
        StringBuilder assField = new StringBuilder();
        StringBuilder assGroupField = new StringBuilder();
        StringBuilder balanceAssTable = new StringBuilder();
        StringBuilder voucherAssTable = new StringBuilder();
        StringBuilder assFieldWrapper = new StringBuilder();
        StringBuilder assGroupFieldWarpper = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            balanceAssTable.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=%3$S.%4$s", assistMapping.getAssistSql(), assistMapping.getAssistCode(), "B", ((AssistPojo)assistMapping.getAccountAssist()).getAssistField()));
            voucherAssTable.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=%3$S.%4$s", assistMapping.getAssistSql(), assistMapping.getAssistCode(), "VI", ((AssistPojo)assistMapping.getAccountAssist()).getAssistField()));
            assField.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            assGroupField.append(String.format(",%1$s.CODE", assistMapping.getAssistCode()));
            assFieldWrapper.append(String.format(",MASTER.%1$s AS %1$s", assistMapping.getAssistCode()));
            assGroupFieldWarpper.append(String.format(",MASTER.%1$s", assistMapping.getAssistCode()));
        }
        variable.put("ASSFIELD", assField.toString());
        variable.put("ASSFIELDWRAPPER", assFieldWrapper.toString());
        variable.put("ASSGROUPFIELDWARPPER", assGroupFieldWarpper.toString());
        variable.put("ASSGROUPFIELD", assGroupField.toString());
        variable.put("BALANCEASSTABLE", balanceAssTable.toString());
        variable.put("VOUCHERASSTABLE", voucherAssTable.toString());
        variable.put("STARTPERIOD", condi.getStartPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u79d1\u76ee\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)condi, (String)lastSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), lastSql, new Object[0], (ResultSetExtractor)new FetchDataExtractor());
    }
}

