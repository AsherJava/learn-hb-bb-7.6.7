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
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.standard.penetrate.detailledger;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.DetailLedgerResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.standard.BdeStandardPluginType;
import com.jiuqi.bde.plugin.standard.util.AssistPojo;
import com.jiuqi.bde.plugin.standard.util.StandardFetchUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractStandardDetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeStandardPluginType standardPluginType;

    public String getPluginType() {
        return this.standardPluginType.getSymbol();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        ArrayList<Integer> args = new ArrayList<Integer>();
        StringBuilder sql = condi.getIncludeUncharged() != false ? this.getIncludeUnchargedSqlTmpl(condi, orgMappingType) : this.getChargedSqlTmpl(condi, orgMappingType, assistMappingList);
        args.add(condi.getAcctYear());
        args.add(condi.getAcctYear());
        StringBuilder externalZWSelectSql = new StringBuilder();
        StringBuilder externalAssJoinSql = new StringBuilder();
        StringBuilder externalZWAssConfigSql = new StringBuilder();
        StringBuilder externalZWGroupSql = new StringBuilder();
        StringBuilder externalSubjectJoinSql = new StringBuilder();
        externalSubjectJoinSql.append(PenetrateUtil.replaceContext((String)schemeMappingProvider.getSubjectSql(), (PenetrateBaseDTO)condi));
        for (AssistMappingBO assistMapping : assistMappingList) {
            externalAssJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.CODE = T.%3$s ", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            externalZWSelectSql.append(String.format("T.%1$s AS %2$s, %2$s.NAME AS %2$s_NAME,", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalZWAssConfigSql.append(this.matchByRule("T", assistMapping.getAccountAssistCode(), assistMapping.getExecuteDim().getDimValue(), "EQ"));
            externalZWGroupSql.append(String.format(", T.%1$s, %2$s.NAME", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
        }
        Variable variable = new Variable();
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
        variable.put("START_PERIOD", String.valueOf(condi.getStartPeriod()));
        variable.put("END_PERIOD", String.valueOf(condi.getEndPeriod()));
        variable.put("EXTERNAL_ZW_SELECT_SQL", externalZWSelectSql.toString());
        variable.put("EXTERNAL_ASSJOIN_SQL", externalAssJoinSql.toString());
        variable.put("SUBJECT_JOIN_SQL", externalSubjectJoinSql.toString());
        variable.put("EXTERNAL_ZW_ASS_CONFIG_SQL", externalZWAssConfigSql.toString());
        variable.put("EXTERNAL_ZW_GROUP_SQL", externalZWGroupSql.toString());
        variable.put("SUBJECTCODE", condi.getSubjectCode());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new DetailLedgerResultSetExtractor(assistMappingList));
        return queryParam;
    }

    private StringBuilder getChargedSqlTmpl(PenetrateBaseDTO condi, String orgMappingType, List<AssistMappingBO<AssistPojo>> assistMappingList) {
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 2 AS ROWTYPE,  \n");
        sql.append("       NULL AS ID,  \n");
        sql.append("       NULL AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append(String.format("       %d as ACCTPERIOD,  \n", condi.getStartPeriod()));
        sql.append("       NULL AS ACCTDAY,  \n");
        sql.append("       NULL AS VCHRDATE,  \n");
        sql.append("       NULL AS VCHRTYPE,  \n");
        sql.append("       NULL AS PZGROUPID,  \n");
        sql.append("       NULL AS PZNUMBER,  \n");
        sql.append("       NULL AS ORDERID,  \n");
        sql.append("       1 AS ORIENT,  \n");
        sql.append(String.format("       %s AS SUBJECTCODE,  \n", sqlHandler.toChar("T.KMCODE")));
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,   \n");
        sql.append("       ${EXTERNAL_ZW_SELECT_SQL}  \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("       SUM(${DEBIT}) AS DEBIT,  \n");
        sql.append("       SUM(${CREDIT}) AS CREDIT,  \n");
        sql.append("       SUM(${ORGND}) AS ORGND,  \n");
        sql.append("       SUM(${ORGNC}) AS ORGNC,  \n");
        sql.append("       SUM(${DEBIT} - ${CREDIT}) AS YE,  \n");
        sql.append("       SUM(${ORGND} - ${ORGNC}) AS ORGNYE  \n");
        sql.append("  FROM ").append(StandardFetchUtil.getTableNameByCondi(!CollectionUtils.isEmpty(assistMappingList))).append(" T \n");
        sql.append("  LEFT JOIN (${SUBJECT_JOIN_SQL}) SUBJECT ON SUBJECT.CODE = T.KMCODE \n");
        sql.append("       ${EXTERNAL_ASSJOIN_SQL}  \n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append("   AND T.ZTYEAR = ?  \n");
        sql.append("   AND T.PZPERIOD >= 0 \n");
        sql.append("   AND T.PZPERIOD < ${START_PERIOD} \n");
        if (!StringUtils.isEmpty((String)condi.getAcctOrgCode())) {
            sql.append(String.format("    AND T.ORGID = '%1$s' \n", condi.getAcctOrgCode()));
        } else {
            sql.append(StandardFetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping()));
        }
        if (!StringUtils.isEmpty((String)condi.getAssistCode())) {
            sql.append(String.format("    AND T.%1$s = '%2$s' \n", orgMappingType, condi.getAssistCode()));
        } else {
            sql.append(StandardFetchUtil.buildAssistSql(orgMappingType, condi.getOrgMapping()));
        }
        sql.append(this.buildSubjectCondi("T", "KMCODE", condi.getSubjectCode())).append(" \n");
        sql.append("       ${EXTERNAL_ZW_ASS_CONFIG_SQL} \n");
        sql.append(" GROUP BY  T.ZTYEAR, T.KMCODE, SUBJECT.NAME ${EXTERNAL_ZW_GROUP_SQL} \n");
        sql.append(" UNION ALL  \n");
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       T.RWID AS ID,  \n");
        sql.append(sqlHandler.concat(new String[]{"T.ORGID", "'|'", "T.ZTYEAR", "'|'", "T.PZPERIOD", "'|'", "T.PZGROUPID", "'|'", "T.PZNUMBER"}) + " AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append("       T.PZPERIOD AS ACCTPERIOD,  \n");
        sql.append(String.format("       %s AS ACCTDAY,  \n", sqlHandler.toChar("T.VCHRDATE,'dd'")));
        sql.append("       T.VCHRDATE,  \n");
        sql.append(String.format("       %s  || '-' || T.PZNUMBER AS VCHRTYPE,  \n", sqlHandler.toChar("T.PZGROUPID")));
        sql.append("       T.PZGROUPID AS PZGROUPID,  \n");
        sql.append(String.format("       %s AS PZNUMBER,  \n", sqlHandler.lpad("T.PZNUMBER", 15, "0")));
        sql.append("       T.ORDERID AS ORDERID,  \n");
        sql.append("       SUBJECT.ORIENT AS ORIENT,  \n");
        sql.append(String.format("       %s AS SUBJECTCODE,  \n", sqlHandler.toChar("T.KMCODE")));
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,  \n");
        sql.append("       ${EXTERNAL_ZW_SELECT_SQL}  \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("T.DIGEST")));
        sql.append("       T.DEBIT AS DEBIT,  \n");
        sql.append("       T.CREDIT AS CREDIT,  \n");
        sql.append("       T.DEBITORGN AS ORGND,  \n");
        sql.append("       T.CREDITORGN AS ORGNC,  \n");
        sql.append("       0 AS YE,  \n");
        sql.append("       0 AS ORGNYE  \n");
        sql.append("  FROM ZW_PZINFOR T  \n");
        sql.append("  LEFT JOIN (${SUBJECT_JOIN_SQL}) SUBJECT ON SUBJECT.CODE = T.KMCODE  \n");
        sql.append("       ${EXTERNAL_ASSJOIN_SQL}  \n");
        sql.append(" WHERE 1 = 1  \n");
        if (!StringUtils.isEmpty((String)condi.getAcctOrgCode())) {
            sql.append(String.format("    AND T.ORGID = '%1$s' \n", condi.getAcctOrgCode()));
        } else {
            sql.append(StandardFetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping()));
        }
        if (!StringUtils.isEmpty((String)condi.getAssistCode())) {
            sql.append(String.format("    AND T.%1$s = '%2$s' \n", orgMappingType, condi.getAssistCode()));
        } else {
            sql.append(StandardFetchUtil.buildAssistSql(orgMappingType, condi.getOrgMapping()));
        }
        sql.append("   AND T.ZTYEAR = ? \n");
        sql.append("   AND T.PZPERIOD >= ${START_PERIOD}  \n");
        sql.append("   AND T.PZPERIOD <= ${END_PERIOD}  \n");
        sql.append(this.buildSubjectCondi("T", "KMCODE", condi.getSubjectCode())).append(" \n");
        sql.append("       ${EXTERNAL_ZW_ASS_CONFIG_SQL} \n");
        sql.append("   AND T.CHARGEDFLAG = 1  \n");
        sql.append(" ORDER BY ROWTYPE DESC, VCHRDATE, PZGROUPID, PZNUMBER, ORDERID  \n");
        return sql;
    }

    private StringBuilder getIncludeUnchargedSqlTmpl(PenetrateBaseDTO condi, String orgMappingType) {
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 2 AS ROWTYPE,  \n");
        sql.append("       NULL AS ID,  \n");
        sql.append("       NULL AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append(String.format("       %d as ACCTPERIOD,  \n", condi.getStartPeriod()));
        sql.append("       NULL AS ACCTDAY,  \n");
        sql.append("       NULL AS VCHRDATE, \n");
        sql.append("       NULL AS VCHRTYPE,  \n");
        sql.append("       NULL AS PZGROUPID,  \n");
        sql.append("       NULL AS PZNUMBER,  \n");
        sql.append("       NULL AS ORDERID,  \n");
        sql.append("       1 AS ORIENT,  \n");
        sql.append(String.format("       %s AS SUBJECTCODE,  \n", sqlHandler.toChar("T.KMCODE")));
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,   \n");
        sql.append("       ${EXTERNAL_ZW_SELECT_SQL}  \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("       SUM(T.DEBIT) AS DEBIT,  \n");
        sql.append("       SUM(T.CREDIT) AS CREDIT,  \n");
        sql.append("       SUM(T.DEBITORGN) AS ORGND,  \n");
        sql.append("       SUM(T.CREDITORGN) AS ORGNC,  \n");
        sql.append("       SUM(T.DEBIT - T.CREDIT) AS YE,  \n");
        sql.append("       SUM(T.DEBITORGN - CREDITORGN) AS ORGNYE  \n");
        sql.append("  FROM ZW_PZINFOR T \n");
        sql.append("  LEFT JOIN (${SUBJECT_JOIN_SQL}) SUBJECT ON SUBJECT.CODE = T.KMCODE \n");
        sql.append("       ${EXTERNAL_ASSJOIN_SQL}  \n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append("   AND T.ZTYEAR = ?  \n");
        sql.append("   AND T.PZPERIOD >= 0 \n");
        sql.append("   AND T.PZPERIOD < ${START_PERIOD} \n");
        if (!StringUtils.isEmpty((String)condi.getAcctOrgCode())) {
            sql.append(String.format("    AND T.ORGID = '%1$s' \n", condi.getAcctOrgCode()));
        } else {
            sql.append(StandardFetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping()));
        }
        if (!StringUtils.isEmpty((String)condi.getAssistCode())) {
            sql.append(String.format("    AND T.%1$s = '%2$s' \n", orgMappingType, condi.getAssistCode()));
        } else {
            sql.append(StandardFetchUtil.buildAssistSql(orgMappingType, condi.getOrgMapping()));
        }
        sql.append(this.buildSubjectCondi("T", "KMCODE", condi.getSubjectCode())).append(" \n");
        sql.append("       ${EXTERNAL_ZW_ASS_CONFIG_SQL} \n");
        sql.append(" GROUP BY  T.ZTYEAR, T.KMCODE, SUBJECT.NAME ${EXTERNAL_ZW_GROUP_SQL} \n");
        sql.append(" UNION ALL  \n");
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       T.RWID AS ID,  \n");
        sql.append(sqlHandler.concat(new String[]{"T.ORGID", "'|'", "T.ZTYEAR", "'|'", "T.PZPERIOD", "'|'", "T.PZGROUPID", "'|'", "T.PZNUMBER"}) + " AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append("       T.PZPERIOD AS ACCTPERIOD,  \n");
        sql.append(String.format("       %s AS ACCTDAY,  \n", sqlHandler.toChar("T.VCHRDATE,'dd'")));
        sql.append("       T.VCHRDATE AS VCHRDATE, \n");
        sql.append(String.format("       %s  || '-' || T.PZNUMBER AS VCHRTYPE,  \n", sqlHandler.toChar("T.PZGROUPID")));
        sql.append("       T.PZGROUPID AS PZGROUPID,  \n");
        sql.append(String.format("       %s AS PZNUMBER,  \n", sqlHandler.lpad("T.PZNUMBER", 15, "0")));
        sql.append("       T.ORDERID AS ORDERID,  \n");
        sql.append("       SUBJECT.ORIENT AS ORIENT,  \n");
        sql.append(String.format("       %s AS SUBJECTCODE,  \n", sqlHandler.toChar("T.KMCODE")));
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,  \n");
        sql.append("       ${EXTERNAL_ZW_SELECT_SQL}  \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("T.DIGEST")));
        sql.append("       T.DEBIT AS DEBIT,  \n");
        sql.append("       T.CREDIT AS CREDIT,  \n");
        sql.append("       T.DEBITORGN AS ORGND,  \n");
        sql.append("       T.CREDITORGN AS ORGNC,  \n");
        sql.append("       0 AS YE,  \n");
        sql.append("       0 AS ORGNYE  \n");
        sql.append("  FROM ZW_PZINFOR T  \n");
        sql.append("  LEFT JOIN (${SUBJECT_JOIN_SQL}) SUBJECT ON SUBJECT.CODE = T.KMCODE  \n");
        sql.append("       ${EXTERNAL_ASSJOIN_SQL}  \n");
        sql.append(" WHERE 1 = 1  \n");
        if (!StringUtils.isEmpty((String)condi.getAcctOrgCode())) {
            sql.append(String.format("    AND T.ORGID = '%1$s' \n", condi.getAcctOrgCode()));
        } else {
            sql.append(StandardFetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping()));
        }
        if (!StringUtils.isEmpty((String)condi.getAssistCode())) {
            sql.append(String.format("    AND T.%1$s = '%2$s' \n", orgMappingType, condi.getAssistCode()));
        } else {
            sql.append(StandardFetchUtil.buildAssistSql(orgMappingType, condi.getOrgMapping()));
        }
        sql.append("   AND T.ZTYEAR = ? \n");
        sql.append("   AND T.PZPERIOD >= ${START_PERIOD}  \n");
        sql.append("   AND T.PZPERIOD <= ${END_PERIOD}  \n");
        sql.append(this.buildSubjectCondi("T", "KMCODE", condi.getSubjectCode())).append(" \n");
        sql.append("       ${EXTERNAL_ZW_ASS_CONFIG_SQL} \n");
        sql.append(" ORDER BY ROWTYPE DESC, VCHRDATE, PZGROUPID, PZNUMBER, ORDERID  \n");
        return sql;
    }
}

