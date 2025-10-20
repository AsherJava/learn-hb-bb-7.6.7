/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue
 *  com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.eas8.penetrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.UnitAndBookValue;
import com.jiuqi.bde.bizmodel.execute.util.OrgSqlUtil;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.eas8.Eas8PluginType;
import com.jiuqi.bde.plugin.eas8.util.Eas8FetchUtil;
import com.jiuqi.common.base.util.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractEas8BalanceContentProvider
extends AbstractBalanceContentProvider {
    @Autowired
    private Eas8PluginType eas8PluginType;

    public String getPluginType() {
        return this.eas8PluginType.getSymbol();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        UnitAndBookValue unitAndBookValue = OrgSqlUtil.getUnitAndBookValue((OrgMappingDTO)condi.getOrgMapping());
        ArrayList<Integer> args = new ArrayList<Integer>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUBJECT.CODE AS SUBJECTCODE,  \n");
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,  \n");
        sql.append("       CURRENCY.CODE AS CURRENCYCODE,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = 1 THEN B.FBEGINBALANCELOCAL ELSE 0 END) AS NC,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = 1 THEN B.FBEGINBALANCEFOR ELSE 0 END) AS ORGNNC,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FYEARDEBITLOCAL ELSE 0 END) AS DSUM,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FYEARDEBITFOR ELSE 0 END) AS ORGNDSUM,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FYEARCREDITLOCAL ELSE 0 END) AS CSUM,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FYEARCREDITFOR ELSE 0 END) AS ORGNCSUM,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER >= ${STARTPERIOD} AND B.FPERIODNUMBER <= ${ENDPERIOD} THEN B.FDEBITLOCAL ELSE 0 END) AS DEBIT,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER >= ${STARTPERIOD} AND B.FPERIODNUMBER <= ${ENDPERIOD} THEN B.FDEBITFOR ELSE 0 END) AS ORGND,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER >= ${STARTPERIOD} AND B.FPERIODNUMBER <= ${ENDPERIOD} THEN B.FCREDITLOCAL ELSE 0 END) AS CREDIT,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER >= ${STARTPERIOD} AND B.FPERIODNUMBER <= ${ENDPERIOD} THEN B.FCREDITFOR ELSE 0 END) AS ORGNC,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${STARTPERIOD} THEN B.FBEGINBALANCELOCAL ELSE 0 END) AS QC,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${STARTPERIOD} THEN B.FBEGINBALANCEFOR ELSE 0 END) AS ORGNQC,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FENDBALANCELOCAL ELSE 0 END) AS YE,  \n");
        sql.append("       SUM(CASE WHEN B.FPERIODNUMBER = ${ENDPERIOD} THEN B.FENDBALANCEFOR ELSE 0 END) AS ORGNYE  \n");
        sql.append("       ${ASSFIELD} \n");
        sql.append("  FROM ${TABLENAME} B  \n");
        sql.append(" INNER JOIN (${EXTERNAL_ORG_SQL}) COMPANY  \n");
        sql.append("    ON B.FORGUNITID = COMPANY.ID \n");
        sql.append(" INNER JOIN (${EXTERNAL_CURRENCY_SQL}) CURRENCY  \n");
        sql.append("    ON CURRENCY.ID = B.FCURRENCYID  \n");
        sql.append(" INNER JOIN (${EXTERNAL_SUBJECT_SQL}) SUBJECT  \n");
        sql.append("    ON B.FACCOUNTID = SUBJECT.ID  \n");
        sql.append("   AND COMPANY.FACCOUNTTABLEID = SUBJECT.FACCOUNTTABLEID  \n");
        sql.append("   AND SUBJECT.FCOMPANYID = COMPANY.ID  \n");
        sql.append("  ${ASSJOIN} \n");
        sql.append(" WHERE B.FPERIODYEAR = ?  \n");
        args.add(condi.getAcctYear());
        sql.append("   ${YETYPE} \n");
        sql.append("   ${UNITCONDITION}  \n");
        sql.append("   AND CURRENCY.CODE = 'GLC' \n");
        sql.append("   AND SUBJECT.FISLEAF = 1 \n");
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
        String yeType = "";
        for (AssistMappingBO assistMapping : assistMappingList) {
            if (assJoin.length() == 0) {
                assJoin.append("  LEFT JOIN T_BD_ASSISTANTHG ASS ON B.FASSISTGRPID = ASS.FID \n");
            }
            assField.append(String.format(", %1$s.CODE AS %1$s, %1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            assJoin.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = ASS.%3$s ", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            groupField.append(String.format(", %1$s.CODE, %1$s.NAME", assistMapping.getAssistCode()));
            orderField.append(String.format(", %1$s", assistMapping.getAssistCode()));
            externalAssConfigSql.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule()));
        }
        yeType = condi.getIncludeUncharged() != false ? " AND B.FBALTYPE = 1 " : " AND B.FBALTYPE = 5 ";
        Variable variable = new Variable();
        variable.put("STARTPERIOD", condi.getStartPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("ASSFIELD", assField.toString());
        variable.put("ASSJOIN", assJoin.toString());
        variable.put("ORDERFIELD", orderField.toString());
        variable.put("TABLENAME", Eas8FetchUtil.getBalanceTableName(!assistMappingList.isEmpty()));
        variable.put("GROUPFIELD", groupField.toString());
        variable.put("YETYPE", yeType);
        variable.put("UNITCONDITION", OrgSqlUtil.getOrgConditionSql((String)"COMPANY.CODE", (List)unitAndBookValue.getUnitCodes()));
        variable.put("EXTERNAL_ORG_SQL", schemeMappingProvider.getOrgSql());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("EXTERNAL_CURRENCY_SQL", schemeMappingProvider.getCurrencySql());
        variable.put("EXTERNAL_ASS_CONFIG_SQL", externalAssConfigSql.toString());
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new BalanceResultSetExtractor(assistMappingList));
        return queryParam;
    }
}

