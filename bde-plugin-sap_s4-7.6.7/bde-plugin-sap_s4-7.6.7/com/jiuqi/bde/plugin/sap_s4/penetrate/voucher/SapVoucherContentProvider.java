/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist
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
package com.jiuqi.bde.plugin.sap_s4.penetrate.voucher;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.impl.BaseAcctAssist;
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
import com.jiuqi.bde.plugin.sap_s4.BdeSapS4PluginType;
import com.jiuqi.bde.plugin.sap_s4.util.SapS4DataSchemeMappingProvider;
import com.jiuqi.common.base.util.Assert;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class SapVoucherContentProvider
extends AbstractVoucherContentProvider {
    @Autowired
    private BdeSapS4PluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public String getBizModel() {
        return "DEFAULT_CONTENTPROVIDER";
    }

    protected QueryParam<PenetrateVoucher> getQueryParam(VoucherPenetrateDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isNotEmpty((String)condi.getVchrId(), (String)"\u51ed\u8bc1\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        String[] keyArr = condi.getVchrId().split("\\|");
        Assert.isTrue((keyArr.length == 4 ? 1 : 0) != 0, (String)"\u51ed\u8bc1\u6807\u8bc6\u683c\u5f0f\u9519\u8bef", (Object[])new Object[0]);
        String mandt = keyArr[0];
        String bukrs = keyArr[1];
        Integer gjahr = Integer.valueOf(keyArr[2]);
        String belnr = keyArr[3];
        StringBuilder sql = new StringBuilder();
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)ComputationModelEnum.VOUCHER.getCode());
        fetchCondi.setEnableDirectFilter(true);
        SapS4DataSchemeMappingProvider schemeMappingProvider = new SapS4DataSchemeMappingProvider(fetchCondi);
        List<AssistMappingBO<BaseAcctAssist>> assistMappingList = schemeMappingProvider.getAssistMappingList();
        sql.append("Select Master.BLART as VCHRTYPE,  \n");
        sql.append("       Rtrim(Master.Belnr) as VCHRNUM,  \n");
        sql.append("       to_char(TO_DATE(Master.budat, 'yyyyMMdd'), 'yyyy-MM-dd') as CREATEDATE,  \n");
        sql.append("       Master.MONAT as PERIOD,  \n");
        sql.append("       Master.PPNAM as CREATEUSER,  \n");
        sql.append("       '' as CHECKER,  \n");
        sql.append("       '' as POSTER,  \n");
        sql.append("       '' as CASHIER,  \n");
        sql.append("       T.SGTXT as DIGEST,  \n");
        sql.append("       T.HKONT as SUBJECTCODE,  \n");
        sql.append("       MD_ACCTSUBJECT.NAME as SUBJECTNAME  \n");
        sql.append("   ${EXTERNAL_SELECT_SQL}  \n");
        sql.append("       ,CASE WHEN T.XNEGP = 'X' THEN CASE WHEN T.SHKZG = 'S' THEN 0 ELSE -1 * T.DMBTR END ELSE CASE WHEN T.SHKZG = 'S' THEN T.DMBTR ELSE 0 END END AS DEBIT,  \n");
        sql.append("        CASE WHEN T.XNEGP = 'X' THEN CASE WHEN T.SHKZG = 'S' THEN 0 ELSE -1 * T.WRBTR END ELSE CASE WHEN T.SHKZG = 'S' THEN T.WRBTR ELSE 0 END END AS CREDIT,  \n");
        sql.append("        CASE WHEN T.XNEGP = 'X' THEN CASE WHEN T.SHKZG = 'H' THEN 0 ELSE -1 * T.DMBTR END ELSE CASE WHEN T.SHKZG = 'H' THEN T.DMBTR ELSE 0 END END AS ORGND,  \n");
        sql.append("        CASE WHEN T.XNEGP = 'X' THEN CASE WHEN T.SHKZG = 'H' THEN 0 ELSE -1 * T.WRBTR END ELSE CASE WHEN T.SHKZG = 'H' THEN T.WRBTR ELSE 0 END END AS ORGNC  \n");
        sql.append("  FROM BKPF MASTER  \n");
        sql.append(" INNER JOIN BSEG T  \n");
        sql.append("         ON MASTER.MANDT = T.MANDT  \n");
        sql.append("        AND MASTER.BUKRS = T.BUKRS  \n");
        sql.append("        AND MASTER.BELNR = T.BELNR  \n");
        sql.append("        AND MASTER.GJAHR = T.GJAHR  \n");
        sql.append(" LEFT JOIN (${EXTERNAL_SUBJECT_SQL}) MD_ACCTSUBJECT ON MD_ACCTSUBJECT.CODE = T.HKONT \n");
        sql.append(" ${EXTERNAL_JOIN_SQL} \n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append("   And Master.MANDT = ?  \n");
        sql.append("   And Master.BUKRS = ?  \n");
        sql.append("   And Master.GJAHR = ?  \n");
        sql.append("   And Master.BELNR = ?  \n");
        sql.append("   ORDER BY T.BUZEI  \n");
        StringBuilder externalSelectSql = new StringBuilder();
        StringBuilder externalJoinSql = new StringBuilder();
        for (AssistMappingBO<BaseAcctAssist> assistMapping : assistMappingList) {
            externalSelectSql.append(String.format(",%1$s AS %2$s,%2$s.NAME AS %2$s_NAME", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            externalJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.ID = %3$s ", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("UNITCODE", bukrs);
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        variable.put("EXTERNAL_SELECT_SQL", externalSelectSql.toString());
        variable.put("EXTERNAL_JOIN_SQL", externalJoinSql.toString());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(querySql, new Object[]{mandt, bukrs, gjahr, belnr}, (ResultSetExtractor)new VoucherResultSetExtractor(assistMappingList));
        return queryParam;
    }
}

