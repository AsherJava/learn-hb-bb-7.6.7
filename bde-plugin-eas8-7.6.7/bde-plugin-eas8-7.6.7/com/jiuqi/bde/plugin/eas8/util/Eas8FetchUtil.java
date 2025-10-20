/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  javax.annotation.PostConstruct
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.eas8.util;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
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
public class Eas8FetchUtil {
    private static Eas8FetchUtil fetchUtil;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private BaseDataRefDefineService baseDataDefineService;
    public static final String ASS_SQL = " LEFT JOIN (%1$s) %2$s ON %2$s.ID=ASS.%3$s";

    @PostConstruct
    public void init() {
        fetchUtil = this;
        Eas8FetchUtil.fetchUtil.dataSourceService = this.dataSourceService;
        Eas8FetchUtil.fetchUtil.baseDataDefineService = this.baseDataDefineService;
    }

    public static String getBalanceTableName(Boolean includeAss) {
        return includeAss != false ? "T_GL_ASSISTBALANCE" : "T_GL_ACCOUNTBALANCE";
    }

    public static Map<String, Integer> loadSubject(BalanceCondition condi) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n");
        query.append("      SUBJECT.FNUMBER AS SUBJECTCODE,\n");
        query.append("      SUBJECT.FDC AS ORIENT\n");
        query.append("FROM\n");
        query.append("      T_BD_ACCOUNTVIEW SUBJECT\n");
        query.append("INNER JOIN T_ORG_COMPANY COMPANY ON\n");
        query.append("      COMPANY.FACCOUNTTABLEID = SUBJECT.FACCOUNTTABLEID\n");
        query.append("      AND SUBJECT.FCompanyID = COMPANY.FID\n");
        query.append("WHERE\n");
        query.append("      COMPANY.FNUMBER = ?\n");
        return (Map)Eas8FetchUtil.fetchUtil.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), query.toString(), new Object[]{condi.getOrgMapping().getAcctOrgCode()}, (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

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

