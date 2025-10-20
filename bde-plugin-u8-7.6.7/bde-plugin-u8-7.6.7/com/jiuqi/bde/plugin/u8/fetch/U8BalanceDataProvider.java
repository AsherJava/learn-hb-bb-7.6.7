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
package com.jiuqi.bde.plugin.u8.fetch;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.u8.assist.U8AssistPojo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class U8BalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;

    protected FetchData queryData(BalanceCondition condi) {
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT T.SUBJECTCODE AS SUBJECTCODE,   \n");
        sql.append("        T.CURRENCYCODE AS CURRENCYCODE,  \n");
        sql.append("        T.ORIENT AS ORIENT,  \n");
        sql.append("        SUM(CASE WHEN T.PERIOD>=${STARTPERIOD} AND T.PERIOD<=${ENDPERIOD} THEN T.JF ELSE 0 END) AS JF,  \n");
        sql.append("        SUM(CASE WHEN T.PERIOD>0 AND T.PERIOD<=${ENDPERIOD} THEN T.JF ELSE 0 END) AS JL,  \n");
        sql.append("        SUM(CASE WHEN T.PERIOD>=${STARTPERIOD} AND T.PERIOD<=${ENDPERIOD} THEN T.DF ELSE 0 END) AS DF,  \n");
        sql.append("        SUM(CASE WHEN T.PERIOD>0 AND T.PERIOD<=${ENDPERIOD} THEN T.DF ELSE 0 END) AS DL,  \n");
        sql.append("        SUM(CASE WHEN T.PERIOD=0 THEN T.JF-T.DF ELSE 0 END) AS NC,  \n");
        sql.append("        SUM(CASE WHEN T.PERIOD=0 THEN T.WJF-T.WDF ELSE 0 END) AS WNC,  \n");
        sql.append("        SUM(CASE WHEN T.PERIOD<${ENDPERIOD} THEN T.JF-T.DF ELSE 0 END) AS C,  \n");
        sql.append("        SUM(CASE WHEN T.PERIOD<${ENDPERIOD} THEN T.WJF-T.WDF ELSE 0 END) AS WC,  \n");
        sql.append("        SUM(CASE WHEN T.PERIOD<=${ENDPERIOD} THEN T.JF-T.DF  ELSE 0 END) AS YE,  \n");
        sql.append("        SUM(CASE WHEN T.PERIOD<=${ENDPERIOD} THEN T.WJF-T.WDF  ELSE 0 END) AS WYE,  \n");
        sql.append("        SUM(CASE WHEN T.PERIOD>=${STARTPERIOD} AND T.PERIOD<=${ENDPERIOD} THEN T.WJF ELSE 0 END) AS WJF,  \n");
        sql.append("        SUM(CASE WHEN T.PERIOD>0 AND T.PERIOD<=${ENDPERIOD} THEN T.WJF ELSE 0 END) AS WJL,  \n");
        sql.append("        SUM(CASE WHEN T.PERIOD>=${STARTPERIOD} AND T.PERIOD<=${ENDPERIOD} THEN T.WDF ELSE 0 END) AS WDF,  \n");
        sql.append("        SUM(CASE WHEN T.PERIOD>0 AND T.PERIOD<=${ENDPERIOD} THEN T.WDF ELSE 0 END) AS WDL  \n");
        sql.append("        ${OUTSELECT}  \n");
        sql.append(" FROM   \n");
        sql.append("      (SELECT BALANCE.IYEAR AS YEAR,   \n");
        sql.append("              0 AS PERIOD,   \n");
        sql.append("              CURRENCY.CEXCH_CODE AS CURRENCYCODE,   \n");
        sql.append("              BALANCE.CCODE AS SUBJECTCODE,   \n");
        sql.append("              CASE WHEN SUBJECT.BPROPERTY=1 THEN 1 ELSE -1 END AS ORIENT,  \n");
        sql.append("              CASE WHEN BALANCE.CBEGIND_C= '\u501f' THEN BALANCE.MB ELSE 0 END AS JF,   \n");
        sql.append("              CASE WHEN BALANCE.CBEGIND_C= '\u8d37' THEN BALANCE.MB ELSE 0 END AS DF,   \n");
        sql.append("              CASE WHEN BALANCE.CBEGIND_C= '\u501f' THEN BALANCE.MB_F ELSE 0 END AS WJF,    \n");
        sql.append("              CASE WHEN BALANCE.CBEGIND_C= '\u8d37' THEN BALANCE.MB_F ELSE 0 END AS WDF    \n");
        sql.append("              ${BALANCESELECT}  \n");
        sql.append("         From ${BEGINMONTHTABLE} BALANCE   \n");
        sql.append("         INNER JOIN CODE SUBJECT ON BALANCE.CCODE=SUBJECT.CCODE     \n");
        sql.append("         INNER JOIN FOREIGNCURRENCY CURRENCY ON CASE WHEN BALANCE.CEXCH_NAME IS NULL THEN  '\u4eba\u6c11\u5e01 ' ELSE BALANCE.CEXCH_NAME END=CURRENCY.CEXCH_NAME     \n");
        sql.append("        WHERE 1=1   \n");
        sql.append("          AND SUBJECT.IYEAR = ${YEAR}   \n");
        sql.append("          AND BALANCE.IPERIOD=1    \n");
        sql.append("          AND SUBJECT.BEND=1     \n");
        sql.append("        UNION ALL     \n");
        sql.append("       SELECT OCCUR.IYEAR AS YEAR,     \n");
        sql.append("              OCCUR.IPERIOD AS PERIOD,     \n");
        sql.append("              CURRENCY.CEXCH_CODE AS CURRENCYCODE,     \n");
        sql.append("              OCCUR.CCODE AS SUBJECTCODE,     \n");
        sql.append("              CASE WHEN SUBJECT.BPROPERTY=1 THEN 1 ELSE -1 END AS ORIENT,    \n");
        sql.append("              OCCUR.MD AS JF,     \n");
        sql.append("              OCCUR.MC AS DF,     \n");
        sql.append("              OCCUR.MD_F AS WJF,     \n");
        sql.append("              OCCUR.MC_F AS WDF     \n");
        sql.append("              ${OCCURSELECT}  \n");
        sql.append("         From ${OCCURTABLE} OCCUR      \n");
        sql.append("         INNER JOIN CODE SUBJECT ON OCCUR.CCODE=SUBJECT.CCODE     \n");
        sql.append("         INNER JOIN FOREIGNCURRENCY CURRENCY ON CASE WHEN OCCUR.CEXCH_NAME is NULL THEN  '\u4eba\u6c11\u5e01 ' ELSE OCCUR.CEXCH_NAME END=CURRENCY.CEXCH_NAME     \n");
        sql.append("        WHERE 1=1    \n");
        sql.append("          AND SUBJECT.IYEAR = ${YEAR}    \n");
        sql.append("          AND OCCUR.IPERIOD>0    \n");
        sql.append("          AND SUBJECT.BEND=1 ${VOUCHERCONDITION} ) T   \n");
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"T.SUBJECTCODE"));
        sql.append(" WHERE 1=1   \n");
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"T.SUBJECTCODE"));
        sql.append(" AND T.YEAR=${YEAR}   \n");
        sql.append(" GROUP BY T.SUBJECTCODE,  \n");
        sql.append("          T.CURRENCYCODE,  \n");
        sql.append("          T.ORIENT  \n");
        sql.append("          ${OUTGROUP}  \n");
        StringBuilder outGroup = new StringBuilder();
        StringBuilder outSelect = new StringBuilder();
        StringBuilder balanceSelect = new StringBuilder();
        StringBuilder occurSelect = new StringBuilder();
        String beginTable = CollectionUtils.isEmpty((Collection)assistMappingList) ? "GL_ACCSUM" : "GL_ACCASS";
        String occurTable = condi.getIncludeUncharged() != false ? "GL_ACCVOUCH" : beginTable;
        for (AssistMappingBO assistMapping : assistMappingList) {
            outGroup.append(String.format(",T.%1$s", assistMapping.getAssistCode()));
            outSelect.append(String.format(",T.%1$s AS %1$s", assistMapping.getAssistCode()));
            balanceSelect.append(String.format(",BALANCE.%1$s AS %2$s", ((U8AssistPojo)assistMapping.getAccountAssist()).getAssField(), assistMapping.getAssistCode()));
            occurSelect.append(String.format(",OCCUR.%1$s AS %2$s", ((U8AssistPojo)assistMapping.getAccountAssist()).getAssField(), assistMapping.getAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("OUTGROUP", outGroup.toString());
        variable.put("STARTPERIOD", condi.getEndPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("OCCURTABLE", occurTable);
        variable.put("VOUCHERCONDITION", condi.getIncludeUncharged() != false ? " AND OCCUR.IFLAG IS NULL " : "");
        variable.put("BALANCESELECT", balanceSelect.toString());
        variable.put("OUTSELECT", outSelect.toString());
        variable.put("OCCURSELECT", occurSelect.toString());
        variable.put("BEGINMONTHTABLE", beginTable);
        variable.put("YEAR", condi.getAcctYear().toString());
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u79d1\u76ee\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)condi, (String)lastSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), lastSql, new Object[0], (ResultSetExtractor)new FetchDataExtractor());
    }
}

