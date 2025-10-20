/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  javax.annotation.PostConstruct
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.gs_cloud.util;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.plugin.gs_cloud.util.GsCloudTableEnum;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class GsCloudFetchUtil {
    public static final String GSCLOUD = "GSCLOUD";
    public static final String BOOKCODE = "BOOKCODE";
    private static GsCloudFetchUtil fetchUtil;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private BaseDataRefDefineService baseDataDefineService;
    private static final String SUBJECT_SELECT_SQL_TMPL = "SELECT CODE, MAX(ORIENT) AS ORIENT FROM (%1$s) GROUP BY CODE ";

    @PostConstruct
    public void init() {
        fetchUtil = this;
        GsCloudFetchUtil.fetchUtil.dataSourceService = this.dataSourceService;
        GsCloudFetchUtil.fetchUtil.baseDataDefineService = this.baseDataDefineService;
    }

    public static Map<String, Integer> loadSubject(BalanceCondition condi) {
        String subjectSql = GsCloudFetchUtil.fetchUtil.baseDataDefineService.getByCode(condi.getOrgMapping().getDataSchemeCode(), "MD_ACCTSUBJECT").getAdvancedSql();
        Variable variable = new Variable();
        variable.put("UNITCODE", condi.getOrgMapping().getAcctOrgCode());
        variable.put(BOOKCODE, condi.getOrgMapping().getAcctBookCode());
        String replaceSql = VariableParseUtil.parse((String)subjectSql, (Map)variable.getVariableMap());
        String lastSql = GsCloudTableEnum.replaceAccYear(replaceSql, condi.getAcctYear());
        return (Map)GsCloudFetchUtil.fetchUtil.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), String.format(SUBJECT_SELECT_SQL_TMPL, lastSql), new Object[0], (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

            public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                HashMap<String, Integer> result = new HashMap<String, Integer>(512);
                while (rs.next()) {
                    result.put(rs.getString(1), rs.getInt(2));
                }
                return result;
            }
        });
    }
}

