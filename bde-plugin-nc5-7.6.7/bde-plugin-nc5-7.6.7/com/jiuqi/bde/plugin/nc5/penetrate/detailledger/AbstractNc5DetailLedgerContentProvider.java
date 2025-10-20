/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nc5.penetrate.detailledger;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.nc5.BdeNc5PluginType;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractNc5DetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeNc5PluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        String sql = condi.getIncludeUncharged() != false ? this.getIncludeUnchargedSql(condi, sqlHandler) : this.getNotIncludeUnchargedSql(condi, sqlHandler);
        HashMap penetrateAssistDimMap = condi.getAssTypeList() == null ? new HashMap() : condi.getAssTypeList().stream().collect(Collectors.toMap(Dimension::getDimCode, item -> item, (k1, k2) -> k2));
        StringBuilder yeAssJoinSql = new StringBuilder();
        StringBuilder voucherAssJoinSql = new StringBuilder();
        StringBuilder outAssField = new StringBuilder();
        StringBuilder inAssField = new StringBuilder();
        StringBuilder inGroupField = new StringBuilder();
        StringBuilder assCondition = new StringBuilder();
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        for (AssistMappingBO assistMapping : assistMappingList) {
            Dimension dimension = assistMapping.getExecuteDim();
            if ("BD_ACCSUBJ".equals(assistMapping.getAccountAssistCode())) {
                inAssField.append(String.format(",KM.SUBJCODE AS %1$s", assistMapping.getAssistCode()));
                inAssField.append(String.format(",KM.SUBJNAME AS %1$s_NAME", assistMapping.getAssistCode()));
                outAssField.append(String.format(" ,MAINTABLE.%1$s", assistMapping.getAssistCode()));
                outAssField.append(String.format(" ,MAINTABLE.%1$s_NAME", assistMapping.getAssistCode()));
                continue;
            }
            yeAssJoinSql.append(String.format(" LEFT JOIN (SELECT CHECKVALUE AS ID, FREEVALUEID ASSID, VALUECODE AS CODE, VALUENAME AS NAME FROM GL_FREEVALUE WHERE GL_FREEVALUE.CHECKTYPE ='%1$s')  %2$s ON %2$s.ASSID=%3$s.ASSID ", assistMapping.getAssistCode(), assistMapping.getAssistCode(), "YE"));
            voucherAssJoinSql.append(String.format(" LEFT JOIN (SELECT CHECKVALUE AS ID, FREEVALUEID ASSID, VALUECODE AS CODE, VALUENAME AS NAME FROM GL_FREEVALUE WHERE GL_FREEVALUE.CHECKTYPE ='%1$s')  %2$s ON %2$s.ASSID=%3$s.ASSID ", assistMapping.getAssistCode(), assistMapping.getAssistCode(), "PZX"));
            inAssField.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            inAssField.append(String.format(",%1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            outAssField.append(String.format(" ,MAINTABLE.%1$s", assistMapping.getAssistCode()));
            outAssField.append(String.format(" ,MAINTABLE.%1$s_NAME", assistMapping.getAssistCode()));
            assCondition.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", dimension.getDimValue(), dimension.getDimRule()));
            inGroupField.append(String.format(",%1$s.CODE,%1$s.NAME", assistMapping.getAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("START_PERIOD", String.format("%02d", condi.getStartPeriod()));
        variable.put("END_PERIOD", String.format("%02d", condi.getEndPeriod()));
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("ASSCONDITION", assCondition.toString());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("YEASSJOINSQL", yeAssJoinSql.toString());
        variable.put("VOUCHERASSJOINSQL", voucherAssJoinSql.toString());
        variable.put("OUTASSFIELD", outAssField.toString());
        variable.put("INASSFIELD", inAssField.toString());
        variable.put("INGROUPFIELD", inGroupField.toString());
        String querySql = VariableParseUtil.parse((String)sql, (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(PenetrateUtil.replaceContext((String)querySql, (PenetrateBaseDTO)condi), new Object[0], (ResultSetExtractor)new DetailLedgerResultSetExtractor(assistMappingList));
        return queryParam;
    }

    private String getNotIncludeUnchargedSql(PenetrateBaseDTO condi, IDbSqlHandler sqlHandler) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ROWTYPE,");
        sql.append("        ID,");
        sql.append("        VCHRID,");
        sql.append("        ACCTYEAR,");
        sql.append("        ACCTPERIOD,");
        sql.append("        ACCTDAY,");
        sql.append("        VCHRTYPE,");
        sql.append("        ORIENT,");
        sql.append("        SUBJECTCODE,");
        sql.append("        SUBJECTNAME,");
        sql.append("        DIGEST,");
        sql.append("        DEBIT,");
        sql.append("        CREDIT,");
        sql.append("        ORGND,");
        sql.append("        ORGNC,");
        sql.append("        YE,");
        sql.append("        ORGNYE,");
        sql.append("        ORDERNUM");
        sql.append("        ${OUTASSFIELD}");
        sql.append(" FROM (");
        sql.append("     SELECT\n");
        sql.append("            3 AS ROWTYPE,\n");
        sql.append("            NULL AS ID ,\n");
        sql.append("            NULL AS VCHRID,\n");
        sql.append("            YE.YEAR AS ACCTYEAR,\n");
        sql.append("            '${START_PERIOD}' AS ACCTPERIOD,\n");
        sql.append("            NULL AS ACCTDAY,\n");
        sql.append("            NULL AS VCHRTYPE,\n");
        sql.append("            CASE WHEN KM.BALANORIENT = 1 THEN 1 ELSE -1 END AS ORIENT,\n");
        sql.append("            KM.SUBJCODE SUBJECTCODE,\n");
        sql.append("            KM.SUBJNAME SUBJECTNAME\n");
        sql.append("            ${INASSFIELD}");
        sql.append(String.format("       ,%s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("            SUM(YE.LOCALDEBITAMOUNT) AS DEBIT,\n");
        sql.append("            SUM(YE.LOCALCREDITAMOUNT) AS CREDIT,\n");
        sql.append("            SUM(YE.DEBITAMOUNT) AS ORGND,\n");
        sql.append("            SUM(YE.CREDITAMOUNT) AS ORGNC,\n");
        sql.append("            SUM(YE.LOCALDEBITAMOUNT - YE.LOCALCREDITAMOUNT) AS YE,\n");
        sql.append("            SUM(YE.DEBITAMOUNT - YE.CREDITAMOUNT) AS ORGNYE\n");
        sql.append(String.format("       ,%s AS ORDERNUM  \n", sqlHandler.toChar("'0'")));
        sql.append("     FROM\n");
        sql.append("            GL_BALANCE YE\n");
        sql.append("     INNER JOIN BD_GLORGBOOK BOOK ON YE.PK_GLORGBOOK = BOOK.PK_GLORGBOOK\n");
        sql.append("     INNER JOIN BD_ACCSUBJ KM ON YE.PK_ACCSUBJ = KM.PK_ACCSUBJ\n");
        sql.append("            AND BOOK.PK_GLORGBOOK = KM.PK_GLORGBOOK\n");
        sql.append("     ${YEASSJOINSQL} \n");
        sql.append("     WHERE\n");
        sql.append("            YE.YEAR = '${YEAR}'\n");
        sql.append("            AND KM.ENDFLAG = 'Y'\n");
        sql.append("            ${ASSCONDITION}");
        sql.append("            AND YE.PERIOD < '${START_PERIOD}' \n");
        sql.append("           AND BOOK.GLORGBOOKCODE='${BOOKCODE}'\n");
        sql.append(this.buildSubjectCondi("KM", "SUBJCODE", condi.getSubjectCode()));
        if (!StringUtils.isEmpty((String)condi.getExcludeSubjectCode())) {
            sql.append(String.format(" AND KM.SUBJCODE NOT LIKE '%s%%%%' ", condi.getExcludeSubjectCode()));
        }
        sql.append("     GROUP BY\n");
        sql.append("            YE.YEAR,\n");
        sql.append("            KM.BALANORIENT,\n");
        sql.append("            KM.SUBJCODE,\n");
        sql.append("            KM.SUBJNAME\n");
        sql.append("            ${INGROUPFIELD}");
        sql.append("     UNION ALL\n");
        sql.append("     SELECT\n");
        sql.append("            0 AS ROWTYPE,\n");
        sql.append("            PZX.PK_DETAIL AS ID,\n");
        sql.append("            PZ.PK_VOUCHER AS VCHRID,\n");
        sql.append("            PZX.YEARV AS ACCTYEAR,\n");
        sql.append("            PZX.PERIODV AS ACCTPERIOD ,\n");
        sql.append("            SUBSTR(PZ.PREPAREDDATE, 9, 2) AS ACCTDAY,\n");
        sql.append(sqlHandler.concatBySeparator("-", new String[]{"BD_VOUCHERTYPE.FORSHORT", sqlHandler.toChar("PZ.NO")})).append(" AS VCHRTYPE, \n");
        sql.append("            CASE WHEN KM.BALANORIENT = 1 THEN 1 ELSE -1 END AS ORIENT,\n");
        sql.append("            KM.SUBJCODE SUBJECTCODE,\n");
        sql.append("            KM.SUBJNAME SUBJECTNAME\n");
        sql.append("            ${INASSFIELD}");
        sql.append(String.format("       ,%s AS DIGEST,  \n", sqlHandler.toChar("PZX.EXPLANATION")));
        sql.append("            PZX.LOCALDEBITAMOUNT AS DEBIT,\n");
        sql.append("            PZX.LOCALCREDITAMOUNT AS CREDIT,\n");
        sql.append("            PZX.DEBITAMOUNT AS ORGND,\n");
        sql.append("            PZX.CREDITAMOUNT AS ORGNC,\n");
        sql.append("            0 AS YE,\n");
        sql.append("            0 AS ORGNYE,\n");
        sql.append(sqlHandler.concat(new String[]{"PZ.PREPAREDDATE", "PZ.NO", "PZX.DETAILINDEX"})).append(" AS ORDERNUM");
        sql.append("     FROM\n");
        sql.append("            GL_DETAIL PZX\n");
        sql.append("     INNER JOIN (\n");
        sql.append("            SELECT\n");
        sql.append("                    VCHR.PK_VOUCHER,\n");
        sql.append("                    VCHR.PREPAREDDATE,\n");
        sql.append("                    VCHR.PK_VOUCHERTYPE,\n");
        sql.append("                    VCHR.NO,\n");
        sql.append("                    PK_GLORGBOOK\n");
        sql.append("            FROM\n");
        sql.append("                    GL_VOUCHER VCHR\n");
        sql.append("            WHERE\n");
        sql.append("                    1 = 1\n");
        sql.append("                    AND VCHR.DISCARDFLAG = 'N'\n");
        sql.append("                    AND VCHR.DR = 0\n");
        sql.append("                    AND VCHR.VOUCHERKIND <> 255\n");
        sql.append("                    AND VCHR.YEAR = '${YEAR}'\n");
        sql.append("                    AND VCHR.PERIOD >= '${START_PERIOD}'\n");
        sql.append("                    AND VCHR.PERIOD <= '${END_PERIOD}'\n");
        sql.append("                    AND VCHR.PK_MANAGER <>'N/A') PZ\n");
        sql.append("            ON\n");
        sql.append("                    PZ.PK_VOUCHER = PZX.PK_VOUCHER\n");
        sql.append("     INNER JOIN BD_GLORGBOOK BOOK ON PZ.PK_GLORGBOOK = BOOK.PK_GLORGBOOK \n");
        sql.append("     INNER JOIN BD_ACCSUBJ KM ON PZX.PK_ACCSUBJ = KM.PK_ACCSUBJ AND BOOK.PK_GLORGBOOK = KM.PK_GLORGBOOK\n");
        sql.append("     LEFT JOIN BD_VOUCHERTYPE BD_VOUCHERTYPE ON BD_VOUCHERTYPE.PK_VOUCHERTYPE=PZ.PK_VOUCHERTYPE \n");
        sql.append("     ${VOUCHERASSJOINSQL} \n");
        sql.append("     WHERE 1=1\n");
        sql.append("           AND BOOK.GLORGBOOKCODE='${BOOKCODE}'\n");
        sql.append("           AND KM.ENDFLAG = 'Y'\n");
        sql.append("           ${ASSCONDITION}");
        sql.append(this.buildSubjectCondi("KM", "SUBJCODE", condi.getSubjectCode()));
        if (!StringUtils.isEmpty((String)condi.getExcludeSubjectCode())) {
            sql.append(String.format(" AND KM.SUBJCODE NOT LIKE '%s%%%%' ", condi.getExcludeSubjectCode()));
        }
        sql.append(" ) MAINTABLE ");
        sql.append("     ORDER BY MAINTABLE.ACCTPERIOD,  \n");
        sql.append("              ORDERNUM  \n");
        return sql.toString();
    }

    private String getIncludeUnchargedSql(PenetrateBaseDTO condi, IDbSqlHandler sqlHandler) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ROWTYPE,");
        sql.append("        ID,");
        sql.append("        VCHRID,");
        sql.append("        ACCTYEAR,");
        sql.append("        ACCTPERIOD,");
        sql.append("        ACCTDAY,");
        sql.append("        VCHRTYPE,");
        sql.append("        ORIENT,");
        sql.append("        SUBJECTCODE,");
        sql.append("        SUBJECTNAME,");
        sql.append("        DIGEST,");
        sql.append("        DEBIT,");
        sql.append("        CREDIT,");
        sql.append("        ORGND,");
        sql.append("        ORGNC,");
        sql.append("        YE,");
        sql.append("        ORGNYE,");
        sql.append("        ORDERNUM");
        sql.append("        ${OUTASSFIELD}");
        sql.append(" FROM (");
        sql.append("     SELECT 3 AS ROWTYPE,\n");
        sql.append("            NULL AS ID ,\n");
        sql.append("            NULL AS VCHRID,\n");
        sql.append("            PZX.YEARV AS ACCTYEAR,\n");
        sql.append("            '${START_PERIOD}' AS ACCTPERIOD,\n");
        sql.append("            NULL AS ACCTDAY,\n");
        sql.append("            NULL AS VCHRTYPE,\n");
        sql.append("            CASE WHEN KM.BALANORIENT = 1 THEN 1 ELSE -1 END AS ORIENT,\n");
        sql.append("            KM.SUBJCODE SUBJECTCODE,\n");
        sql.append("            KM.SUBJNAME SUBJECTNAME\n");
        sql.append("            ${INASSFIELD} \n");
        sql.append(String.format("       ,%s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("            SUM(PZX.LOCALDEBITAMOUNT) AS DEBIT,\n");
        sql.append("            SUM(PZX.LOCALCREDITAMOUNT) AS CREDIT,\n");
        sql.append("            SUM(PZX.DEBITAMOUNT) AS ORGND,\n");
        sql.append("            SUM(PZX.CREDITAMOUNT) AS ORGNC,\n");
        sql.append("            SUM(PZX.LOCALDEBITAMOUNT - PZX.LOCALCREDITAMOUNT) AS YE,\n");
        sql.append("            SUM(PZX.DEBITAMOUNT - PZX.CREDITAMOUNT) AS ORGNYE,\n");
        sql.append(String.format("       %s AS ORDERNUM  \n", sqlHandler.toChar("'0'")));
        sql.append("     FROM  GL_DETAIL PZX \n");
        sql.append("     INNER JOIN GL_VOUCHER PZ ON PZ.PK_VOUCHER = PZX.PK_VOUCHER \n");
        sql.append("     INNER JOIN BD_GLORGBOOK BOOK ON PZ.PK_GLORGBOOK = BOOK.PK_GLORGBOOK \n");
        sql.append("     INNER JOIN BD_ACCSUBJ KM ON PZX.PK_ACCSUBJ = KM.PK_ACCSUBJ AND BOOK.PK_GLORGBOOK = KM.PK_GLORGBOOK \n");
        sql.append("     ${VOUCHERASSJOINSQL} \n");
        sql.append("     WHERE PZ.YEAR='${YEAR}'\n");
        sql.append("           AND PZ.VOUCHERKIND <> 255\n");
        sql.append("           AND KM.ENDFLAG = 'Y'\n");
        sql.append(this.buildSubjectCondi("KM", "SUBJCODE", condi.getSubjectCode()));
        if (!StringUtils.isEmpty((String)condi.getExcludeSubjectCode())) {
            sql.append(String.format(" AND KM.SUBJCODE NOT LIKE '%s%%%%' ", condi.getExcludeSubjectCode()));
        }
        sql.append("           AND PZ.DISCARDFLAG = 'N'\n");
        sql.append("           AND PZX.PERIODV <'${START_PERIOD}' \n");
        sql.append("           AND PZ.DR = 0\n");
        sql.append("           AND BOOK.GLORGBOOKCODE='${BOOKCODE}'\n");
        sql.append("           ${ASSCONDITION}");
        sql.append("     GROUP BY KM.SUBJCODE,KM.SUBJNAME,KM.BALANORIENT,PZX.YEARV ${INGROUPFIELD}\n");
        sql.append("     UNION ALL\n");
        sql.append("     SELECT\n");
        sql.append("            0 AS ROWTYPE,\n");
        sql.append("            PZX.PK_DETAIL AS ID,\n");
        sql.append("            PZ.PK_VOUCHER AS VCHRID,\n");
        sql.append("            PZX.YEARV AS ACCTYEAR,\n");
        sql.append("            PZX.PERIODV AS ACCTPERIOD ,\n");
        sql.append("            SUBSTR(PZ.PREPAREDDATE, 9, 2) AS ACCTDAY,\n");
        sql.append(sqlHandler.concatBySeparator("-", new String[]{"BD_VOUCHERTYPE.FORSHORT", sqlHandler.toChar("PZ.NO")})).append(" AS VCHRTYPE, \n");
        sql.append("            CASE WHEN KM.BALANORIENT = 1 THEN 1 ELSE -1 END AS ORIENT,\n");
        sql.append("            TO_CHAR(KM.SUBJCODE) SUBJECTCODE,\n");
        sql.append("            TO_CHAR(KM.SUBJNAME) SUBJECTNAME\n");
        sql.append("            ${INASSFIELD} \n");
        sql.append(String.format("       ,%s AS DIGEST,  \n", sqlHandler.toChar("PZX.EXPLANATION")));
        sql.append("            PZX.LOCALDEBITAMOUNT AS DEBIT,\n");
        sql.append("            PZX.LOCALCREDITAMOUNT AS CREDIT,\n");
        sql.append("            PZX.DEBITAMOUNT AS ORGND,\n");
        sql.append("            PZX.CREDITAMOUNT AS ORGNC,\n");
        sql.append("            0 AS YE,\n");
        sql.append("            0 AS ORGNYE,\n");
        sql.append(sqlHandler.concat(new String[]{"PZ.PREPAREDDATE", "PZ.NO", "PZX.DETAILINDEX"})).append(" AS ORDERNUM");
        sql.append("     FROM\n");
        sql.append("            GL_DETAIL PZX\n");
        sql.append("     INNER JOIN (\n");
        sql.append("            SELECT\n");
        sql.append("                    VCHR.PK_VOUCHER,\n");
        sql.append("                    VCHR.PREPAREDDATE,\n");
        sql.append("                    VCHR.PK_VOUCHERTYPE,\n");
        sql.append("                    VCHR.NO,\n");
        sql.append("                    PK_GLORGBOOK\n");
        sql.append("            FROM\n");
        sql.append("                    GL_VOUCHER VCHR\n");
        sql.append("            WHERE\n");
        sql.append("                    1 = 1\n");
        sql.append("                    AND VCHR.DISCARDFLAG = 'N'\n");
        sql.append("                    AND VCHR.DR = 0\n");
        sql.append("                    AND VCHR.VOUCHERKIND <> 255\n");
        sql.append("                    AND VCHR.YEAR = '${YEAR}'\n");
        sql.append("                    AND VCHR.PERIOD >= '${START_PERIOD}'\n");
        sql.append("                    AND VCHR.PERIOD <= '${END_PERIOD}') PZ\n");
        sql.append("            ON PZ.PK_VOUCHER = PZX.PK_VOUCHER\n");
        sql.append("     INNER JOIN BD_GLORGBOOK BOOK ON PZ.PK_GLORGBOOK = BOOK.PK_GLORGBOOK \n");
        sql.append("     INNER JOIN BD_ACCSUBJ KM ON PZX.PK_ACCSUBJ = KM.PK_ACCSUBJ AND BOOK.PK_GLORGBOOK = KM.PK_GLORGBOOK\n");
        sql.append("     LEFT JOIN BD_VOUCHERTYPE BD_VOUCHERTYPE ON BD_VOUCHERTYPE.PK_VOUCHERTYPE=PZ.PK_VOUCHERTYPE \n");
        sql.append("     ${VOUCHERASSJOINSQL} \n");
        sql.append("     WHERE 1=1\n");
        sql.append("           AND BOOK.GLORGBOOKCODE='${BOOKCODE}'\n");
        sql.append("           AND KM.ENDFLAG = 'Y'\n");
        sql.append("           ${ASSCONDITION}");
        sql.append(this.buildSubjectCondi("KM", "SUBJCODE", condi.getSubjectCode()));
        if (!StringUtils.isEmpty((String)condi.getExcludeSubjectCode())) {
            sql.append(String.format(" AND KM.SUBJCODE NOT LIKE '%s%%%%' ", condi.getExcludeSubjectCode()));
        }
        sql.append(" ) MAINTABLE ");
        sql.append("     ORDER BY MAINTABLE.ACCTPERIOD,  \n");
        sql.append("              ORDERNUM  \n");
        return sql.toString();
    }
}

