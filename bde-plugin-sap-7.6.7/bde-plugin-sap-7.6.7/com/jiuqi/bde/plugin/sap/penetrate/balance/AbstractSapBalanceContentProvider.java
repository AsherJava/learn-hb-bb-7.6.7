/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  com.jiuqi.dc.mappingscheme.impl.util.AdvanceSqlParser
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.bde.plugin.sap.penetrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.sap.BdeSapPluginType;
import com.jiuqi.bde.plugin.sap.util.SapDataSchemeMappingProvider;
import com.jiuqi.bde.plugin.sap.util.SapFetchUtil;
import com.jiuqi.bde.plugin.sap.util.SapOrgMappingType;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.dc.mappingscheme.impl.util.AdvanceSqlParser;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import tk.mybatis.mapper.util.StringUtil;

public abstract class AbstractSapBalanceContentProvider
extends AbstractBalanceContentProvider {
    @Autowired
    private BdeSapPluginType pluginType;
    @Autowired
    private AdvanceSqlParser sqlParser;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        SapDataSchemeMappingProvider schemeMappingProvider = new SapDataSchemeMappingProvider(fetchCondi);
        List<AssistMappingBO<BaseAcctAssist>> assistMappingList = schemeMappingProvider.getAssistMappingList();
        SapOrgMappingType orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? SapOrgMappingType.DEFAULT : SapOrgMappingType.fromCode(schemeMappingProvider.getOrgMappingType());
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        String subjectSql = schemeMappingProvider.getSubjectSql();
        List columnList = this.sqlParser.parseSql(condi.getOrgMapping().getDataSourceCode(), subjectSql);
        Set columnSet = columnList.stream().map(item -> item.getName().toUpperCase()).collect(Collectors.toSet());
        boolean containMandt = columnSet.contains("MANDT");
        boolean containBookCode = columnSet.contains("KTOPL") && !StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode());
        boolean containBukrs = columnSet.contains("BUKRS");
        boolean includePlData = SapFetchUtil.isIncludePlData(condi.getOrgMapping().getDataSchemeCode());
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT                    \n");
        sql.append("  ${UNITSELECTFIELD}       \n");
        sql.append("  RTRIM(T.RACCT) AS SUBJECTCODE,       \n");
        sql.append("  MD_ACCTSUBJECT.NAME AS SUBJECTNAME       \n");
        sql.append("   ${EXTERNAL_SELECT_SQL}  \n");
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
        for (int i = 1; i < condi.getEndPeriod(); ++i) {
            periodSql.append(" + ").append(sqlHandler.nullToValue(String.format("HSL%s", CommonUtil.lpad((String)String.valueOf(i), (String)"0", (int)2)), "0"));
            orgnPeriodSql.append(" + ").append(sqlHandler.nullToValue(String.format("TSL%s", CommonUtil.lpad((String)String.valueOf(i), (String)"0", (int)2)), "0"));
        }
        if (includePlData) {
            sql.append(String.format("         ,SUM(CASE WHEN (KTOKS = 'PL' and T.RYEAR <= '${YEAR}') OR (KTOKS != 'PL' and T.RYEAR = '${YEAR}') THEN %s ELSE 0 END) AS NC,  \n", sqlHandler.nullToValue("HSLVT", "0")));
            sql.append(String.format("          SUM(CASE WHEN (KTOKS = 'PL' and T.RYEAR <= '${YEAR}') OR (KTOKS != 'PL' and T.RYEAR = '${YEAR}') THEN %1$s %2$s ELSE 0 END) AS QC,", sqlHandler.nullToValue("HSLVT", "0"), periodSql.toString()));
        } else {
            sql.append(String.format("         ,SUM(%1$s) AS NC,  \n", sqlHandler.nullToValue("HSLVT", "0")));
            sql.append(String.format("          SUM(%1$s %2$s) AS QC,", sqlHandler.nullToValue("HSLVT", "0"), periodSql.toString()));
        }
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'S' THEN %1$s %2$s ELSE 0 END) AS DEBIT,\n", sqlHandler.nullToValue(String.format("HSL%s", AbstractSapBalanceContentProvider.lpadPeriod((Integer)condi.getEndPeriod())), "0"), adjustPeriodSql));
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'H' THEN %1$s %2$s ELSE 0 END) * -1 AS CREDIT,\n", sqlHandler.nullToValue(String.format("HSL%s", AbstractSapBalanceContentProvider.lpadPeriod((Integer)condi.getEndPeriod())), "0"), adjustPeriodSql));
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'S' THEN 0 %1$s + %2$s %3$s ELSE 0 END) AS DSUM,\n", periodSql.toString(), sqlHandler.nullToValue(String.format("HSL%s", AbstractSapBalanceContentProvider.lpadPeriod((Integer)condi.getEndPeriod())), "0"), adjustPeriodSql));
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'H' THEN 0 %1$s + %2$s %3$s ELSE 0 END)  * -1 AS CSUM,\n", periodSql.toString(), sqlHandler.nullToValue(String.format("HSL%s", AbstractSapBalanceContentProvider.lpadPeriod((Integer)condi.getEndPeriod())), "0"), adjustPeriodSql));
        if (includePlData) {
            sql.append(String.format("         SUM(CASE WHEN (KTOKS = 'PL' and T.RYEAR <= '${YEAR}') OR (KTOKS != 'PL' and T.RYEAR = '${YEAR}') THEN %1$s %2$s + %3$s %4$s ELSE 0 END) AS YE,\n", sqlHandler.nullToValue("HSLVT", "0"), periodSql.toString(), sqlHandler.nullToValue(String.format("HSL%s", AbstractSapBalanceContentProvider.lpadPeriod((Integer)condi.getEndPeriod())), "0"), adjustPeriodSql));
            sql.append(String.format("         SUM(CASE WHEN (KTOKS = 'PL' and T.RYEAR <= '${YEAR}') OR (KTOKS != 'PL' and T.RYEAR = '${YEAR}') THEN %s ELSE 0 END) AS ORGNNC,  \n", sqlHandler.nullToValue("TSLVT", "0")));
            sql.append(String.format("         SUM(CASE WHEN (KTOKS = 'PL' and T.RYEAR <= '${YEAR}') OR (KTOKS != 'PL' and T.RYEAR = '${YEAR}') THEN %1$s %2$s ELSE 0 END) AS ORGNQC,", sqlHandler.nullToValue("TSLVT", "0"), orgnPeriodSql.toString()));
        } else {
            sql.append(String.format("         SUM(%1$s %2$s + %3$s %4$s) AS YE,\n", sqlHandler.nullToValue("HSLVT", "0"), periodSql.toString(), sqlHandler.nullToValue(String.format("HSL%s", AbstractSapBalanceContentProvider.lpadPeriod((Integer)condi.getEndPeriod())), "0"), adjustPeriodSql));
            sql.append(String.format("         SUM(%1$s) AS ORGNNC,  \n", sqlHandler.nullToValue("TSLVT", "0")));
            sql.append(String.format("         SUM(%1$s %2$s) AS ORGNQC,", sqlHandler.nullToValue("TSLVT", "0"), orgnPeriodSql.toString()));
        }
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'S' THEN %1$s %2$s ELSE 0 END) AS ORGND,\n", sqlHandler.nullToValue(String.format("TSL%s", AbstractSapBalanceContentProvider.lpadPeriod((Integer)condi.getEndPeriod())), "0"), orgnAdjustPeriodSql));
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'H' THEN %1$s %2$s ELSE 0 END) * -1 AS ORGNC,\n", sqlHandler.nullToValue(String.format("TSL%s", AbstractSapBalanceContentProvider.lpadPeriod((Integer)condi.getEndPeriod())), "0"), orgnAdjustPeriodSql));
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'S' THEN %1$s %2$s +%3$s %4$s ELSE 0 END) AS ORGNDSUM,\n", sqlHandler.nullToValue("TSLVT", "0"), orgnPeriodSql.toString(), sqlHandler.nullToValue(String.format("TSL%s", AbstractSapBalanceContentProvider.lpadPeriod((Integer)condi.getEndPeriod())), "0"), orgnAdjustPeriodSql));
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'H' THEN %1$s %2$s +%3$s %4$s ELSE 0 END) * -1 AS ORGNCSUM,\n", sqlHandler.nullToValue("TSLVT", "0"), orgnPeriodSql.toString(), sqlHandler.nullToValue(String.format("TSL%s", AbstractSapBalanceContentProvider.lpadPeriod((Integer)condi.getEndPeriod())), "0"), orgnAdjustPeriodSql));
        if (includePlData) {
            sql.append(String.format("         SUM(CASE WHEN (KTOKS = 'PL' and T.RYEAR <= '${YEAR}') OR (KTOKS != 'PL' and T.RYEAR = '${YEAR}') THEN %1$s %2$s + %3$s %4$s ELSE 0 END) AS ORGNYE\n", sqlHandler.nullToValue("TSLVT", "0"), orgnPeriodSql.toString(), sqlHandler.nullToValue(String.format("TSL%s", AbstractSapBalanceContentProvider.lpadPeriod((Integer)condi.getEndPeriod())), "0"), orgnAdjustPeriodSql));
        } else {
            sql.append(String.format("         SUM(%1$s %2$s + %3$s %4$s) AS ORGNYE\n", sqlHandler.nullToValue("TSLVT", "0"), orgnPeriodSql.toString(), sqlHandler.nullToValue(String.format("TSL%s", AbstractSapBalanceContentProvider.lpadPeriod((Integer)condi.getEndPeriod())), "0"), orgnAdjustPeriodSql));
        }
        sql.append("    FROM FAGLFLEXT T  \n");
        sql.append("   ${UNITJOINSQL} \n");
        sql.append("   LEFT JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = T.RACCT \n");
        if (containMandt) {
            sql.append("    AND MD_ACCTSUBJECT.MANDT = T.RCLNT \n");
        }
        if (containBookCode) {
            sql.append(String.format("    AND MD_ACCTSUBJECT.KTOPL = '%s' \n", condi.getOrgMapping().getAcctBookCode()));
        }
        if (containBukrs) {
            sql.append(SapFetchUtil.buildUnitSql("MD_ACCTSUBJECT.BUKRS", orgMappingType, condi.getOrgMapping()));
        }
        sql.append("   ${EXTERNAL_JOIN_SQL}  \n");
        sql.append("     WHERE 1 = 1 \n");
        sql.append(SapFetchUtil.buildUnitSql("T.RBUKRS", orgMappingType, condi.getOrgMapping()));
        sql.append(SapFetchUtil.buildAssistSql("T.RBUKRS", "T.PRCTR", orgMappingType, condi.getOrgMapping()));
        if (!includePlData) {
            sql.append("AND T.RYEAR = '${YEAR}'");
        }
        if (containBukrs) {
            sql.append(String.format("    AND MD_ACCTSUBJECT.BUKRS = '%s' \n", condi.getOrgMapping().getAcctOrgCode()));
        }
        sql.append(this.buildSubjectCondi("T", "RACCT", condi.getSubjectCode()));
        sql.append(this.buildExcludeCondi("T", "RACCT", condi.getExcludeSubjectCode()));
        if (!StringUtils.isEmpty((String)condi.getCurrencyCode())) {
            sql.append(String.format("AND T.RTCUR = '%s'", condi.getCurrencyCode()));
        }
        sql.append("   ${EXTERNAL_CONDI_SQL}  \n");
        sql.append("   GROUP BY ${UNITFIELD}T.RACCT,MD_ACCTSUBJECT.NAME  \n");
        sql.append("   ${EXTERNAL_GROUP_SQL}    \n");
        sql.append(" ORDER BY ${UNITFIELD}T.RACCT  \n");
        sql.append("   ${EXTERNAL_ORDER_SQL}  \n");
        StringBuilder externalSelectSql = new StringBuilder();
        StringBuilder externalJoinSql = new StringBuilder();
        StringBuilder externalGroupSql = new StringBuilder();
        StringBuilder externalOrderSql = new StringBuilder();
        StringBuilder externalCondiSql = new StringBuilder();
        for (AssistMappingBO<BaseAcctAssist> assistMapping : assistMappingList) {
            externalSelectSql.append(String.format(",%1$s AS %2$s,%2$s.NAME AS %2$s_NAME", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %3$s = %2$s.ID ", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            externalCondiSql.append(String.format("AND %1$s LIKE '%2$s%%' ", assistMapping.getAccountAssistCode(), assistMapping.getExecuteDim().getDimValue()));
            externalGroupSql.append(String.format(",%1$s,%2$s.NAME", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalOrderSql.append(String.format(",%1$s", assistMapping.getAssistCode()));
        }
        StringBuilder unitSelectFieldSql = new StringBuilder();
        StringBuilder unitJoinSql = new StringBuilder();
        StringBuilder unitFieldSql = new StringBuilder();
        if (!CollectionUtils.isEmpty((Collection)condi.getOrgMapping().getOrgMappingItems())) {
            if (SapOrgMappingType.DEFAULT == orgMappingType) {
                unitSelectFieldSql.append("T.RBUKRS AS ACCTORGCODE, ORG.NAME AS ACCTORGNAME,");
                unitJoinSql.append(String.format("LEFT JOIN (%1$s) ORG ON ORG.CODE = T.RBUKRS ", schemeMappingProvider.getOrgSql()));
                unitFieldSql.append("T.RBUKRS, ORG.NAME,");
            } else {
                unitSelectFieldSql.append("T.RBUKRS AS ACCTORGCODE, ORG.NAME AS ACCTORGNAME, ORG.ASSISTCODE, ORG.ASSISTNAME AS ASSISTNAME,");
                unitJoinSql.append(String.format("LEFT JOIN (%1$s) ORG ON ORG.CODE = T.RBUKRS AND ORG.ASSISTCODE = T.PRCTR ", schemeMappingProvider.getOrgSql()));
                unitFieldSql.append("T.RBUKRS, ORG.NAME, ORG.ASSISTCODE, ORG.ASSISTNAME,");
            }
        }
        Variable variable = new Variable();
        variable.put("UNITSELECTFIELD", unitSelectFieldSql.toString());
        variable.put("UNITJOINSQL", unitJoinSql.toString());
        variable.put("UNITFIELD", unitFieldSql.toString());
        variable.put("UNITCODE", condi.getUnitCode());
        variable.put("EXTERNAL_SELECT_SQL", externalSelectSql.toString());
        variable.put("EXTERNAL_JOIN_SQL", externalJoinSql.toString());
        variable.put("EXTERNAL_GROUP_SQL", externalGroupSql.toString());
        variable.put("EXTERNAL_ORDER_SQL", externalOrderSql.toString());
        variable.put("EXTERNAL_CONDI_SQL", externalCondiSql.toString());
        variable.put("EXTERNAL_SUBJECT_SQL", subjectSql);
        variable.put("YEAR", condi.getAcctYear().toString());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(querySql, new Object[0], (ResultSetExtractor)new BalanceResultSetExtractor(assistMappingList));
        return queryParam;
    }
}

