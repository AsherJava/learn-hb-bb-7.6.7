/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  com.jiuqi.dc.mappingscheme.impl.util.AdvanceSqlParser
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.bde.plugin.sap.penetrate.detailledger;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.sap.BdeSapPluginType;
import com.jiuqi.bde.plugin.sap.util.SapDataSchemeMappingProvider;
import com.jiuqi.bde.plugin.sap.util.SapFetchUtil;
import com.jiuqi.bde.plugin.sap.util.SapOrgMappingType;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.dc.mappingscheme.impl.util.AdvanceSqlParser;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import tk.mybatis.mapper.util.StringUtil;

public abstract class AbstractSapDetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeSapPluginType pluginType;
    @Autowired
    private AdvanceSqlParser sqlParser;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        StringBuilder sql = new StringBuilder();
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        SapDataSchemeMappingProvider schemeMappingProvider = new SapDataSchemeMappingProvider(fetchCondi);
        List<AssistMappingBO<BaseAcctAssist>> balAssistMappingList = schemeMappingProvider.getAssistMappingList();
        SapOrgMappingType orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? SapOrgMappingType.DEFAULT : SapOrgMappingType.fromCode(schemeMappingProvider.getOrgMappingType());
        BalanceCondition vchrCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)ComputationModelEnum.VOUCHER.getCode());
        SapDataSchemeMappingProvider vchSchemeMappingProvider = new SapDataSchemeMappingProvider(vchrCondi);
        List<AssistMappingBO<BaseAcctAssist>> vchrAssistMappingList = vchSchemeMappingProvider.getAssistMappingList();
        OrgMappingDTO orgMapping = condi.getOrgMapping();
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        String subjectSql = schemeMappingProvider.getSubjectSql();
        List columnList = this.sqlParser.parseSql(condi.getOrgMapping().getDataSourceCode(), subjectSql);
        Set columnSet = columnList.stream().map(item -> item.getName().toUpperCase()).collect(Collectors.toSet());
        boolean containMandt = columnSet.contains("MANDT");
        boolean containBookCode = columnSet.contains("KTOPL") && !StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode());
        boolean containBukrs = columnSet.contains("BUKRS");
        boolean includePlData = SapFetchUtil.isIncludePlData(orgMapping.getDataSchemeCode());
        sql.append("Select ROWTYPE AS ROWTYPE,  \n");
        sql.append("       A.ID AS ID,  \n");
        sql.append("       A.VCHRID AS VCHRID,  \n");
        sql.append("       A.acctyear as ACCTYEAR,  \n");
        sql.append("       A.acctmonth as ACCTPERIOD,  \n");
        sql.append("       A.acctday as ACCTDAY,  \n");
        sql.append("       A.VCHRTYPE as VCHRTYPE,  \n");
        sql.append("       A.MD_ACCTSUBJECT as SUBJECTCODE,  \n");
        sql.append("       A.MD_ACCTSUBJECT_NAME as SUBJECTNAME  \n");
        sql.append("   ${EXTERNAL_TOTAL_SELECT_SQL}  \n");
        sql.append("       ,1 as ORIENT,  \n");
        sql.append("       A.digest as DIGEST,  \n");
        sql.append("       A.DEBIT as DEBIT,  \n");
        sql.append("       A.ORGND as ORGND,  \n");
        sql.append("       A.CREDIT as CREDIT,  \n");
        sql.append("       A.ORGNC as ORGNC,  \n");
        sql.append("       A.ye as YE,  \n");
        sql.append("       A.ORGNYE as ORGNYE  \n");
        sql.append("  From (Select 2 AS ROWTYPE,  \n");
        sql.append("               null AS ID,  \n");
        sql.append("               null AS VCHRID,  \n");
        sql.append("               NULL AS ClientID,  \n");
        sql.append(String.format("               TO_CHAR(%d) AS  acctyear,  \n", condi.getAcctYear()));
        sql.append(String.format("               '%d'  AS  acctmonth,  \n", condi.getStartPeriod()));
        sql.append("               null AS  acctday,  \n");
        sql.append("               NULL AS MD_ACCTSUBJECT,  \n");
        sql.append("               NULL AS MD_ACCTSUBJECT_NAME  \n");
        sql.append("   ${EXTERNAL_BALANCE_SELECT_SQL}  \n");
        sql.append("               ,''   AS VCHRTYPE,  \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("               0  AS DEBIT,  \n");
        sql.append("               0  AS ORGND,  \n");
        sql.append("               0  AS CREDIT,  \n");
        sql.append("               0  AS ORGNC,  \n");
        StringBuilder periodSql = new StringBuilder();
        StringBuilder orgnPeriodSql = new StringBuilder();
        StringBuilder adjustPeriodSql = new StringBuilder();
        StringBuilder orgnAdjustPeriodSql = new StringBuilder();
        String startAdjustPeriod = condi.getStartAdjustPeriod();
        String endAdjustPeriod = condi.getEndAdjustPeriod();
        if (StringUtil.isNotEmpty((String)startAdjustPeriod) && StringUtil.isNotEmpty((String)endAdjustPeriod)) {
            int start = Integer.parseInt(startAdjustPeriod);
            int end = Integer.parseInt(endAdjustPeriod);
            for (int i = start; i <= end; ++i) {
                adjustPeriodSql.append(" + ").append(sqlHandler.nullToValue(String.format("HSL%s", CommonUtil.lpad((String)String.valueOf(i), (String)"0", (int)2)), "0"));
                orgnAdjustPeriodSql.append(" + ").append(sqlHandler.nullToValue(String.format("TSL%s", CommonUtil.lpad((String)String.valueOf(i), (String)"0", (int)2)), "0"));
            }
        }
        for (int i = 1; i < condi.getStartPeriod(); ++i) {
            periodSql.append(" + ").append(sqlHandler.nullToValue(String.format("HSL%s", CommonUtil.lpad((String)String.valueOf(i), (String)"0", (int)2)), "0"));
            orgnPeriodSql.append(" + ").append(sqlHandler.nullToValue(String.format("TSL%s", CommonUtil.lpad((String)String.valueOf(i), (String)"0", (int)2)), "0"));
        }
        if (includePlData) {
            sql.append(String.format("         SUM(CASE WHEN (KTOKS = 'PL' and T.RYEAR <= '${YEAR}') OR (KTOKS != 'PL' and T.RYEAR = '${YEAR}') THEN %1$s %2$s %3$s ELSE 0 END) AS YE,\n", sqlHandler.nullToValue("HSLVT", "0"), periodSql.toString(), adjustPeriodSql.toString()));
            sql.append(String.format("         SUM(CASE WHEN (KTOKS = 'PL' and T.RYEAR <= '${YEAR}') OR (KTOKS != 'PL' and T.RYEAR = '${YEAR}') THEN %1$s %2$s %3$s ELSE 0 END) AS ORGNYE,\n", sqlHandler.nullToValue("TSLVT", "0"), orgnPeriodSql.toString(), orgnAdjustPeriodSql.toString()));
        } else {
            sql.append(String.format("         SUM(%1$s %2$s %3$s) AS YE,\n", sqlHandler.nullToValue("HSLVT", "0"), periodSql.toString(), adjustPeriodSql.toString()));
            sql.append(String.format("         SUM(%1$s %2$s %3$s) AS ORGNYE,\n", sqlHandler.nullToValue("TSLVT", "0"), orgnPeriodSql.toString(), orgnAdjustPeriodSql.toString()));
        }
        sql.append("               '0' AS ORDERNO  \n");
        sql.append("          FROM FAGLFLEXT T  \n");
        sql.append("   LEFT JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.CODE = T.RACCT \n");
        if (containMandt) {
            sql.append("    AND MD_ACCTSUBJECT.MANDT = T.RCLNT \n");
        }
        if (containBookCode) {
            sql.append(String.format("           And MD_ACCTSUBJECT.KTOPL = '%1$s'  \n", condi.getOrgMapping().getAcctBookCode()));
        }
        if (containBukrs) {
            sql.append(" AND MD_ACCTSUBJECT.BUKRS  = '${UNITCODE}' ");
        }
        sql.append("   ${EXTERNAL_BALANCE_JOIN_SQL}  \n");
        sql.append("         Where 1 = 1  \n");
        if (!StringUtils.isEmpty((String)condi.getAcctOrgCode())) {
            sql.append(String.format("    AND T.RBUKRS = '%1$s' \n", condi.getAcctOrgCode()));
        } else {
            sql.append(SapFetchUtil.buildUnitSql("T.RBUKRS", orgMappingType, condi.getOrgMapping()));
        }
        if (!StringUtils.isEmpty((String)condi.getAssistCode())) {
            sql.append(String.format("    AND T.%1$s = '%2$s' \n", new Object[]{orgMappingType, condi.getAssistCode()}));
        } else {
            sql.append(SapFetchUtil.buildAssistSql("T.RBUKRS", "T.PRCTR", orgMappingType, condi.getOrgMapping()));
        }
        if (!includePlData) {
            sql.append("AND T.RYEAR = '${YEAR}'");
        }
        sql.append(String.format("   AND T.RACCT LIKE '%s%%%%'  \n", condi.getSubjectCode()));
        if (!StringUtils.isEmpty((String)condi.getCurrencyCode())) {
            sql.append(String.format("AND T.RTCUR = '%s'", condi.getCurrencyCode()));
        }
        sql.append("   ${EXTERNAL_BALANCE_CONDI_SQL}  \n");
        sql.append("   GROUP BY T.RACCT  \n");
        sql.append("   ${EXTERNAL_BALANCE_GROUP_SQL}   \n");
        sql.append("        UNION ALL  \n");
        sql.append("        SELECT 0 AS ROWTYPE,  \n");
        sql.append("               T.MANDT || '|' || MASTER.BUKRS || '|' || TO_CHAR(MASTER.GJAHR) || '|' || TO_CHAR(RTRIM(T.BELNR)) || '|' || T.BUZEI  AS ID,  \n");
        sql.append("               T.MANDT || '|' || MASTER.BUKRS || '|' || TO_CHAR(MASTER.GJAHR) || '|' || TO_CHAR(RTRIM(T.BELNR))  AS VCHRID,  \n");
        sql.append("               T.MANDT CLIENTID,  \n");
        sql.append("               TO_CHAR(MASTER.GJAHR) ACCTYEAR,  \n");
        sql.append("               TO_CHAR(MASTER.MONAT) ACCTMONTH,  \n");
        sql.append(String.format("       %s AS ACCTDAY,  \n", sqlHandler.day(sqlHandler.toDate("MASTER.BUDAT", "'yyyyMMdd'"))));
        sql.append("               T.HKONT SUBJECTCODE,  \n");
        sql.append("               MD_ACCTSUBJECT.NAME AS MD_ACCTSUBJECT_NAME  \n");
        sql.append("               ${EXTERNAL_VCHR_SELECT_SQL}  \n");
        sql.append("              ,TO_CHAR(RTRIM(Master.BLART)) || '-' || Master.BELNR AS VCHRTYPE,  \n");
        sql.append("               T.SGTXT AS DIGEST,  \n");
        sql.append("               CASE WHEN T.XNEGP = 'X' THEN CASE WHEN T.SHKZG = 'S' THEN 0 ELSE -1 * T.DMBTR END ELSE CASE WHEN T.SHKZG = 'S' THEN T.DMBTR ELSE 0 END END AS DEBIT,  \n");
        sql.append("               CASE WHEN T.XNEGP = 'X' THEN CASE WHEN T.SHKZG = 'S' THEN 0 ELSE -1 * T.WRBTR END ELSE CASE WHEN T.SHKZG = 'S' THEN T.WRBTR ELSE 0 END END AS ORGND,  \n");
        sql.append("               CASE WHEN T.XNEGP = 'X' THEN CASE WHEN T.SHKZG = 'H' THEN 0 ELSE -1 * T.DMBTR END ELSE CASE WHEN T.SHKZG = 'H' THEN T.DMBTR ELSE 0 END END AS CREDIT,  \n");
        sql.append("               CASE WHEN T.XNEGP = 'X' THEN CASE WHEN T.SHKZG = 'H' THEN 0 ELSE -1 * T.WRBTR END ELSE CASE WHEN T.SHKZG = 'H' THEN T.WRBTR ELSE 0 END END AS ORGNC,  \n");
        sql.append("               0 AS YE,  \n");
        sql.append("               0 AS ORGNYE,  \n");
        sql.append("               TO_CHAR(TO_DATE(MASTER.BUDAT, 'YYYYMMDD')) || TO_CHAR(MASTER.BELNR) || T.BUZEI AS ORDERNO  \n");
        sql.append("          FROM BKPF MASTER  \n");
        sql.append("          JOIN BSEG T ON  MASTER.MANDT = T.MANDT AND MASTER.BUKRS = T.BUKRS AND MASTER.BELNR = T.BELNR AND MASTER.GJAHR = T.GJAHR \n");
        sql.append("          LEFT JOIN T003T PZZ ON MASTER.MANDT = PZZ.MANDT AND MASTER.BLART = PZZ.BLART AND PZZ.SPRAS = '1' \n");
        sql.append("          LEFT JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.CODE = T.HKONT \n");
        if (containMandt) {
            sql.append("    AND MD_ACCTSUBJECT.MANDT = MASTER.MANDT  \n");
        }
        if (containBookCode) {
            sql.append(String.format("           And MD_ACCTSUBJECT.KTOPL = '%1$s'  \n", condi.getOrgMapping().getAcctBookCode()));
        }
        if (containBukrs) {
            sql.append(SapFetchUtil.buildUnitSql("MD_ACCTSUBJECT.BUKRS", orgMappingType, orgMapping));
        }
        sql.append("   ${EXTERNAL_VCHR_JOIN_SQL}  \n");
        sql.append("         WHERE 1 = 1  \n");
        if (!StringUtils.isEmpty((String)condi.getAcctOrgCode())) {
            sql.append(String.format("    AND MASTER.BUKRS = '%1$s' \n", condi.getAcctOrgCode()));
        } else {
            sql.append(SapFetchUtil.buildUnitSql("MASTER.BUKRS", orgMappingType, condi.getOrgMapping()));
        }
        if (!StringUtils.isEmpty((String)condi.getAssistCode())) {
            sql.append(String.format("    AND T.PRCTR = '%1$s' \n", condi.getAssistCode()));
        } else {
            sql.append(SapFetchUtil.buildAssistSql("MASTER.BUKRS", "T.PRCTR", orgMappingType, condi.getOrgMapping()));
        }
        sql.append("    AND MASTER.GJAHR = '${YEAR}' \n");
        if (!StringUtils.isEmpty((String)condi.getCurrencyCode())) {
            sql.append(String.format("AND T.PSWSL = '%s'", condi.getCurrencyCode()));
        }
        sql.append(String.format("   AND ((%1$s >= '${BEGINPERIOD}'", sqlHandler.toChar("MASTER.MONAT")));
        sql.append(String.format(" AND %1$s <= '${ENDPERIOD}') %2$s) ", sqlHandler.toChar("MASTER.MONAT"), StringUtil.isNotEmpty((String)startAdjustPeriod) && StringUtil.isNotEmpty((String)endAdjustPeriod) ? String.format("\n    OR (%1$s >= '${STARTADJUSTPERIOD}' AND %1$s <= '${ENDADJUSTPERIOD}')", sqlHandler.toChar("MASTER.MONAT")) : ""));
        sql.append(String.format("   AND T.HKONT LIKE '%s%%%%'  \n", condi.getSubjectCode()));
        sql.append("   ${EXTERNAL_VCHR_CONDI_SQL}  \n");
        sql.append("                              ) A  \n");
        sql.append(" ORDER BY A.ORDERNO  \n");
        StringBuilder externalTotalSelectSql = new StringBuilder();
        StringBuilder externalBalanceSelectSql = new StringBuilder();
        StringBuilder externalBalanceJoinSql = new StringBuilder();
        StringBuilder externalBalanceCondiSql = new StringBuilder();
        StringBuilder externalBalanceGroupSql = new StringBuilder();
        for (AssistMappingBO<BaseAcctAssist> balAssistMapping : balAssistMappingList) {
            externalTotalSelectSql.append(String.format(",A.%1$s AS %1$s,A.%1$s_NAME AS %1$s_NAME", balAssistMapping.getAssistCode()));
            externalBalanceJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %3$s = %2$s.ID ", balAssistMapping.getAssistSql(), balAssistMapping.getAssistCode(), balAssistMapping.getAccountAssistCode()));
            externalBalanceSelectSql.append(String.format(",%1$s AS %2$s,%2$s.NAME AS %2$s_NAME", balAssistMapping.getAccountAssistCode(), balAssistMapping.getAssistCode()));
            externalBalanceCondiSql.append(String.format("AND %1$s LIKE '%2$s%%' ", balAssistMapping.getAccountAssistCode(), balAssistMapping.getExecuteDim().getDimValue()));
            externalBalanceGroupSql.append(String.format(",%1$s,%2$s.NAME", balAssistMapping.getAccountAssistCode(), balAssistMapping.getAssistCode()));
        }
        StringBuilder externalVchrSelectSql = new StringBuilder();
        StringBuilder externalVchrJoinSql = new StringBuilder();
        StringBuilder externalVchrCondiSql = new StringBuilder();
        for (AssistMappingBO<BaseAcctAssist> vchrAssistMapping : vchrAssistMappingList) {
            externalVchrSelectSql.append(String.format(",%1$s AS %2$s,%2$s.NAME AS %2$s_NAME", vchrAssistMapping.getAccountAssistCode(), vchrAssistMapping.getAssistCode()));
            externalVchrJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %3$s = %2$s.ID ", vchrAssistMapping.getAssistSql(), vchrAssistMapping.getAssistCode(), vchrAssistMapping.getAccountAssistCode()));
            externalVchrCondiSql.append(String.format("AND %1$s LIKE '%2$s%%' ", vchrAssistMapping.getAccountAssistCode(), vchrAssistMapping.getExecuteDim().getDimValue()));
        }
        Variable variable = new Variable();
        variable.put("EXTERNAL_TOTAL_SELECT_SQL", externalTotalSelectSql.toString());
        variable.put("EXTERNAL_BALANCE_SELECT_SQL", externalBalanceSelectSql.toString());
        variable.put("EXTERNAL_BALANCE_JOIN_SQL", externalBalanceJoinSql.toString());
        variable.put("EXTERNAL_BALANCE_CONDI_SQL", externalBalanceCondiSql.toString());
        variable.put("EXTERNAL_BALANCE_GROUP_SQL", externalBalanceGroupSql.toString());
        variable.put("EXTERNAL_VCHR_SELECT_SQL", externalVchrSelectSql.toString());
        variable.put("EXTERNAL_VCHR_JOIN_SQL", externalVchrJoinSql.toString());
        variable.put("EXTERNAL_VCHR_CONDI_SQL", externalVchrCondiSql.toString());
        variable.put("EXTERNAL_SUBJECT_SQL", subjectSql);
        variable.put("UNITCODE", condi.getUnitCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("BEGINPERIOD", CommonUtil.lpad((String)String.valueOf(condi.getStartPeriod()), (String)"0", (int)2));
        variable.put("ENDPERIOD", CommonUtil.lpad((String)String.valueOf(condi.getEndPeriod()), (String)"0", (int)2));
        variable.put("STARTADJUSTPERIOD", CommonUtil.lpad((String)String.valueOf(startAdjustPeriod), (String)"0", (int)2));
        variable.put("ENDADJUSTPERIOD", CommonUtil.lpad((String)String.valueOf(endAdjustPeriod), (String)"0", (int)2));
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(querySql, new Object[0], (ResultSetExtractor)new DetailLedgerResultSetExtractor(balAssistMappingList));
        return queryParam;
    }
}

