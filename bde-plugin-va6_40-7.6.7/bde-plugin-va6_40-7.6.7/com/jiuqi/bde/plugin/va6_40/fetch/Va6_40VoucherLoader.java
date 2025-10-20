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
 *  com.jiuqi.bde.bizmodel.execute.util.TempTableUtils
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.bde.plugin.common.datamodel.voucher.BaseVoucherDataLoader
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.va6_40.fetch;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.adaptor.util.SqlHandlerUtil;
import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.assist.AssistMappingBO;
import com.jiuqi.bde.bizmodel.execute.assist.DataSchemeMappingProvider;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.datamodel.voucher.BaseVoucherDataLoader;
import com.jiuqi.bde.plugin.va6_40.BdeVa6_40PluginType;
import com.jiuqi.bde.plugin.va6_40.util.Va6_40FetchUtil;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class Va6_40VoucherLoader
extends BaseVoucherDataLoader {
    @Autowired
    private BdeVa6_40PluginType pluginType;
    @Autowired
    private DataSourceService dataSourceService;

    public IBdePluginType getPluginType() {
        return this.pluginType;
    }

    protected FetchData queryData(BalanceCondition condi) {
        this.buildTempTable(condi);
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(condi.getOrgMapping().getDataSourceCode()));
        DataSchemeMappingProvider schemeMappingProvider = new DataSchemeMappingProvider(condi);
        List assistMappingList = schemeMappingProvider.getAssistMappingList();
        condi.setIncludeUncharged(Boolean.valueOf(condi.isIncludeUncharged() == null || condi.isIncludeUncharged() != false));
        ArrayList args = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUBJECT.CODE AS SUBJECTCODE,  \n");
        sql.append("       CURRENCY.CODE AS CURRENCYCODE,  \n");
        sql.append("       SUBJECT.ORIENT AS ORIENT  ");
        sql.append("       ${ASSIST_SELECT_SQL}  ");
        sql.append("       ,0 AS NC,  \n");
        sql.append("       SUM((CASE WHEN VCHR.CREATEDATE <= ${PREVIOUSDATE} THEN (ITEM.DEBIT - ITEM.CREDIT) ELSE 0 END) * SUBJECT.ORIENT) AS C,  \n");
        sql.append("       SUM(ITEM.DEBIT) AS JF,  \n");
        sql.append("       SUM(ITEM.CREDIT) AS DF,  \n");
        sql.append("       SUM(ITEM.DEBIT) AS JL,  \n");
        sql.append("       SUM(ITEM.CREDIT) AS DL,  \n");
        sql.append("       SUM((ITEM.DEBIT - ITEM.CREDIT) * SUBJECT.ORIENT) AS YE,  \n");
        sql.append("       0 AS WNC,  \n");
        sql.append("       SUM((CASE WHEN VCHR.CREATEDATE <= ${PREVIOUSDATE} THEN (ITEM.ORGND - ITEM.ORGNC) ELSE 0 END) * SUBJECT.ORIENT) AS WC,  \n");
        sql.append("       SUM(ITEM.ORGND) AS WJF,  \n");
        sql.append("       SUM(ITEM.ORGNC) AS WDF,  \n");
        sql.append("       SUM(ITEM.ORGND) AS WJL,  \n");
        sql.append("       SUM(ITEM.ORGNC) AS WDL,  \n");
        sql.append("       SUM((ITEM.ORGND - ITEM.ORGNC) * SUBJECT.ORIENT) AS WYE  \n");
        sql.append("  FROM GL_VOUCHER_${ACCTYEAR} VCHR  \n");
        sql.append("  JOIN GL_VOUCHERITEM_${ACCTYEAR} ITEM ON VCHR.RECID = ITEM.VCHRID  \n");
        sql.append("  JOIN (${MD_ACCTSUBJECT}) SUBJECT ON ITEM.SUBJECTID = SUBJECT.ID  \n");
        sql.append("  JOIN (${MD_CURRENCY}) CURRENCY ON ITEM.CURRENCYID = CURRENCY.ID  \n");
        sql.append("  JOIN MD_FINORG ORG ON VCHR.UNITID = ORG.RECID AND ORG.STDCODE = '${UNITCODE}'  ");
        if (!StringUtils.isEmpty((String)condi.getOrgMapping().getAcctBookCode())) {
            sql.append("  JOIN SM_BOOK BOOK ON VCHR.ACCTBOOKID = BOOK.RECID AND BOOK.STDCODE = '${BOOKCODE}'  \n");
        }
        sql.append(TempTableUtils.buildSubjectJoinSql((BalanceCondition)condi, (String)"SUBJECT.CODE"));
        sql.append("${ASSIST_JOIN_SQL}  ");
        sql.append(" WHERE 1 = 1  \n");
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"SUBJECT.CODE"));
        sql.append("   AND VCHR.CREATEDATE >= ${STARTDATE}  \n");
        sql.append("   AND VCHR.CREATEDATE <= ${ENDDATE}  \n");
        if (!condi.getIncludeUncharged().booleanValue()) {
            sql.append("   AND VCHR.POSTFLAG = 1  \n");
        }
        sql.append("GROUP BY  SUBJECT.CODE, CURRENCY.CODE, SUBJECT.ORIENT ${ASSIST_GROUP_SQL} \n");
        StringBuilder assistSelectSql = new StringBuilder();
        StringBuilder assistJoinSql = new StringBuilder();
        StringBuilder assistGroupSql = new StringBuilder();
        for (AssistMappingBO assistMapping : assistMappingList) {
            if (assistJoinSql.length() == 0) {
                assistJoinSql.append("JOIN GL_ASSISTCOMB ASSCOMB ON ASSCOMB.RECID = ITEM.ASSCOMBID \n");
            }
            assistSelectSql.append(String.format(", %1$s.CODE AS %1$s \n", assistMapping.getAssistCode()));
            assistJoinSql.append(String.format("LEFT JOIN (%1$s) %2$s ON ASSCOMB.%3$s = %2$s.ID \n", assistMapping.getAssistSql(), assistMapping.getAssistCode(), assistMapping.getAccountAssistCode()));
            assistGroupSql.append(String.format(",%1$s.CODE", assistMapping.getAssistCode()));
        }
        Variable variable = new Variable();
        variable.put("ASSIST_SELECT_SQL", assistSelectSql.toString());
        variable.put("MD_ACCTSUBJECT", schemeMappingProvider.getSubjectSql());
        variable.put("MD_CURRENCY", schemeMappingProvider.getCurrencySql());
        variable.put("ASSIST_JOIN_SQL", assistJoinSql.toString());
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put("BOOKCODE", condi.getOrgMapping().getAcctBookCode());
        variable.put("ASSIST_GROUP_SQL", assistGroupSql.toString());
        variable.put("ACCTYEAR", String.valueOf(condi.getAcctYear()));
        Date previousDate = DateUtils.previousDateOf((Date)condi.getEndDate());
        variable.put("PREVIOUSDATE", sqlHandler.toDate(DateUtils.format((Date)previousDate)));
        variable.put("STARTDATE", sqlHandler.toDate(DateUtils.format((Date)condi.getStartDate())));
        variable.put("ENDDATE", sqlHandler.toDate(DateUtils.format((Date)condi.getEndDate())));
        String querySql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        querySql = Va6_40FetchUtil.parse(querySql, condi);
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u51ed\u8bc1\u4f59\u989d", (Object)condi, (String)querySql);
        return (FetchData)this.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), querySql, args.toArray(), (ResultSetExtractor)new FetchDataExtractor());
    }
}

