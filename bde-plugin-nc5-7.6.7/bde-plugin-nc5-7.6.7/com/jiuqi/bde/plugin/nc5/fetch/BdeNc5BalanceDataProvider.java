/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.bde.plugin.common.service.FetchResultConvert
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nc5.fetch;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.service.FetchResultConvert;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BdeNc5BalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private FetchResultConvert fetchResultConvert;

    protected FetchData queryData(BalanceCondition condi) {
        String sql = condi.getIncludeUncharged() != false ? this.getIncludeSql() : this.getUnIncludeSql();
        StringBuilder assJoinSql = new StringBuilder();
        StringBuilder outAssField = new StringBuilder();
        StringBuilder inAssField = new StringBuilder();
        StringBuilder outGroupField = new StringBuilder();
        Boolean subjectMapping = this.fetchResultConvert.subjectIsItemMapping(condi.getOrgMapping().getDataSchemeCode());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        for (AssistMappingBO assistMappingBO : assistMappingList) {
            if ("BD_ACCSUBJ".equals(assistMappingBO.getAccountAssistCode())) {
                inAssField.append(String.format(",KM.%1$s AS %2$s", "SUBJCODE", assistMappingBO.getAssistCode()));
                outGroupField.append(String.format(",T.%1$s", assistMappingBO.getAssistCode()));
                outAssField.append(String.format(",T.%1$s AS %1$s", assistMappingBO.getAssistCode()));
                continue;
            }
            assJoinSql.append(String.format(" LEFT JOIN (SELECT CHECKVALUE AS ID, FREEVALUEID ASSID, VALUECODE AS CODE FROM GL_FREEVALUE WHERE GL_FREEVALUE.CHECKTYPE ='%1$s') %2$s ON %2$s.ASSID=%3$s.ASSID ", assistMappingBO.getAssistCode(), assistMappingBO.getAssistCode(), condi.getIncludeUncharged() != false ? "PZX" : "B"));
            outAssField.append(String.format(",T.%1$s AS %1$s", assistMappingBO.getAssistCode()));
            inAssField.append(String.format(",%1$s.CODE AS %1$s", assistMappingBO.getAssistCode()));
            outGroupField.append(String.format(",T.%1$s", assistMappingBO.getAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("STARTPERIOD", String.format("%02d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%02d", condi.getEndPeriod()));
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("ASSJOINSQL", assJoinSql.toString());
        variable.put("SUBJECTFIELD", subjectMapping != false ? "KM.PK_ACCSUBJ" : "KM.SUBJCODE");
        variable.put("OUTASSFIELD", outAssField.toString());
        variable.put("INASSFIELD", inAssField.toString());
        variable.put("OUTGROUPFIELD", outGroupField.toString());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u79d1\u76ee\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)condi, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), querySql, null, (ResultSetExtractor)new FetchDataExtractor());
    }

    private String getIncludeSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT T.SUBJECTCODE AS SUBJECTCODE,\n");
        sql.append("        T.CURRENCYCODE AS CURRENCYCODE,\n");
        sql.append("        T.ORIENT AS ORIENT,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.JF ELSE 0 END) AS JF,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.WJF ELSE 0 END) AS WJF,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.DF ELSE 0 END) AS DF,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.WDF ELSE 0 END) AS WDF,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.JF ELSE 0 END) AS JL,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.WJF ELSE 0 END) AS WJL,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.DF ELSE 0 END) AS DL,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.WDF ELSE 0 END) AS WDL,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD='00' THEN T.JF-T.DF ELSE 0 END) AS NC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD='00' THEN T.WJF-T.WDF ELSE 0 END) AS WNC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<'${STARTPERIOD}' THEN T.JF-T.DF ELSE 0 END) AS C,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<'${STARTPERIOD}' THEN T.WJF-T.WDF ELSE 0 END) AS WC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<='${ENDPERIOD}' THEN T.JF-T.DF ELSE 0 END) AS YE,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<='${ENDPERIOD}' THEN T.WJF-T.WDF ELSE 0 END) AS WYE\n");
        sql.append("        ${OUTASSFIELD} \n");
        sql.append(" FROM\n \n");
        sql.append("   (SELECT  ${SUBJECTFIELD} SUBJECTCODE,\n");
        sql.append("            PZ.PERIOD PERIOD,\n");
        sql.append("            CASE WHEN KM.BALANORIENT = 1 THEN 1 ELSE -1 END AS ORIENT,\n");
        sql.append("            CURRENCY.CURRTYPECODE CURRENCYCODE,\n");
        sql.append("            PZX.LOCALDEBITAMOUNT JF,\n");
        sql.append("            PZX.LOCALCREDITAMOUNT DF,\n");
        sql.append("            PZX.DEBITAMOUNT WJF,\n");
        sql.append("            PZX.CREDITAMOUNT WDF\n");
        sql.append("            ${INASSFIELD} \n");
        sql.append("      FROM  GL_DETAIL PZX \n");
        sql.append("     INNER JOIN GL_VOUCHER PZ ON PZ.PK_VOUCHER = PZX.PK_VOUCHER \n");
        sql.append("     INNER JOIN BD_GLORGBOOK BOOK ON PZ.PK_GLORGBOOK = BOOK.PK_GLORGBOOK \n");
        sql.append("     INNER JOIN BD_ACCSUBJ KM ON PZX.PK_ACCSUBJ = KM.PK_ACCSUBJ AND BOOK.PK_GLORGBOOK = KM.PK_GLORGBOOK \n");
        sql.append("     INNER JOIN BD_CURRTYPE CURRENCY ON PZX.PK_CURRTYPE = CURRENCY.PK_CURRTYPE \n");
        sql.append("     ${ASSJOINSQL} \n");
        sql.append("     WHERE PZ.YEAR='${YEAR}'\n");
        sql.append("       AND PZ.VOUCHERKIND <> 255\n");
        sql.append("       AND KM.ENDFLAG = 'Y'\n");
        sql.append("       AND PZ.DISCARDFLAG = 'N'\n");
        sql.append("       AND PZ.DR = 0\n");
        sql.append("       AND BOOK.GLORGBOOKCODE='${BOOKCODE}') T\n");
        sql.append(" GROUP BY T.SUBJECTCODE,T.CURRENCYCODE,T.ORIENT ${OUTGROUPFIELD}\n");
        return sql.toString();
    }

    private String getUnIncludeSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT T.SUBJECTCODE AS SUBJECTCODE,\n");
        sql.append("        T.CURRENCYCODE AS CURRENCYCODE,\n");
        sql.append("        T.ORIENT AS ORIENT,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.JF ELSE 0 END) AS JF,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.WJF ELSE 0 END) AS WJF,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.DF ELSE 0 END) AS DF,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.WDF ELSE 0 END) AS WDF,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.JF ELSE 0 END) AS JL,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.WJF ELSE 0 END) AS WJL,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.DF ELSE 0 END) AS DL,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.WDF ELSE 0 END) AS WDL,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD='00' THEN T.JF-T.DF ELSE 0 END) AS NC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD='00' THEN T.WJF-T.WDF ELSE 0 END) AS WNC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<'${STARTPERIOD}' THEN T.JF-T.DF ELSE 0 END) AS C,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<'${STARTPERIOD}' THEN T.WJF-T.WDF ELSE 0 END) AS WC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<='${ENDPERIOD}' THEN T.JF-T.DF ELSE 0 END) AS YE,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<='${ENDPERIOD}' THEN T.WJF-T.WDF ELSE 0 END) AS WYE\n");
        sql.append("        ${OUTGROUPFIELD} \n");
        sql.append(" FROM\n");
        sql.append("   (SELECT  ${SUBJECTFIELD} SUBJECTCODE,\n");
        sql.append("            CASE WHEN KM.BALANORIENT = 1 THEN 1 ELSE -1 END AS ORIENT,\n");
        sql.append("            B.PERIOD AS PERIOD,\n");
        sql.append("            CURRENCY.CURRTYPECODE AS CURRENCYCODE,\n");
        sql.append("            B.LOCALDEBITAMOUNT AS JF,\n");
        sql.append("            B.LOCALCREDITAMOUNT AS DF,\n");
        sql.append("            B.DEBITAMOUNT AS WJF,\n");
        sql.append("            B.CREDITAMOUNT AS WDF\n");
        sql.append("            ${INASSFIELD} \n");
        sql.append("      FROM  GL_BALANCE B \n");
        sql.append("     INNER JOIN BD_GLORGBOOK BOOK ON B.PK_GLORGBOOK = BOOK.PK_GLORGBOOK \n");
        sql.append("     INNER JOIN BD_ACCSUBJ KM ON B.PK_ACCSUBJ = KM.PK_ACCSUBJ AND BOOK.PK_GLORGBOOK = KM.PK_GLORGBOOK \n");
        sql.append("     INNER JOIN BD_CURRTYPE CURRENCY ON B.PK_CURRTYPE = CURRENCY.PK_CURRTYPE \n");
        sql.append("     ${ASSJOINSQL} \n");
        sql.append("     WHERE B.YEAR='${YEAR}'\n");
        sql.append("       AND KM.ENDFLAG = 'Y'\n");
        sql.append("       AND BOOK.GLORGBOOKCODE='${BOOKCODE}') T\n");
        sql.append(" GROUP BY T.SUBJECTCODE,T.CURRENCYCODE,T.ORIENT ${OUTGROUPFIELD}\n");
        return sql.toString();
    }
}

