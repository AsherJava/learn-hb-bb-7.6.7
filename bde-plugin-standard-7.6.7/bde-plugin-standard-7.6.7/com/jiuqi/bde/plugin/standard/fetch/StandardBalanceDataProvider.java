/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.util.TempTableUtils
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.standard.fetch;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.standard.util.StandardFetchUtil;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class StandardBalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;

    public FetchData queryData(BalanceCondition condi) {
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        StringBuilder sql = condi.getIncludeUncharged() != false ? this.getIncludeUnchargedSqlTmpl(condi, orgMappingType) : this.getChargedSqlTmpl(condi, orgMappingType);
        StringBuilder assistSelectSql = new StringBuilder();
        StringBuilder assistGroupSql = new StringBuilder();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        for (AssistMappingBO assistMapping : assistMappingList) {
            assistSelectSql.append(String.format(",T.%1$s AS %2$s \n", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            assistGroupSql.append(String.format(",T.%1$s", assistMapping.getAccountAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("ASSIST_SELECT_SQL", assistSelectSql.toString());
        variable.put("BALANCE_TABLENAME", StandardFetchUtil.getTableNameByCondi(!CollectionUtils.isEmpty((Collection)assistMappingList)));
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("ACCTYEAR", String.valueOf(condi.getAcctYear()));
        variable.put("ACCTPERIOD", String.valueOf(condi.getEndPeriod()));
        variable.put("PREVIOUSPERIOD", String.valueOf(condi.getEndPeriod() - 1));
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
        variable.put("ASSIST_GROUP_SQL", assistGroupSql.toString());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        querySql = StandardFetchUtil.parse(querySql, condi);
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u79d1\u76ee\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)condi, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), querySql, null, (ResultSetExtractor)new FetchDataExtractor());
    }

    private StringBuilder getChargedSqlTmpl(BalanceCondition condi, String orgMappingType) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.KMCODE AS SUBJECTCODE,  \n");
        sql.append("       T.HBCODE AS CURRENCYCODE,  \n");
        sql.append("       1          AS ORIENT  \n");
        sql.append("       ${ASSIST_SELECT_SQL}  \n");
        sql.append("      ,SUM(CASE WHEN T.PZPERIOD = 0 THEN ${DEBIT} - ${CREDIT} ELSE 0 END) AS NC,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD < ${ACCTPERIOD} THEN ${DEBIT} - ${CREDIT} ELSE 0 END) AS C,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD = ${ACCTPERIOD} THEN ${DEBIT} ELSE 0 END) AS JF,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD = ${ACCTPERIOD} THEN ${CREDIT} ELSE 0 END) AS DF,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 AND T.PZPERIOD <= ${ACCTPERIOD} THEN ${DEBIT} ELSE 0 END) AS JL,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 AND T.PZPERIOD <= ${ACCTPERIOD} THEN ${CREDIT} ELSE 0 END) AS DL,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD <= ${ACCTPERIOD} THEN ${DEBIT} - ${CREDIT} ELSE 0 END) AS YE,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD = 0 THEN ${ORGND} - ${ORGNC} ELSE 0 END) AS WNC,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD < ${ACCTPERIOD} THEN ${ORGND} - ${ORGNC} ELSE 0 END) AS WC,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD = ${ACCTPERIOD} THEN ${ORGND} ELSE 0 END) AS WJF,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD = ${ACCTPERIOD} THEN ${ORGNC} ELSE 0 END) AS WDF,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 AND T.PZPERIOD <= ${ACCTPERIOD} THEN ${ORGND} ELSE 0 END) AS WJL,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 AND T.PZPERIOD <= ${ACCTPERIOD} THEN ${ORGNC} ELSE 0 END) AS WDL,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD <= ${ACCTPERIOD} THEN ${ORGND} - ${ORGNC} ELSE 0 END) AS WYE  \n");
        sql.append("FROM ${BALANCE_TABLENAME}     T \n");
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"T.KMCODE"));
        sql.append("WHERE  1 = 1  \n");
        sql.append(StandardFetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping()));
        sql.append(StandardFetchUtil.buildAssistSql(orgMappingType, condi.getOrgMapping()));
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"T.KMCODE"));
        sql.append("  AND T.ZTYEAR = ${ACCTYEAR}  \n");
        sql.append("  AND T.PZPERIOD >= 0  \n");
        sql.append("  AND T.PZPERIOD <= ${ACCTPERIOD}  \n");
        sql.append("GROUP BY  T.KMCODE, T.HBCODE ${ASSIST_GROUP_SQL} \n");
        return sql;
    }

    private StringBuilder getIncludeUnchargedSqlTmpl(BalanceCondition condi, String orgMappingType) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.KMCODE AS SUBJECTCODE,  \n");
        sql.append("       T.HBCODE AS CURRENCYCODE,  \n");
        sql.append("       1          AS ORIENT  \n");
        sql.append("       ${ASSIST_SELECT_SQL}  \n");
        sql.append("      ,SUM(CASE WHEN T.PZPERIOD = 0 THEN DEBIT - CREDIT ELSE 0 END) AS NC,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD < ${ACCTPERIOD} THEN DEBIT - CREDIT ELSE 0 END) AS C,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD = ${ACCTPERIOD} THEN DEBIT ELSE 0 END) AS JF,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD = ${ACCTPERIOD} THEN CREDIT ELSE 0 END) AS DF,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 AND T.PZPERIOD <= ${ACCTPERIOD} THEN DEBIT ELSE 0 END) AS JL,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 AND T.PZPERIOD <= ${ACCTPERIOD} THEN CREDIT ELSE 0 END) AS DL,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD <= ${ACCTPERIOD} THEN DEBIT - CREDIT ELSE 0 END) AS YE,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD = 0 THEN DEBITORGN - CREDITORGN ELSE 0 END) AS WNC,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD < ${ACCTPERIOD} THEN DEBITORGN - CREDITORGN ELSE 0 END) AS WC,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD = ${ACCTPERIOD} THEN DEBITORGN ELSE 0 END) AS WJF,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD = ${ACCTPERIOD} THEN CREDITORGN ELSE 0 END) AS WDF,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 AND T.PZPERIOD <= ${ACCTPERIOD} THEN DEBITORGN ELSE 0 END) AS WJL,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD > 0 AND T.PZPERIOD <= ${ACCTPERIOD} THEN CREDITORGN ELSE 0 END) AS WDL,  \n");
        sql.append("       SUM(CASE WHEN T.PZPERIOD <= ${ACCTPERIOD} THEN DEBITORGN - CREDITORGN ELSE 0 END) AS WYE  \n");
        sql.append("  FROM ZW_PZINFOR T  \n");
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"T.KMCODE"));
        sql.append(" WHERE 1 = 1  \n");
        sql.append(StandardFetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping()));
        sql.append(StandardFetchUtil.buildAssistSql(orgMappingType, condi.getOrgMapping()));
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"T.KMCODE"));
        sql.append("  AND T.ZTYEAR = ${ACCTYEAR}  \n");
        sql.append("  AND T.PZPERIOD >= 0  \n");
        sql.append("  AND T.PZPERIOD <= ${ACCTPERIOD}  \n");
        sql.append("GROUP BY  T.KMCODE, T.HBCODE ${ASSIST_GROUP_SQL} \n");
        return sql;
    }
}

