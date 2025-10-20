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
package com.jiuqi.bde.plugin.u8.util;

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
public class BdeU8FetchUtil {
    private static BdeU8FetchUtil fetchUtil;
    public static String SQL_TEMP;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private BaseDataRefDefineService baseDataDefineService;

    @PostConstruct
    public void init() {
        fetchUtil = this;
        BdeU8FetchUtil.fetchUtil.dataSourceService = this.dataSourceService;
        BdeU8FetchUtil.fetchUtil.baseDataDefineService = this.baseDataDefineService;
    }

    public static Map<String, Integer> loadSubject(BalanceCondition condi) {
        String sql = "SELECT CCODE, CASE WHEN SUBJECT.BPROPERTY=1 THEN 1 ELSE -1 END AS ORIENT FROM CODE SUBJECT WHERE SUBJECT.IYEAR = ? ";
        return (Map)BdeU8FetchUtil.fetchUtil.dataSourceService.query(condi.getOrgMapping().getDataSourceCode(), sql, new Object[]{condi.getAcctYear()}, (ResultSetExtractor)new ResultSetExtractor<Map<String, Integer>>(){

            public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                HashMap<String, Integer> result = new HashMap<String, Integer>(512);
                while (rs.next()) {
                    result.put(rs.getString(1), rs.getInt(2));
                }
                return result;
            }
        });
    }

    static {
        SQL_TEMP = "SELECT %1$s AS ID,%1$s AS CODE,%2$s AS NAME FROM %3$s ";
    }
}

