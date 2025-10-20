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
 *  com.jiuqi.bde.common.util.Pair
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.ebs_r12.fetch;

import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.common.util.Pair;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.ebs_r12.util.EbsR12FetchUtil;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
class BdeEbsR12BalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;

    BdeEbsR12BalanceDataProvider() {
    }

    FetchData queryData(BalanceCondition condi) {
        IDbSqlHandler SQL_HANDLER = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        Variable variable = new Variable();
        variable.put("startPeriod", condi.getAcctYear().toString() + "-01");
        variable.put("endPeriod", EbsR12FetchUtil.formatPeriod(condi.getAcctYear(), condi.getEndPeriod()));
        variable.put("adjustmentPeriod", EbsR12FetchUtil.buildAdjustmentPeriod(condi.getAcctYear()));
        StringBuilder assistField = new StringBuilder();
        StringBuilder assistGroup = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            assistField.append(String.format("comb.%1$s AS %2$s,", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            assistGroup.append(String.format(", comb .%1$s", assistMapping.getAccountAssistCode()));
        }
        variable.put("assistField", assistField.toString());
        variable.put("assistGroup", assistGroup.toString());
        Pair<String, List<Object>> sqlPair = this.getQueryDataSql(condi, SQL_HANDLER);
        String lastSql = VariableParseUtil.parse((String)((String)sqlPair.getFirst()), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u79d1\u76ee\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)condi, (String)lastSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), lastSql, ((List)sqlPair.getSecond()).toArray(), (ResultSetExtractor)new FetchDataExtractor());
    }

    private Pair<String, List<Object>> getQueryDataSql(BalanceCondition condi, IDbSqlHandler sqlHandler) {
        LinkedList<String> sqlParams = new LinkedList<String>();
        StringBuilder sql = new StringBuilder(16384);
        sql.append("SELECT COMB.SEGMENT3 AS SUBJECTCODE,  \n");
        sql.append("       MASTER.CURRENCYCODE AS CURRENCYCODE,  \n");
        sql.append("       1 AS ORIENT,  \n");
        sql.append("       ${assistField}  \n");
        sql.append(sqlHandler.nullToValue("SUM(NC)", "0")).append(" AS NC, \n");
        sql.append(sqlHandler.nullToValue("SUM(C)", "0")).append(" AS C, \n");
        sql.append(sqlHandler.nullToValue("SUM(JF)", "0")).append(" AS JF, \n");
        sql.append(sqlHandler.nullToValue("SUM(DF)", "0")).append(" AS DF, \n");
        sql.append(sqlHandler.nullToValue("SUM(JL)", "0")).append(" AS JL, \n");
        sql.append(sqlHandler.nullToValue("SUM(DL)", "0")).append(" AS DL, \n");
        sql.append(sqlHandler.nullToValue("SUM(YE)", "0")).append(" AS YE, \n");
        sql.append(sqlHandler.nullToValue("SUM(WNC)", "0")).append(" AS WNC, \n");
        sql.append(sqlHandler.nullToValue("SUM(WC)", "0")).append(" AS WC, \n");
        sql.append(sqlHandler.nullToValue("SUM(WJF)", "0")).append(" AS WJF, \n");
        sql.append(sqlHandler.nullToValue("SUM(WDF)", "0")).append(" AS WDF, \n");
        sql.append(sqlHandler.nullToValue("SUM(WJL)", "0")).append(" AS WJL, \n");
        sql.append(sqlHandler.nullToValue("SUM(WDL)", "0")).append(" AS WDL, \n");
        sql.append(sqlHandler.nullToValue("SUM(WYE)", "0")).append(" AS WYE \n");
        sql.append("  FROM( \n");
        sql.append("    SELECT BALA.CODE_COMBINATION_ID COMBID ,\n");
        sql.append("           P.CLOSING_STATUS ADJSTATUE,\n");
        sql.append("           P.PERIOD_NAME endPeriod,\n");
        sql.append("           BALA.CURRENCY_CODE CURRENCYCODE, \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,8) IN (${adjustmentPeriod}) \n");
        sql.append("                     THEN 0\n");
        sql.append("                     WHEN SUBSTR(BALA.PERIOD_NAME,1,7) = '${startPeriod}'\n");
        sql.append("                     THEN BALA.BEGIN_BALANCE_DR_BEQ - BALA.BEGIN_BALANCE_CR_BEQ  \n");
        sql.append("                     ELSE 0 END) NC, \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,8) IN (${adjustmentPeriod}) \n");
        sql.append("                     THEN 0\n");
        sql.append("                     WHEN SUBSTR(BALA.PERIOD_NAME,1,7) = '${endPeriod}' \n");
        sql.append("                     THEN BALA.BEGIN_BALANCE_DR_BEQ - BALA.BEGIN_BALANCE_CR_BEQ  \n");
        sql.append("                     ELSE 0 END) C,\n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,7) = '${endPeriod}' \n");
        sql.append("                     THEN BALA.PERIOD_NET_DR_BEQ  \n");
        sql.append("                     ELSE 0 END) JF, \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,7) = '${endPeriod}' \n");
        sql.append("                     THEN BALA.PERIOD_NET_CR_BEQ  \n");
        sql.append("                     ELSE 0 END) DF, \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,8) IN (${adjustmentPeriod}) \n");
        sql.append("                     THEN 0\n");
        sql.append("                     WHEN SUBSTR(BALA.PERIOD_NAME,1,7) = '${endPeriod}' \n");
        sql.append("                     THEN BALA.BEGIN_BALANCE_DR_BEQ  \n");
        sql.append("                     ELSE 0 END) CJ,  \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,8) IN (${adjustmentPeriod}) \n");
        sql.append("                     THEN 0\n");
        sql.append("                     WHEN SUBSTR(BALA.PERIOD_NAME,1,7) = '${endPeriod}' \n");
        sql.append("                     THEN BALA.BEGIN_BALANCE_CR_BEQ  \n");
        sql.append("                     ELSE 0 END) CD, \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,7) >= '${startPeriod}' \n");
        sql.append("                     THEN BALA.PERIOD_NET_DR_BEQ  \n");
        sql.append("                     ELSE 0 END) JL, \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,7) >= '${startPeriod}' \n");
        sql.append("                     THEN BALA.PERIOD_NET_CR_BEQ  \n");
        sql.append("                     ELSE 0 END) DL, \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,8) IN (${adjustmentPeriod}) AND SUBSTR(BALA.PERIOD_NAME,1,7) = '${endPeriod}' \n");
        sql.append("                     THEN BALA.PERIOD_NET_DR_BEQ - BALA.PERIOD_NET_CR_BEQ \n");
        sql.append("                     WHEN SUBSTR(BALA.PERIOD_NAME,1,7) = '${endPeriod}' \n");
        sql.append("                     THEN BALA.BEGIN_BALANCE_DR_BEQ - BALA.BEGIN_BALANCE_CR_BEQ + BALA.PERIOD_NET_DR_BEQ - BALA.PERIOD_NET_CR_BEQ  \n");
        sql.append("                     ELSE 0 END) YE, \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,8) IN (${adjustmentPeriod}) \n");
        sql.append("                     THEN 0\n");
        sql.append("                     WHEN SUBSTR(BALA.PERIOD_NAME,1,7) = '${startPeriod}' THEN BALA.BEGIN_BALANCE_DR - BALA.BEGIN_BALANCE_CR  \n");
        sql.append("                     ELSE 0 END) WNC, \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,8) IN (${adjustmentPeriod}) \n");
        sql.append("                     THEN 0\n");
        sql.append("                     WHEN SUBSTR(BALA.PERIOD_NAME,1,7) = '${endPeriod}' THEN BALA.BEGIN_BALANCE_DR - BALA.BEGIN_BALANCE_CR  \n");
        sql.append("                     ELSE 0 END) WC,\n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,7) = '${endPeriod}' THEN BALA.PERIOD_NET_DR  \n");
        sql.append("                     ELSE 0 END) WJF, \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,7) = '${endPeriod}' THEN BALA.PERIOD_NET_CR  \n");
        sql.append("                     ELSE 0 END) WDF, \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,8) IN (${adjustmentPeriod}) \n");
        sql.append("                     THEN 0\n");
        sql.append("                     WHEN SUBSTR(BALA.PERIOD_NAME,1,7) = '${endPeriod}' \n");
        sql.append("                     THEN BALA.BEGIN_BALANCE_DR  \n");
        sql.append("                     ELSE 0 END) WJC,  \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,8) IN (${adjustmentPeriod}) \n");
        sql.append("                     THEN 0\n");
        sql.append("                     WHEN SUBSTR(BALA.PERIOD_NAME,1,7) = '${endPeriod}' \n");
        sql.append("                     THEN BALA.BEGIN_BALANCE_CR  \n");
        sql.append("                     ELSE 0 END) WDC, \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,7) >= '${startPeriod}' THEN BALA.PERIOD_NET_DR  \n");
        sql.append("                     ELSE 0 END) WJL, \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,7) >= '${startPeriod}' THEN BALA.PERIOD_NET_CR  \n");
        sql.append("                     ELSE 0 END) WDL, \n");
        sql.append("               (CASE WHEN SUBSTR(BALA.PERIOD_NAME,1,8) IN (${adjustmentPeriod}) \n");
        sql.append("                     THEN BALA.PERIOD_NET_DR - BALA.PERIOD_NET_CR \n");
        sql.append("                     WHEN SUBSTR(BALA.PERIOD_NAME,1,7) = '${endPeriod}' \n");
        sql.append("                     THEN BALA.BEGIN_BALANCE_DR - BALA.BEGIN_BALANCE_CR + BALA.PERIOD_NET_DR - BALA.PERIOD_NET_CR  \n");
        sql.append("                     ELSE 0 END) WYE \n");
        sql.append("     FROM GL_BALANCES BALA, \n");
        sql.append("          GL_LEDGERS ACCT, \n");
        sql.append("          GL_PERIOD_STATUSES P \n");
        sql.append("    WHERE BALA.LEDGER_ID = ACCT.LEDGER_ID   \n");
        sql.append("      AND BALA.LEDGER_ID = P.SET_OF_BOOKS_ID AND BALA.PERIOD_NAME = P.PERIOD_NAME  \n");
        sql.append("      AND P.APPLICATION_ID = '101' \n");
        sql.append("      AND ACCT.LEDGER_ID = ?  \n");
        sqlParams.add(condi.getOrgMapping().getAcctBookCode());
        sql.append("      AND SUBSTR(BALA.PERIOD_NAME,1,7) >= '${startPeriod}' \n");
        sql.append("      AND SUBSTR(BALA.PERIOD_NAME,1,7) <= '${endPeriod}' \n");
        sql.append("      AND BALA.ACTUAL_FLAG = 'A' \n");
        if (Boolean.TRUE.equals(condi.isIncludeUncharged())) {
            Pair<String, List<Object>> unChargedSqlPair = this.getUnChargedSql(condi.getOrgMapping().getAcctBookCode());
            sql.append((String)unChargedSqlPair.getFirst());
            sqlParams.addAll((Collection)unChargedSqlPair.getSecond());
        }
        sql.append(" ) MASTER ,GL_CODE_COMBINATIONS COMB\n");
        sql.append(" WHERE MASTER.COMBID = COMB.CODE_COMBINATION_ID \n");
        sql.append("  AND COMB.SUMMARY_FLAG = 'N'  \n");
        sql.append("  AND COMB.SEGMENT1 = ?   \n");
        sqlParams.add(condi.getOrgMapping().getAcctOrgCode());
        sql.append(" GROUP BY COMB.SEGMENT3, MASTER.CURRENCYCODE ${assistGroup}\n");
        return new Pair((Object)sql.toString(), sqlParams);
    }

    private Pair<String, List<Object>> getUnChargedSql(String accountBookCode) {
        LinkedList<String> sqlParams = new LinkedList<String>();
        StringBuilder sql = new StringBuilder(4096);
        sql.append(" UNION ALL \n");
        sql.append("    SELECT PZFL.CODE_COMBINATION_ID COMBID , \n");
        sql.append("           P.CLOSING_STATUS ADJSTATUE, \n");
        sql.append("           P.PERIOD_NAME endPeriod,   \n");
        sql.append("           PZ.CURRENCY_CODE CURRENCYCODE,\n");
        sql.append("       0 NC, \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) = '${startPeriod}' THEN 0 \n");
        sql.append("             WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) < '${endPeriod}' \n");
        sql.append("             THEN NVL(PZFL.ACCOUNTED_DR,0) - NVL(PZFL.ACCOUNTED_CR,0)  \n");
        sql.append("             ELSE 0 END) C, \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) = '${endPeriod}'  \n");
        sql.append("             THEN NVL(PZFL.ACCOUNTED_DR,0)  \n");
        sql.append("             ELSE 0 END) JF, \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) = '${endPeriod}'  \n");
        sql.append("             THEN NVL(PZFL.ACCOUNTED_CR,0)  \n");
        sql.append("             ELSE 0 END) DF,  \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) < '${endPeriod}'  \n");
        sql.append("             THEN NVL(PZFL.ACCOUNTED_DR,0)  \n");
        sql.append("             ELSE 0 END) CJ, \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) < '${endPeriod}'  \n");
        sql.append("             THEN NVL(PZFL.ACCOUNTED_CR,0)  \n");
        sql.append("             ELSE 0 END) CD, \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) <= '${endPeriod}'  \n");
        sql.append("             THEN NVL(PZFL.ACCOUNTED_DR,0)  \n");
        sql.append("             ELSE 0 END) JL, \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) <= '${endPeriod}'  \n");
        sql.append("             THEN NVL(PZFL.ACCOUNTED_CR,0)  \n");
        sql.append("             ELSE 0 END) DL,  \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) <= '${endPeriod}'  \n");
        sql.append("             THEN NVL(PZFL.ACCOUNTED_DR,0) - NVL(PZFL.ACCOUNTED_CR,0)  \n");
        sql.append("             ELSE 0 END) YE, \n");
        sql.append("       0 WNC, \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) = '${startPeriod}' THEN 0 \n");
        sql.append("             WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) < '${endPeriod}' THEN NVL(PZFL.ENTERED_DR,0) - NVL(PZFL.ENTERED_CR,0)  \n");
        sql.append("             ELSE 0 END) WC, \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) = '${endPeriod}'  THEN NVL(PZFL.ENTERED_DR,0)  \n");
        sql.append("             ELSE 0 END) WJF, \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) = '${endPeriod}'  THEN NVL(PZFL.ENTERED_CR,0)  \n");
        sql.append("             ELSE 0 END) WDF,  \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) < '${endPeriod}'  THEN NVL(PZFL.ENTERED_DR,0)  \n");
        sql.append("             ELSE 0 END) WJC, \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) < '${endPeriod}'  THEN NVL(PZFL.ENTERED_CR,0)  \n");
        sql.append("             ELSE 0 END) WDC, \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) <= '${endPeriod}'  THEN NVL(PZFL.ENTERED_DR,0)  \n");
        sql.append("             ELSE 0 END) WJL, \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) <= '${endPeriod}'  THEN NVL(PZFL.ENTERED_CR,0)  \n");
        sql.append("             ELSE 0 END) WDL,  \n");
        sql.append("       (CASE WHEN SUBSTR(PZFL.PERIOD_NAME,1,7) <= '${endPeriod}'  THEN NVL(PZFL.ENTERED_DR,0) - NVL(PZFL.ENTERED_CR,0) \n");
        sql.append("             ELSE 0 END) WYE \n");
        sql.append("     FROM GL_JE_HEADERS PZ,\n");
        sql.append("          GL_JE_LINES PZFL,\n");
        sql.append("          GL_LEDGERS ACCT, \n");
        sql.append("          GL_PERIOD_STATUSES P\n");
        sql.append("    WHERE PZ.JE_HEADER_ID=PZFL.JE_HEADER_ID\n");
        sql.append("      AND PZ.LEDGER_ID = ACCT.LEDGER_ID\n");
        sql.append("      AND PZ.LEDGER_ID = P.SET_OF_BOOKS_ID \n");
        sql.append("      AND PZ.PERIOD_NAME = P.PERIOD_NAME \n");
        sql.append("      AND P.APPLICATION_ID = '101' \n");
        sql.append("      AND ACCT.LEDGER_ID = ? \n");
        sqlParams.add(accountBookCode);
        sql.append("      AND SUBSTR(PZ.PERIOD_NAME,1,7) >= '${startPeriod}'");
        sql.append("      AND SUBSTR(PZ.PERIOD_NAME,1,7) <= '${endPeriod}'  \n");
        sql.append("      AND PZ.ACTUAL_FLAG = 'A' \n");
        sql.append("      AND PZ.STATUS = 'U'  \n");
        return new Pair((Object)sql.toString(), sqlParams);
    }
}

