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
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.XjllDetailLedgerResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nbrj_n9.penetrate.detailledger;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractDetailLedgerContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.XjllDetailLedgerResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.nbrj_n9.BdeNbrjN9PluginType;
import com.jiuqi.bde.plugin.nbrj_n9.util.AssistPojo;
import com.jiuqi.bde.plugin.nbrj_n9.util.NbrjN9FetchUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class NbrjN9XjllDetailLedgerContentProvider
extends AbstractDetailLedgerContentProvider {
    @Autowired
    private BdeNbrjN9PluginType pluginType;
    @Autowired
    private DataSchemeService dataSchemeService;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    protected QueryParam<PenetrateDetailLedger> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isNotEmpty((String)condi.getCashCode(), (String)"\u73b0\u6d41\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        ArrayList<Integer> args = new ArrayList<Integer>();
        StringBuilder sql = new StringBuilder();
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        sql.append("SELECT 0 AS ROWTYPE,  \n");
        sql.append("       VI.INO AS ID,  \n");
        sql.append("       VI.HVICODE AS VCHRID,  \n");
        sql.append(String.format("       %d as ACCTYEAR,  \n", condi.getAcctYear()));
        sql.append("       VI.MONTH AS ACCTPERIOD,  \n");
        sql.append(String.format("       %s AS ACCTDAY,  \n", sqlHandler.day("VI.VDATE")));
        sql.append(sqlHandler.concatBySeparator(" ", new String[]{"' '", String.format("%s", sqlHandler.toChar("VI.VNO"))})).append(" AS VCHRTYPE, \n");
        sql.append(String.format("       %s AS PZNUMBER,  \n", sqlHandler.lpad("VI.VNO", 15, "0")));
        sql.append("       VI.INO AS ORDERID,  \n");
        sql.append("       SUBJECT.ORIENT AS ORIENT,  \n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append("          SUBJECT.ACODE AS SUBJECTCODE,  \n");
            sql.append("          SUBJECT.LANGNAME1 AS SUBJECTNAME,   \n");
        }
        sql.append("       VI.RCODE AS MD_CFITEM,  \n");
        sql.append("       CR.RNAME AS MD_CFITEM_NAME,\n");
        sql.append(String.format("       %s AS DIGEST,  \n", sqlHandler.toChar("VI.EXPL")));
        sql.append("       CASE WHEN VI.VDC = 1 THEN VI.SCY ELSE 0 END AS DEBIT,  \n");
        sql.append("       CASE WHEN VI.VDC = -1 THEN VI.SCY ELSE 0 END AS CREDIT,  \n");
        sql.append("       CASE WHEN VI.VDC = 1 THEN VI.FCY ELSE 0 END AS ORGND,  \n");
        sql.append("       CASE WHEN VI.VDC = -1 THEN VI.FCY ELSE 0 END AS ORGNC,  \n");
        sql.append("       0 AS YE,  \n");
        sql.append("       0 AS ORGNYE  \n");
        sql.append("       ${ASSFIELD} \n");
        sql.append("  FROM IVOUCHER VI  \n");
        sql.append("  LEFT JOIN (${EXTERNAL_SUBJECT_SQL}) SUBJECT ON SUBJECT.CODE = VI.CODE00 \n");
        sql.append("  LEFT JOIN RCODE CR  \n");
        sql.append("    ON VI.RCODE = CR.RCODE  \n");
        sql.append("  ${ASSJOIN} \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append(NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "VI."));
        sql.append("   AND VI.YEAR = ? \n");
        args.add(condi.getAcctYear());
        sql.append("          AND VI.MONTH > (SELECT MIN(MONTH) \n");
        sql.append("                     \t\tFROM MONTHLOGB  \n");
        sql.append("                     \t  WHERE 1 = 1 " + NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "") + " AND YEAR = ${YEAR})  \n");
        sql.append("   AND VI.MONTH<=${ENDPERIOD} \n");
        sql.append("   AND VI.MONTH>=${STARTPERIOD} \n");
        sql.append(this.buildCfItemCondi("VI", "RCODE", condi.getCashCode()));
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(this.buildSubjectCondi("SUBJECT", "ACODE", condi.getSubjectCode()));
        }
        sql.append("   ${EXTERNAL_ASS_CONFIG_SQL} \n");
        sql.append(" ORDER BY ROWTYPE DESC, ACCTYEAR, ACCTPERIOD, ACCTDAY, VCHRTYPE, ORDERID");
        StringBuilder assJoin = new StringBuilder();
        StringBuilder assField = new StringBuilder();
        StringBuilder externalAssConfigSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            assField.append(String.format(", %1$s.CODE AS %1$s, %1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            assJoin.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=%3$S.%4$s", assistMapping.getAssistSql(), assistMapping.getAssistCode(), "VI", ((AssistPojo)assistMapping.getAccountAssist()).getAssistField()));
            externalAssConfigSql.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), "EQ"));
        }
        Variable variable = new Variable();
        variable.put("ASSFIELD", assField.toString());
        variable.put("ASSJOIN", assJoin.toString());
        variable.put("STARTPERIOD", condi.getStartPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("EXTERNAL_ASS_CONFIG_SQL", externalAssConfigSql.toString());
        variable.put("EXTERNAL_SUBJECT_SQL", schemeMappingProvider.getSubjectSql());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        return new QueryParam(querySql, args.toArray(), (ResultSetExtractor)new XjllDetailLedgerResultSetExtractor(condi, assistMappingList));
    }
}

