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
package com.jiuqi.bde.plugin.k3.util;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.plugin.k3.util.AssistPojo;
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
public class K3FetchUtil {
    public static K3FetchUtil fetchUtil;
    private static final String SQL_TEMPLANT = "SELECT FITEMID ID,FNUMBER AS CODE,FNAME AS NAME FROM T_ITEM WHERE FITEMCLASSID = %1$d";
    @Autowired
    private DataSourceService dataSourceService;

    @PostConstruct
    public void init() {
        fetchUtil = this;
        K3FetchUtil.fetchUtil.dataSourceService = this.dataSourceService;
    }

    public static Map<String, Integer> loadSubject(BalanceCondition condi) {
        StringBuilder subjectSql = new StringBuilder();
        subjectSql.append("SELECT\n");
        subjectSql.append("     SUBJECT.FNUMBER AS SUBJECTCODE,\n");
        subjectSql.append("     SUBJECT.FDC AS ORIENT\n");
        subjectSql.append("FROM\n");
        subjectSql.append("     T_ACCOUNT SUBJECT\n");
        return (Map)K3FetchUtil.fetchUtil.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), String.format(subjectSql.toString(), subjectSql, condi.getOrgMapping().getAcctBookCode()), null, (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

            public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                HashMap<String, Integer> result = new HashMap<String, Integer>(512);
                while (rs.next()) {
                    result.put(rs.getString(1), rs.getInt(2));
                }
                return result;
            }
        });
    }

    public String getAdvancedSqlByAssistPojo(AssistPojo assistPojo) {
        return String.format(SQL_TEMPLANT, assistPojo.getAssId());
    }
}

