/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader
 *  com.jiuqi.bde.plugin.common.service.FetchResultConvert
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nc5.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader;
import com.jiuqi.bde.plugin.common.service.FetchResultConvert;
import com.jiuqi.bde.plugin.nc5.BdeNc5PluginType;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Nc5XjllBalanceLoader
extends BaseXjllDataLoader {
    @Autowired
    private BdeNc5PluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private FetchResultConvert fetchResultConvert;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    protected FetchData queryData(BalanceCondition condi) {
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT CASHFLOW.CFITEMCODE AS CFITEMCODE,  \n");
        sql.append("        CASE WHEN KM.BALANORIENT = 1 THEN 1 ELSE -1 END AS ORIENT ,  \n");
        sql.append("        ${SUBJECTFIELD} AS SUBJECTCODE,  \n");
        sql.append("        HB.CURRTYPECODE AS CURRENCYCODE,  \n");
        sql.append("        SUM(CASE WHEN PZ.PERIOD >= '${STARTPERIOD}' AND PZ.PERIOD <= '${ENDPERIOD}' THEN CASHFLOWCASE.MONEYMAIN ELSE 0 END) AS BQNUM,  \n");
        sql.append("        SUM(CASE WHEN PZ.PERIOD > '00' AND PZ.PERIOD <= '${ENDPERIOD}' THEN CASHFLOWCASE.MONEYMAIN  ELSE 0 END) AS LJNUM,  \n");
        sql.append("        SUM(CASE WHEN PZ.PERIOD >= '${STARTPERIOD}' AND PZ.PERIOD <= '${ENDPERIOD}' THEN CASHFLOWCASE.MONEYMAIN ELSE 0 END) AS WBQNUM,  \n");
        sql.append("        SUM(CASE WHEN PZ.PERIOD > '00' AND PZ.PERIOD <= '${ENDPERIOD}' THEN CASHFLOWCASE.MONEYMAIN ELSE 0 END) AS WLJNUM  \n");
        sql.append("        ${ASSFIELD}");
        sql.append("  FROM GL_DETAIL PZX  \n");
        sql.append(" INNER JOIN (SELECT VCHR.PK_VOUCHER,VCHR.PERIOD,VCHR.PK_GLORGBOOK  \n");
        sql.append("               FROM GL_VOUCHER VCHR  \n");
        sql.append("               INNER JOIN BD_GLORGBOOK BOOK ON VCHR.PK_GLORGBOOK = BOOK.PK_GLORGBOOK");
        sql.append("              WHERE 1=1 \n");
        if (!condi.isIncludeUncharged().booleanValue()) {
            sql.append("                AND VCHR.PK_MANAGER <>'N/A'  \n");
        }
        sql.append("                AND VCHR.DISCARDFLAG = 'N'  \n");
        sql.append("                AND BOOK.GLORGBOOKCODE='${BOOKCODE}' \n");
        sql.append("                AND VCHR.DR = 0  \n");
        sql.append("                AND VCHR.VOUCHERKIND <> 255  \n");
        sql.append("                AND VCHR.ERRMESSAGE IS NULL  \n");
        sql.append("                AND VCHR.YEAR = '${YEAR}'  \n");
        sql.append("                AND VCHR.PERIOD <= '${ENDPERIOD}'  \n");
        sql.append("                AND VCHR.PERIOD > '00') PZ  \n");
        sql.append("    ON PZ.PK_VOUCHER = PZX.PK_VOUCHER  \n");
        sql.append("  INNER JOIN GL_CASHFLOWCASE CASHFLOWCASE  \n");
        sql.append("    ON CASHFLOWCASE.PK_DETAIL = PZX.PK_DETAIL  \n");
        sql.append("  INNER JOIN BD_CASHFLOW CASHFLOW \n");
        sql.append("    ON CASHFLOW.PK_CASHFLOW=CASHFLOWCASE.PK_CASHFLOW  \n");
        sql.append("   \n");
        sql.append("  INNER JOIN BD_ACCSUBJ KM ON PZX.PK_ACCSUBJ = KM.PK_ACCSUBJ AND PZ.PK_GLORGBOOK = KM.PK_GLORGBOOK \n");
        sql.append("         INNER JOIN BD_CURRTYPE HB ON PZX.PK_CURRTYPE = HB.PK_CURRTYPE \n");
        sql.append("   ${ASSJOINSQL}");
        sql.append(" WHERE 1 = 1  \n");
        sql.append(" AND KM.ENDFLAG = 'Y'\n");
        sql.append(" GROUP BY PZ.PERIOD,  \n");
        sql.append("          ${SUBJECTFIELD},  \n");
        sql.append("          KM.BALANORIENT,  \n");
        sql.append("          CASHFLOW.CFITEMCODE  \n");
        sql.append("          ,HB.CURRTYPECODE  \n");
        sql.append("          ${GROUPFIELD}  \n");
        StringBuilder assJoinSql = new StringBuilder();
        StringBuilder assfield = new StringBuilder();
        StringBuilder groupField = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            if ("BD_ACCSUBJ".equals(assistMapping.getAccountAssistCode())) {
                assfield.append(String.format(",KM.%1$s AS %2$s", "SUBJCODE", assistMapping.getAssistCode()));
                continue;
            }
            assJoinSql.append(String.format(" LEFT JOIN (SELECT CHECKVALUE AS ID, FREEVALUEID ASSID, VALUECODE AS CODE FROM GL_FREEVALUE WHERE GL_FREEVALUE.CHECKTYPE ='%1$s') %2$s ON %2$s.ASSID = PZX.ASSID ", assistMapping.getAssistCode(), assistMapping.getAssistCode()));
            assfield.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            groupField.append(String.format(",%1$s.CODE", assistMapping.getAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("STARTPERIOD", String.format("%02d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%02d", condi.getEndPeriod()));
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("ASSJOINSQL", assJoinSql.toString());
        variable.put("SUBJECTFIELD", "KM.SUBJCODE");
        variable.put("ASSFIELD", assfield.toString());
        variable.put("GROUPFIELD", groupField.toString());
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), querySql, null, (ResultSetExtractor)new FetchDataExtractor());
    }
}

