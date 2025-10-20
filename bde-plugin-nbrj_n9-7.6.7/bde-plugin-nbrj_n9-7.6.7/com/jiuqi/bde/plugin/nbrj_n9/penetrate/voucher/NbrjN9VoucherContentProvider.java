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
package com.jiuqi.bde.plugin.nbrj_n9.penetrate.voucher;

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
import com.jiuqi.bde.plugin.nbrj_n9.BdeNbrjN9PluginType;
import com.jiuqi.bde.plugin.nbrj_n9.util.AssistPojo;
import com.jiuqi.common.base.util.Assert;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class NbrjN9VoucherContentProvider
extends AbstractVoucherContentProvider {
    @Autowired
    private BdeNbrjN9PluginType bdeNbrjN9PluginType;

    public String getPluginType() {
        return this.bdeNbrjN9PluginType.getSymbol();
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
        sql.append("SELECT VI.INO AS ID,   \n");
        sql.append("       VI.EXPL AS DIGEST,   \n");
        sql.append("       VI.CODE00 AS SUBJECTCODE,  \n");
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,   \n");
        sql.append("       CASE WHEN VI.VDC = 1 THEN VI.SCY ELSE 0 END AS DEBIT,  \n");
        sql.append("       CASE WHEN VI.VDC = -1 THEN VI.SCY ELSE 0 END AS CREDIT,  \n");
        sql.append("       CASE WHEN VI.VDC = 1 THEN VI.FCY ELSE 0 END AS ORGND,  \n");
        sql.append("       CASE WHEN VI.VDC = -1 THEN VI.FCY ELSE 0 END AS ORGNC  \n");
        sql.append("       ${ASSFIELD} ");
        sql.append("  FROM IVOUCHER VI  \n");
        sql.append("  LEFT JOIN (${EXTERNAL_SUBJECT_SQL}) SUBJECT ON SUBJECT.CODE = VI.CODE00 \n");
        sql.append(" ${VOUCHERASSTABLE} ");
        sql.append(" WHERE 1 = 1   \n");
        sql.append("   AND VI.HVICODE = ?   \n");
        StringBuilder assField = new StringBuilder();
        StringBuilder voucherAssTable = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            assField.append(String.format(", %1$s.CODE AS %1$s\n", assistMapping.getAssistCode()));
            voucherAssTable.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=%3$S.%4$s", assistMapping.getAssistSql(), assistMapping.getAssistCode(), "VI", ((AssistPojo)assistMapping.getAccountAssist()).getAssistField())).append(" \n");
        }
        Variable variable = new Variable();
        variable.put("ASSFIELD", assField.toString());
        variable.put("VOUCHERASSTABLE", voucherAssTable.toString());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(executeSql, new Object[]{condi.getVchrId()}, (ResultSetExtractor)new VoucherResultSetExtractor(assistMappingList));
        return queryParam;
    }
}

