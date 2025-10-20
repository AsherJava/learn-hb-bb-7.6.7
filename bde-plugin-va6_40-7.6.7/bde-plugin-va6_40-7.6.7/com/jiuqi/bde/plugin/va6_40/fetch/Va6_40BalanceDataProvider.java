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
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.va6_40.fetch;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.va6_40.util.Va6_40FetchUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Va6_40BalanceDataProvider {
    @Autowired
    private DataSourceService dataSourceService;

    public FetchData queryData(BalanceCondition condi) {
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUBJECT.CODE AS SUBJECTCODE,  \n");
        sql.append("       CURRENCY.CODE AS CURRENCYCODE,   \n");
        sql.append("       SUBJECT.ORIENT AS ORIENT   \n");
        sql.append("       ${ASSIST_SELECT_SQL}   \n");
        sql.append("       ,SUM(BAL.BF * SUBJECT.ORIENT) AS NC,  \n");
        sql.append("       SUM(BAL.ORGNBF * SUBJECT.ORIENT) AS WNC,  \n");
        if (condi.getEndPeriod() == 1) {
            sql.append("       SUM(BAL.BF * SUBJECT.ORIENT) AS C,  \n");
            sql.append("       SUM(BAL.ORGNBF * SUBJECT.ORIENT) AS WC,  \n");
            sql.append("       SUM(BAL.DSUM_${ACCTPERIOD}) AS JF,  \n");
            sql.append("       SUM(BAL.CSUM_${ACCTPERIOD}) AS DF,  \n");
            sql.append("       SUM(BAL.ORGNDSUM_${ACCTPERIOD}) AS WJF,  \n");
            sql.append("       SUM(BAL.ORGNCSUM_${ACCTPERIOD}) AS WDF,  \n");
        } else {
            sql.append("       SUM(BAL.BF * SUBJECT.ORIENT + BAL.ORGNDSUM_${PREVIOUSPERIOD} - BAL.ORGNCSUM_${PREVIOUSPERIOD}) AS C,  \n");
            sql.append("       SUM(BAL.BF * SUBJECT.ORIENT + BAL.ORGNDSUM_${PREVIOUSPERIOD} - BAL.ORGNCSUM_${PREVIOUSPERIOD}) AS WC,  \n");
            sql.append("       SUM(BAL.DSUM_${ACCTPERIOD} - BAL.DSUM_${PREVIOUSPERIOD}) AS JF,  \n");
            sql.append("       SUM(BAL.CSUM_${ACCTPERIOD} - BAL.CSUM_${PREVIOUSPERIOD}) AS DF,  \n");
            sql.append("       SUM(BAL.ORGNDSUM_${ACCTPERIOD} - BAL.ORGNDSUM_${PREVIOUSPERIOD}) AS WJF,  \n");
            sql.append("       SUM(BAL.ORGNCSUM_${ACCTPERIOD} - BAL.ORGNCSUM_${PREVIOUSPERIOD}) AS WDF,  \n");
        }
        sql.append("       SUM(BAL.DSUM_${ACCTPERIOD}) AS JL,  \n");
        sql.append("       SUM(BAL.CSUM_${ACCTPERIOD}) AS DL,  \n");
        sql.append("       SUM(BAL.ORGNDSUM_${ACCTPERIOD}) AS WJL,  \n");
        sql.append("       SUM(BAL.ORGNCSUM_${ACCTPERIOD}) AS WDL,  \n");
        sql.append("       SUM(BAL.BF * SUBJECT.ORIENT + BAL.DSUM_${ACCTPERIOD}     - BAL.CSUM_${ACCTPERIOD})     AS YE,  \n");
        sql.append("       SUM(BAL.BF * SUBJECT.ORIENT + BAL.ORGNDSUM_${ACCTPERIOD} - BAL.ORGNCSUM_${ACCTPERIOD}) AS WYE  \n");
        sql.append("FROM ${BALANCE_TABLENAME}          BAL \n");
        sql.append("JOIN (${MD_ACCTSUBJECT}) SUBJECT ON BAL.SUBJECTID = SUBJECT.ID  \n");
        sql.append("JOIN (${MD_CURRENCY}) CURRENCY   ON BAL.CURRENCYID = CURRENCY.ID  \n");
        sql.append("JOIN MD_FINORG ORG               ON BAL.UNITID = ORG.RECID AND ORG.STDCODE = '${UNITCODE}'   \n");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("JOIN SM_BOOK BOOK ON BAL.ACCTBOOKID = BOOK.RECID AND BOOK.STDCODE = '${BOOKCODE}'  \n");
        }
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"SUBJECT.CODE"));
        sql.append("${ASSIST_JOIN_SQL}   \n");
        sql.append("WHERE  1 = 1  \n");
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"SUBJECT.CODE"));
        sql.append("GROUP BY SUBJECT.CODE, CURRENCY.CODE, SUBJECT.ORIENT ${ASSIST_GROUP_SQL} \n");
        StringBuilder assistSelectSql = new StringBuilder();
        StringBuilder assistJoinSql = new StringBuilder();
        StringBuilder assistGroupSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            if (assistJoinSql.length() == 0) {
                assistJoinSql.append("JOIN GL_ASSISTCOMB ASSCOMB ON ASSCOMB.RECID = BAL.ASSCOMBID \n");
            }
            assistSelectSql.append(String.format(", %1$s.CODE AS %1$s \n", assistMapping.getAssistCode()));
            assistJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON ASSCOMB.%3$s = %2$s.ID \n", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            assistGroupSql.append(String.format(",%1$s.CODE", assistMapping.getAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("ASSIST_SELECT_SQL", assistSelectSql.toString());
        variable.put("BALANCE_TABLENAME", Va6_40FetchUtil.getTableNameByCondi(condi.getIncludeUncharged(), condi.getAcctYear()));
        variable.put("MD_ACCTSUBJECT", schemeMappingProvider.getSubjectSql());
        variable.put("MD_CURRENCY", schemeMappingProvider.getCurrencySql());
        variable.put("ASSIST_JOIN_SQL", assistJoinSql.toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("ACCTYEAR", String.valueOf(condi.getAcctYear()));
        variable.put("ACCTPERIOD", String.valueOf(condi.getEndPeriod()));
        variable.put("PREVIOUSPERIOD", String.valueOf(condi.getEndPeriod() - 1));
        variable.put("ASSIST_GROUP_SQL", assistGroupSql.toString());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        querySql = Va6_40FetchUtil.parse(querySql, condi);
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u79d1\u76ee\uff08\u8f85\u52a9\uff09\u4f59\u989d", (Object)condi, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), querySql, null, (ResultSetExtractor)new FetchDataExtractor());
    }
}

