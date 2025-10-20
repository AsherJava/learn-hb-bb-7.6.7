/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractXjllBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.XjllBalanceResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.k3_cloud.penetrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractXjllBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.XjllBalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.k3_cloud.BdeK3CloudPluginType;
import com.jiuqi.bde.plugin.k3_cloud.util.AssistPojo;
import com.jiuqi.common.base.util.StringUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class K3CloudXjllBalanceContentProvider
extends AbstractXjllBalanceContentProvider {
    @Autowired
    private BdeK3CloudPluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder pzAssJoinSql = new StringBuilder();
        StringBuilder inAssFieldGroup = new StringBuilder();
        StringBuilder inAssField = new StringBuilder();
        StringBuilder assistCondition = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            if (pzAssJoinSql.length() == 0) {
                pzAssJoinSql.append(" INNER JOIN T_BD_FLEXITEMDETAILV FLEXVALUE ON VOUCHERENTRY.FDETAILID =FLEXVALUE.FID  \n");
            }
            pzAssJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=FLEXVALUE.%3$s ", assistMapping.getAssistSql(), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
            inAssField.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            inAssFieldGroup.append(String.format(",%1$s.CODE", assistMapping.getAssistCode()));
            assistCondition.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule()));
        }
        String includeSql = condi.getIncludeUncharged() != false ? "" : " AND VOUCHER.FPOSTED ='1' ";
        Variable variable = new Variable();
        variable.put("STARTPERIOD", condi.getEndPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("INCLUDESQL", includeSql);
        variable.put("PZASSJOINSQL", pzAssJoinSql.toString());
        variable.put("INASSFIELDGROUP", inAssFieldGroup.toString());
        variable.put("ASSCONDITION", assistCondition.toString());
        variable.put("INASSFIELD", inAssField.toString());
        String querySql = VariableParseUtil.parse((String)this.getQuerySql(condi), (Map)variable.getVariableMap());
        return new QueryParam(PenetrateUtil.replaceContext((String)querySql, (PenetrateBaseDTO)condi), new Object[0], (ResultSetExtractor)new XjllBalanceResultSetExtractor(condi, assistMappingList));
    }

    private String getQuerySql(PenetrateBaseDTO condi) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            query.append("          SUBJECT.FNUMBER AS MD_ACCTSUBJECT,\n");
            query.append("          SUBJECT_L.FNAME AS MD_ACCTSUBJECT_NAME,\n");
        }
        query.append("          CASHITEM.FNUMBER AS MD_CFITEM,\n");
        query.append("          CASHITEM_L.FNAME AS MD_CFITEM_NAME,\n");
        query.append("          SUM(CASE WHEN VOUCHER.FPERIOD = ${ENDPERIOD} THEN CASH.FAMOUNT ELSE 0 END) AS BQNUM,\n");
        query.append("          SUM(CASE WHEN VOUCHER.FPERIOD = ${ENDPERIOD} THEN CASH.FAMOUNTFOR ELSE 0 END) AS WBQNUM,\n");
        query.append("          SUM(CASE WHEN VOUCHER.FPERIOD <= ${ENDPERIOD} THEN CASH.FAMOUNT ELSE 0 END) AS LJNUM,\n");
        query.append("          SUM(CASE WHEN VOUCHER.FPERIOD <= ${ENDPERIOD} THEN CASH.FAMOUNTFOR ELSE 0 END) AS WLJNUM\n");
        query.append("          ${INASSFIELD} \n");
        query.append("  FROM\n");
        query.append("          T_GL_CASHFLOWBAL CASH\n");
        query.append("  INNER JOIN T_GL_CASHFLOW CASHITEM ON\n");
        query.append("          CASHITEM.FID = CASH.FITEMID\n");
        query.append("  INNER JOIN T_GL_CASHFLOW_L CASHITEM_L ON\n");
        query.append("          CASHITEM_L.FID = CASHITEM.FID AND CASHITEM_L.FLOCALEID = 2052\n");
        query.append("  INNER JOIN T_GL_VOUCHER VOUCHER ON\n");
        query.append("          CASH.FVOUCHERID = VOUCHER.FVOUCHERID\n");
        query.append("  INNER JOIN T_GL_VOUCHERENTRY VOUCHERENTRY ON\n");
        query.append("          CASH.FVCHOPPOENTRYID = VOUCHERENTRY.FENTRYID  \n");
        query.append("  INNER JOIN T_BD_ACCOUNT SUBJECT ON\n");
        query.append("          VOUCHERENTRY.FACCOUNTID = SUBJECT.FACCTID\n");
        query.append("  INNER JOIN T_BD_ACCOUNT_L SUBJECT_L ON\n");
        query.append("          SUBJECT.FACCTID = SUBJECT_L.FACCTID AND SUBJECT_L.FLOCALEID = 2052\n");
        query.append("  ${PZASSJOINSQL} \n");
        query.append("  WHERE\n");
        query.append("          1 = 1\n");
        query.append("          AND SUBJECT.FISDETAIL = 1\n");
        query.append("          AND VOUCHER.FYEAR = ${YEAR}\n");
        query.append("          AND VOUCHER.FISCASHFLOW='1'\n");
        query.append("          AND CASH.FACCTBOOKID=${UNITCODE}\n");
        query.append(this.buildCfItemCondi("CASHITEM", "FNUMBER", condi.getCashCode()));
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            query.append(this.buildSubjectCondi("SUBJECT", "FNUMBER", condi.getSubjectCode()));
        }
        query.append("          ${ASSCONDITION}\n");
        query.append("          ${INCLUDESQL}\n");
        query.append("  GROUP BY CASHITEM.FNUMBER,CASHITEM_L.FNAME\n");
        query.append("  ORDER BY CASHITEM.FNUMBER,CASHITEM_L.FNAME\n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            query.append(",SUBJECT.FNUMBER,SUBJECT_L.FNAME \n");
        }
        query.append("           ${INASSFIELDGROUP} \n");
        return query.toString();
    }
}

