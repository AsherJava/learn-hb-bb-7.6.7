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
 *  com.jiuqi.bde.penetrate.impl.core.model.res.XjllDetailLedgerResultSetExtractor
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
import com.jiuqi.bde.penetrate.impl.core.model.res.XjllDetailLedgerResultSetExtractor;
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
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

@Component
public class SapXjllDetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeSapPluginType pluginType;
    @Autowired
    private AdvanceSqlParser sqlParser;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isNotEmpty((String)condi.getCashCode(), (String)"\u73b0\u6d41\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        SapDataSchemeMappingProvider schemeMappingProvider = new SapDataSchemeMappingProvider(fetchCondi);
        List<AssistMappingBO<BaseAcctAssist>> assistMappingList = schemeMappingProvider.getAssistMappingList();
        SapOrgMappingType orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? SapOrgMappingType.DEFAULT : SapOrgMappingType.fromCode(schemeMappingProvider.getOrgMappingType());
        OrgMappingDTO orgMapping = condi.getOrgMapping();
        String subjectSql = schemeMappingProvider.getSubjectSql();
        List columnList = this.sqlParser.parseSql(condi.getOrgMapping().getDataSourceCode(), subjectSql);
        Set columnSet = columnList.stream().map(item -> item.getName().toUpperCase()).collect(Collectors.toSet());
        boolean containMandt = columnSet.contains("MANDT");
        boolean containBookCode = columnSet.contains("KTOPL") && !StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode());
        boolean containBukrs = columnSet.contains("BUKRS");
        StringBuilder sql = new StringBuilder();
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        sql.append("        Select 0 AS ROWTYPE,  \n");
        sql.append("               T.Mandt || '|' || Master.Bukrs || '|' || TO_CHAR(Master.Gjahr) || '|' || TO_CHAR(rtrim(T.Belnr)) || '|' || T.Buzei  AS ID,  \n");
        sql.append("               T.Mandt || '|' || Master.Bukrs || '|' || TO_CHAR(Master.Gjahr) || '|' || TO_CHAR(rtrim(T.Belnr))  AS VCHRID,  \n");
        sql.append("               T.Mandt ClientID,  \n");
        sql.append("               TO_CHAR(Master.Gjahr) acctyear,  \n");
        sql.append("               TO_CHAR(Master.Monat) ACCTPERIOD,  \n");
        sql.append(String.format("       %s AS ACCTDAY,  \n", sqlHandler.day(sqlHandler.toDate("Master.Budat", "'yyyyMMdd'"))));
        sql.append("   T.RSTGR             AS MD_CFITEM,            \n");
        sql.append("   MD_CFITEM.NAME      AS MD_CFITEM_NAME       \n");
        sql.append("   ,T.HKONT             AS MD_ACCTSUBJECT,       \n");
        sql.append("   MD_ACCTSUBJECT.NAME AS MD_ACCTSUBJECT_NAME   \n");
        sql.append("   ${EXTERNAL_VCHR_SELECT_SQL}  \n");
        sql.append("       ,TO_CHAR(rtrim(Master.BLART)) || '-' || Master.Belnr AS VCHRTYPE,  \n");
        sql.append("               1 as ORIENT,  \n");
        sql.append("               T.SGTXT AS DIGEST,  \n");
        sql.append("               CASE WHEN RTRIM(T.SHKZG)='S' THEN DMBTR ELSE 0 END DEBIT,  \n");
        sql.append("               CASE WHEN RTRIM(T.SHKZG)='S' THEN WRBTR ELSE 0 END ORGND,  \n");
        sql.append("               CASE WHEN RTRIM(T.SHKZG)='H' THEN DMBTR ELSE 0 END CREDIT,  \n");
        sql.append("               CASE WHEN RTRIM(T.SHKZG)='H' THEN WRBTR ELSE 0 END ORGNC,  \n");
        sql.append("               CASE WHEN CASE WHEN RTRIM(T.SHKZG)='S' THEN DMBTR ELSE 0 END - CASE WHEN RTRIM(T.SHKZG)='H' THEN DMBTR ELSE 0 END > 0 then 1   when Case when rtrim(T.Shkzg)='S' Then Dmbtr Else 0 End - Case when rtrim(T.Shkzg)='H' Then Dmbtr Else 0 End < 0  then -1 ELSE 0 END as YEORIENT,  \n");
        sql.append("               ABS(CASE WHEN RTRIM(T.SHKZG)='S' THEN DMBTR ELSE 0 END - CASE WHEN RTRIM(T.SHKZG)='H' THEN DMBTR ELSE 0 END) YE,  \n");
        sql.append("               ABS(CASE WHEN RTRIM(T.SHKZG)='S' THEN WRBTR ELSE 0 END - CASE WHEN RTRIM(T.SHKZG)='H' THEN WRBTR ELSE 0 END) ORGNYE,  \n");
        sql.append("               TO_CHAR(TO_DATE(MASTER.BUDAT, 'YYYYMMDD')) ||  \n");
        sql.append("               TO_CHAR(MASTER.BELNR) || T.BUZEI ORDERNO  \n");
        sql.append("          From BKPF Master  \n");
        sql.append("           JOIN BSEG T ON Master.Mandt = T.Mandt And Master.Bukrs = T.Bukrs And Master.Belnr = T.Belnr And Master.Gjahr = T.Gjahr \n");
        sql.append("           LEFT JOIN T003T PZZ ON Master.Mandt = PZZ.Mandt And Master.BLART = PZZ.BLART AND PZZ.SPRAS = '1'  \n");
        sql.append("   LEFT JOIN (${EXTERNAL_CFITEM_SQL}) MD_CFITEM ON MD_CFITEM.ID = T.RSTGR \n");
        sql.append("   LEFT JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = T.HKONT \n");
        sql.append("   ${EXTERNAL_VCHR_JOIN_SQL}  \n");
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
            sql.append(String.format("    AND T.BUKRS = '%1$s' \n", condi.getAcctOrgCode()));
        } else {
            sql.append(SapFetchUtil.buildUnitSql("MASTER.BUKRS", orgMappingType, condi.getOrgMapping()));
            sql.append(SapFetchUtil.buildUnitSql("T.BUKRS", orgMappingType, condi.getOrgMapping()));
        }
        if (!StringUtils.isEmpty((String)condi.getAssistCode())) {
            sql.append(String.format("    AND T.PRCTR = '%1$s' \n", condi.getAssistCode()));
        } else {
            sql.append(SapFetchUtil.buildAssistSql("MASTER.BUKRS", "T.PRCTR", orgMappingType, condi.getOrgMapping()));
            sql.append(SapFetchUtil.buildAssistSql("T.BUKRS", "T.PRCTR", orgMappingType, condi.getOrgMapping()));
        }
        sql.append("    AND MASTER.GJAHR = '${YEAR}' \n");
        sql.append("    AND T.GJAHR = '${YEAR}' \n");
        sql.append(String.format("   AND ((%1$s >= '${BEGINPERIOD}'", sqlHandler.toChar("MASTER.MONAT")));
        sql.append(String.format(" AND %1$s <= '${ENDPERIOD}') %2$s) ", sqlHandler.toChar("MASTER.MONAT"), StringUtil.isNotEmpty((String)condi.getStartAdjustPeriod()) && StringUtil.isNotEmpty((String)condi.getEndAdjustPeriod()) ? String.format("\n    OR (%1$s >= '${STARTADJUSTPERIOD}' AND %1$s <= '${ENDADJUSTPERIOD}')", sqlHandler.toChar("MASTER.MONAT")) : ""));
        if (!StringUtils.isEmpty((String)condi.getCurrencyCode())) {
            sql.append(String.format("AND MASTER.WAERS = '%s'", condi.getCurrencyCode()));
        }
        sql.append(String.format("AND T.RSTGR LIKE '%1$s%%' ", condi.getCashCode()));
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(this.buildSubjectCondi("T", "HKONT", condi.getSubjectCode()));
        }
        sql.append("   ${EXTERNAL_VCHR_CONDI_SQL}  \n");
        sql.append(" order by   \n");
        sql.append("               TO_CHAR(TO_DATE(Master.Budat, 'yyyyMMdd')) ||  \n");
        sql.append("               TO_CHAR(Master.Belnr) || T.Buzei   \n");
        StringBuilder externalVchrSelectSql = new StringBuilder();
        StringBuilder externalVchrJoinSql = new StringBuilder();
        StringBuilder externalVchrCondiSql = new StringBuilder();
        StringBuilder externalVchrGroupSql = new StringBuilder();
        String SELECT_TMPL = ",%1$s AS %2$s,%2$s.NAME AS %2$s_NAME";
        String GROUP_TMPL = ",%1$s,%2$s.NAME";
        String TMPL = " JOIN (#advancedSql#) #odsTableAlias# ON #odsTableAlias#.ID = #bizTableField# ";
        for (AssistMappingBO<BaseAcctAssist> assistMapping : assistMappingList) {
            externalVchrSelectSql.append(String.format(",%1$s AS %2$s,%2$s.NAME AS %2$s_NAME", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            String sqlJoin = " JOIN (#advancedSql#) #odsTableAlias# ON #odsTableAlias#.ID = #bizTableField# ".replace("#advancedSql#", assistMapping.getAccountAssistCode());
            sqlJoin = sqlJoin.replace("#odsTableAlias#", assistMapping.getAssistCode());
            sqlJoin = sqlJoin.replace("#bizTableField#", this.getOdsFieldSql(sqlHandler, assistMapping.getAccountAssistCode()));
            externalVchrJoinSql.append(sqlJoin);
            externalVchrJoinSql.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule()));
            externalVchrCondiSql.append(String.format("AND %1$s LIKE '%2$s%%' ", assistMapping.getAccountAssistCode(), assistMapping.getExecuteDim().getDimValue()));
            externalVchrGroupSql.append(String.format(",%1$s,%2$s.NAME", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("EXTERNAL_VCHR_SELECT_SQL", externalVchrSelectSql.toString());
        variable.put("EXTERNAL_VCHR_CONDI_SQL", externalVchrCondiSql.toString());
        variable.put("EXTERNAL_VCHR_JOIN_SQL", externalVchrJoinSql.toString());
        variable.put("EXTERNAL_VCHR_GROUP_SQL", externalVchrGroupSql.toString());
        variable.put("EXTERNAL_CFITEM_SQL", schemeMappingProvider.getCfItemSql());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("UNITCODE", condi.getUnitCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("BEGINPERIOD", CommonUtil.lpad((String)String.valueOf(condi.getStartPeriod()), (String)"0", (int)2));
        variable.put("ENDPERIOD", CommonUtil.lpad((String)String.valueOf(condi.getEndPeriod()), (String)"0", (int)2));
        variable.put("STARTADJUSTPERIOD", CommonUtil.lpad((String)String.valueOf(condi.getStartAdjustPeriod()), (String)"0", (int)2));
        variable.put("ENDADJUSTPERIOD", CommonUtil.lpad((String)String.valueOf(condi.getEndAdjustPeriod()), (String)"0", (int)2));
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(querySql, new Object[0], (ResultSetExtractor)new XjllDetailLedgerResultSetExtractor(condi, assistMappingList));
        return queryParam;
    }

    private String getOdsFieldSql(IDbSqlHandler sqlHandler, String odsFieldName) {
        Assert.isNotEmpty((String)odsFieldName);
        String[] odsFieldNameArr = odsFieldName.split("/");
        return sqlHandler.concatBySeparator("|", odsFieldNameArr);
    }
}

