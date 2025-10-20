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
 *  com.jiuqi.bde.penetrate.client.dto.VoucherPenetrateDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractVoucherContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateVoucher
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.VoucherResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.gs_cloud.penetrate.voucher;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.client.dto.VoucherPenetrateDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractVoucherContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateVoucher;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.VoucherResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.gs_cloud.BdeGsCloudPluginType;
import com.jiuqi.bde.plugin.gs_cloud.util.AssistPojo;
import com.jiuqi.bde.plugin.gs_cloud.util.GsCloudTableEnum;
import com.jiuqi.common.base.util.Assert;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class GsCloudVoucherContentProvider
extends AbstractVoucherContentProvider {
    @Autowired
    private BdeGsCloudPluginType bdeStandardPluginType;

    public String getPluginType() {
        return this.bdeStandardPluginType.getSymbol();
    }

    public String getBizModel() {
        return "DEFAULT_CONTENTPROVIDER";
    }

    protected QueryParam<PenetrateVoucher> getQueryParam(VoucherPenetrateDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)ComputationModelEnum.VOUCHER.getCode());
        fetchCondi.setEnableDirectFilter(true);
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT VOUCHERDETAIL.ID AS ID,   \n");
        sql.append("       VOUCHER.ABSTRACT AS DIGEST,   \n");
        sql.append("       SUBJECT.CODE AS SUBJECTCODE,   \n");
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,   \n");
        sql.append("       ${YEASSFIELD}");
        sql.append("       CASE WHEN VOUCHERITEM.LENDINGDIRECTION = 1 THEN ${DEBIT} ELSE 0 END AS DEBIT, \n");
        sql.append("       CASE WHEN VOUCHERITEM.LENDINGDIRECTION = 2 THEN ${CREDIT} ELSE 0 END AS CREDIT, \n");
        sql.append("       CASE WHEN VOUCHERITEM.LENDINGDIRECTION = 1 THEN ${ORGND} ELSE 0 END AS ORGND, \n");
        sql.append("       CASE WHEN VOUCHERITEM.LENDINGDIRECTION = 2 THEN ${ORGNC} ELSE 0 END AS ORGNC \n");
        sql.append("  FROM FIGLACCOUNTINGDOCUMENT${YEAR} VOUCHER \n");
        sql.append("  INNER JOIN FIGLACCDOCENTRY${YEAR} VOUCHERITEM ON VOUCHER.ID = VOUCHERITEM.ACCDOCID \n");
        sql.append("  LEFT  JOIN FIGLACCDOCASSISTANCE${YEAR} VOUCHERDETAIL ON VOUCHER.ID = VOUCHERDETAIL.ACCDOCID AND VOUCHERITEM.ID = VOUCHERDETAIL.ACCDOCENTRYID \n");
        if (ComputationModelEnum.XJLLBALANCE.getCode().equals(condi.getBizModelKey())) {
            sql.append("  LEFT  JOIN FIZWXJYS${YEAR} XJYS  \n");
            sql.append("          ON (VOUCHERITEM.ID = XJYS.ZWXJYS_FLNM AND VOUCHERDETAIL.ID IS NULL OR VOUCHERDETAIL.ID = XJYS.ZWXJYS_FZID) \n");
            sql.append("  LEFT  JOIN FIZWXJJG${YEAR} XJJG ON XJYS.ID = XJJG.ZWXJJG_YSID \n");
            sql.append("  LEFT  JOIN BFCASHFLOWTYPE CASHFLOW  ON CASHFLOW.ID = XJJG.ZWXJJG_XJXM\n");
            sql.append("  INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = VOUCHERITEM.ACCTITLEID AND SUBJECT.ACCOUNTTYPE = '0' and SUBJECT.CASHACCTITLE = 0 \r\n");
        } else {
            sql.append("  INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = VOUCHERDETAIL.ACCTITLEID \n");
        }
        sql.append("       ${PZJOINSQL}");
        sql.append(" WHERE 1 = 1   \n");
        sql.append("   AND VOUCHER.ID = ?   \n");
        sql.append(" ORDER BY VOUCHERITEM.ACCENTRYCODE,VOUCHERDETAIL.ACCASSCODE   \n");
        StringBuilder yeAssField = new StringBuilder();
        StringBuilder pzJoinSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            yeAssField.append(String.format("%1$s.CODE AS %1$s, \n", assistMapping.getAssistCode()));
            pzJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = VOUCHERDETAIL.%3$s \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getAssField()));
        }
        Variable variable = new Variable();
        variable.put("YEASSFIELD", yeAssField.toString());
        variable.put("PZJOINSQL", pzJoinSql.toString());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        if (ComputationModelEnum.XJLLBALANCE.getCode().equals(condi.getBizModelKey())) {
            variable.put("DEBIT", "CASE WHEN CASHFLOW.CODE IS NULL THEN VOUCHERITEM.AMOUNT ELSE XJJG.ZWXJJG_JE END");
            variable.put("CREDIT", "CASE WHEN CASHFLOW.CODE IS NULL THEN VOUCHERITEM.AMOUNT ELSE XJJG.ZWXJJG_JE * -1 END");
            variable.put("ORGND", "CASE WHEN CASHFLOW.CODE IS NULL THEN VOUCHERITEM.AMOUNT ELSE XJJG.ZWXJJG_JE END");
            variable.put("ORGNC", "CASE WHEN CASHFLOW.CODE IS NULL THEN VOUCHERITEM.AMOUNT ELSE XJJG.ZWXJJG_JE * -1 END");
        } else {
            variable.put("DEBIT", assistMappingList.isEmpty() ? "VOUCHERITEM.AMOUNT" : "VOUCHERDETAIL.AMOUNT");
            variable.put("CREDIT", assistMappingList.isEmpty() ? "VOUCHERITEM.AMOUNT" : "VOUCHERDETAIL.AMOUNT");
            variable.put("ORGND", assistMappingList.isEmpty() ? "VOUCHERITEM.FOREIGNCURRENCY" : "VOUCHERDETAIL.FOREIGNCURRENCY");
            variable.put("ORGNC", assistMappingList.isEmpty() ? "VOUCHERITEM.FOREIGNCURRENCY" : "VOUCHERDETAIL.FOREIGNCURRENCY");
        }
        variable.put("YEAR", condi.getAcctYear().toString());
        String replaceSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        String executeSql = GsCloudTableEnum.replaceAccYear(replaceSql, condi.getAcctYear());
        QueryParam queryParam = new QueryParam(executeSql, new Object[]{condi.getVchrId()}, (ResultSetExtractor)new VoucherResultSetExtractor(assistMappingList));
        return queryParam;
    }
}

