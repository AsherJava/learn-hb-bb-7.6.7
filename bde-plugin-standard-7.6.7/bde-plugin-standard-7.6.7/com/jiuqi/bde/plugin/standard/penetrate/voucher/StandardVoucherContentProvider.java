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
 *  com.jiuqi.common.base.util.StringUtils
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.standard.penetrate.voucher;

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
import com.jiuqi.bde.plugin.standard.BdeStandardPluginType;
import com.jiuqi.bde.plugin.standard.util.StandardFetchUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class StandardVoucherContentProvider
extends AbstractVoucherContentProvider {
    @Autowired
    private BdeStandardPluginType bdeStandardPluginType;

    public String getPluginType() {
        return this.bdeStandardPluginType.getSymbol();
    }

    public String getBizModel() {
        return "DEFAULT_CONTENTPROVIDER";
    }

    protected QueryParam<PenetrateVoucher> getQueryParam(VoucherPenetrateDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isNotEmpty((String)condi.getVchrId(), (String)"\u51ed\u8bc1\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        String[] keyArr = condi.getVchrId().split("\\|");
        Assert.isTrue((keyArr.length == 5 ? 1 : 0) != 0, (String)"\u51ed\u8bc1\u6807\u8bc6\u683c\u5f0f\u9519\u8bef", (Object[])new Object[0]);
        String ORGID = keyArr[0];
        Integer ZTYEAR = Integer.valueOf(keyArr[1]);
        Integer PZPERIOD = Integer.valueOf(keyArr[2]);
        String PZGROUPID = keyArr[3];
        String PZNUMBER = keyArr[4];
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)ComputationModelEnum.VOUCHER.getCode());
        fetchCondi.setEnableDirectFilter(true);
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.RWID AS ID,   \n");
        sql.append("       T.DIGEST AS DIGEST,   \n");
        sql.append("       T.KMCODE AS SUBJECTCODE,   \n");
        sql.append("       SUBJECT.NAME AS SUBJECTNAME,   \n");
        sql.append("       ${EXTERNAL_SELECT_SQL}   ");
        sql.append("       T.DEBITORGN AS ORGND,  \n");
        sql.append("       T.CREDITORGN AS ORGNC,  \n");
        sql.append("       T.DEBIT AS DEBIT,  \n");
        sql.append("       T.CREDIT AS CREDIT  \n");
        sql.append("       FROM ZW_PZINFOR T   \n");
        sql.append("  LEFT JOIN (${SUBJECT_JOIN_SQL}) SUBJECT ON SUBJECT.CODE = T.KMCODE  \n");
        sql.append("  ${EXTERNAL_ASSJOIN_SQL}   \n");
        sql.append(" WHERE 1 = 1   \n");
        sql.append("   AND T.ORGID = ?   \n");
        args.add(ORGID);
        sql.append(StandardFetchUtil.buildAssistSql(orgMappingType, condi.getOrgMapping()));
        sql.append("   AND T.ZTYEAR = ?   \n");
        args.add(ZTYEAR);
        sql.append("   AND T.PZPERIOD = ?   \n");
        args.add(PZPERIOD);
        sql.append("   AND T.PZGROUPID = ?   \n");
        args.add(PZGROUPID);
        sql.append("   AND T.PZNUMBER = ?   \n");
        args.add(PZNUMBER);
        StringBuilder externalAssJoinSql = new StringBuilder();
        StringBuilder externalSubjectJoinSql = new StringBuilder();
        StringBuilder externalSelectSql = new StringBuilder();
        externalSubjectJoinSql.append(PenetrateUtil.replaceContext((String)schemeMappingProvider.getSubjectSql(), (PenetrateBaseDTO)condi));
        for (AssistMappingBO assistMapping : assistMappingList) {
            externalAssJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON %2$s.CODE = T.%3$s \n", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            externalSelectSql.append(String.format("T.%1$s AS %2$s, \n", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("EXTERNAL_ASSJOIN_SQL", externalAssJoinSql.toString());
        variable.put("SUBJECT_JOIN_SQL", externalSubjectJoinSql.toString());
        variable.put("EXTERNAL_SELECT_SQL", externalSelectSql.toString());
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new VoucherResultSetExtractor(assistMappingList));
        return queryParam;
    }
}

