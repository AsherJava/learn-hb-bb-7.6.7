/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nc5.penetrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.nc5.BdeNc5PluginType;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractNc5BalanceContentProvider
extends AbstractBalanceContentProvider {
    @Autowired
    private BdeNc5PluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        String sql = condi.getIncludeUncharged() != false ? this.getIncludeSql(condi) : this.getUnIncludeSql(condi);
        StringBuilder assJoinSql = new StringBuilder();
        StringBuilder outAssField = new StringBuilder();
        StringBuilder inAssField = new StringBuilder();
        StringBuilder outGroupField = new StringBuilder();
        StringBuilder assCondition = new StringBuilder();
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        for (AssistMappingBO assistMapping : assistMappingList) {
            if ("BD_ACCSUBJ".equals(assistMapping.getAccountAssistCode())) {
                inAssField.append(String.format(",KM.SUBJCODE AS %1$s", assistMapping.getAssistCode()));
                inAssField.append(String.format(",KM.SUBJNAME AS %1$s_NAME", assistMapping.getAssistCode()));
                outAssField.append(String.format(",T.%1$s AS %1$s,T.%1$s_NAME AS %1$s_NAME", assistMapping.getAssistCode()));
                continue;
            }
            assJoinSql.append(String.format(" LEFT JOIN (SELECT CHECKVALUE AS ID, FREEVALUEID ASSID, VALUECODE AS CODE, VALUENAME AS NAME FROM GL_FREEVALUE WHERE GL_FREEVALUE.CHECKTYPE ='%1$s') %2$s ON %2$s.ASSID=%3$s.ASSID ", assistMapping.getAssistCode(), assistMapping.getAssistCode(), condi.getIncludeUncharged() != false ? "PZX" : "B"));
            outAssField.append(String.format(",T.%1$s AS %1$s", assistMapping.getAssistCode()));
            outAssField.append(String.format(",T.%1$s_NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            inAssField.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            inAssField.append(String.format(",%1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            assCondition.append(this.matchByRule("T", assistMapping.getAssistCode(), assistMapping.getExecuteDim().getDimCode(), assistMapping.getExecuteDim().getDimRule()));
            outGroupField.append(String.format(",T.%1$s", assistMapping.getAssistCode()));
            outGroupField.append(String.format(",T.%1$s_NAME", assistMapping.getAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("STARTPERIOD", String.format("%02d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%02d", condi.getEndPeriod()));
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("ASSCONDITION", assCondition.toString());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("ASSJOINSQL", assJoinSql.toString());
        variable.put("OUTASSFIELD", outAssField.toString());
        variable.put("INASSFIELD", inAssField.toString());
        variable.put("OUTGROUPFIELD", outGroupField.toString());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        return new QueryParam(PenetrateUtil.replaceContext((String)querySql, (PenetrateBaseDTO)condi), new Object[0], (ResultSetExtractor)new BalanceResultSetExtractor(assistMappingList));
    }

    private String getIncludeSql(PenetrateBaseDTO condi) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT T.SUBJECTCODE AS SUBJECTCODE,\n");
        sql.append("        T.CURRENCYCODE AS CURRENCYCODE,\n");
        sql.append("        T.SUBJECTNAME AS SUBJECTNAME,\n");
        sql.append("        T.ORIENT AS ORIENT,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.DEBIT ELSE 0 END) AS DEBIT,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.ORGND ELSE 0 END) AS ORGND,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.CREDIT ELSE 0 END) AS CREDIT,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.ORGNC ELSE 0 END) AS ORGNC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.DEBIT ELSE 0 END) AS DSUM,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.ORGND ELSE 0 END) AS ORGNDSUM,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.CREDIT ELSE 0 END) AS CSUM,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.ORGNC ELSE 0 END) AS ORGNCSUM,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD='00' THEN T.DEBIT-T.CREDIT ELSE 0 END) AS NC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD='00' THEN T.ORGND-T.ORGNC ELSE 0 END) AS ORGNNC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<'${STARTPERIOD}' THEN T.DEBIT-T.CREDIT ELSE 0 END) AS QC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<'${STARTPERIOD}' THEN T.ORGND-T.ORGNC ELSE 0 END) AS ORGNQC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<='${ENDPERIOD}' THEN T.DEBIT-T.CREDIT ELSE 0 END) AS YE,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<='${ENDPERIOD}' THEN T.ORGND-T.ORGNC ELSE 0 END) AS ORGNYE\n");
        sql.append("        ${OUTASSFIELD} \n");
        sql.append(" FROM\n \n");
        sql.append("   (SELECT  KM.SUBJCODE SUBJECTCODE,\n");
        sql.append("            KM.SUBJNAME SUBJECTNAME,\n");
        sql.append("            PZ.PERIOD PERIOD,\n");
        sql.append("            CASE WHEN KM.BALANORIENT = 1 THEN 1 ELSE -1 END AS ORIENT,\n");
        sql.append("            CURRENCY.CURRTYPECODE CURRENCYCODE,\n");
        sql.append("            PZX.LOCALDEBITAMOUNT DEBIT,\n");
        sql.append("            PZX.LOCALCREDITAMOUNT CREDIT,\n");
        sql.append("            PZX.DEBITAMOUNT ORGND,\n");
        sql.append("            PZX.CREDITAMOUNT ORGNC\n");
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
        sql.append(this.buildSubjectCondi("KM", "SUBJCODE", condi.getSubjectCode()));
        sql.append(this.buildExcludeCondi("KM", "SUBJCODE", condi.getExcludeSubjectCode()));
        sql.append("       AND PZ.DISCARDFLAG = 'N'\n");
        sql.append("       AND PZ.DR = 0\n");
        sql.append("       AND BOOK.GLORGBOOKCODE='${BOOKCODE}') T\n");
        sql.append(" WHERE 1=1 \n");
        sql.append("       ${ASSCONDITION}");
        sql.append(" GROUP BY T.SUBJECTCODE,T.CURRENCYCODE,T.ORIENT,T.SUBJECTNAME ${OUTGROUPFIELD}\n");
        sql.append(" ORDER BY T.SUBJECTCODE");
        return sql.toString();
    }

    private String getUnIncludeSql(PenetrateBaseDTO condi) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT T.SUBJECTCODE AS SUBJECTCODE,\n");
        sql.append("        T.SUBJECTNAME AS SUBJECTNAME,\n");
        sql.append("        T.CURRENCYCODE AS CURRENCYCODE,\n");
        sql.append("        T.ORIENT AS ORIENT,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.DEBIT ELSE 0 END) AS DEBIT,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.ORGND ELSE 0 END) AS ORGND,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.CREDIT ELSE 0 END) AS CREDIT,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>='${STARTPERIOD}' AND T.PERIOD<='${ENDPERIOD}' THEN T.ORGNC ELSE 0 END) AS ORGNC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.DEBIT ELSE 0 END) AS DSUM,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.ORGND ELSE 0 END) AS ORGNDSUM,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.CREDIT ELSE 0 END) AS CSUM,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD>'00' AND T.PERIOD<='${ENDPERIOD}' THEN T.ORGNC ELSE 0 END) AS ORGNCSUM,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD='00' THEN T.DEBIT-T.CREDIT ELSE 0 END) AS NC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD='00' THEN T.ORGND-T.ORGNC ELSE 0 END) AS ORGNNC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<'${STARTPERIOD}' THEN T.DEBIT-T.CREDIT ELSE 0 END) AS QC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<'${STARTPERIOD}' THEN T.ORGND-T.ORGNC ELSE 0 END) AS ORGNQC,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<='${ENDPERIOD}' THEN T.DEBIT-T.CREDIT ELSE 0 END) AS YE,\n");
        sql.append("        SUM(CASE WHEN T.PERIOD<='${ENDPERIOD}' THEN T.ORGND-T.ORGNC ELSE 0 END) AS ORGNYE\n");
        sql.append("        ${OUTGROUPFIELD} \n");
        sql.append(" FROM\n");
        sql.append("   (SELECT  KM.SUBJCODE SUBJECTCODE,\n");
        sql.append("            KM.SUBJNAME SUBJECTNAME,\n");
        sql.append("            CASE WHEN KM.BALANORIENT = 1 THEN 1 ELSE -1 END AS ORIENT,\n");
        sql.append("            B.PERIOD AS PERIOD,\n");
        sql.append("            CURRENCY.CURRTYPECODE AS CURRENCYCODE,\n");
        sql.append("            B.LOCALDEBITAMOUNT AS DEBIT,\n");
        sql.append("            B.LOCALCREDITAMOUNT AS CREDIT,\n");
        sql.append("            B.DEBITAMOUNT AS ORGND,\n");
        sql.append("            B.CREDITAMOUNT AS ORGNC\n");
        sql.append("            ${INASSFIELD} \n");
        sql.append("      FROM  GL_BALANCE B \n");
        sql.append("     INNER JOIN BD_GLORGBOOK BOOK ON B.PK_GLORGBOOK = BOOK.PK_GLORGBOOK \n");
        sql.append("     INNER JOIN BD_ACCSUBJ KM ON B.PK_ACCSUBJ = KM.PK_ACCSUBJ AND BOOK.PK_GLORGBOOK = KM.PK_GLORGBOOK \n");
        sql.append("     INNER JOIN BD_CURRTYPE CURRENCY ON B.PK_CURRTYPE = CURRENCY.PK_CURRTYPE \n");
        sql.append("     ${ASSJOINSQL} \n");
        sql.append("     WHERE B.YEAR='${YEAR}'\n");
        sql.append("       AND KM.ENDFLAG = 'Y'\n");
        sql.append(this.buildSubjectCondi("KM", "SUBJCODE", condi.getSubjectCode()));
        sql.append(this.buildExcludeCondi("KM", "SUBJCODE", condi.getExcludeSubjectCode()));
        sql.append("       AND BOOK.GLORGBOOKCODE='${BOOKCODE}') T\n");
        sql.append(" WHERE 1=1 \n");
        sql.append("       ${ASSCONDITION}");
        sql.append(" GROUP BY T.SUBJECTCODE,T.CURRENCYCODE,T.ORIENT,T.SUBJECTNAME ${OUTGROUPFIELD}\n");
        sql.append(" ORDER BY T.SUBJECTCODE");
        return sql.toString();
    }
}

