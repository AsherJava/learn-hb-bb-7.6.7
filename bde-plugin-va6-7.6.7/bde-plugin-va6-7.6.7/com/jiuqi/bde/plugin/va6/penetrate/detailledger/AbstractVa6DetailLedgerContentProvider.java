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
package com.jiuqi.bde.plugin.va6.penetrate.detailledger;

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
import com.jiuqi.bde.plugin.va6.BdeVa6PluginType;
import com.jiuqi.bde.plugin.va6.util.Va6FetchUtil;
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

public abstract class AbstractVa6DetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeVa6PluginType va6PluginType;

    public String getPluginType() {
        return this.va6PluginType.getSymbol();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT 2 AS ROWTYPE,  \n");
        sql.append("       NULL AS ID,  \n");
        sql.append("       NULL AS VCHRID,  \n");
        sql.append(String.format("       '%d' as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append(String.format("       '%d' as ACCTPERIOD,  \n", condi.getStartPeriod()));
        sql.append("       NULL AS ACCTDAY,  \n");
        sql.append("       NULL AS VCHRTYPE,  \n");
        sql.append("       NULL AS ORDERID,  \n");
        sql.append("       1 AS ORIENT,  \n");
        sql.append(String.format("       %s AS SUBJECTCODE,  \n", sqlHandler.toChar("SUBJECT.CODE")));
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,   \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("'\u671f\u521d\u4f59\u989d'")));
        sql.append("       0 AS DEBIT,  \n");
        sql.append("       0 AS CREDIT,  \n");
        sql.append("       0 AS ORGND,  \n");
        sql.append("       0 ORGNC,  \n");
        sql.append("       SUM(T.BF * SUBJECT.ORIENT) AS YE,  \n");
        sql.append("       SUM(T.ORGNBF * SUBJECT.ORIENT) AS ORGNYE  \n");
        sql.append("       ${YEASSFIELD} \n");
        sql.append("  FROM ${BALANCE_TABLENAME} T  \n");
        sql.append("       ${YEJOINSQL}");
        sql.append(" INNER JOIN (${MD_ACCTSUBJECT}) SUBJECT ON T.SUBJECTID = SUBJECT.ID  \n");
        sql.append(" INNER JOIN MD_FINORG ORG ON T.UNITID = ORG.RECID  \n");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append(" INNER JOIN SM_BOOK BOOK ON T.ACCTBOOKID = BOOK.RECID AND BOOK.STDCODE = ?  \n");
            args.add(condi.getOrgMapping().getAcctBookCode());
        }
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND T.ACCTYEAR = ?  \n");
        args.add(condi.getAcctYear());
        sql.append("  AND T.ACCTPERIOD = ?  \n");
        args.add(condi.getStartPeriod());
        sql.append(" AND ORG.STDCODE = ?  \n");
        args.add(condi.getOrgMapping().getAcctOrgCode());
        sql.append(this.buildSubjectCondi("SUBJECT", "CODE", condi.getSubjectCode())).append(" \n");
        sql.append("   ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" GROUP BY T.ACCTYEAR, SUBJECT.CODE, SUBJECT.NAME \n");
        sql.append("       ${YEGROUPFIELD} \n");
        sql.append(" UNION ALL  \n");
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       VOUCHERDETAIL.RECID AS ID,  \n");
        sql.append("       VOUCHERDETAIL.VCHRID AS VCHRID,  \n");
        sql.append(String.format("       %s as ACCTYEAR,  \n", sqlHandler.year("VOUCHER.CREATEDATE")));
        sql.append(String.format("       %s as ACCTPERIOD,  \n", sqlHandler.month("VOUCHER.CREATEDATE")));
        sql.append(String.format("       %s AS ACCTDAY,  \n", sqlHandler.day("VOUCHER.CREATEDATE")));
        sql.append(sqlHandler.concatBySeparator("-", new String[]{"VOUCHERTYPE.STDCODE", "VOUCHER.VCHRNUM"})).append(" AS VCHRTYPE, \n");
        sql.append("       VOUCHERDETAIL.ITEMORDER AS ORDERID,  \n");
        sql.append("       SUBJECT.ORIENT AS ORIENT,  \n");
        sql.append(String.format("       %s AS SUBJECTCODE,  \n", sqlHandler.toChar("SUBJECT.CODE")));
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,  \n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("VOUCHERDETAIL.DIGEST")));
        sql.append("       VOUCHERDETAIL.DEBIT AS DEBIT, \n");
        sql.append("       VOUCHERDETAIL.CREDIT AS CREDIT, \n");
        sql.append("       VOUCHERDETAIL.ORGND AS ORGND, \n");
        sql.append("       VOUCHERDETAIL.ORGNC AS ORGNC, \n");
        sql.append("       0 AS YE,  \n");
        sql.append("       0 AS ORGNYE  \n");
        sql.append("       ${YEASSFIELD} \n");
        sql.append("  FROM GL_VOUCHER VOUCHER \n");
        sql.append("  INNER JOIN GL_VOUCHERITEM VOUCHERDETAIL ON VOUCHER.RECID = VOUCHERDETAIL.VCHRID \n");
        sql.append("  INNER JOIN MD_VCHRTYPE VOUCHERTYPE ON VOUCHER.VCHRTYPEID = VOUCHERTYPE.RECID \n");
        sql.append("  INNER JOIN (${MD_ACCTSUBJECT}) SUBJECT ON VOUCHERDETAIL.SUBJECTID = SUBJECT.ID  \n");
        sql.append("  INNER JOIN MD_FINORG ORG ON VOUCHER.UNITID = ORG.RECID  \n");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append(" INNER JOIN SM_BOOK BOOK ON VOUCHER.ACCTBOOKID = BOOK.RECID AND BOOK.STDCODE = '${BOOKCODE}'  \n");
        }
        sql.append("       ${PZJOINSQL}");
        sql.append("  WHERE 1=1 \n");
        sql.append("   AND VOUCHER.ACCTYEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("   AND VOUCHER.ACCTPERIOD <= ? \n");
        args.add(condi.getEndPeriod());
        sql.append("   AND VOUCHER.ACCTPERIOD >= ? \n");
        args.add(condi.getStartPeriod());
        sql.append(" AND ORG.STDCODE = ?  \n");
        args.add(condi.getOrgMapping().getAcctOrgCode());
        if (!condi.getIncludeUncharged().booleanValue()) {
            sql.append("   AND VOUCHER.POSTFLAG = 1  \n");
        }
        sql.append(this.buildSubjectCondi("SUBJECT", "CODE", condi.getSubjectCode())).append(" \n");
        sql.append("   ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" ORDER BY ROWTYPE DESC, ACCTYEAR, ACCTPERIOD, ACCTDAY, VCHRTYPE, ORDERID  \n");
        sql.append("   ${ORDERASSFIELD} \n");
        StringBuilder yeAssField = new StringBuilder();
        StringBuilder yeGroupField = new StringBuilder();
        StringBuilder orderAssField = new StringBuilder();
        StringBuilder yeJoinSql = new StringBuilder();
        StringBuilder pzJoinSql = new StringBuilder();
        StringBuilder externalAssConfigSql = new StringBuilder();
        Boolean includeAss = assistMappingList.size() > 0;
        if (includeAss.booleanValue()) {
            yeJoinSql.append(" INNER JOIN GL_ASSISTCOMB ASSCOMB ON ASSCOMB.RECID = T.ASSCOMBID \n");
            pzJoinSql.append(" INNER JOIN GL_ASSISTCOMB ASSCOMB ON ASSCOMB.RECID = VOUCHERDETAIL.ASSCOMBID \n");
        }
        for (AssistMappingBO assistMapping : assistMappingList) {
            yeAssField.append(String.format(", %1$s.CODE AS %1$s, %1$s.NAME AS %1$s_NAME ", assistMapping.getAssistCode()));
            yeJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = ASSCOMB.%3$s \n", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            pzJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = ASSCOMB.%3$s \n", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            yeGroupField.append(String.format(", %1$s.CODE, %1$s.NAME", assistMapping.getAssistCode()));
            orderAssField.append(String.format(", %1$s", assistMapping.getAssistCode()));
            externalAssConfigSql.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), "EQ"));
        }
        Variable variable = new Variable();
        variable.put("YEASSFIELD", yeAssField.toString());
        variable.put("YEJOINSQL", yeJoinSql.toString());
        variable.put("PZJOINSQL", pzJoinSql.toString());
        variable.put("YEGROUPFIELD", yeGroupField.toString());
        variable.put("ORDERASSFIELD", orderAssField.toString());
        variable.put("STARTPERIOD", String.valueOf(condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.valueOf(condi.getEndPeriod()));
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("ACCTYEAR", String.valueOf(condi.getAcctYear()));
        variable.put("EXTERNAL_ASS_CONFIG_SQL", externalAssConfigSql.toString());
        variable.put("BALANCE_TABLENAME", Va6FetchUtil.getTableNameByCondi(Boolean.TRUE.equals(condi.getIncludeUncharged()), !CollectionUtils.isEmpty((Collection)assistMappingList)));
        variable.put("MD_ACCTSUBJECT", schemeMappingProvider.getSubjectSql());
        variable.put("MD_CURRENCY", schemeMappingProvider.getCurrencySql());
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        executeSql = Va6FetchUtil.parse(executeSql, condi.getOrgMapping().getAcctOrgCode(), condi.getOrgMapping().getAcctBookCode(), String.valueOf(condi.getAcctYear()), String.valueOf(condi.getEndPeriod()), condi.getIncludeUncharged());
        QueryParam queryParam = new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new DetailLedgerResultSetExtractor(assistMappingList));
        return queryParam;
    }
}

