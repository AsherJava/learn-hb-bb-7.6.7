/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
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
package com.jiuqi.bde.plugin.bip_flagship.pentrate.voucher;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
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
import com.jiuqi.bde.plugin.bip_flagship.BdeBipFlagShipPluginType;
import com.jiuqi.bde.plugin.bip_flagship.util.AssistPojo;
import com.jiuqi.bde.plugin.bip_flagship.util.BipFlagShipFetchUtil;
import com.jiuqi.common.base.util.Assert;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BipFlagVoucherContentProvider
extends AbstractVoucherContentProvider {
    @Autowired
    private BdeBipFlagShipPluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
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
        Variable variable = new Variable();
        StringBuilder assJoinSql = new StringBuilder();
        StringBuilder inSelectField = new StringBuilder();
        StringBuilder inGroupField = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            assJoinSql.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=ASSTABLE.DEF%3$s \n", assistMapping.getAssistSql(), assistMapping.getAssistCode(), ((AssistPojo)assistMapping.getAccountAssist()).getDocNum()));
            inSelectField.append(String.format(",%1$s.CODE AS %1$s,%1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            inGroupField.append(String.format(",%1$s.CODE,%1$s.NAME", assistMapping.getAssistCode()));
        }
        variable.put("EXTERNAL_SUBJECT_SQL", ModelExecuteUtil.replaceContext((String)schemeMappingProvider.getSubjectSql(), (BalanceCondition)fetchCondi));
        variable.put("STARTPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), condi.getStartPeriod()));
        variable.put("ENDPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), condi.getEndPeriod()));
        variable.put("NCPERIODUNION", BipFlagShipFetchUtil.getPeriodUnion(condi.getAcctYear(), 0));
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("BALANCEASSJOINSQL", " LEFT JOIN FI_AUXILIARY ASSTABLE ON B.AUXILIARY=ASSTABLE.ID ");
        variable.put("VOUCHERASSJOINSQL", " LEFT JOIN FI_AUXILIARY ASSTABLE ON DETAIL.AUXILIARY=ASSTABLE.ID ");
        variable.put("ASSTABLEJOIN", assJoinSql.toString());
        variable.put("INSELECTFIELD", inSelectField.toString());
        String executeSql = VariableParseUtil.parse((String)this.getVoucherSql(condi), (Map)variable.getVariableMap());
        return new QueryParam(PenetrateUtil.replaceContext((String)executeSql, (PenetrateBaseDTO)condi), new Object[]{condi.getVchrId()}, (ResultSetExtractor)new VoucherResultSetExtractor(assistMappingList));
    }

    private String getVoucherSql(VoucherPenetrateDTO condi) {
        StringBuilder querySql = new StringBuilder();
        querySql.append(" SELECT\n");
        querySql.append("          DETAIL.ID AS ID,\n");
        querySql.append("          DETAIL.DESCRIPTION AS DIGEST,\n");
        querySql.append("          SUBJECT.CODE AS SUBJECTCODE,\n");
        querySql.append("          SUBJECT.NAME AS SUBJECTNAME\n");
        querySql.append("          ${INSELECTFIELD}");
        querySql.append("          ,DETAIL.CREDIT_ORG AS CREDIT,  \n");
        querySql.append("          DETAIL.DEBIT_ORG AS DEBIT,  \n");
        querySql.append("          DETAIL.DEBIT_ORIGINAL AS ORGND,  \n");
        querySql.append("          DETAIL.CREDIT_ORIGINAL AS ORGNC,  \n");
        querySql.append("          0 AS YE,\n");
        querySql.append("          0 AS ORGNYE\n");
        querySql.append(" FROM\n");
        querySql.append("          (SELECT FI_VOUCHER.PERIODUNION,FI_VOUCHER.ID,FI_VOUCHER.VOUCHERTYPE,BOOK.CODE AS BOOKCODE  \n");
        querySql.append("            FROM  FI_VOUCHER  \n");
        querySql.append("           INNER JOIN EPUB_ACCOUNTBOOK BOOK ON BOOK.ID = FI_VOUCHER.ACCBOOK");
        querySql.append(BipFlagShipFetchUtil.buildUnitSql("BOOK.CODE", condi.getOrgMapping())).append(" \n");
        querySql.append("           WHERE 1=1  \n");
        if (condi.getIncludeUncharged().booleanValue()) {
            querySql.append("                  AND FI_VOUCHER.VOUCHERSTATUS IN('04','01','03') \n");
        } else {
            querySql.append("                  AND FI_VOUCHER.VOUCHERSTATUS = '04' \n");
        }
        querySql.append("                 AND FI_VOUCHER.VOUCHERSTATUS<>'05') VOUCHER  \n");
        querySql.append(" JOIN EPUB_VOUCHERTYPE VOUCHERTYPE ON VOUCHERTYPE.ID = VOUCHER.VOUCHERTYPE");
        querySql.append(" JOIN FI_VOUCHER_B DETAIL ON\n");
        querySql.append("          DETAIL.VOUCHERID = VOUCHER.ID\n");
        querySql.append(" JOIN (${EXTERNAL_SUBJECT_SQL}) SUBJECT ON\n");
        querySql.append("          SUBJECT.ID = DETAIL.ACCSUBJECT AND SUBJECT.BOOKCODE =VOUCHER.BOOKCODE\n");
        querySql.append(" JOIN BD_CURRENCY_TENANT CURRENCY ON\n");
        querySql.append("          CURRENCY.ID = DETAIL.CURRENCY\n");
        querySql.append(" ${VOUCHERASSJOINSQL}");
        querySql.append(" ${ASSTABLEJOIN}");
        querySql.append(" WHERE\n");
        querySql.append("          1 = 1\n");
        querySql.append("          AND VOUCHER.ID=?\n");
        querySql.append(" ORDER BY DETAIL.ID");
        return querySql.toString();
    }
}

