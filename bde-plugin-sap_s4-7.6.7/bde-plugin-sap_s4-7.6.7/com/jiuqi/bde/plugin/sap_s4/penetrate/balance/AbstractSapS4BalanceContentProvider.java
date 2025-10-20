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
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.sap_s4.penetrate.balance;

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
import com.jiuqi.bde.plugin.sap_s4.BdeSapS4PluginType;
import com.jiuqi.bde.plugin.sap_s4.util.SapS4DataSchemeMappingProvider;
import com.jiuqi.bde.plugin.sap_s4.util.SapS4FetchUtil;
import com.jiuqi.bde.plugin.sap_s4.util.SapS4OrgMappingType;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractSapS4BalanceContentProvider
extends AbstractBalanceContentProvider {
    @Autowired
    private BdeSapS4PluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        SapS4DataSchemeMappingProvider schemeMappingProvider = new SapS4DataSchemeMappingProvider(fetchCondi);
        List<AssistMappingBO<BaseAcctAssist>> assistMappingList = schemeMappingProvider.getAssistMappingList();
        SapS4OrgMappingType orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? SapS4OrgMappingType.DEFAULT : SapS4OrgMappingType.fromCode(schemeMappingProvider.getOrgMappingType());
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT                    \n");
        sql.append("         ${UNITSELECTFIELD}       \n");
        sql.append("         RTRIM(T.RACCT) AS SUBJECTCODE,       \n");
        sql.append("         MD_ACCTSUBJECT.NAME AS SUBJECTNAME,       \n");
        sql.append("         SUM(CASE WHEN T.POPER = '000' THEN T.HSL ELSE 0 END) AS NC,  \n");
        sql.append("         SUM(CASE WHEN T.POPER < '${STARTPERIOD}' THEN T.HSL ELSE 0 END) AS QC, \n");
        sql.append("         SUM(CASE WHEN T.POPER >= '${STARTPERIOD}' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'S' THEN T.HSL ELSE 0 END) AS DEBIT,  \n");
        sql.append("         SUM(CASE WHEN T.POPER >= '${STARTPERIOD}' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'H' THEN T.HSL ELSE 0 END) AS CREDIT,  \n");
        sql.append("         SUM(CASE WHEN T.POPER > '000' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'S' THEN T.HSL ELSE 0 END) AS DSUM,  \n");
        sql.append("         SUM(CASE WHEN T.POPER > '000' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'H' THEN T.HSL ELSE 0 END) AS CSUM,  \n");
        sql.append("         SUM(CASE WHEN T.POPER <= '${ENDPERIOD}' THEN T.HSL ELSE 0 END) AS YE,  \n");
        sql.append("         SUM(CASE WHEN T.POPER = '000' THEN T.WSL ELSE 0 END) AS ORGNNC,  \n");
        sql.append("         SUM(CASE WHEN T.POPER < '${STARTPERIOD}' THEN T.WSL ELSE 0 END) AS ORGNQC, \n");
        sql.append("         SUM(CASE WHEN T.POPER >= '${STARTPERIOD}' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'S' THEN T.WSL ELSE 0 END) AS ORGND,  \n");
        sql.append("         SUM(CASE WHEN T.POPER >= '${STARTPERIOD}' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'H' THEN T.WSL ELSE 0 END) AS ORGNC,  \n");
        sql.append("         SUM(CASE WHEN T.POPER > '000' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'S' THEN T.WSL ELSE 0 END) AS ORGNDSUM,  \n");
        sql.append("         SUM(CASE WHEN T.POPER > '000' AND T.POPER <= '${ENDPERIOD}' AND T.DRCRK = 'H' THEN T.WSL ELSE 0 END) AS ORGNCSUM,  \n");
        sql.append("         SUM(CASE WHEN T.POPER <= '${ENDPERIOD}' THEN T.WSL ELSE 0 END) AS ORGNYE  \n");
        sql.append("    ${EXTERNAL_SELECT_SQL}  \n");
        sql.append("    FROM ACDOCA T \n");
        sql.append("   ${UNITJOINSQL} \n");
        sql.append("   LEFT JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = T.RACCT \n");
        sql.append("   ${EXTERNAL_JOIN_SQL}  \n");
        sql.append("     WHERE 1 = 1 \n");
        sql.append(SapS4FetchUtil.buildUnitSql("T.RBUKRS", orgMappingType, condi.getOrgMapping()));
        sql.append(SapS4FetchUtil.buildAssistSql("T.RBUKRS", "T.RCNTR", orgMappingType, condi.getOrgMapping()));
        sql.append("     AND T.RYEAR = '${YEAR}'");
        sql.append("     AND T.RLDNR = '0L' \n");
        sql.append("     AND T.BSTAT <> 'V'  \n");
        sql.append(this.buildSubjectCondi("T", "RACCT", condi.getSubjectCode()));
        sql.append(this.buildExcludeCondi("T", "RACCT", condi.getExcludeSubjectCode()));
        if (!StringUtils.isEmpty((String)condi.getCurrencyCode())) {
            sql.append(String.format("AND T.RTCUR = '%s'", condi.getCurrencyCode()));
        }
        sql.append("   ${EXTERNAL_CONDI_SQL}  \n");
        sql.append("   GROUP BY ${UNITFIELD} T.RACCT, MD_ACCTSUBJECT.NAME  \n");
        sql.append("   ${EXTERNAL_GROUP_SQL}    \n");
        sql.append(" ORDER BY ${UNITFIELD} T.RACCT  \n");
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
            if (SapS4OrgMappingType.DEFAULT == orgMappingType) {
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
        variable.put("STARTPERIOD", String.format("%03d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%03d", condi.getEndPeriod()));
        variable.put("EXTERNAL_SELECT_SQL", externalSelectSql.toString());
        variable.put("EXTERNAL_JOIN_SQL", externalJoinSql.toString());
        variable.put("EXTERNAL_GROUP_SQL", externalGroupSql.toString());
        variable.put("EXTERNAL_ORDER_SQL", externalOrderSql.toString());
        variable.put("EXTERNAL_CONDI_SQL", externalCondiSql.toString());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("YEAR", condi.getAcctYear().toString());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(querySql, new Object[0], (ResultSetExtractor)new BalanceResultSetExtractor(assistMappingList));
        return queryParam;
    }
}

