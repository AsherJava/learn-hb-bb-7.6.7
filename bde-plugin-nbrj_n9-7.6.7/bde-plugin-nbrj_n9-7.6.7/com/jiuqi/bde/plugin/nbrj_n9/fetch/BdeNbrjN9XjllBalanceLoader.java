/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO
 *  com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nbrj_n9.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.datamodel.xjll.BaseXjllDataLoader;
import com.jiuqi.bde.plugin.nbrj_n9.BdeNbrjN9PluginType;
import com.jiuqi.bde.plugin.nbrj_n9.util.AssistPojo;
import com.jiuqi.bde.plugin.nbrj_n9.util.NbrjN9FetchUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BdeNbrjN9XjllBalanceLoader
extends BaseXjllDataLoader {
    @Autowired
    private BdeNbrjN9PluginType bdeNbrjN9PluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public IBdePluginType getPluginType() {
        return this.bdeNbrjN9PluginType;
    }

    protected FetchData queryData(BalanceCondition condi) {
        IDbSqlHandler SQL_HANDLER = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        String orgMappingType = StringUtils.isEmpty((String)schemeMappingProvider.getOrgMappingType()) ? "DEFAULT" : schemeMappingProvider.getOrgMappingType();
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT \n");
        sql.append("       VI.RCODE AS CFITEMCODE,  \n");
        sql.append("       SUBJECT.ACODE AS SUBJECTCODE,\n");
        sql.append("       VI.CODE04 AS CURRENCYCODE,  \n");
        sql.append("       SUBJECT.DC AS ORIENT,  \n");
        sql.append("       SUM(CASE WHEN VI.MONTH>=${STARTPERIOD} AND VI.MONTH<=${ENDPERIOD} AND VI.VDC = 1 THEN ").append(SQL_HANDLER.nullToValue("VI.SCY", "0")).append("WHEN VI.MONTH>=${STARTPERIOD} AND VI.MONTH<=${ENDPERIOD} AND VI.VDC = -1 THEN ").append(SQL_HANDLER.nullToValue("VI.SCY * -1", "0")).append(" ELSE 0 END) AS BQNUM, \n");
        sql.append("       SUM(CASE WHEN VI.MONTH>=${STARTPERIOD} AND VI.MONTH<=${ENDPERIOD} AND VI.VDC = 1 THEN ").append(SQL_HANDLER.nullToValue("VI.FCY", "0")).append("WHEN VI.MONTH>=${STARTPERIOD} AND VI.MONTH<=${ENDPERIOD} AND VI.VDC = -1 THEN ").append(SQL_HANDLER.nullToValue("VI.FCY * -1", "0")).append(" ELSE 0 END) AS WBQNUM, \n");
        sql.append("       SUM(CASE WHEN VI.MONTH<=${ENDPERIOD} AND VI.VDC = 1 THEN ").append(SQL_HANDLER.nullToValue("VI.SCY", "0")).append("WHEN VI.MONTH<=${ENDPERIOD} AND VI.VDC = -1 THEN ").append(SQL_HANDLER.nullToValue("VI.SCY * -1", "0")).append(" ELSE 0 END) AS LJNUM, \n");
        sql.append("       SUM(CASE WHEN VI.MONTH<=${ENDPERIOD} AND VI.VDC = 1 THEN ").append(SQL_HANDLER.nullToValue("VI.FCY", "0")).append("WHEN VI.MONTH<=${ENDPERIOD} AND VI.VDC = -1 THEN ").append(SQL_HANDLER.nullToValue("VI.FCY * -1", "0")).append(" ELSE 0 END) AS WLJNUM \n");
        sql.append("       ${ASSFIELD} \n");
        sql.append("  FROM IVOUCHER VI LEFT JOIN ACODE SUBJECT ON SUBJECT.ACODE = VI.CODE00 \n");
        sql.append("  ${ASSJOIN} \n");
        sql.append(" WHERE  1 = 1  \n");
        sql.append(NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "VI."));
        sql.append("   AND VI.YEAR = ${YEAR} \n");
        sql.append("   AND VI.MONTH > (SELECT MIN(MONTH) \n");
        sql.append("                     FROM MONTHLOGB  \n");
        sql.append("                    WHERE 1 = 1 " + NbrjN9FetchUtil.buildUnitSql(orgMappingType, condi.getOrgMapping(), "") + " AND YEAR = ${YEAR})  \n");
        sql.append("   AND VI.RCODE IS NOT NULL  \n");
        sql.append(" GROUP BY VI.RCODE, SUBJECT.ACODE, SUBJECT.DC, VI.CODE04 \n");
        sql.append("       ${GROUPFIELD} \n");
        Variable variable = new Variable();
        StringBuilder assJoin = new StringBuilder();
        StringBuilder assField = new StringBuilder();
        StringBuilder groupField = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            assJoin.append(String.format(" LEFT JOIN (%1$s) %2$s ON %2$s.ID=%3$S.%4$s", assistMapping.getAssistSql(), assistMapping.getAssistCode(), "VI", ((AssistPojo)assistMapping.getAccountAssist()).getAssistField()));
            assField.append(String.format(",%1$s.CODE AS %1$s", assistMapping.getAssistCode()));
            groupField.append(String.format(",%1$s.CODE", assistMapping.getAssistCode()));
        }
        variable.put("STARTPERIOD", condi.getStartPeriod().toString());
        variable.put("ENDPERIOD", condi.getEndPeriod().toString());
        variable.put("YEAR", condi.getAcctYear().toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("ASSFIELD", assField.toString());
        variable.put("ASSJOIN", assJoin.toString());
        variable.put("GROUPFIELD", groupField.toString());
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u73b0\u91d1\u6d41\u91cf", (Object)condi, (String)lastSql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), lastSql, new Object[0], (ResultSetExtractor)new FetchDataExtractor());
    }
}

