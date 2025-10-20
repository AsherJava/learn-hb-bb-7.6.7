/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.XjllDetailLedgerResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nc6.penetrate.detailledger;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.XjllDetailLedgerResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.nc6.BdeNc6PluginType;
import com.jiuqi.bde.plugin.nc6.assist.Nc6AssistPojo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Nc6XjllDetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeNc6PluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isNotEmpty((String)condi.getCashCode(), (String)"\u73b0\u6d41\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        UnitAndBookValue unitAndBookValue = OrgSqlUtil.getUnitAndBookValue((OrgMappingDTO)condi.getOrgMapping());
        boolean containSubject = !StringUtils.isEmpty((String)condi.getSubjectCode());
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT   \n");
        sql.append("       0 AS ROWTYPE,");
        sql.append("       PZX.PK_DETAIL AS ID,  \n");
        sql.append("       PZ.PK_VOUCHER AS VCHRID,  \n");
        sql.append("       PZX.YEARV AS ACCTYEAR,  \n");
        sql.append("       PZX.PERIODV AS ACCTPERIOD ,  \n");
        sql.append("       ").append(sqlHandler.SubStr("PZ.PREPAREDDATE", "9", "2")).append(" AS ACCTDAY, \n");
        sql.append(sqlHandler.concatBySeparator("-", new String[]{"BD_VOUCHERTYPE.SHORTNAME", sqlHandler.toChar("PZ.NUM")})).append(" AS VCHRTYPE, \n");
        sql.append("       MD_ACCTSUBJECT.ORIENT AS ORIENT");
        if (containSubject) {
            sql.append("       ,").append(sqlHandler.toChar("MD_ACCTSUBJECT.CODE")).append(" as MD_ACCTSUBJECT,  \n");
            sql.append("       ").append(sqlHandler.toChar("MD_ACCTSUBJECT.NAME")).append(" as MD_ACCTSUBJECT_NAME  \n");
        }
        sql.append("       ${EXTERNAL_SELECT_SQL}  \n");
        sql.append("       ,MD_CFITEM.CODE AS MD_CFITEM,  \n");
        sql.append("       MD_CFITEM.NAME AS MD_CFITEM_NAME");
        sql.append(String.format("       ,%s AS DIGEST,  \n", sqlHandler.toChar("PZX.EXPLANATION")));
        sql.append("       CASE WHEN CASHFLOWCASE.MONEYMAIN>=0 THEN CASHFLOWCASE.MONEYMAIN ELSE 0 END AS DEBIT,  \n");
        sql.append("       CASE WHEN CASHFLOWCASE.MONEYMAIN<0 THEN CASHFLOWCASE.MONEYMAIN*-1 ELSE 0 END AS CREDIT,  \n");
        sql.append("       CASE WHEN CASHFLOWCASE.MONEYMAIN>=0 THEN CASHFLOWCASE.MONEYMAIN ELSE 0 END AS ORGND,  \n");
        sql.append("       CASE WHEN CASHFLOWCASE.MONEYMAIN<0 THEN CASHFLOWCASE.MONEYMAIN*-1 ELSE 0 END AS ORGNC,  \n");
        sql.append("       CASHFLOWCASE.MONEYMAIN AS YE,  \n");
        sql.append("       CASHFLOWCASE.MONEYMAIN AS ORGNYE  \n");
        sql.append("  FROM GL_DETAIL PZX  \n");
        sql.append(" INNER JOIN (SELECT VCHR.PK_VOUCHER,VCHR.PREPAREDDATE,VCHR.PK_VOUCHERTYPE,VCHR.NUM  \n");
        sql.append("               FROM GL_VOUCHER VCHR  \n");
        sql.append("              WHERE 1=1 \n");
        sql.append("\t\t\t\t\t${VOUCHERBOOKCODECONDITION}");
        if (!condi.getIncludeUncharged().booleanValue()) {
            sql.append("                AND VCHR.PK_MANAGER <>'N/A'  \n");
        }
        sql.append("                AND VCHR.DISCARDFLAG = 'N'  \n");
        sql.append("                AND VCHR.DR = 0  \n");
        sql.append("                AND VCHR.VOUCHERKIND <> 255  \n");
        sql.append(this.getErrmessageCondi());
        sql.append("               AND VCHR.YEAR = '${ACCTYEAR}'  \n");
        sql.append("                AND VCHR.PERIOD >= '${START_PERIOD}'  \n");
        sql.append("                AND VCHR.PERIOD <= '${END_PERIOD}'  ) PZ   \n");
        sql.append("    ON PZ.PK_VOUCHER = PZX.PK_VOUCHER  \n");
        sql.append("   ${VOUCHERORGJOINSQL}");
        sql.append("  JOIN GL_CASHFLOWCASE CASHFLOWCASE  \n");
        sql.append("    ON CASHFLOWCASE.PK_DETAIL = PZX.PK_DETAIL  \n");
        sql.append("  JOIN BD_CASHFLOW MD_CFITEM  \n");
        sql.append("    ON MD_CFITEM.PK_CASHFLOW = CASHFLOWCASE.PK_CASHFLOW  \n");
        sql.append("         JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.ID = PZX.PK_ACCASOA  \n");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append(" \t\t${BOOKCODE} \n");
        }
        sql.append("\tINNER JOIN BD_VOUCHERTYPE BD_VOUCHERTYPE ON BD_VOUCHERTYPE.PK_VOUCHERTYPE=PZ.PK_VOUCHERTYPE \n");
        sql.append("         INNER JOIN (${EXTERNAL_CURRENCY_SQL}) HB ON PZX.PK_CURRTYPE = HB.ID \n");
        sql.append("\t\t ${VOUCHER_EXTERNAL_ASSIT_SQL} \n");
        sql.append("         ${ASS_JOIN_SQL}  \n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append(this.buildSubjectCondi("MD_CFITEM", "CODE", condi.getCashCode()));
        if (containSubject) {
            sql.append(this.buildSubjectCondi("MD_ACCTSUBJECT", "CODE", condi.getSubjectCode()));
        }
        sql.append("        ${ASS_CONDITION}");
        sql.append("\t\t${ORGCONDITION}");
        sql.append("        ORDER BY PZ.PREPAREDDATE,PZ.NUM,PZX.DETAILINDEX");
        StringBuilder externalSelectSql = new StringBuilder();
        HashMap dimensionMap = CollectionUtils.isEmpty((Collection)condi.getAssTypeList()) ? new HashMap() : condi.getAssTypeList().stream().collect(Collectors.toMap(Dimension::getDimCode, Function.identity(), (K1, K2) -> K1));
        StringBuilder voucherExternalAssistSql = new StringBuilder();
        StringBuilder assistSelectSql = new StringBuilder();
        StringBuilder assistJoinSql = new StringBuilder();
        StringBuilder assistCondition = new StringBuilder();
        String mainDocFreeTable = null;
        HashSet<String> docFreeSet = new HashSet<String>(3);
        for (AssistMappingBO assistMapping : assistMappingList) {
            externalSelectSql.append(String.format(",%1$s.CODE AS %1$s,%1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            assistJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID = %3$s.F%4$d ", schemeMappingProvider.buildAssistSql(assistMapping), assistMapping.getAssistCode(), ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName(), ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableNum()));
            assistSelectSql.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            assistCondition.append(String.format(" AND %1$s.CODE LIKE '%2$s%%%%'", assistMapping.getAssistCode(), ((Dimension)dimensionMap.get(assistMapping.getAssistCode())).getDimValue()));
            if (mainDocFreeTable == null) {
                mainDocFreeTable = ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName();
                voucherExternalAssistSql.append(String.format(" LEFT JOIN %1$s %1$s ON %1$s.ASSID=PZX.ASSID  \n", mainDocFreeTable));
                docFreeSet.add(mainDocFreeTable);
                continue;
            }
            if (docFreeSet.contains(((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName())) continue;
            voucherExternalAssistSql.append(String.format("  LEFT JOIN %1$s %1$s ON %1$s.ASSID = PZX.ASSID \r\n", ((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName()));
            docFreeSet.add(((Nc6AssistPojo)assistMapping.getAccountAssist()).getDocFreeTableName());
        }
        Variable variable = new Variable();
        variable.put("VOUCHER_EXTERNAL_ASSIT_SQL", voucherExternalAssistSql.toString());
        variable.put("ASS_JOIN_SQL", assistJoinSql.toString());
        variable.put("EXTERNAL_SELECT_SQL", externalSelectSql.toString());
        variable.put("ASS_CONDITION", assistCondition.toString());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("EXTERNAL_CURRENCY_SQL", schemeMappingProvider.getCurrencySql());
        variable.put("START_PERIOD", ModelExecuteUtil.lpadPeriod((Integer)condi.getStartPeriod()));
        variable.put("END_PERIOD", ModelExecuteUtil.lpadPeriod((Integer)condi.getEndPeriod()));
        variable.put("VOUCHERORGJOINSQL", !orgMappingType.equals("DEFAULT") ? "JOIN ORG_ORGS OORG ON PZX.${ORGRELATEFIELD} = OORG.PK_ORG" : "");
        variable.put("ORGRELATEFIELD", orgMappingType.equals("BUSINESSUNIT") ? "PK_UNIT" : "PK_ORG");
        variable.put("VOUCHERBOOKCODECONDITION", !orgMappingType.equals("UNIT") ? "AND VCHR.PK_ACCOUNTINGBOOK IN (SELECT PK_ACCOUNTINGBOOK FROM ORG_ACCOUNTINGBOOK WHERE 1=1 ${BOOKCONDITON})" : "");
        variable.put("BOOKCODE", OrgSqlUtil.getOrgConditionSql((String)"MD_ACCTSUBJECT.BOOKCODE", (List)unitAndBookValue.getBookCodes()));
        variable.put("ACCTYEAR", String.valueOf(condi.getAcctYear()));
        variable.put("ORGCONDITION", !orgMappingType.equals("DEFAULT") ? OrgSqlUtil.getOrgConditionSql((String)"OORG.CODE", (List)unitAndBookValue.getUnitCodes()) : "");
        variable.put("BOOKCONDITON", OrgSqlUtil.getOrgConditionSql((String)"CODE", (List)unitAndBookValue.getBookCodes()));
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(querySql, new Object[0], (ResultSetExtractor)new XjllDetailLedgerResultSetExtractor(condi, assistMappingList));
        return queryParam;
    }

    protected String getErrmessageCondi() {
        return "                       AND VCHR.ERRMESSAGE = '~'  \n";
    }
}

