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
package com.jiuqi.bde.plugin.nc5.penetrate.voucher;

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
import com.jiuqi.bde.plugin.nc5.BdeNc5PluginType;
import com.jiuqi.common.base.util.Assert;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Nc5VoucherContentProvider
extends AbstractVoucherContentProvider {
    @Autowired
    private BdeNc5PluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
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
        sql.append("SELECT ITEM.PK_DETAIL      AS ID,  \n");
        sql.append("       ITEM.EXPLANATION    AS DIGEST,  \n");
        sql.append("       KM.SUBJCODE         AS SUBJECTCODE,  \n");
        sql.append("       KM.SUBJNAME         AS SUBJECTNAME  \n");
        sql.append("       ${INASSFIELD}  \n");
        sql.append("       ,ITEM.DEBITAMOUNT      AS ORGND,  \n");
        sql.append("       ITEM.CREDITAMOUNT      AS ORGNC,  \n");
        sql.append("       ITEM.LOCALDEBITAMOUNT  AS DEBIT,  \n");
        sql.append("       ITEM.LOCALCREDITAMOUNT AS CREDIT  \n");
        sql.append("       FROM GL_DETAIL ITEM  \n");
        sql.append("       INNER JOIN (SELECT VCHR.PK_VOUCHER,VCHR.PREPAREDDATE,VCHR.PK_VOUCHERTYPE,VCHR.NO,VCHR.PK_GLORGBOOK   \n");
        sql.append("                          FROM GL_VOUCHER VCHR WHERE VCHR.PK_VOUCHER = ? ) VCHR ON VCHR.PK_VOUCHER = ITEM.PK_VOUCHER  \n");
        sql.append(" INNER JOIN BD_GLORGBOOK BOOK ON VCHR.PK_GLORGBOOK = BOOK.PK_GLORGBOOK \n");
        sql.append(" INNER JOIN BD_ACCSUBJ KM ON ITEM.PK_ACCSUBJ = KM.PK_ACCSUBJ AND BOOK.PK_GLORGBOOK = KM.PK_GLORGBOOK\n");
        sql.append(" ${ASSJOINSQL}");
        sql.append(" WHERE 1=1\n");
        sql.append("   ORDER BY ITEM.DETAILINDEX  \n");
        StringBuilder assJoinSql = new StringBuilder();
        StringBuilder outAssField = new StringBuilder();
        StringBuilder inAssField = new StringBuilder();
        StringBuilder inGroupField = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            if ("BD_ACCSUBJ".equals(assistMapping.getAccountAssistCode())) {
                inAssField.append(String.format(",KM.SUBJCODE AS %1$s", assistMapping.getAssistCode()));
                inAssField.append(String.format(",KM.SUBJNAME AS %1$s_NAME", assistMapping.getAssistCode()));
                continue;
            }
            assJoinSql.append(String.format(" LEFT JOIN (SELECT CHECKVALUE AS ID, FREEVALUEID ASSID, VALUECODE AS CODE, VALUENAME AS NAME FROM GL_FREEVALUE WHERE GL_FREEVALUE.CHECKTYPE ='%1$s')  %2$s ON %2$s.ASSID=%3$s.ASSID ", assistMapping.getAssistCode(), assistMapping.getAssistCode(), "ITEM"));
            inAssField.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            inAssField.append(String.format(",%1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            inGroupField.append(String.format(",%1$s.CODE,%1$s.NAME", assistMapping.getAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("ASSJOINSQL", assJoinSql.toString());
        variable.put("OUTASSFIELD", outAssField.toString());
        variable.put("INASSFIELD", inAssField.toString());
        variable.put("INGROUPFIELD", inGroupField.toString());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        return new QueryParam(PenetrateUtil.replaceContext((String)querySql, (PenetrateBaseDTO)condi), new Object[]{condi.getVchrId()}, (ResultSetExtractor)new VoucherResultSetExtractor(assistMappingList));
    }
}

