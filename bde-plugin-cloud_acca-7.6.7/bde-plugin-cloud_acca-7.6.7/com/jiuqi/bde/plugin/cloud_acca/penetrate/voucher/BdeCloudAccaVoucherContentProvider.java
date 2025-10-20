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
 *  com.jiuqi.dc.base.impl.assistdim.enums.ValueTypeEnum
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.cloud_acca.penetrate.voucher;

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
import com.jiuqi.bde.plugin.cloud_acca.BdeCloudAccaPluginType;
import com.jiuqi.bde.plugin.cloud_acca.assist.CloudAccaAssistPojo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.base.impl.assistdim.enums.ValueTypeEnum;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BdeCloudAccaVoucherContentProvider
extends AbstractVoucherContentProvider {
    @Autowired
    private BdeCloudAccaPluginType bdeCloudAccaPluginType;
    private static final String JOIN_SQL = " LEFT JOIN ( SELECT CODE ,MAX(${ASNAME}.NAME) AS NAME FROM ${TABLENAME} ${ASNAME} WHERE (${ASNAME}.UNITCODE IN(SELECT ORG.CODE FROM MD_ORG_FIN ORG INNER JOIN ( SELECT MDORG.PARENTS FROM MD_ORG_FIN MDORG WHERE MDORG.CODE = '${ORGCODE}' ) ORGT ON ORGT.PARENTS LIKE CONCAT(ORG.PARENTS, '%')) OR ${ASNAME}.UNITCODE='-') GROUP BY ${ASNAME}.CODE ) ${ASNAME} ON V.${TABLENAME} = ${ASNAME}.CODE";

    public String getPluginType() {
        return this.bdeCloudAccaPluginType.getSymbol();
    }

    public String getBizModel() {
        return "DEFAULT_CONTENTPROVIDER";
    }

    protected QueryParam<PenetrateVoucher> getQueryParam(VoucherPenetrateDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)ComputationModelEnum.ASSBALANCE.getCode());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        Object[] args = new Object[2];
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT");
        sql.append("    V.VCHRID AS ID,");
        sql.append("    V.DIGEST AS DIGEST,");
        sql.append("    V.SUBJECTCODE AS SUBJECTCODE ,");
        sql.append("    V.ORGNC AS ORGNC,");
        sql.append("    V.ORGND AS ORGND,");
        sql.append("    V.CREDIT AS CREDIT,");
        sql.append("    V.DEBIT AS DEBIT,");
        sql.append("    ${ASSFIELD}");
        sql.append("    SUBJECT.NAME AS SUBJECTNAME ");
        sql.append(" FROM");
        sql.append("    GL_VOUCHERITEMASS_${YEAR} V");
        sql.append(" LEFT JOIN(");
        sql.append("    SELECT");
        sql.append("        SUBJECT.CODE,");
        sql.append("        MAX(SUBJECT.NAME) AS NAME");
        sql.append("    FROM");
        sql.append("        MD_ACCTSUBJECT SUBJECT");
        sql.append("    WHERE");
        sql.append("        (SUBJECT.UNITCODE IN (");
        sql.append("            SELECT");
        sql.append("                ORG.CODE");
        sql.append("            FROM");
        sql.append("                MD_ORG_FIN ORG");
        sql.append("            INNER JOIN (");
        sql.append("                SELECT");
        sql.append("                    ORGFIN.PARENTS");
        sql.append("                FROM MD_ORG_FIN ORGFIN");
        sql.append("                WHERE");
        sql.append("                    ORGFIN.CODE = '${UNITCODE}' ) ORGFIN ON");
        sql.append("                    ORGFIN.PARENTS LIKE CONCAT(ORG.PARENTS, '%')) OR SUBJECT.UNITCODE='-' )");
        sql.append("                GROUP BY");
        sql.append("                    SUBJECT.CODE) SUBJECT ON");
        sql.append("                SUBJECT.CODE = V.SUBJECTCODE  ${ASSJOINSQL}");
        sql.append("    WHERE 1=1");
        sql.append("        ${WHERESQL} ");
        sql.append("        AND V.VCHRID = '${VCHRID}' ");
        sql.append("    ORDER BY V.CREATEDATE,V.VCHRTYPECODE,V.VCHRNUM,V.ITEMORDER");
        StringBuilder ass = new StringBuilder();
        StringBuilder whereSql = new StringBuilder();
        StringBuilder assJoinSql = new StringBuilder();
        StringBuilder assField = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            ValueTypeEnum valueTypeEnum = ValueTypeEnum.fromCode((String)((CloudAccaAssistPojo)assistMapping.getAccountAssist()).getValueType());
            switch (valueTypeEnum) {
                case BASEDATA: {
                    Variable joinVariable = new Variable();
                    joinVariable.put("TABLENAME", assistMapping.getAccountAssistCode());
                    joinVariable.put("ASNAME", assistMapping.getAssistCode());
                    joinVariable.put("ORGCODE", condi.getUnitCode());
                    joinVariable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
                    assJoinSql.append(VariableParseUtil.parse((String)JOIN_SQL, (Map)joinVariable.getVariableMap()));
                    break;
                }
            }
            ass.append(String.format("V.%1$s AS %2$s,", assistMapping.getAccountAssistCode(), assistMapping.getAssistCode()));
            assField.append(String.format("V.%1$s,", assistMapping.getAccountAssistCode()));
        }
        args[0] = condi.getUnitCode();
        args[1] = condi.getVchrId();
        Variable variable = new Variable();
        variable.put("SUBJECTCODE", condi.getSubjectCode());
        variable.put("UNITCODE", condi.getUnitCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("ASSFIELD", ass.toString());
        variable.put("WHERESQL", whereSql.toString());
        variable.put("ASSJOINSQL", assJoinSql.toString());
        variable.put("VASSFIELD", assField.toString());
        variable.put("VCHRID", condi.getVchrId());
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        return new QueryParam(PenetrateUtil.replaceContext((String)lastSql, (PenetrateBaseDTO)condi), new Object[0], (ResultSetExtractor)new VoucherResultSetExtractor(assistMappingList));
    }
}

