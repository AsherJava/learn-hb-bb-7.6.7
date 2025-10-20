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
package com.jiuqi.bde.plugin.eas8.penetrate.voucher;

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
import com.jiuqi.bde.plugin.eas8.Eas8PluginType;
import com.jiuqi.common.base.util.Assert;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Eas8VoucherContentProvider
extends AbstractVoucherContentProvider {
    @Autowired
    private Eas8PluginType eas8PluginType;

    public String getPluginType() {
        return this.eas8PluginType.getSymbol();
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
        sql.append("SELECT VOUCHERDETAIL.FID AS ID,   \n");
        sql.append("       VOUCHER.FABSTRACT AS DIGEST,   \n");
        sql.append("       SUBJECT.CODE AS SUBJECTCODE,   \n");
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,   \n");
        sql.append("       ${YEASSFIELD}");
        sql.append("       CASE WHEN ${VOUCHERDETAIL}.FENTRYDC = 1 THEN VOUCHERDETAIL.FLOCALAMOUNT ELSE 0 END AS DEBIT, \n");
        sql.append("       CASE WHEN ${VOUCHERDETAIL}.FENTRYDC = 0 THEN VOUCHERDETAIL.FLOCALAMOUNT ELSE 0 END AS CREDIT, \n");
        sql.append("       CASE WHEN ${VOUCHERDETAIL}.FENTRYDC = 1 THEN VOUCHERDETAIL.FORIGINALAMOUNT ELSE 0 END AS ORGND, \n");
        sql.append("       CASE WHEN ${VOUCHERDETAIL}.FENTRYDC = 0 THEN VOUCHERDETAIL.FORIGINALAMOUNT ELSE 0 END AS ORGNC \n");
        sql.append("  FROM T_GL_VOUCHER VOUCHER \n");
        if (assistMappingList.isEmpty()) {
            sql.append("  INNER JOIN T_GL_VOUCHERENTRY VOUCHERDETAIL ON VOUCHER.FID = VOUCHERDETAIL.FBILLID \n");
        } else {
            sql.append("  INNER JOIN T_GL_VOUCHERENTRY VOUCHERENTRY ON VOUCHER.FID = VOUCHERENTRY.FBILLID \n");
            sql.append("  INNER JOIN T_GL_VOUCHERASSISTRECORD VOUCHERDETAIL ON VOUCHER.FID = VOUCHERDETAIL.FBILLID AND VOUCHERDETAIL.FENTRYID = VOUCHERENTRY.FID \n");
        }
        sql.append("  INNER JOIN (${EXTERNAL_SUBJECT_SQL})SUBJECT ON SUBJECT.ID  = ${VOUCHERDETAIL}.FACCOUNTID \n");
        sql.append("       ${PZJOINSQL}");
        sql.append(" WHERE 1 = 1   \n");
        sql.append("   AND VOUCHERDETAIL.FBILLID = ?   \n");
        StringBuilder yeAssField = new StringBuilder();
        StringBuilder pzJoinSql = new StringBuilder();
        Variable variable = new Variable();
        if (!assistMappingList.isEmpty()) {
            pzJoinSql.append("  LEFT JOIN T_BD_ASSISTANTHG ASS ON VOUCHERDETAIL.FASSGRPID = ASS.FID \n");
            variable.put("VOUCHERDETAIL", "VOUCHERENTRY");
        } else {
            variable.put("VOUCHERDETAIL", "VOUCHERDETAIL");
        }
        for (AssistMappingBO assistMapping : assistMappingList) {
            yeAssField.append(String.format("%1$s.CODE AS %1$s, \n", assistMapping.getAssistCode()));
            pzJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = ASS.%3$s \n", PenetrateUtil.replaceContext((String)schemeMappingProvider.buildAssistSql(assistMapping), (PenetrateBaseDTO)condi), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
        }
        variable.put("YEASSFIELD", yeAssField.toString());
        variable.put("PZJOINSQL", pzJoinSql.toString());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(executeSql, new Object[]{condi.getVchrId()}, (ResultSetExtractor)new VoucherResultSetExtractor(assistMappingList));
        return queryParam;
    }
}

