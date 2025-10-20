/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.util.TempTableUtils
 *  com.jiuqi.bde.common.constant.MatchRuleEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.sap_s4.fetch;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.constant.MatchRuleEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.sap_s4.util.SapS4DataSchemeMappingProvider;
import com.jiuqi.bde.plugin.sap_s4.util.SapS4FetchUtil;
import com.jiuqi.bde.plugin.sap_s4.util.SapS4OrgMappingType;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class SapS4AssBalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;

    protected FetchData queryData(BalanceCondition condi) {
        SapS4DataSchemeMappingProvider schemeMappingProvider = new SapS4DataSchemeMappingProvider(condi);
        SapS4OrgMappingType orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? SapS4OrgMappingType.DEFAULT : SapS4OrgMappingType.fromCode(schemeMappingProvider.getOrgMappingType());
        OrgMappingDTO orgMapping = condi.getOrgMapping();
        List<AssistMappingBO<BaseAcctAssist>> assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT RTRIM(T.RACCT) AS SUBJECTCODE, \n");
        sql.append("                      1 AS ORIENT,            \n");
        sql.append("         RTRIM(T.RTCUR) AS CURRENCYCODE,       \n");
        sql.append("         SUM(CASE WHEN T.POPER = '000' THEN T.HSL ELSE 0 END) AS NC,  \n");
        sql.append("         SUM(CASE WHEN T.POPER < '${STARTPERIOD}' THEN T.HSL ELSE 0 END) AS C, \n");
        sql.append("         SUM(CASE WHEN T.POPER >= '${STARTPERIOD}' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'S' THEN T.HSL ELSE 0 END) AS JF,  \n");
        sql.append("         SUM(CASE WHEN T.POPER >= '${STARTPERIOD}' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'H' THEN T.HSL ELSE 0 END) AS DF,  \n");
        sql.append("         SUM(CASE WHEN T.POPER > '000' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'S' THEN T.HSL ELSE 0 END) AS JL,  \n");
        sql.append("         SUM(CASE WHEN T.POPER > '000' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'H' THEN T.HSL ELSE 0 END) AS DL,  \n");
        sql.append("         SUM(CASE WHEN T.POPER <= '${ENDPERIOD}' THEN T.HSL ELSE 0 END) AS YE,  \n");
        sql.append("         SUM(CASE WHEN T.POPER = '000' THEN T.WSL ELSE 0 END) AS WNC,  \n");
        sql.append("         SUM(CASE WHEN T.POPER < '${STARTPERIOD}' THEN T.WSL ELSE 0 END) AS WC, \n");
        sql.append("         SUM(CASE WHEN T.POPER >= '${STARTPERIOD}' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'S' THEN T.WSL ELSE 0 END) AS WJF,  \n");
        sql.append("         SUM(CASE WHEN T.POPER >= '${STARTPERIOD}' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'H' THEN T.WSL ELSE 0 END) AS WDF,  \n");
        sql.append("         SUM(CASE WHEN T.POPER > '000' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'S' THEN T.WSL ELSE 0 END) AS WJL,  \n");
        sql.append("         SUM(CASE WHEN T.POPER > '000' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'H' THEN T.WSL ELSE 0 END) AS WDL,  \n");
        sql.append("         SUM(CASE WHEN T.POPER <= '${ENDPERIOD}' THEN T.WSL ELSE 0 END) AS WYE  \n");
        sql.append("         ${ASSFIELD} \n");
        sql.append("    FROM ACDOCA T \n");
        if (condi.isEnableDirectFilter() && condi.getConditionMatchRule().getMatchRule() != MatchRuleEnum.RANGE) {
            sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"RTRIM(T.RACCT)"));
        }
        sql.append("   WHERE 1 = 1 \n");
        sql.append(SapS4FetchUtil.buildUnitSql("T.RBUKRS", orgMappingType, orgMapping));
        sql.append(SapS4FetchUtil.buildAssistSql("T.RBUKRS", "T.RCNTR", orgMappingType, orgMapping));
        sql.append("     AND T.RYEAR = '${YEAR}'");
        sql.append("     AND T.RLDNR = '0L' \n");
        sql.append("     AND T.BSTAT <> 'V'  \n");
        if (condi.isEnableDirectFilter() && condi.getConditionMatchRule().getMatchRule() == MatchRuleEnum.RANGE) {
            sql.append(String.format("     AND RTRIM(T.RACCT)>='%1$s' AND RTRIM(T.RACCT)<='%2$sZZ' \n", condi.getConditionMatchRule().getSubjectCodes().get(0), condi.getConditionMatchRule().getSubjectCodes().get(1)));
        }
        sql.append("   GROUP BY T.RACCT,T.RTCUR ${GROUPFIELD}  \n");
        StringBuilder externalSelectSql = new StringBuilder();
        StringBuilder externalGroupSql = new StringBuilder();
        for (AssistMappingBO<BaseAcctAssist> assistMapping : assistMappingList) {
            externalSelectSql.append(String.format(", %1$s AS %2$s", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalGroupSql.append(String.format(", %1$s", assistMapping.getAccountAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("STARTPERIOD", String.format("%03d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%03d", condi.getEndPeriod()));
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("ASSFIELD", externalSelectSql.toString());
        variable.put("GROUPFIELD", externalGroupSql.toString());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        Object[] args = new Object[]{};
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u79d1\u76ee\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)new Object[]{args, condi}, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), querySql, args, (ResultSetExtractor)new FetchDataExtractor());
    }
}

