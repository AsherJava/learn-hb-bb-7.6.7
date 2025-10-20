/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.bizmodel.execute.util.TempTableUtils
 *  com.jiuqi.bde.common.constant.MatchRuleEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.dc.mappingscheme.impl.util.AdvanceSqlParser
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.bde.plugin.sap.fetch;

import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.constant.MatchRuleEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.sap.util.SapDataSchemeMappingProvider;
import com.jiuqi.bde.plugin.sap.util.SapFetchUtil;
import com.jiuqi.bde.plugin.sap.util.SapOrgMappingType;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
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
public class SapAssBalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private AdvanceSqlParser sqlParser;

    protected FetchData queryData(BalanceCondition condi) {
        SapDataSchemeMappingProvider schemeMappingProvider = new SapDataSchemeMappingProvider(condi);
        SapOrgMappingType orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? SapOrgMappingType.DEFAULT : SapOrgMappingType.fromCode(schemeMappingProvider.getOrgMappingType());
        OrgMappingDTO orgMapping = condi.getOrgMapping();
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        List<AssistMappingBO<BaseAcctAssist>> assistMappingList = schemeMappingProvider.getAssistMappingList();
        String subjectSql = schemeMappingProvider.getSubjectSql();
        List columnList = this.sqlParser.parseSql(condi.getOrgMapping().getDataSourceCode(), subjectSql);
        Set columnSet = columnList.stream().map(item -> item.getName().toUpperCase()).collect(Collectors.toSet());
        boolean containMandt = columnSet.contains("MANDT");
        boolean containBookCode = columnSet.contains("KTOPL") && !StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode());
        boolean containBukrs = columnSet.contains("BUKRS");
        boolean includePlData = SapFetchUtil.isIncludePlData(condi.getOrgMapping().getDataSchemeCode());
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT                    \n");
        sql.append("  RTRIM(T.RACCT) AS SUBJECTCODE,       \n");
        sql.append("  1              AS ORIENT,            \n");
        sql.append("  RTRIM(T.RTCUR) AS CURRENCYCODE,       \n");
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
            sql.append(String.format("         SUM(CASE WHEN (KTOKS = 'PL' and T.RYEAR <= '${YEAR}') OR (KTOKS != 'PL' and T.RYEAR = '${YEAR}') THEN %1$s ELSE 0 END) AS NC,  \n", sqlHandler.nullToValue("HSLVT", "0")));
            sql.append(String.format("         SUM(CASE WHEN (KTOKS = 'PL' and T.RYEAR <= '${YEAR}') OR (KTOKS != 'PL' and T.RYEAR = '${YEAR}') THEN %1$s %2$s ELSE 0 END) AS C, \n", sqlHandler.nullToValue("HSLVT", "0"), periodSql.toString()));
        } else {
            sql.append(String.format("         SUM(%1$s) AS NC,  \n", sqlHandler.nullToValue("HSLVT", "0")));
            sql.append(String.format("         SUM(%1$s %2$s) AS C,", sqlHandler.nullToValue("HSLVT", "0"), periodSql.toString()));
        }
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'S' AND T.RYEAR = '${YEAR}' THEN %1$s %2$s ELSE 0 END) AS JF,\n", sqlHandler.nullToValue(String.format("HSL%s", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod())), "0"), adjustPeriodSql));
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'H' AND T.RYEAR = '${YEAR}' THEN %1$s %2$s ELSE 0 END) * -1 AS DF,\n", sqlHandler.nullToValue(String.format("HSL%s", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod())), "0"), adjustPeriodSql));
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'S' AND T.RYEAR = '${YEAR}' THEN 0 %1$s + %2$s %3$s ELSE 0 END) AS JL,\n", periodSql.toString(), sqlHandler.nullToValue(String.format("HSL%s", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod())), "0"), adjustPeriodSql));
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'H' AND T.RYEAR = '${YEAR}' THEN 0 %1$s + %2$s %3$s ELSE 0 END) * -1 AS DL,\n", periodSql, sqlHandler.nullToValue(String.format("HSL%s", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod())), "0"), adjustPeriodSql));
        if (includePlData) {
            sql.append(String.format("         SUM(CASE WHEN (KTOKS = 'PL' and T.RYEAR <= '${YEAR}') OR (KTOKS != 'PL' and T.RYEAR = '${YEAR}') THEN %1$s %2$s + %3$s %4$s ELSE 0 END) AS YE,\n", sqlHandler.nullToValue("HSLVT", "0"), periodSql, sqlHandler.nullToValue(String.format("HSL%s", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod())), "0"), adjustPeriodSql));
            sql.append(String.format("         SUM(CASE WHEN (KTOKS = 'PL' and T.RYEAR <= '${YEAR}') OR (KTOKS != 'PL' and T.RYEAR = '${YEAR}') THEN %1$s ELSE 0 END) AS WNC,  \n", sqlHandler.nullToValue("TSLVT", "0")));
            sql.append(String.format("         SUM(CASE WHEN (KTOKS = 'PL' and T.RYEAR <= '${YEAR}') OR (KTOKS != 'PL' and T.RYEAR = '${YEAR}') THEN %1$s %2$s ELSE 0 END) AS WC,\n", sqlHandler.nullToValue("TSLVT", "0"), orgnPeriodSql.toString()));
        } else {
            sql.append(String.format("         SUM(%1$s %2$s + %3$s %4$s) AS YE,\n", sqlHandler.nullToValue("HSLVT", "0"), periodSql.toString(), sqlHandler.nullToValue(String.format("HSL%s", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod())), "0"), adjustPeriodSql));
            sql.append(String.format("         SUM(%1$s) AS WNC,  \n", sqlHandler.nullToValue("TSLVT", "0")));
            sql.append(String.format("         SUM(%1$s %2$s) AS WC,", sqlHandler.nullToValue("TSLVT", "0"), orgnPeriodSql.toString()));
        }
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'S' AND T.RYEAR = '${YEAR}' THEN %1$s %2$s ELSE 0 END) AS WJF,\n", sqlHandler.nullToValue(String.format("TSL%s", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod())), "0"), orgnAdjustPeriodSql));
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'H' AND T.RYEAR = '${YEAR}' THEN %1$s %2$s ELSE 0 END) * -1 AS WDF,\n", sqlHandler.nullToValue(String.format("TSL%s", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod())), "0"), orgnAdjustPeriodSql));
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'S' AND T.RYEAR = '${YEAR}' THEN %1$s %2$s +%3$s %4$s ELSE 0 END) AS WJL,\n", sqlHandler.nullToValue("TSLVT", "0"), orgnPeriodSql.toString(), sqlHandler.nullToValue(String.format("TSL%s", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod())), "0"), orgnAdjustPeriodSql));
        sql.append(String.format("             SUM(CASE WHEN RTRIM(DRCRK) = 'H' AND T.RYEAR = '${YEAR}' THEN %1$s %2$s +%3$s %4$s ELSE 0 END) * -1 AS WDL,\n", sqlHandler.nullToValue("TSLVT", "0"), orgnPeriodSql.toString(), sqlHandler.nullToValue(String.format("TSL%s", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod())), "0"), orgnAdjustPeriodSql));
        if (includePlData) {
            sql.append(String.format("         SUM(CASE WHEN (KTOKS = 'PL' and T.RYEAR <= '${YEAR}') OR (KTOKS != 'PL' and T.RYEAR = '${YEAR}') THEN %1$s %2$s + %3$s %4$s ELSE 0 END) AS WYE \n", sqlHandler.nullToValue("TSLVT", "0"), orgnPeriodSql.toString(), sqlHandler.nullToValue(String.format("TSL%s", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod())), "0"), orgnAdjustPeriodSql));
        } else {
            sql.append(String.format("         SUM(%1$s %2$s + %3$s %4$s) AS WYE \n", sqlHandler.nullToValue("TSLVT", "0"), orgnPeriodSql.toString(), sqlHandler.nullToValue(String.format("TSL%s", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod())), "0"), orgnAdjustPeriodSql));
        }
        sql.append("    FROM FAGLFLEXT T \n");
        if (containBookCode) {
            sql.append("    JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.CODE = T.RACCT \n");
        }
        if (containBookCode && containMandt) {
            sql.append("    AND MD_ACCTSUBJECT.MANDT = T.RCLNT \n");
        }
        if (containBookCode) {
            sql.append(String.format("    AND MD_ACCTSUBJECT.KTOPL = '%s' \n", condi.getOrgMapping().getAcctBookCode()));
        }
        if (containBookCode && containBukrs) {
            sql.append(SapFetchUtil.buildUnitSql("MD_ACCTSUBJECT.BUKRS", orgMappingType, orgMapping));
        }
        if (condi.isEnableDirectFilter() && condi.getConditionMatchRule().getMatchRule() != MatchRuleEnum.RANGE) {
            sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"RTRIM(T.RACCT)"));
        }
        sql.append("  WHERE 1 = 1 \n");
        sql.append(SapFetchUtil.buildUnitSql("T.RBUKRS", orgMappingType, orgMapping));
        sql.append(SapFetchUtil.buildAssistSql("T.RBUKRS", "T.PRCTR", orgMappingType, orgMapping));
        if (!includePlData) {
            sql.append("AND T.RYEAR = '${YEAR}'");
        }
        if (condi.isEnableDirectFilter() && condi.getConditionMatchRule().getMatchRule() == MatchRuleEnum.RANGE) {
            sql.append(String.format("     AND RTRIM(T.RACCT)>='%1$s' AND RTRIM(T.RACCT)<='%2$sZZ' \n", condi.getConditionMatchRule().getSubjectCodes().get(0), condi.getConditionMatchRule().getSubjectCodes().get(1)));
        }
        sql.append("   GROUP BY T.RACCT,T.RTCUR,  \n");
        sql.append("   ${EXTERNAL_GROUP_SQL} T.DRCRK  \n");
        StringBuilder externalSelectSql = new StringBuilder();
        StringBuilder externalGroupSql = new StringBuilder();
        for (AssistMappingBO<BaseAcctAssist> assistMapping : assistMappingList) {
            externalSelectSql.append(String.format("%1$s AS %2$s,", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalGroupSql.append(String.format("%1$s,", assistMapping.getAccountAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("EXTERNAL_SELECT_SQL", externalSelectSql.toString());
        variable.put("EXTERNAL_GROUP_SQL", externalGroupSql.toString());
        variable.put("EXTERNAL_GROUP_SQL", externalGroupSql.toString());
        variable.put("EXTERNAL_SUBJECT_SQL", subjectSql);
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        Object[] args = new Object[]{};
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u79d1\u76ee\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)new Object[]{args, condi}, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), querySql, args, (ResultSetExtractor)new FetchDataExtractor());
    }
}

