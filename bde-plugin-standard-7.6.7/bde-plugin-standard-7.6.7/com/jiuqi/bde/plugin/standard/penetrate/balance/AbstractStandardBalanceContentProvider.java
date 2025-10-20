/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
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
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.standard.penetrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
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
import com.jiuqi.bde.plugin.standard.BdeStandardPluginType;
import com.jiuqi.bde.plugin.standard.util.AssistPojo;
import com.jiuqi.bde.plugin.standard.util.StandardAssistProvider;
import com.jiuqi.bde.plugin.standard.util.StandardFetchUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractStandardBalanceContentProvider
extends AbstractBalanceContentProvider {
    @Autowired
    private BdeStandardPluginType standardPluginType;
    @Autowired
    private StandardAssistProvider assistProvider;

    public String getPluginType() {
        return this.standardPluginType.getSymbol();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        ArrayList<Integer> args = new ArrayList<Integer>();
        StringBuilder sql = condi.getIncludeUncharged() != false ? this.getIncludeUnchargedSqlTmpl(condi, orgMappingType) : this.getChargedSqlTmpl(condi, orgMappingType, assistMappingList);
        args.add(condi.getAcctYear());
        Variable variable = new Variable();
        variable.put("START_PERIOD", String.valueOf(condi.getStartPeriod()));
        variable.put("END_PERIOD", String.valueOf(condi.getEndPeriod()));
        if (CollectionUtils.isEmpty((Collection)assistMappingList)) {
            variable.put("DEBIT", "T.KM_JF");
            variable.put("CREDIT", "T.KM_DF");
            variable.put("ORGND", "T.KM_WJF");
            variable.put("ORGNC", "T.KM_WDF");
        } else {
            variable.put("DEBIT", "T.FZHS_JF");
            variable.put("CREDIT", "T.FZHS_DF");
            variable.put("ORGND", "T.FZHS_WJF");
            variable.put("ORGNC", "T.FZHS_WDF");
        }
        StringBuilder externalDimFieldSql = new StringBuilder();
        StringBuilder externalDimGroupSql = new StringBuilder();
        StringBuilder externalAssConfigSql = new StringBuilder();
        StringBuilder externalAssJoinSql = new StringBuilder();
        StringBuilder externalSubjectJoinSql = new StringBuilder();
        externalSubjectJoinSql.append(PenetrateUtil.replaceContext((String)schemeMappingProvider.getSubjectSql(), (PenetrateBaseDTO)condi));
        for (AssistMappingBO assistMapping : assistMappingList) {
            externalAssJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.CODE = T.%3$s ", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            externalDimFieldSql.append(String.format("T.%1$s AS %2$s, %2$s.NAME AS %2$s_NAME,", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalDimGroupSql.append(String.format(", T.%1$s, %2$s.NAME", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalAssConfigSql.append(this.matchByRule("T", assistMapping.getAccountAssistCode(), assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule()));
        }
        variable.put("EXTERNAL_DIM_FIELD_SQL", externalDimFieldSql.toString());
        variable.put("EXTERNAL_DIM_GROUP_SQL", externalDimGroupSql.toString());
        variable.put("ASSJOINSQL", externalAssJoinSql.toString());
        variable.put("SUBJECT_JOIN_SQL", externalSubjectJoinSql.toString());
        variable.put("EXTERNAL_ASS_CONFIG_SQL", externalAssConfigSql.toString());
        variable.put("EXTERNAL_ASS_CONFIG_SQL", externalAssConfigSql.toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        StringBuilder unitSelectFieldSql = new StringBuilder();
        StringBuilder unitJoinSql = new StringBuilder();
        StringBuilder unitFieldSql = new StringBuilder();
        if (!CollectionUtils.isEmpty((Collection)condi.getOrgMapping().getOrgMappingItems())) {
            if ("DEFAULT".equals(condi.getDataScheme().getDataMapping().getOrgMapping().getOrgMappingType())) {
                unitSelectFieldSql.append("ORG.CODE AS ACCTORGCODE, ORG.NAME AS ACCTORGNAME,");
                unitJoinSql.append(String.format("LEFT JOIN (%1$s) ORG ON ORG.CODE = T.ORGID ", schemeMappingProvider.getOrgSql()));
                unitFieldSql.append("ORG.CODE, ORG.NAME,");
            } else {
                HashMap assistItemMap;
                unitSelectFieldSql.append("ORG.CODE AS ACCTORGCODE, ORG.NAME AS ACCTORGNAME, ASSIST.CODE AS ASSISTCODE, ASSIST.NAME AS ASSISTNAME,");
                unitJoinSql.append(String.format("LEFT JOIN (%1$s) ORG ON ORG.CODE = T.ORGID ", schemeMappingProvider.getOrgSql()));
                Map<Object, Object> map = assistItemMap = assistMappingList.isEmpty() ? this.assistProvider.listAssist(condi.getDataScheme().getDataSourceCode()).stream().collect(Collectors.toMap(BaseAcctAssist::getCode, item -> item, (k1, k2) -> k1)) : CollectionUtils.newHashMap();
                if (!assistItemMap.containsKey(condi.getDataScheme().getDataMapping().getOrgMapping().getOrgMappingType())) {
                    throw new BusinessRuntimeException(String.format("\u4ee3\u7801\u4e3a%1$s\u7684\u8f85\u52a9\u6838\u7b97\u9879\u6ca1\u6709\u5339\u914d\u5230\u5bf9\u5e94\u7684\u57fa\u7840\u6570\u636e\uff0c\u8bf7\u68c0\u67e5\u6570\u636e\u65b9\u6848\u7ec4\u7ec7\u673a\u6784\u6620\u5c04\u89c4\u5219", condi.getDataScheme().getDataMapping().getOrgMapping().getOrgMappingType()));
                }
                AssistPojo assistPojo = (AssistPojo)((Object)assistItemMap.get(condi.getDataScheme().getDataMapping().getOrgMapping().getOrgMappingType()));
                unitJoinSql.append(String.format("LEFT JOIN (SELECT RWID AS ID,STDCODE AS CODE,STDNAME AS NAME FROM %1$S) ASSIST ON ASSIST.CODE = T.%2$s ", assistPojo.getTableName(), condi.getDataScheme().getDataMapping().getOrgMapping().getOrgMappingType()));
                unitFieldSql.append("ORG.CODE, ORG.NAME, ASSIST.CODE, ASSIST.NAME,");
            }
        }
        variable.put("UNITSELECTFIELD", unitSelectFieldSql.toString());
        variable.put("UNITJOINSQL", unitJoinSql.toString());
        variable.put("UNITFIELD", unitFieldSql.toString());
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new BalanceResultSetExtractor(assistMappingList));
        return queryParam;
    }

    private StringBuilder getChargedSqlTmpl(PenetrateBaseDTO condi, String orgMappingType, List<AssistMappingBO<AssistPojo>> assistMappingList) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ${UNITSELECTFIELD}T.KMCODE AS SUBJECTCODE, \n");
        sql.append("       SUBJECT.NAME AS SUBJECTNAME, \n");
        sql.append("       T.HBCODE AS CURRENCYCODE, \n");
        sql.append("       1 AS ORIENT, \n");
        sql.append("       ${EXTERNAL_DIM_FIELD_SQL} \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD = 0 THEN ${DEBIT} - ${CREDIT} ELSE 0 END) AS NC, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD < ${START_PERIOD} THEN ${DEBIT} - ${CREDIT} ELSE 0 END) AS QC, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD >= ${START_PERIOD} AND T.PZPERIOD <= ${END_PERIOD} THEN ${DEBIT} ELSE 0 END) AS DEBIT, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD >= ${START_PERIOD} AND T.PZPERIOD <= ${END_PERIOD} THEN ${CREDIT} ELSE 0 END) AS CREDIT, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 THEN ${DEBIT} ELSE 0 END) AS DSUM, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 THEN ${CREDIT} ELSE 0 END) AS CSUM, \n");
        sql.append("       SUM(${DEBIT} - ${CREDIT}) AS YE, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD = 0 THEN ${ORGND} - ${ORGNC} ELSE 0 END) AS ORGNNC, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD < ${START_PERIOD} THEN ${ORGND} - ${ORGNC} ELSE 0 END) AS ORGNQC, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD >= ${START_PERIOD} AND T.PZPERIOD <= ${END_PERIOD} THEN ${ORGND} ELSE 0 END) AS ORGND, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD >= ${START_PERIOD} AND T.PZPERIOD <= ${END_PERIOD} THEN ${ORGNC} ELSE 0 END) AS ORGNC, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 THEN ${ORGND} ELSE 0 END) AS ORGNDSUM, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 THEN ${ORGNC} ELSE 0 END) AS ORGNCSUM, \n");
        sql.append("       SUM(${ORGND} - ${ORGNC}) AS ORGNYE \n");
        sql.append("  FROM ").append(StandardFetchUtil.getTableNameByCondi(!CollectionUtils.isEmpty(assistMappingList))).append(" T \n");
        sql.append("  LEFT JOIN (${SUBJECT_JOIN_SQL}) SUBJECT ON SUBJECT.CODE = T.KMCODE \n");
        sql.append("       ${UNITJOINSQL} \n");
        sql.append("       ${ASSJOINSQL} \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append(StandardFetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping()));
        sql.append(StandardFetchUtil.buildAssistSql(orgMappingType, condi.getOrgMapping()));
        sql.append("   AND T.ZTYEAR = ? \n");
        sql.append("   AND T.PZPERIOD >= 0 \n");
        sql.append("   AND T.PZPERIOD <= ${END_PERIOD} \n");
        sql.append(this.buildSubjectCondi("T", "KMCODE", condi.getSubjectCode())).append(" \n");
        sql.append(this.buildExcludeCondi("T", "KMCODE", condi.getExcludeSubjectCode())).append(" \n");
        sql.append(" ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" GROUP BY ${UNITFIELD}T.KMCODE, SUBJECT.NAME, T.HBCODE ${EXTERNAL_DIM_GROUP_SQL} \n");
        sql.append(" ORDER BY ${UNITFIELD}T.KMCODE, T.HBCODE \n");
        return sql;
    }

    private StringBuilder getIncludeUnchargedSqlTmpl(PenetrateBaseDTO condi, String orgMappingType) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ${UNITSELECTFIELD}T.KMCODE AS SUBJECTCODE, \n");
        sql.append("       SUBJECT.NAME AS SUBJECTNAME, \n");
        sql.append("       T.HBCODE AS CURRENCYCODE, \n");
        sql.append("       1 AS ORIENT, \n");
        sql.append("       ${EXTERNAL_DIM_FIELD_SQL} \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD = 0 THEN T.DEBIT - T.CREDIT ELSE 0 END) AS NC, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD < ${START_PERIOD} THEN T.DEBIT - T.CREDIT ELSE 0 END) AS QC, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD >= ${START_PERIOD} AND T.PZPERIOD <= ${END_PERIOD} THEN T.DEBIT ELSE 0 END) AS DEBIT, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD >= ${START_PERIOD} AND T.PZPERIOD <= ${END_PERIOD} THEN T.CREDIT ELSE 0 END) AS CREDIT, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 THEN T.DEBIT ELSE 0 END) AS DSUM, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 THEN T.CREDIT ELSE 0 END) AS CSUM, \n");
        sql.append("       SUM(T.DEBIT - T.CREDIT) AS YE, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD = 0 THEN T.DEBITORGN - T.CREDITORGN ELSE 0 END) AS ORGNNC, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD < ${START_PERIOD} THEN T.DEBITORGN - T.CREDITORGN ELSE 0 END) AS ORGNQC, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD >= ${START_PERIOD} AND T.PZPERIOD <= ${END_PERIOD} THEN T.DEBITORGN ELSE 0 END) AS ORGND, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD >= ${START_PERIOD} AND T.PZPERIOD <= ${END_PERIOD} THEN T.CREDITORGN ELSE 0 END) AS ORGNC, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 THEN T.DEBITORGN ELSE 0 END) AS ORGNDSUM, \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 THEN T.CREDITORGN ELSE 0 END) AS ORGNCSUM, \n");
        sql.append("       SUM(T.DEBITORGN - T.CREDITORGN) AS ORGNYE \n");
        sql.append("  FROM ZW_PZINFOR T \n");
        sql.append("  LEFT JOIN (${SUBJECT_JOIN_SQL}) SUBJECT ON SUBJECT.CODE = T.KMCODE \n");
        sql.append("       ${UNITJOINSQL} \n");
        sql.append("         ${ASSJOINSQL} \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append(StandardFetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping()));
        sql.append(StandardFetchUtil.buildAssistSql(orgMappingType, condi.getOrgMapping()));
        sql.append("   AND T.ZTYEAR = ? \n");
        sql.append("   AND T.PZPERIOD >= 0 \n");
        sql.append("   AND T.PZPERIOD <= ${END_PERIOD} \n");
        sql.append(this.buildSubjectCondi("T", "KMCODE", condi.getSubjectCode())).append(" \n");
        sql.append(this.buildExcludeCondi("T", "KMCODE", condi.getExcludeSubjectCode())).append(" \n");
        sql.append(" ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" GROUP BY ${UNITFIELD}T.KMCODE, SUBJECT.NAME, T.HBCODE ${EXTERNAL_DIM_GROUP_SQL} \n");
        sql.append(" ORDER BY ${UNITFIELD}T.KMCODE, T.HBCODE \n");
        return sql;
    }
}

