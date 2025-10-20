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
package com.jiuqi.bde.plugin.eas8.penetrate.balance;

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
import com.jiuqi.bde.plugin.eas8.Eas8PluginType;
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
public class Eas8XjllBalanceContentProvider
extends AbstractXjllBalanceContentProvider {
    @Autowired
    private Eas8PluginType eas8PluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public String getPluginType() {
        return this.eas8PluginType.getSymbol();
    }

    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    protected QueryParam<PenetrateBalance> getQueryParam(PenetrateBaseDTO condi) {
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        BalanceCondition fetchCondi = PenetrateUtil.convert2Condi((PenetrateBaseDTO)condi, (String)this.getBizModel());
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(fetchCondi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        ArrayList<Object> args = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT\n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append("          KM.FNUMBER AS MD_ACCTSUBJECT,  \n");
            sql.append("          KM.FNAME_L1 AS MD_ACCTSUBJECT_NAME,\n");
        }
        sql.append("          CI.FNUMBER AS MD_CFITEM,  \n");
        sql.append("          CI.FNAME_L2 AS MD_CFITEM_NAME,\n");
        sql.append("          SUM(" + sqlHandler.nullToValue("CASE WHEN PERIOD.FPERIODNUMBER >= 1 AND PERIOD.FPERIODNUMBER <= ${ENDPERIOD} THEN CR.FPRIMARYCOEFFIENT * CR.FLOCALAMOUNT ELSE 0 END ", "0") + ") AS LJNUM,\n");
        sql.append("          SUM(" + sqlHandler.nullToValue("CASE WHEN PERIOD.FPERIODNUMBER >= 1 AND PERIOD.FPERIODNUMBER <= ${ENDPERIOD} THEN CR.FPRIMARYCOEFFIENT * CR.FORIGINALAMOUNT ELSE 0 END ", "0") + ") AS WLJNUM,\n");
        sql.append("          SUM(" + sqlHandler.nullToValue("CASE WHEN PERIOD.FPERIODNUMBER = ${ENDPERIOD} THEN CR.FPRIMARYCOEFFIENT * CR.FLOCALAMOUNT ELSE 0 END  ", "0") + ") AS BQNUM,\n");
        sql.append("          SUM(" + sqlHandler.nullToValue("CASE WHEN PERIOD.FPERIODNUMBER = ${ENDPERIOD} THEN CR.FPRIMARYCOEFFIENT * CR.FORIGINALAMOUNT ELSE 0 END ", "0") + ") AS WBQNUM \n");
        sql.append("       ${ASSFIELD} \n");
        sql.append("  FROM T_GL_VOUCHER VOUCHER  \n");
        sql.append("  INNER JOIN T_GL_VOUCHERENTRY V  \n");
        sql.append("    ON VOUCHER.FID = V.FBillID\n");
        sql.append("  INNER JOIN T_BD_ACCOUNTVIEW KM  \n");
        sql.append("    ON KM.FID = V.FACCOUNTID  \n");
        sql.append("  LEFT JOIN T_BD_PERIOD PERIOD  \n");
        sql.append("    ON VOUCHER.FPERIODID = PERIOD.FID  \n");
        sql.append(" LEFT JOIN T_GL_CASHFLOWRECORD CR  \n");
        sql.append("    ON (V.FID = CR.FOPPOSINGACCOUNTENTRYID OR V.FID = CR.FSUPPLEMENTARYITEMID)  \n");
        sql.append("  LEFT JOIN T_BD_ASSISTANTHG ASS  \n");
        sql.append("    ON CR.FASSGRPID = ASS.FID  \n");
        sql.append(" LEFT JOIN T_BD_CASHFLOWITEM CI  \n");
        sql.append("    ON CR.FPRIMARYITEMID = CI.FID  \n");
        sql.append(" LEFT JOIN T_ORG_COMPANY ORG  \n");
        sql.append("    ON VOUCHER.FCOMPANYID = ORG.FID  \n");
        sql.append(" LEFT JOIN T_BD_CURRENCY HB  \n");
        sql.append("    ON V.FCURRENCYID = HB.FID  \n");
        sql.append("  ${ASSJOIN} \n");
        sql.append(" WHERE PERIOD.FPERIODYEAR = ?  \n");
        args.add(condi.getAcctYear());
        sql.append("   AND ORG.FNUMBER = ?  \n");
        args.add(condi.getOrgMapping().getAcctOrgCode());
        sql.append(this.buildCfItemCondi("CI", "FNUMBER", condi.getCashCode()));
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(this.buildSubjectCondi("KM", "FNUMBER", condi.getSubjectCode()));
        }
        sql.append("          ${ASSCONDITION}\n");
        sql.append("   AND VOUCHER.FCASHFLOWFLAG <> 0  \n");
        sql.append("   AND VOUCHER.FBIZSTATUS <> 0  \n");
        sql.append("   AND VOUCHER.FBIZSTATUS <> 2  \n");
        sql.append("   ${INCLUDECON}\n");
        sql.append(" GROUP BY CI.FNUMBER, CI.FNAME_L2 \n");
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            sql.append(", KM.FNUMBER, KM.FNAME_L1 \n");
        }
        sql.append("          ${GROUPFIELD}  \n");
        StringBuilder assJoin = new StringBuilder();
        StringBuilder groupField = new StringBuilder();
        StringBuilder assField = new StringBuilder();
        StringBuilder assistCondition = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            assJoin.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=ASS.%3$s", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
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
        variable.put("INCLUDECON", condi.getIncludeUncharged() != false ? "" : "AND VOUCHER.FBizStatus = 5\n");
        String executeSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        return new QueryParam(executeSql, args.toArray(), (ResultSetExtractor)new XjllBalanceResultSetExtractor(condi, assistMappingList));
    }
}

