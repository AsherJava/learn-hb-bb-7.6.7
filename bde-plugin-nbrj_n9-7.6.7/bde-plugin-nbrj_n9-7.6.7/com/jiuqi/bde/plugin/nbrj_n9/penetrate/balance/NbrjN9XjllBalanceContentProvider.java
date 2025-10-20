/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.bde.penetrate.impl.core.content.AbstractXjllBalanceContentProvider
 *  com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance
 *  com.jiuqi.bde.penetrate.impl.core.intf.QueryParam
 *  com.jiuqi.bde.penetrate.impl.core.model.res.XjllBalanceResultSetExtractor
 *  com.jiuqi.bde.penetrate.impl.util.PenetrateUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nbrj_n9.penetrate.balance;

import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.core.content.AbstractXjllBalanceContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.bde.penetrate.impl.core.intf.QueryParam;
import com.jiuqi.bde.penetrate.impl.core.model.res.XjllBalanceResultSetExtractor;
import com.jiuqi.bde.penetrate.impl.util.PenetrateUtil;
import com.jiuqi.bde.plugin.nbrj_n9.BdeNbrjN9PluginType;
import com.jiuqi.bde.plugin.nbrj_n9.util.AssistPojo;
import com.jiuqi.bde.plugin.nbrj_n9.util.NbrjN9FetchUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class NbrjN9XjllBalanceContentProvider
extends AbstractXjllBalanceContentProvider {
    @Autowired
    private BdeNbrjN9PluginType bdeNbrjN9PluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public String getPluginType() {
        return this.bdeNbrjN9PluginType.getSymbol();
    }

    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        IDbSqlHandler SQL_HANDLER = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        ArrayList args = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT\n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append("          SUBJECT.ACODE AS MD_ACCTSUBJECT,  \n");
            sql.append("          SUBJECT.LANGNAME1 AS MD_ACCTSUBJECT_NAME,\n");
        }
        sql.append("          VI.RCODE AS MD_CFITEM,  \n");
        sql.append("          CR.RNAME AS MD_CFITEM_NAME,\n");
        sql.append("       SUM(CASE WHEN VI.MONTH>=${STARTPERIOD} AND VI.MONTH<=${ENDPERIOD} AND VI.VDC = 1 THEN ").append(SQL_HANDLER.nullToValue("VI.SCY", "0")).append("WHEN VI.MONTH>=${STARTPERIOD} AND VI.MONTH<=${ENDPERIOD} AND VI.VDC = -1 THEN ").append(SQL_HANDLER.nullToValue("VI.SCY * -1", "0")).append(" ELSE 0 END) AS BQNUM, \n");
        sql.append("       SUM(CASE WHEN VI.MONTH>=${STARTPERIOD} AND VI.MONTH<=${ENDPERIOD} AND VI.VDC = 1 THEN ").append(SQL_HANDLER.nullToValue("VI.FCY", "0")).append("WHEN VI.MONTH>=${STARTPERIOD} AND VI.MONTH<=${ENDPERIOD} AND VI.VDC = -1 THEN ").append(SQL_HANDLER.nullToValue("VI.FCY * -1", "0")).append(" ELSE 0 END) AS WBQNUM, \n");
        sql.append("       SUM(CASE WHEN VI.MONTH<=${ENDPERIOD} AND VI.VDC = 1 THEN ").append(SQL_HANDLER.nullToValue("VI.SCY", "0")).append("WHEN VI.MONTH<=${ENDPERIOD} AND VI.VDC = -1 THEN ").append(SQL_HANDLER.nullToValue("VI.SCY * -1", "0")).append(" ELSE 0 END) AS LJNUM, \n");
        sql.append("       SUM(CASE WHEN VI.MONTH<=${ENDPERIOD} AND VI.VDC = 1 THEN ").append(SQL_HANDLER.nullToValue("VI.FCY", "0")).append("WHEN VI.MONTH<=${ENDPERIOD} AND VI.VDC = -1 THEN ").append(SQL_HANDLER.nullToValue("VI.FCY * -1", "0")).append(" ELSE 0 END) AS WLJNUM \n");
        sql.append("       ${ASSFIELD} \n");
        sql.append("  FROM IVOUCHER VI LEFT JOIN ACODE SUBJECT ON SUBJECT.ACODE = VI.CODE00 \n");
        sql.append("  LEFT JOIN RCODE CR  \n");
        sql.append("    ON VI.RCODE = CR.RCODE  \n");
        sql.append("  ${ASSJOIN} \n");
        sql.append(" WHERE  1 = 1  \n");
        sql.append(NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "VI."));
        sql.append("          AND VI.YEAR = ${YEAR} \n");
        sql.append("          AND VI.MONTH > (SELECT MIN(MONTH) \n");
        sql.append("                     \t\tFROM MONTHLOGB  \n");
        sql.append("                     \t  WHERE 1 = 1 " + NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "") + " AND YEAR = ${YEAR})  \n");
        sql.append(this.buildCfItemCondi("VI", "RCODE", condi.getCashCode()));
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(this.buildSubjectCondi("SUBJECT", "ACODE", condi.getSubjectCode()));
        }
        sql.append("          ${ASSCONDITION}\n");
        sql.append(" GROUP BY VI.RCODE, CR.RNAME \n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(", SUBJECT.ACODE, SUBJECT.LANGNAME1 \n");
        }
        sql.append("          ${GROUPFIELD}  \n");
        StringBuilder assJoin = new StringBuilder();
        StringBuilder assField = new StringBuilder();
        StringBuilder groupField = new StringBuilder();
        StringBuilder assistCondition = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            assJoin.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=%3$S.%4$s", assistMapping.getAssistSql(), assistMapping.getAssistCode(), "VI", ((AssistPojo)assistMapping.getAccountAssist()).getAssistField()));
            assField.append(String.format(",%1$s.CODE AS %1$s ,%1$s.NAME AS %1$s_NAME", assistMapping.getAssistCode()));
            groupField.append(String.format(",%1$s.CODE,%1$s.NAME", assistMapping.getAssistCode()));
            assistCondition.append(this.matchByRule(assistMapping.getAssistCode(), "CODE", assistMapping.getExecuteDim().getDimValue(), assistMapping.getExecuteDim().getDimRule()));
        }
        Variable variable = new Variable();
        variable.put("STARTPERIOD", condi.getEndPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("ASSCONDITION", assistCondition.toString());
        variable.put("ASSFIELD", assField.toString());
        variable.put("ASSJOIN", assJoin.toString());
        variable.put("GROUPFIELD", groupField.toString());
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        return new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new XjllBalanceResultSetExtractor(condi, assistMappingList));
    }
}

