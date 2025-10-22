/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.sql.DefaultSQLQueryListener
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener$ParamInfo
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.np.definition.internal.dao;

import com.jiuqi.bi.syntax.sql.DefaultSQLQueryListener;
import com.jiuqi.bi.syntax.sql.ISQLQueryListener;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import com.jiuqi.np.definition.facade.FieldSearchItem;
import com.jiuqi.np.definition.facade.FileldSearchParam;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QueryDataDefinitionDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<FieldSearchItem> fuzzyQueryFieldsByTitleOrCode(FileldSearchParam param) throws Exception {
        int startRow = (param.getPageNo() - 1) * param.getPageSize();
        int endRow = param.getPageNo() * param.getPageSize();
        String mainQuerySql = "SELECT t.fd_key,t.fd_code,t.fd_title FROM des_sys_fielddefine t WHERE (upper(fd_code) LIKE ?CODE or upper(fd_title) LIKE ?TITLE) ORDER BY t.fd_order ";
        ArrayList<FieldSearchItem> fieldSearchItems = new ArrayList<FieldSearchItem>();
        try (Connection connection = this.jdbcTemplate.getDataSource().getConnection();){
            SQLQueryExecutor sqlExecutor = new SQLQueryExecutor(connection);
            sqlExecutor.open(mainQuerySql);
            HashMap<String, ISQLQueryListener.ParamInfo> params = new HashMap<String, ISQLQueryListener.ParamInfo>();
            params.put("CODE", new ISQLQueryListener.ParamInfo("CODE", 6, (Object)("%" + param.getKeywords().toUpperCase() + "%")));
            params.put("TITLE", new ISQLQueryListener.ParamInfo("TITLE", 6, (Object)("%" + param.getKeywords().toUpperCase() + "%")));
            DefaultSQLQueryListener sqlQueryListener = new DefaultSQLQueryListener(params);
            sqlExecutor.registerListener((ISQLQueryListener)sqlQueryListener);
            try (ResultSet resultSet = sqlExecutor.executeQuery(startRow, endRow);){
                while (resultSet.next()) {
                    FieldSearchItem item = new FieldSearchItem();
                    int j = 1;
                    item.setKey(resultSet.getString(j++));
                    item.setCode(resultSet.getString(j++));
                    item.setTitle(resultSet.getString(j));
                    fieldSearchItems.add(item);
                }
            }
            sqlExecutor.close();
        }
        return fieldSearchItems;
    }
}

