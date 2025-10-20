/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue
 *  com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nc6.penetrate.detailledger;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue;
import com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.nc6.BdeNc6PluginType;
import com.jiuqi.bde.plugin.nc6.assist.Nc6AssistPojo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractNc6DetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeNc6PluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        boolean containAssist = !CollectionUtils.isEmpty((Collection)assistMappingList) && !CollectionUtils.isEmpty((Collection)condi.getAssTypeList());
        HashMap penetrateAssistDimMap = condi.getAssTypeList() == null ? new HashMap() : condi.getAssTypeList().stream().collect(Collectors.toMap(Dimension::getDimCode, item -> item, (k1, k2) -> k2));
        UnitAndBookValue unitAndBookValue = OrgSqlUtil.getUnitAndBookValue((OrgMappingDTO)condi.getOrgMapping());
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        StringBuilder sql = null;
        sql = condi.getIncludeUncharged() != false ? this.getIncludeUnchargedSql(condi, containAssist) : this.getNotIncludeUnchargedSql(condi, containAssist);
        StringBuilder externalAllSelectSql = new StringBuilder();
        StringBuilder externalSelectSql = new StringBuilder();
        StringBuilder externalGroupSql = new StringBuilder();
        StringBuilder yeExternalAssistSql = new StringBuilder();
        StringBuilder voucherExternalAssistSql = new StringBuilder();
        StringBuilder assistJoinSql = new StringBuilder();
        String mainDocFreeTable = null;
        HashSet<String> docFreeSet = new HashSet<String>(3);
        for (AssistMappingBO assistMapping : assistMappingList) {
            externalAllSelectSql.append(String.format(",T.%1$s AS %1$s,T.%1$s_NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            externalSelectSql.append(String.format(",%1$s.CODE AS %1$s,%1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            externalGroupSql.append(String.format(",T.%1$s,T.%1$s_NAME", assistMapping.getAssistCode()));
            assistJoinSql.append(String.format(" INNER JOIN (%1$s) %2$s ON %2$s.ID = %3$s.F%4$d %5$s ", schemeMappingProvider.buildAssistSql(assistMapping), assistMapping.getAssistCode(), ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName(), ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableNum(), this.matchByRule(assistMapping.getAssistCode(), "CODE", ((Dimension)penetrateAssistDimMap.get(assistMapping.getAssistCode())).getDimValue(), ((Dimension)penetrateAssistDimMap.get(assistMapping.getAssistCode())).getDimRule())));
            if (mainDocFreeTable == null) {
                mainDocFreeTable = ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName();
                yeExternalAssistSql.append(String.format(" LEFT JOIN %1$s %1$s ON %1$s.ASSID=YE.ASSID\t\n", mainDocFreeTable));
                voucherExternalAssistSql.append(String.format(" LEFT JOIN %1$s %1$s ON %1$s.ASSID=PZX.ASSID  \n", mainDocFreeTable));
                docFreeSet.add(mainDocFreeTable);
                continue;
            }
            if (docFreeSet.contains(((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName())) continue;
            yeExternalAssistSql.append(String.format(" LEFT JOIN %1$s %1$s ON %1$s.ASSID = YE.ASSID \r\n", ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName()));
            voucherExternalAssistSql.append(String.format(" LEFT JOIN %1$s %1$s ON %1$s.ASSID = PZX.ASSID \r\n", ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName()));
            docFreeSet.add(((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName());
        }
        Variable variable = new Variable();
        variable.put("ASSIST_DIM_JOIN_SQL", assistJoinSql.toString());
        variable.put("EXTERNAL_ALL_SELECT_SQL", externalAllSelectSql.toString());
        variable.put("EXTERNAL_SELECT_SQL", externalSelectSql.toString());
        variable.put("EXTERNAL_GROUP_SQL", externalGroupSql.toString());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("EXTERNAL_CURRENCY_SQL", schemeMappingProvider.getCurrencySql());
        variable.put("YE_EXTERNAL_ASSIT_SQL", yeExternalAssistSql.toString());
        variable.put("VOUCHER_EXTERNAL_ASSIT_SQL", voucherExternalAssistSql.toString());
        variable.put("START_PERIOD", AbstractNc6DetailLedgerContentProvider.lpadPeriod((Integer)condi.getStartPeriod()));
        variable.put("END_PERIOD", AbstractNc6DetailLedgerContentProvider.lpadPeriod((Integer)condi.getEndPeriod()));
        variable.put("END_PRE_PERIOD", AbstractNc6DetailLedgerContentProvider.lpadPeriod((Integer)(condi.getStartPeriod() - 1)));
        variable.put("BOOKCODE", OrgSqlUtil.getOrgConditionSql((String)"MD_ACCTSUBJECT.BOOKCODE", (List)unitAndBookValue.getBookCodes()));
        variable.put("ACCTYEAR", String.valueOf(condi.getAcctYear()));
        variable.put("ORGCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("YEORGJOINSQL", !orgMappingType.equals("DEFAULT") ? "JOIN ORG_ORGS OORG ON YE.${ORGRELATEFIELD} = OORG.PK_ORG" : "");
        variable.put("VOUCHERORGJOINSQL", !orgMappingType.equals("DEFAULT") ? "JOIN ORG_ORGS OORG ON PZX.${ORGRELATEFIELD} = OORG.PK_ORG" : "");
        variable.put("ORGCODECONDITION", !orgMappingType.equals("DEFAULT") ? OrgSqlUtil.getOrgConditionSql((String)"OORG.CODE", (List)unitAndBookValue.getUnitCodes()) : "");
        variable.put("YEBOOKCODECONDITION", !orgMappingType.equals("UNIT") ? "AND YE.PK_ACCOUNTINGBOOK IN (SELECT PK_ACCOUNTINGBOOK FROM ORG_ACCOUNTINGBOOK WHERE 1=1 ${BOOKCONDITON})" : "");
        variable.put("VOUCHERBOOKCODECONDITION", !orgMappingType.equals("UNIT") ? "AND VCHR.PK_ACCOUNTINGBOOK IN (SELECT PK_ACCOUNTINGBOOK FROM ORG_ACCOUNTINGBOOK WHERE 1=1 ${BOOKCONDITON})" : "");
        variable.put("BOOKCONDITON", OrgSqlUtil.getOrgConditionSql((String)"CODE", (List)unitAndBookValue.getBookCodes()));
        variable.put("ORGRELATEFIELD", orgMappingType.equals("BUSINESSUNIT") ? "PK_UNIT" : "PK_ORG");
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(PenetrateUtil.replaceContext((String)querySql, (PenetrateBaseDTO)condi), new Object[0], (ResultSetExtractor)new DetailLedgerResultSetExtractor(assistMappingList));
        return queryParam;
    }

    private StringBuilder getIncludeUnchargedSql(PenetrateBaseDTO condi, boolean containAssist) {
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        StringBuilder sql = new StringBuilder();
        sql.append("Select 3 AS ROWTYPE,  \n");
        sql.append("       NULL AS ID,  \n");
        sql.append("       NULL AS VCHRID,  \n");
        sql.append("       T.ACCTYEAR as ACCTYEAR,  \n");
        sql.append(String.format("       '%d' as ACCTPERIOD,  \n", condi.getStartPeriod()));
        sql.append("       NULL AS ACCTDAY,  \n");
        sql.append("       NULL AS VCHRTYPE,  \n");
        sql.append("       T.ORIENT as ORIENT,  \n");
        sql.append("       ").append(sqlHandler.toChar("T.SUBJECTCODE")).append(" as SUBJECTCODE,  \n");
        sql.append("       ").append(sqlHandler.toChar("T.SUBJECTNAME")).append(" as SUBJECTNAME  \n");
        sql.append("       ${EXTERNAL_ALL_SELECT_SQL}  \n");
        sql.append(String.format("       ,%s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("       SUM(T.DEBIT)       as DEBIT,  \n");
        sql.append("       SUM(T.CREDIT)       as CREDIT,  \n");
        sql.append("       SUM(T.ORGND)       as ORGND,  \n");
        sql.append("       SUM(T.ORGNC)       as ORGNC,  \n");
        sql.append("       SUM(T.DEBIT - T.CREDIT) as YE,  \n");
        sql.append("       SUM(T.ORGND - T.ORGNC)  as ORGNYE,  \n");
        sql.append("       ").append(sqlHandler.toChar("'0'")).append(" as ORDERNUM  \n");
        sql.append("  FROM (  \n");
        sql.append("        SELECT 3 AS ROWTYPE,  \n");
        sql.append("        NULL AS ID,  \n");
        sql.append("        NULL AS VCHRID,  \n");
        sql.append("        YE.YEAR AS ACCTYEAR,  \n");
        sql.append("        YE.PERIOD AS ACCTPERIOD,  \n");
        sql.append("        NULL AS ACCTDAY,  \n");
        sql.append("        NULL AS VCHRTYPE,  \n");
        sql.append("               MD_ACCTSUBJECT.ORIENT,  \n");
        sql.append("               MD_ACCTSUBJECT.CODE SUBJECTCODE,  \n");
        sql.append("               MD_ACCTSUBJECT.NAME SUBJECTNAME  \n");
        sql.append("               ${EXTERNAL_SELECT_SQL}  \n");
        sql.append(String.format("       ,%s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("       YE.LOCALDEBITAMOUNT AS DEBIT,  \n");
        sql.append("       YE.LOCALCREDITAMOUNT AS CREDIT,  \n");
        sql.append("       YE.DEBITAMOUNT AS ORGND,  \n");
        sql.append("       YE.CREDITAMOUNT AS ORGNC  \n");
        sql.append("          FROM GL_BALANCE YE  \n");
        sql.append("         INNER JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = YE.PK_ACCASOA ");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("\t${BOOKCODE} \n");
        }
        if (containAssist) {
            sql.append("         ${YE_EXTERNAL_ASSIT_SQL} \n");
            sql.append("\t     ${ASSIST_DIM_JOIN_SQL} \n");
        }
        sql.append("         ${YEORGJOINSQL} \n");
        sql.append("         INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON YE.PK_CURRTYPE = HB.ID \n");
        sql.append("         WHERE YE.YEAR = '${ACCTYEAR}'  \n");
        sql.append("           AND YE.PERIOD = '00'  \n");
        sql.append("           ${ORGCODECONDITION}  \n");
        sql.append("\t\t   ${YEBOOKCODECONDITION}");
        sql.append("           AND YE.PK_UNIT != 'N/A'  \n");
        sql.append(this.buildSubjectCondi("MD_ACCTSUBJECT", "CODE", condi.getSubjectCode()));
        if (!StringUtils.isEmpty((String)condi.getExcludeSubjectCode())) {
            sql.append(String.format(" AND MD_ACCTSUBJECT.CODE NOT LIKE '%s%%%%' ", condi.getExcludeSubjectCode()));
        }
        sql.append("        UNION ALL  \n");
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       PZX.PK_DETAIL AS ID,  \n");
        sql.append("       PZ.PK_VOUCHER AS VCHRID,  \n");
        sql.append("       PZX.YEARV AS ACCTYEAR,  \n");
        sql.append("       PZX.PERIODV AS ACCTPERIOD ,  \n");
        sql.append("       ").append(sqlHandler.SubStr("PZ.PREPAREDDATE", "9", "2")).append(" AS ACCTDAY, \n");
        sql.append(sqlHandler.concatBySeparator("-", new String[]{"BD_VOUCHERTYPE.SHORTNAME", sqlHandler.toChar("PZ.NUM")})).append(" AS VCHRTYPE, \n");
        sql.append("       MD_ACCTSUBJECT.ORIENT,  \n");
        sql.append("       MD_ACCTSUBJECT.CODE SUBJECTCODE,  \n");
        sql.append("       MD_ACCTSUBJECT.NAME SUBJECTNAME  \n");
        sql.append("   ${EXTERNAL_SELECT_SQL}  \n");
        sql.append(String.format("       ,%s AS DIGEST,  \n", sqlHandler.toChar("PZX.EXPLANATION")));
        sql.append("       PZX.LOCALDEBITAMOUNT  AS DEBIT,  \n");
        sql.append("       PZX.LOCALCREDITAMOUNT AS CREDIT,  \n");
        sql.append("       PZX.DEBITAMOUNT       AS ORGND,  \n");
        sql.append("       PZX.CREDITAMOUNT      AS ORGNC  \n");
        sql.append("       FROM GL_DETAIL PZX  \n");
        sql.append("         INNER JOIN (SELECT VCHR.PK_VOUCHER,VCHR.PREPAREDDATE,VCHR.PK_VOUCHERTYPE,VCHR.NUM   \n");
        sql.append("                      FROM GL_VOUCHER VCHR  \n");
        sql.append("                     WHERE 1=1 \n");
        sql.append("                       AND VCHR.DISCARDFLAG = 'N'  \n");
        sql.append("                       AND VCHR.DR = 0  \n");
        sql.append("\t\t\t\t\t   ${VOUCHERBOOKCODECONDITION}");
        sql.append("                       AND VCHR.VOUCHERKIND <> 255  \n");
        sql.append(this.getErrmessageCondi());
        sql.append("                       AND VCHR.YEAR = '${ACCTYEAR}'  \n");
        sql.append("                       AND VCHR.PERIOD <= '${END_PRE_PERIOD}'  \n");
        sql.append("                       AND VCHR.PERIOD > '00') PZ  \n");
        sql.append("            ON PZ.PK_VOUCHER = PZX.PK_VOUCHER  \n");
        sql.append("         ${VOUCHERORGJOINSQL} \n");
        sql.append("         INNER JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = PZX.PK_ACCASOA ");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("\t\t${BOOKCODE} \n");
        }
        if (containAssist) {
            sql.append("         ${VOUCHER_EXTERNAL_ASSIT_SQL} \n");
            sql.append("\t     ${ASSIST_DIM_JOIN_SQL} \n");
        }
        sql.append("         INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON PZX.PK_CURRTYPE = HB.ID \n");
        sql.append("\tINNER JOIN BD_VOUCHERTYPE BD_VOUCHERTYPE ON BD_VOUCHERTYPE.PK_VOUCHERTYPE=PZ.PK_VOUCHERTYPE \n");
        sql.append("         WHERE 1 = 1  \n");
        sql.append("          \t  ${ORGCODECONDITION}  \n");
        sql.append(this.buildSubjectCondi("MD_ACCTSUBJECT", "CODE", condi.getSubjectCode()));
        if (!StringUtils.isEmpty((String)condi.getExcludeSubjectCode())) {
            sql.append(String.format(" AND MD_ACCTSUBJECT.CODE NOT LIKE '%s%%%%' ", condi.getExcludeSubjectCode()));
        }
        sql.append("   ) T   GROUP BY T.ACCTYEAR,  \n");
        sql.append("                  T.SUBJECTCODE,  \n");
        sql.append("                  T.SUBJECTNAME,  \n");
        sql.append("                  T.ORIENT  \n");
        sql.append("                  ${EXTERNAL_GROUP_SQL}  \n");
        sql.append("        UNION ALL  \n");
        sql.append("SELECT T.* FROM (  \n");
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       PZX.PK_DETAIL AS ID,  \n");
        sql.append("       PZ.PK_VOUCHER AS VCHRID,  \n");
        sql.append("       PZX.YEARV AS ACCTYEAR,  \n");
        sql.append("       PZX.PERIODV AS ACCTPERIOD ,  \n");
        sql.append("       ").append(sqlHandler.SubStr("PZ.PREPAREDDATE", "9", "2")).append(" AS ACCTDAY, \n");
        sql.append(sqlHandler.concatBySeparator("-", new String[]{"BD_VOUCHERTYPE.SHORTNAME", sqlHandler.toChar("PZ.NUM")})).append(" AS VCHRTYPE, \n");
        sql.append("       MD_ACCTSUBJECT.ORIENT,  \n");
        sql.append("       ").append(sqlHandler.toChar("MD_ACCTSUBJECT.CODE")).append(" as SUBJECTCODE,  \n");
        sql.append("       ").append(sqlHandler.toChar("MD_ACCTSUBJECT.NAME")).append(" as SUBJECTNAME  \n");
        sql.append("   ${EXTERNAL_SELECT_SQL}  \n");
        sql.append(String.format("       ,%s AS DIGEST,  \n", sqlHandler.toChar("PZX.EXPLANATION")));
        sql.append("       PZX.LOCALDEBITAMOUNT  AS DEBIT,  \n");
        sql.append("       PZX.LOCALCREDITAMOUNT AS CREDIT,  \n");
        sql.append("       PZX.DEBITAMOUNT       AS ORGND,  \n");
        sql.append("       PZX.CREDITAMOUNT      AS ORGNC,  \n");
        sql.append("       0                     AS YE,  \n");
        sql.append("       0                     AS ORGNYE,  \n");
        sql.append("                       ").append(sqlHandler.concatBySeparator("-", new String[]{"PZ.PREPAREDDATE", sqlHandler.toChar("PZ.NUM"), sqlHandler.toChar("PZX.DETAILINDEX")})).append(" AS ORDERNUM \n");
        sql.append("       FROM GL_DETAIL PZX  \n");
        sql.append("         INNER JOIN (SELECT VCHR.PK_VOUCHER,VCHR.PREPAREDDATE,VCHR.PK_VOUCHERTYPE,VCHR.NUM   \n");
        sql.append("                      FROM GL_VOUCHER VCHR  \n");
        sql.append("                     WHERE 1=1  \n");
        sql.append("                       AND VCHR.DISCARDFLAG = 'N'  \n");
        sql.append("                       AND VCHR.DR = 0  \n");
        sql.append("\t\t\t\t\t   ${VOUCHERBOOKCODECONDITION}");
        sql.append("                       AND VCHR.VOUCHERKIND <> 255  \n");
        sql.append(this.getErrmessageCondi());
        sql.append("                       AND VCHR.YEAR = '${ACCTYEAR}'  \n");
        sql.append("                       AND VCHR.PERIOD >= '${START_PERIOD}'  \n");
        sql.append("                       AND VCHR.PERIOD <= '${END_PERIOD}'  \n");
        sql.append("                       AND VCHR.PERIOD > '00') PZ  \n");
        sql.append("            ON PZ.PK_VOUCHER = PZX.PK_VOUCHER  \n");
        sql.append("         \t\t\t ${VOUCHERORGJOINSQL} \n");
        sql.append("         INNER JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = PZX.PK_ACCASOA ");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("\t\t${BOOKCODE} \n");
        }
        if (containAssist) {
            sql.append("         ${VOUCHER_EXTERNAL_ASSIT_SQL} \n");
            sql.append("\t     ${ASSIST_DIM_JOIN_SQL} \n");
        }
        sql.append("         INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON PZX.PK_CURRTYPE = HB.ID \n");
        sql.append("\tINNER JOIN BD_VOUCHERTYPE BD_VOUCHERTYPE ON BD_VOUCHERTYPE.PK_VOUCHERTYPE=PZ.PK_VOUCHERTYPE \n");
        sql.append("         WHERE 1 = 1  \n");
        sql.append("           \t   ${ORGCODECONDITION}  \n");
        sql.append(this.buildSubjectCondi("MD_ACCTSUBJECT", "CODE", condi.getSubjectCode()));
        if (!StringUtils.isEmpty((String)condi.getExcludeSubjectCode())) {
            String.format(" AND MD_ACCTSUBJECT.CODE NOT LIKE '%s%%%%' ", condi.getExcludeSubjectCode());
        }
        sql.append("          ) T ORDER BY ROWTYPE DESC,ORDERNUM \n");
        return sql;
    }

    private StringBuilder getNotIncludeUnchargedSql(PenetrateBaseDTO condi, boolean containAssist) {
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        StringBuilder sql = new StringBuilder();
        sql.append("Select 3 AS ROWTYPE,  \n");
        sql.append("       NULL AS ID,  \n");
        sql.append("       NULL AS VCHRID,  \n");
        sql.append("       T.ACCTYEAR as ACCTYEAR,  \n");
        sql.append(String.format("       '%d' as ACCTPERIOD,  \n", condi.getStartPeriod()));
        sql.append("       NULL AS ACCTDAY,  \n");
        sql.append("       NULL AS VCHRTYPE,  \n");
        sql.append("       T.ORIENT as ORIENT,  \n");
        sql.append("       ").append(sqlHandler.toChar("T.SUBJECTCODE")).append(" as SUBJECTCODE,  \n");
        sql.append("       ").append(sqlHandler.toChar("T.SUBJECTNAME")).append(" as SUBJECTNAME  \n");
        sql.append("       ${EXTERNAL_ALL_SELECT_SQL}  \n");
        sql.append(String.format("       ,%s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("       SUM(T.DEBIT)       as DEBIT,  \n");
        sql.append("       SUM(T.CREDIT)       as CREDIT,  \n");
        sql.append("       SUM(T.ORGND)       as ORGND,  \n");
        sql.append("       SUM(T.ORGNC)       as ORGNC,  \n");
        sql.append("       SUM(T.DEBIT - T.CREDIT) as YE,  \n");
        sql.append("       SUM(T.ORGND - T.ORGNC)  as ORGNYE,  \n");
        sql.append("       TO_CHAR('0') AS ORDERNUM  \n");
        sql.append("  FROM (  \n");
        sql.append("        SELECT 3 AS ROWTYPE,  \n");
        sql.append("        NULL AS ID,  \n");
        sql.append("        NULL AS VCHRID,  \n");
        sql.append("        YE.YEAR AS ACCTYEAR,  \n");
        sql.append("        YE.PERIOD AS ACCTPERIOD,  \n");
        sql.append("        NULL AS ACCTDAY,  \n");
        sql.append("        NULL AS VCHRTYPE,  \n");
        sql.append("               MD_ACCTSUBJECT.ORIENT,  \n");
        sql.append("               MD_ACCTSUBJECT.CODE SUBJECTCODE,  \n");
        sql.append("               MD_ACCTSUBJECT.NAME SUBJECTNAME  \n");
        sql.append("               ${EXTERNAL_SELECT_SQL}  \n");
        sql.append(String.format("       ,%s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("       YE.LOCALDEBITAMOUNT AS DEBIT,  \n");
        sql.append("       YE.LOCALCREDITAMOUNT AS CREDIT,  \n");
        sql.append("       YE.DEBITAMOUNT AS ORGND,  \n");
        sql.append("       YE.CREDITAMOUNT AS ORGNC  \n");
        sql.append("          FROM GL_BALANCE YE  \n");
        sql.append("         INNER JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = YE.PK_ACCASOA ");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("\t\t${BOOKCODE} \n");
        }
        if (containAssist) {
            sql.append("         ${YE_EXTERNAL_ASSIT_SQL} \n");
            sql.append("\t     ${ASSIST_DIM_JOIN_SQL} \n");
        }
        sql.append("         ${YEORGJOINSQL} \n");
        sql.append("         INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON YE.PK_CURRTYPE = HB.ID \n");
        sql.append("         WHERE YE.YEAR = '${ACCTYEAR}'  \n");
        sql.append("           AND YE.PERIOD < '${START_PERIOD}'  \n");
        sql.append("           ${ORGCODECONDITION}  \n");
        sql.append("\t\t   ${YEBOOKCODECONDITION}");
        sql.append("           AND YE.PK_UNIT != 'N/A'  \n");
        sql.append(this.buildSubjectCondi("MD_ACCTSUBJECT", "CODE", condi.getSubjectCode()));
        if (!StringUtils.isEmpty((String)condi.getExcludeSubjectCode())) {
            sql.append(String.format(" AND MD_ACCTSUBJECT.CODE NOT LIKE '%s%%%%' ", condi.getExcludeSubjectCode()));
        }
        sql.append("   ) T   GROUP BY T.ACCTYEAR,  \n");
        sql.append("                  T.SUBJECTCODE,  \n");
        sql.append("                  T.SUBJECTNAME,  \n");
        sql.append("                  T.ORIENT  \n");
        sql.append("                  ${EXTERNAL_GROUP_SQL}  \n");
        sql.append("       UNION ALL  \n");
        sql.append("SELECT * FROM (  \n");
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       PZX.PK_DETAIL AS ID,  \n");
        sql.append("       PZ.PK_VOUCHER AS VCHRID,  \n");
        sql.append("       PZX.YEARV AS ACCTYEAR,  \n");
        sql.append("       PZX.PERIODV AS ACCTPERIOD ,  \n");
        sql.append("       ").append(sqlHandler.SubStr("PZ.PREPAREDDATE", "9", "2")).append(" AS ACCTDAY, \n");
        sql.append(sqlHandler.concatBySeparator("-", new String[]{"BD_VOUCHERTYPE.SHORTNAME", sqlHandler.toChar("PZ.NUM")})).append(" AS VCHRTYPE, \n");
        sql.append("       MD_ACCTSUBJECT.ORIENT,  \n");
        sql.append("       ").append(sqlHandler.toChar("MD_ACCTSUBJECT.CODE")).append(" as SUBJECTCODE,  \n");
        sql.append("       ").append(sqlHandler.toChar("MD_ACCTSUBJECT.NAME")).append(" as SUBJECTNAME  \n");
        sql.append("   ${EXTERNAL_SELECT_SQL}  \n");
        sql.append(String.format("       ,%s AS DIGEST,  \n", sqlHandler.toChar("PZX.EXPLANATION")));
        sql.append("       PZX.LOCALDEBITAMOUNT  AS DEBIT,  \n");
        sql.append("       PZX.LOCALCREDITAMOUNT AS CREDIT,  \n");
        sql.append("       PZX.DEBITAMOUNT       AS ORGND,  \n");
        sql.append("       PZX.CREDITAMOUNT      AS ORGNC,  \n");
        sql.append("       0                     AS YE,  \n");
        sql.append("       0                     AS ORGNYE,  \n");
        sql.append("                       ").append(sqlHandler.concatBySeparator("-", new String[]{"PZ.PREPAREDDATE", sqlHandler.toChar("PZ.NUM"), sqlHandler.toChar("PZX.DETAILINDEX")})).append(" AS ORDERNUM \n");
        sql.append("       FROM GL_DETAIL PZX  \n");
        sql.append("         INNER JOIN (SELECT VCHR.PK_VOUCHER,VCHR.PREPAREDDATE,VCHR.PK_VOUCHERTYPE,VCHR.NUM   \n");
        sql.append("                      FROM GL_VOUCHER VCHR  \n");
        sql.append("                     WHERE 1=1  \n");
        sql.append("                       AND VCHR.DISCARDFLAG = 'N'  \n");
        sql.append("                       AND VCHR.DR = 0  \n");
        sql.append("                       AND VCHR.VOUCHERKIND <> 255  \n");
        sql.append(this.getErrmessageCondi());
        sql.append("\t\t\t\t\t   ${VOUCHERBOOKCODECONDITION}");
        sql.append("                       AND VCHR.YEAR = '${ACCTYEAR}'  \n");
        sql.append("                       AND VCHR.PERIOD >= '${START_PERIOD}'  \n");
        sql.append("                       AND VCHR.PERIOD <= '${END_PERIOD}'  \n");
        sql.append("                       AND VCHR.PK_MANAGER <>'N/A'  \n");
        sql.append("                       AND VCHR.PERIOD > '00') PZ  \n");
        sql.append("            ON PZ.PK_VOUCHER = PZX.PK_VOUCHER  \n");
        sql.append("         \t\t\t ${VOUCHERORGJOINSQL} \n");
        sql.append("         INNER JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = PZX.PK_ACCASOA ");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("\t\t${BOOKCODE} \n");
        }
        if (containAssist) {
            sql.append("         ${VOUCHER_EXTERNAL_ASSIT_SQL} \n");
            sql.append("\t     ${ASSIST_DIM_JOIN_SQL} \n");
        }
        sql.append("         INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON PZX.PK_CURRTYPE = HB.ID \n");
        sql.append("\tINNER JOIN BD_VOUCHERTYPE BD_VOUCHERTYPE ON BD_VOUCHERTYPE.PK_VOUCHERTYPE=PZ.PK_VOUCHERTYPE \n");
        sql.append("         WHERE 1 = 1  \n");
        sql.append("           \t\t\t   ${ORGCODECONDITION}  \n");
        sql.append(this.buildSubjectCondi("MD_ACCTSUBJECT", "CODE", condi.getSubjectCode()));
        if (!StringUtils.isEmpty((String)condi.getExcludeSubjectCode())) {
            sql.append(String.format(" AND MD_ACCTSUBJECT.CODE NOT LIKE '%s%%%%' ", condi.getExcludeSubjectCode()));
        }
        sql.append("          ) T ORDER BY ROWTYPE DESC,ORDERNUM \n");
        return sql;
    }

    protected String getErrmessageCondi() {
        return "                       AND VCHR.ERRMESSAGE = '~'  \n";
    }
}

