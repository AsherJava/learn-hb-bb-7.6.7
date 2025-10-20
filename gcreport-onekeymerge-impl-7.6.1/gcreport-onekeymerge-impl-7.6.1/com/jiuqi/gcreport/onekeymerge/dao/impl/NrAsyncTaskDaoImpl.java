/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.onekeymerge.dao.impl;

import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.onekeymerge.dao.NrAsyncTaskDao;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NrAsyncTaskDaoImpl
implements NrAsyncTaskDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> queryStates(List<String> ids) {
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(ids, (String)"task_id");
        StringBuilder sql = new StringBuilder();
        sql.append("select task_id, state from np_asynctask where ").append(tempTableCondition.getCondition());
        List taskList = this.jdbcTemplate.queryForList(sql.toString());
        return taskList;
    }
}

