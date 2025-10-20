/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  javax.annotation.PostConstruct
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.nc5.util;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
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
public class Nc5FetchUtil {
    private static Nc5FetchUtil fetchUtil;
    @Autowired
    private DataSourceService dataSourceService;
    public static final String SUBJECTTABLENAME = "BD_ACCSUBJ";

    @PostConstruct
    public void init() {
        fetchUtil = this;
        Nc5FetchUtil.fetchUtil.dataSourceService = this.dataSourceService;
    }

    public static Map<String, Integer> loadSubject(BalanceCondition condi) {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT\n");
        query.append("          KM.SUBJCODE AS SUBJECTCODE,\n");
        query.append("          CASE\n");
        query.append("          WHEN KM.BALANORIENT = 1 THEN 1\n");
        query.append("          ELSE -1\n");
        query.append("          END AS YEORIENT\n");
        query.append(" FROM\n");
        query.append("          BD_ACCSUBJ KM\n");
        query.append(" INNER JOIN  BD_GLORGBOOK ZB ON ZB.PK_GLORGBOOK = KM.PK_GLORGBOOK ");
        query.append(" WHERE    zb.glorgbookcode=? ");
        return (Map)Nc5FetchUtil.fetchUtil.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), query.toString(), new Object[]{condi.getOrgMapping().getAcctBookCode()}, (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

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

