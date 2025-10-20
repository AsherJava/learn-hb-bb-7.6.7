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
package com.jiuqi.bde.plugin.va6.penetrate.voucher;

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
import com.jiuqi.bde.plugin.va6.BdeVa6PluginType;
import com.jiuqi.bde.plugin.va6.util.Va6FetchUtil;
import com.jiuqi.common.base.util.Assert;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Va6VoucherContentProvider
extends AbstractVoucherContentProvider {
    @Autowired
    private BdeVa6PluginType va6PluginType;

    public String getPluginType() {
        return this.va6PluginType.getSymbol();
    }

    public String getBizModel() {
        return "DEFAULT_CONTENTPROVIDER";
    }

    protected QueryParam<PenetrateVoucher> getQueryParam(VoucherPenetrateDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)ComputationModelEnum.ASSBALANCE.getCode());
        fetchCondi.setEnableDirectFilter(false);
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT VOUCHERDETAIL.RECID AS ID,   \n");
        sql.append("       VOUCHERDETAIL.DIGEST AS DIGEST,   \n");
        sql.append("       SUBJECT.CODE AS SUBJECTCODE,   \n");
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,   \n");
        sql.append("       ${YEASSFIELD}");
        sql.append("       VOUCHERDETAIL.DEBIT AS DEBIT, \n");
        sql.append("       VOUCHERDETAIL.CREDIT AS CREDIT, \n");
        sql.append("       VOUCHERDETAIL.ORGND AS ORGND, \n");
        sql.append("       VOUCHERDETAIL.ORGNC AS ORGNC \n");
        sql.append("  FROM GL_VOUCHER VOUCHER \n");
        sql.append("  INNER JOIN GL_VOUCHERITEM VOUCHERDETAIL ON VOUCHER.RECID = VOUCHERDETAIL.VCHRID \n");
        sql.append("  JOIN (${MD_ACCTSUBJECT}) SUBJECT ON VOUCHERDETAIL.SUBJECTID = SUBJECT.ID  \n");
        sql.append("       ${PZJOINSQL}");
        sql.append(" WHERE 1 = 1   \n");
        sql.append("   AND VOUCHERDETAIL.VCHRID = ?   \n");
        StringBuilder yeAssField = new StringBuilder();
        StringBuilder pzJoinSql = new StringBuilder();
        Variable variable = new Variable();
        Boolean includeAss = assistMappingList.size() > 0;
        if (includeAss.booleanValue()) {
            pzJoinSql.append("  JOIN GL_ASSISTCOMB ASSCOMB ON ASSCOMB.RECID = VOUCHERDETAIL.ASSCOMBID \n");
        }
        for (AssistMappingBO assistMapping : assistMappingList) {
            yeAssField.append(String.format("%1$s.CODE AS %1$s, \n", assistMapping.getAssistCode()));
            pzJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = ASSCOMB.%3$s \n", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
        }
        variable.put("YEASSFIELD", yeAssField.toString());
        variable.put("PZJOINSQL", pzJoinSql.toString());
        variable.put("MD_ACCTSUBJECT", schemeMappingProvider.getSubjectSql());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        executeSql = Va6FetchUtil.parse(executeSql, condi.getOrgMapping().getAcctOrgCode(), condi.getOrgMapping().getAcctBookCode(), String.valueOf(condi.getAcctYear()), String.valueOf(condi.getEndPeriod()), condi.getIncludeUncharged());
        QueryParam queryParam = new QueryParam(executeSql, new Object[]{condi.getVchrId()}, (ResultSetExtractor)new VoucherResultSetExtractor(assistMappingList));
        return queryParam;
    }
}

