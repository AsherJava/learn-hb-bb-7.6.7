/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nbrj_n9.penetrate.balance;

import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.BalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.nbrj_n9.BdeNbrjN9PluginType;
import com.jiuqi.bde.plugin.nbrj_n9.util.AssistPojo;
import com.jiuqi.bde.plugin.nbrj_n9.util.NbrjN9FetchUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractNbrjN9aBalanceContentProvider
extends AbstractBalanceContentProvider {
    @Autowired
    private BdeNbrjN9PluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        Assert.isTrue((condi.getOffset() != null && condi.getOffset() >= 0 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u5f00\u59cb\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        Assert.isTrue((condi.getLimit() != null && condi.getLimit() > 0 && condi.getLimit() < 5000 ? 1 : 0) != 0, (String)"\u5206\u9875\u53c2\u6570\u3010\u6bcf\u9875\u6761\u6570\u3011\u4e0d\u5408\u7406", (Object[])new Object[0]);
        IDbSqlHandler sqlHandler = this.getDbSqlHandler(condi.getDataScheme().getDataSourceCode());
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT MASTER.SUBJECTCODE AS SUBJECTCODE, \n");
        sql.append("        MASTER.SUBJECTNAME AS SUBJECTNAME, \n");
        sql.append("        ORIENT AS ORIENT, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.JF ELSE 0 END) AS DEBIT, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.DF ELSE 0 END) AS CREDIT, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.YE ELSE 0 END) AS YE, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD = 0 THEN MASTER.NC ELSE 0 END) AS NC, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<${STARTPERIOD} THEN MASTER.C ELSE 0 END) AS QC, \n");
        sql.append("        SUM(MASTER.JF) AS DSUM, \n");
        sql.append("        SUM(MASTER.DF) AS CSUM, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.WJF ELSE 0 END) AS ORGND, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD>=${STARTPERIOD} AND MASTER.PERIOD<=${ENDPERIOD} THEN MASTER.WDF ELSE 0 END) AS ORGNC, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<=${ENDPERIOD} THEN WYE ELSE 0 END) AS ORGNYE, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD=0 THEN MASTER.WNC ELSE 0 END) AS ORGNNC, \n");
        sql.append("        SUM(CASE WHEN MASTER.PERIOD<${STARTPERIOD} THEN MASTER.WC ELSE 0 END)AS ORGNQC, \n");
        sql.append("        SUM(WJF)AS ORGNDSUM, \n");
        sql.append("        SUM(WDF)AS ORGNCSUM \n");
        sql.append("        ${ASSFIELDWRAPPER} \n");
        sql.append(" FROM ( \n");
        sql.append("    SELECT SUBJECT.ACODE as SUBJECTCODE,  \n");
        sql.append("           SUBJECT.LANGNAME1 AS SUBJECTNAME, \n");
        sql.append("           SUBJECT.DC AS ORIENT, \n");
        sql.append("           0 AS JF, \n");
        sql.append("           0 AS DF,\n");
        sql.append("           SUM(").append(sqlHandler.nullToValue("B.SCYBALANCE", "0")).append("* SUBJECT.DC) AS YE,\n");
        sql.append("           SUM(").append(sqlHandler.nullToValue("B.SCYBALANCE", "0")).append("* SUBJECT.DC) AS NC,\n");
        sql.append("           0 AS WJF,\n");
        sql.append("           0 AS WDF,\n");
        sql.append("           SUM(").append(sqlHandler.nullToValue("B.FCYBALANCE", "0")).append("* SUBJECT.DC) AS WYE,\n");
        sql.append("           SUM(").append(sqlHandler.nullToValue("B.FCYBALANCE", "0")).append("* SUBJECT.DC) AS WNC,\n");
        sql.append("           SUM(").append(sqlHandler.nullToValue("B.SCYBALANCE", "0")).append("* SUBJECT.DC) AS C,\n");
        sql.append("           SUM(").append(sqlHandler.nullToValue("B.FCYBALANCE", "0")).append("* SUBJECT.DC) AS WC,\n");
        sql.append("           0 AS PERIOD \n");
        sql.append("           ${ASSFIELD} \n");
        sql.append("    FROM  BALANCE B LEFT JOIN ACODE SUBJECT ON SUBJECT.ACODE = B.CODE00  \n");
        sql.append("         ${BALANCEASSTABLE} \n");
        sql.append("    WHERE  1 = 1  \n");
        sql.append(NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "B."));
        sql.append("\t        AND B.YEAR = ${YEAR}  \n");
        sql.append("            AND B.MONTH = (SELECT MIN(MONTH)  \n");
        sql.append("                           FROM MONTHLOGB  \n");
        sql.append("                     \t  WHERE 1 = 1 " + NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "") + " AND YEAR = ${YEAR})  \n");
        sql.append("    GROUP BY \n");
        sql.append("\t    SUBJECT.ACODE, \n");
        sql.append("        SUBJECT.DC, \n");
        sql.append("        B.CODE04, \n");
        sql.append("        SUBJECT.LANGNAME1  \n");
        sql.append("        ${ASSGROUPFIELD} \n");
        sql.append("    UNION ALL   \n");
        sql.append("    SELECT SUBJECT.ACODE as SUBJECTCODE,\n \n");
        sql.append("           SUBJECT.LANGNAME1 AS SUBJECTNAME, \n");
        sql.append("           SUBJECT.DC AS ORIENT,  \n");
        sql.append("           SUM(CASE WHEN VI.VDC = 1 THEN ").append(sqlHandler.nullToValue("VI.SCY", "0")).append("  ELSE 0 END) JF, \n");
        sql.append("           SUM(CASE WHEN VI.VDC = -1 THEN ").append(sqlHandler.nullToValue("VI.SCY", "0")).append(" ELSE 0 END) DF, \n");
        sql.append("           SUM(").append(sqlHandler.nullToValue("VI.SCY", "0")).append(") * VI.VDC  AS YE, \n");
        sql.append("           0 AS NC, \n");
        sql.append("           SUM(CASE WHEN VI.VDC = 1 THEN ").append(sqlHandler.nullToValue("VI.FCY", "0")).append(" ELSE 0 END) WJF,\n");
        sql.append("           SUM(CASE WHEN VI.VDC = -1 THEN ").append(sqlHandler.nullToValue("VI.FCY", "0")).append(" ELSE 0 END) WDF,\n");
        sql.append("           SUM(").append(sqlHandler.nullToValue("VI.FCY", "0")).append(") * VI.VDC AS WYE,\n");
        sql.append("           0 AS WNC,\n");
        sql.append("           SUM(").append(sqlHandler.nullToValue("VI.SCY", "0")).append(") * VI.VDC AS C,\n");
        sql.append("           SUM(").append(sqlHandler.nullToValue("VI.FCY", "0")).append(") * VI.VDC AS WC, \n");
        sql.append("           VI.MONTH AS PERIOD \n");
        sql.append("           ${ASSFIELD} \n");
        sql.append("    FROM IVOUCHER VI LEFT JOIN ACODE SUBJECT ON SUBJECT.ACODE = VI.CODE00 \n");
        sql.append("         ${VOUCHERASSTABLE} \n");
        sql.append("    WHERE  1 = 1  \n");
        sql.append(NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "VI."));
        sql.append("          AND VI.YEAR = ${YEAR} \n");
        sql.append("          AND VI.MONTH > (SELECT MIN(MONTH) \n");
        sql.append("                     \t\tFROM MONTHLOGB  \n");
        sql.append("                     \t  WHERE 1 = 1 " + NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "") + " AND YEAR = ${YEAR})  \n");
        sql.append("    GROUP BY   \n");
        sql.append("\t    SUBJECT.ACODE, \n");
        sql.append("        SUBJECT.DC, \n");
        sql.append("        VI.CODE04, \n");
        sql.append("        VI.VDC, \n");
        sql.append("        VI.MONTH, \n");
        sql.append("        SUBJECT.LANGNAME1 \n");
        sql.append("        ${ASSGROUPFIELD} \n");
        sql.append("    ) MASTER  \n");
        sql.append(" WHERE MASTER.SUBJECTCODE is NOT NULL  \n");
        sql.append(this.buildSubjectCondi("MASTER", "SUBJECTCODE", condi.getSubjectCode()));
        sql.append(this.buildExcludeCondi("MASTER", "SUBJECTCODE", condi.getExcludeSubjectCode()));
        sql.append(" ${ASSCONDITION} \n");
        sql.append(" GROUP BY MASTER.SUBJECTCODE, \n");
        sql.append("          MASTER.SUBJECTNAME, \n");
        sql.append("          MASTER.ORIENT \n");
        sql.append("          ${ASSGROUPFIELDWARPPER} \n");
        sql.append(" ORDER BY MASTER.SUBJECTCODE");
        Variable variable = new Variable();
        StringBuilder assField = new StringBuilder();
        StringBuilder assGroupField = new StringBuilder();
        StringBuilder balanceAssTable = new StringBuilder();
        StringBuilder voucherAssTable = new StringBuilder();
        StringBuilder assFieldWrapper = new StringBuilder();
        StringBuilder assCondition = new StringBuilder();
        StringBuilder assGroupFieldWarpper = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            balanceAssTable.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=%3$S.%4$s", assistMapping.getAssistSql(), assistMapping.getAssistCode(), "B", ((AssistPojo)assistMapping.getAccountAssist()).getAssistField()));
            voucherAssTable.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=%3$S.%4$s", assistMapping.getAssistSql(), assistMapping.getAssistCode(), "VI", ((AssistPojo)assistMapping.getAccountAssist()).getAssistField()));
            assField.append(String.format(",%1$s.CODE AS %1$s, %1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            assGroupField.append(String.format(",%1$s.CODE,%1$s.NAME", assistMapping.getAssistCode()));
            assFieldWrapper.append(String.format(",MASTER.%1$s AS %1$s,MASTER.%1$s_NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            assGroupFieldWarpper.append(String.format(",MASTER.%1$s,MASTER.%1$s_NAME", assistMapping.getAssistCode()));
            assCondition.append(this.matchByRule("MASTER", assistMapping.getExecuteDim().getDimCode(), assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule()));
        }
        variable.put("ASSFIELD", assField.toString());
        variable.put("ASSFIELDWRAPPER", assFieldWrapper.toString());
        variable.put("ASSGROUPFIELDWARPPER", assGroupFieldWarpper.toString());
        variable.put("ASSGROUPFIELD", assGroupField.toString());
        variable.put("BALANCEASSTABLE", balanceAssTable.toString());
        variable.put("VOUCHERASSTABLE", voucherAssTable.toString());
        variable.put("STARTPERIOD", condi.getStartPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("ASSCONDITION", assCondition.toString());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        QueryParam queryParam = new QueryParam(querySql, new Object[0], (ResultSetExtractor)new BalanceResultSetExtractor(assistMappingList));
        return queryParam;
    }
}

