/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
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
package com.jiuqi.bde.plugin.va6.penetrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.va6.BdeVa6PluginType;
import com.jiuqi.bde.plugin.va6.util.Va6FetchUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractVa6BalanceContentProvider
extends AbstractBalanceContentProvider {
    @Autowired
    private BdeVa6PluginType va6PluginType;

    public String getPluginType() {
        return this.va6PluginType.getSymbol();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUBJECT.CODE AS SUBJECTCODE,  \n");
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,  \n");
        sql.append("       CURRENCY.CODE AS CURRENCYCODE,  \n");
        sql.append("       SUM(CASE WHEN BAL.ACCTPERIOD = 0 THEN BAL.BF ELSE 0 END * SUBJECT.ORIENT) AS NC,  \n");
        sql.append("       SUM(CASE WHEN BAL.ACCTPERIOD = 0 THEN BAL.ORGNBF ELSE 0 END * SUBJECT.ORIENT) AS ORGNNC,  \n");
        sql.append("       SUM(CASE WHEN BAL.ACCTPERIOD = ${STARTPERIOD} THEN BAL.BF ELSE 0 END * SUBJECT.ORIENT) AS QC,  \n");
        sql.append("       SUM(CASE WHEN BAL.ACCTPERIOD = ${STARTPERIOD} THEN BAL.ORGNBF ELSE 0 END * SUBJECT.ORIENT) AS ORGNQC,  \n");
        sql.append("       SUM(CASE WHEN BAL.ACCTPERIOD = ${ACCTPERIOD} THEN BAL.DEBIT ELSE 0 END) AS DEBIT,  \n");
        sql.append("       SUM(CASE WHEN BAL.ACCTPERIOD = ${ACCTPERIOD} THEN BAL.ORGND ELSE 0 END) AS ORGND,  \n");
        sql.append("       SUM(CASE WHEN BAL.ACCTPERIOD = ${ACCTPERIOD} THEN BAL.CREDIT ELSE 0 END) AS CREDIT,  \n");
        sql.append("       SUM(CASE WHEN BAL.ACCTPERIOD = ${ACCTPERIOD} THEN BAL.ORGNC ELSE 0 END) AS ORGNC,  \n");
        sql.append("       SUM(CASE WHEN BAL.ACCTPERIOD = ${ACCTPERIOD} THEN BAL.DSUM ELSE 0 END) AS DSUM,  \n");
        sql.append("       SUM(CASE WHEN BAL.ACCTPERIOD = ${ACCTPERIOD} THEN BAL.ORGNDSUM ELSE 0 END) AS ORGNDSUM,  \n");
        sql.append("       SUM(CASE WHEN BAL.ACCTPERIOD = ${ACCTPERIOD} THEN BAL.CSUM ELSE 0 END) AS CSUM,  \n");
        sql.append("       SUM(CASE WHEN BAL.ACCTPERIOD = ${ACCTPERIOD} THEN BAL.ORGNCSUM ELSE 0 END) AS ORGNCSUM,  \n");
        sql.append("       SUM(CASE WHEN BAL.ACCTPERIOD = ${ACCTPERIOD} THEN BAL.CF ELSE 0 END * SUBJECT.ORIENT) AS YE,  \n");
        sql.append("       SUM(CASE WHEN BAL.ACCTPERIOD = ${ACCTPERIOD} THEN BAL.ORGNCF ELSE 0 END * SUBJECT.ORIENT) AS ORGNYE  \n");
        sql.append("       ${ASSFIELD} \n");
        sql.append("  FROM ${BALANCE_TABLENAME} BAL");
        sql.append(" INNER JOIN (${MD_ACCTSUBJECT}) SUBJECT ON BAL.SUBJECTID = SUBJECT.ID  \n");
        sql.append(" INNER JOIN (${MD_CURRENCY}) CURRENCY ON BAL.CURRENCYID = CURRENCY.ID  \n");
        sql.append(" INNER JOIN MD_FINORG ORG ON BAL.UNITID = ORG.RECID  \n");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append(" INNER JOIN SM_BOOK BOOK ON BAL.ACCTBOOKID = BOOK.RECID AND BOOK.STDCODE = ?  \n");
            args.add(condi.getOrgMapping().getAcctBookCode());
        }
        sql.append("${ASSJOIN}  ");
        sql.append(" WHERE  1 = 1  \n");
        sql.append("   AND BAL.ACCTYEAR = ?  \n");
        args.add(condi.getAcctYear());
        sql.append("  AND BAL.ACCTPERIOD IN(0 , ?, ?)  \n");
        args.add(condi.getStartPeriod());
        args.add(condi.getEndPeriod());
        sql.append(" AND ORG.STDCODE = ?  \n");
        args.add(condi.getOrgMapping().getAcctOrgCode());
        sql.append(this.buildSubjectCondi("SUBJECT", "CODE", condi.getSubjectCode())).append(" \n");
        sql.append(this.buildExcludeCondi("SUBJECT", "CODE", condi.getExcludeSubjectCode())).append(" \n");
        sql.append(" ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" GROUP BY SUBJECT.CODE, SUBJECT.NAME, CURRENCY.CODE  \n");
        sql.append("          ${GROUPFIELD}  \n");
        sql.append(" ORDER BY SUBJECT.CODE, CURRENCY.CODE \n");
        sql.append("       ${ORDERFIELD} \n");
        StringBuilder assJoin = new StringBuilder();
        StringBuilder assField = new StringBuilder();
        StringBuilder groupField = new StringBuilder();
        StringBuilder orderField = new StringBuilder();
        StringBuilder externalAssConfigSql = new StringBuilder();
        Boolean includeAss = assistMappingList.size() > 0;
        if (includeAss.booleanValue()) {
            assJoin.append(" INNER JOIN GL_ASSISTCOMB ASSCOMB ON ASSCOMB.RECID = BAL.ASSCOMBID \n");
        }
        for (AssistMappingBO assistMapping : assistMappingList) {
            assField.append(String.format(", %1$s.CODE AS %1$s, %1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            assJoin.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = ASSCOMB.%3$s ", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            groupField.append(String.format(", %1$s.CODE, %1$s.NAME", assistMapping.getAssistCode()));
            orderField.append(String.format(", %1$s", assistMapping.getAssistCode()));
            externalAssConfigSql.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule()));
        }
        Variable variable = new Variable();
        variable.put("ASSFIELD", assField.toString());
        variable.put("BALANCE_TABLENAME", Va6FetchUtil.getTableNameByCondi(Boolean.TRUE.equals(condi.getIncludeUncharged()), !CollectionUtils.isEmpty((Collection)assistMappingList)));
        variable.put("MD_ACCTSUBJECT", schemeMappingProvider.getSubjectSql());
        variable.put("MD_CURRENCY", schemeMappingProvider.getCurrencySql());
        variable.put("ASSJOIN", assJoin.toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("ACCTYEAR", String.valueOf(condi.getAcctYear()));
        variable.put("STARTPERIOD", String.valueOf(condi.getStartPeriod()));
        variable.put("ACCTPERIOD", String.valueOf(condi.getEndPeriod()));
        variable.put("ORDERFIELD", orderField.toString());
        variable.put("GROUPFIELD", groupField.toString());
        variable.put("EXTERNAL_ASS_CONFIG_SQL", externalAssConfigSql.toString());
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        executeSql = Va6FetchUtil.parse(executeSql, condi.getOrgMapping().getAcctOrgCode(), condi.getOrgMapping().getAcctBookCode(), String.valueOf(condi.getAcctYear()), String.valueOf(condi.getEndPeriod()), condi.getIncludeUncharged());
        QueryParam queryParam = new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new BalanceResultSetExtractor(assistMappingList));
        return queryParam;
    }
}

