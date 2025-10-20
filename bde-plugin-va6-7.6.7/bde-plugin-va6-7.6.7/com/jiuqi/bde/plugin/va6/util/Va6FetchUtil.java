/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.constant.ArgumentValueEnum
 *  com.jiuqi.bde.common.util.ContextVariableParseUtil
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  javax.annotation.PostConstruct
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.va6.util;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.ArgumentValueEnum;
import com.jiuqi.bde.common.util.ContextVariableParseUtil;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class Va6FetchUtil {
    public static final String EMPTYGUID = "00000000000000000000000000000000";
    public static final String TBMD_FINORG = "MD_FINORG";
    public static final String TBMD_ACCOUNTSUBJECT = "MD_ACCOUNTSUBJECT";
    public static final String TBMD_CURRENCY = "MD_CURRENCY";
    public static final String FN_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static Va6FetchUtil fetchUtil;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private BaseDataRefDefineService baseDataDefineService;
    private static final String SUBJECT_SELECT_SQL_TMPL = "SELECT CODE, MAX(ORIENT) AS ORIENT FROM (%1$s) T WHERE 1 = 1 GROUP BY CODE ";
    private static final String TBGL_PREASSBALANCE = "GL_PREASSBALANCE";
    private static final String TBGL_ASSBALANCE = "GL_ASSBALANCE";
    private static final String TBGL_PREBALANCE = "GL_PREBALANCE";
    private static final String TBGL_BALANCE = "GL_BALANCE";

    @PostConstruct
    public void init() {
        fetchUtil = this;
        Va6FetchUtil.fetchUtil.dataSourceService = this.dataSourceService;
        Va6FetchUtil.fetchUtil.baseDataDefineService = this.baseDataDefineService;
    }

    public static String getBaseDataSql(IDbSqlHandler sqlHandler, String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ${RECID} AS ID,   \n");
        sql.append("       T.STDCODE AS CODE,  \n");
        sql.append("       T.STDNAME AS NAME  \n");
        sql.append("  FROM ${TABLENAME} T  \n");
        sql.append("  LEFT JOIN MD_FINORG ORG ON T.UNITID = ORG.RECID  \n");
        sql.append(" WHERE 1 = 1  \n");
        sql.append("   AND (  \n");
        sql.append("            T.UNITID = ${EMPTYUUUID}  \n");
        sql.append("         OR ORG.STDCODE = '#unitCode#' \n");
        sql.append("       )  \n");
        Variable variable = new Variable();
        variable.put("RECID", sqlHandler.hex("T.RECID", false));
        variable.put("TABLENAME", tableName);
        variable.put("EMPTYUUUID", sqlHandler.unHex(EMPTYGUID, true));
        return VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
    }

    public static Map<String, Integer> loadSubject(BalanceCondition condi) {
        String subjectSql = Va6FetchUtil.fetchUtil.baseDataDefineService.getByCode(condi.getOrgMapping().getDataSchemeCode(), "MD_ACCTSUBJECT").getAdvancedSql();
        String executeSql = Va6FetchUtil.parse(subjectSql, condi.getOrgMapping().getAcctOrgCode(), condi.getOrgMapping().getAcctBookCode(), String.valueOf(condi.getAcctYear()), String.valueOf(condi.getEndPeriod()), condi.isIncludeUncharged());
        return (Map)Va6FetchUtil.fetchUtil.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), String.format(SUBJECT_SELECT_SQL_TMPL, executeSql), null, (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

            public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                HashMap<String, Integer> result = new HashMap<String, Integer>(512);
                while (rs.next()) {
                    result.put(rs.getString(1), rs.getInt(2));
                }
                return result;
            }
        });
    }

    public static String parse(String text, String acctOrgCode, String acctBookCode, String year, String period, Boolean includeUncharged) {
        return ContextVariableParseUtil.parse((String)text, Va6FetchUtil.initContextVariableMap(acctOrgCode, acctBookCode, year, period, includeUncharged));
    }

    private static Map<String, String> initContextVariableMap(String acctOrgCode, String acctBookCode, String year, String period, Boolean includeUncharged) {
        LinkedHashMap<String, String> precastParamMap = new LinkedHashMap<String, String>(8);
        precastParamMap.put(ContextVariableParseUtil.getVariable((String)ArgumentValueEnum.UNITCODE.getCode()), acctOrgCode);
        precastParamMap.put(ContextVariableParseUtil.getVariable((String)ArgumentValueEnum.YEARPERIOD.getCode()), String.format("%1$s-%2$s", year, CommonUtil.lpad((String)period, (String)"0", (int)2)));
        precastParamMap.put(ContextVariableParseUtil.getVariable((String)ArgumentValueEnum.BOOKCODE.getCode()), acctBookCode);
        precastParamMap.put(ContextVariableParseUtil.getVariable((String)ArgumentValueEnum.YEAR.getCode()), year);
        precastParamMap.put(ContextVariableParseUtil.getVariable((String)ArgumentValueEnum.PERIOD.getCode()), period);
        precastParamMap.put(ContextVariableParseUtil.getVariable((String)ArgumentValueEnum.FULLPERIOD.getCode()), CommonUtil.lpad((String)period, (String)"0", (int)2));
        precastParamMap.put(ContextVariableParseUtil.getVariable((String)ArgumentValueEnum.INCLUDEUNCHARGED.getCode()), Boolean.TRUE.equals(includeUncharged) ? "1" : "0");
        return precastParamMap;
    }

    public static String getTableNameByCondi(boolean preBalance, boolean assist) {
        if (preBalance) {
            return assist ? TBGL_PREASSBALANCE : TBGL_PREBALANCE;
        }
        return assist ? TBGL_ASSBALANCE : TBGL_BALANCE;
    }
}

