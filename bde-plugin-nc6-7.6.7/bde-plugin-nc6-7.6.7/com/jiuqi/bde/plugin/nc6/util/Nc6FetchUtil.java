/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  javax.annotation.PostConstruct
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nc6.util;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
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
public class Nc6FetchUtil {
    private static Nc6FetchUtil fetchUtil;
    public static final String DEFAULT = "DEFAULT";
    public static final String BUSINESSUNIT = "BUSINESSUNIT";
    public static final String UNIT = "UNIT";
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private BaseDataRefDefineService baseDataDefineService;
    private static final String SUBJECT_SELECT_SQL_TMPL = "SELECT CODE, MAX(ORIENT) AS ORIENT FROM (%1$s) T WHERE 1 = 1 AND BOOKCODE = '%2$s' GROUP BY CODE ";

    @PostConstruct
    public void init() {
        fetchUtil = this;
        Nc6FetchUtil.fetchUtil.dataSourceService = this.dataSourceService;
        Nc6FetchUtil.fetchUtil.baseDataDefineService = this.baseDataDefineService;
    }

    public static Map<String, Integer> loadSubject(BalanceCondition condi) {
        String subjectSql = ModelExecuteUtil.replaceContext((String)Nc6FetchUtil.fetchUtil.baseDataDefineService.getByCode(condi.getOrgMapping().getDataSchemeCode(), "MD_ACCTSUBJECT").getAdvancedSql(), (BalanceCondition)condi);
        return (Map)Nc6FetchUtil.fetchUtil.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), String.format(SUBJECT_SELECT_SQL_TMPL, subjectSql, condi.getOrgMapping().getAcctBookCode()), null, (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

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

