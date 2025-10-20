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
package com.jiuqi.bde.plugin.gs5.util;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
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
public class Gs5FetchUtil {
    private static Gs5FetchUtil fetchUtil;
    @Autowired
    private DataSourceService dataSourceService;

    @PostConstruct
    public void init() {
        fetchUtil = this;
        Gs5FetchUtil.fetchUtil.dataSourceService = this.dataSourceService;
    }

    public static String getTableName() {
        return String.format("LSXMLB%1$d", Calendar.getInstance().get(1));
    }

    public static Map<String, Integer> loadSubject(BalanceCondition condi) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT  ZWKMZD_KMBH AS SUBJECTCODE,  \n");
        query.append("          CASE WHEN ZWKMZD_YEFX = 1 THEN 1 ELSE -1 END AS KMORIENT  \n");
        query.append("   FROM   ZWKMZD%1$s SUBJECT ");
        query.append("  WHERE   1=1 AND (SUBJECT.ZWKMZD_DWBH=' ' OR SUBJECT.ZWKMZD_DWBH=?)");
        return (Map)Gs5FetchUtil.fetchUtil.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), String.format(query.toString(), condi.getAcctYear()), new Object[]{condi.getUnitCode()}, (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

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

